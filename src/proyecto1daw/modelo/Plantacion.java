/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Jaime
 */
public class Plantacion {
    private String id;
    private String tipo;
    private String variedad;
    private LocalDate fInicio;
    private LocalDate fFin;
    private String idExplotacion;

    /**
     *
     * @param id
     * @param tipo
     * @param variedad
     * @param fInicio
     * @param fFin
     * @param idExplotacion
     */
    public Plantacion(String id, String tipo, String variedad, LocalDate fInicio, LocalDate fFin, String idExplotacion) {
        this.id = id;
        this.tipo = tipo;
        this.variedad = variedad;
        this.fInicio = fInicio;
        this.fFin = fFin;
        this.idExplotacion = idExplotacion;
    }

    /**
     *
     * @return
     */
    public boolean estaActiva(){
        boolean res = true;
        if(LocalDate.now().isBefore(fInicio)){
            res = false;
        }
        if(fFin != null && LocalDate.now().isAfter(fFin)){
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     *
     * @return
     */
    public String getIdPlant() {
        return id;
    }

    /**
     *
     * @param idPlant
     */
    public void setIdPlant(String idPlant) {
        this.id = idPlant;
    }

    /**
     *
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @return
     */
    public String getVariedad() {
        return variedad;
    }

    /**
     *
     * @param varidad
     */
    public void setVariedad(String varidad) {
        this.variedad = varidad;
    }

    /**
     *
     * @return
     */
    public LocalDate getfInicio() {
        return fInicio;
    }

    /**
     *
     * @param fInicio
     */
    public void setfInicio(LocalDate fInicio) {
        this.fInicio = fInicio;
    }

    /**
     *
     * @return
     */
    public LocalDate getfFin() {
        return fFin;
    }

    /**
     *
     * @param fFin
     */
    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
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

    /**
     *
     * @return
     */
    public String toString() {
        return tipo + "-" + variedad + " F.In: " + fInicio;
    }
    
    
}
