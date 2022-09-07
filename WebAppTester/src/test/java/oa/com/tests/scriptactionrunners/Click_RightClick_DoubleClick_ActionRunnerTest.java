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
import org.junit.jupiter.api.Test;

/**
 *
 * @author nesto
 */
public class Click_RightClick_DoubleClick_ActionRunnerTest extends ActionRunnerBaseTest {

    public Click_RightClick_DoubleClick_ActionRunnerTest() {

    }

    @Test
    public void testIt() throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        run("go={https://dustinbrett.com/}");
        run("wait={\"selector\":\"li.sc-2412a3de-0:nth-child(1) > button:nth-child(1)\",\"type\":\"css\"}");
        run("right click={\"selector\":\"li.sc-2412a3de-0:nth-child(1) > button:nth-child(1)\",\"type\":\"css\"}");
        run("pause={\"time\":\"100 S\"}");
        //click on desktop
        run("click={\"selector\":\".crHBuw\",\"type\":\"css\"}");
        run("pause={\"time\":\"1 s\"}");
        run("double click={\"selector\":\"li.sc-2412a3de-0:nth-child(1) > button:nth-child(1)\",\"type\":\"css\"}");

        run("wait={\"selector\":\"li.sc-2412a3de-0:nth-child(2)\",\"type\":\"css\"}");
        run("right click={\"selector\":\"li.sc-2412a3de-0:nth-child(2)\",\"type\":\"css\"}");
        run("pause={\"time\":\"100 S\"}");
        //click on desktop
        run("click={\"selector\":\".crHBuw\",\"type\":\"css\"}");
        run("double click={\"selector\":\"li.sc-2412a3de-0:nth-child(2)\",\"type\":\"css\"}");

        run("right click={\"selector\":\"html body div#__next main.giqYCD.sc-f96cc198-0 nav.cdJzMD.sc-123ca132-0\",\"type\":\"css\"}");
        run("pause={\"time\":\"1 s\"}");
        //show the desktop
        run("click={\"selector\":\"html body div#__next>nav\",\"type\":\"css\"}");
        run("pause={\"time\":\"5 s\"}");
    }
}
