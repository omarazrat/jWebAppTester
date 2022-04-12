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

import java.util.ResourceBundle;
import lombok.Data;
import oa.com.tests.Utils;
import oa.com.tests.actions.TestAction;

/**
 *
 * @author nesto
 */
@Data
public class PathFinder {
    
    /**
     * Tipos de búsquedas soportadas
     */
    public enum SearchTypes {
        /**
         * Por omisión
         */
        CSS,
        XPATH
    }

    private SearchTypes type;
    /**
     * El path usado.
     */
    private String path;

    private boolean required;

    public PathFinder(TestAction action) {
        String key = "CssSelectorActionRunner.attr.selector";
        final String actionCommand = action.getCommand();
        this.path = Utils.getJSONAttributeML(actionCommand, key);
        if (path == null && !isRequired()) {
            return;
        } 
        this.type = SearchTypes.CSS;
        key = "CssSelectorActionRunner.attr.type";
        String strType = Utils.getJSONAttributeML(actionCommand, key);
        if (strType != null) {
            this.type = SearchTypes.valueOf(strType.toUpperCase());
        }
    }

    public boolean hasPath(){
        return path!=null;
    }

}
