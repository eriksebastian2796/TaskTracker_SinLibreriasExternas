TaskTracker(Sin Librerías Externas)

Este proyecto es un rastreador de tareas desarrollado en Java , sin utilizar librerías externas. Está diseñado para ejecutarse por consola y permite gestionar tareas de forma sencilla y estructurada.

Funcionalidades principales

- Agregar tareas con nombre, descripción, prioridad y estado.
- Eliminar tareas por ID.
- Buscar tareas por ID.
- Modificar el estado de una tarea (En proceso, Terminada, Cancelada).
- Persistencia de datos en archivo JSON.
- Organización modular con clases como `Tarea`, `GestorTareas`, `Estado`, `Prioridad`.

Estructura del proyecto

TaskTracker_SinLibreriasExternas/ ├── src/ │ └── main/ │ └── java/ │ └── com/erik/tasktracker/ │ ├── Main.java │ ├── Tarea.java │ ├── GestorTareas.java │ ├── Estado.java │ └── Prioridad.java ├── pom.xml ├── .gitignore └── README.md
