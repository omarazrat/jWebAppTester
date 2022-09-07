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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import lombok.Getter;
import oa.com.tests.actionrunners.enums.BROWSERTYPE;
import oa.com.tests.actionrunners.enums.PlaceMousePointerOffsetType;
import static oa.com.tests.actionrunners.enums.PlaceMousePointerOffsetType.FROM_UL_CORNER;
import oa.com.tests.actionrunners.exceptions.InvalidParamException;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.actionrunners.interfaces.PluginInterface;
import org.openqa.selenium.WebDriver;

/**
 * Definición de acción para implementación nativa.
 *
 * @author nesto
 */
public abstract class AbstractDefaultPluginRunner {
    private static final Logger log = Logger.getLogger("WebAppTester");

    /**
     * Utilice esta interfaz para hacer sus llamados a funciones ya definidas.
     */
    @Getter
    public static PluginInterface actionManager;

    /**
     * Constructora para plugins escritos en java, que no requieren dll's
     * Coloque sus inicializaciones aquì
     *
     * @param actionManager
     */
    public AbstractDefaultPluginRunner() {
    }

    // <editor-fold defaultstate="collapsed" desc="Required to implement methods">
    /**
     * Asegúrese de que este mensaje cabrá en el botón de 32 pixeles de altura
     * por máximo 190 px ancho
     *
     * @return
     */
    public abstract String getButtonActionCommand();

    /**
     * Este auditor de eventos se activará con el click del botón
     *
     * @return
     */
    public abstract ActionListener getActionListener();

    // </editor-fold>
    /**
     * Retorne aquì todas las librerìas que use su plugin
     *
     * @return
     */
    private Iterable<String> getLibraries() {
        return new LinkedList<>();
    }

    /**
     * Se utilizará en los mensajes de inicialización del plugin.
     *
     * @return
     */
    public String getName() {
        return getButtonActionCommand();
    }

    /**
     * Opcional, ícono para su botón (se redimensionará al tamaaño del botón!!)
     *
     * @return
     */
    public Icon getIcon() throws IOException {
        final BufferedImage img = ImageIO.read(AbstractDefaultPluginRunner.class.getResource("/icons/pluginIcon.png"));
        return new ImageIcon(img);
    }

    //Metodos delegados
    public void setActionManager(PluginInterface actionManager) {
        this.actionManager = actionManager;
    }

    public static List<Exception> exec(File file, Logger log) throws InvalidVarNameException, FileNotFoundException, IOException, InvalidParamException,Exception {
        return actionManager.exec(file, log);
    }

    public static String parse(String pwdString) throws InvalidVarNameException, InvalidParamException,Exception {
        return actionManager.parse(pwdString);
    }

