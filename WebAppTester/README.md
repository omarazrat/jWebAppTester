#WebAppTester: Probador de formularios online
#El programa utiliza Selenium para abrir un navegador web y ejecuta en él, comandos escritos por el usuario.
#Descargue el controlador de Selenium para su navegador favorito (recuerde revisar la versión de su navegador) 
#desde https://www.selenium.dev/downloads/ y deje el controlador en la misma carpeta en que ejecutará el programa.
#Cada lí­nea que comienza con #, no se tiene en cuenta y será tratada como comentario.
#El programa lee la carpeta "Acciones" y con todas las carpetas en su interior arma un árbol.
#El árbol se compone de carpetas y de acciones representadas en cada uno de los archivos con extension .txt en cada carpeta
#Si se encuentra un archivo inicio.txt en una carpeta, se ejecutará ese archivo antes de ejecutar cualquier otra acción en la carpeta.
#Si se encuentra un archivo fin.txt en una carpeta, se ejecutará ese archivo después de ejecutar cualquier acción en la carpeta.
#Ejemplo de estructura
#[Acciones]
#Acciones/inicio.txt
#Acciones/fin.txt
#[Acciones/administracion]
#Acciones/administracion/inicio.txt
#Acciones/administracion/crearpregunta.txt
#Acciones/administracion/listarpreguntas.txt
#
#Si desde el programa se solicita la ejecución del comando "Acciones/administracion/listarpreguntas", el programa ejecutará
#los siguientes comandos en orden:
#Acciones/inicio.txt
#Acciones/administracion/inicio.txt
#Acciones/administracion/listarpreguntas.txt
#Acciones/fin.txt
#
#Los archivos inicio.txt y fin.txt no son obligatorios, son solo una forma de colocar acciones como autenticarse antes de entrar a una 
#página en la cual se ejecutará un comando o cerrar sesión después de salir de cierta página
#
#Cada acción es un conjunto de instrucciones que serán ejecutadas por el navegador de internet.
#Cada instrucción tiene el formato "acción={comando}"
#Descargue el programa ya compilado desde https://drive.google.com/open?id=1p8EWeIFzNU6YNwwH0PtU5ym7FV44aVds
#JRE <= 1.8
#[Acciones soportadas]

#ir - Abre una página internet. 
#Ejemplo:
ir={https://duckduckgo.com/}

#esperar - Espera a que la página cargue completamente despues de un cambio de URL o despues de hacer clic en algun lado.
#esperará a que cierto objeto (dado su selector), aparezca o simplemente a que la página cargue completa
#Ejemplo:
esperar={}

#escribir - Escribe texto en un componente de la pagina dado un selector css.
#Ejemplo:
escribir={
 "selector":"#search_form_input_homepage",
 "texto":"inicio"
}

#clic - Hace clic en un componente de la pagina dado un selector css.
#Ejemplo:
clic={ 
  "selector":"#search_button_homepage"
}

#esperar - Espera a que la página cargue completamente despues de un cambio de URL o despues de hacer clic en algun lado.
#esperará a que cierto objeto (dado su selector), aparezca o simplemente a que la página cargue completa
#Ejemplo:
esperar={
  "selector":"#duckbar"
}

#doble clic - Hace doble clic en algun elemento de la pagina, dado su selector.
#Ejemplo:
doble clic={
    "selector": "body"
}

#clic derecho - Hace clic derecho en cualquier elemento de la pagina, dado su selector.
#Ejemplo:
clic derecho={
    "selector": "body"
}

#Cuando ejecute cualquier comando en esta carpeta, también se ejecutará este archivo.
#
#Referencias de Selectores CSS: 
# https://w3.org/wiki/CSS_/_Selectores_CSS
# https://www.w3schools.com/cssref/css_selectors.asp