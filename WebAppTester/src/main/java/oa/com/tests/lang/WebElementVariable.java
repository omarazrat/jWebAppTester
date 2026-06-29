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

import lombok.*;
import oa.com.tests.actionrunners.interfaces.PathKeeper;
import org.openqa.selenium.WebElement;

@Getter
@Setter
@ToString(callSuper = true)
public class WebElementVariable extends Variable{
    @NonNull
    private PathKeeper path;
    @NonNull
    private String text;
    private String href;

    public WebElementVariable(String name, PathKeeper path,String text,WebElement value) {
        super(TYPE.WEB_ELEMENT,name,value);
        setPath(path);
        setText(text);
    }

    public WebElementVariable(String name, PathKeeper path,String text,String href,WebElement value) {
        super(TYPE.WEB_ELEMENT,name,value);
        setPath(path);
        setText(text);
        this.href = href;
    }
}
