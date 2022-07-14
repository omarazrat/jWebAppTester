/*
 * Selenium tester- library to be used for projects extending WebAppTester functionality
Copyright (C) 2021-Nestor Arias
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

import org.openqa.selenium.WebDriver;
import java.util.logging.Logger;

/**
 * Un ejecutor de cualquier acción no incluida en un script.
 * @author nesto
 */
public interface ActionRunner {

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
}
