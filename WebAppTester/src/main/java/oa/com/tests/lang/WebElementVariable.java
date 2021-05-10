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
package oa.com.tests.lang;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.openqa.selenium.WebElement;

/**
 *
 * @author nesto
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class WebElementVariable extends Variable{
    /**
     * Ruta Css para llegar a este objeto
     */
    private String cssSelector;
    private WebElement value;

    public WebElementVariable(WebElement value,String cssSelector) {
        super(TYPE.WEB_ELEMENT);
        setValue(value);
        setCssSelector(cssSelector);
    }

    private void setCssSelector(String cssSelector) {
        this.cssSelector = cssSelector;
    }

    private void setValue(WebElement value) {
        this.value = value;
    }
}
