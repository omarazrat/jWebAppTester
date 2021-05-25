# jWebAppTester
## English

WebAppTester V0.0.3: Tester for online forms
This program uses Selenium to open a web browser and run user written commands.

### Installation
You need at least Java version >=11

Download Selenium controllers for your favorite browser (check your browser version)
from https://www.selenium.dev/downloads/ and save it in the same folder with the program.

Once the program is run for the first time, it will create some sample scripts as files you can change with any text editor.
These files are created in the folder *scripts*

### Syntax of the script files

Every line starting with #, is ignored.
This program builds a tree the folder "scripts" and subfolders.
The tree is composed of folders and actions in every txt file.
If there is a file _start.txt, that file will be read and executed before proceeding to any other action in that folder.
If there is a file _end.txt, that file will be read and executed after any other action in that folder.

Folder Tree Examples
[scripts]

scripts/_start.txt

scripts/_end.txt

[scripts/management]

scripts/management/_start.txt

scripts/management/crearpregunta.txt

scripts/management/listarpreguntas.txt


If you request the execution of the command "scripts/management/listarpreguntas", the program will run these 
commands in order:

scripts/_start.txt

scripts/management/_start.txt

scripts/management/listarpreguntas.txt

scripts/_end.txt

The files _start.txt and _end.txt are not mandatory: they are justa a way to automate actions as authentication
prior to work with a page or execute a logout after using a given page

Every action is a set of instructions to be executed by your web browser.
each instruction has the format "action={command}"

[Supported actions]

#### go - Opens a web page. 
Example:

