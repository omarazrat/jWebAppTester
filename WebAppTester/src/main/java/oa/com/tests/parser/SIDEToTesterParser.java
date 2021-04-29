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
package oa.com.tests.parser;

import oa.com.tests.actionrunners.exceptions.InvalidDirectoryException;
import oa.com.tests.Utils;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actions.TestAction;
import oa.com.tests.scriptactionrunners.ClickActionRunner;
import oa.com.tests.scriptactionrunners.GoActionRunner;
import oa.com.tests.scriptactionrunners.WaitActionRunner;
import oa.com.tests.scriptactionrunners.WriteActionRunner;
import oa.com.tests.webapptester.MainApp;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oa.com.utils.I18n;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Convierte el contenido de un script SIDE a Probador de formularios.
 *
 * @author nesto
 */
public class SIDEToTesterParser {

    private final String NEW_LINE = System.getProperty("line.separator");
    private final String WAIT_ACT_COMMAND = getWaitAction();
    private final List<Pair<String, Reader>> testReaders = new LinkedList<>();
    private String url = "";
    private String testName;
    private static ResourceBundle globals = ResourceBundle.getBundle("application");

    /**
     * Comandos de Selemium IDE soportados por esta herramienta
     */
    private enum COMMANDS {
        open,
        //        setWindowSize,
        click,
        type
    }

    /**
     * Lee de un archivo texto.
     *
     * @param in
     * @throws IOException
     * @throws ParseException
     * @throws BadSyntaxException
     */
    public void parse(File in) throws IOException, ParseException, BadSyntaxException {
        parse(new FileReader(in));
    }

    /**
     * Lee de un flujo de datos.
     *
     * @param in
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     * @throws oa.com.tests.actionrunners.exceptions.BadSyntaxException
     */
    public void parse(Reader in) throws IOException, ParseException, BadSyntaxException {
        testReaders.clear();
        testName = "";
        JSONParser parser = new JSONParser();
        final JSONObject JSONObj = (JSONObject) parser.parse(in);
        checkContainsKeys(JSONObj, "name", "url", "tests");
        testName = (String) JSONObj.get("name");
        url = (String) JSONObj.get("url");
        JSONArray tests = (JSONArray) JSONObj.get("tests");
        Iterator it = tests.iterator();
        while (it.hasNext()) {
            JSONObject test = (JSONObject) it.next();
            LinkedList<Exception> errores = new LinkedList<>();
            final Pair<String, Reader> fileReader = parse(url, test, errores);
            if (!errores.isEmpty()) {
                MainApp.addException(errores.toArray(new Exception[]{}));
            }
            testReaders.add(fileReader);
        }
    }

    /**
     * Guarda lo recolectado con {@link #parse(java.io.File) }
     * o {@link #parse(java.io.Reader) }
     *
     * @param directory El directorio en el cual guardar todos los casos de
     * puebas hallados.
     * @throws oa.com.tests.actionrunners.exceptions.InvalidDirectoryException
     * @throws java.io.IOException
     */
    public void saveAll(File directory) throws InvalidDirectoryException, IOException {
        if (!directory.exists()
                || !directory.isDirectory()) {
            final InvalidDirectoryException idex = new InvalidDirectoryException(directory.getAbsolutePath());
            idex.setContext(globals.getString("parser.side.context"));
            throw idex;
        }

        File parent = new File(directory, testName);
        if (!parent.exists()) {
            parent.mkdir();
        } else {
            for (int i = 0;
                    !parent.exists() && !parent.isDirectory();
                    i++) {
                parent = new File(directory, testName + "_" + i);
                if (!parent.exists()) {
                    parent.mkdir();
                }
            }
        }
        for (Pair<String, Reader> testReader : testReaders) {
            File testFile = new File(parent, testReader.getKey());
            FileWriter writer = new FileWriter(testFile);
            HashMap<String, String> fileVars = new HashMap<>();
            final Date now = new Date();
            fileVars.put("{0}", new SimpleDateFormat("YYYY/MM/dd").format(now));
            Utils.appendToWriter("templates/parser/seleniumide.txt", writer, fileVars);
            writer.append(NEW_LINE);
            IOUtils.copy(testReader.getValue(), writer);
            final String filename = I18n.appendLangCode("notas");
            Utils.appendToWriter("templates/" + filename + ".txt", writer);
            writer.close();
        }
    }

