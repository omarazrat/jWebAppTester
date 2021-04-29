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

import org.openqa.selenium.WebDriver;

/**
 * Definición de acción para implementación nativa.
 * @author nesto
 */
public abstract class AbstractDefaultNativeActionRunner implements ActionRunner{
    /**
     * Ponga aquí el nombre de la dll a cargar.
     * 
     * @return 
     */
    public abstract String getLibPath();
    
    @Override
    public void run(WebDriver driver) {
        System.loadLibrary(getLibPath());
    }
    
}
