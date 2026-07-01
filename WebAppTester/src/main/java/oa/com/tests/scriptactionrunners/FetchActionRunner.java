/*
 * Web application tester- Utility to test web applications via Selenium 
 * Copyright (C) 2021-Nestor Arias
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package oa.com.tests.scriptactionrunners;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oa.com.tests.Utils;
import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actionrunners.interfaces.VariableProvider;
import oa.com.tests.globals.ActionRunnerManager;
import oa.com.tests.lang.StringVariable;
import oa.com.tests.lang.Variable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

public class FetchActionRunner extends AbstractDefaultScriptActionRunner
        implements VariableProvider {

    private String url;
    private String prefix;
    private String regex;
    private String baseUrl;
    private Map<String, String> fields;
    private Map<String, String> params;
    private Variable variable;

    public FetchActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        String cmd = action.getCommand();
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(cmd);
            url = (String) json.get("url");
            if (url == null || url.isEmpty()) {
                throw new InvalidActionException(cmd);
            }
            regex = (String) json.get("regex");
            baseUrl = (String) json.get("baseUrl");
            prefix = (String) json.get("prefix");
            JSONObject byPath = (JSONObject) json.get("byPath");
            if (byPath == null) {
                throw new InvalidActionException(cmd);
            }
            fields = (Map<String, String>) byPath;
            JSONObject paramsObj = (JSONObject) json.get("params");
            params = paramsObj != null ? (Map<String, String>) paramsObj : null;
        } catch (ParseException e) {
            throw new InvalidActionException(cmd);
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        String resolvedUrl = resolveUrl();
        Logger log = Logger.getLogger("WebAppTester");
        log.log(Level.INFO, "fetch URL: {0}", resolvedUrl);
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(resolvedUrl))
                .timeout(Duration.ofSeconds(30))
                .GET()
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP " + response.statusCode() + " for " + resolvedUrl);
        }
        String body = response.body();
        if (body.length() > 500) {
            log.log(Level.INFO, "fetch response (first 500 chars): {0}", body.substring(0, 500));
            log.log(Level.INFO, "fetch response (last 500 chars): {0}", body.substring(body.length() - 500));
        } else {
            log.log(Level.INFO, "fetch response: {0}", body);
        }
        JSONObject root = (JSONObject) new JSONParser().parse(body);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String varName = (prefix != null && !prefix.isEmpty()) ? prefix + "_" + entry.getKey() : entry.getKey();
            String path = entry.getValue();
            String value = resolveJsonPath(root, path);
            StringVariable var = new StringVariable(varName, value != null ? value : "");
            ActionRunnerManager.addStVariable(var);
        }
        String sentinelName = (prefix != null && !prefix.isEmpty()) ? prefix + "_fetched" : "fetched";
        variable = new StringVariable(sentinelName, "ok");
    }

    private String resolveUrl() {
        String base;
        if (regex == null || regex.isEmpty()) {
            base = url;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(url);
            if (!m.find() || m.groupCount() < 1) {
                throw new RuntimeException("Regex \"" + regex + "\" did not match URL: " + url);
            }
            String id = m.group(1);
            if (baseUrl != null && !baseUrl.isEmpty()) {
                base = baseUrl + id;
            } else {
                base = "https://dataportal.eplan.com/api/parts/" + id;
            }
        }
        if (params != null && !params.isEmpty()) {
            StringBuilder qs = new StringBuilder("?");
            for (Map.Entry<String, String> e : params.entrySet()) {
                if (qs.length() > 1) qs.append("&");
                qs.append(e.getKey());
                qs.append("=");
                qs.append(e.getValue());
            }
            base += qs.toString();
        }
        return base;
    }

    private String[] splitPath(String path) {
        List<String> parts = new ArrayList<>();
        int i = 0;
        while (i < path.length()) {
            if (path.charAt(i) == '{') {
                int end = path.indexOf('}', i);
                if (end == -1) end = path.length();
                parts.add(path.substring(i, end + 1));
                i = end + 1;
                if (i < path.length() && path.charAt(i) == '.') i++;
            } else {
                int end = path.indexOf('.', i);
                if (end == -1) {
                    parts.add(path.substring(i));
                    break;
                }
                parts.add(path.substring(i, end));
                i = end + 1;
            }
        }
        return parts.toArray(new String[0]);
    }

    private String resolveJsonPath(JSONObject root, String path) {
        if (path.startsWith("included.")) {
            return resolveIncludedPath(root, path.substring("included.".length()));
        }
        String[] parts = splitPath(path);
        Object current = resolveParts(root, root, parts, 0);
        return current != null ? current.toString() : null;
    }

    private Object resolveParts(JSONObject root, Object current, String[] parts, int startIdx) {
        for (int i = startIdx; i < parts.length; i++) {
            String part = parts[i];
            if (part.startsWith("{") && part.endsWith("}")) {
                String refPath = part.substring(1, part.length() - 1);
                String refValue = refPath.startsWith("included.")
                    ? resolveIncludedPath(root, refPath.substring("included.".length()))
                    : resolveJsonPath(root, refPath);
                if (refValue == null) return null;
                current = refValue;
            } else if (current instanceof JSONObject) {
                current = ((JSONObject) current).get(part);
            } else {
                return null;
            }
        }
        return current;
    }

    private String resolveIncludedPath(JSONObject root, String path) {
        String[] parts = splitPath(path);
        JSONArray included = (JSONArray) root.get("included");
        if (included == null) return null;
        String type = parts[0];
        String idPart = parts[1];
        String targetId;
        if (idPart.startsWith("{") && idPart.endsWith("}")) {
            String refPath = idPart.substring(1, idPart.length() - 1);
            String resolved = refPath.startsWith("included.")
                ? resolveIncludedPath(root, refPath.substring("included.".length()))
                : resolveJsonPath(root, refPath);
            if (resolved == null) return null;
            targetId = resolved;
        } else {
            targetId = idPart;
        }
        for (Object obj : included) {
            JSONObject entry = (JSONObject) obj;
            if (type.equals(entry.get("type")) && targetId.equals(entry.get("id"))) {
                Object current = resolveParts(root, entry, parts, 2);
                return current != null ? current.toString() : null;
            }
        }
        return null;
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, url);
        run(driver);
    }

    @Override
    public Variable getVariable() {
        return variable;
    }
}
