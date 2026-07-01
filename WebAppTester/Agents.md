I don't want my source code to be changed unless I explicitly accept or request to do so. When I ask for the solution to any problem, show me your suggestions and I'll be the one applying them.
When I ask my source code to be changed, follow these rules:

*RULES SUMMARY*

**PROGRAMMING STYLE:**:**

- between statements import... no empty lines
- inside javadoc blocks, no empty lines.
- inside functions, no empty lines.
- before class definition and before properties and funcions: 1 empty line
- after class definiiton: no empty lines
- Every supported command, and every supported parameter inside it must be defined in the properties file according to the language.
- Function names in english

**CHARACTER SET:**

  Always use the codification UTF-8 to avoid issues with spanish and chinese characters.
  My spanish and chinese characters won't be modified. e.g.:
- "Espa�a" is correct.
- The word "compa�ia" must NOT be transformed into "compa??a"

**COPYRIGHT HEADERS:**

Web application tester- Utility to test web applications via Selenium
Copyright (C) 2021-Nestor Arias

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

**COMPILATION COMMANDS**

always include package. e.g: 
- mvn -Dmaven.test.skip=true clean install
- mvn -Dmaven.test.skip=true package

**ARCHITECTURAL CONSIDERATIONS**

- Every modification must take into account to keep a low coupling. This is: this framework must be able to run with or without any of their plugins.
- ForActionRunner must capture, along with each WebElement, the text, css path and href values, and store them in the SelectorVariable. The SelectorVariable caches these three fields at creation time and then releases the WebElement reference (setValue(null)) to free memory. SetVariableActionRunner uses the parent variable's cachedCss — concatenated with the child selector via space — to build a compound CSS selector resolved by driver.findElement(), eliminating the need for a parent WebElement. resolveVarDef uses the cached text/css/href as fallback when the element is stale.
- FetchActionRunner supports `params` (JSON object appended as URL query string) and `byPath` paths with `included.{type}.{id|{ref}}.rest` syntax to access the JSON:API `included` array. `{ref}` is a sub-path resolved against the root JSON, enabling dynamic lookups like `included.eplan_catalog_entries.{data.relationships.product_group.data.id}.attributes.name`.