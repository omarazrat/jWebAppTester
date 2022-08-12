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

import oa.com.tests.actionrunners.enums.PlaceMousePointerOffsetType;
import java.time.Duration;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import oa.com.tests.Utils;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractSelectorActionRunner;
import oa.com.tests.actions.TestAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author nesto
 */
public class PlaceMousePointerActionRunner extends AbstractSelectorActionRunner {

    private final PlaceMousePointerOffsetType offType;
    private final int x;
    private final int y;

    public PlaceMousePointerActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        final String actionCommand = action.getCommand();
        final String simpleName = getClass().getSimpleName();

        String key = simpleName + ".attr.type";
        final String provoffType = Utils.getJSONAttributeML(actionCommand, key);
        this.offType = provoffType == null ? PlaceMousePointerOffsetType.FROM_UL_CORNER : PlaceMousePointerOffsetType.valueOf(provoffType);

        key = simpleName + ".attr.x";
        this.x = Integer.parseInt(Utils.getJSONAttributeML(actionCommand, key));
        key = simpleName + ".attr.y";
        this.y = Integer.parseInt(Utils.getJSONAttributeML(actionCommand, key));
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        switch (offType) {
            case FROM_UL_CORNER:
                PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");
                Sequence actions = new Sequence(mouse, 0)
                        .addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
                ((RemoteWebDriver) driver).perform(Collections.singletonList(actions));
                break;
            case FROM_CUR_LOCATION:
                new Actions(driver).moveByOffset(x, y).perform();
                break;
            case FROM_CNTR_OBJECT:
                new Actions(driver).moveToElement(get(driver), x, y).perform();
                break;
        }
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        final ResourceBundle bundle = ResourceBundle.getBundle("application");
        String key = getClass().getSimpleName() + ".action.log";

        switch (offType) {
            case FROM_UL_CORNER:
                key += "1";
                break;
            case FROM_CNTR_OBJECT:
                key += "2";
                break;
            case FROM_CUR_LOCATION:
            default:
                key += "3";
                break;
        }
        
        String msg = bundle.getString(key);
        if (offType.equals(PlaceMousePointerOffsetType.FROM_CNTR_OBJECT)) {
            msg = msg.replace("2", get(driver).toString());

        }
        msg = msg.replace("0", "" + x)
                .replace("1", "" + y);
        log.log(Level.INFO, msg);
        run(driver);
    }

}
