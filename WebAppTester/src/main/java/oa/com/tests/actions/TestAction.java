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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import oa.com.tests.actionrunners.interfaces.ActionRunner;

/**
 * Una accion debe tener:
 * un nombre unico, que corresponda con un {@link ActionRunner}
 * un comando que sera interpretado por ese ActionRunner
 * Ejemplo:
 * go:{https://www.wolframalpha.com/}
 * @author nesto
 */
@Getter()
public class TestAction {
    private String name;
    private String command;
    /**
     * Constructora a partir de un comando completo.
     * @param fullCommand 
     * @throws oa.com.tests.actionrunners.exceptions.BadSyntaxException 
     */
    public TestAction(String fullCommand) throws BadSyntaxException{
        final Pattern pattern = Pattern.compile("^(.*)=\\{(.*)\\}$",Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(fullCommand);
        if(! matcher.matches() || matcher.groupCount()<2){
            throw new BadSyntaxException(fullCommand);
        }
        int counter = 1;
        name=matcher.group(counter++).toLowerCase();
        command="{"+matcher.group(counter++)+"}";
    }
}
