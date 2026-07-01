**jWebAppTester**

## English

WebAppTester: Web form tester using Selenium.

### Variable Resolution

`set` stores a variable. How it's resolved depends on the parameters:

**set with `"value"` (plain string):**
```
set={"name":"myvar","value":"hello"}
```
→ `[:myvar]` resolves to `"hello"`

**set with `"value"` + `"type"` (CSS/XPath selector, stores a WebElement):**
```
set={"name":"elem","value":"div.title","type":"css"}
```
→ `[:elem]` → element's text
→ `[:elem:text]` → element's text
→ `[:elem:css]` → CSS selector path
→ `[:elem:href]` (or any attribute) → `getAttribute("href")`

**set with `"selector"` (extracts text from element):**
```
set={"name":"title","selector":"h1.title"}
```
→ `[:title]` → text of h1.title

**set with `"selector"` + `"parent"` (scoped within a parent element):**
```
for={"selector":"ul.items > li","var":"item"}
    set={"name":"title","selector":"a.link","parent":"item"}
```
→ `[:title]` → text of `a.link` inside each `<li>`

The `parent` parameter uses the parent variable's cached CSS path, concatenated with the child selector (`#parent-item-css a.link`), resolved by `driver.findElement()`. No parent WebElement is needed.

**set with `"selector"` + `"parent"` + `"attr"` (extract attribute):**
```
set={"name":"url","selector":"a.link","parent":"item","attr":"href"}
```
→ `[:url]` → href value of a.link

### Tab Commands

Each command accepts an optional `"tab":"id"` parameter to execute in a different tab without switching global context:

**newtab** / **nuevapestaña** — Opens URL in a new tab and registers it:
```
newtab={"url":"https://example.com","id":"exTab"}
```

**closetab** / **cerrarpestaña** — Closes a tab by id:
```
closetab={"id":"exTab"}
```

**tab** / **pestaña** — Switches the global context to a registered tab:
```
tab={"id":"exTab"}
```

### For Loop

Iterates over CSS selector matches, creating a loop variable for each:
```
for={"selector":"ul.items > li","var":"item"}
    set={"name":"title","selector":"a.link","parent":"item"}
end={}
```

The loop variable (`item`) caches `text`, `css` path, and `href` at creation time, then releases the WebElement:
- `[:item]` or `[:item:text]` → element's text (cached fallback if stale)
- `[:item:css]` → CSS selector path
- `[:item:href]` → href attribute
- `parent:"item"` → uses cached CSS + child selector concatenation via `driver.findElement()`

The cache enables variable access even after page navigation (e.g., Ocupacol profile fetch). Parent scoping uses CSS path concatenation, so no WebElement reference is kept.

### Fetch

Makes an HTTP GET request to a JSON API and stores fields as variables:
```
fetch={"url":"[:url]","regex":"id-(\\d+)","baseUrl":"https://api.example.com/","params":{"include":"relation1,relation2"},"byPath":{"name":"data.attributes.name","relName":"included.eplan_catalog_entries.{data.relationships.rel.data.id}.attributes.name"},"prefix":""}
```
- `url` — source URL (extracted from a link attribute)
- `regex` — extracts group(1) from url to build the API call
- `baseUrl` — prepended to the extracted group to form the final URL
- `params` — optional JSON object appended as URL query string (e.g., `?include=...`)
- `byPath` — dot-separated JSON paths mapped to variable names. Use `included.{type}.{id|{ref}}.rest` to access the JSON:API `included` array, where `{ref}` is a sub-path resolved against the root. Example: `included.eplan_catalog_entries.{data.relationships.product_group.data.id}.attributes.name` resolves the catalog entry ID dynamically and fetches its `name`.
- `prefix` — optional prefix for variable names (e.g., `prefix:"p"` → `[:p_name]`)

---

## Español

WebAppTester: Probador de formularios web mediante Selenium.

### Resolución de Variables

`set` almacena una variable. Su resolución depende de los parámetros:

**set con `"value"` (texto plano):**
```
set={"name":"myvar","value":"hola"}
```
→ `[:myvar]` se resuelve a `"hola"`

