package com.erik.tasktracker;

import java.time.LocalDate;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        GestorTareas gestorTareas = new GestorTareas();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {

            mostrarMenu();

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Descripcion: ");
                    String descripcion = scanner.nextLine();

                    System.out.println("Prioridad: \n" +
                            "1. Alta.\n" +
                            "2. Media.\n" +
                            "3. Baja.\n: ");

                    int seleccion = scanner.nextInt();
                    scanner.nextLine();
                    Prioridad prioridad = null;

                    switch (seleccion){
                        case 1:
                            prioridad = Prioridad.ALTA;
                            break;
                        case 2:
                            prioridad = Prioridad.MEDIA;
                            break;
                        case 3:
                            prioridad = Prioridad.BAJA;
                    }

                    int nuevoId = gestorTareas.generarIdsAutoincremental();
                    LocalDate ahora = LocalDate.now();

                    Tarea nueva = new Tarea(nuevoId, descripcion, Estado.EN_PROCESO, ahora, ahora, prioridad);
                    gestorTareas.agregarTarea(nueva);
                    System.out.println("Tarea agregada con ID " + nuevoId);
                    break;

                case "2":
                    gestorTareas.mostrarTareas();
                    break;

                case "3":
                    System.out.println("Ingrese el ID de la tarea: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    if (id > gestorTareas.tamanio()) {
                        System.out.println("Tarea no encontrada");
                    } else {
                        Tarea tareaModificar = gestorTareas.buscarTarea(id);

                        System.out.println("Seleccione el estado de la tarea: \n" +
                                "1. En proceso.\n" +
                                "2. Terminado.\n" +
                                "3. Cancelado.");
                        int estado = scanner.nextInt();
                        scanner.nextLine();
                        gestorTareas.editarEstado(tareaModificar,estado);
                        gestorTareas.guardarTareasEnArchivoJson();
                        System.out.println("Tarea modificada correctamente.");
                    }
                    break;

                case "4":
                    System.out.println("Ingrese ID de la tarea a liminar: ");
                    int idEliminar = scanner.nextInt()-1;
                    scanner.nextLine();
                    gestorTareas.eliminarTarea(idEliminar);
                    System.out.println("Tarea eliminada.");
                    break;

                case "5":
                    salir = true;
                    System.out.println("Saliendo del programa...");
                    break;
            }
        }
        scanner.close();
    }

    private static void mostrarMenu () {
        System.out.println("\n--- Menú de opciones ---");
        System.out.println("1. Agregar tarea.\n" +
                "2. Mostrar tareas.\n" +
                "3. Editar estado.\n" +
                "4. Eliminar tarea.\n" +
                "5. Salir");
        System.out.print("Seleccione una opción: ");
    }
}