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
import oa.com.tests.globals.ActionRunnerManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nesto
 */
public class PickChoiceActionRunnerTest extends ActionRunnerBaseTest {

    public PickChoiceActionRunnerTest() {
//        super(ActionRunnerManager.BROWSERTYPE.OPERA);
    }

    @Test
    public void testIt() throws IOException, InvalidVarNameException, InvalidParamException {
        run("go={https://stackoverflow.com/}\n"
                + "pause={\"time\":\"3 s\"}");
        run("pick choice={\"selector\":\"/html/body/footer/div/nav/div[3]/ul\",\"type\":\"xpath\",\"subselector\":\"li/a\","
                + "\"variable\":\"link\",\"title\":\"" + getClass().getSimpleName() + ":\",\"message\":\"Select a link to navigate\""
                + "\"sorted\":\"no\"}\n"
                + "click={\"selector\":\"[:link]\"}\n"
                + "wait={\"time\":\"10 s\"}");
    }
}
