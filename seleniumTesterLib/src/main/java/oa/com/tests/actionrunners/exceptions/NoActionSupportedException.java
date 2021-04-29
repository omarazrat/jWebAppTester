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
package oa.com.tests.actionrunners.exceptions;

import lombok.Data;

/**
 * Para cuando un parser no es compatible con una accion
 * @author nesto
 */
@Data
public class NoActionSupportedException extends AbstractException {
    /**
     * La accion del ejecutor para la cual se intento ejecutar esta accion.
     */
    private String runnerAction;
    /**
     * Creates a new instance of <code>NoActionSupportedException</code> without
     * detail message.
     */
    public NoActionSupportedException() {
    }

    /**
     * Constructs an instance of <code>NoActionSupportedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoActionSupportedException(String msg) {
        super(msg);
    }
}
