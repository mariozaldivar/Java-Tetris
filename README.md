
<h1>INTRODUCCIÓN: </h1>

Java-Tetris es un proyecto estudiantil para la materia de Lenguajes Orientados a Objetos, en la universidad Anáhuac Mayab. Se desarrolló utilizando la librería de JavaFX, y con el controlador de dependencias Maven. Favor de referirse a la instalación para los requisitos mínimos y troubleshooting. 

El juego fue hecho por: 
Mario Zaldívar
Rodolfo Concha
Arturo Wulfrath
Ariana Cihchilla

Se puede encontrar el archivo .jar en la pestaña de lanzamientos

<h1>CONTROLES POR DEFECTO: </h1>
- Flechas laterales para mover las piezas a la izquierda y derecha.
- Tecla X para girar la pieza en sentido horario. 
- Tecla C para utilizar la Pieza Reserva.
- Tecla ESPACIO para hacer "Hard Drop".
- Tecla ESC para pausar el juego.

<h1>INSTALACIÓN: </h1>
<p>
Compatible con: Windows, Linux. 

  Ejecución posible por medio de archivo .jar, o .exe para Windows.

Requisitos para utilizar el JAR: 
  - Tener la versión de Java más reciente (Java 26)

Con el archivo .jar y Java 26 instalado, debería bastar con ejecutar el archivo de forma normal para correr el juego. 
En caso de que el juego no se ejecute directamente, ejecutar por medio de la terminal por medio del comando `java -jar nombre-del-archivo-descargado.jar`, con el cual se podrán leer los errores en la terminal. Contactar a los desarrolladores en este caso. 

<b>Avertencia para correr el juego con el .exe: </b>El juego puede ser bloqueado por Windows Defender, al no haber sido firmado en su empaquetado. Para jugarlo  de todas maneras dar en "más información", y "Ejecutar de todas formas". 

<h2>Trabajar con el código fuente</h2>
Para trabajar con el código fuente es indispensable tener instalado Maven localmente, de forma que su archivo binario se encuentre en el PATH. 

Por la estructura que deben tener todos los proyectos de Maven, los archivos .java se encuentran en el directorio "src/main/java/Tetris/".

Una vez clonado el repositorio, en la raíz del proyecto utilizar `mvn dependency:resolve`.
Para ejecutar el proyecto utilizar `mvn javafx:run`, o `mvn clean javafx:run` para reinstalar las dependencias y volver a compilar.
Para crear el archivo FAT .jar, utilizar `mvn package`.


<h2>Diagrama UML del proyecto: </h2>
<img src="./Java Tetris UML Diagram.png"> 

<h2> Video explicando la arquitectura del juego </h2> 

[https://drive.google.com/file/d/1h9tTCHcW66iXECjLpZM74kZbQHIQ8TSv/view?usp=sharing]


Requisitos para ejecutar desde el código fuente: 
  - Haber instalado el OpenSDK. 
  - Tener Maven instalado. 
  - Correr por medio de la terminal, dentro del directorio del proyecto 
  `mvn dependency:resolve`
  - Correr utilizando 
`mvn javafx:run`


</p>
