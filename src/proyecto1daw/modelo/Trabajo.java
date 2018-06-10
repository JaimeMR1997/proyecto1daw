/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.time.LocalDate;

/**
 *
 * @author alumno
 */
public class Trabajo {
   private String idCuadrilla;
   private LocalDate fecha;
   private int horas;
   private String tarea;
   private String idExplotacion;

    public Trabajo(String idCuadrilla, LocalDate fecha, int horas, String tipo, String idExplotacion) {
        this.idCuadrilla = idCuadrilla;
        this.fecha = fecha;
        this.horas = horas;
        this.tarea = tipo;
        this.idExplotacion = idExplotacion;
    }
   
    public Trabajo(String idCuadrilla, LocalDate fecha, int horas, String idExplotacion) {
        this.idCuadrilla = idCuadrilla;
        this.fecha = fecha;
        this.horas = horas;
        this.idExplotacion = idExplotacion;
    }

    public String getIdCuadrilla() {
        return idCuadrilla;
    }

    public void setIdCuadrilla(String idCuadrilla) {
        this.idCuadrilla = idCuadrilla;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getIdExplotacion() {
        return idExplotacion;
    }

    public void setIdExplotacion(String idExplotacion) {
        this.idExplotacion = idExplotacion;
    }
    
    
}
