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

import java.io.IOException;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.globals.ActionRunnerBaseTest;
import oa.com.tests.lang.StringVariable;
import org.junit.Test;

/**
 *
 * @author nesto
 */
public class SetVariableActionRunnerTest extends ActionRunnerBaseTest {

    @Test
    public void testit() throws IOException, InvalidVarNameException, InvalidParamException {
        StringVariable pause_time = new StringVariable("pause_time", "7 s"),
                comment = new StringVariable("comment", "I like this");

        //testing variable names
        for (String varName : new String[]{"MY_CALENDAR", "MY-calendar02",
            "1234567890oiuytrewqsdfghjkl___________________jjj1234567890098765432qwertyuiopkjhgfdsxcvbnm",
            "12", "invalid'_name"}) {
            System.out.println("testing with " + varName);
            try {
                run("set={\"name\":\"" + varName + "\",\"value\":\"https://calendar.google.com\"}\n"
                        + "go={[:" + varName + "]/login.action}\n"
                        + "pause={\"time\":\"5s\"}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Bug: equalsTo function was failing
        assert (!pause_time.equals(comment));

        run("set={\"name\":\"pause_time\",\"value\":\"7 s\"}");
        run("go={https://music-videos-only.icu/pet-shop-boys/watch/i-wouldn-t-normally-do-this-kind-of-thing_c2snohp7.html}");
        run("set={\"name\":\"comment\",\"value\":\"I like this\"}");
        run("write={\"selector\":\"#form-user_name\",\"text\":\"Nestor\"}");
        run("write={\"selector\":\"#form-comment\",\"text\":\"[:comment]\"}");
        run("pause={\"time\":\"[:pause_time]\"}");
    }
}
