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
package oa.com.tests.globals;

import oa.com.tests.Utils;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import oa.com.tests.actions.TestAction;
import oa.com.tests.webapptester.MainApp;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import oa.com.tests.actionrunners.interfaces.ScriptActionRunner;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oa.com.tests.actionrunners.exceptions.InvalidVarNameException;
import oa.com.tests.actionrunners.interfaces.VariableProvider;
import oa.com.tests.lang.Variable;
import oa.com.tests.lang.WebElementVariable;
import org.openqa.selenium.Keys;

/**
 * Gestor de acciones.
 *
 * @author nesto
 */
@Data
public final class ActionRunnerManager {

    public enum BROWSERTYPE {
        CHROME,
        EDGE,
        FIREFOX,
        INTERNET_EXPLORER,
        OPERA,
        SAFARI
    }

    public enum ACTIONTYPE {
        START,
        END
    }

    private static ResourceBundle globals = ResourceBundle.getBundle("application");

    /**
     * Listado con todas las clases de funciones registradas.
     */
    private static List<Class<? extends ScriptActionRunner>> runnersCls = null;

    static {
        try {
            runnersCls = findRunnersCls();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActionRunnerManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ActionRunnerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Listado con todas las funciones registradas.
     */
    private TreeModel rootTree;
    private BROWSERTYPE browserType;
    private WebDriver driver;
    private List<Variable> variables = new LinkedList();
    private static ActionRunnerManager instance = new ActionRunnerManager();
    final private static String sqOpen = Pattern.quote("[");
    final private static String sqClose = Pattern.quote("]");

    private ActionRunnerManager() {
        //Clases
        try {
            //Carpetas
            rootTree = asTree();
            //Acciones
        } //TODO: Mandar estas excepciones a UI
        catch (Exception ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        }
    }

    public static void quit() {
        if (instance.driver != null) {
            instance.driver.quit();
        }
    }

    private AbstractDefaultScriptActionRunner findRunner(String actionCommand) throws BadSyntaxException {
        TestAction tester;
        tester = new TestAction(actionCommand);
        AbstractDefaultScriptActionRunner runner = null;
        for (Class runnerCls : ActionRunnerManager.runnersCls) {
            try {
                final Constructor constructor = runnerCls.getConstructor(TestAction.class);
                runner = (AbstractDefaultScriptActionRunner) constructor.newInstance(tester);
                break;
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                //Runner incorrecto, va con otro.
            }
        }
        return runner;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (driver != null) {
            driver.quit();
        }
    }

    public static TreeModel getTreeModel() {
        try {
            instance.rootTree = asTree();
        } catch (IOException ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        }
        return instance.getRootTree();
    }

    /**
     * Establece el tipo de navegador a utilizar PRE: btype != null
     *
     * @param btype
     */
    public static void set(BROWSERTYPE btype) {
        final BROWSERTYPE instanceBrowserType = instance.getBrowserType();
        if (instanceBrowserType != null) {
            if (instanceBrowserType == btype) {
                return;
            }
        }
        instance.setBrowserType(btype);
        final WebDriver instance_driver = instance.getDriver();
        if (instance_driver != null) {
            instance_driver.quit();
        }
        WebDriver driver = null;
        try {
            switch (btype) {
                case CHROME:
                    driver = new ChromeDriver();
                    break;
                case EDGE:
                    driver = new EdgeDriver();
                    break;
                case FIREFOX:
                    driver = new FirefoxDriver();
                    break;
                case INTERNET_EXPLORER:
                    driver = new InternetExplorerDriver();
                    break;
                case OPERA:
                    driver = new OperaDriver();
                    break;
                case SAFARI:
                default:
                    driver = new SafariDriver();
                    break;
            }
        } catch (Exception e) {
            String message = globals.getString("settings.driver.createException")
                    .replace("{0}", btype.name());
            JOptionPane.showMessageDialog(null, message,
                    globals.getString("globals.error.title"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        instance.setDriver(driver);
    }

    /**
     * Ejecuta una opcion del arbol
     *
     * @param item
     */
    public static void exec(TreePath item, Logger log) throws IOException, InvalidVarNameException {
        File file = Utils.getFile(item);
        final String ERR_TITLE = globals.getString("globals.error.title");
        if (file.isDirectory()) {
            JOptionPane.showMessageDialog(MainApp.getInstance(), globals.getString("exec.err.isFolder"), ERR_TITLE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Exception> exceptions = new LinkedList<>();
        for (File f : Utils.getRunnableFiles(item)) {
            exceptions.addAll(instance.exec(f, log));
        }
        //Errores de la ejecucion
        if (!exceptions.isEmpty()) {
            MainApp.addException(exceptions.toArray(new Exception[]{}));
        }
    }

    /**
     * Busca en un directorio y retorna un arbol dependiendo de lo que encuentre
     * alli.
     *
     * @param folderName
     * @return
     */
    private static TreeModel asTree() throws IOException {
        ResourceBundle globals = ResourceBundle.getBundle("application");
        final String FOLDER_NAME = globals.getString("globals.actionfolder.name");
        File rootFile = new File(FOLDER_NAME);
        if (!rootFile.exists()) {
            final String message = globals.getString("globals.folder.read.errNoFolder");
            final String title = globals.getString("globals.error.title");
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
            Utils.buildDefaultActionRunnerFolder(FOLDER_NAME);
            rootFile = new File(FOLDER_NAME);
        }
        final TreeModel model = new DefaultTreeModel(fileAsTree(rootFile));
        return model;
    }

    private static MutableTreeNode fileAsTree(File file) {
        File[] children = file.listFiles((pfile, ext)
                -> pfile.isDirectory() || ext.toLowerCase().trim().endsWith(".txt")
        );
        if (file.isDirectory() && children.length == 0) {
            return null;
        }
        if (children != null) {
            Arrays.sort(children, (f1, f2) -> f1.getName().compareTo(f2.getName()));
        }
        final DefaultMutableTreeNode resp = new DefaultMutableTreeNode(file.getName());
        final String[] keyNamesArr = new String[]{"_end.txt", "_start.txt"};
        try {
            for (MutableTreeNode node : Arrays.asList(children)
                    .stream()
                    //Se aceptan directorios o archivos diferentes a _start.txt,_end.txt
                    .filter(f -> f.isDirectory()
                    || Arrays.binarySearch(keyNamesArr, f.getName()) < 0)
                    .map(f -> fileAsTree(f))
                    .filter(n -> n != null)
                    .collect(toList())) {
                resp.add(node);
            }
        } catch (NullPointerException npe) {
            ;
        }
        return resp;
    }

    private static List<Class<? extends ScriptActionRunner>> findRunnersCls() throws ClassNotFoundException, IOException {
        final Class<? extends ScriptActionRunner>[] classes = Utils.getClasses("oa.com.tests.scriptactionrunners", ScriptActionRunner.class);
        List<Class<? extends ScriptActionRunner>> resp
                = Arrays.asList(classes);
        return resp;
    }

    /**
     * Ejecuta las instrucciones en un archivo.
     *
     * @param file
     * @param log
     */
    private List<Exception> exec(File file, Logger log) throws IOException, InvalidVarNameException {
//        final List<String> filteredLines = Files.lines(FileSystems.getDefault().getPath(file.getAbsolutePath()))
//                .map(String::trim)
//                .filter(l -> !l.isEmpty() && !l.startsWith("#"))
//                .collect(toList());
        List<Exception> resp = new LinkedList<>();
        final List<String> filteredLines = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            filteredLines.add(line);
        };
        reader.close();

        List<String> command = new LinkedList<>();
        int lineCounter = 0;
        try {
            while (lineCounter < filteredLines.size()) {
                boolean correct = true;
                command.add(filteredLines.get(lineCounter++));
                final String actionCommand = parse(
                        command.stream().collect(joining(" "))
                );
                correct = !command.isEmpty();
                if (correct) {
                    AbstractDefaultScriptActionRunner runner;
                    try {
                        runner = findRunner(actionCommand);
                    } catch (BadSyntaxException ex) {
                        continue;
                    }
                    if (runner == null) {
                        String message = globals.getString("exec.err.noSuchRunnerException")
                                .replace("{0}", actionCommand)
                                .replace("{1}", file.getAbsolutePath());
                        resp.add(new NoActionSupportedException(message));
//                    JOptionPane.showMessageDialog(null, message,
//                            globals.getString("globals.error.title"), JOptionPane.ERROR_MESSAGE);
                        log.severe(message);
                        command.clear();
                        continue;
                    }

                    try {
                        runner.run(instance.getDriver(), log);
                        if (runner instanceof VariableProvider) {
                            VariableProvider varprovider = (VariableProvider) runner;
                            WebElementVariable variable = varprovider.getVariable();
                            if (variables.contains(variable)) {
                                variables.remove(variable);
                            }
                            variables.add(variable);
                        }
                    } catch (Exception ex) {
                        BadSyntaxException badSyntaxException = new BadSyntaxException(prepareBadSystaxExMsg(actionCommand, file));
                        log.log(Level.SEVERE, actionCommand, ex);
                        resp.add(badSyntaxException);
                    } finally {
                        command.clear();
                    }
                }
            }
            if (!command.isEmpty()) {
                throwBadSynstaxEx(command.stream().collect(joining(" ")), file, log);
            }
        } finally {
            variables.clear();
        }
        return resp;
    }

    private static void throwBadSynstaxEx(final String actionCommand, File file, Logger log) throws HeadlessException {
        String badSyntazMsg = prepareBadSystaxExMsg(actionCommand, file);
//        JOptionPane.showMessageDialog(null, badSyntazMsg,globals.getString("globals.error.title"), JOptionPane.ERROR_MESSAGE);
        log.severe(badSyntazMsg);
    }

    public static String prepareBadSystaxExMsg(final String actionCommand, File file) {
        String badSyntazMsg = globals.getString("exec.err.syntaxException")
                .replace("{0}", actionCommand)
                .replace("{1}", file.getAbsolutePath());
        return badSyntazMsg;
    }

    /**
     * @fixme Corrige el comando de consulta, buscando y reemplazando las
     * variables correspondientes
     * @param actionCommand
     * @return
     */
    public static String parse(String actionCommand) throws InvalidVarNameException {
        String resp = actionCommand;
        //Variables [:variable]
        resp = parseVariables(resp);
        resp = resp.replace("[::", "¨{:");
        //Teclas especiales.[%keys]
        resp = parseKeys(resp);
        resp = resp.replace("[%%", "¨{%");
        return resp;
    }
    /**
     * Helper for {@link #parse(java.lang.String)}
     * @param resp
     * @param sqOpen
     * @param sqClose
     * @return 
     */
    public static String parseKeys(String resp){
        final String SEPARATOR = ",";
        String regexp = "("+sqOpen + "%[Keys\\.[0-9|a-z|A-Z]*\\"+(SEPARATOR)+"?]*" + sqClose+")";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(resp);
        if(!matcher.find()){
            return resp;
        }
        
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String original = matcher.group(i);
            String varDef = original.substring(0, original.length() - 1)
                .substring(2);
            String[] varDefs = varDef.contains(SEPARATOR)?
                    varDef.split(SEPARATOR):
                    new String[]{varDef};
            final List<CharSequence> varAsKeys = Arrays.asList(varDefs)
                    .stream()
                    .map(token->Keys.valueOf(token.replace("Keys.", "")))
                    .collect(toList());
            String command = Keys.chord(varAsKeys);
            resp = resp.replace(original, command);
        }
        return resp;
    }
    
    /**
     * Helper for {@link #parse(java.lang.String) }
     * @param actionCommand
     * @param sqOpen
     * @param sqClose
     * @return
     * @throws InvalidVarNameException 
     */
    private static String parseVariables(String actionCommand) 
            throws InvalidVarNameException{
        String regexp = "(" + sqOpen + ":[0-9|a-z|A-Z]*" + sqClose + ")";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(actionCommand);

        if (!matcher.find()) {
            return actionCommand;
        }

        String resp = actionCommand;
        
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String varDef = matcher.group(i);
            resp = resp.replace(varDef, resolveSelector4VarDef(varDef));
        }
        return resp;
    }

    /**
     * Retorna el selector para una variable que está en un texto con su nombre.
     *
     * @param varDef El texto con el nombre de la variable en el formato
     * [:NOMBRE_VARIABLE]
     * @return
     */
    public static String resolveSelector4VarDef(String varDef) throws InvalidVarNameException {
        final String varName = varDef.substring(0, varDef.length() - 1)
                .substring(2);
        if (varName.isEmpty()) {
            throw new InvalidVarNameException("variable with name " + varName);
        }
        Optional<Variable> varMatch = instance.variables.stream()
                .filter(var -> var.getName().equals(varName))
                .findAny();
        if (varMatch.isEmpty()) {
            throw new InvalidVarNameException(varName);
        }
        WebElementVariable var = (WebElementVariable) varMatch.get();
        return var.getCssSelector();
    }
}
