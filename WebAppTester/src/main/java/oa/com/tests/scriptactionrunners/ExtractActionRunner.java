package oa.com.tests.scriptactionrunners;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import oa.com.tests.Utils;
import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actionrunners.interfaces.VariableProvider;
import oa.com.tests.globals.ActionRunnerManager;
import oa.com.tests.lang.StringVariable;
import oa.com.tests.lang.Variable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ExtractActionRunner extends AbstractDefaultScriptActionRunner
        implements VariableProvider {

    private String prefix;
    private Map<String, String> fields;
    private String tab;
    private Variable variable;

    public ExtractActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        String cmd = action.getCommand();
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(cmd);
            prefix = (String) json.get("prefix");
            JSONObject byLabel = (JSONObject) json.get("byLabel");
            if (byLabel == null) {
                throw new InvalidActionException(cmd);
            }
            fields = (Map<String, String>) byLabel;
            tab = (String) json.get("tab");
        } catch (ParseException e) {
            throw new InvalidActionException(cmd);
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        StringBuilder labelsJson = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (!first) labelsJson.append(",");
            labelsJson.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue().replace("\"", "\\\"")).append("\"");
            first = false;
        }
        labelsJson.append("}");

        String js = "(async function(){for(var i=0;i<30;i++){var c=document.querySelector('.properties-container');if(c){var r={};var l=" + labelsJson + ";var rows=c.querySelectorAll('tr');for(var ri=0;ri<rows.length;ri++){var lc=rows[ri].querySelector('.part-property');if(lc){var t=lc.textContent.trim();for(var k in l){if(t.indexOf(l[k])>=0){var vc=rows[ri].querySelector('.part-property-value');r[k]=vc?vc.textContent.trim():'';}}}return JSON.stringify(r);}await new Promise(function(x){setTimeout(x,300)});}return null;})()";

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String result = (String) jsExecutor.executeScript(js);

        if (result == null) {
            throw new RuntimeException("ExtractActionRunner: no se encontr\u00f3 .properties-container despu\u00e9s de 9 segundos");
        }

        JSONObject parsed;
        try {
            parsed = (JSONObject) new JSONParser().parse(result);
        } catch (ParseException e) {
            throw new RuntimeException("Error parseando resultado JS: " + result, e);
        }

        for (Object keyObj : parsed.keySet()) {
            String key = (String) keyObj;
            String value = (String) parsed.get(key);
            String varName = (prefix != null && !prefix.isEmpty()) ? prefix + "_" + key : key;
            StringVariable var = new StringVariable(varName, value);
            ActionRunnerManager.addStVariable(var);
        }
        String sentinelName = (prefix != null && !prefix.isEmpty()) ? prefix + "_extracted" : "extracted";
        variable = new StringVariable(sentinelName, "ok");
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, prefix);
        run(driver);
    }

    @Override
    public Variable getVariable() {
        return variable;
    }
}
