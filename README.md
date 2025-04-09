# Juego de la Vida - Simulación Celular

Este proyecto presenta una implementación del clásico "Juego de la Vida", desarrollado por Jesús Collado y Carlos Torralvo, como parte de su formación en 1º de Desarrollo de Aplicaciones Web (DAW) en el IES Mar de Alborán.



## Descripción del Proyecto

El Juego de la Vida es un autómata celular bidimensional ideado por el matemático británico John Horton Conway en 1970. El universo del juego es una cuadrícula ortogonal infinita de células, cada una de las cuales se encuentra en uno de dos posibles estados: viva o muerta. Cada célula interactúa con sus ocho vecinos (las células adyacentes horizontal, vertical y diagonalmente). En cada paso en el tiempo, las siguientes transiciones ocurren basadas en el estado de los vecinos:

1.  Una célula viva con menos de dos vecinos vivos muere por subpoblación.
2.  Una célula viva con dos o tres vecinos vivos vive para la siguiente generación.
3.  Una célula viva con más de tres vecinos vivos muere por superpoblación.
4.  Una célula muerta con exactamente tres vecinos vivos se convierte en una célula viva por reproducción.

El estado inicial del "universo" constituye la "semilla" del patrón. La primera generación se crea aplicando simultáneamente las reglas anteriores a cada célula de la semilla; nacimientos y muertes ocurren simultáneamente, y el momento discreto en el que esto sucede a menudo se denomina un "tick". Cada generación es una función pura de la generación anterior.



## Componentes del Proyecto

El proyecto se estructura en las siguientes clases principales:

1.  **`Celula.java`:** Define cómo es cada célula individual en el juego.
    * Tiene un atributo (`viva`) que indica si la célula está viva o muerta.
    * Permite crear una célula con un estado inicial.
    * Ofrece formas de saber si está viva (`isViva()`) y de cambiar su estado (`setViva()`).

2.  **`Fichero.java`:** Se encarga de guardar y cargar el estado del juego en un archivo.
    * **`guardarPartida()`:** Toma el estado actual de las células, el número de generación y el registro de generaciones, y los escribe en un archivo para poder continuar la partida más tarde.
    * **`cargarPartida()`:** Lee la información de un archivo guardado para restaurar el juego a ese punto, incluyendo el estado de las células y el número de generación.
    * **`PartidaCargada`:** Una forma de organizar la información que se carga desde un archivo (las células, la generación y el registro).

3.  **`Generacion.java`:** Contiene la lógica principal del juego, como crear el tablero, mostrarlo y calcular la siguiente generación.
    * **`celulas`:** Una cuadrícula (matriz) donde se guardan todas las células del juego.
    * **`creaMatriz()`:** Crea el tablero inicial con un tamaño dado y un porcentaje de células vivas colocadas al azar. También permite crear un tablero vacío si se elige la colocación manual.
    * **`mostrarMatriz()`:** Muestra el estado actual del tablero en la pantalla, usando "1" para las células vivas y "0" para las muertas.
    * **`contarCelulasVivas()`:** Para una célula en una posición específica, cuenta cuántas de sus vecinas (hasta 8 alrededor) están vivas.
    * **`nuevaGeneracion()`:** Aplica las reglas del Juego de la Vida a cada célula del tablero basándose en el número de vecinos vivos, creando así la siguiente generación del juego.
    * **`registroCelulas()`:** Cuenta cuántas células vivas y muertas hay en la generación actual y guarda esta información junto con el número de generación.
    * **`colocacionManual()`:** Permite al jugador decidir si cada célula del tablero inicial estará viva o muerta.
    * **`menuGeneraciones()`:** Muestra un menú para que el jugador pueda avanzar a la siguiente generación, ver el registro de las células, guardar la partida o salir del juego. También detecta si el juego entra en un patrón repetitivo.
    * **`compararMatrices()`:** Compara dos tableros de células para ver si son idénticos, lo que se usa para detectar patrones repetitivos.

4.  **`Main.java`:** Es el punto de inicio del programa.
    * **`main()`:** La función principal que se ejecuta al iniciar el programa. Llama a `mostrarMenu()`.
    * **`mostrarMenu()`:** Muestra el menú principal con opciones para cargar una partida existente, iniciar un nuevo juego o salir.
    * **`cargarPartida()`:** Permite al usuario ingresar el nombre de un archivo para cargar una partida guardada, utilizando la funcionalidad de `Fichero.java`.
    * **`iniciarJuego()`:** Pregunta al usuario si quiere colocar las células manualmente o de forma aleatoria, y luego inicia el menú principal del juego (`menuGeneraciones()` en `Generacion.java`).



