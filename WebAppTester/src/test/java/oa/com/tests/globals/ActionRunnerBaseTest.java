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
package oa.com.tests.globals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import lombok.Getter;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import org.junit.AfterClass;

/**
 *
 * @author nesto
 */
@Getter
public abstract class ActionRunnerBaseTest {

    public ActionRunnerBaseTest() {
        this(ActionRunnerManager.BROWSERTYPE.FIREFOX);
    }
    
    
    /**
     * Opens some web driver and run a simple command
     * Change browser in @BeforeClass as you wish
     * @param command 
     */
    public ActionRunnerBaseTest(ActionRunnerManager.BROWSERTYPE browser) {
        ActionRunnerManager.set(browser);
    }
    
    public void run(String command) throws IOException, InvalidVarNameException, InvalidParamException{
        ActionRunnerManager.runSt(command);
    }
    
    @AfterClass
    public static void tearDownClass() {
            ActionRunnerManager.quit();
    }

}