**set con `"value"` + `"type"` (selector CSS/XPath, almacena un WebElement):**
```
set={"name":"elem","value":"div.title","type":"css"}
```
→ `[:elem]` → texto del elemento
→ `[:elem:text]` → texto del elemento
→ `[:elem:css]` → ruta del selector CSS
→ `[:elem:href]` (o cualquier atributo) → `getAttribute("href")`

**set con `"selector"` (extrae texto del elemento):**
```
set={"name":"titulo","selector":"h1.title"}
```
→ `[:titulo]` → texto de h1.title

**set con `"selector"` + `"parent"` (búsqueda dentro de un elemento padre):**
```
for={"selector":"ul.items > li","var":"item"}
    set={"name":"titulo","selector":"a.link","parent":"item"}
```
→ `[:titulo]` → texto de `a.link` dentro de cada `<li>`

El parámetro `parent` usa el CSS cacheado de la variable padre, concatenado con el selector hijo (`#css-padre a.link`), resuelto por `driver.findElement()`. No necesita el WebElement padre.

**set con `"selector"` + `"parent"` + `"attr"` (extrae atributo):**
```
set={"name":"url","selector":"a.link","parent":"item","attr":"href"}
```
→ `[:url]` → valor del href de a.link

### Comandos de Pestañas

Cada comando acepta un parámetro opcional `"tab":"id"` para ejecutar en una pestaña diferente sin cambiar el contexto global:

**newtab** / **nuevapestaña** — Abre URL en nueva pestaña y la registra:
```
nuevapestaña={"url":"https://example.com","id":"exTab"}
```

**closetab** / **cerrarpestaña** — Cierra una pestaña por su id:
```
cerrarpestaña={"id":"exTab"}
```

**tab** / **pestaña** — Cambia el contexto global a una pestaña registrada:
```
pestaña={"id":"exTab"}
```

### Bucle For

Itera sobre elementos que coinciden con un selector CSS, creando una variable de bucle para cada uno:
```
for={"selector":"ul.items > li","var":"item"}
    set={"name":"title","selector":"a.link","parent":"item"}
end={}
```

La variable de bucle (`item`) cachea `text`, `css` y `href` al crearse, y luego libera el WebElement:
- `[:item]` o `[:item:text]` → texto del elemento (usa cache si está stale)
- `[:item:css]` → ruta del selector CSS
- `[:item:href]` → atributo href
- `parent:"item"` → usa CSS cacheado + selector hijo concatenado, resuelto por `driver.findElement()`

El cache permite acceder a la variable incluso tras navegar a otra página. El scoping padre-hijo usa concatenación de CSS, por lo que no se conserva ninguna referencia al WebElement.

### Fetch

Hace una petición HTTP GET a una API JSON y guarda campos como variables:
```
fetch={"url":"[:url]","regex":"id-(\\d+)","baseUrl":"https://api.example.com/","params":{"include":"relation1,relation2"},"byPath":{"name":"data.attributes.name","relName":"included.eplan_catalog_entries.{data.relationships.rel.data.id}.attributes.name"},"prefix":""}
```
- `url` — URL de origen (extraída de un atributo de enlace)
- `regex` — extrae group(1) de la url para construir la llamada API
- `baseUrl` — se antepone al grupo extraído para formar la URL final
- `params` — objeto JSON opcional que se añade como query string a la URL (ej. `?include=...`)
- `byPath` — rutas JSON separadas por punto mapeadas a nombres de variable. Usa `included.{type}.{id|{ref}}.rest` para acceder al array `included` de JSON:API, donde `{ref}` es una sub-ruta resuelta contra la raíz. Ejemplo: `included.eplan_catalog_entries.{data.relationships.product_group.data.id}.attributes.name` resuelve dinámicamente el ID del catálogo y obtiene su `name`.
- `prefix` — prefijo opcional para nombres de variable (ej. `prefix:"p"` → `[:p_name]`)

---

## 中文

WebAppTester: 基于 Selenium 的网页表单测试工具。

### 变量解析

**带 `"value"` 的 set（纯文本）：**
```
set={"name":"myvar","value":"hello"}
```
→ `[:myvar]` 解析为 `"hello"`

