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
import java.util.List;
import java.util.logging.Logger;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.lang.Variable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nesto
 */
public class ActionRunnerManagerTest {
    
    public ActionRunnerManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of quit method, of class ActionRunnerManager.
     */
//    @Test
    public void testQuit() {
        System.out.println("quit");
        ActionRunnerManager.quit();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finalize method, of class ActionRunnerManager.
     */
//    @Test
    public void testFinalize() throws Exception {
        System.out.println("finalize");
        ActionRunnerManager instance = null;
//        instance.finalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTreeModel method, of class ActionRunnerManager.
     */
//    @Test
    public void testGetTreeModel() {
        System.out.println("getTreeModel");
        TreeModel expResult = null;
        TreeModel result = ActionRunnerManager.getTreeModel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of set method, of class ActionRunnerManager.
     */
//    @Test
    public void testSet() {
        System.out.println("set");
        ActionRunnerManager.BROWSERTYPE btype = null;
        ActionRunnerManager.set(btype);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exec method, of class ActionRunnerManager.
     */
//    @Test
    public void testExec() throws Exception {
        System.out.println("exec");
        TreePath item = null;
        Logger log = null;
        ActionRunnerManager.exec(item, log);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of prepareBadSystaxExMsg method, of class ActionRunnerManager.
     */
//    @Test
    public void testPrepareBadSystaxExMsg() {
        System.out.println("prepareBadSystaxExMsg");
        String actionCommand = "";
        File file = null;
        String expResult = "";
        String result = ActionRunnerManager.prepareBadSystaxExMsg(actionCommand, file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRootTree method, of class ActionRunnerManager.
     */
//    @Test
    public void testGetRootTree() {
        System.out.println("getRootTree");
        ActionRunnerManager instance = null;
        TreeModel expResult = null;
        TreeModel result = instance.getRootTree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBrowserType method, of class ActionRunnerManager.
     */
//    @Test
    public void testGetBrowserType() {
        System.out.println("getBrowserType");
        ActionRunnerManager instance = null;
        ActionRunnerManager.BROWSERTYPE expResult = null;
        ActionRunnerManager.BROWSERTYPE result = instance.getBrowserType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDriver method, of class ActionRunnerManager.
     */
//    @Test
    public void testGetDriver() {
        System.out.println("getDriver");
        ActionRunnerManager instance = null;
        WebDriver expResult = null;
        WebDriver result = instance.getDriver();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVariables method, of class ActionRunnerManager.
     */
//    @Test
    public void testGetVariables() {
        System.out.println("getVariables");
        ActionRunnerManager instance = null;
        List<Variable> expResult = null;
        List<Variable> result = instance.getVariables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRootTree method, of class ActionRunnerManager.
     */
//    @Test
    public void testSetRootTree() {
        System.out.println("setRootTree");
        TreeModel rootTree = null;
        ActionRunnerManager instance = null;
        instance.setRootTree(rootTree);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBrowserType method, of class ActionRunnerManager.
     */
//    @Test
    public void testSetBrowserType() {
        System.out.println("setBrowserType");
        ActionRunnerManager.BROWSERTYPE browserType = null;
        ActionRunnerManager instance = null;
        instance.setBrowserType(browserType);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDriver method, of class ActionRunnerManager.
     */
//    @Test
    public void testSetDriver() {
        System.out.println("setDriver");
        WebDriver driver = null;
        ActionRunnerManager instance = null;
        instance.setDriver(driver);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVariables method, of class ActionRunnerManager.
     */
//    @Test
    public void testSetVariables() {
        System.out.println("setVariables");
        List<Variable> variables = null;
        ActionRunnerManager instance = null;
        instance.setVariables(variables);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class ActionRunnerManager.
     */
//    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        ActionRunnerManager instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class ActionRunnerManager.
     */
//    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        ActionRunnerManager instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class ActionRunnerManager.
     */
//    @Test
    public void testToString() {
        System.out.println("toString");
        ActionRunnerManager instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
//    @Test
    public void testParse() throws InvalidVarNameException{
        String actionCommand = "clic={" +
                " \"selector\":\"[:Lenguaje] > a" +
                "}";
        System.out.println(ActionRunnerManager.parse(actionCommand));
    }
//    @Test
    public void testResolveSelector4VarDef(){
    }
}
