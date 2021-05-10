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
package oa.com.tests.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Una variable en el entorno de ejecución de pruebas
 * @author nesto
 */
@Getter
@RequiredArgsConstructor
public abstract class Variable {
    public enum TYPE{
        STRING,
        NUMBER,
        WEB_ELEMENT
    }
    /**
     * Tipo de esta variable
     * @return 
     */
    @NonNull
    protected TYPE type;
    /**
     * Nombre de la variable.Distintivo entre una y otra.
     * @return 
     */
    protected String name;
    /**
     * Valor de la variable
     * @return 
     */
    protected Object value;

}
