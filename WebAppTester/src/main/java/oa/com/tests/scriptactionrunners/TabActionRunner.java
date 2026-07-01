package oa.com.tests.scriptactionrunners;

import oa.com.tests.Utils;
import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.globals.ActionRunnerManager;
import org.openqa.selenium.WebDriver;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabActionRunner extends AbstractDefaultScriptActionRunner {

    private String tabId;

    public TabActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        String cmd = action.getCommand();
        final String idKey = getClass().getSimpleName() + ".attr.id";
        tabId = Utils.getJSONAttributeML(cmd, idKey);
        if (tabId == null) {
            throw new InvalidActionException(cmd);
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        String handle = ActionRunnerManager.getTabHandle(tabId);
        if (handle != null) {
            driver.switchTo().window(handle);
        }
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, tabId);
        run(driver);
    }
}
