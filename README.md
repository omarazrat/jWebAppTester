**jWebAppTester**

## English

WebAppTester: Web form tester using Selenium.

### General conventions

Every instruction has the format `command={...}`, one per line. Lines starting with `#` are comments. The content between braces is usually a JSON object; the only exceptions are `go` and `browser`, which take plain text.

Command names **and** parameter names are localized: the same command has a different name per language (`go` / `ir` / `访问`). Every name is accepted in any language, so the three languages can be mixed in the same script. The examples below use the English names.

Valid for almost every command:

- `"tab":"id"` — optional. Runs the command on the tab registered with that `id`, without changing the global context. Not available on `go` and `browser` (see the tabs annex).
- `[:variable]` — replaced with the value of the variable before the command is executed (see *Defining and using variables*).
- `[%Keys.ENTER]` — sends special keys. `[%Keys.CONTROL,Keys.ALT]r` sends key combinations. Write `[%%` to type the literal text `[%`.
- `[$ENCRYPTED]` — replaced with the decrypted password (button *Crypt password*). Write `[$$` to type the literal text `[$`.

### 1. Command go

Navigates to a URL. The URL is written **directly** between the braces: it is not JSON and must not be quoted.

```
go={https://www.wikipedia.org/}
```

The whole content is used as the URL, so variables and keys also work here:

```
set={"name":"site","value":"https://www.wikipedia.org/"}
go={[:site]}
```

### 2. Command click

Clicks an element: moves the mouse pointer over it and clicks (`Actions.moveToElement().click()`).

| Parameter | Required | Description |
|---|---|---|
| `selector` | optional | CSS or XPath selector of the element. **If omitted, the click is triggered wherever the mouse pointer currently is.** |
| `type` | optional | `css` (default) or `xpath` |

```
click={"selector":"button[type='submit']"}
```

```
click={"selector":"//*[@id='search_form_input_homepage']","type":"xpath"}
```

```
click={}
```

### 3. Command double click

Double-clicks an element (`Actions.moveToElement().doubleClick()`). Same parameters as `click`: `selector` (optional, without it the double click happens at the current pointer position) and `type` (optional).

```
double click={"selector":"#resultList li:first-child"}
```

```
double click={"selector":"/html/body","type":"xpath"}
```

### 4. Command right click

Right-clicks an element (`Actions.moveToElement().contextClick()`), opening its context menu. Same parameters as `click`: `selector` (optional) and `type` (optional).

```
right click={"selector":"#menuContainer"}
```

### 5. Command pause

Waits a given amount of time before proceeding with the next instruction.

| Parameter | Required | Description |
|---|---|---|
| `time` | yes | A number followed by a unit, e.g. `"2 s"` |

Units: `S` = milliseconds, `s` = seconds, `m` = minutes, `h` = hours, `d` = days.

```
pause={"time":"500 S"}
pause={"time":"3 s"}
pause={"time":"10 m"}
pause={"time":"1 h"}
```

### 6. Command pick choice

Shows a dialog with the elements matched by the selector and asks the user to pick one. The picked element is stored in a variable.

| Parameter | Required | Description |
|---|---|---|
| `selector` | yes | Selector matching the collection of elements to offer. |
| `subselector` | yes | Path used **inside** each matched element to get the text of each option. If omitted, the command fails with *No subselector provided!* |
| `variable` | yes | Name of the variable that stores the selected element. |
| `title` | optional | Dialog title. Default: `Web Tester`. |
| `message` | optional | Message shown inside the dialog. Default: *To continue, please pick a choice:* |
| `sorted` | optional | `yes` / `no` — sorts the options alphabetically. |
| `type` | optional | Applies to both `selector` and `subselector`. `css` (default) or `xpath`. |

```
pick choice={
 "selector":"div.central-featured-lang",
 "subselector":"a",
 "sorted":"yes",
 "title":"Wikipedia",
 "message":"Select a language",
 "variable":"Language"
}
```

The resulting variable behaves like a selector variable:

```
click={"selector":"[:Language] > a"}
```

Errors: the selector matches nothing → *element for selector {0} has no child elements*; the user cancels → the run is aborted; no option is picked → the run is aborted.

### 7. Command write

Types text into the element found by the selector (`WebElement.sendKeys`).

| Parameter | Required | Description |
|---|---|---|
| `selector` | yes | CSS or XPath selector of the input control. |
| `type` | optional | `css` (default) or `xpath`. |
| `text` | yes | Text to type. Supports variables, special keys and encrypted passwords. |

```
write={"selector":"input[type='search']","text":"inicio"}
```

```
write={"selector":"#password","text":"[$QTUgOMDYfXZ4gEjZ7BTYpw==]"}
```

```
write={"selector":"input[name='q']","text":"[%Keys.CONTROL,Keys.ALT]r"}
```

Notes: the text is appended to the existing content of the control (the field is not cleared first), and passwords are masked as `********` in the log.

### 8. Command wait

Waits for an element to be present and clickable (visible and enabled). Maximum wait: 10 seconds.

| Parameter | Required | Description |
|---|---|---|
| `selector` | optional | CSS or XPath selector of the object to wait for. **If omitted, `body` is used**, which means "wait for the page to be completely loaded" after a URL change or after a click. |
| `type` | optional | `css` (default) or `xpath`. |

```
wait={}
```

```
wait={"selector":"#results"}
```

```
wait={"selector":"//*[@id='search_form_input_homepage']","type":"xpath"}
```

### 9. Defining and using variables / command set

`set` creates a variable. Its behaviour depends on the parameters used:

**`value` (plain text):**
```
set={"name":"myvar","value":"hello"}
```
→ `[:myvar]` resolves to `"hello"`

**`value` + `type` (CSS/XPath selector, stores a WebElement):**
```
set={"name":"elem","value":"div.title","type":"css"}
```
→ `[:elem]` → element's text
→ `[:elem:text]` → element's text
→ `[:elem:css]` → CSS selector path
→ `[:elem:href]` (or any attribute) → `getAttribute("href")`

**`selector` (extracts the text of an element):**
```
set={"name":"title","selector":"h1.title"}
```
→ `[:title]` → text of `h1.title`

**`selector` + `parent` (scoped inside a parent variable):**
```
for={"selector":"ul.items > li","var":"item"}
    set={"name":"title","selector":"a.link","parent":"item"}
end={}
```
→ `[:title]` → text of `a.link` inside each `<li>`

The `parent` parameter uses the parent variable's cached CSS path concatenated with the child selector (`#parent-item-css a.link`), resolved by `driver.findElement()`. No parent WebElement is needed.

**`selector` + `parent` + `attr` (extracts an attribute):**
```
set={"name":"url","selector":"a.link","parent":"item","attr":"href"}
```
→ `[:url]` → href value of a.link

