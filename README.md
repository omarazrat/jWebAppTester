# jWebAppTester

WebAppTester: Tester for online forms / Probador de formularios online / 在线表单测试工具

This program uses Selenium to open a web browser and run user-written commands.

---

## English

### Overview
WebAppTester executes script files containing commands that control a web browser.  
Commands follow the format: `action={json_params}`

### Script File Format
- Lines starting with `#` are comments.
- Empty lines are ignored.
- Multi-line commands are joined until valid JSON is formed.
- Files are read from the `scripts/` folder.

### Variables
- `[:varname]` — Runtime variable (set via `set` command)
- `[:varname:text]` — Variable resolved as text (default)
- `[:varname:path]` — Variable resolved as CSS/XPath path
- `[:varname:href]` — Variable resolved as element's `href` attribute (from `for` loops with selector)
- `[%Keys.xxx%]` — Special keys (CONTROL, ESCAPE, TAB, ENTER, etc.)
- `[$encrypted]` — Decrypted password (use `Crypt password` button to generate)

### Command Reference

| Action | Command | Description | JSON Parameters |
|--------|---------|-------------|----------------|
| **go** | `go={url}` | Navigate to URL | `url` as plain text inside `{}` |
| **click** | `click={...}` | Click on element | `"selector"` (CSS/XPath), `"type"` (css/xpath) |
| **double click** | `double click={...}` | Double-click on element | `"selector"`, `"type"` |
| **right click** | `right click={...}` | Right-click on element | `"selector"`, `"type"` |
| **write** | `write={...}` | Type text into element | `"selector"`, `"type"`, `"text"` |
| **wait** | `wait={...}` | Wait for element to appear | `"selector"`, `"type"` (default: `body`) |
| **pause** | `pause={...}` | Pause execution | `"time"` (e.g., `5s`, `100S`, `2m`, `1h`, `1d`) |
| **scroll** | `scroll={...}` | Scroll page or element | `"x"`, `"y"`, `"selector"` (optional) |
| **set** | `set={...}` | Set a variable | `"name"`/`"var"`, `"value"` (string), or `"selector"` + `"type"` (extracts text), or `"script"` (JavaScript result) |
| **browser** | `browser={...}` | Switch browser | browser type as plain text: `CHROME`, `EDGE`, `FIREFOX` |
| **for** | `for={...}` | Iterative loop | `"var"`, `"exp"` (range `{1..10}` or space-separated list) |
| **end** | `end={}` | End iterative block | (no parameters) |
| **place mouse pointer** | `place mouse pointer={...}` | Move mouse | `"x"`, `"y"`, `"offsetType"` (FROM_UL_CORNER / FROM_CNTR_OBJECT / FROM_CUR_LOCATION) |
| **pick choice** | `pick choice={...}` | Show selection dialog | `"selector"`, `"subselector"`, `"variable"`, `"title"`, `"message"`, `"sorted"` |

### Examples

```
# Navigate to a website
go={https://example.com}

# Click on a button
click={"selector":"#submit-btn"}

# Type into a text field
write={"selector":"#username","text":"admin"}

# Wait for element to load
wait={"selector":".result-table"}

# Pause for 2 seconds
pause={"time":"2s"}

# Set a string variable
set={"name":"url","value":"https://example.com"}

# Set a variable from element text (CSS selector, type defaults to css)
set={"name":"profesion","selector":".col-lg-9 > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(1)"}

# Set a variable from element text (explicit type)
set={"name":"heading","selector":"#main-title","type":"css"}

# Set a variable from element text using XPath
set={"name":"price","selector":"//span[@class='price']","type":"xpath"}

# Set a variable by executing JavaScript (React SPAs)
set={"name":"price","script":"return window.__APP_INITIAL_STATE__.productData.priceInfo.regularPrice;"}

# Use a variable
go={[:url]}

# Scroll 100 pixels down
scroll={"x":0,"y":100}

# Double-click on an element
double click={"selector":"#item1"}

# Right-click
right click={"selector":"#context-menu"}

# Loop from 1 to 5
for={"var":"i","exp":"{1..5}"}
    click={"selector":"#row-[:i]"}
end={}

# Loop over a list
for={"var":"name","exp":"Alice Bob Charlie"}
    write={"selector":"#name","text":"[:name]"}
end={}

# Iterate over elements and extract href attributes
for={"var":"product","selector":"div.owned-brands__container div.card a"}
	go={[:product:href]}
end={}

# Switch browser
browser={FIREFOX}

# Pick a choice from a list
pick choice={
    "selector":"div.options",
    "subselector":"a",
    "variable":"selected",
    "title":"Select an option",
    "message":"Please choose:"
}

# Move mouse to coordinates
place mouse pointer={"x":100,"y":200}

# Encrypted password
write={"selector":"#pass","text":"[$AbCdEf12345]"}
```

