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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nesto
 */
public interface PluginInterface {

    /**
     * Ejecuta las instrucciones en un archivo.
     *
     * @param file
     * @param log
     */
    List<Exception> exec(File file, Logger log) throws InvalidVarNameException, FileNotFoundException, IOException, InvalidParamException;

    /**
     * Decodifica cualquier candena de texto como:
     * -[$CONTRASEÑA_ENCRIPTADA] -> contraseña
     * -[%VARIABLE] - > valor de variable
     * @param pwdString
     * @return
     * @throws InvalidVarNameException
     * @throws InvalidParamException 
     */
    String parse(String pwdString) throws InvalidVarNameException, InvalidParamException;
    
    void run(String command) throws IOException, InvalidVarNameException, InvalidParamException;
    
    WebDriver getDriver();
}
