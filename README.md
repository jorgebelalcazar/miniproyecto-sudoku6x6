# 🧩 Sudoku 6x6 — Mini Proyecto #2

> **Fundamentos de Programación Orientada a Eventos (750014C)**  
> Universidad del Valle — Periodo 2026-1

Implementación del juego clásico Sudoku adaptado a una cuadrícula de **6x6**, desarrollado en **Java + JavaFX** siguiendo la arquitectura **Modelo-Vista-Controlador (MVC)** y los principios de la **programación orientada a eventos**.

---

## 📋 Tabla de contenidos

- [Descripción del proyecto](#-descripción-del-proyecto)
- [Características principales](#-características-principales)
- [Requisitos del sistema](#-requisitos-del-sistema)
- [Instrucciones de instalación](#-instrucciones-de-instalación)
- [Cómo jugar](#-cómo-jugar)
- [Arquitectura del proyecto](#-arquitectura-del-proyecto)
- [Estructuras de datos utilizadas](#-estructuras-de-datos-utilizadas)
- [Heurísticas de usabilidad aplicadas](#-heurísticas-de-usabilidad-aplicadas)
- [Documentación del código](#-documentación-del-código)
- [Tecnologías utilizadas](#-tecnologías-utilizadas)
- [Autores](#-autores)

---

## 📖 Descripción del proyecto

Sudoku 6x6 es una aplicación de escritorio que implementa el clásico juego de lógica numérica. El tablero consta de **36 casillas** organizadas en **6 filas, 6 columnas y 6 bloques de 2x3**. El jugador debe completar el tablero respetando las tres reglas fundamentales del Sudoku:

1. Cada **fila** debe contener los números del 1 al 6 sin repetir.
2. Cada **columna** debe contener los números del 1 al 6 sin repetir.
3. Cada **bloque de 2x3** debe contener los números del 1 al 6 sin repetir.

Cada nueva partida genera un tablero **único y aleatorio**, con 12 pistas iniciales (2 por bloque) que el jugador no puede modificar.

---

## ✨ Características principales

### 🎮 Funcionalidades de juego

- **Tablero dinámico**: cada partida es diferente gracias al algoritmo de backtracking.
- **Pistas fijas**: 2 números pre-llenados por bloque, claramente identificados con un color especial.
- **Validación en tiempo real**: las celdas que violan las reglas se resaltan automáticamente con borde rojo.
- **Sistema de ayuda inteligente**: el botón "Ayuda" sugiere un número válido para una celda vacía, destacándola visualmente.
- **Detección automática de victoria**: cuando el tablero está completo y correcto, se muestra un mensaje de felicitación.

### ⌨️ Interacción del usuario

- **Entrada por teclado**: solo se permiten números del 1 al 6 (filtro de entrada activo).
- **Eventos de mouse**: selección de celdas con clic.
- **Navegación con flechas**: las teclas direccionales (↑ ↓ ← →) permiten desplazarse entre celdas editables saltando las pistas fijas.
- **Borrado de números**: se pueden eliminar valores con `Backspace` o `Delete`.

### 🎨 Diseño visual

- Interfaz limpia y profesional con paleta de colores inspirada en Material Design.
- Bordes diferenciados para resaltar los bloques 2x3.
- Estados visuales claros: celda normal, celda fija, celda con error, celda con sugerencia.
- Feedback visual mediante mensajes de estado con colores semánticos (verde para éxito, rojo para errores).

---

## 💻 Requisitos del sistema

- **Java**: JDK 17 o superior
- **Maven**: 3.6 o superior (para la gestión de dependencias)
- **Sistema operativo**: Windows, macOS o Linux
- **Memoria**: 512 MB mínimo

---

## ⚙️ Instrucciones de instalación

### **Opción 1: Ejecutar desde IntelliJ IDEA**

1. Clona el repositorio:
```bash
   git clone https://github.com/jorgebelalcazar/miniproyecto-sudoku6x6.git
```
2. Abre el proyecto en IntelliJ IDEA.
3. Espera a que Maven descargue las dependencias automáticamente.
4. Ejecuta la clase `Main.java` ubicada en `src/main/java/com/example/miniproyectosudoku6x6/`.

### **Opción 2: Ejecutar desde la línea de comandos**

1. Clona el repositorio y entra a la carpeta:
```bash
   git clone https://github.com/jorgebelalcazar/miniproyecto-sudoku6x6.git
   cd miniproyecto-sudoku6x6
```
2. Compila y ejecuta con Maven:
```bash
   mvn clean javafx:run
```

---

## 🎯 Cómo jugar

1. Al iniciar la aplicación, se genera automáticamente un nuevo tablero.
2. Haz **clic** en una celda vacía para seleccionarla, o navega con las **flechas del teclado**.
3. **Escribe un número del 1 al 6** en la celda seleccionada.
4. Si el número viola una regla, la celda se resaltará en **rojo** automáticamente.
5. Si necesitas ayuda, pulsa el botón **"Ayuda"** para recibir una sugerencia.
6. Completa el tablero respetando las tres reglas del Sudoku.
7. Cuando termines correctamente, recibirás un mensaje de felicitación.
8. Pulsa **"Nuevo Juego"** para iniciar otra partida con un tablero diferente.

### Atajos de teclado

| Tecla | Acción |
|---|---|
| `1` – `6` | Ingresar número |
| `Backspace` / `Delete` | Borrar el número de la celda |
| `↑ ↓ ← →` | Navegar entre celdas editables |
| `Tab` / `Shift+Tab` | Navegación secuencial |

---

## 🏗️ Arquitectura del proyecto

El proyecto sigue rigurosamente el patrón **Modelo-Vista-Controlador (MVC)**:
src/main/java/com/example/miniproyectosudoku6x6/
├── Main.java                              ← Punto de entrada
│
├── model/                                 ← MODELO (lógica del juego)
│   ├── Celda.java                         ← Representa una celda individual
│   ├── TableroSudoku.java                 ← Tablero completo con sus agrupaciones
│   ├── ValidadorSudoku.java               ← Validación de reglas con HashSets
│   ├── GeneradorSudoku.java               ← Generación con backtracking aleatorio
│   └── ProveedorAyuda.java                ← Sugerencias inteligentes
│
├── controller/                            ← CONTROLADOR (coordinación)
│   ├── SudokuController.java              ← Conecta vista con modelo
│   └── handlers/
│       ├── EscuchadorCambioCelda.java     ← Interface personalizada
│       └── AdaptadorFocoFiltro.java       ← Adaptador de filtrado de teclas
│
└── view/                                  ← VISTA (presentación)
└── FabricaEstilos.java                ← Utilidad de manipulación de estilos
src/main/resources/com/example/miniproyectosudoku6x6/
├── sudoku-view.fxml                       ← Estructura visual (Scene Builder)
└── styles.css                             ← Estilos visuales
docs/javadoc/                              ← Documentación generada

### Principios aplicados

- **Cohesión alta**: cada clase tiene una responsabilidad bien definida.
- **Bajo acoplamiento**: el modelo no conoce JavaFX; la vista no conoce el modelo directamente.
- **Inversión de dependencias**: el controlador media entre las capas.
- **Reutilización**: clases utilitarias (`FabricaEstilos`) y adaptadoras (`AdaptadorFocoFiltro`).

---

## 📊 Estructuras de datos utilizadas

Más allá de los arreglos básicos, el proyecto emplea **estructuras avanzadas** estratégicamente:

| Estructura | Ubicación | Propósito |
|---|---|---|
| **`HashSet<Integer>`** | `ValidadorSudoku` | Detección de duplicados en filas, columnas y bloques en tiempo O(1). |
| **`HashMap<TextField, Celda>`** | `SudokuController` | Mapeo bidireccional entre componentes visuales y celdas del modelo. |
| **`List<List<Celda>>`** | `TableroSudoku` | Agrupaciones lógicas de filas, columnas y bloques. |
| **`Celda[][]`** | `TableroSudoku` | Matriz de objetos personalizados (no enteros). |

### Estructuras orientadas a eventos

- **Interfaces personalizadas**: `EscuchadorCambioCelda`
- **Clases adaptadoras**: `AdaptadorFocoFiltro`
- **Clases internas**: `ManejadorTeclado` (dentro del controlador)
- **Expresiones lambda**: usadas para acciones de botones y propiedades de foco

---

## 🎨 Rubrica de usabilidad aplicadas

Se aplicaron **6 Rubricas** para optimizar la experiencia de usuario:

| #      | Rubrica                            | Implementación |
|--------|------------------------------------|---|
| **R1** | Visibilidad del estado del sistema | Etiqueta de estado dinámica con feedback de cada acción |
| **R2** | Coincidencia con el mundo real     | Lenguaje natural en español ("Nuevo Juego", "Ayuda") |
| **R5** | Prevención de errores              | Filtro de entrada (solo 1-6) + validación en tiempo real |
| **R6** | Reconocer antes que recordar       | Texto de instrucciones permanente bajo el tablero |
| **R7** | Flexibilidad y eficiencia de uso   | Navegación con flechas para usuarios expertos |
| **R8** | Diseño estético y minimalista      | Paleta limpia, jerarquía visual clara, espaciado consistente |

---

## 📚 Documentación del código

Todo el código fuente está documentado en **inglés** siguiendo el estándar **Javadoc**. La documentación HTML se encuentra en: docs/javadoc/index.html

---

## 🛠️ Tecnologías utilizadas

- **[Java SE 17](https://www.oracle.com/java/)** — Lenguaje base
- **[JavaFX 21](https://openjfx.io/)** — Framework gráfico
- **[Scene Builder](https://gluonhq.com/products/scene-builder/)** — Diseño visual del FXML
- **[Maven](https://maven.apache.org/)** — Gestión de dependencias
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/)** — IDE de desarrollo
- **[Git](https://git-scm.com/) + [GitHub](https://github.com/)** — Control de versiones

---

## 👥 Autores

| Nombre                    | Código Estudiantil | Correo                                   |
|---------------------------|--------------------|------------------------------------------|
| **Jorge Iván Belalcázar** | `2374654`          | `jorge.belalcazar@correounivalle.edu.co` |

**Profesor:** Fabian Stiven Valencia Cordoba<br>
**Curso:** Fundamentos de Programación Orientada a Eventos<br>
**Código:** 750014C<br>
**Periodo:** 2026-1

---

## 📄 Licencia

Este proyecto se desarrolla con fines **académicos** como parte del curso *Fundamentos de Programación Orientada a Eventos* de la Universidad del Valle.

---

<p align="center">
  <strong>Universidad del Valle</strong><br>
  Escuela de Ingeniería de Sistemas y Computación<br>
  Cali, Colombia — 2026
</p>