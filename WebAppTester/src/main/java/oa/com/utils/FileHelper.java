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
package oa.com.utils;

import java.io.File;

/**
 *
 * @author nesto
 */
public final class FileHelper {
    public static String getPathPrefix() {
        if(getCurDir().equals("/")){
            return System.getProperty("user.dir")+"/";
        }
        return "";
    }

    private static String getCurDir() {
        File f = new File(".");
        return f.getAbsolutePath();
    }
    
}
