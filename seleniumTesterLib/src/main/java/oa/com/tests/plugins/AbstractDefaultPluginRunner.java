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
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import oa.com.tests.actionrunners.interfaces.ActionManagerInterface;

/**
 * Definición de acción para implementación nativa.
 *
 * @author nesto
 */
public abstract class AbstractDefaultPluginRunner{

    /**
     * Utilice esta interfaz para hacer sus llamados a funciones ya definidas.
     */
    protected ActionManagerInterface actionManager;

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
     * Asegúrese de que este mensaje cabrá en el botón de 32 pixeles de altura por máximo 190 px ancho
     * @return
     */
    public abstract String getButtonActionCommand();
    /**
     * Este auditor de eventos se activará con el click del botón
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

    public void setActionManager(ActionManagerInterface actionManager) {
        this.actionManager = actionManager;
    }

}
