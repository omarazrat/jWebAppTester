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

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import oa.com.tests.actionrunners.exceptions.BadSyntaxException;
import oa.com.tests.actionrunners.interfaces.PathKeeper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author nesto
 */
public class WebUtils {

    public static String generateXPATH(WebElement childElement) {
        return generateXPATH(childElement, "");
    }

    private static String generateXPATH(WebElement childElement, String current) {
        String childTag = childElement.getTagName();
        if (childTag.equals("html")) {
            return "/html[1]" + current;
        }
        WebElement parentElement = childElement.findElement(By.xpath(".."));
        List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
        int count = 0;
        for (int i = 0; i < childrenElements.size(); i++) {
            WebElement childrenElement = childrenElements.get(i);
            String childrenElementTag = childrenElement.getTagName();
            if (childTag.equals(childrenElementTag)) {
                count++;
            }
            if (childElement.equals(childrenElement)) {
                return generateXPATH(parentElement, "/" + childTag + "[" + count + "]" + current);
            }
        }
        return null;
    }

    /*  public static String getXpath(WebDriver driver, WebElement selectedElem) {
        String function = "function createXPathFromElement(elm) { \n"
                + "    var allNodes = document.getElementsByTagName('*'); \n"
                + "    for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode) \n"
                + "    { \n"
                + "        if (elm.hasAttribute('id')) { \n"
                + "                var uniqueIdCount = 0; \n"
                + "                for (var n=0;n < allNodes.length;n++) { \n"
                + "                    if (allNodes[n].hasAttribute('id') && allNodes[n].id == elm.id) uniqueIdCount++; \n"
                + "                    if (uniqueIdCount > 1) break; \n"
                + "                }; \n"
                + "                if ( uniqueIdCount == 1) { \n"
                + "                    segs.unshift('id(\"' + elm.getAttribute('id') + '\")'); \n"
                + "                    return segs.join('/'); \n"
                + "                } else { \n"
                + "                    segs.unshift(elm.localName.toLowerCase() + '[@id=\"' + elm.getAttribute('id') + '\"]'); \n"
                + "                } \n"
                + "        } else if (elm.hasAttribute('class')) { \n"
                + "            segs.unshift(elm.localName.toLowerCase() + '[@class=\"' + elm.getAttribute('class') + '\"]'); \n"
                + "        } else { \n"
                + "            for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) { \n"
                + "                if (sib.localName == elm.localName)  i++; }; \n"
                + "                segs.unshift(elm.localName.toLowerCase() + '[' + i + ']'); \n"
                + "        }; \n"
                + "    }; \n"
                + "    return segs.length ? '/' + segs.join('/') : null; \n"
                + "}; \n"
                + "\n"
                + "function lookupElementByXPath(path) { \n"
                + "    var evaluator = new XPathEvaluator(); \n"
                + "    var result = evaluator.evaluate(path, document.documentElement, null,XPathResult.FIRST_ORDERED_NODE_TYPE, null); \n"
                + "    return  result.singleNodeValue; \n"
                + "} "
                + "return createXPathFromElement(arguments[0]);";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            final Object result = js.executeScript(function, selectedElem);
            return (String) result;
    }*/
    public static void waitToBeClickable(WebDriver driver, final WebElement elem) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(elem));
    }

    /**
     * Busca todos los elementos que coincidan con un patrón de búsqueda
     *
     * @param selector
     * @param driver
     * @return
     * @throws BadSyntaxException
     */
    public static List<WebElement> getMany(PathKeeper selector, WebDriver driver) throws BadSyntaxException {
        List<WebElement> resp = new LinkedList<>();
        if (selector.hasPath()) {
            final String path = selector.getPath();
            switch (selector.getType()) {
                case CSS:
                    resp = driver.findElements(By.cssSelector(path));
                    break;
                case XPATH:
                default:
                    resp = driver.findElements(By.xpath(path));
            }
        }
        return resp;
    }

    /**
     * Busca el Navo elemento después de un patrón de búsqueda.
     *
     * @param selector
     * @param driver
     * @param i
     * @return
     */
    public static WebElement getNthChild(PathKeeper selector, WebDriver driver, int i) {
        WebElement resp = null;
        try {
            if (selector.hasPath()) {
                final String path = selector.getPath();
                switch (selector.getType()) {
                    case CSS:
                        resp = driver.findElement(By.cssSelector(path + ":nth-child(" + i + ")"));
                        break;
                    case XPATH:
                    default:
                        resp = driver.findElement(By.xpath(path + "[" + i + "]"));
                }
            }
            return resp;
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }

    public static PathKeeper getNthPathKeeper(PathKeeper selector, int i) {
        final String path = selector.getPath();
        switch (selector.getType()) {
            case CSS:
                return new PathKeeper(path + ":nth-child(" + i + ")", PathKeeper.SearchTypes.CSS);
            case XPATH:
            default:
                return new PathKeeper(path + "[" + i + "]", PathKeeper.SearchTypes.XPATH);
        }
    }

    public static void preClick(final WebElement elem, WebDriver driver) {
        if (driver instanceof FirefoxDriver) {
            final Point point = elem.getLocation();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(arguments[0],arguments[1]);", point.x, point.y);
        }
    }
}
