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
package oa.com.tests.scriptactionrunners;

import java.io.IOException;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.globals.ActionRunnerBaseTest;
import org.junit.Test;

/**
 *
 * @author nesto
 */
public class ForActionRunnerTest extends ActionRunnerBaseTest{

     @Test
     public void testIt() throws IOException, InvalidVarNameException, InvalidParamException,Exception {
         //forma 1 incremental
         run("go={https://blankslate.io/}\n"+
         "for={\"var\":\"i\", \"exp\":\"{-1..50}\"}\n"+
         "write={\"text\":\" [:i]\",\"selector\":\"#note-area\"}\n"+
         "end={}\n"+
         "pause={\"time\":\"3 s\"}");
         //forma 1 decremento
         run("go={https://blankslate.io/}\n"+
         "for={\"var\":\"i\", \"exp\":\"{1..-50}\"}\n"+
         "write={\"text\":\" [:i]\",\"selector\":\"#note-area\"}\n"+
         "end={}\n"+
         "pause={\"time\":\"3 s\"}");
         //Forma 2
         run("go={https://blankslate.io/}\n"+
         "for={\"var\":\"word\", \"exp\":\"A B C D E F \"}\n"+
         "write={\"text\":\" [:word]\",\"selector\":\"#note-area\"}\n"+
         "end={}\n"+
         "pause={\"time\":\"3 s\"}");
         //another one
         run("go={https://mutable.gallery/board/}\n"+
         "for={\"var\":\"word\", \"exp\":\"A1 B2 C3 D4 E5 F6 \"}\n"+
         "click={\"selector\":\"button:first-of-type\",\"type\":\"css\" }\n"+
         "pause={\"time\":\"1 s\"}\n"+
         "end={}\n"+
         "pause={\"time\":\"3 s\"}");
         //forma 3
        run("go={file:///"+RESOURCES_FOLDER+"/files/checkboxesPage.html}");
        
        run("for={\"var\":\"cb\", \"selector\":\"input\"}\n"+
        "  click={\"selector\":\"[:cb]\"}\n"+
        "  pause={\"time\":\"4 s\"}\n"+
        "end={}\n"+
         "pause={\"time\":\"3 s\"}");
     }

}
