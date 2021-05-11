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
package oa.com.tests.scriptactionrunners;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import oa.com.tests.Utils;
import oa.com.tests.actionrunners.exceptions.InvalidActionException;
import oa.com.tests.actionrunners.exceptions.NoActionSupportedException;
import oa.com.tests.actionrunners.exceptions.UserActionException;
import oa.com.tests.actionrunners.interfaces.AbstractCssSelectorActionRunner;
import oa.com.tests.actions.TestAction;
import oa.com.tests.lang.WebElementVariable;
import oa.com.utils.I18n;
import org.apache.commons.lang3.NotImplementedException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * seleccionar opcion
 * Dado un selector que apunte a varios elementos, pide al usuario seleccionar 
 * un elemento y lo deja en una variable.</br>
 * Elementos 
 * <ul>
 *  <ol><b>selector</b>: Obligatorio. El selector css que apunta a los elementos a mostrar</ol>
 *  <ol><b>subselector</b>: Opcional. Ruta complementaria, para determinar el valor de cada opciòn
 *  que se mostrará al usuario. Por omisiòn se tomará el texto de cada elemento
 *  web asociado con la ruta en <i>selector</i></ol>
 *  <ol><b>variable</b>: Obligatorio. Nombre de la variable que se va a asignar con el elemento que el usuario
 *  seleccione</ol>
 *  <ol><b>titulo</b>: Opcional. Título que tendrá la caja de texto que se va a mostrar al usuario.</ol>
 *  <ol><b>mensaje</b>: Opcional. Mensaje a mostrar en la caja de texto que se va a mostrar al usuario.</ol>
 *  <ol><b>orden alfabetico</b>: Opcional. ordenar alfabèticamente las opciones a mostrar (si/no)</ol>
 * </ul>
 * 
 * Ejemplos:
 * # En https://www.facebook.com/, historias:
 * seleccionar opcion={
 *      "selector": "div.g3eujd1d",
 *      "subselector": "> div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2)",
 *      "variable":"Mi historia",
 *      "mensaje":"Selecciona una historia para ver...",
 *      "titulo":"Historias del FB"
 * }
 * 
 * # En https://www.wikipedia.org/, lenguajes:
 * seleccionar opcion={
 *      "selector": "div.central-featured-lang",
 *      "subselector": " #js-link-box-es > strong:nth-child(1)",
 *      "variable": "lenguaje",
 *      "titulo": "wikipedia"
 * }
 * 
 * #Temas en 
 * seleccionar opcion={
 *      "selector":"yt-chip-cloud-chip-renderer.style-scope",
 *      "subselector":"yt-formatted-string:nth-child(1)",
 *      "variable":"yt tema",
 *      "mensaje":"selecciona un tema",
 *      "orden alfabetico":"si"
 * }
 * 
 * @author nesto
 */
public class PickChoiceActionRunner extends AbstractCssSelectorActionRunner{
    private List<WebElement> elements;
    private String subSelector;
    private boolean sorted;
    /**
     * Lo que el usuario seleccione.
     */
    private WebElementVariable variable;
    public PickChoiceActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        throw new NotImplementedException("Still under development");
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        final String clsName = getClass().getSimpleName();
        //Busca las opciones
        elements = driver.findElements(By.cssSelector(getSelector()));
        if(elements.isEmpty()){
            String message = bundle.getString(clsName+".err.selectorWOChlds")
                    .replace("{0}", getSelector());
            throw new InvalidActionException(message);
        }
        JSONParser parser = new JSONParser();
        JSONObject JSObj = (JSONObject) parser.parse(getAction().getCommand());
        subSelector = Utils.getJSONAttributeML(JSObj,clsName+".attr.subselector");
        String ssorted = Utils.getJSONAttributeML(JSObj,clsName+".attr.sorted");
        sorted = ssorted==null?false:ssorted.equals(bundle.getString("options.value.YES"));
        //Combo con opciones
        Stream<String> elementsStr = elements.stream()
                .map(webElementToString);
        if(sorted){
            elementsStr = elementsStr.sorted();
        }
        String title = Utils.getJSONAttributeML(JSObj,clsName+".attr.title");
        if(title==null){
            title = bundle.getString("options.title.default");
        }
        String msg = Utils.getJSONAttributeML(JSObj,clsName+".attr.msg");
        if(msg==null){
            msg = bundle.getString(clsName+".attr.msg.default");
        }
        String varName = Utils.getJSONAttributeML(JSObj,clsName+".attr.varName");
        int idx = promptUser(elementsStr.toList(), msg, title);
        String fullSelector = getSelector()+":nth-child("+idx+")"+subSelector;
        //Va por el elemento seleccionado.
        WebElement elem = driver.findElement(By.cssSelector(fullSelector));
        this.variable = new WebElementVariable(elem,varName,fullSelector);
    }

    private int promptUser(List<String> list, String msg, String title) throws UserActionException, HeadlessException {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        //Componente grafico
        JComboBox<String> opciones = new JComboBox<>(new Vector<>(list));
        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JTextArea taMsg = new JTextArea(msg, 3, 30);
        taMsg.setEditable(false);
        taMsg.setEnabled(false);
        content.add(taMsg,gbc);
        final int HEIGHT = 10;
        gbc.gridheight=HEIGHT;
        gbc.gridy++;
        content.add(opciones,gbc);
        int resp = JOptionPane.showConfirmDialog(null, content, title, JOptionPane.OK_CANCEL_OPTION);
        if(resp==JOptionPane.CANCEL_OPTION){
            String message = bundle.getString("options.user.cancelled");
            throw new UserActionException(message);
        }
        if(opciones.getSelectedIndex()==-1){
            String message = bundle.getString("PickChoiceActionRunner.option.required");
            throw new UserActionException(message);
        }
        int idx = opciones.getSelectedIndex()+1;
        return idx;
    }
    
    private Function<WebElement,String> webElementToString = (elem)->{
        WebElement optElement = elem.findElement(By.cssSelector(subSelector));
        return optElement.getText();
    };
}