### Supported Browsers
- `CHROME` — Google Chrome
- `EDGE` — Microsoft Edge
- `FIREFOX` — Mozilla Firefox
- `INTERNET_EXPLORER` — Internet Explorer
- `SAFARI` — Apple Safari

### Selenium IDE Import
You can import Selenium IDE (.side) files using the "Load Selenium IDE script" button.

### Password Encryption
Use the "Crypt password" button to generate encrypted strings for use with `[$...]` syntax.

---

## Español

### Descripción General
WebAppTester ejecuta archivos de script que contienen comandos para controlar un navegador web.  
Los comandos siguen el formato: `acción={parámetros_json}`

### Formato de Archivo de Script
- Las líneas que comienzan con `#` son comentarios.
- Las líneas vacías se ignoran.
- Los comandos multilínea se unen hasta formar un JSON válido.
- Los archivos se leen desde la carpeta `scripts/`.

### Variables
- `[:nombre]` — Variable de ejecución (establecida con el comando `asignar`)
- `[:nombre:texto]` — Variable resuelta como texto (predeterminado)
- `[:nombre:ruta]` — Variable resuelta como ruta CSS/XPath
- `[:nombre:href]` — Variable resuelta como el atributo `href` del elemento (desde bucles `for` con selector)
- `[%Keys.xxx%]` — Teclas especiales (CONTROL, ESCAPE, TAB, ENTER, etc.)
- `[$encriptado]` — Contraseña desencriptada (use el botón `Encriptar contraseña` para generar)

### Referencia de Comandos

| Acción | Comando | Descripción | Parámetros JSON |
|--------|---------|-------------|-----------------|
| **ir** | `ir={url}` | Navegar a URL | `url` como texto plano dentro de `{}` |
| **clic** | `clic={...}` | Clic en elemento | `"selector"` (CSS/XPath), `"tipo"` (css/xpath) |
| **doble clic** | `doble clic={...}` | Doble clic en elemento | `"selector"`, `"tipo"` |
| **clic derecho** | `clic derecho={...}` | Clic derecho en elemento | `"selector"`, `"tipo"` |
| **escribir** | `escribir={...}` | Escribir texto en elemento | `"selector"`, `"tipo"`, `"texto"` |
| **esperar** | `esperar={...}` | Esperar a que aparezca elemento | `"selector"`, `"tipo"` (predeterminado: `body`) |
| **pausa** | `pausa={...}` | Pausar ejecución | `"tiempo"` (ej: `5s`, `100S`, `2m`, `1h`, `1d`) |
| **desplazar** | `desplazar={...}` | Desplazar página o elemento | `"x"`, `"y"`, `"selector"` (opcional) |
| **asignar** | `asignar={...}` | Establecer una variable | `"nombre"`/`"var"`, `"valor"` (texto), o `"selector"` + `"tipo"` (extrae texto), o `"script"` (resultado JavaScript) |
| **navegador** | `navegador={...}` | Cambiar de navegador | tipo de navegador como texto plano: `CHROME`, `EDGE`, `FIREFOX` |
| **for** | `for={...}` | Bucle iterativo | `"var"`, `"exp"` (rango `{1..10}` o lista separada por espacios) |
| **end** | `end={}` | Fin del bloque iterativo | (sin parámetros) |
| **ubicar puntero raton** | `ubicar puntero raton={...}` | Mover el ratón | `"x"`, `"y"`, `"tipoMovimiento"` (FROM_UL_CORNER / FROM_CNTR_OBJECT / FROM_CUR_LOCATION) |
| **seleccionar opcion** | `seleccionar opcion={...}` | Mostrar diálogo de selección | `"selector"`, `"subselector"`, `"variable"`, `"titulo"`, `"mensaje"`, `"ordenado"` |

