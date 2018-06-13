/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.time.LocalDate;



/**
 *
 * @author Jaime
 */
public class Cuadrilla {
    private String id;
    private LocalDate fInicio;
    private LocalDate fFin;
    private Encargado enc;

    public Cuadrilla(String idCuadrilla, LocalDate fInicio, LocalDate fFin) {
        this.id = idCuadrilla;
        this.fInicio = fInicio;
        this.fFin = fFin;
        
        CuadrillaDAO modeloCuad = new CuadrillaDAO();
        String dni = modeloCuad.getDniEncargado(this);
        enc = modeloCuad.recuperarEncargado(dni);
    }

    public String getId() {
        return id;
    }

    public void setId(String idCuadrilla) {
        this.id = idCuadrilla;
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

    public String toString() {
        return "Cuadrilla - " + id;
    }

    public Encargado getEncargado() {
        return enc;
    }

    public void setEncargado(Encargado enc) {
        this.enc = enc;
    }
    
    
}