Notes:

- Variable names accept letters, digits, `_` and `-`.
- A variable holding a WebElement caches `text`, `css` and `href` at creation time; if the element becomes stale, `[:name]` still returns the cached text.
- `[:name:css]` is automatically converted to an XPath path when it is used inside a command whose `type` is `xpath`.
- Variables can be used anywhere inside a command: selectors, texts, URLs, pause times, fetch parameters, tab ids.

### 10. Writing an iterative loop / command for

`for` has two exclusive forms: it iterates over a list of values, or over the elements matching a selector. The block must be closed with `end={}`, and loops can be nested.

| Parameter | Required | Description |
|---|---|---|
| `var` | yes | Name of the loop variable. |

**Form A — over values, using `exp`:**

```
for={"var":"i","exp":"{1..10}"}
    write={"selector":"#field","text":"[:i]"}
end={}
```

- `exp="{a..b}"` — the inclusive numeric range between `a` and `b`. `b` may be smaller than `a` (then it counts down), and negative values are allowed. Each value is padded with zeros up to the number of digits of the written endpoint, so `{1..10}` iterates `1, 2, ... 10` while `{01..10}` iterates `01, 02, ... 10`.
- `exp="uno dos tres"` — space-separated words, which is very handy to run the same block for a list of data.

```
for={"var":"brand","exp":"bosch siemens schneider"}
    go={[:brandUrl]}
end={}
```

**Form B — over elements, using `selector`:**

| Parameter | Required | Description |
|---|---|---|
| `selector` | yes | CSS selector matching the elements to iterate. **Only CSS selectors are supported here**; an XPAth selector raises an error. |
| `type` | optional | `css` only. |

```
for={"selector":"ul.items > li","var":"item"}
    set={"name":"title","selector":"a.link","parent":"item"}
    click={"selector":"#detail"}
end={}
```

The loop variable (`item`) caches `text`, `css` path and `href` at creation time, then releases the WebElement:

- `[:item]` or `[:item:text]` → element's text (cached fallback if stale)
- `[:item:css]` → CSS selector path
- `[:item:href]` → href attribute
- `"parent":"item"` → uses the cached CSS + child selector concatenation via `driver.findElement()`

The cache enables variable access even after page navigation (e.g. an Ocupacol profile fetch). Parent scoping uses CSS path concatenation, so no WebElement reference is kept.

### 11. Command fetch

Performs an HTTP GET request against a JSON API and stores the selected fields as variables.

**Important:** the keys inside the `fetch` JSON are **never localized** — they are always `url`, `regex`, `baseUrl`, `params`, `byPath` and `prefix`, in every language.

| Key | Required | Description |
|---|---|---|
| `url` | yes | Source URL. Variables are resolved before the request. |
| `regex` | optional | When present, group(1) is extracted from `url`. |
| `baseUrl` | optional | Prepended to the extracted group to build the final URL. If omitted while `regex` is used, `https://dataportal.eplan.com/api/parts/` is used as base. |
| `params` | optional | JSON object appended to the URL as query string (e.g. `?include=...`). Values are concatenated as written, not URL-encoded. |
| `byPath` | yes | Object mapping variable names to dot-separated JSON paths. |
| `prefix` | optional | Prefix for the generated variable names, e.g. `prefix:"p"` → `[:p_name]`. |

```
fetch={"url":"https://example.com/parts/id-12345","regex":"id-(\\d+)","baseUrl":"https://api.example.com/parts/","byPath":{"name":"data.attributes.name"}}
```

```
fetch={"url":"[:url]","regex":"id-(\\d+)","baseUrl":"https://api.example.com/","params":{"include":"relation1,relation2"},"byPath":{"name":"data.attributes.name","relName":"included.eplan_catalog_entries.{data.relationships.rel.data.id}.attributes.name"},"prefix":"p"}
```

`byPath` paths:

- Plain paths are resolved from the root of the JSON: `data.attributes.name`.
- `included.{type}.{id|{ref}}.rest` accesses the JSON:API `included` array, where `{ref}` is a sub-path resolved against the root. Example: `included.eplan_catalog_entries.{data.relationships.product_group.data.id}.attributes.name` resolves the catalog entry ID dynamically and fetches its `name`.

Additional behaviour:

- The request is a GET with `Accept: application/json`, 10 s connection timeout and 30 s request timeout. Any status other than 200 raises an error.
- A sentinel variable `fetched` (or `[:p_fetched]` when `prefix` is used) with the value `ok` is created, useful to confirm the fetch succeeded before continuing.

### 12. Command scroll

Scrolls horizontally (`x`) and/or vertically (`y`) a given number of pixels.

| Parameter | Required | Description |
|---|---|---|
| `x` | yes | Pixels on the horizontal axis (> 0 right, < 0 left). |
| `y` | yes | Pixels on the vertical axis (> 0 down, < 0 up). |
| `selector` | optional | CSS or XPath selector of the scrollable element. **If omitted, the whole page is scrolled.** |
| `type` | optional | `css` (default) or `xpath`. |

```
scroll={"x":"0","y":"100"}
```

```
scroll={"x":"10","y":"-100","selector":"#main-content-inner"}
```

```
scroll={"x":"10","y":"-100","selector":"//*[@id='main-content-inner']","type":"xpath"}
```

Both `x` and `y` are mandatory and must be integers. Note that this runner is still marked as *not yet productive* in the source code.

### 13. Command browser

Changes the browser used by the tester. Like `go`, the content between braces is plain text, not JSON, and it is the enum name in upper case.

```
browser={CHROME}
```

Available values: `CHROME`, `EDGE`, `FIREFOX`, `INTERNET_EXPLORER`, `OPERA`, `SAFARI`.

Notes: the current browser is closed and a new one is started, so the session, the cookies and the registered tabs are lost and the start page is loaded. The Selenium driver matching the exact version of the browser must be installed.

### 14. Command newtab

Opens a URL in a new tab, switches to it and registers it so other commands can use it.

| Parameter | Required | Description |
|---|---|---|
| `url` | yes | URL to open in the new tab. |
| `id` | yes | Name used to register the tab. |

```
newtab={"url":"https://example.com","id":"exTab"}
```

After that, any command can be targeted to that tab with `"tab":"exTab"`, and the `tab` command can make it the global context.

---

### Annex A. Tab commands

Every JSON command accepts an optional `"tab":"id"` parameter to run in a different tab without switching the global context.

**`closetab`** — closes a tab by id:
```
closetab={"id":"exTab"}
```

**`tab`** — switches the global context to a registered tab:
```
tab={"id":"exTab"}
```

