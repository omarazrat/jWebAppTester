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

import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractSelectorActionRunner;
import oa.com.tests.actions.TestAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * doble clic en algun lado.
 *
 * @author nesto
 */
public class DoubleClickActionRunner extends AbstractSelectorActionRunner {

    public DoubleClickActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        Actions actions = new Actions(driver);
        try {
            final WebElement elem = get(driver);
//        WebUtils.waitToBeClickable(driver, elem);
            actions.moveToElement(elem).doubleClick().build().perform();
        } catch (BadSyntaxException noSelectorEx) {
            //click donde est�
            actions.doubleClick().build().perform();
        }
    }

}
