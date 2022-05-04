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

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actions.TestAction;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.PathKeeper.SearchTypes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parser basado en seleccion de elementos por seleccion css
 *
 * @author nesto
 */
@Data
public abstract class AbstractSelectorActionRunner
        extends AbstractDefaultScriptActionRunner {

    private PathKeeper selector;

    public AbstractSelectorActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        selector = new PathKeeper(action);
        if (!selector.hasPath()) {
            selector = null;
        } else {
            selector.setRequired(isRequired());
        }
    }

    /**
     * Sobreescribir para determinar si el selector y su tipo son obligatorios.
     *
     * @return
     */
    public boolean isRequired() {
        return true;
    }

    protected WebElement get(WebDriver driver) throws BadSyntaxException {
        final String actionCommand = getAction().getCommand();
        if (this.selector == null) {
            throw new BadSyntaxException(actionCommand);
        }
        return get(driver, selector);
    }

    private WebElement get(WebDriver driver, PathKeeper selector) {
        if (selector.hasPath()) {
            return get(driver, selector.getType(), selector.getPath());
        } else { //mal diligenciado?
            return null;
        }
    }

    public static WebElement get(WebDriver driver, SearchTypes type, String selector) {
        //FIXME: sòlo para manejo interno - dejar el nombre de la variable en el selector.
        //Se podría arreglar obteniendo la ruta xpath directamente.
        WebElement resp = null;
        switch (type) {
            case CSS:
                resp = driver.findElement(By.cssSelector(selector));
                break;
            case XPATH:
            default:
                resp = driver.findElement(By.xpath(selector));
        }
        return resp;
    }

    public static WebElement get(WebElement elem, SearchTypes type, String selector) {
        WebElement resp = null;
        switch (type) {
            case CSS:
                resp = elem.findElement(By.cssSelector(selector));
                break;
            case XPATH:
            default:
                resp = elem.findElement(By.xpath(selector));
        }
        return resp;
    }

    protected List<WebElement> getMany(WebDriver driver) throws BadSyntaxException {
        final String actionCommand = getAction().getCommand();
        if (this.selector == null) {
            throw new BadSyntaxException(actionCommand);
        }
        List<WebElement> resp = new LinkedList<>();
        if (selector.hasPath()) {
            final String path = selector.getPath();
            switch (selector.getType()) {
                case CSS:
                    resp = driver.findElements(By.cssSelector(path));
                    break;
                case XPATH:
                default:
                    resp = driver.findElements(By.xpath(path));
            }
        }
        return resp;
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        String templateMsg = getActionLog();
        log.log(Level.INFO, templateMsg, getSelector());
        run(driver);
    }

}
