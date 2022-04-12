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

import java.io.IOException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nesto
 */
public class ScrollActionRunnerTest extends ActionRunnerBaseTest {

    public ScrollActionRunnerTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIt() throws IOException, InvalidVarNameException {
        run("go={https://lipsum.com/}");
        run("scroll={\"x\":\"10\", \"y\":\"100\"}");
        run("pause={\"time\":\"100 S\"}");
        run("scroll={\"x\":\"10\", \"y\":\"100\"}");
        run("pause={\"time\":\"100 S\"}");
        run("scroll={\"x\":\"10\", \"y\":\"100\"}");
        run("pause={\"time\":\"100 S\"}");
        run("scroll={\"x\":\"10\", \"y\":\"100\"}");
        run("pause={\"time\":\"5s\"}");
        run("go={https://www.w3schools.com/tags/default.asp}");
        run("scroll={\"x\":\"0\", \"y\":\"200\", \"selector\":\"#sidenav\",\"type\":\"css\"}");
        run("pause={\"time\":\"100 S\"}");
        run("scroll={\"x\":\"0\", \"y\":\"200\", \"selector\":\"#leftmenuinner\",\"type\":\"css\"}");
        run("pause={\"time\":\"100 S\"}");
        run("scroll={\"x\":\"0\", \"y\":\"200\", \"selector\":\"#leftmenuinnerinner\",\"type\":\"xpath\"}");
        run("pause={\"time\":\"10 s\"}");
    }
}
