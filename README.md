# Informe y Documentación del Proyecto de Laberintos

## Carátula o Datos Generales

**Universidad:** Nombre de la Universidad  
**Carrera:** Nombre de la Carrera  
**Materia:** Nombre de la Materia  
**Integrantes:**

- **Nombre del Integrante 1**
  - Correo Institucional: [integrante1@universidad.edu](mailto:integrante1@universidad.edu)
- **Nombre del Integrante 2**
  - Correo Institucional: [integrante2@universidad.edu](mailto:integrante2@universidad.edu)
- **Nombre del Integrante 3**
  - Correo Institucional: [integrante3@universidad.edu](mailto:integrante3@universidad.edu)

## Descripción del Problema

El problema consiste en diseñar e implementar una aplicación que permita resolver laberintos utilizando diferentes algoritmos de búsqueda. La aplicación debe permitir al usuario generar laberintos de diferentes tamaños, marcar celdas como transitables o no transitables, y definir puntos de inicio y fin. Además, debe comparar el desempeño de distintos algoritmos en términos de tiempo de ejecución y eficiencia en encontrar el camino más corto.

## Propuesta de Solución

### Marco Teórico

**Programación Dinámica:**  
La programación dinámica es una técnica de optimización que resuelve problemas almacenando los resultados de subproblemas para evitar cálculos redundantes. Es especialmente útil en problemas de optimización y búsqueda de caminos.

**BFS (Breadth-First Search):**  
El algoritmo de búsqueda en anchura explora todos los nodos de un nivel antes de pasar al siguiente. Es útil para encontrar el camino más corto en grafos no ponderados.

**DFS (Depth-First Search):**  
El algoritmo de búsqueda en profundidad explora lo más lejos posible a lo largo de cada rama antes de retroceder. Es útil para recorrer todos los nodos de un grafo, aunque no garantiza encontrar el camino más corto en todos los casos.

### Descripción de la Propuesta de Solución

Para resolver el problema, se implementó una aplicación en Java utilizando Swing para la interfaz gráfica y algoritmos de búsqueda para resolver el laberinto. Las herramientas y lenguajes utilizados fueron:

- **Lenguaje:** Java
- **Interfaz Gráfica:** Swing
- **Algoritmos:** Recursivo Simple, Programación Dinámica, BFS, DFS

Cada integrante del equipo propuso diferentes criterios y enfoques para la implementación de los algoritmos, los cuales se describen a continuación:

### Criterio por Integrante

- **Integrante 1:**
  - Propuso utilizar BFS debido a su eficiencia en encontrar el camino más corto en laberintos no ponderados.
  - Implementó la generación de laberintos y la visualización de la interfaz gráfica.

- **Integrante 2:**
  - Sugerió el uso de DFS para explorar completamente el laberinto y comparar su eficiencia con otros métodos.
  - Implementó la lógica de movimiento del personaje y la animación.

- **Integrante 3:**
  - Propuso la programación dinámica para optimizar la búsqueda de caminos en laberintos grandes y complejos.
  - Se encargó de la integración de los algoritmos y la medición del tiempo de ejecución.

### Capturas de la Implementación Final de la UI

![Captura 1](ruta/a/la/captura1.png)
*Descripción de la captura 1*

![Captura 2](ruta/a/la/captura2.png)
*Descripción de la captura 2*

![Captura 3](ruta/a/la/captura3.png)
*Descripción de la captura 3*

## Conclusiones

Entre los métodos analizados, el BFS demostró ser la mejor opción para encontrar el camino más corto en laberintos no ponderados. Su capacidad para explorar todos los nodos de un nivel antes de pasar al siguiente garantiza la obtención del camino más corto, aunque puede requerir más memoria que DFS. La programación dinámica también fue eficiente, pero su implementación puede ser más compleja y no siempre necesaria para laberintos pequeños.

## Consideraciones

### Futuras Mejoras

- **Integrante 1:**
  - Implementar algoritmos de búsqueda heurísticos como A* para mejorar la eficiencia en laberintos grandes.

- **Integrante 2:**
  - Añadir la capacidad de guardar y cargar laberintos desde archivos.

- **Integrante 3:**
  - Integrar una funcionalidad de generación de laberintos aleatorios más compleja, incluyendo laberintos ponderados.

### Aplicaciones

- **Integrante 1:**
  - Aplicación en la robótica para la navegación autónoma de robots en entornos complejos.

- **Integrante 2:**
  - Uso en videojuegos para generar niveles de manera procedimental y mejorar la jugabilidad.

- **Integrante 3:**
  - Aplicación en la optimización de rutas de entrega en logística, minimizando tiempos de desplazamiento y costos.