**`newtab`** opens and registers a tab (see section 14).

### Annex B. Command extract

Extracts values from a details table by matching the label text of each row.

| Key | Required | Description |
|---|---|---|
| `byLabel` | yes | Object mapping variable names to the label text to look for. |
| `prefix` | optional | Prefix for the generated variable names, e.g. `prefix:"p"` → `[:p_name]`. |
| `tab` | optional | Runs the extraction on the given tab. |

```
extract={"prefix":"p","byLabel":{"name":"Name","desc":"Description"}}
```

Creates the variables `p_name` and `p_desc` with the content of the matching cells, plus the sentinel `[:p_extracted]` = `ok`.

### Annex C. The `_start.txt` and `_end.txt` files

Inside any folder of the `scripts` tree, two file names have a special meaning: `_start.txt` and `_end.txt`. They are ordinary scripts (same format, same commands), but they never appear in the tree and cannot be run on their own: they run by themselves, surrounding the script you select.

When you run a script, the instructions are executed in this order:

1. the `_start.txt` of every folder that contains the script, from the outermost folder inwards;
2. the script itself;
3. the `_end.txt` of those same folders, from the innermost folder outwards.

So a `_start.txt` placed next to your scripts runs before **each** of them, and the `_end.txt` placed next to them runs after **each** of them. With nested folders you can put the common steps at the root of `scripts` and the specific ones in a subfolder; the outermost always runs first.

The browser, the cookies, the open tabs and the variables are shared during the whole run: a variable created in `_start.txt` can be used in the script and in `_end.txt`.

Typical uses: sign in before any test and sign out or clean up afterwards, accept a cookie banner, create the temporary data that a group of tests needs, close the tabs that were left open.

The application creates these files by default. On the first start, when the `scripts` folder does not exist yet, the application creates it together with `_start.txt`, `_end.txt` and a few example scripts in the `Africa`, `Asia` and `Europa` subfolders. **The two default files contain a working example** (a search in Wikipedia and another one in DuckDuckGo) that runs before and after every script. Edit them, or leave them empty, if you do not want that example to run.

Nothing obliges you to keep them: they are plain text files, you can edit them with any editor and you can delete them if you prefer to run only your own scripts.

An `_start.txt` that opens the site and accepts the cookie banner, and an `_end.txt` that signs out:

```
# _start.txt
go={https://example.com/}
click={"selector":"#accept-cookies"}
set={"name":"baseUrl","value":"https://example.com/"}
wait={}
```

```
# _end.txt
go={[:baseUrl]}
click={"selector":"#logout"}
wait={}
```

The scripts of that folder can then start from `[:baseUrl]` and end after signing out, without repeating those steps.

---

## Español

WebAppTester: Probador de formularios web mediante Selenium.

### Convenciones generales

Cada instrucción tiene el formato `comando={...}`, una por línea. Las líneas que empiezan con `#` son comentarios. El contenido entre llaves suele ser un objeto JSON; las únicas excepciones son `ir` y `navegador`, que admiten texto plano.

Los nombres de comandos **y** de parámetros están traducidos: un mismo comando tiene un nombre distinto en cada idioma (`go` / `ir` / `访问`). Todos los nombres se aceptan en cualquier idioma, de modo que los tres idiomas pueden mezclarse en un mismo script. Los ejemplos de esta sección usan los nombres en español.

Válido para casi todos los comandos:

- `"tab":"id"` — opcional. Ejecuta el comando en la pestaña registrada con ese `id`, sin cambiar el contexto global. No está disponible en `ir` ni en `navegador` (véase el anexo de pestañas).
- `[:variable]` — se reemplaza por el valor de la variable antes de ejecutar el comando (véase *Definición y uso de variables*).
- `[%Keys.ENTER]` — envía teclas especiales. `[%Keys.CONTROL,Keys.ALT]r` envía combinaciones de teclas. Escriba `[%%` para escribir el texto literal `[%`.
- `[$CIFRADO]` — se reemplaza por la contraseña descifrada (botón *Cifrar contraseña*). Escriba `[$$` para escribir el texto literal `[$`.

### 1. Comando ir

Navega a una dirección. La dirección se escribe **directamente** entre las llaves: no es JSON y no debe ir entre comillas.

```
ir={https://www.wikipedia.org/}
```

Todo el contenido se usa como dirección, por lo que aquí también funcionan las variables y las teclas:

```
asignar={"nombre":"sitio","valor":"https://www.wikipedia.org/"}
ir={[:sitio]}
```

### 2. Comando clic

Hace clic en un elemento: mueve el puntero del ratón sobre él y hace clic (`Actions.moveToElement().click()`).

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `selector` | opcional | Selector CSS o XPath del elemento. **Si se omite, el clic se dispara donde esté el puntero del ratón en ese momento.** |
| `tipo` | opcional | `css` (por omisión) o `xpath` |

```
clic={"selector":"button[type='submit']"}
```

```
clic={"selector":"//*[@id='search_form_input_homepage']","tipo":"xpath"}
```

```
clic={}
```

### 3. Comando doble clic

Hace doble clic en un elemento (`Actions.moveToElement().doubleClick()`). Mismos parámetros que `clic`: `selector` (opcional; sin él el doble clic se hace donde esté el puntero) y `tipo` (opcional).

```
doble clic={"selector":"#resultList li:first-child"}
```

```
doble clic={"selector":"/html/body","tipo":"xpath"}
```

### 4. Comando clic derecho

Hace clic derecho en un elemento (`Actions.moveToElement().contextClick()`), abriendo su menú contextual. Mismos parámetros que `clic`: `selector` (opcional) y `tipo` (opcional).

```
clic derecho={"selector":"#menuContainer"}
```

### 5. Comando pausa

Espera un tiempo dado antes de continuar con la siguiente instrucción.

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `tiempo` | sí | Un número seguido de una unidad, por ejemplo `"2 s"` |

Unidades: `S` = milisegundos, `s` = segundos, `m` = minutos, `h` = horas, `d` = días.

```
pausa={"tiempo":"500 S"}
pausa={"tiempo":"3 s"}
pausa={"tiempo":"10 m"}
pausa={"tiempo":"1 h"}
```

### 6. Comando seleccionar opcion

Muestra un cuadro de diálogo con los elementos que coincide con el selector y pide al usuario que elija uno. El elemento elegido se guarda en una variable.

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `selector` | sí | Selector que coincide con la colección de elementos a ofrecer. |
| `subselector` | sí | Ruta usada **dentro** de cada elemento coincidente para obtener el texto de cada opción. Si se omite, el comando falla con *No se especificó subselector*. |
| `variable` | sí | Nombre de la variable donde se guarda el elemento seleccionado. |
| `titulo` | opcional | Título del cuadro de diálogo. Por omisión: `Probador de formularios web`. |
| `mensaje` | opcional | Mensaje mostrado dentro del cuadro. Por omisión: *Seleccione una opción para continuar:* |
| `ordenado` | opcional | `si` / `no` — ordena las opciones alfabéticamente. |
| `tipo` | opcional | Se aplica a `selector` y a `subselector`. `css` (por omisión) o `xpath`. |

