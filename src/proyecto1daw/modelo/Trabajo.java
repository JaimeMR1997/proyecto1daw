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

    /**
     *
     * @param idCuadrilla
     * @param fecha
     * @param horas
     * @param tipo
     * @param idExplotacion
     */
    public Trabajo(String idCuadrilla, LocalDate fecha, int horas, String tipo, String idExplotacion) {
        this.idCuadrilla = idCuadrilla;
        this.fecha = fecha;
        this.horas = horas;
        this.tarea = tipo;
        this.idExplotacion = idExplotacion;
    }
   
    /**
     *
     * @param idCuadrilla
     * @param fecha
     * @param horas
     * @param idExplotacion
     */
    public Trabajo(String idCuadrilla, LocalDate fecha, int horas, String idExplotacion) {
        this.idCuadrilla = idCuadrilla;
        this.fecha = fecha;
        this.horas = horas;
        this.idExplotacion = idExplotacion;
    }

    /**
     *
     * @return
     */
    public String getIdCuadrilla() {
        return idCuadrilla;
    }

    /**
     *
     * @param idCuadrilla
     */
    public void setIdCuadrilla(String idCuadrilla) {
        this.idCuadrilla = idCuadrilla;
    }

    /**
     *
     * @return
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     */
    public int getHoras() {
        return horas;
    }

    /**
     *
     * @param horas
     */
    public void setHoras(int horas) {
        this.horas = horas;
    }

    /**
     *
     * @return
     */
    public String getTarea() {
        return tarea;
    }

    /**
     *
     * @param tarea
     */
    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    /**
     *
     * @return
     */
    public String getIdExplotacion() {
        return idExplotacion;
    }

    /**
     *
     * @param idExplotacion
     */
    public void setIdExplotacion(String idExplotacion) {
        this.idExplotacion = idExplotacion;
    }
    
    
}
