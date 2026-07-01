package oa.com.tests.scriptactionrunners;

import oa.com.tests.Utils;
import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.globals.ActionRunnerManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewTabActionRunner extends AbstractDefaultScriptActionRunner {

    private String url;
    private String tabId;

    public NewTabActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        String cmd = action.getCommand();
        final String urlKey = getClass().getSimpleName() + ".attr.url";
        final String idKey = getClass().getSimpleName() + ".attr.id";
        url = Utils.getJSONAttributeML(cmd, urlKey);
        tabId = Utils.getJSONAttributeML(cmd, idKey);
        if (url == null || tabId == null) {
            throw new InvalidActionException(cmd);
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        String currentHandle = driver.getWindowHandle();
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", url);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        ActionRunnerManager.registerTab(tabId);
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, new Object[]{tabId, url});
        run(driver);
    }
}
