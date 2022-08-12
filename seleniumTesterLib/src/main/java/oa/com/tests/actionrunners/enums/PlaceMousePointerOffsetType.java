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
package oa.com.tests.actionrunners.enums;

/**
 * Tipo de movimiento
 */
public enum PlaceMousePointerOffsetType {
    /**
     * Desde la esquina superior izquierda de la página
     */
    FROM_UL_CORNER, /**
     * Desde el centro de cierto objeto
     */ FROM_CNTR_OBJECT, /**
     * Desde la ubicación actual, cualquiera que sea.
     */ FROM_CUR_LOCATION
    
}