```
seleccionar opcion={
 "selector":"div.central-featured-lang",
 "subselector":"a",
 "ordenado":"si",
 "titulo":"Wikipedia",
 "mensaje":"Seleccione un lenguaje",
 "variable":"Lenguaje"
}
```

La variable resultante se comporta como una variable de selector:

```
clic={"selector":"[:Lenguaje] > a"}
```

Errores: el selector no coincide con nada → *el elemento para el selector {0} no tiene elementos hijos*; el usuario cancela → se aborta la ejecución; no se elige ninguna opción → se aborta la ejecución.

### 7. Comando escribir

Escribe texto en el elemento localizado por el selector (`WebElement.sendKeys`).

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `selector` | sí | Selector CSS o XPath del control de entrada. |
| `tipo` | opcional | `css` (por omisión) o `xpath`. |
| `texto` | sí | Texto a escribir. Admite variables, teclas especiales y contraseñas cifradas. |

```
escribir={"selector":"input[type='search']","texto":"inicio"}
```

```
escribir={"selector":"#password","texto":"[$QTUgOMDYfXZ4gEjZ7BTYpw==]"}
```

```
escribir={"selector":"input[name='q']","texto":"[%Keys.CONTROL,Keys.ALT]r"}
```

Notas: el texto se agrega al contenido existente del control (no se limpia antes) y las contraseñas se muestran como `********` en el registro.

### 8. Comando esperar

Espera a que un elemento exista y sea pulsable (visible y habilitado). Espera máxima: 10 segundos.

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `selector` | opcional | Selector CSS o XPath del objeto esperado. **Si se omite se usa `body`**, es decir, "esperar a que la página cargue completamente" después de un cambio de URL o de un clic. |
| `tipo` | opcional | `css` (por omisión) o `xpath`. |

```
esperar={}
```

```
esperar={"selector":"#results"}
```

```
esperar={"selector":"//*[@id='search_form_input_homepage']","tipo":"xpath"}
```

### 9. Definición y uso de variables / comando asignar

`asignar` crea una variable. Su comportamiento depende de los parámetros utilizados:

**`valor` (texto plano):**
```
asignar={"nombre":"mivariable","valor":"hola"}
```
→ `[:mivariable]` se resuelve a `"hola"`

**`valor` + `tipo` (selector CSS/XPath, almacena un WebElement):**
```
asignar={"nombre":"elem","valor":"div.title","tipo":"css"}
```
→ `[:elem]` → texto del elemento
→ `[:elem:text]` → texto del elemento
→ `[:elem:css]` → ruta del selector CSS
→ `[:elem:href]` (o cualquier atributo) → `getAttribute("href")`

**`selector` (extrae el texto del elemento):**
```
asignar={"nombre":"titulo","selector":"h1.title"}
```
→ `[:titulo]` → texto de h1.title

**`selector` + `parent` (búsqueda dentro de una variable padre):**
```
for={"selector":"ul.items > li","var":"item"}
    asignar={"nombre":"titulo","selector":"a.link","parent":"item"}
end={}
```
→ `[:titulo]` → texto de `a.link` dentro de cada `<li>`

El parámetro `parent` usa el CSS cacheado de la variable padre, concatenado con el selector hijo (`#css-padre a.link`), resuelto por `driver.findElement()`. No necesita el WebElement padre.

**`selector` + `parent` + `atributo` (extrae un atributo):**
```
asignar={"nombre":"url","selector":"a.link","parent":"item","atributo":"href"}
```
→ `[:url]` → valor del href de a.link

Notas:

- Los nombres de variable admiten letras, dígitos, `_` y `-`.
- Una variable que contiene un WebElement cachea `text`, `css` y `href` al crearse; si el elemento queda obsoleto, `[:nombre]` sigue devolviendo el texto cacheado.
- `[:nombre:css]` se convierte automáticamente en una ruta XPath cuando se usa dentro de un comando cuyo `tipo` es `xpath`.
- Las variables se pueden usar en cualquier parte de un comando: selectores, textos, direcciones, tiempos de pausa, parámetros de `obtener`, ids de pestañas.

### 10. Cómo escribir un ciclo iterativo con el comando for

`for` tiene dos formas excluyentes: itera sobre una lista de valores o sobre los elementos que coinciden con un selector. El bloque debe cerrarse con `end={}` y los ciclos pueden anidarse.

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `var` | sí | Nombre de la variable de iteración. |

**Forma A — sobre valores, usando `exp`:**

```
for={"var":"i","exp":"{1..10}"}
    escribir={"selector":"#field","texto":"[:i]"}
end={}
```

- `exp="{a..b}"` — rango numérico cerrado entre `a` y `b`. `b` puede ser menor que `a` (entonces cuenta hacia atrás) y se admiten valores negativos. Cada valor se rellena con ceros hasta el número de dígitos del extremo escrito, por lo que `{1..10}` itera `1, 2, ... 10` mientras que `{01..10}` itera `01, 02, ... 10`.
- `exp="uno dos tres"` — palabras separadas por espacios, muy útil para ejecutar el mismo bloque con una lista de datos.

```
for={"var":"marca","exp":"bosch siemens schneider"}
    ir={[:urlMarca]}
end={}
```

**Forma B — sobre elementos, usando `selector`:**

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `selector` | sí | Selector CSS de los elementos a iterar. **Aquí sólo se soportan selectores CSS**; un selector XPath genera un error. |
| `tipo` | opcional | Sólo `css`. |

```
for={"selector":"ul.items > li","var":"item"}
    asignar={"nombre":"titulo","selector":"a.link","parent":"item"}
    clic={"selector":"#detail"}
end={}
```

La variable de iteración (`item`) cachea `text`, `css` y `href` al crearse, y luego libera el WebElement:

- `[:item]` o `[:item:text]` → texto del elemento (usa el cache si está obsoleto)
- `[:item:css]` → ruta del selector CSS
- `[:item:href]` → atributo href
- `"parent":"item"` → usa el CSS cacheado + el selector hijo concatenado, resuelto por `driver.findElement()`

El cache permite acceder a la variable incluso tras navegar a otra página. El scoping padre-hijo usa concatenación de CSS, por lo que no se conserva ninguna referencia al WebElement.

### 11. Comando obtener

