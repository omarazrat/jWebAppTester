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

import java.util.logging.Level;
import java.util.logging.Logger;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import lombok.Getter;
import oa.com.tests.Utils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parser basado en seleccion de elementos por seleccion css
 *
 * @author nesto
 */
@Getter
public abstract class AbstractCssSelectorActionRunner extends AbstractDefaultScriptActionRunner {

    /**
     * El selector usado.
     */
    private String selector;

    public AbstractCssSelectorActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        String key = "CssSelectorActionRunner.attr.selector";
        final String actionCommand = getAction().getCommand();
        this.selector = Utils.getJSONAttributeML(actionCommand,key);
    }

    protected WebElement get(WebDriver driver) throws BadSyntaxException {
        final String actionCommand = getAction().getCommand();
        if (this.selector == null) {
            throw new BadSyntaxException(actionCommand);
        }
        return driver.findElement(By.cssSelector(selector));
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, getSelector());
        run(driver); 
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

}
