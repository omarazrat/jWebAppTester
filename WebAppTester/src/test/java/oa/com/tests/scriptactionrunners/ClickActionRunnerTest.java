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
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.globals.ActionRunnerBaseTest;
import org.junit.Test;

/**
 *
 * @author nesto
 */
public class ClickActionRunnerTest extends ActionRunnerBaseTest{
    
    public ClickActionRunnerTest() {

    }
    

     @Test
     public void testIt() throws IOException, InvalidVarNameException {
        run("go={http://www.csszengarden.com}");
        run("wait={\"selector\":\".next > a:nth-child(1)\",\"type\":\"css\"}");
        run("click={\"selector\":\".next > a:nth-child(1)\",\"type\":\"css\"}");

        run("go={http://www.csszengarden.com/205}");
        run("wait={\"selector\":\"/html/body/div[1]/aside/div/div[1]/nav/ul/li[1]/a[1]\",\"type\":\"xpath\"}");
        run("click={\"selector\":\"/html/body/div[1]/aside/div/div[1]/nav/ul/li[1]/a[1]\",\"type\":\"xpath\"}");
        run("pause={\"time\":\"5 s\"}");
     }
}