Realiza una petición HTTP GET a una API JSON y guarda campos como variables.

**Importante:** las claves dentro del JSON de `obtener` **nunca se traducen** — siempre son `url`, `regex`, `baseUrl`, `params`, `byPath` y `prefix`, en cualquier idioma.

| Clave | Obligatorio | Descripción |
|---|---|---|
| `url` | sí | Dirección de origen. Las variables se resuelven antes de la petición. |
| `regex` | opcional | Si está presente, se extrae group(1) de la `url`. |
| `baseUrl` | opcional | Se antepone al grupo extraído para formar la URL final. Si se omite mientras se usa `regex`, se usa `https://dataportal.eplan.com/api/parts/` como base. |
| `params` | opcional | Objeto JSON que se añade a la URL como cadena de consulta (p. ej. `?include=...`). Los valores se concatenan tal cual, sin codificar. |
| `byPath` | sí | Objeto que mapea nombres de variable a rutas JSON separadas por punto. |
| `prefix` | opcional | Prefijo para los nombres de variable generados, p. ej. `prefix:"p"` → `[:p_name]`. |

```
obtener={"url":"https://example.com/parts/id-12345","regex":"id-(\\d+)","baseUrl":"https://api.example.com/parts/","byPath":{"name":"data.attributes.name"}}
```

```
obtener={"url":"[:url]","regex":"id-(\\d+)","baseUrl":"https://api.example.com/","params":{"include":"relation1,relation2"},"byPath":{"name":"data.attributes.name","relName":"included.eplan_catalog_entries.{data.relationships.rel.data.id}.attributes.name"},"prefix":"p"}
```

Rutas de `byPath`:

- Las rutas simples se resuelven desde la raíz del JSON: `data.attributes.name`.
- `included.{tipo}.{id|{ref}}.rest` accede al array `included` de JSON:API, donde `{ref}` es una sub-ruta resuelta contra la raíz. Ejemplo: `included.eplan_catalog_entries.{data.relationships.product_group.data.id}.attributes.name` resuelve dinámicamente el ID del catálogo y obtiene su `name`.

Comportamiento adicional:

- La petición es un GET con `Accept: application/json`, 10 s de tiempo de espera de conexión y 30 s de tiempo de espera de respuesta. Cualquier estado distinto de 200 genera un error.
- Se crea una variable centinela `fetched` (o `[:p_fetched]` si se usa `prefix`) con el valor `ok`, útil para confirmar que la obtención fue exitosa antes de continuar.

### 12. Comando desplazar

Desplaza horizontalmente (`x`) y/o verticalmente (`y`) una cantidad dada de píxeles.

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `x` | sí | Píxeles en el eje horizontal (> 0 derecha, < 0 izquierda). |
| `y` | sí | Píxeles en el eje vertical (> 0 abajo, < 0 arriba). |
| `selector` | opcional | Selector CSS o XPath del elemento desplazable. **Si se omite se desplaza toda la página.** |
| `tipo` | opcional | `css` (por omisión) o `xpath`. |

```
desplazar={"x":"0","y":"100"}
```

```
desplazar={"x":"10","y":"-100","selector":"#main-content-inner"}
```

```
desplazar={"x":"10","y":"-100","selector":"//*[@id='main-content-inner']","tipo":"xpath"}
```

Tanto `x` como `y` son obligatorios y deben ser números enteros. Tenga en cuenta que este ejecutor aún está marcado como *aún no productivo* en el código fuente.

### 13. Comando navegador

Cambia el navegador usado por el probador. Igual que `ir`, el contenido entre llaves es texto plano, no JSON, y es el nombre de la constante en mayúsculas.

```
navegador={CHROME}
```

Valores disponibles: `CHROME`, `EDGE`, `FIREFOX`, `INTERNET_EXPLORER`, `OPERA`, `SAFARI`.

Notas: el navegador actual se cierra y se inicia uno nuevo, por lo que se pierden la sesión, las cookies y las pestañas registradas, y se carga la página de inicio. Debe estar instalado el controlador de Selenium correspondiente a la versión exacta del navegador.

### 14. Comando nuevapestaña

Abre una dirección en una pestaña nueva, cambia a ella y la registra para que otros comandos puedan usarla.

| Parámetro | Obligatorio | Descripción |
|---|---|---|
| `url` | sí | Dirección a abrir en la pestaña nueva. |
| `id` | sí | Nombre con el que se registra la pestaña. |

```
nuevapestaña={"url":"https://example.com","id":"exTab"}
```

Después de eso, cualquier comando puede dirigirse a esa pestaña con `"tab":"exTab"`, y el comando `pestaña` puede convertirla en el contexto global.

---

### Anexo A. Comandos de pestañas

Cada comando JSON acepta un parámetro opcional `"tab":"id"` para ejecutarse en una pestaña diferente sin cambiar el contexto global.

**`cerrarpestaña`** — cierra una pestaña por su id:
```
cerrarpestaña={"id":"exTab"}
```

**`pestaña`** — cambia el contexto global a una pestaña registrada:
```
pestaña={"id":"exTab"}
```

**`nuevapestaña`** abre y registra una pestaña (véase la sección 14).

### Anexo B. Comando extraer

Extrae valores de una tabla de detalles comparando el texto de la etiqueta de cada fila.

| Clave | Obligatorio | Descripción |
|---|---|---|
| `byLabel` | sí | Objeto que mapea nombres de variable al texto de la etiqueta a buscar. |
| `prefix` | opcional | Prefijo para los nombres de variable generados, p. ej. `prefix:"p"` → `[:p_name]`. |
| `tab` | opcional | Ejecuta la extracción en la pestaña indicada. |

```
extraer={"prefix":"p","byLabel":{"name":"Nombre","desc":"Descripción"}}
```

Crea las variables `p_name` y `p_desc` con el contenido de las celdas correspondientes, además de la centinela `[:p_extracted]` = `ok`.

### Anexo C. Los archivos `_start.txt` y `_end.txt`

Dentro de cualquier carpeta del árbol `scripts` hay dos nombres de archivo con un significado especial: `_start.txt` y `_end.txt`. Son scripts corrientes (mismo formato, mismos comandos), pero no aparecen en el árbol y no se pueden ejecutar por separado: se ejecutan solos, rodeando al script que usted selecciona.

Al ejecutar un script, las instrucciones se ejecutan en este orden:

1. el `_start.txt` de cada carpeta que contiene al script, de la carpeta más externa hacia la más interna;
2. el script en sí;
3. el `_end.txt` de esas mismas carpetas, de la carpeta más interna hacia la más externa.

