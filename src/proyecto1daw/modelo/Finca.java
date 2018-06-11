/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Jaime-torre
 */
public class Finca {
    private String id;
    private String localidad;
    private ArrayList<Encargado> listaEncargados;
    private int superficie;
    private LocalDate fCompra;
    private LocalDate fFin;

    public Finca(String id, String localidad, int superficie, LocalDate fCompra, LocalDate fFin) {
        this.id = id;
        this.localidad = localidad;
        this.superficie = superficie;
        this.fCompra = fCompra;
        this.fFin = fFin;
        
        //Cargar lista encargados
        FincaDAO modeloFinca = new FincaDAO();
        this.listaEncargados = modeloFinca.recuperarEncargadosFinca(this.id);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public ArrayList<Encargado> getListaEncargados() {
        return listaEncargados;
    }

    public void setListaEncargados(ArrayList<Encargado> listaEncargados) {
        this.listaEncargados = listaEncargados;
    }
    
    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public LocalDate getfCompra() {
        return fCompra;
    }

    public void setfCompra(LocalDate fCompra) {
        this.fCompra = fCompra;
    }

    public LocalDate getfFin() {
        return fFin;
    }

    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }

   
    public String toString() {
        return id +" - "+ superficie + " ha";
    }
 
    
}
