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

import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import oa.com.tests.Utils;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actionrunners.interfaces.AbstractSelectorActionRunner;
import oa.com.tests.actionrunners.interfaces.PathKeeper;
import oa.com.tests.actionrunners.interfaces.VariableProvider;
import oa.com.tests.actions.TestAction;
import oa.com.tests.lang.SelectorVariable;
import oa.com.tests.lang.StringVariable;
import oa.com.tests.lang.Variable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Para establecer el valor de una variable en algún valor Params: name value
 * type[Optional], only required for value= css or xpath selector
 *
 * @author nesto
 */
@Getter
public class SetVariableActionRunner extends AbstractDefaultScriptActionRunner
        implements VariableProvider {

    private Variable variable;
    private String name;

    public SetVariableActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        final String actionCommand = getAction().getCommand();
        String keyName = getClass().getSimpleName() + ".attr.name";
        name = Utils.getJSONAttributeML(actionCommand, keyName);
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        final String actionCommand = getAction().getCommand();
        String keyName = getClass().getSimpleName() + ".attr.value";
        String value = Utils.getJSONAttributeML(actionCommand, keyName);
        //opcional: tipo
        keyName = "CssSelectorActionRunner.attr.type";
        String type = Utils.getJSONAttributeML(actionCommand, keyName);
        if (type != null) {
            PathKeeper path = new PathKeeper(value, type);
            final WebElement element = AbstractSelectorActionRunner.get(driver, path.getType(), path.getPath());
            SelectorVariable var = new SelectorVariable(element, name, path);
            setVariable(var);
        }else{
            StringVariable var = new StringVariable(name, value);
            setVariable(var);
        }
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, name);
        run(driver);
    }

    
    private void setVariable(Variable variable) {
        this.variable = variable;
    }

    
}
