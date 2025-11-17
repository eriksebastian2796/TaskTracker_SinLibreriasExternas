    package com.erik.tasktracker;

    import java.io.FileWriter;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;

    public class GestorTareas {

        private final ArrayList<Tarea> tareas;

        public GestorTareas() {
            tareas = new ArrayList<>();
            leerArchivoJson();
        }

        public int generarIdsAutoincremental() {
            return tareas.size() + 1;
        }

        public void agregarTarea(Tarea tarea) {
            tareas.add(tarea);
            guardarTareasEnArchivoJson();
        }

        public void mostrarTareas() {
            if (tareas.isEmpty()) {
                System.out.println("No hay tareas cargadas.");
            }else{
                System.out.println("\nLista de tareas:");
                for (Tarea tarea : tareas) {
                    System.out.println(tarea.toString());

                }
            }
        }

        public void editarEstado (Tarea tareaModificar , int estado){
            if(estado > 0 && estado <4){
                if (estado == 1) {
                    tareaModificar.setEstado(Estado.EN_PROCESO);
                } else if (estado == 2) {
                    tareaModificar.setEstado(Estado.TERMINADO);
                } else {
                    tareaModificar.setEstado(Estado.CANCELADO);
                }
                tareaModificar.setFechaModificacion(LocalDate.now());
            } else {
                System.out.println("Opción inválida");
            }
        }

        public void eliminarTarea(int id){
            Tarea t = buscarTarea(id);
            if (t != null){
                tareas.remove(t);
                guardarTareasEnArchivoJson();
            }else{
                throw new NullPointerException("ID no encontrada o null");
            }
        }

        public int tamanio(){
            return tareas.size();
        }

        public Tarea buscarTarea(int id){
            for (Tarea t : tareas){
                if (t.getId() == id){
                    return t;
                }
            }
            return null;
        }

        private void leerArchivoJson() {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/tasks.json"));
                StringBuilder contenidoJson = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenidoJson.append(linea.trim());
                }
                reader.close();
                String contenidoJsonEnUnaLinea = contenidoJson.toString();

                if(contenidoJsonEnUnaLinea.startsWith("[") && contenidoJsonEnUnaLinea.endsWith("]")){
                    contenidoJsonEnUnaLinea = contenidoJsonEnUnaLinea.substring(1,
                            contenidoJsonEnUnaLinea.length() - 1);
                }
                if (contenidoJsonEnUnaLinea.trim().isEmpty()){
                    return;
                }

                String[] tareasEnBloques = contenidoJsonEnUnaLinea.split("},");
                for (String bloqueTarea : tareasEnBloques) {
                    bloqueTarea = bloqueTarea.trim();
                    if (!bloqueTarea.endsWith("}")) {
                        bloqueTarea += "}";
                    }
                    Tarea tarea = parsearTareaDesdeJson(bloqueTarea);
                    tareas.add(tarea);
                }
                System.out.println("Tareas cargadas: " + tareas.size());
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }

        public void guardarTareasEnArchivoJson(){

            try(FileWriter archivoJson = new FileWriter("src/main/resources/tasks.json")){
                archivoJson.write("[\n");
                for (int i = 0 ; i < tareas.size(); i++) {
                    Tarea tarea = tareas.get(i);
                    String json = "  {\n" +
                            "    \"id\": " + tarea.getId() + ",\n" +
                            "    \"descripcion\": \"" + tarea.getDescripcion() + "\",\n" +
                            "    \"estado\": \"" + tarea.getEstado() + "\",\n" +
                            "    \"fechaCreacion\": \"" + tarea.getFechaCreacion() + "\",\n" +
                            "    \"fechaModificacion\": \"" + tarea.getFechaModificacion() + "\",\n" +
                            "    \"prioridad\": \"" + tarea.getPrioridad() + "\"\n" +
                            "  }";
                    if (i < tareas.size()-1){
                        json += ",";
                    }
                    archivoJson.write(json + "\n");
                }
                archivoJson.write("]");
            } catch (IOException e) {
                System.out.println("Error al guardar tareas: " + e.getMessage());
            }
        }

        private Tarea parsearTareaDesdeJson(String bloqueDeTarea) {
            int inicioId = indiceInicio(bloqueDeTarea , "id");
            int finId = indiceFinal(bloqueDeTarea, inicioId);
            int id = Integer.parseInt(bloqueDeTarea.substring(inicioId, finId).trim());

            int inicioDesc = indiceInicio(bloqueDeTarea , "descripcion");
            int finDesc = indiceFinal(bloqueDeTarea, inicioDesc);
            String descripcion = quitarComillas(bloqueDeTarea.substring(inicioDesc, finDesc));

            int inicioEstado = indiceInicio(bloqueDeTarea , "estado");
            int finEstado = indiceFinal(bloqueDeTarea, inicioEstado);
            String estado = quitarComillas(bloqueDeTarea.substring(inicioEstado, finEstado));

            int inicioFC = indiceInicio(bloqueDeTarea , "fechaCreacion");
            int finFC = indiceFinal(bloqueDeTarea, inicioFC);
            String fechaCreacionStr = quitarComillas(bloqueDeTarea.substring(inicioFC, finFC));
            LocalDate fechaCreacion = LocalDate.parse(fechaCreacionStr);

            int inicioFM = indiceInicio(bloqueDeTarea , "fechaModificacion");
            int finFM = indiceFinal(bloqueDeTarea, inicioFM);
            String fechaModificacionStr = quitarComillas(bloqueDeTarea.substring(inicioFM, finFM));
            LocalDate fechaModificacion = LocalDate.parse(fechaModificacionStr);

            int inicioPrioridad = indiceInicio(bloqueDeTarea , "prioridad");
            int finPrioridad = indiceFinal(bloqueDeTarea, inicioPrioridad);
            Prioridad prioridad = Prioridad.valueOf(quitarComillas
                    (bloqueDeTarea.substring(inicioPrioridad, finPrioridad)));

            return new Tarea(id, descripcion, Estado.valueOf(estado),
                    fechaCreacion, fechaModificacion, prioridad);
        }

        private int indiceInicio (String bloqueDeTarea , String parametro){
            return bloqueDeTarea.indexOf(("\"")+ parametro+("\":")) + ("\""+parametro+"\":").length();
        }

        private int indiceFinal (String bloqueDeTarea , int indiceInicio){
            int fin = bloqueDeTarea.indexOf(",", indiceInicio);
            if (fin == -1) {
                fin = bloqueDeTarea.indexOf("}", indiceInicio);
            }
            return fin;
        }

        private String quitarComillas (String palabra){
            return palabra.replace("\"", "").trim();
        }

    }

