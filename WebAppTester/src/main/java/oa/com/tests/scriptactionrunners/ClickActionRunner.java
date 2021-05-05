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

import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractCssSelectorActionRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Usa la funcion {@link WebElement#click()}, buscando el elemento en el
 * navegador por {@link By#cssSelector(java.lang.String) Selector css}
 *
 * @author nesto
 */
public class ClickActionRunner extends AbstractCssSelectorActionRunner {

    public ClickActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        get(driver).click();
    }

}
