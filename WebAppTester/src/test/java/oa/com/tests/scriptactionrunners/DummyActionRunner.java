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

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.globals.ActionRunnerBaseTest;
import oa.com.tests.globals.ActionRunnerManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nesto
 */
public class DummyActionRunner extends ActionRunnerBaseTest {

//    public DummyActionRunner() {
//        super(ActionRunnerManager.BROWSERTYPE.CHROME);
//    }

    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testit() throws IOException, InvalidVarNameException, InvalidParamException {
        forInjqueryPagination();
//        forInFreePeople();
    }

    public void forInFreePeople() throws InvalidVarNameException, InvalidParamException, IOException {
        run("for={\"var\":\"browser\", \"exp\":\"CHROME EDGE FIREFOX OPERA SAFARI INTERNET_EXPLORER\"}");
        run("  browser={[:browser]}");
        run("  go={https://www.freepeople.com/}");
        run("  wait={}");
        run("  click={\"selector\":\"button#onetrust-accept-btn-handler\"}");
        run("  for={\"var\":\"i\", \"exp\":\"{1..70}\"}");
        run("    scroll={\"x\":\"0\",\"y\":\"50\"}");
        run("    pause={\"time\":\"50 S\"}");
        run("  end={}");
        run("  scroll={\"x\":\"0\",\"y\":\"-3500\"}");
        run("  for={\"var\":\"div\", \"selector\":\"div.o-pwa-product-tile\"}");
        run("    wait={\"selector\":\"[:div] > a\"}");
        run("    right click={\"selector\":\"[:div] > a\"}");
        run("    pause={\"time\":\"3 s\"}");
        run("    click={\"selector\":\".c-pwa-header-tabs\"}");
        run("  end={}");
        run("end={}");

    }

    private void forInjqueryPagination() throws InvalidParamException, InvalidVarNameException, IOException {
        run("for={\"var\":\"browser\", \"exp\":\"CHROME EDGE FIREFOX OPERA SAFARI INTERNET_EXPLORER\"}");
        run("  browser={[:browser]}");
        run("  go={https://mdbootstrap.com/docs/b4/jquery/tables/pagination#basic-example}");
        
        run("  for={\"var\":\"div\", \"selector\":\"ul.pagination > li.paginate_button\"}");
        run("    click={\"selector\":\"[:div] > a\"}");
        run("    pause={\"time\":\"10 s\"}");
        run("  end={}");
        run("end={}");
        
        
//        run("for={\"var\":\"container\",\"selector\":\"tr > td:nth-child(15) > div.bg-grey.div-paso-inscripcion\",\"type\":\"css\"}");
//        // tr.even:nth-child(250) > td:nth-child(15) > div:nth-child(1)
//        run("wait={\"selector\":\"[:container] > a:first()\",\"type\":\"css\"}");
//        run("click={\"selector\":\"[:container] > a:first()\",\"type\":\"css\"}");
//        run("pause={\"time\":\"7 s\"}");
//        run("end={}");
/*
final String BASE_PATH="C:\\Users\\nesto\\OneDrive\\Documentos\\Laboral\\Empresas\\Blackboard\\bin\\scripts\\1.Ultra\\";
for (String path : new String[]{BASE_PATH+"login\\01.adminUltra-next.bbpd.io.txt"
, BASE_PATH+"courses\\_start.txt"
}) {
File f = new File(path);
final Logger log = Logger.getLogger(getClass().getSimpleName());
ActionRunnerManager.execInstance(f, log);
}
run("click={\"selector\":\"/html/body/div[1]/div[2]/bb-base-layout/div/aside/div[1]/nav/ul/bb-base-navigation-button[4]/div/li/a\""
+ ",\"type\":\"xpath\"}");
run("scroll={\"selector\":\"#main-content-inner\",\"x\":\"0\",,\"y\":\"100\"}");
System.out.println("");
*/
    }
}