**带 `"value"` + `"type"` 的 set（CSS/XPath 选择器，存储 WebElement）：**
```
set={"name":"elem","value":"div.title","type":"css"}
```
→ `[:elem]` → 元素的文本
→ `[:elem:text]` → 元素的文本
→ `[:elem:css]` → CSS 选择器路径
→ `[:elem:href]`（或任何属性）→ `getAttribute("href")`

**带 `"selector"` 的 set（提取元素文本）：**
```
set={"name":"title","selector":"h1.title"}
```
→ `[:title]` → h1.title 的文本

**带 `"selector"` + `"parent"` 的 set（在父元素内查找）：**
```
for={"selector":"ul.items > li","var":"item"}
    set={"name":"title","selector":"a.link","parent":"item"}
```
→ `[:title]` → 每个 `<li>` 中 `a.link` 的文本

`parent` 参数使用父变量的缓存 CSS 路径，与子选择器拼接（`#父元素-css a.link`），由 `driver.findElement()` 解析。无需父元素的 WebElement。

**带 `"selector"` + `"parent"` + `"attr"` 的 set（提取属性）：**
```
set={"name":"url","selector":"a.link","parent":"item","attr":"href"}
```
→ `[:url]` → a.link 的 href 值

### 标签页命令

每个命令可接受可选的 `"tab":"id"` 参数以在不同标签页中执行，无需切换全局上下文：

**newtab** / **nuevapestaña** — 在新标签页中打开 URL 并注册：
```
newtab={"url":"https://example.com","id":"exTab"}
```

**closetab** / **cerrarpestaña** — 按 ID 关闭标签页：
```
closetab={"id":"exTab"}
```

**tab** / **pestaña** — 将全局上下文切换到已注册的标签页：
```
tab={"id":"exTab"}
```

### 按标签提取

通过匹配标签文本从详情表中提取值：
```
extract={"prefix":"p","byLabel":{"name":"名称","desc":"描述"}}
```
创建变量 `p_name`、`p_desc`，值为对应单元格的内容。

### For 循环

遍历 CSS 选择器匹配的元素，为每个元素创建循环变量：
```
for={"selector":"ul.items > li","var":"item"}
    set={"name":"title","selector":"a.link","parent":"item"}
end={}
```

循环变量 (`item`) 在创建时缓存了 `text`、`css` 路径和 `href`，然后释放 WebElement：
- `[:item]` 或 `[:item:text]` → 元素文本（元素失效时使用缓存值）
- `[:item:css]` → CSS 选择器路径
- `[:item:href]` → href 属性
- `parent:"item"` → 使用缓存 CSS + 子选择器拼接，由 `driver.findElement()` 解析

缓存机制确保即使页面导航后仍可访问变量值。父-子作用域使用 CSS 路径拼接，因此不保留任何 WebElement 引用。

### Fetch

向 JSON API 发送 HTTP GET 请求，并将字段存储为变量：
```
fetch={"url":"[:url]","regex":"id-(\\d+)","baseUrl":"https://api.example.com/","params":{"include":"relation1,relation2"},"byPath":{"name":"data.attributes.name","relName":"included.eplan_catalog_entries.{data.relationships.rel.data.id}.attributes.name"},"prefix":""}
```
- `url` — 源 URL（从链接属性中提取）
- `regex` — 从 url 提取 group(1) 以构建 API 调用
- `baseUrl` — 前置到提取的 group 以形成最终 URL
- `params` — 可选的 JSON 对象，将作为 URL 查询字符串附加（例如 `?include=...`）
- `byPath` — 点分隔的 JSON 路径映射到变量名。使用 `included.{type}.{id|{ref}}.rest` 访问 JSON:API 的 `included` 数组，其中 `{ref}` 是一个子路径，相对于根解析。示例：`included.eplan_catalog_entries.{data.relationships.product_group.data.id}.attributes.name` 动态解析目录条目 ID 并获取其 `name`。
- `prefix` — 变量名的可选前缀（例如 `prefix:"p"` → `[:p_name]`）
