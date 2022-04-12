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
package oa.com.tests.globals;

import java.io.IOException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nesto
 */
public class WriteActionRunnerTest extends ActionRunnerBaseTest{
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testit() throws IOException, InvalidVarNameException {
         run("go={https://yari-demos.prod.mdn.mozit.cloud/es/docs/Web/HTML/Element/textarea/_sample_.example.html}");
         run("wait={\"selector\":\"body > textarea\"}");
         run("write={\"selector\":\"textarea\","
                 + "\"text\":\"Testing jWebAppTester\"}");
         run("pause={\"time\":\"5 s\"}");
     }
}
