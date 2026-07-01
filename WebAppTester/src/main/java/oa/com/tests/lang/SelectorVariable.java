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

import lombok.Data;
import oa.com.tests.actionrunners.interfaces.PathKeeper;
import oa.com.utils.WebUtils;
import org.openqa.selenium.WebElement;

/**
 *
 * @author nesto
 */
@Data
public class SelectorVariable extends Variable{
    private PathKeeper finder;
    private WebElement value;
    private String cachedText;
    private String cachedCss;
    private String cachedHref;

    public SelectorVariable(WebElement value, String name, PathKeeper selector) {
        super(TYPE.WEB_SELECTOR);
        this.value = value;
        this.finder = selector;
        this.name = name;
        this.cachedText = value != null ? value.getText() : "";
        this.cachedCss = selector != null ? selector.getPath() : (value != null ? WebUtils.generateCSS(value) : "");
        this.cachedHref = value != null ? value.getAttribute("href") : "";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
    
}