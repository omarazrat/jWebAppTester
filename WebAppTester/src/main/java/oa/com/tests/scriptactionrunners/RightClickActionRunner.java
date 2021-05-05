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

import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractCssSelectorActionRunner;
import oa.com.tests.actions.TestAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * Hacer click derecho en algun elemento.
 * @author nesto
 */
public class RightClickActionRunner extends AbstractCssSelectorActionRunner{

    public RightClickActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        Actions actions = new Actions(driver);
        actions.contextClick(get(driver));
    }
    
}
