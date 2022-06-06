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

import lombok.Data;
import oa.com.tests.Utils;
import oa.com.tests.actions.TestAction;

/**
 *
 * @author nesto
 */
@Data
public class PathKeeper {

    /**
     * Tipos de b�squedas soportadas
     */
    public enum SearchTypes {
        /**
         * Por omisi�n
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

    public PathKeeper(TestAction action) {
        String key = "CssSelectorActionRunner.attr.selector";
        final String actionCommand = action.getCommand();
        this.path = Utils.getJSONAttributeML(actionCommand, key);
        setRequired(true);
        if (path == null) {
            setRequired(false);
            return;
        }
        this.type = SearchTypes.CSS;
        key = "CssSelectorActionRunner.attr.type";
        String strType = Utils.getJSONAttributeML(actionCommand, key);
        if (strType != null) {
            this.type = SearchTypes.valueOf(strType.toUpperCase());
        }
    }

    public PathKeeper(String path, String type) {
        try {
            this.type = SearchTypes.valueOf(type);
        } catch (IllegalArgumentException | NullPointerException e) {
            this.type = SearchTypes.CSS;
        }
        this.path = path;
    }

    public PathKeeper(String path, SearchTypes type) {
        this.type = type;
        if (type == null) {
            this.type = SearchTypes.CSS;
        }
        this.path = path;
    }

    public boolean hasPath() {
        return path != null;
    }

}
