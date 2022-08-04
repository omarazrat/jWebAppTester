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

    /**
     * Utilice esta interfaz para hacer sus llamados a funciones ya definidas.
     */
    protected static PluginInterface actionManager;

    /**
     * Constructora para plugins escritos en java, que no requieren dll's
     * Coloque sus inicializaciones aquì
     *
     * @param actionManager
     */
    public AbstractDefaultPluginRunner() {
        for (String library : getLibraries()) {
            System.loadLibrary(library);
        }
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

    public static List<Exception> exec(File file, Logger log) throws InvalidVarNameException, FileNotFoundException, IOException, InvalidParamException {
        return actionManager.exec(file, log);
    }

    public static String parse(String pwdString) throws InvalidVarNameException, InvalidParamException {
        return actionManager.parse(pwdString);
    }

    public static void run(String command) throws IOException, InvalidVarNameException, InvalidParamException {
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
    public static void fnGo(String url) throws IOException, InvalidVarNameException, InvalidParamException {
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
    public static void fnWait(String selector, String type) throws IOException, InvalidVarNameException, InvalidParamException {
        if(type==null || ! type.equals("xpath"))
            type = "css";
        actionManager.run("wait={\"selector\":\""+selector+"\",\"type\":\""+type+"\"}");
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
    public static void fnWait(String selector) throws IOException, InvalidVarNameException, InvalidParamException {
        fnWait(selector, "css");
    }
//    public static void fn""
    //</editor-fold>
}
