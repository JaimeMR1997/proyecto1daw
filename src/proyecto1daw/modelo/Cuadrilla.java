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

    /**
     *
     * @param idCuadrilla
     * @param fInicio
     * @param fFin
     */
    public Cuadrilla(String idCuadrilla, LocalDate fInicio, LocalDate fFin) {
        this.id = idCuadrilla;
        this.fInicio = fInicio;
        this.fFin = fFin;
        
        CuadrillaDAO modeloCuad = new CuadrillaDAO();
        String dni = modeloCuad.getDniEncargado(this);
        enc = modeloCuad.recuperarEncargado(dni);
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
     * @param idCuadrilla
     */
    public void setId(String idCuadrilla) {
        this.id = idCuadrilla;
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
    public String toString() {
        return "Cuadrilla - " + id;
    }

    /**
     *
     * @return
     */
    public Encargado getEncargado() {
        return enc;
    }

    /**
     *
     * @param enc
     */
    public void setEncargado(Encargado enc) {
        this.enc = enc;
    }
    
    
}
