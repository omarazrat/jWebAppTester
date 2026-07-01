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
package oa.com.tests.actionrunners.interfaces;

import java.util.HashMap;
import java.util.logging.Level;
import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import java.util.logging.Logger;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

/**
 * @author nesto
 */
@Getter
public abstract class AbstractDefaultScriptActionRunner implements ScriptActionRunner {

    private TestAction action;
    private HashMap<String, Boolean> supportedActionCache = new HashMap<>();

    public AbstractDefaultScriptActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        final String actionName = action.getName();
        if (action == null) {
            throw new IllegalArgumentException(actionName);
        }
        boolean matches = false;
        if (supportedActionCache.containsKey(actionName)) {
            matches = supportedActionCache.get(actionName);
        } else {
            matches = matches(action);
            supportedActionCache.put(actionName, matches);
        }
        if (!matches) {
            final NoActionSupportedException except = new NoActionSupportedException(actionName);
            except.setRunnerAction(getActionName());
            throw except;
        }
        this.action = action;
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, getAction().getCommand());
        run(driver);
    }

}
