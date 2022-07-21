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
import org.junit.Test;

/**
 *
 * @author nesto
 */
public class PauseActionRunnerTest extends ActionRunnerBaseTest {
    @Test
    public void testit() throws IOException, InvalidVarNameException, InvalidParamException{
        String command = "set={\"name\":\"pausetime\",\"value\":\"3 s\"}\n"
                + "go={https://theuselessweb.com/}\n"
                + "pause={\"time\":\"[:pausetime]\"}\n"
                + "click={\"selector\":\"#button\"}\n"
                + "pause={\"time\":\"[:pausetime]\"}";
        run(command);
    }
}
