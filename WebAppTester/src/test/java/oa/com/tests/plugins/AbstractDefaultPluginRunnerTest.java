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
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
    public void fnSetBrowser() throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnSetBrowser(BROWSERTYPE.EDGE);
        //Time for the browser to start
        fnPause(4);
        log.info("fnSetBrowser() ");
    }

    @Test
    @Order(2)
    public void fnGo() throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnGo("https://www.soccercoachingpro.com/how-many-players-on-a-soccer-team/");
        log.info("fnGo()");
    }

    @Test
    @Order(3)
    public void fnWait() throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnWait("body");
        log.info("fnWait()");
    }

    @Test
    @Order(4)
    public void fnWait2() throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnWait("//*[@id=\"recent-posts-4\"]", "xpath");
        log.info("fnWait2()");
    }

    @Test
    @Order(5)
    public void fnWrite() throws IOException, InvalidVarNameException, InvalidParamException {
        final String selector = "div.tcb-flex-col:nth-child(1) > div:nth-child(1) > div:nth-child(1) > input:nth-child(1)";
        Plugin.fnScroll(0, 0, selector);
        Plugin.fnWait(selector);
        Plugin.fnWrite(selector, "Hello there");
        fnPause(3);
        log.info("fnWrite()");
    }

    @Test
    @Order(6)
    public void fnWrite2() throws IOException, InvalidVarNameException, InvalidParamException {
        String selector = "html body.post-template-default.single.single-post.postid-1891.single-format-standard.tve-desktop-browser.tve-mozilla-browser div.flex-cnt div.wrp.cnt div.bSeCont section.bSe.left article#comments div.awr div#thrive_container_form_add_comment.lrp form#commentform input#author.text_field.author//*[@id=\"author\"]";
        Plugin.fnScroll(0, 0, selector);
        final String type = "xpath";
        Plugin.fnWait(selector, type);
        Plugin.fnWrite(selector,"it's me", type);
        log.info("fnWrite2()");
    }

    public void fnClick() throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnClick();
    }

    public void fnClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnClick(selector);
    }

    public void fnClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnClick(selector, type);
    }

    public void fnDoubleClick() throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnDoubleClick();
    }

    public void fnDoubleClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnDoubleClick(selector);
    }

    public void fnDoubleClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnDoubleClick(selector, type);
    }

    public void fnRightClick() throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnRightClick();
    }

    public void fnRightClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnRightClick(selector);
    }

    public void fnRightClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnRightClick(selector, type);
    }

    public void fnScroll(int x, int y) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnScroll(x, y);
    }

    public void fnScroll(int x, int y, String selector) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnScroll(x, y, selector);
    }

    public void fnScroll(int x, int y, String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnScroll(x, y, selector, type);
    }

    public void fnPlaceMousePointer(int x, int y) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPlaceMousePointer(x, y);
    }

    public void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPlaceMousePointer(x, y, offsetType);
    }

    public void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType, String selector) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPlaceMousePointer(x, y, offsetType, selector);
    }

    public void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType, String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPlaceMousePointer(x, y, offsetType, selector, type);
    }

    public void fnPause(int seconds) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPause(seconds);
    }

    public void fnPause(String time) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPause(time);
    }

    public void fnPickChoice(String selector, String variable, String title, String message, boolean sorted) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPickChoice(selector, variable, title, message, sorted);
    }

    public void fnPickChoice(String selector, String variable, String title, String message, boolean sorted, String subselector) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPickChoice(selector, variable, title, message, sorted, subselector);
    }

    public void fnPickChoice(String selector, String variable, String title, String message, boolean sorted, String subselector, String type) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnPickChoice(selector, variable, title, message, sorted, subselector, type);
    }

    public void fnSetVariable(String name, String value) throws IOException, InvalidVarNameException, InvalidParamException {
        Plugin.fnSetVariable(name, value);
    }

    @AfterAll
    public static void finish() {
        Plugin.getDriver().quit();
    }
}
