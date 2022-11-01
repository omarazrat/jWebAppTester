/*
 * Selenium tester- library to be used for projects extending WebAppTester functionality
Copyright (C) 2021-Nestor Arias
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

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static oa.com.utils.FileHelper.getPathPrefix;

/**
 *
 * @author nesto
 */
public class PluginService<T>{

    private static PluginService service;
    private ServiceLoader<T> loader;
    //Donde cargar los plugins
    private final String FOLDER_NAME = getPathPrefix()+"lib";

    public PluginService(JarClassLoader clsLoader,Class<T> clazz) throws MalformedURLException {
        File f = new File(FOLDER_NAME);
        if(!f.exists()){
            f.mkdir();
        }
        final File[] files = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().endsWith(".jar");
            }
        });
        for (File file : files) {
            URL url = file.toURI().toURL();
            clsLoader.add(url);
        }
        loader = ServiceLoader.load(clazz,clsLoader);
    }

    public static synchronized PluginService getInstance(Class clazz) throws MalformedURLException {
        if (service == null) {
            service = new PluginService(new JarClassLoader(ClassLoader.getSystemClassLoader()),clazz);
        }
        return service;
    }

    public List<T> getPlugins() {
        Logger log = Logger.getLogger("WebAppTester");
        ResourceBundle globals = ResourceBundle.getBundle("application");
        try {
            log.info(globals.getString("PluginService.loadplugins"));
            final Iterator<T> iterator = loader.iterator();
            while (iterator.hasNext()) {
                log.log(Level.INFO, "-{0}", iterator.next().getClass().getCanonicalName());
            }
            return loader.stream().map(t -> t.get()).collect(Collectors.toList());
        } catch (Exception serviceError) {
            serviceError.printStackTrace();
        }
        return null;
    }

}
