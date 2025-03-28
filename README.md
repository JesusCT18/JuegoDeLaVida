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

El proyecto se estructura en dos clases principales:

1.  **`Celula.java`:** Define la entidad fundamental del juego, la célula.
    * Contiene un atributo privado `viva` de tipo `boolean` que representa el estado actual de la célula (viva o muerta).
    * Proporciona un constructor para inicializar el estado de una célula.
    * Incluye métodos getter (`isViva()`) y setter (`setViva(boolean viva)`) para acceder y modificar el estado de la célula, respectivamente.

2.  **`Generacion.java`:** Contiene la lógica principal para la gestión del tablero de células y la evolución de las generaciones.
    * Utiliza una matriz bidimensional (`Celula[][] celulas`) para representar la cuadrícula del juego.
    * Implementa métodos para:
        * **Creación inicial del tablero (`creaMatriz()`):** Genera una matriz de un tamaño especificado, con un porcentaje configurable de células iniciales vivas distribuidas aleatoriamente. También incluye validaciones para el tamaño de la matriz y el porcentaje de células vivas.
        * **Visualización del tablero (`mostrarMatriz()`):** Muestra el estado actual de la matriz en la consola, representando las células vivas con "1" y las muertas con "0". Esto implica recorrer la matriz bidimensional, accediendo al estado de cada objeto `Celula`.
        * **Conteo de vecinos vivos (`contarCelulasVivas()`):** Dada una célula en una posición específica, este método itera a través de sus ocho vecinos adyacentes en la matriz, verificando sus estados mediante el método `isViva()` y retornando el número total de vecinos vivos. Este proceso requiere el manejo de los límites de la matriz para evitar errores de acceso.
        * **Generación de la siguiente generación (`nuevaGeneracion()`):** Este método implementa las reglas del Juego de la Vida. Recorre cada célula de la matriz actual, utiliza `contarCelulasVivas()` para determinar su vecindario, y crea una nueva matriz con el estado de cada célula en la siguiente generación según las reglas definidas.
        * **Registro de la población (`registroCelulas()`):** Cuenta el número de células vivas y muertas en la generación actual recorriendo la matriz y utilizando el método `isViva()`. Almacena esta información junto con el número de generación en una lista (`registroGeneraciones`) y la muestra al usuario.
        * **Colocación manual de células (`colocacionManual()`):** Permite al usuario definir el estado inicial de cada célula en la matriz a través de cuadros de diálogo, ofreciendo una alternativa a la generación aleatoria.
        * **Menú de control de generaciones (`menuGeneraciones()`):** Proporciona una interfaz de usuario básica para avanzar a la siguiente generación, mostrar el registro de poblaciones y salir del programa. Este menú gestiona la interacción del usuario y la secuencia de las operaciones del juego.

## Aspectos de Desarrollo y Colaboración

Este proyecto ha proporcionado una valiosa experiencia en diversos aspectos del desarrollo de software:

* **Organización de Proyectos:** La separación de la lógica del juego en dos clases distintas (`Celula` y `Generacion`) promueve una mejor organización y modularidad del código, facilitando su comprensión y mantenimiento.
* **Recorrido de Matrices:** La manipulación del tablero del Juego de la Vida implica el uso intensivo de bucles anidados para recorrer y procesar cada elemento de la matriz bidimensional que representa las células.
* **Colaboración en Equipo (Consideraciones para el futuro):** Aunque este README se presenta de forma individual, proyectos de esta naturaleza son ideales para la colaboración en equipo. El uso de plataformas de control de versiones como **GitHub** permite a múltiples desarrolladores trabajar en el mismo código de manera simultánea, gestionando los cambios y resolviendo conflictos de forma eficiente.
* **Uso de GitHub:** GitHub facilita la creación de un repositorio centralizado donde el código del proyecto se almacena y se pueden rastrear las modificaciones. Permite la creación de ramas para el desarrollo de nuevas funcionalidades o la corrección de errores sin afectar la versión principal del código.
* **Colaboración entre dos personas:** Al trabajar en equipo, es fundamental establecer una comunicación clara y definir roles y responsabilidades para cada miembro. La planificación de las tareas, la revisión del código por pares y la realización de pruebas conjuntas son prácticas importantes para asegurar la calidad del proyecto. GitHub proporciona herramientas para la revisión de código (Pull Requests) y la discusión sobre los cambios propuestos.

## Conclusiones

El desarrollo de esta implementación del Juego de la Vida ha sido un ejercicio práctico significativo que ha permitido aplicar y consolidar conocimientos fundamentales de programación orientada a objetos en Java, incluyendo la creación de clases, la gestión de estructuras de datos como matrices, la implementación de algoritmos para la simulación y la interacción básica con el usuario a través de la interfaz gráfica de Swing. A través de este proceso, se han abordado conceptos esenciales para el desarrollo de aplicaciones más complejas y se han sentado las bases para futuras colaboraciones en proyectos de mayor envergadura utilizando herramientas de gestión de código como GitHub.