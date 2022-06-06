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
package oa.com.tests.environment;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.IteratorActionRunner;
import oa.com.tests.actionrunners.interfaces.ScriptActionRunner;
import static oa.com.tests.globals.ActionRunnerManager.detectRunner;
import static oa.com.tests.globals.ActionRunnerManager.execRunner;
import static oa.com.tests.globals.ActionRunnerManager.parse;
import static oa.com.tests.globals.ActionRunnerManager.prepareBadSystaxExMsg;
import oa.com.tests.lang.Variable;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nesto
 */
//@Setter
public class IterationEnvironment {

    private IteratorActionRunner runner;
    private Queue<String> instructions;

    public IterationEnvironment(IteratorActionRunner runner) {
        this.runner = runner;
        instructions = new ArrayDeque<String>();
    }

    public void addInstruction(String actionCommand) {
        instructions.add(actionCommand);
    }

    public List<Exception> runitAll(WebDriver driver, String filePath, Logger log) throws Exception {
        Variable var = runner.iterate(driver);
        ScriptActionRunner innerRunner = null;
        List<Exception> resp = new LinkedList<>();
        while (var != null) {
            for (String instruction : instructions) {
                instruction = parse(instruction);
                //Runner detection 
                try {
                    innerRunner = detectRunner(instruction, filePath, log);
                } catch (NoActionSupportedException nase) {
                    resp.add(nase);
                }
                if (innerRunner == null) {
                    continue;
                }
                try {
                    //runner execution
                    execRunner(innerRunner, log);
                } catch (Exception ex) {
                    BadSyntaxException badSyntaxException = new BadSyntaxException(prepareBadSystaxExMsg(instruction, filePath));
                    log.log(Level.SEVERE, instruction, ex);
                    resp.add(badSyntaxException);
                }
            }
            var = runner.iterate(driver);
        }
        return resp;
    }
}