    /**
     * Convierte un test de SIDE
     *
     * @param BASE_URL
     * @param test
     */
    private Pair<String, Reader> parse(String BASE_URL, JSONObject test,
            List<Exception> errores) throws BadSyntaxException {
        StringBuilder buffer = new StringBuilder();
        checkContainsKeys(test, "name", "commands");
        final String name = (String) test.get("name");
        JSONArray commands = (JSONArray) test.get("commands");
        final Iterator it = commands.iterator();
        while (it.hasNext()) {
            JSONObject cmd = (JSONObject) it.next();
            try {
                buffer.append(parseCommand(BASE_URL, cmd)).append(NEW_LINE);
            } catch (Exception e) {
                buffer.append("# comando no soportado:")
                        .append(NEW_LINE)
                        .append("# ").append(cmd)
                        .append(NEW_LINE);
                errores.add(e);
            }
        }

        final StringReader stringReader = new StringReader(buffer.toString());
        Pair<String, Reader> resp = new ImmutablePair<>(name, stringReader);
        return resp;
    }

    /**
     * Convierte un comando de selenium IDE a lenguaje de Probador de
     * formularios
     *
     * @param BASE_URL
     * @param command
     * @return
     */
    private String parseCommand(String BASE_URL, JSONObject command) throws BadSyntaxException {
        checkContainsKeys(command, "comment", "command", "target", "value");
        String resp = "";
        final String comment = ((String) command.get("comment"))
                .replace(NEW_LINE, NEW_LINE + "# ");
        if (!comment.trim().isEmpty()) {
            resp += "# " + comment + NEW_LINE;
            return resp;
        }
        String str_command = (String) command.get("command");
        COMMANDS en_command;
        try {
            en_command = COMMANDS.valueOf(str_command);
        } catch (IllegalArgumentException iae) {
            final BadSyntaxException bse = new BadSyntaxException("Comando no soportado: " + str_command);
            bse.setContext(globals.getString("parser.side.parsingerror"));
            throw bse;
        }
        String target = (String) command.get("target");
        String value = (String) command.get("value");

        AbstractDefaultScriptActionRunner runner = null;
        String runnerAction = getRunnerAction(en_command);

        resp = runnerAction + "=";
        JSONObject JSONaction = new JSONObject();
        String selector = null;
        //Para comandos click, type, hay que esperar antes que el obj exista.
        switch (en_command) {
            case click:
            case type:
                selector = parseSelector(target);
                resp = buildWaitCommand(selector) + resp;
        }
        switch (en_command) {
            case click:
                JSONaction.put("selector", selector);
                resp += JSONaction.toJSONString();
                break;
            case open:
                resp += "{" + BASE_URL + value + "}";
                break;
            case type:
                JSONaction.put("selector", selector);
                JSONaction.put("texto", value);
                resp += JSONaction.toJSONString();
        }
        return resp;
    }

    private String buildWaitCommand(final String selector) {
        JSONObject waitObj = new JSONObject();
        waitObj.put("selector", selector);
        final String waitCommand = WAIT_ACT_COMMAND + "=" + waitObj.toJSONString();
        return waitCommand + NEW_LINE;
    }

    private String parseSelector(String target) {
        //id=Identificacion
        final Pattern pattern1 = Pattern.compile("^id=(.*)$");
        final Matcher matcher1 = pattern1.matcher(target);
        if (matcher1.matches()) {
            return "#" + matcher1.group(1);
        }
        //css=.card-footer
        final Pattern pattern2 = Pattern.compile("^css=(.*)$");
        final Matcher matcher2 = pattern2.matcher(target);
        if (matcher2.matches()) {
            return matcher2.group(1);
        }
        return null;
    }

    private String getRunnerAction(COMMANDS en_command) throws BadSyntaxException {
        AbstractDefaultScriptActionRunner runner;
        String runnerAction;
        //Busca el nombre de la accion a cargar
        TestAction act = new TestAction("={}");
        try {
            switch (en_command) {
                case click:
                    runner = new ClickActionRunner(act);
                    break;
                case open:
                    runner = new GoActionRunner(act);
                    break;
                case type:
                    runner = new WriteActionRunner(act);
                    break;
            }
        } catch (NoActionSupportedException nas) {
            return nas.getRunnerAction();
        } catch (InvalidActionException iae) {
            ;
        }
        return null;
    }

    public void checkContainsKeys(final JSONObject JSONObj, final String... keys) throws BadSyntaxException {
        for (String key : keys) {
            if (!JSONObj.containsKey(key)) {
                final BadSyntaxException bse = new BadSyntaxException("falta la clave " + key);
                bse.setContext(globals.getString("parser.side.parsingerror"));
                throw bse;
            }
        }
    }

    /**
     * Retorna la accion para el ejecutor "Esperar"
     *
     * @return
     */
    private String getWaitAction() {
        try {
            TestAction t = new TestAction("invalid_action={}");
            try {
                WaitActionRunner runner = new WaitActionRunner(t);
            } catch (NoActionSupportedException ex) {
                return ex.getRunnerAction();
            } catch (InvalidActionException ex) {
                Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
            }
        } catch (BadSyntaxException ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
