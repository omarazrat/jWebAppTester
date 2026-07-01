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

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import oa.com.tests.Utils;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractSelectorActionRunner;
import oa.com.tests.actionrunners.interfaces.PathKeeper;
import oa.com.tests.actions.TestAction;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * WARNING: not still productive.
 *
 * @author nestor
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class ScrollActionRunner extends AbstractSelectorActionRunner {

    private enum SCROLL_DIRECTION {
        VERTICAL,
        HORIZONTAL
    };

    /**
     * El desplazamiento en pixeles a ejecutar
     */
    private int x = 0, y = 0;
    private final ResourceBundle globals = ResourceBundle.getBundle("application");

    public ScrollActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        final String command = getAction().getCommand();
        boolean hasXY = false;
        //has selector param?
        try { //has x,y arguments?
            setX(getParamInt("x", command));
            setY(getParamInt("y", command));
            hasXY = true;
        } catch (NumberFormatException nfe) {
            final String errMsg = globals.getString("ScrollRunner.attr.err.noNumbers");
            throw new InvalidActionException(errMsg);
        } catch (ParseException pe) {
            System.out.println("parseExcept.");
            final String errMsg = globals.getString("ScrollRunner.attr.err.parseEx");
            throw new InvalidActionException(errMsg);
        }
    }

    private int getParamInt(String attr, final String command) throws NumberFormatException, ParseException {
        String key = ScrollActionRunner.class.getSimpleName() + ".attr." + attr;
        return Integer.parseInt(Utils.getJSONAttributeML(command, key));
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        if(getSelector()!=null){ //Desplazamiento con el selector...
            final WebElement element = get(driver);
            js.executeScript("arguments[0].scrollLeft+="+x+";",element);
            js.executeScript("arguments[0].scrollTop+="+y+ ";",element);
        }else{//Va sin selector...
//            driver.manage().window().maximize();
            js.executeScript("window.scrollBy(" + getX() + "," + getY() + ")");
        }
    }

    private boolean scroll_Page(WebDriver driver, WebElement webelement, int scrollPoints, SCROLL_DIRECTION direction) {
        if (scrollPoints == 0) {
            return true;
        }
        try {
            String script;
            JavascriptExecutor js = (JavascriptExecutor) driver;
            switch (direction) {
                case HORIZONTAL:
                    script = "arguments[0].scroll(" + scrollPoints + ",0);";
                    break;
                case VERTICAL:
                default:
                    script = "arguments[0].scroll(0," + scrollPoints + ");";
                    break;
            }
            js.executeScript(script, webelement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getActionLog() {
        final ResourceBundle bundle = ResourceBundle.getBundle("application");
        String key = getClass().getSimpleName() + ".action.log";
        if (getSelector() == null) {
            key = getClass().getSimpleName() + ".action.log.noselector";
        }
        return bundle.getString(key);
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        final PathKeeper PathKeeper = getSelector();
        if (PathKeeper != null) {
            log.log(Level.INFO, templateMsg, new String[]{"" + getX(), "" + getY(), PathKeeper.getPath()});
        } else {
            log.log(Level.INFO, templateMsg, new String[]{"" + getX(), "" + getY()});
        }
//        JavascriptExecutor js = (JavascriptExecutor) driver;
        String alertMsg = templateMsg.replace("{0}", "" + getX())
                .replace("{1}", "" + getY());
        if (PathKeeper != null) {
            alertMsg = alertMsg.replace("{2}", PathKeeper.getPath());
        }
//        js.executeAsyncScript("window.status=arguments[0];", alertMsg);
        run(driver);
    }
}
