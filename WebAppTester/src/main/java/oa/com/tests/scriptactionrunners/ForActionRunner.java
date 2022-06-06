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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import oa.com.tests.Utils;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actionrunners.interfaces.IteratorActionRunner;
import oa.com.tests.actionrunners.interfaces.PathKeeper;
import oa.com.tests.actions.TestAction;
import oa.com.tests.globals.ActionRunnerManager;
import oa.com.tests.lang.SelectorVariable;
import oa.com.tests.lang.StringVariable;
import oa.com.tests.lang.Variable;
import oa.com.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * for estilo linux argumentos: var=nombre de la variable a usar expr
 * [Opcional]=expresión a utilizar que puede ser de dos formas:
 * selector[Opcional]=selector css/xpath cuyos hijos serán utilizados como
 * opciones. type[Opcional]=Tipo del selector (css/xpath). Por momisión = css
 * <ul>
 * <li>{a..b} Recorrido de la variable "var" entre los numeros a y b. b puede
 * ser menor que a.</li>
 * <li>a b c d e</li> Recorrido de la variable "var" entre los valores a, b, c,
 * d, e
 * </ul>
 *
 * @author nesto
 */
public class ForActionRunner extends AbstractDefaultScriptActionRunner
        implements IteratorActionRunner {

    private List<String> interval;
    private PathKeeper path;
    private int intervalPtr = 0;
    private int interval_length = 0;
    private String varName;

    public ForActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        String key = getClass().getSimpleName() + ".attr.var";
        final String actionCommand = getAction().getCommand();
        varName = Utils.getJSONAttributeML(actionCommand, key);
        key = getClass().getSimpleName() + ".attr.expr";
        final String expression = Utils.getJSONAttributeML(actionCommand, key);
        //expression based for
        if (expression != null) {
            final Pattern pattern = Pattern.compile("^\\{(-?[0-9]*)\\.\\.(-?[0-9]*)\\}$");
            final Matcher matcher = pattern.matcher(expression);
            int field = 1;
            //Forma 1: {Num_1.. Num_2}
            if (matcher.matches()) {
                final String stermA = matcher.group(field++);
                final String stermB = matcher.group(field++);
                try {
                    int termA = Integer.parseInt(stermA);
                    int termB = Integer.parseInt(stermB);
                    interval = IntStream.rangeClosed(Math.min(termA, termB), Math.max(termA, termB))
                            .mapToObj(i -> "" + i).map(Object::toString).collect(toList());
                    interval_length = stermA.length();
                    if (termA > termB) {
                        interval_length = stermB.length();
                        Collections.reverse(interval);
                    }

                } catch (NumberFormatException nfe) {
                    throw new InvalidActionException("Invalid numbers: {" + stermA + "," + stermB + "}");
                }
            } else {//forma 2: distinas palabras separadas por espacios
                interval = Arrays.asList(expression.split(" "));
            }
        } else { //Selector based for
            key = "CssSelectorActionRunner.attr.selector";
            final String selector = Utils.getJSONAttributeML(actionCommand, key);
            if (selector == null) {
                throw new InvalidActionException(actionCommand);
            }
            key = "CssSelectorActionRunner.attr.type";
            final String stype = Utils.getJSONAttributeML(actionCommand, key);
            PathKeeper.SearchTypes type = null;
            try {
                type = PathKeeper.SearchTypes.valueOf(stype);
            } catch (IllegalArgumentException | NullPointerException e) {
                type = PathKeeper.SearchTypes.CSS;
            }
            //only css is supported
            if (!type.equals(PathKeeper.SearchTypes.CSS)) {
                throw new InvalidParamException("only selectors of type CSS are supported");
            }
            path = new PathKeeper(selector, type);
        }
    }

    private int countLeftZeroes(final String number) {

        int resp = 0;
        for (int i = 0; i < number.length(); i++) {
            final String curchar = "" + number.charAt(i);
            if (curchar.equals("-")) {
                continue;
            }
            if (!curchar.equals("0")) {
                break;
            } else {
                resp++;
            }
        }
        if (resp == number.length()) {
            resp--;
        }
        return resp;
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        run(driver);
        String templateMsg = getActionLog();
        if (interval != null) {
            log.log(Level.INFO, templateMsg, interval.stream().collect(Collectors.joining(" ")));
        } else {
            final ResourceBundle bundle = ResourceBundle.getBundle("application");
            final String key = getClass().getSimpleName() + ".action.log2";
            templateMsg = bundle.getString(key);
            log.log(Level.INFO, templateMsg, path.getPath());
        }
    }

    @Override
    public Variable iterate(WebDriver driver) {
        final boolean usingInterval = interval != null;
        final int listSize = usingInterval ? interval.size() : Integer.MAX_VALUE;
        if (intervalPtr >= listSize) {
            return null;
        }
        Variable resp = null;
        if (usingInterval) {
            String point = interval.get(intervalPtr++);
            String varValue
                    = interval_length == 0
                            ? point
                            : StringUtils.leftPad("" + point, interval_length, '0');
            if (varValue.contains("-")) {
                varValue = "-" + varValue.replace("-", "");
            }
            resp = new StringVariable(varName, varValue);
        } else {
            final WebElement element = WebUtils.getNthChild(path, driver, intervalPtr + 1);
            if (element == null) {
                return null;
            }
            resp = new SelectorVariable(element, varName, WebUtils.getNthPathKeeper(path, intervalPtr + 1));
            intervalPtr++;
        }
        ActionRunnerManager.addStVariable(resp);
        return resp;
    }

}
