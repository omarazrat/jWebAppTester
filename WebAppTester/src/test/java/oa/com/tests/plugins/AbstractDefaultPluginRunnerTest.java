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
package oa.com.tests.plugins;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;
import oa.com.tests.actionrunners.enums.BROWSERTYPE;
import oa.com.tests.actionrunners.enums.PlaceMousePointerOffsetType;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.globals.ActionRunnerManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 * @author nesto
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AbstractDefaultPluginRunnerTest {

    // <editor-fold defaultstate="collapsed" desc="private types">
    private class Plugin extends AbstractDefaultPluginRunner {

        @Override
        public String getButtonActionCommand() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public ActionListener getActionListener() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    };

    private class Manager extends ActionRunnerManager {

        public ActionRunnerManager getInstance() {
            return instance;
        }
    };
    //</editor-fold>
    private static Plugin plugin;
    private static Manager manager;
    private static Logger log = Logger.getAnonymousLogger();

    public AbstractDefaultPluginRunnerTest() {
        if (plugin != null) {
            return;
        }
        plugin = new Plugin();
        manager = new Manager();
        plugin.setActionManager(manager);
    }

    @BeforeAll
    public static void init() {
    }

    @Test
    @Order(1)
    public void fnSetBrowser() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        for(BROWSERTYPE type:new BROWSERTYPE[]{BROWSERTYPE.FIREFOX,BROWSERTYPE.OPERA,BROWSERTYPE.CHROME}){
        Plugin.fnSetBrowser(type);
        //Time for the browser to start
        fnPause(4);
        }
        log.info("fnSetBrowser() ");
    }

    @Test
    @Order(2)
    public void fnGo() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnGo("https://www.findagrave.com/");
        log.info("fnGo()");
    }

    @Test
    @Order(3)
    public void fnWait() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnWait("body");
        log.info("fnWait()");
    }

    @Test
    @Order(4)
    public void fnWait2() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnWait("//*[@id=\\\"firstname\\\"]", "xpath");
        log.info("fnWait2()");
    }

    @Test
    @Order(5)
    public void fnWrite() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        final String selector = "#firstname";
        Plugin.fnScroll(0, 0, selector);
        Plugin.fnWait(selector);
        Plugin.fnWrite(selector, "Walter");
        log.info("fnWrite()");
    }

    @Test
    @Order(6)
    public void fnWrite2() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        String selector = "//*[@id=\\\"lastname\\\"]";
        final String type = "xpath";
        Plugin.fnScroll(0, 0, selector, type);
        Plugin.fnWait(selector, type);
        Plugin.fnWrite(selector, "Chrysler",type);
        log.info("fnWrite2()");
        fnClick(".mr-3");
        fnPause(1);
        fnGo();
        fnWait();
        fnPause(1);
    }

    @Test
    @Order(7)
    public void fnClick() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        String selector = "html body#home-index div#main-wrap.main-wrap section#content div.section-banner.section-form.section-homepage.position-relative.bg-dark.d-flex.flex-column div.container-fluid.order-1 div.row.flex-column div.col.home-search div.grave-search-bg div.grave-search-inner.d-print-none form#memorialNewSearchForm.grave-search.mb-3 div.form-row.mt-3 div.col button.btn.mr-3.btn-primary.btn-lg";
        Plugin.fnPlaceMousePointer(0, 0, PlaceMousePointerOffsetType.FROM_UL_CORNER,selector);
        fnPause("5 s");
        Plugin.fnClick("li.d-none > a:nth-child(1)", "css");
        Plugin.fnWait("/html/body/div[5]/div/div/div[1]/button", "xpath");
        Plugin.fnClick("/html/body/div[5]/div/div/div[1]/button", "xpath");
        Plugin.fnWait();
        Plugin.fnPlaceMousePointer(50, 10);
        Plugin.fnClick(selector);
        fnPause(1);
    }

    public void fnClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnClick(selector);
    }

    public void fnClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnClick(selector, type);
    }

    @Test
    @Order(8)
    public void fnDoubleClick() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        final String selector = "html body#home-index div#main-wrap.main-wrap section#content div.section-banner.section-form.section-homepage.position-relative.bg-dark.d-flex.flex-column div.container-fluid.order-1 div.row.flex-column div.col.home-search div.grave-search-bg h1";
        Plugin.fnPlaceMousePointer(0, 0, PlaceMousePointerOffsetType.FROM_CNTR_OBJECT, selector, "css");
        Plugin.fnDoubleClick();
        Plugin.fnPause(1);
        Plugin.fnClick();
        Plugin.fnDoubleClick(selector);
        Plugin.fnPause(1);
        Plugin.fnClick();
        Plugin.fnDoubleClick(selector,"css");
        Plugin.fnPause(1);
        Plugin.fnClick();
    }

    public void fnDoubleClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnDoubleClick(selector);
    }

    public void fnDoubleClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnDoubleClick(selector, type);
    }

    @Test
    @Order(9)
    public void fnRightClick() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        final String selector = "html body#home-index div#main-wrap.main-wrap section#content div.section-banner.section-form.section-homepage.position-relative.bg-dark.d-flex.flex-column div.container-fluid.order-1 div.row.flex-column div.col.home-search div.grave-search-bg h1";
        Plugin.fnPlaceMousePointer(0, 0, PlaceMousePointerOffsetType.FROM_CNTR_OBJECT, selector, "css");
        Plugin.fnRightClick();
        Plugin.fnPause(1);
        Plugin.fnClick();
        Plugin.fnRightClick(selector);
        Plugin.fnPause(1);
        Plugin.fnClick();
        Plugin.fnRightClick(selector,"css");
        Plugin.fnPause(1);
        Plugin.fnClick();
    }

    public void fnRightClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnRightClick(selector);
    }

    public void fnRightClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnRightClick(selector, type);
    }

    @Test
    @Order(10)
    public void fnScroll() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        int y=50;
        Plugin.fnScroll(0,y);
        Plugin.fnPause(1);
        Plugin.fnScroll(0,-y);
        Plugin.fnPause(1);
        fnScroll(0, y, "/html/body/div[1]/section/div[2]/div/div/div[2]/div/ul/li[1]/a/span","xpath");
        Plugin.fnPause(1);
        fnScroll(0, -y, "/html/body/div[1]/section/div[2]/div/div/div[2]/div/ul/li[1]/a/span","xpath");
        Plugin.fnPause(1);
    }

    public void fnScroll(int x, int y, String selector) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnScroll(x, y, selector);
    }

    public void fnScroll(int x, int y, String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnScroll(x, y, selector, type);
    }

    public void fnPlaceMousePointer(int x, int y) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnPlaceMousePointer(x, y);
    }

    public void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnPlaceMousePointer(x, y, offsetType);
    }

    public void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType, String selector) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnPlaceMousePointer(x, y, offsetType, selector);
    }

    public void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType, String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnPlaceMousePointer(x, y, offsetType, selector, type);
    }

    public void fnPause(int seconds) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnPause(seconds);
    }

    public void fnPause(String time) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnPause(time);
    }

//    @Test
//    @Order(11)
    public void fnPickChoice() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        String selector=" .fg-links-wrapper > .list-unstyled > li",
                variable="LI", 
                title="Opciones", 
                message="Seleccione cualquier accion",
                subselector="a>strong";
        boolean sorted=true;
        
        Plugin.fnPickChoice(selector, variable, title, message, sorted,subselector);
        fnClick("[:LI]>a");
        fnPause(5);
    }

    public void fnPickChoice(String selector, String variable, String title, String message, boolean sorted, String subselector) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnPickChoice(selector, variable, title, message, sorted, subselector);
    }

    public void fnPickChoice(String selector, String variable, String title, String message, boolean sorted, String subselector, String type) throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnPickChoice(selector, variable, title, message, sorted, subselector, type);
    }

    @Test
    @Order(12)
        public void fnSetVariable() throws IOException, InvalidVarNameException, InvalidParamException, Exception {
        Plugin.fnSetVariable("url", "https://es.findagrave.com");
        Plugin.fnGo("[:url]");
        Plugin.fnWait();
    }

    @AfterAll
    public static void finish() {
        Plugin.getDriver().quit();
    }
}
