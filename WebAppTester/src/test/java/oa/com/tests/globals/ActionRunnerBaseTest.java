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

import oa.com.tests.actionrunners.enums.BROWSERTYPE;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
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

    public final String RESOURCES_FOLDER = findResourcesFolder();

    public ActionRunnerBaseTest() {
        this(BROWSERTYPE.FIREFOX);
    }

    private String findResourcesFolder() {
        String slash = "/";
        String resp;
        File f = new File("");
        resp = f.getAbsoluteFile() + "/src/main/resources";
        resp = resp.replaceAll("\\\\", slash);
        return resp;
    }

    /**
     * Opens some web driver and run a simple command Change browser in
     *
     * @BeforeClass as you wish
     *
     * @param command
     */
    public ActionRunnerBaseTest(BROWSERTYPE browser) {
        ActionRunnerManager.set(browser);
    }

    public void run(String command) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        ActionRunnerManager.runSt(command);
    }

    @AfterClass
    public static void tearDownClass() {
        ActionRunnerManager.quit();
    }

}
