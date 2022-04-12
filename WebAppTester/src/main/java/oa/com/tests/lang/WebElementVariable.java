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

import lombok.Getter;
import org.openqa.selenium.WebElement;

/**
 *
 * @author nesto
 */
@Getter
public class WebElementVariable 
        extends Variable{
    private final WebElement value;

    public WebElementVariable(WebElement value,String name) {
        super(TYPE.WEB_ELEMENT);
        this.name = name;
        this.value = value;
    }    
}