### Ejemplos

```
# Navegar a un sitio web
ir={https://ejemplo.com}

# Hacer clic en un botón
clic={"selector":"#btn-enviar"}

# Escribir en un campo de texto
escribir={"selector":"#usuario","texto":"admin"}

# Esperar a que cargue un elemento
esperar={"selector":".tabla-resultados"}

# Pausar por 2 segundos
pausa={"tiempo":"2s"}

# Establecer una variable de texto
asignar={"nombre":"url","valor":"https://ejemplo.com"}

# Extraer texto de un elemento CSS
asignar={"nombre":"profesion","selector":".col-lg-9 > div:nth-child(1) > div:nth-child(2) > span"}

# Extraer texto con XPath
asignar={"nombre":"titulo","selector":"//h1","tipo":"xpath"}

# Establecer variable ejecutando JavaScript (SPA React)
asignar={"nombre":"precio","script":"return window.__APP_INITIAL_STATE__.productData.priceInfo.regularPrice;"}

# Usar una variable
ir={[:url]}

# Desplazar 100 píxeles hacia abajo
desplazar={"x":0,"y":100}

# Doble clic en un elemento
doble clic={"selector":"#item1"}

# Clic derecho
clic derecho={"selector":"#menu-contextual"}

# Bucle del 1 al 5
for={"var":"i","exp":"{1..5}"}
    clic={"selector":"#fila-[:i]"}
end={}

# Bucle sobre una lista
for={"var":"nombre","exp":"Ana Juan Carlos"}
    escribir={"selector":"#nombre","texto":"[:nombre]"}
end={}

# Iterar sobre elementos y extraer atributos href
for={"var":"producto","selector":"div.owned-brands__container div.card a"}
	ir={[:producto:href]}
end={}

# Cambiar de navegador
navegador={FIREFOX}

# Seleccionar una opción de una lista
seleccionar opcion={
    "selector":"div.opciones",
    "subselector":"a",
    "variable":"seleccionado",
    "titulo":"Seleccione una opción",
    "mensaje":"Por favor elija:"
}

# Mover el ratón a coordenadas
ubicar puntero raton={"x":100,"y":200}

# Contraseña encriptada
escribir={"selector":"#pass","texto":"[$AbCdEf12345]"}
```

### Navegadores Soportados
- `CHROME` — Google Chrome
- `EDGE` — Microsoft Edge
- `FIREFOX` — Mozilla Firefox
- `INTERNET_EXPLORER` — Internet Explorer
- `SAFARI` — Apple Safari

### Importación de Selenium IDE
Puede importar archivos Selenium IDE (.side) usando el botón "Cargar script de Selenium IDE".

### Encriptación de Contraseñas
Use el botón "Encriptar contraseña" para generar cadenas encriptadas para usar con la sintaxis `[$...]`.

---

## 中文

### 概述
WebAppTester 执行包含浏览器控制命令的脚本文件。  
命令格式：`操作={json参数}`

### 脚本文件格式
- 以 `#` 开头的行为注释。
- 空行将被忽略。
- 多行命令会合并直到形成有效的 JSON。
- 文件从 `scripts/` 文件夹读取。

### 变量
- `[:变量名]` — 运行时变量（通过设置命令创建）
- `[:变量名:文字]` — 解析为文本的变量（默认）
- `[:变量名:路径]` — 解析为 CSS/XPath 路径的变量
- `[:变量名:href]` — 解析为元素 `href` 属性的变量（来自带选择器的 `循环`）
- `[%Keys.xxx%]` — 特殊按键（CONTROL、ESCAPE、TAB、ENTER 等）
- `[$加密文本]` — 解密后的密码（使用加密密码按钮生成）

### 命令参考

