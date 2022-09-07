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
package oa.com.tests.actions;

import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class TestActionTest {

    @Test
    public void testAction_invalidCommand_throwBadSyntaxException() {
        String command = "command={ http://command/ ";
        BadSyntaxException exception = assertThrows(BadSyntaxException.class, () -> {
            new TestAction(command);
        });
        
        assertEquals("command={ http://command/ ", exception.getMessage());
    }
    
    @Test
    public void testAction_emptyValue_throwBadSyntaxException() {
        String command = "";
        BadSyntaxException exception = assertThrows(BadSyntaxException.class, () -> {
            new TestAction(command);
        });

        assertEquals("", exception.getMessage());
    }
    
    @Test
    public void testAction_validCommand_testActionInstance() throws BadSyntaxException {
        String command = "command={selector:div}";
        TestAction testAction = new TestAction(command);
        
        assertEquals("{selector:div}", testAction.getCommand());
        assertEquals("command", testAction.getName());
    }
    
    @Test
    public void testAction_validCommandWithMultipleValues_testActionInstance() throws BadSyntaxException {
        String command = "command={selector: div, type: normal}";
        TestAction testAction = new TestAction(command);

        assertEquals("{selector: div, type: normal}", testAction.getCommand());
        assertEquals("command", testAction.getName());
    }
}
