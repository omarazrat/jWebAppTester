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
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractSelectorActionRunner;
import oa.com.tests.actionrunners.interfaces.PathFinder;
import oa.com.tests.actions.TestAction;
import oa.com.utils.WebUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
#esperar - Espera a que la página cargue completamente despues de un cambio de URL o despues de hacer clic en algun lado.
#esperará a que cierto objeto (dado su selector), aparezca o simplemente a que la página cargue completa
#Ejemplos:
esperar:{}

esperar:{selector:{#__next > div > div > main > div._3ZoET > div > div._2oVR5 > section > section:nth-child(2) > div}}
 * @author nesto
 */
public class WaitActionRunner extends AbstractSelectorActionRunner{

    public WaitActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException, BadSyntaxException {
        super(action);
        if(getSelector()==null){
            final TestAction testAction = new TestAction("command={\"selector\":\"body\"}");
            final PathFinder pathFinder = new PathFinder(testAction);
            setSelector(pathFinder);
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        final WebElement element = get(driver);
        WebUtils.waitToBeClickable(driver, element);
    }
    
    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, getSelector());
        run(driver);
    }

    @Override
    public boolean isRequired() {
        return false;
    }
    
 }