Es decir, un `_start.txt` situado junto a sus scripts se ejecuta antes de **cada** uno de ellos, y el `_end.txt` situado junto a ellos se ejecuta después de **cada** uno. Con carpetas anidadas puede dejar los pasos comunes en la raíz de `scripts` y los específicos en una subcarpeta; la más externa siempre se ejecuta primero.

Durante toda la ejecución se comparten el navegador, las cookies, las pestañas abiertas y las variables: una variable creada en `_start.txt` se puede usar en el script y en `_end.txt`.

Usos típicos: iniciar sesión antes de cualquier prueba y cerrarla o limpiar después, aceptar el aviso de cookies, crear los datos temporales que necesita un grupo de pruebas, cerrar las pestañas que se hayan quedado abiertas.

La aplicación crea estos archivos por omisión. En el primer arranque, cuando todavía no existe la carpeta `scripts`, la aplicación la crea junto con `_start.txt`, `_end.txt` y algunos scripts de ejemplo en las subcarpetas `Africa`, `Asia` y `Europa`. **Los dos archivos por omisión contienen un ejemplo que funciona** (una búsqueda en Wikipedia y otra en DuckDuckGo) que se ejecuta antes y después de cada script. Edítelos, o déjelos vacíos, si no quiere que ese ejemplo se ejecute.

No está obligado a conservarlos: son archivos de texto normal, puede editarlos con cualquier editor y puede borrarlos si prefiere ejecutar únicamente sus propios scripts.

Un `_start.txt` que abre el sitio y acepta el aviso de cookies, y un `_end.txt` que cierra la sesión:

```
# _start.txt
ir={https://example.com/}
clic={"selector":"#accept-cookies"}
asignar={"nombre":"urlBase","valor":"https://example.com/"}
esperar={}
```

```
# _end.txt
ir={[:urlBase]}
clic={"selector":"#logout"}
esperar={}
```

Los scripts de esa carpeta pueden así empezar por `[:urlBase]` y terminar cerrando la sesión, sin repetir esos pasos.

---

## 中文

WebAppTester: 基于 Selenium 的网页表单测试工具。

### 通用约定

每条指令的格式为 `命令={...}`，每行一条。以 `#` 开头的行是注释。花括号之间的内容通常是 JSON 对象；唯一的例外是 `访问` 和 `浏览器`，它们接受纯文本。

命令名**和**参数名都已本地化：同一个命令在不同语言中名称不同（`go` / `ir` / `访问`）。任何语言中的名称都可使用，因此三种语言可以在同一个脚本中混用。本节示例使用中文名称。

对几乎所有命令都有效：

- `"tab":"id"` —— 可选。在注册了相同 `id` 的标签页中执行该命令，而不改变全局上下文。`访问` 和 `浏览器` 不支持该参数（参见标签页附录）。
- `[:变量名]` —— 在执行命令前被替换为变量的值（参见*定义与使用变量*）。
- `[%Keys.ENTER]` —— 发送特殊按键。`[%Keys.CONTROL,Keys.ALT]r` 发送组合键。要输入字面文本 `[%`，请写 `[%%`。
- `[$已加密]` —— 被替换为解密后的密码（"加密密码"按钮）。要输入字面文本 `[$`，请写 `[$$`。

### 1. 访问命令

导航到某个网址。网址**直接**写在花括号之间：它不是 JSON，也不应加引号。

```
访问={https://www.wikipedia.org/}
```

整个内容都会作为网址使用，因此这里也可以使用变量和按键：

```
设置={"名称":"sitio","值":"https://www.wikipedia.org/"}
访问={[:sitio]}
```

### 2. 单击命令

单击某个元素：将鼠标指针移到该元素上并点击（`Actions.moveToElement().click()`）。

| 参数 | 必填 | 说明 |
|---|---|---|
| `选择器` | 可选 | 元素的 CSS 或 XPath 选择器。**若省略，则在当前鼠标指针所在的位置触发单击。** |
| `类型` | 可选 | `css`（默认）或 `xpath` |

```
单击={"选择器":"button[type='submit']"}
```

```
单击={"选择器":"//*[@id='search_form_input_homepage']","类型":"xpath"}
```

```
单击={}
```

### 3. 双击命令

双击某个元素（`Actions.moveToElement().doubleClick()`）。参数与 `单击` 相同：`选择器`（可选，若省略则在当前指针位置双击）和 `类型`（可选）。

```
双击={"选择器":"#resultList li:first-child"}
```

```
双击={"选择器":"/html/body","类型":"xpath"}
```

### 4. 右击命令

右键单击某个元素（`Actions.moveToElement().contextClick()`），打开其上下文菜单。参数与 `单击` 相同：`选择器`（可选）和 `类型`（可选）。

```
右击={"选择器":"#menuContainer"}
```

### 5. 暂停命令

在继续下一条指令之前等待指定的时间。

| 参数 | 必填 | 说明 |
|---|---|---|
| `时间` | 是 | 一个数字加单位，例如 `"2 s"` |

单位：`S` = 毫秒，`s` = 秒，`m` = 分，`h` = 小时，`d` = 天。

```
暂停={"时间":"500 S"}
暂停={"时间":"3 s"}
暂停={"时间":"10 m"}
暂停={"时间":"1 h"}
```

### 6. 选择选项命令

显示一个对话框，其中列出与选择器匹配的元素，并要求用户挑选一个。被选中的元素保存在一个变量中。

| 参数 | 必填 | 说明 |
|---|---|---|
| `选择器` | 是 | 匹配待展示元素集合的选择器。 |
| `子选择器` | 是 | 在每个匹配元素**内部**使用、用于取得每个选项文本的路径。若省略，命令会以 *未提供子选择器！* 失败。 |
| `变量` | 是 | 保存所选元素的变量名。 |
| `标题` | 可选 | 对话框标题。默认：`网页测试`。 |
| `消息` | 可选 | 对话框中显示的消息。默认：*请选择一个选项以继续：* |
| `排序` | 可选 | `是` / `否` —— 按字母顺序排列选项。 |
| `类型` | 可选 | 同时作用于 `选择器` 和 `子选择器`。`css`（默认）或 `xpath`。 |

```
选择选项={
 "选择器":"div.central-featured-lang",
 "子选择器":"a",
 "排序":"是",
 "标题":"Wikipedia",
 "消息":"请选择一种语言",
 "变量":"Language"
}
```

生成的变量行为与选择器变量相同：

```
单击={"选择器":"[:Language] > a"}
```

错误情况：选择器没有匹配到任何元素 → *选择器 {0} 的元素没有子元素*；用户取消 → 中止执行；未选择任何选项 → 中止执行。

### 7. 写入命令

在由选择器定位到的元素中输入文本（`WebElement.sendKeys`）。

