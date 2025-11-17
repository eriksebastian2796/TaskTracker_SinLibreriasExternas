package com.erik.tasktracker;

import java.time.LocalDate;

public class Tarea {

    private int id;
    private String descripcion;
    private Estado estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;
    private Prioridad prioridad;

    public Tarea(int id, String descripcion, Estado estado, LocalDate fechaCreacion,
                 LocalDate fechaModificacion, Prioridad prioridad) {

        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.prioridad = prioridad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getId() {
        return id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "---------------\n" +
                "ID: " + id +"\n" +
                "Descripcion: " + descripcion + "\n" +
                "Estado: " + estado + "\n" +
                "Prioridad: " + prioridad + "\n" +
                "FechaCreacion: " + fechaCreacion+ "\n" +
                "FechaModificacion: " + fechaModificacion
                ;
    }

}
