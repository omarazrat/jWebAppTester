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

import oa.com.tests.lang.Variable;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nesto
 */
public interface IteratorActionRunner{
    /**
     * Inicia o continúa la iteraciòn.
     * Tiene la responsabilida de definir la variable de sistema
     * usando {@link oa.com.tests.globals.ActionRunnerManager#addStVariable(oa.com.tests.lang.Variable)} 
     * @param driver
     * @return La variable que sigue o null, si ya terminó la iteración
     */
    public Variable iterate(WebDriver driver);
}