| 参数 | 必填 | 说明 |
|---|---|---|
| `选择器` | 是 | 输入控件的 CSS 或 XPath 选择器。 |
| `类型` | 可选 | `css`（默认）或 `xpath`。 |
| `文本` | 是 | 要输入的文本。支持变量、特殊按键和加密密码。 |

```
写入={"选择器":"input[type='search']","文本":"inicio"}
```

```
写入={"选择器":"#password","文本":"[$QTUgOMDYfXZ4gEjZ7BTYpw==]"}
```

```
写入={"选择器":"input[name='q']","文本":"[%Keys.CONTROL,Keys.ALT]r"}
```

注意：文本会追加到控件已有内容之后（不会先清空），且密码在日志中显示为 `********`。

### 8. 等待命令

等待某个元素存在并且可点击（可见且已启用）。最长等待：10 秒。

| 参数 | 必填 | 说明 |
|---|---|---|
| `选择器` | 可选 | 要等待对象的 CSS 或 XPath 选择器。**若省略则使用 `body`**，即在 URL 变更或点击之后"等待页面完全加载"。 |
| `类型` | 可选 | `css`（默认）或 `xpath`。 |

```
等待={}
```

```
等待={"选择器":"#results"}
```

```
等待={"选择器":"//*[@id='search_form_input_homepage']","类型":"xpath"}
```

### 9. 定义与使用变量 / 设置命令

`设置` 创建一个变量。其行为取决于所使用的参数：

**`值`（纯文本）：**
```
设置={"名称":"myvar","值":"hello"}
```
→ `[:myvar]` 解析为 `"hello"`

**`值` + `类型`（CSS/XPath 选择器，存储 WebElement）：**
```
设置={"名称":"elem","值":"div.title","类型":"css"}
```
→ `[:elem]` → 元素的文本
→ `[:elem:text]` → 元素的文本
→ `[:elem:css]` → CSS 选择器路径
→ `[:elem:href]`（或任何属性）→ `getAttribute("href")`

**`选择器`（提取元素文本）：**
```
设置={"名称":"title","选择器":"h1.title"}
```
→ `[:title]` → h1.title 的文本

**`选择器` + `parent`（在父变量内查找）：**
```
循环={"选择器":"ul.items > li","变量":"item"}
    设置={"名称":"title","选择器":"a.link","parent":"item"}
结束={}
```
→ `[:title]` → 每个 `<li>` 中 `a.link` 的文本

`parent` 参数使用父变量的缓存 CSS 路径，与子选择器拼接（`#父元素-css a.link`），由 `driver.findElement()` 解析。无需父元素的 WebElement。

**`选择器` + `parent` + `属性`（提取属性）：**
```
设置={"名称":"url","选择器":"a.link","parent":"item","属性":"href"}
```
→ `[:url]` → a.link 的 href 值

注意：

- 变量名可使用字母、数字、`_` 和 `-`。
- 持有 WebElement 的变量会在创建时缓存 `text`、`css` 和 `href`；即使元素失效，`[:名称]` 仍返回缓存的文本。
- 当 `[:名称:css]` 用在 `类型` 为 `xpath` 的命令中时，会自动转换为 XPath 路径。
- 变量可以用在命令的任何位置：选择器、文本、网址、暂停时间、`获取` 参数、标签页 id。

### 10. 使用循环命令编写迭代循环

`循环` 有两种互斥的形式：遍历一组值，或遍历与选择器匹配的元素。代码块必须以 `结束={}` 收尾，循环可以嵌套。

| 参数 | 必填 | 说明 |
|---|---|---|
| `变量` | 是 | 迭代变量的名称。 |

**形式 A —— 遍历值，使用 `表达式`：**

```
循环={"变量":"i","表达式":"{1..10}"}
    写入={"选择器":"#field","文本":"[:i]"}
结束={}
```

- `表达式="{a..b}"` —— `a` 与 `b` 之间的闭区间数值范围。`b` 可以小于 `a`（此时倒序遍历），也支持负数。各值会按所写端点的位数补零，因此 `{1..10}` 依次迭代 `1, 2, ... 10`，而 `{01..10}` 依次迭代 `01, 02, ... 10`。
- `表达式="uno dos tres"` —— 以空格分隔的单词，非常适合用一份数据列表执行同一个代码块。

```
循环={"变量":"brand","表达式":"bosch siemens schneider"}
    访问={[:brandUrl]}
结束={}
```

**形式 B —— 遍历元素，使用 `选择器`：**

| 参数 | 必填 | 说明 |
|---|---|---|
| `选择器` | 是 | 要迭代的元素的 CSS 选择器。**这里仅支持 CSS 选择器**；使用 XPath 选择器会报错。 |
| `类型` | 可选 | 只能是 `css`。 |

```
循环={"选择器":"ul.items > li","变量":"item"}
    设置={"名称":"title","选择器":"a.link","parent":"item"}
    单击={"选择器":"#detail"}
结束={}
```

循环变量（`item`）在创建时缓存 `text`、`css` 路径和 `href`，然后释放 WebElement：

- `[:item]` 或 `[:item:text]` → 元素文本（元素失效时使用缓存值）
- `[:item:css]` → CSS 选择器路径
- `[:item:href]` → href 属性
- `"parent":"item"` → 使用缓存 CSS + 子选择器拼接，由 `driver.findElement()` 解析

缓存机制确保即使页面导航后仍可访问变量值。父-子作用域使用 CSS 路径拼接，因此不保留任何 WebElement 引用。

### 11. 获取命令

向 JSON API 发送 HTTP GET 请求，并将选定字段保存为变量。

**重要：** `获取` 的 JSON 内部键名**从不翻译** —— 在任何语言中始终是 `url`、`regex`、`baseUrl`、`params`、`byPath` 和 `prefix`。

| 键 | 必填 | 说明 |
|---|---|---|
| `url` | 是 | 源网址。请求前会先解析其中的变量。 |
| `regex` | 可选 | 若提供，则从 `url` 中提取 group(1)。 |
| `baseUrl` | 可选 | 前置到提取出的 group 之前以形成最终 URL。若在使用 `regex` 时省略，则以 `https://dataportal.eplan.com/api/parts/` 为基础。 |
| `params` | 可选 | 作为查询字符串附加到 URL 的 JSON 对象（例如 `?include=...`）。值按原样拼接，不做 URL 编码。 |
| `byPath` | 是 | 将变量名映射到点分隔 JSON 路径的对象。 |
| `prefix` | 可选 | 生成变量名的前缀，例如 `prefix:"p"` → `[:p_name]`。 |

```
获取={"url":"https://example.com/parts/id-12345","regex":"id-(\\d+)","baseUrl":"https://api.example.com/parts/","byPath":{"name":"data.attributes.name"}}
```