go={https://duckduckgo.com/}

wait - Waits for a page to be completely loaded after a change in the location bar URL, or after clicking any element.
The browser will wait until a given object (given its selector), appear or simply the page to be fully loaded
Examples:

wait={}

wait={
  "selector":"duckbar"
}

#### write - Writes text in a page component given its css selector.
-selector: Required. The css selector of the component to write on. 
 if selector=="", then the action is send to the whole page. e.g.: CTRL+P
-text: the text to write

Example:

write={
 "selector":"search_form_input_homepage",
 "text":"inicio"
}
You can specify special commands (ALT, F1,ENTER, ESCAPE, etc)
using this format:

[%Keys.CONTROL]

The full list of supported constants can be found in https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/Keys.html
If you need to send command combinations, you can write using this format:

[%Keys.CONTROL,Keys.ALT]

You can also add non-commands characters, like this:

[%Keys.CONTROL,Keys.ALT]r

Examples:

write={
 "selector":"search_form_input_homepage",
 "text":"[%Keys.CONTROL,Keys.ALT]r"
}

write={
 "selector":"",
 "text":"[%Keys.CONTROL]p"
}

If you need to write the text "[%", instead write "[%%"

#### click - clickks a componente of the page given a css selector

Example:

click={ 
  "selector":"search_button_homepage"
}

#### double click - Double-clickks any element in the page, given its selector.

Example:

double click={
    "selector": "body"
}

#### right click - Right clickks any element in the page, given its selector.

Example:

right click={
    "selector": "Identificacion"
}

#### pause - Waits a given time, before proceeding with the next instruction.
Format: NUMBER UNITS
Where UNITS can be one of these values: S=milliseconds,s=seconds, m=minutes, h=hours, d=days

Example:

To wait three seconds

pause={"time":"3 s"}

To wait 10 minutes

pause={"time":"10 m"}

#### pick choice - Prompts user to pick a single choice among a list, stores selected
option in a variable.
Properties:
- selector: Required. This selector must match to a collection of HTML elements</ol>
- subselector: Secondary css path, to specify the element of every option to be shown to the user. By default, the text of every element gathered with the "selector"  will be used as option.
- variable: Required. Name of the variable used to store user's selection.
- title: Title for the promtpt window
- message: Message to use in the prompt window.
- sorted: Sort options alphabetically? (yes/no)

Example:

pick choice={
 "selector":"div.central-featured-lang",
 "subselector": "a",
 "sorted":"yes",
 "title":"Wikipedia",
 "message":"Select a language",
 "variable":"Language"
}

#### Variables

Use existing variables inside your instructions writing the variable's name between square brackets
like this: [:VARIABLE_NOMBRE]

Example:

click={
 "selector":"[:Language] > a"
}

 About CSS Selectors: 
 https://www.w3.org/wiki/CSS/Selectors
 https://www.w3schools.com/cssref/css_selectors.asp

### Running Binary version

You can use the running version at https://drive.google.com/file/d/15_aMOJ-0-RtYJfjQWMotJw_NnhOvQ_dW/view?usp=sharing

### Debugging

This program writes all its output to the file WebTester.log

### Upcoming

* Use encripted words for passwords
* Enter special characters with write command: ESC, ENTER, F1,... etc.
* for_each loops
* Mouse Over

## Español
WebAppTester V0.0.3: Probador de formularios online
El programa utiliza Selenium para abrir un navegador web y ejecuta en él, comandos escritos por el usuario.

### Instalación
Descargue e instale Java versión >=11

Descargue el controlador de Selenium para su navegador favorito (recuerde revisar la versión de su navegador) 
desde https://www.selenium.dev/downloads/ y deje el controlador en la misma carpeta en que ejecutará el programa.

Cuando el programa se ejecute por primera vez, creará algunos archivos con comandos de ejemplo en la carpeta *scripts*, que se pueden cambiar con cualquier editor de texto.

### Sintaxis de los archivos de comandos

Cada línea que comienza con #, no se tiene en cuenta y será tratada como comentario.
El programa lee la carpeta "scripts" y con todas las carpetas en su interior arma un árbol.
El árbol se compone de carpetas y de acciones representadas en cada uno de los archivos con extension .txt en cada carpeta
Si se encuentra un archivo _start.txt en una carpeta, se ejecutará ese archivo antes de ejecutar cualquier otra acción en la carpeta.
Si se encuentra un archivo _end.txt en una carpeta, se ejecutará ese archivo después de ejecutar cualquier acción en la carpeta.
Ejemplo de estructura

[scripts]

scripts/_start.txt

scripts/_end.txt

[scripts/administracion]

scripts/administracion/_start.txt

scripts/administracion/crearpregunta.txt

scripts/administracion/listarpreguntas.txt

Si desde el programa se solicita la ejecución del comando "scripts/administracion/listarpreguntas", el programa ejecutará
los siguientes comandos en orden:

scripts/_start.txt

scripts/administracion/_start.txt

scripts/administracion/listarpreguntas.txt

scripts/_end.txt

Los archivos _start.txt y _end.txt no son obligatorios, son solo una forma de colocar acciones como autenticarse antes de entrar a una 
página en la cual se ejecutará un comando o cerrar sesión después de salir de cierta página

Cada acción es un conjunto de instrucciones que serán ejecutadas por el navegador de internet.
Cada instrucción tiene el formato "acción={comando}"

[Acciones soportadas]

#### ir - Abre una página internet. 
Ejemplo:

ir={https://duckduckgo.com/}

esperar - Espera a que la página cargue completamente despues de un cambio de URL o despues de hacer clic en algun lado.
esperará a que cierto objeto (dado su selector), aparezca o simplemente a que la página cargue completa

Ejemplos:

esperar={}

esperar={

  "selector":"duckbar"
  
}

#### escribir - Escribe texto en un componente de la pagina dado un selector css.
-selector: Obligatorio. El selector del componente en el cual vamos a escribir.
 si el selector es igual a "", entonces la accion se envía directamente a toda 
 la página. p. ej.: el comando CONTROL+P, para imprimir
-texto: Obligatorio. El texto que se va a escribir.
Ejemplo:

escribir={
 "selector":"search_form_input_homepage",
 "texto":"inicio"
}

Puede enviar comandos especiales (ALT, F1,ENTER, ESCAPE, etc)
escribiendolo de la siguiente forma:

[%Keys.CONTROL]

Utilice las constantes definidas en https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/Keys.html

Si requiere enviar combinaciones de comandos, puede escribirlo así:

[%Keys.CONTROL,Keys.ALT]

Además puede agregar otras letras así:

[%Keys.CONTROL,Keys.ALT]r

Ejemplos:
escribir={
 "selector":"search_form_input_homepage",
 "texto":"[%Keys.CONTROL,Keys.ALT]r"
}

escribir={
 "selector":"",
 "texto":"[%Keys.CONTROL]p"
}

Si necesita escribir el texto "[%", sin referirse a ningún comando, escríbalo así:
"[%%"

#### clic - Hace clic en un componente de la pagina dado un selector css.

Ejemplo:

clic={ 
  "selector":"search_button_homepage"
}

#### doble clic - Hace doble clic en algun elemento de la pagina, dado su selector.

Ejemplo:

doble clic={
    "selector": "body"
}

#### clic derecho - Hace clic derecho en cualquier elemento de la pagina, dado su selector.

Ejemplo:

clic derecho={
    "selector": "Identificacion"
}

Cuando ejecute cualquier comando en esta carpeta, también se ejecutará este archivo.

#### pausa - Espera cierto tiempo, antes de continuar con la siguiente instrucción.
Formato: NUMERO UNIDADES
Donde UNIDADES puede tener uno de los siguientes valores: S=milisegundos,s=segundos, m=minutos, h=horas, d=dias

Ejemplos:

Para esperar 3 segundos

pausa={"tiempo":"3 s"}

Para esperar 10 minutos

pausa={"tiempo":"10 m"}

#### seleccionar opcion - Pide al usuario seleccionar una opción de una lista y guarda el valor seleccionado
en una variable.

Propiedades:

- selector: Obligatorio. El selector css que corresponde a los elementos HTML a mostrar</ol>
- subselector: Opcional. Ruta complementaria, para determinar el valor de cada opción que se mostrará al usuario. Por omisiòn se tomará el texto de cada elemento web asociado con la ruta en el parámetro "selector"
- variable: Obligatorio. Nombre de la variable que se va a asignar con el elemento que el usuario seleccione
- titulo: Opcional. Título que tendrá la caja de texto que se va a mostrar al usuario.
- mensaje: Opcional. Mensaje a mostrar en la caja de texto que se va a mostrar al usuario.
- orden alfabetico: Opcional. ordenar alfabèticamente las opciones a mostrar (si/no)

Ejemplo:

seleccionar opcion={
 "selector":"div.central-featured-lang",
 "subselector": "a",
 "orden alfabetico":"yes",
 "titulo":"Wikipedia",
 "mensaje":"Seleccione un lenguaje",
 "variable":"Lenguaje"
}

#### Variables
Utilice variables ya definidas como parte de sus instrucciones colocando el nombre 
de la variable entre los signos [:] así: [:NOMBRE_VARIABLE]
Ejemplo:
clic={
 "selector":"[:Lenguaje] > a"
}

Referencias de Selectores CSS: 

 https://w3.org/wiki/CSS_/_Selectores_CSS
 
 https://www.w3schools.com/cssref/css_selectors.asp

### Versión compilada

Si desea utilizar este programa sin compilar nada, use la versión en https://drive.google.com/file/d/15_aMOJ-0-RtYJfjQWMotJw_NnhOvQ_dW/view?usp=sharing

### Depuración

El programa escribe todo lo que hace en el archivo WebTester.log

### Próximamente

* Soportar palabras encriptadas para contraseñas en los scripts
* Poder definir caracteres especiales con el comando escribir: ESC, ENTER, F1,... etc.
* iteraciones al estilo for_each 
* Implementar el Mouse Over, esa acción de colocar el cursor sobre un objeto.
