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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author nesto
 */
public class I18n {
    private static final String defaultLang="en";
    public static final String []supportedLangs={defaultLang,"es"};
    /**
     * Agrega el código del lenguaje activo para la máquina virtual actual, al string
     * proporcionado como parámetro
     * @param str
     * @param preffix el prefijo para colocar antes del cód. lenguaje.
     * por omisión="_"
     * @return str+preffix+cur_lang
     */
    public static String appendLangCode(String str,String preffix){
        String langCode = Locale.getDefault().getLanguage();
        if(!Arrays.asList(supportedLangs).contains(langCode)){
            langCode=defaultLang;
        }
        return str+preffix+langCode;
    }
    /**
     * Agrega el código del lenguaje activo para la máquina virtual actual, al string
     * proporcionado como parámetro
     * @param str
     * @return str+"_"+cur_lang
     */
    public static String appendLangCode(String str){
        return appendLangCode(str,"_");
    }
    /**
     * Retorna todos los alias de una clave en el archivo de propiedades 
     * "application"
     * @param key
     * @return 
     */
    public static List<String> aliases(String key){
        return aliases("application",key);
    }
    /**
     * Retorna todos los alias de una clave en un archivo de propiedades
     * @param bundleName
     * @param key
     * @return 
     */
    public static List<String> aliases(String bundleName,String key){
        LinkedList<String> resp = new LinkedList<>();
        for (String langCode : I18n.supportedLangs) {
            try {
                final ResourceBundle bundle = ResourceBundle.getBundle(bundleName+"_" + langCode);
                if (bundle != null
                        && bundle.containsKey(key)) {
                    resp.add(bundle.getString(key));
                }
            } catch (Exception ex) {;
            }
        }
        return resp;
    }
}
