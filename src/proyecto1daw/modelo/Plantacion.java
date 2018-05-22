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
    private String idPlant;
    private String tipo;
    private String varidad;
    private LocalDate fInicio;
    private LocalDate fFin;
    private String idExplotacion;

    public Plantacion(String idPlant, String tipo, String varidad, LocalDate fInicio, LocalDate fFin, String idExplotacion) {
        this.idPlant = idPlant;
        this.tipo = tipo;
        this.varidad = varidad;
        this.fInicio = fInicio;
        this.fFin = fFin;
        this.idExplotacion = idExplotacion;
    }

    public String getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(String idPlant) {
        this.idPlant = idPlant;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVaridad() {
        return varidad;
    }

    public void setVaridad(String varidad) {
        this.varidad = varidad;
    }

    public LocalDate getfInicio() {
        return fInicio;
    }

    public void setfInicio(LocalDate fInicio) {
        this.fInicio = fInicio;
    }

    public LocalDate getfFin() {
        return fFin;
    }

    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    public String getIdExplotacion() {
        return idExplotacion;
    }

    public void setIdExplotacion(String idExplotacion) {
        this.idExplotacion = idExplotacion;
    }
    
    
}
