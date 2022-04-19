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
package oa.com.tests.actionrunners.interfaces;

import java.io.IOException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.globals.ActionRunnerBaseTest;
import oa.com.tests.lang.SelectorVariable;
import oa.com.tests.lang.Variable;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nesto
 */
public class VariableProviderTest 
//extends ActionRunnerBaseTest
{

    public VariableProviderTest() {

    }

    /**
     * Test of getVariable method, of class VariableProvider.
     */
    @Test
    public void testGetVariable() throws IOException, InvalidVarNameException {
//        run("go={https://www.lexico.com/en/definition/hazzle}");
//       ActionRunnerManager.
        System.out.println("getVariable");
        VariableProvider instance = new VariableProviderImpl();
        Variable expResult = null;
        Variable result = instance.getVariable();
        assertEquals(expResult, result);
    }

    public class VariableProviderImpl implements VariableProvider {

        public Variable getVariable() {
            return null;
        }
    }
    
}
