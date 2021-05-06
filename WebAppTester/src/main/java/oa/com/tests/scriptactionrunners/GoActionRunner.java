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

import java.util.logging.Level;
import java.util.logging.Logger;
import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nesto
 */
public class GoActionRunner extends AbstractDefaultScriptActionRunner{

    private String url;
    public GoActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        final Pattern pattern = Pattern.compile("^\\{(.*)\\}$");
        final Matcher matcher = pattern.matcher(action.getCommand());
        if(!matcher.matches()){
            throw new InvalidActionException(action.getCommand());
        }
        url = matcher.group(1);
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        driver.get(url);
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, url);
        run(driver);
    }
}
