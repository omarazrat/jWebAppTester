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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import oa.com.tests.Utils;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actions.TestAction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

/**
 * Para esperar ciertos miliseg, segundos, minutos, horas o días.
 *
 * @author nesto
 */
@Getter
public class PauseActionRunner extends AbstractDefaultScriptActionRunner {

    public enum TimeUnit {
        MILLISECOND,
        SECOND,
        MINUTE,
        HOUR,
        DAY
    }
    /**
     * Cantidad de tiempo
     */
    private int amount;
    /**
     * Unidades del tiempo para hacer pausa.
     */
    private TimeUnit unit;
    /**
     * La conversión a milisegundos para la pausa.
     */
    private long millis = 0;

    public PauseActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        String actionCommand = action.getCommand();

        final String keyName = getClass().getSimpleName() + ".attr.time";
        String strTime = Utils.getJSONAttributeML(keyName, actionCommand);
        if (strTime == null) {
            throw new InvalidActionException(actionCommand);
        }

        final Matcher matcher = Pattern.compile("^([0-9]*).*([S|s|m|h|d]).*$").matcher(strTime);
        if (!matcher.matches()) {
            throw new InvalidActionException(actionCommand);
        }
        int groupIdx = 1;
        amount = Integer.parseInt(matcher.group(groupIdx++));
        switch (matcher.group(groupIdx++)) {
            case "S":
                unit = TimeUnit.MILLISECOND;
                millis = amount;
                break;
            case "s":
                unit = TimeUnit.SECOND;
                millis = amount * 1000;
                break;
            case "m":
                unit = TimeUnit.MINUTE;
                millis = amount * 1000 * 60;
                break;
            case "h":
                unit = TimeUnit.HOUR;
                millis = amount * 1000 * 60 * 60;
                break;
            case "d":
            default:
                unit = TimeUnit.DAY;
                millis = amount * 1000 * 60 * 60 * 24;
                break;
        }
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        Thread.sleep(millis);
    }
}
