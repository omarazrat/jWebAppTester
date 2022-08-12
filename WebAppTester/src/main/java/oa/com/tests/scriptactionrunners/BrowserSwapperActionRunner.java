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

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actions.TestAction;
import oa.com.tests.globals.ActionRunnerManager;
import oa.com.tests.actionrunners.enums.BROWSERTYPE;
import oa.com.tests.swing.MainApp;
import org.openqa.selenium.WebDriver;

/**
 * Para cambiar fácilmente de navegador.
 *
 * @author nesto
 */
public class BrowserSwapperActionRunner extends AbstractDefaultScriptActionRunner {

    private BROWSERTYPE browser;

    public BrowserSwapperActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        final Pattern pattern = Pattern.compile("^\\{(.*)\\}$");
        final Matcher matcher = pattern.matcher(action.getCommand());
        if (!matcher.matches()) {
            throw new InvalidActionException(action.getCommand());
        }
        browser = BROWSERTYPE.valueOf(matcher.group(1));
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        MainApp.setBrowser(browser);
        ActionRunnerManager.set(browser);
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        ResourceBundle globals = ResourceBundle.getBundle("application");
        final String browserName = globals.getString("settings.driver." + browser.name() + ".name");
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, browserName);
        run(driver);
    }

}
