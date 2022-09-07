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
import java.util.logging.Logger;
import oa.com.tests.actionrunners.enums.BROWSERTYPE;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.globals.ActionRunnerBaseTest;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nesto
 */
public class PlaceMousePointerTest extends ActionRunnerBaseTest{

    public PlaceMousePointerTest() {
        super(BROWSERTYPE.FIREFOX);
    }
    
    @Test
    public void testIt() throws IOException, InvalidParamException, InvalidVarNameException,Exception{
        final Logger log = Logger.getAnonymousLogger();
        log.info("testing type=FROM_UL_CORNER");
        run("go={https://www.facebook.com/thestrangepage}\n"+
                "place mouse pointer={\"offsetType\":\"FROM_UL_CORNER\",\"x\":\"50\",\"y\":\"100\"}\n"+
                "right click={}\n"+
                "pause={\"time\":\"3 s\"}\n"+
                "click={}");
        log.info("testing again, type=FROM_UL_CORNER");
        run("place mouse pointer={\"x\":\"50\",\"y\":\"200\"}\n"+
                "right click={}\n"+
                "pause={\"time\":\"3 s\"}");
        log.info("testing type=FROM_CUR_LOCATION");
        run("place mouse pointer={\"offsetType\":\"FROM_CUR_LOCATION\",\"x\":\"0\",\"y\":\"100\"}\n"+
                "right click={}\n"+
                "pause={\"time\":\"3 s\"}");
        log.info("testing type=FROM_CNTR_OBJECT");
        run("place mouse pointer={\"offsetType\":\"FROM_CNTR_OBJECT\",\"x\":\"150\",\"y\":\"350\",\"selector\":\"img:nth-of-type(2)\"}\n"+
                "right click={}\n"+
                "pause={\"time\":\"3 s\"}");
        log.info("done!");
    }
}
