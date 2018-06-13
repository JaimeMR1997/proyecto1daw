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

    public Plantacion(String id, String tipo, String variedad, LocalDate fInicio, LocalDate fFin, String idExplotacion) {
        this.id = id;
        this.tipo = tipo;
        this.variedad = variedad;
        this.fInicio = fInicio;
        this.fFin = fFin;
        this.idExplotacion = idExplotacion;
    }

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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getIdPlant() {
        return id;
    }

    public void setIdPlant(String idPlant) {
        this.id = idPlant;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String varidad) {
        this.variedad = varidad;
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

    @Override
    public String toString() {
        return tipo + ":" + variedad + "F.Inicio -> " + fInicio;
    }
    
    
}
