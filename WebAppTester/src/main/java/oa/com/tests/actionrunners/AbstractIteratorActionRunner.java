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
package oa.com.tests.actionrunners;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.joining;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.ExceptionList;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actionrunners.interfaces.ScriptActionRunner;
import oa.com.tests.actions.TestAction;
import static oa.com.tests.globals.ActionRunnerManager.detectRunner;
import static oa.com.tests.globals.ActionRunnerManager.execRunner;
import static oa.com.tests.globals.ActionRunnerManager.prepareBadSystaxExMsg;
import static oa.com.tests.globals.ActionRunnerManager.testEndCommand;
import static oa.com.tests.globals.ActionRunnerManager.testIterativeCommand;
import static oa.com.tests.plugins.AbstractDefaultPluginRunner.parse;
import oa.com.tests.scriptactionrunners.EndActionRunner;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nesto
 */
public abstract class AbstractIteratorActionRunner
        extends AbstractDefaultScriptActionRunner {

    private int openedContexts = 0;
    private String absolutePath;
    private Logger log;
    protected List<String> instructions = new LinkedList<>();

    public AbstractIteratorActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        openedContexts = 1;
    }

    /**
     * Inicia o continúa la iteraciòn. Tiene la responsabilida de definir la
     * variable de sistema usando
     * {@link oa.com.tests.globals.ActionRunnerManager#addStVariable(oa.com.tests.lang.Variable)}
     *
     * @param driver
     * @return La variable que sigue o null, si ya terminó la iteración
     */
    public abstract boolean iterate(WebDriver driver);

    /**
     * Prepara el iterador
     *
     * @param driver
     * @throws Exception
     */
    public abstract void prepare(WebDriver driver) throws Exception;

    /**
     * Alimenta este iterador con las lineas proporcinadas hasta que ya no haya
     * nada mas que ingresar o hasta que se encuentre una instrucción end que
     * finalice pre: hay uno y sólo un enunciado por línea
     *
     * @param filteredLines
     * @return
     */
    public int seed(List<String> filteredLines, String absolutePath, Logger log) throws ExceptionList {
        openedContexts = 1;
        this.absolutePath = absolutePath;
        this.log = log;
        int lineCounter = 0;
        List<Exception> exceptions = new LinkedList<>();
//        try {
        while (lineCounter < filteredLines.size() && openedContexts > 0) {
            //Command preparation and parsing
            String actionCommand = (filteredLines.get(lineCounter++));
            final boolean isEnd = testEndCommand(actionCommand);
            final boolean isIteration = testIterativeCommand(actionCommand);
            addInstruction(actionCommand);
            if (isIteration) {
                openedContexts++;
            }
            if (isEnd) {
                openedContexts--;
            }
        }
        return lineCounter;
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        List<Exception> resp = new LinkedList<>();
        while (iterate(driver)) {
            for (int i = 0; i < instructions.size(); i++) {
                String actionCommand = parse(instructions.get(i));
                ScriptActionRunner runner = null;
                //Runner detection 
                try {
                    runner = detectRunner(actionCommand, absolutePath, log);
                } catch (NoActionSupportedException nase) {
                    resp.add(nase);
                }
                if (runner == null) {
                    continue;
                }

                final boolean isEnd = runner instanceof EndActionRunner;
                try {
                    final boolean isIterator = runner instanceof AbstractIteratorActionRunner;
                    if (isIterator) {
                        AbstractIteratorActionRunner iterator = (AbstractIteratorActionRunner) runner;
                        iterator.prepare(driver);
                        i += iterator.seed(instructions.subList(i+1, instructions.size() - 1), absolutePath, log);
                    }
                    //runner execution
                    execRunner(runner, log);
                } catch (Exception ex) {
                    BadSyntaxException badSyntaxException = new BadSyntaxException(prepareBadSystaxExMsg(actionCommand, absolutePath));
                    log.log(Level.SEVERE, actionCommand, ex);
                    resp.add(badSyntaxException);
                }
            }
        }
    }

    private void addInstruction(String actionCommand) {
        instructions.add(actionCommand);
    }

}