```
获取={"url":"[:url]","regex":"id-(\\d+)","baseUrl":"https://api.example.com/","params":{"include":"relation1,relation2"},"byPath":{"name":"data.attributes.name","relName":"included.eplan_catalog_entries.{data.relationships.rel.data.id}.attributes.name"},"prefix":"p"}
```

`byPath` 路径：

- 普通路径从 JSON 根节点开始解析：`data.attributes.name`。
- `included.{type}.{id|{ref}}.rest` 用于访问 JSON:API 的 `included` 数组，其中 `{ref}` 是相对于根节点解析的子路径。示例：`included.eplan_catalog_entries.{data.relationships.product_group.data.id}.attributes.name` 动态解析目录条目 ID 并获取其 `name`。

其他行为：

- 请求为 GET，带 `Accept: application/json`，连接超时 10 秒，请求超时 30 秒。任何非 200 的状态码都会产生错误。
- 会创建一个值为 `ok` 的哨兵变量 `fetched`（使用 `prefix` 时为 `[:p_fetched]`），可在继续执行前确认获取是否成功。

### 12. 滚动命令

按指定的像素数水平（`x`）和／或垂直（`y`）滚动。

| 参数 | 必填 | 说明 |
|---|---|---|
| `x` | 是 | 水平方向的像素数（> 0 向右，< 0 向左）。 |
| `y` | 是 | 垂直方向的像素数（> 0 向下，< 0 向上）。 |
| `选择器` | 可选 | 可滚动元素的 CSS 或 XPath 选择器。**若省略则滚动整个页面。** |
| `类型` | 可选 | `css`（默认）或 `xpath`。 |

```
滚动={"x":"0","y":"100"}
```

```
滚动={"x":"10","y":"-100","选择器":"#main-content-inner"}
```

```
滚动={"x":"10","y":"-100","选择器":"//*[@id='main-content-inner']","类型":"xpath"}
```

`x` 与 `y` 均为必填且必须是整数。请注意，该执行器在源代码中仍被标记为"尚未投入生产"。

### 13. 浏览器命令

更换测试工具使用的浏览器。与 `访问` 一样，花括号之间是纯文本而非 JSON，且为大写的枚举名称。

```
浏览器={CHROME}
```

可用值：`CHROME`、`EDGE`、`FIREFOX`、`INTERNET_EXPLORER`、`OPERA`、`SAFARI`。

注意：当前浏览器会被关闭并启动新的浏览器，因此会丢失会话、Cookie 和已注册的标签页，并加载起始页。必须安装与浏览器确切版本相匹配的 Selenium 驱动程序。

### 14. 新标签页命令

在新标签页中打开一个网址，切换到该标签页并注册它，以便其他命令使用。

| 参数 | 必填 | 说明 |
|---|---|---|
| `url` | 是 | 要在新标签页中打开的网址。 |
| `id` | 是 | 用于注册该标签页的名称。 |

```
新标签页={"url":"https://example.com","id":"exTab"}
```

之后，任何命令都可以用 `"tab":"exTab"` 指向该标签页，也可以用 `标签页` 命令将其设为全局上下文。

---

### 附录 A. 标签页命令

每个 JSON 命令都接受可选的 `"tab":"id"` 参数，用于在另一个标签页中执行而不切换全局上下文。

**`关闭标签页`** —— 按 id 关闭标签页：
```
关闭标签页={"id":"exTab"}
```

**`标签页`** —— 将全局上下文切换到已注册的标签页：
```
标签页={"id":"exTab"}
```

**`新标签页`** 打开并注册一个标签页（参见第 14 节）。

### 附录 B. 提取命令

通过匹配每一行的标签文本，从详情表中提取值。

| 键 | 必填 | 说明 |
|---|---|---|
| `byLabel` | 是 | 将变量名映射到待查找标签文本的对象。 |
| `prefix` | 可选 | 生成变量名的前缀，例如 `prefix:"p"` → `[:p_name]`。 |
| `tab` | 可选 | 在指定的标签页中执行提取。 |

```
extract={"prefix":"p","byLabel":{"name":"名称","desc":"描述"}}
```

创建变量 `p_name` 和 `p_desc`，值为对应单元格的内容，并创建哨兵变量 `[:p_extracted]` = `ok`。

### 附录 C. `_start.txt` 和 `_end.txt` 文件

在 `scripts` 目录树的任何文件夹中，有两个文件名具有特殊含义：`_start.txt` 和 `_end.txt`。它们是普通脚本（格式相同、命令相同），但不会出现在目录树中，也无法单独执行：它们会自动执行，包裹您所选择的脚本。

执行某个脚本时，指令按以下顺序运行：

1. 包含该脚本的各级文件夹中的 `_start.txt`，由最外层向内层；
2. 脚本本身；
3. 这些相同文件夹中的 `_end.txt`，由最内层向外层。

也就是说，放在脚本旁边的 `_start.txt` 会在**每个**脚本之前执行，同一目录下的 `_end.txt` 会在**每个**脚本之后执行。使用嵌套文件夹时，可以把通用步骤放在 `scripts` 的根目录，把特定步骤放在子文件夹中；最外层的总是最先执行。

在整个运行过程中，浏览器、Cookie、已打开的标签页和变量都是共享的：在 `_start.txt` 中创建的变量可以在脚本和 `_end.txt` 中使用。

典型用途：在所有测试之前登录，在之后登出或清理；接受 Cookie 提示；为一批测试创建所需的临时数据；关闭遗留的标签页。

应用程序会默认创建这些文件。首次启动时，如果 `scripts` 文件夹尚不存在，应用程序会创建该文件夹以及 `_start.txt`、`_end.txt`，并在 `Africa`、`Asia` 和 `Europa` 子文件夹中放入一些示例脚本。**两个默认文件包含可正常运行的示例**（一次 Wikipedia 搜索和一次 DuckDuckGo 搜索），它会在每个脚本之前和之后执行。如果您不希望该示例被执行，请编辑它们或将它们留空。

您不必保留它们：它们就是普通文本文件，可以用任意编辑器修改，也可以删除，以便只运行自己的脚本。

一个打开站点并接受 Cookie 提示的 `_start.txt`，以及一个退出登录的 `_end.txt`：

```
# _start.txt
访问={https://example.com/}
单击={"选择器":"#accept-cookies"}
设置={"名称":"urlBase","值":"https://example.com/"}
等待={}
```

```
# _end.txt
访问={[:urlBase]}
单击={"选择器":"#logout"}
等待={}
```

该文件夹中的脚本就可以从 `[:urlBase]` 开始，并以退出登录结束，无需重复这些步骤。
