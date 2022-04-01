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

import java.util.Optional;
import java.util.ResourceBundle;
import oa.com.tests.actions.TestAction;
import java.util.logging.Logger;
import oa.com.utils.I18n;
import org.openqa.selenium.WebDriver;

/**
 * Definicion basica de un lector de instrucciones para el tester. Estas
 * instrucciones se ejecutaran con driver y Selenium.
 *
 * @author nesto
 */
public interface ScriptActionRunner {

    /**
     * La accion debe ser distintiva, unica entre todos los parsers Nota: Se
     * maneja en minusculas.
     *
     * @return
     */
    public default String getActionName() {
        return ResourceBundle.getBundle("application").getString(getClass().getSimpleName() + ".action");
    }
    /**
     * Busca el mensaje con el cual debe correr esta accion.
     * @return 
     */
    public default String getActionLog() {
        final ResourceBundle bundle = ResourceBundle.getBundle("application");
        final String key = getClass().getSimpleName()+".action.log";
        return bundle.getString(key);
    }    

    /**
     * Ejecucion del script correspondiente.
     *
     * @param driver
     * @throws Exception
     */
    public void run(WebDriver driver) throws Exception;

    /**
     * Ejecucion del script correspondiente.
     *
     * @param driver
     * @param log Archivo de registro
     * @throws Exception
     */
    public void run(WebDriver driver, Logger log) throws Exception;

    /**
     * Si este runner soporta este comando o no.
     *
     * @param action
     * @return
     */
    public default boolean matches(TestAction action) {
        final String actionName = action.getName();
        if (getActionName().equals(actionName)) {
            return true;
        }

        final String key = getClass().getSimpleName() + ".action";
        Optional<String> aliasmatch = I18n.aliases(key)
                .stream()
                .filter(alias->alias.equals(actionName))
                .findFirst();
        return aliasmatch.isPresent();
    }
}
