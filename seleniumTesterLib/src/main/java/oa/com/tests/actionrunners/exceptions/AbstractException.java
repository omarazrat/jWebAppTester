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
 *
 * @author nesto
 */
@Data
public abstract class AbstractException extends Exception {
    /**
     * El contexto del error.
     */
    private String context;
    /**
     * Creates a new instance of <code>AbstractException</code> without detail
     * message.
     */
    public AbstractException() {
    }

    /**
     * Constructs an instance of <code>AbstractException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AbstractException(String msg) {
        super(msg);
        context = msg;
    }
}
