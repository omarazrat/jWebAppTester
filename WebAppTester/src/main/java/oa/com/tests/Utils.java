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
package oa.com.tests;

import oa.com.tests.globals.ActionRunnerManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import javax.swing.tree.TreePath;
import oa.com.utils.I18n;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author nesto
 */
public abstract class Utils {

    private final static String separator = System.getProperty("file.separator");

    /**
     * Busca un atributo en un texto con formato JSON
     *
     * @param JSONtext
     * @param key
     * @return
     * @throws ParseException
     */
    public static String getJSONAttribute(String JSONtext, String key) 
            throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(JSONtext);
        return getJSONAttribute(obj, key);
    }
    /**
     * Busca un atributo en un objeto JSON
     * @param obj
     * @param key
     * @return
     * @throws ParseException 
     */
    @SuppressWarnings("unchecked")
    public static String getJSONAttribute(JSONObject obj, String key) 
            throws ParseException {
        String resp;
        resp = (String) obj.getOrDefault(key, null);
        if (resp == null) {
            throw new ParseException(0);
        }
        return resp;
    }

    /**
     * https://dzone.com/articles/get-all-classes-within-package Scans all
     * classes accessible from the context class loader which belong to the
     * given package and subpackages.
     *
     * @param <T>
     * @param packageName The base package
     * @param <error>
     * @param clazz
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T>
            Class<T>[]
            getClasses(String packageName, Class<T> clazz)
            throws ClassNotFoundException, IOException {
//        Logger log = getLogger();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        @SuppressWarnings("unchecked")
        List<File> dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
//            log.info("resource:"+resource);
//jar:file:/C:/Users/X512DK/Desktop/CodigoFuente/selenium-tester/target/WebAppTester-0.0.1-jar-with-dependencies.jar!/oa/com/tests/actionrunners
            final File file = new File(resource.getFile());
//            log.info("file:"+file.getPath()+"/"+file.exists());
            dirs.add(file);
        }
        ArrayList<Class<T>> classes = new ArrayList<>();
        for (File directory : dirs) {
            final String JAR_EXT = ".jar";
            final String dirPath = directory.getPath();
            if (dirPath.contains(JAR_EXT + "!")) {
                final String JARSTART = "file:" + separator;
                boolean unix=separator.equals("/");
                String jarPath = dirPath.substring(0, dirPath.indexOf(JAR_EXT) + JAR_EXT.length());
                //Le vuela el "file:\"
//System.out.println("jp: : "+jarPath+" // "+JARSTART);
                jarPath = (unix?separator:"")+jarPath.substring(jarPath.indexOf(JARSTART) + JARSTART.length())
                        .replace("%20", " ");
//System.out.println("jp: : "+jarPath);
                classes.addAll(findClasesFromJar(new JarFile(jarPath), packageName, clazz));
            }
            classes.addAll(findClasses(directory, packageName, clazz));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * https://dzone.com/articles/get-all-classes-within-package Recursive
     * method used to find all classes in a given directory and subdirs.
     *
     * @param directory The base directory
     * @param packageName The package name for classes found inside the base
     * directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    private static <T> List<Class<T>>
            findClasses(File directory, String packageName, Class<T> clazz) throws ClassNotFoundException {
//        Logger log = getLogger();
        @SuppressWarnings("unchecked")
        List<Class<T>> classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
//                assert !file.getName().contains(".");
//                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                final Class<?> objClazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                if (clazz.isAssignableFrom(objClazz)) {
                    classes.add((Class<T>)objClazz);
                }
            }
        }
        return classes;
    }

    /**
     * Crea una carpeta con el formato de un ejecutor (ActionRunner) PRE:
     * folderName no existe
     *
     * @param folderName
     */
    public static void buildDefaultActionRunnerFolder(String folderName) throws IOException {
        File folder = new File(folderName);
        folder.mkdir();
        buildDefaultFiles(folder);
    }

    /**
     * Crea un archivo main.txt, con unas acciones por omision, como ejemplo.
     *
     * @param parent
     */
    public static void buildDefaultFiles(File parent) throws IOException {
        String fname = "_start";
        final String EXT = ".txt";
        final String CITYPATH="templates/cities/";
        buildFileFromTemplate(parent, fname+EXT,"templates/"+I18n.appendLangCode(fname)+EXT);
        fname = "_end";
        buildFileFromTemplate(parent, fname+EXT,"templates/"+I18n.appendLangCode(fname)+EXT);
        File europe = new File(parent, "Europa");
        europe.mkdir();
        fname = "constantinopla";
        buildFileFromTemplate(europe, fname+EXT,CITYPATH+I18n.appendLangCode(fname)+EXT);
        fname = "amsterdam";
        buildFileFromTemplate(europe, fname+EXT,CITYPATH+I18n.appendLangCode(fname)+EXT);
        File afrika = new File(parent, "Africa");
        afrika.mkdir();
        fname = "uagadugu";
        buildFileFromTemplate(afrika, fname+EXT,CITYPATH+I18n.appendLangCode(fname)+EXT);
        File asia = new File(parent, "Asia");
        asia.mkdir();
        fname = "pyongyang";
        buildFileFromTemplate(asia, fname+EXT,CITYPATH+I18n.appendLangCode(fname)+EXT);
    }

    public static void buildFileFromTemplate(File parent, String destFileName) throws IOException {
        buildFileFromTemplate(parent, destFileName, "templates/"+destFileName);
    }
    public static void buildFileFromTemplate(File parent
            , String destFileName
            , String sourceFullFilePath) throws IOException {
        File init = new File(parent, destFileName);
        if (!init.exists()) {
            init.createNewFile();
        }
        final FileWriter fileWriter = new FileWriter(init);
        final String notes=I18n.appendLangCode("notas");
        for (String source : new String[]{sourceFullFilePath, "templates/"+notes+".txt"}) {
            appendToWriter(source, fileWriter);
        }
        fileWriter.close();
    }

    /**
     * Agrega el contenido de una plantilla a un flujo de datos de escritura.
     *
     * @param templateSource
     * @param writer
     * @throws IOException
     */
    public static void appendToWriter(String templateSource, final Writer writer) throws IOException {
        appendToWriter(templateSource, writer, new HashMap<>());
    }

    /**
     * Agrega el contenido de una plantilla a un flujo de datos de escritura.
     *
     * @param templateSource
     * @param writer
     * @param map listado de parejas clave-valor, a ser reemplazadas en el
     * archivo que se va a leer.
     * @throws IOException
     */
    public static void appendToWriter(String templateSource, final Writer writer,
             HashMap<String, String> map) throws IOException {
        final URL indexResource = ClassLoader.getSystemResource(templateSource);
        Reader indexReader = new InputStreamReader(indexResource.openStream());
        if (!map.isEmpty()) {
            StringWriter swriter = new StringWriter();
            IOUtils.copy(indexReader, swriter);
            String buffer = swriter.getBuffer().toString();
            for (String key : map.keySet()) {
                final String value = map.get(key);
                buffer = buffer.replace(key, value);
            }
            indexReader = new StringReader(buffer);
        }
        IOUtils.copy(indexReader, writer);
        indexReader.close();
    }

    /**
     * Saca una ruta completa de archivo a partir de un nodo del arbol de
     * archivos.
     *
     * @param item
     * @return
     */
    public static File getFile(TreePath item) {
        String filePath = Arrays.asList(item.getPath())
                .stream()
                .map(Object::toString)
                .collect(joining(separator));
        return new File(filePath);
    }

    /**
     * Saca una lista de todos los archivos que hay que ejecutar en orden para
     * un nodo del arbol.
     *
     * @param item
     * @return
     */
    public static Queue<File> getRunnableFiles(TreePath item) {
        List<String> filePath = Arrays.asList(item.getPath())
                .stream()
                .map(Object::toString)
                .collect(toList());
        File itemFile = getFile(item);
        Queue<File> resp = new ArrayDeque<>();
        final TreePath parent = item.getParentPath();
        if (parent != null) {
            resp.addAll(getRunnableFiles(parent, ActionRunnerManager.ACTIONTYPE.START));
        }
        resp.add(itemFile);
        if (parent != null) {
            resp.addAll(getRunnableFiles(parent, ActionRunnerManager.ACTIONTYPE.END));
        }
        return resp;
    }

    private static Queue<File> getRunnableFiles(TreePath item, ActionRunnerManager.ACTIONTYPE type) {
        final TreePath parent = item.getParentPath();
        Queue<File> resp = new ArrayDeque<>();
        final Queue<File> parentRunnableFiles
                = parent == null ? new ArrayDeque<>()
                        : getRunnableFiles(parent, type);

        final File file = getFile(item);
        if (file.isDirectory()) {
            File child = null;
            switch (type) {
                case START:
                    resp.addAll(parentRunnableFiles);
                    child = new File(file, "_start.txt");
                    if (child.exists()) {
                        resp.add(child);
                    }
                    break;
                case END:
                default:
                    child = new File(file, "_end.txt");
                    if (child.exists()) {
                        resp.add(child);
                    }
                    resp.addAll(parentRunnableFiles);
                    break;
            }
        }
        return resp;
    }

    /**
     * Determina si encontramos una expresion JSON valida o no.
     *
     * @param actionCommand
     * @return
     */
    public static boolean validJSON(String actionCommand) {
        try {
            new JSONParser().parse(actionCommand);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     *
     * @param file
     * @param packageName
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T> Collection<Class<T>> findClasesFromJar(JarFile file, String packageName, Class<T> clazz) {
//        Logger log = getLogger();
        Collection<Class<T>> resp = new LinkedList<>();
        Enumeration<JarEntry> entries = file.entries();
        for (JarEntry entry = entries.nextElement();
                entries.hasMoreElements();
                entry = entries.nextElement()) {
            final String entryStr = entry.toString();
            if (!entryStr.startsWith(packageName.replace(".", "/"))) {
                continue;
            }
//            log.info("Revisando entrada "+entryStr);
            if (entryStr.endsWith(".class")) {
                try {
                    final String classPath = entryStr.replace("/", ".").replace(".class", "");
                    Class objclazz = Class.forName(classPath);
//                    log.info("cls:"+clazz);
                    if (objclazz.getPackage().getName().equals(packageName)
                            && clazz.isAssignableFrom(objclazz)) {
                        resp.add((Class<T>) objclazz);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger("WebAppTester").log(Level.SEVERE, null, ex);
                }
            }
        }
        return resp;
    }

    public static Logger getLogger() {
        Logger log = Logger.getLogger("WebAppTester");
        return log;
    }

    /**
     * Busca una representacion como texto no muy extensa, para un error.
     *
     * @param e
     * @return
     */
    public static String getFirstLine(Exception e) {
        Throwable t = e;
        String resp = t.getMessage();
        while (t != null && resp.trim().isEmpty()) {
            t = t.getCause();
            resp = t.getMessage();
        }
        return resp;
    }

    /**
     * Busca un atributo JSON a traves de todos los lenguajes disponibles
     * @param key La clave por la cual buscar
     * @param obj Objeto en el cual buscar la entrada.
     * @return el valor del atributo o null, si no encontrado.
     */
    public static String getJSONAttributeML(JSONObject obj,String key) {
        for (String attrAlias : I18n.aliases(key)) {
            try {
                return Utils.getJSONAttribute(obj,attrAlias);
            } catch (ParseException ex) {
                ;
            }
        }
        return null;
    }

    /**
     * Busca un atributo JSON a traves de todos los lenguajes disponibles
     * @param key La clave por la cual buscar
     * @param JSONtext Texto en el cual buscar la entrada.
     * @return el valor del atributo o null, si no encontrado.
     */
    public static String getJSONAttributeML(String JSONtext,String key) {
        for (String attrAlias : I18n.aliases(key)) {
            try {
                return Utils.getJSONAttribute(JSONtext,attrAlias);
            } catch (ParseException ex) {
                ;
            }
        }
        return null;
    }
}
