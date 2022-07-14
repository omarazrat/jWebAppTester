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

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

/**
 *
 * @author nesto
 */
public class JarClassLoader   extends URLClassLoader{
    static {
        registerAsParallelCapable();
    }

    public JarClassLoader(String name, ClassLoader parent) {
        super(name, new URL[0], parent);
    }

    /*
     * Required when this classloader is used as the system classloader
     */
    public JarClassLoader(ClassLoader parent) {
        this("classpath", parent);
    }

    public JarClassLoader() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public void add(URL url) {
        addURL(url);
    }

    public static JarClassLoader findAncestor(ClassLoader cl) {
        do {

            if (cl instanceof JarClassLoader)
                return (JarClassLoader) cl;

            cl = cl.getParent();
        } while (cl != null);

        return null;
    }

    /*
     *  Required for Java Agents when this classloader is used as the system classloader
     */
    @SuppressWarnings("unused")
    private void appendToClassPathForInstrumentation(String jarfile) throws IOException {
        add(Paths.get(jarfile).toRealPath().toUri().toURL());
    }
}
