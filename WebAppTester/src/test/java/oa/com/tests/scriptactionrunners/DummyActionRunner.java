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

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.globals.ActionRunnerBaseTest;
import oa.com.tests.globals.ActionRunnerManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nesto
 */
public class DummyActionRunner extends ActionRunnerBaseTest {

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//    @Test
    public void testit() throws IOException, InvalidVarNameException {
        final String BASE_PATH="C:\\Users\\nesto\\OneDrive\\Documentos\\Laboral\\Empresas\\Blackboard\\bin\\scripts\\1.Ultra\\";
        for (String path : new String[]{BASE_PATH+"login\\01.adminUltra-next.bbpd.io.txt"
                , BASE_PATH+"courses\\_start.txt"
        }) {
            File f = new File(path);
            final Logger log = Logger.getLogger(getClass().getSimpleName());
            ActionRunnerManager.execInstance(f, log);
        }
        run("click={\"selector\":\"/html/body/div[1]/div[2]/bb-base-layout/div/aside/div[1]/nav/ul/bb-base-navigation-button[4]/div/li/a\""
                + ",\"type\":\"xpath\"}");
        run("scroll={\"selector\":\"#main-content-inner\",\"x\":\"0\",,\"y\":\"100\"}");
        System.out.println("");
    }
}