## Flujo del Programa

1.  El programa comienza en la clase `Main` con la función `main()`, que muestra el menú principal.
2.  Desde el menú principal, el usuario puede:
    * **Cargar Partida:** Llama a la función `cargarPartida()` en `Main`, que a su vez usa la función `cargarPartida()` en `Fichero.java` para leer un archivo y cargar el estado del juego en las clases `Celula` y `Generacion`. Luego se muestra el menú de generaciones.
    * **Iniciar Juego:** Llama a la función `iniciarJuego()` en `Main`, que permite configurar el tablero inicial (manual o aleatorio usando funciones en `Generacion.java`). Una vez creado el tablero inicial, se muestra el menú de generaciones.
3.  El menú de generaciones (`menuGeneraciones()` en `Generacion.java`) permite al usuario:
    * **Avanzar Generación:** Llama a la función `nuevaGeneracion()` en `Generacion.java` para calcular el siguiente estado del tablero. También se usa `registroCelulas()` para guardar la información de la generación actual.
    * **Mostrar Registros:** Muestra la historia de cuántas células vivas y muertas ha habido en cada generación, almacenada en `Generacion.java`.
    * **Guardar Partida:** Llama a la función `guardarPartida()` en `Fichero.java` para guardar el estado actual del juego en un archivo.
    * **Salir:** Termina el programa.



## Aspectos de Desarrollo y Colaboración

Este proyecto ha proporcionado una valiosa experiencia en diversos aspectos del desarrollo de software:

* **Organización de Proyectos:** La separación de la lógica del juego en dos clases distintas (`Celula` y `Generacion`) promueve una mejor organización y modularidad del código, facilitando su comprensión y mantenimiento.
* **Recorrido de Matrices:** La manipulación del tablero del Juego de la Vida implica el uso intensivo de bucles anidados para recorrer y procesar cada elemento de la matriz bidimensional que representa las células.
* **Colaboración en Equipo (Consideraciones para el futuro):** Aunque este README se presenta de forma individual, proyectos de esta naturaleza son ideales para la colaboración en equipo. El uso de plataformas de control de versiones como **GitHub** permite a múltiples desarrolladores trabajar en el mismo código de manera simultánea, gestionando los cambios y resolviendo conflictos de forma eficiente.
* **Uso de GitHub:** GitHub facilita la creación de un repositorio centralizado donde el código del proyecto se almacena y se pueden rastrear las modificaciones. Permite la creación de ramas para el desarrollo de nuevas funcionalidades o la corrección de errores sin afectar la versión principal del código.
* **Colaboración entre dos personas:** Al trabajar en equipo, es fundamental establecer una comunicación clara y definir roles y responsabilidades para cada miembro. La planificación de las tareas, la revisión del código por pares y la realización de pruebas conjuntas son prácticas importantes para asegurar la calidad del proyecto. GitHub proporciona herramientas para la revisión de código (Pull Requests) y la discusión sobre los cambios propuestos.



## Nuestras conclusiones

Este proyecto demuestra cómo se pueden usar clases y objetos en Java para simular un sistema complejo a partir de reglas simples. La clase `Celula` representa los componentes básicos, `Generacion` contiene la lógica de cómo interactúan estas células a lo largo del tiempo, y `Fichero` permite persistir el estado del juego. La clase `Main` actúa como el punto de entrada y gestiona la interacción principal con el usuario. Entender cómo estas clases trabajan juntas es clave para comprender la funcionalidad completa del Juego de la Vida en esta implementación.

El desarrollo de esta implementación del Juego de la Vida ha sido un ejercicio práctico significativo que ha permitido aplicar y consolidar conocimientos fundamentales de programación orientada a objetos en Java, incluyendo la creación de clases, la gestión de estructuras de datos como matrices, la implementación de algoritmos para la simulación y la interacción básica con el usuario a través de la interfaz gráfica de Swing. A través de este proceso, se han abordado conceptos esenciales para el desarrollo de aplicaciones más complejas y se han sentado las bases para futuras colaboraciones en proyectos de mayor envergadura utilizando herramientas de gestión de código como GitHub.