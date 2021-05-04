# jWebAppTester
## English
WebAppTester: Tester for online forms
This program uses Selenium to open a web browser and run user written commands.
Download Selenium controllers for your favorite browser (check your browser version)
from https://www.selenium.dev/downloads/ and save it in the same folder with the program.
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

ir - Opens a web page. 
Example:
ir={https://duckduckgo.com/}
esperar - Waits for a page to be completely loaded after a change in the location bar URL, or after clicking any element.
The browser will wait until a given object (given its selector), appear or simply the page to be fully loaded
Examples:
esperar={}
esperar={
  "selector":"duckbar"
}
escribir - Writes text in a page component given its css selector.
-selector: the css selector of the component to write on
-texto: the text to write
Example:
escribir={
 "selector":"search_form_input_homepage",
 "texto":"inicio"
}

clic - Clicks a componente of the page given a css selector
Example:
clic={ 
  "selector":"search_button_homepage"
}
doble clic - Double-clicks any element in the page, given its selector.
Example:
doble clic={
    "selector": "body"
}
clic derecho - Right clicks any element in the page, given its selector.
Example:
clic derecho={
    "selector": "Identificacion"
}

pausa - Waits a given time, before proceeding with the next instruction.
Format: NUMBER UNITS
Where UNITS can be one of these values: S=milliseconds,s=seconds, m=minutes, h=hours, d=days
Example:
To wait three seconds
pausa={"tiempo":"3 s"}
To wait 10 minutes
pausa={"tiempo":"10 m"}

 About CSS Selectors: 
 https://w3.org/wiki/CSS_/_Selectores_CSS
 https://www.w3schools.com/cssref/css_selectors.asp
###Running Binary version
You can use the running version at https://drive.google.com/file/d/15_aMOJ-0-RtYJfjQWMotJw_NnhOvQ_dW/view?usp=sharing

## Español
WebAppTester: Probador de formularios online
El programa utiliza Selenium para abrir un navegador web y ejecuta en él, comandos escritos por el usuario.
Descargue el controlador de Selenium para su navegador favorito (recuerde revisar la versión de su navegador) 
desde https://www.selenium.dev/downloads/ y deje el controlador en la misma carpeta en que ejecutará el programa.
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

ir - Abre una página internet. 
Ejemplo:

ir={https://duckduckgo.com/}

esperar - Espera a que la página cargue completamente despues de un cambio de URL o despues de hacer clic en algun lado.
esperará a que cierto objeto (dado su selector), aparezca o simplemente a que la página cargue completa
Ejemplos:

esperar={}

esperar={

  "selector":"duckbar"
  
}

escribir - Escribe texto en un componente de la pagina dado un selector css.
Ejemplo:

escribir={
 "selector":"search_form_input_homepage",
 "texto":"inicio"
}

clic - Hace clic en un componente de la pagina dado un selector css.
Ejemplo:

clic={ 
  "selector":"search_button_homepage"
}

doble clic - Hace doble clic en algun elemento de la pagina, dado su selector.
Ejemplo:

doble clic={
    "selector": "body"
}

clic derecho - Hace clic derecho en cualquier elemento de la pagina, dado su selector.
Ejemplo:

clic derecho={
    "selector": "Identificacion"
}

Cuando ejecute cualquier comando en esta carpeta, también se ejecutará este archivo.

pausa - Espera cierto tiempo, antes de continuar con la siguiente instrucción.
Formato: NUMERO UNIDADES
Donde UNIDADES puede tener uno de los siguientes valores: S=milisegundos,s=segundos, m=minutos, h=horas, d=dias

Ejemplos:

Para esperar 3 segundos

pausa={"tiempo":"3 s"}

Para esperar 10 minutos

pausa={"tiempo":"10 m"}

Referencias de Selectores CSS: 

 https://w3.org/wiki/CSS_/_Selectores_CSS
 
 https://www.w3schools.com/cssref/css_selectors.asp

###Versión compilada
Si desea utilizar este programa sin compilar nada, use la versión en https://drive.google.com/file/d/15_aMOJ-0-RtYJfjQWMotJw_NnhOvQ_dW/view?usp=sharing