| 操作 | 命令 | 描述 | JSON 参数 |
|------|------|------|-----------|
| **访问** | `访问={网址}` | 导航到网址 | 网址为 `{}` 内的纯文本 |
| **单击** | `单击={...}` | 单击元素 | `"选择器"` (CSS/XPath), `"类型"` (css/xpath) |
| **双击** | `双击={...}` | 双击元素 | `"选择器"`, `"类型"` |
| **右击** | `右击={...}` | 右键单击元素 | `"选择器"`, `"类型"` |
| **写入** | `写入={...}` | 在元素中输入文本 | `"选择器"`, `"类型"`, `"文本"` |
| **等待** | `等待={...}` | 等待元素出现 | `"选择器"`, `"类型"` (默认: `body`) |
| **暂停** | `暂停={...}` | 暂停执行 | `"时间"` (例如: `5s`, `100S`, `2m`, `1h`, `1d`) |
| **滚动** | `滚动={...}` | 滚动页面或元素 | `"x"`, `"y"`, `"选择器"` (可选) |
| **设置** | `设置={...}` | 设置变量 | `"名称"`/`"变量"`, `"值"` (文本), 或 `"选择器"` + `"类型"` (提取文本), 或 `"脚本"` (JavaScript 结果) |
| **浏览器** | `浏览器={...}` | 切换浏览器 | 浏览器类型为纯文本: `CHROME`, `EDGE`, `FIREFOX` |
| **循环** | `循环={...}` | 迭代循环 | `"变量"`, `"表达式"` (范围 `{1..10}` 或空格分隔列表) |
| **结束** | `结束={}` | 结束迭代块 | (无参数) |
| **移动鼠标** | `移动鼠标={...}` | 移动鼠标 | `"x"`, `"y"`, `"偏移类型"` (FROM_UL_CORNER / FROM_CNTR_OBJECT / FROM_CUR_LOCATION) |
| **选择选项** | `选择选项={...}` | 显示选择对话框 | `"选择器"`, `"子选择器"`, `"变量"`, `"标题"`, `"消息"`, `"排序"` |

### 示例

```
# 导航到网站
访问={https://example.com}

# 单击按钮
单击={"选择器":"#submit-btn"}

# 在文本框中输入
写入={"选择器":"#username","文本":"admin"}

# 等待元素加载
等待={"选择器":".result-table"}

# 暂停 2 秒
暂停={"时间":"2s"}

# 设置字符串变量
设置={"名称":"url","值":"https://example.com"}

# 从 CSS 元素提取文本
设置={"名称":"profesion","选择器":".col-lg-9 > div:nth-child(1) > div:nth-child(2) > span"}

# 使用 XPath 提取文本
设置={"名称":"标题","选择器":"//h1","类型":"xpath"}

# 通过执行 JavaScript 设置变量 (React SPA)
设置={"名称":"价格","脚本":"return window.__APP_INITIAL_STATE__.productData.priceInfo.regularPrice;"}

# 使用变量
访问={[:url]}

# 向下滚动 100 像素
滚动={"x":0,"y":100}

# 双击元素
双击={"选择器":"#item1"}

# 右键单击
右击={"选择器":"#context-menu"}

# 从 1 循环到 5
循环={"变量":"i","表达式":"{1..5}"}
    单击={"选择器":"#row-[:i]"}
结束={}

# 遍历列表
循环={"变量":"name","表达式":"Alice Bob Charlie"}
    写入={"选择器":"#name","文本":"[:name]"}
结束={}

# 遍历元素并提取 href 属性
循环={"变量":"产品","选择器":"div.owned-brands__container div.card a"}
    访问={[:产品:href]}
结束={}

# 切换浏览器
浏览器={FIREFOX}

# 从列表中选择选项
选择选项={
    "选择器":"div.options",
    "子选择器":"a",
    "变量":"selected",
    "标题":"选择选项",
    "消息":"请选择："
}

# 将鼠标移动到坐标
移动鼠标={"x":100,"y":200}

# 加密密码
写入={"选择器":"#pass","文本":"[$AbCdEf12345]"}
```

### 支持的浏览器
- `CHROME` — 谷歌浏览器
- `EDGE` — 微软 Edge
- `FIREFOX` — 火狐浏览器
- `INTERNET_EXPLORER` — 互联网浏览器
- `SAFARI` — 苹果 Safari

### Selenium IDE 导入
您可以使用"加载 Selenium IDE 脚本"按钮导入 Selenium IDE（.side）文件。

### 密码加密
使用"加密密码"按钮生成加密字符串，用于 `[$...]` 语法。