    public static void run(String command) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        actionManager.run(command);
    }

    public static WebDriver getDriver() {
        return actionManager.getDriver();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // <editor-fold defaultstate="collapsed" desc="Shortcut functions">
    /**
     * Opens a web page.
     *
     * Example:
     *
     * go={https://duckduckgo.com/}
     *
     * @param url
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnGo(String url) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        actionManager.run("go={" + url + "}");
    }

    /**
     * Waits for a page to be completely loaded after a change in the location
     * bar URL, or after clicking any element. The browser will wait until a
     * given object (given its selector), appear or simply the page to be fully
     * loaded
     *
     * Params:
     *
     * selector : The selector of the object to wait for. Could be a css or
     * xpath selector type (Optional): The type of the selector to be used:
     * "css/xpath". If missing, css will be used
     *
     * Examples:
     *
     * wait={}
     *
     * wait={ "selector":"duckbar" }
     *
     * wait={ "selector":"//*[@id="search_form_input_homepage"]", "type":"xpath"
     * }
     *
     * @param selector
     * @param type
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnWait() throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnWait("body");
    }

    /**
     * Waits for a page to be completely loaded after a change in the location
     * bar URL, or after clicking any element. The browser will wait until a
     * given object (given its selector), appear or simply the page to be fully
     * loaded
     *
     * Params:
     *
     * selector : The selector of the object to wait for. Could be a css or
     * xpath selector type (Optional): The type of the selector to be used:
     * "css/xpath". If missing, css will be used
     *
     * Examples:
     *
     * wait={}
     *
     * wait={ "selector":"duckbar" }
     *
     * wait={ "selector":"//*[@id="search_form_input_homepage"]", "type":"xpath"
     * }
     *
     * @param selector
     * @param type
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnWait(String selector) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnWait(selector, "css");
    }

    /**
     * Waits for a page to be completely loaded after a change in the location
     * bar URL, or after clicking any element. The browser will wait until a
     * given object (given its selector), appear or simply the page to be fully
     * loaded
     *
     * Params:
     *
     * selector : The selector of the object to wait for. Could be a css or
     * xpath selector type (Optional): The type of the selector to be used:
     * "css/xpath". If missing, css will be used
     *
     * Examples:
     *
     * wait={}
     *
     * wait={ "selector":"duckbar" }
     *
     * wait={ "selector":"//*[@id="search_form_input_homepage"]", "type":"xpath"
     * }
     *
     * @param selector
     * @param type
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnWait(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        if (type == null || !type.equals("xpath")) {
            type = "css";
        }
        actionManager.run("wait={\"selector\":\"" + selector + "\",\"type\":\"" + type + "\"}");
    }

    /**
     * write - Writes text in a page component given its css selector.Params:
     *
     * selector: the css selector of the component to write on, If selector=="",
     * then the action is send to the whole page.e.g.: CTRL+P text: the text to
     * write type (Optional): The type of the selector to be used:
     * "css/xpath".If missing, css will be used
     *
     * Examples:
     *
     * write={ "selector":"search_form_input_homepage", "text":"inicio" }
     *
     * write={ "selector":"#search_form_input_homepage", "text":"inicio"
     * "type":"css" }
     *
     * You can specify special commands (ALT, F1,ENTER, ESCAPE, etc) using this
     * format:
     *
     * [%Keys.CONTROL]
     *
     * The full list of supported constants can be found in
     * https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/Keys.html
     * If you need to send command combinations, you can write using this
     * format:
     *
     * [%Keys.CONTROL,Keys.ALT]
     *
     * You can also add non-commands characters, like this:
     *
     * [%Keys.CONTROL,Keys.ALT]r
     *
     * Examples:
     *
     * write={ "selector":"search_form_input_homepage",
     * "text":"[%Keys.CONTROL,Keys.ALT]r" }
     *
     * write={ "selector":"", "text":"[%Keys.CONTROL]p" }
     *
     * If you need to write the text "[%", instead write "[%%"
     *
     *
     * @param selector
     * @param text
     * @throws java.io.IOException
     * @throws oa.com.tests.actionrunners.exceptions.InvalidVarNameException
     * @throws oa.com.tests.actionrunners.exceptions.InvalidParamException
     */
    public static void fnWrite(String selector, String text) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnWrite(selector, text, null);
    }

    /**
     * write - Writes text in a page component given its css selector.Params:
     *
     * selector: the css selector of the component to write on, If selector=="",
     * then the action is send to the whole page.e.g.: CTRL+P text: the text to
     * write type (Optional): The type of the selector to be used:
     * "css/xpath".If missing, css will be used
     *
     * Examples:
     *
     * write={ "selector":"search_form_input_homepage", "text":"inicio" }
     *
     * write={ "selector":"#search_form_input_homepage", "text":"inicio"
     * "type":"css" }
     *
     * You can specify special commands (ALT, F1,ENTER, ESCAPE, etc) using this
     * format:
     *
     * [%Keys.CONTROL]
     *
     * The full list of supported constants can be found in
     * https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/Keys.html
     * If you need to send command combinations, you can write using this
     * format:
     *
     * [%Keys.CONTROL,Keys.ALT]
     *
     * You can also add non-commands characters, like this:
     *
     * [%Keys.CONTROL,Keys.ALT]r
     *
     * Examples:
     *
     * write={ "selector":"search_form_input_homepage",
     * "text":"[%Keys.CONTROL,Keys.ALT]r" }
     *
     * write={ "selector":"", "text":"[%Keys.CONTROL]p" }
     *
     * If you need to write the text "[%", instead write "[%%"
     *
     *
     * @param selector
     * @param text
     * @param type
     * @throws java.io.IOException
     * @throws oa.com.tests.actionrunners.exceptions.InvalidParamException
     * @throws oa.com.tests.actionrunners.exceptions.InvalidVarNameException
     */
    public static void fnWrite(String selector, String text, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        if (type == null || !type.equals("xpath")) {
            type = "css";
        }
        actionManager.run("write={\"selector\":\"" + selector + "\"\n"
                + ", \"text\":\"" + text + "\"\n"
                + ",  \"type\":\"" + type + "\"}");
    }

    /**
     * click - clicks a component of the page given a css selector
     *
     * @param selector (optional): The selector of the object to click. Could be
     * a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the click event is
     * triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * click={ "selector":"search_button_homepage" }
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnClick() throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnClick(null, null);
    }

    /**
     * click - clicks a component of the page given a css selector
     *
     * @param selector (optional): The selector of the object to click. Could be
     * a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the click event is
     * triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * click={ "selector":"search_button_homepage" }
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnClick(selector, null);
    }

    /**
     * click - clicks a component of the page given a css selector
     *
     * @param selector (optional): The selector of the object to click. Could be
     * a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the click event is
     * triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * click={ "selector":"search_button_homepage" }
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        String mouseCommand = "click";
        runMouseCommand(mouseCommand, selector, type);
    }

    /**
     * Double-clicks any element in the page, given its selector.
     *
     * @param selector (optional): The selector of the object to double click.
     * Could be a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the double click
     * event is triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * double click={ "selector": "body" }
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnDoubleClick() throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnDoubleClick(null, null);
    }

    /**
     * Double-clicks any element in the page, given its selector.
     *
     * @param selector (optional): The selector of the object to double click.
     * Could be a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the double click
     * event is triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * double click={ "selector": "body" }
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnDoubleClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnDoubleClick(selector, null);
    }

    /**
     * Double-clicks any element in the page, given its selector.
     *
     * @param selector (optional): The selector of the object to double click.
     * Could be a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the double click
     * event is triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * double click={ "selector": "body" }
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnDoubleClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        String mouseCommand = "double click";
        runMouseCommand(mouseCommand, selector, type);
    }

    /**
     * Right clicks any element in the page, given its selector.
     *
     * @param selector (optional): The selector of the object to right click.
     * Could be a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the right click
     * event is triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * right click={ "selector": "Identificacion" }
     *
     * @param selector
     * @param type
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnRightClick() throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnRightClick(null);
    }

    /**
     * Right clicks any element in the page, given its selector.
     *
     * @param selector (optional): The selector of the object to right click.
     * Could be a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the right click
     * event is triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * right click={ "selector": "Identificacion" }
     *
     * @param selector
     * @param type
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnRightClick(String selector) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnRightClick(selector, null);
    }

    /**
     * Right clicks any element in the page, given its selector.
     *
     * @param selector (optional): The selector of the object to right click.
     * Could be a css or xpath selector
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used If no selector provided, the right click
     * event is triggered wherever the mouse pointer is located.
     *
     * Example:
     *
     * right click={ "selector": "Identificacion" }
     *
     * @param selector
     * @param type
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnRightClick(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        String mouseCommand = "right click";
        runMouseCommand(mouseCommand, selector, type);
    }

    /**
     * Scrolls the screen horizantally (x) or vertically (y) a given amount of
     * pixels.
     *
     * Examples:
     *
     * scroll={ "x":"0", "y":"100" }
     *
     * scroll={ "x":"10", "y":"-100" }
     *
     * scroll={ "x":"10", "y":"-100",
     * "selector":"//*[@id='main-content-inner']", "type": "xpath" }
     *
     * @param selector (optional): The selector of the object to scroll on.
     * Could be a css or xpath selector. If missing, the whole page will be
     * selected (body)
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used
     * @param x : pixels to scroll in horizontal axis (>0 right, <0 left) @
     * param y : pixels to scroll in vertical axis (>0 down, <0 up)
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnScroll(int x, int y) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnScroll(x, y, null);
    }

    /**
     * Scrolls the screen horizantally (x) or vertically (y) a given amount of
     * pixels.
     *
     * Examples:
     *
     * scroll={ "x":"0", "y":"100" }
     *
     * scroll={ "x":"10", "y":"-100" }
     *
     * scroll={ "x":"10", "y":"-100",
     * "selector":"//*[@id='main-content-inner']", "type": "xpath" }
     *
     * @param selector (optional): The selector of the object to scroll on.
     * Could be a css or xpath selector. If missing, the whole page will be
     * selected (body)
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used
     * @param x : pixels to scroll in horizontal axis (>0 right, <0 left) @
     * param y : pixels to scroll in vertical axis (>0 down, <0 up)
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnScroll(int x, int y, String selector) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnScroll(x, y, selector, null);
    }

    /**
     * Scrolls the screen horizantally (x) or vertically (y) a given amount of
     * pixels.
     *
     * Examples:
     *
     * scroll={ "x":"0", "y":"100" }
     *
     * scroll={ "x":"10", "y":"-100" }
     *
     * scroll={ "x":"10", "y":"-100",
     * "selector":"//*[@id='main-content-inner']", "type": "xpath" }
     *
     * @param selector (optional): The selector of the object to scroll on.
     * Could be a css or xpath selector. If missing, the whole page will be
     * selected (body)
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used
     * @param x : pixels to scroll in horizontal axis (>0 right, <0 left) @
     * param y : pixels to scroll in vertical axis (>0 down, <0 up)
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnScroll(int x, int y, String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        String command = "scroll={\"x\":\"" + x + "\",\"y\":\"" + y + "\"";
        if (selector != null) {
            if (type == null || type != "xpath") {
                type = "css";
            }
            command += ",\"selector\":\"" + selector + "\",\"type\":\"" + type + "\"";
        }
        command += "}";
        actionManager.run(command);
    }

    /**
     * Place the mouse pointer in screen
     *
     * Examples:
     *
     * place mouse pointer={ "offsetType":"FROM_UL_CORNER", "x":"150", "y":"350"
     * }
     *
     * place mouse pointer={ "offsetType":"FROM_CNTR_OBJECT", "x":"150",
     * "y":"350", "selector":"img:nth-of-type(2)" }
     *
     * @param offsetType (optional): One of these: FROM_UL_CORNER (default)- To
     * place the mouse pointer relative to the upper left corner
     * FROM_CUR_LOCATION - To place the mouse pointer from its current location
     * FROM_CNTR_OBJECT - To place the mouse pointer from the center of a given
     * object
     * @param x: the horizontal pixels to move the mouse pointer
     * @param y: the vertical pixels to move the mouse pointer
     * @param selector: the selector for the object (use only with
     * FROM_CNTR_OBJECT) -
     * @param type: Type of the selector for the object (default = css)
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnPlaceMousePointer(int x, int y) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnPlaceMousePointer(x, y, FROM_UL_CORNER);
    }

    /**
     * Place the mouse pointer in screen
     *
     * Examples:
     *
     * place mouse pointer={ "offsetType":"FROM_UL_CORNER", "x":"150", "y":"350"
     * }
     *
     * place mouse pointer={ "offsetType":"FROM_CNTR_OBJECT", "x":"150",
     * "y":"350", "selector":"img:nth-of-type(2)" }
     *
     * @param offsetType (optional): One of these: FROM_UL_CORNER (default)- To
     * place the mouse pointer relative to the upper left corner
     * FROM_CUR_LOCATION - To place the mouse pointer from its current location
     * FROM_CNTR_OBJECT - To place the mouse pointer from the center of a given
     * object
     * @param x: the horizontal pixels to move the mouse pointer
     * @param y: the vertical pixels to move the mouse pointer
     * @param selector: the selector for the object (use only with
     * FROM_CNTR_OBJECT) -
     * @param type: Type of the selector for the object (default = css)
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnPlaceMousePointer(x, y, offsetType, null);
    }

    /**
     * Place the mouse pointer in screen
     *
     * Examples:
     *
     * place mouse pointer={ "offsetType":"FROM_UL_CORNER", "x":"150", "y":"350"
     * }
     *
     * place mouse pointer={ "offsetType":"FROM_CNTR_OBJECT", "x":"150",
     * "y":"350", "selector":"img:nth-of-type(2)" }
     *
     * @param offsetType (optional): One of these: FROM_UL_CORNER (default)- To
     * place the mouse pointer relative to the upper left corner
     * FROM_CUR_LOCATION - To place the mouse pointer from its current location
     * FROM_CNTR_OBJECT - To place the mouse pointer from the center of a given
     * object
     * @param x: the horizontal pixels to move the mouse pointer
     * @param y: the vertical pixels to move the mouse pointer
     * @param selector: the selector for the object (use only with
     * FROM_CNTR_OBJECT) -
     * @param type: Type of the selector for the object (default = css)
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType, String selector) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnPlaceMousePointer(x, y, offsetType, selector, null);
    }

    /**
     * Place the mouse pointer in screen
     *
     * Examples:
     *
     * place mouse pointer={ "offsetType":"FROM_UL_CORNER", "x":"150", "y":"350"
     * }
     *
     * place mouse pointer={ "offsetType":"FROM_CNTR_OBJECT", "x":"150",
     * "y":"350", "selector":"img:nth-of-type(2)" }
     *
     * @param offsetType (optional): One of these: FROM_UL_CORNER (default)- To
     * place the mouse pointer relative to the upper left corner
     * FROM_CUR_LOCATION - To place the mouse pointer from its current location
     * FROM_CNTR_OBJECT - To place the mouse pointer from the center of a given
     * object
     * @param x: the horizontal pixels to move the mouse pointer
     * @param y: the vertical pixels to move the mouse pointer
     * @param selector: the selector for the object (use only with
     * FROM_CNTR_OBJECT) -
     * @param type: Type of the selector for the object (default = css)
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnPlaceMousePointer(int x, int y, PlaceMousePointerOffsetType offsetType, String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        if (offsetType == null) {
            offsetType = FROM_UL_CORNER;
        }
        String command = "place mouse pointer={\"offsetType\":\"" + offsetType + "\",\"x\":\"" + x + "\",\"y\":\"" + y + "\"";
        if (selector != null) {
            command += ",\"selector\":\"" + selector + "\"";
            if (type == null || type != "xpath") {
                type = "css";
            }
            command += ",\"type\":\"" + type + "\"";
        }
        command += "}";
        actionManager.run(command);
    }

    /**
     *
     * Waits a given number of seconds
     *
     * @param seconds
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnPause(int seconds) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnPause("" + seconds + " s");;
    }

    /**
     * Waits a given time, before proceeding with the next instruction.
     *
     *
     * Example:
     *
     * To wait three seconds
     *
     * pause={"time":"3 s"}
     *
     * To wait 10 minutes
     *
     * pause={"time":"10 m"}
     *
     * @param time: NUMBER UNITS, Where UNITS can be one of these values:
     * S=milliseconds,s=seconds, m=minutes, h=hours, d=days
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnPause(String time) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        actionManager.run("pause={\"time\":\"" + time + "\"}");
    }

    /**
     * Prompts user to pick a single choice among a list, stores selected
     *
     * option in a variable. Example:
     *
     * pick choice={ "selector":"div.central-featured-lang", "subselector": "a",
     * "sorted":"yes", "title":"Wikipedia", "message":"Select a language",
     * "variable":"Language" }
     *
     * @param selector: Required. This selector must match to a collection of
     * HTML elements
     * @param subselector: Secondary css path, to specify the element of every
     * option to be shown to the user. By default, the text of every element
     * gathered with the "selector" will be used as option.
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used
     * @param variable: Required. Name of the variable used to store user's
     * selection.
     * @param title: Title for the promtpt window
     * @param message: Message to use in the prompt window.
     * @param sorted: Sort options alphabetically? (yes/no)
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnPickChoice(String selector, String variable, String title, String message, boolean sorted, String subselector) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        fnPickChoice(selector, variable, title, message, sorted, subselector, null);
    }

    /**
     * Prompts user to pick a single choice among a list, stores selected
     *
     * option in a variable. Example:
     *
     * pick choice={ "selector":"div.central-featured-lang", "subselector": "a",
     * "sorted":"yes", "title":"Wikipedia", "message":"Select a language",
     * "variable":"Language" }
     *
     * @param selector: Required. This selector must match to a collection of
     * HTML elements
     * @param subselector: Secondary css path, to specify the element of every
     * option to be shown to the user. By default, the text of every element
     * gathered with the "selector" will be used as option.
     * @param type (optional): The type of the selector to be used: "css/xpath".
     * If missing, css will be used
     * @param variable: Required. Name of the variable used to store user's
     * selection.
     * @param title: Title for the promtpt window
     * @param message: Message to use in the prompt window.
     * @param sorted: Sort options alphabetically? (yes/no)
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnPickChoice(String selector, String variable, String title, String message, boolean sorted, String subselector, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        String ssorted = sorted?"yes":"no";
        String command = "pick choice={\"selector\":\"" + selector + "\" "
                + ",\"variable\":\"" + variable + "\",\"title\":\"" + title + "\""
                + ",\"message\":\"" + message + "\",\"sorted\":\"" + ssorted + "\"";
        if (subselector != null) {
            command += ",\"subselector\":\"" + subselector + "\"";
        }
        if (type == null || type != "xpath") {
            type = "css";
        }
        command += ",\"type\":\"" + type + "\"}";
        actionManager.run(command);
    }

    /**
     * A handy way to assign text to a name.
     *
     * Example:
     *
     * set={"name":"comment","value":"I like this"}
     *
     * @param name : Name of the variable. Allowed characters in the name:
     * numbers, letters and the signs _ -. e.g.: "MY_KEY","comment-02"
     * @param value : The text inside it
     *
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnSetVariable(String name, String value) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        actionManager.run("set={\"name\":\"" + name + "\",\"value\":\"" + value + "\"}");
    }

    /**
     * Use to change between these browsers:
     *
     * CHROME, EDGE, FIREFOX, INTERNET_EXPLORER, OPERA, SAFARI
     *
     * Example:
     *
     * browser={SAFARI}
     *
     * Note: When a new browser is set, the default page will be loaded and all
     * authentication data will be lost
     *
     * @param browser
     * @throws IOException
     * @throws InvalidVarNameException
     * @throws InvalidParamException
     */
    public static void fnSetBrowser(BROWSERTYPE browser) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        actionManager.run("browser={" + browser.name() + "}");
    }

    private static void runMouseCommand(String mouseCommand, String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException,Exception {
        String command = mouseCommand + "={ \n";
        if (selector != null) {
            command += "  \"selector\":\"" + selector + "\"\n";
            if (type != null) {
                if (type != "xpath") {
                    type = "css";
                }
                command += "  \"type\":\"" + type + "\"\n";
            }
        }
        command += "}";
        actionManager.run(command);
    }
    //</editor-fold>
}
