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

/**
 * Para cuando una accion no esta tan bien escrita.
 * @author nesto
 */
public class InvalidActionException extends AbstractException {

    /**
     * Creates a new instance of <code>InvalidActionException</code> without
     * detail message.
     */
    public InvalidActionException() {
    }

    /**
     * Constructs an instance of <code>InvalidActionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidActionException(String msg) {
        super(msg);
    }
}
