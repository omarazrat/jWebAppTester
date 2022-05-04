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
import oa.com.tests.globals.ActionRunnerManager;
import oa.com.tests.globals.ActionRunnerManager.BROWSERTYPE;
import org.junit.Test;

/**
 *
 * @author nesto
 */
public class BrowserSwapperActionRunnerTest extends ActionRunnerBaseTest {

    @Test
    public void testit() throws IOException, InvalidVarNameException {
        for (BROWSERTYPE btype : BROWSERTYPE.values()) {
            try {
//                switch (btype) {
//                    //Change accordingly
//                    case INTERNET_EXPLORER:
//                    case SAFARI:
//                        break;
//                    default:
                        System.out.println("setting browser " + btype);
                        run("browser={" + btype.name() + "}");
                        run("go={https://miro.com/miroverse/space-bingo}");
                        run("pause={\"time\":\"3 s\"}");
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
