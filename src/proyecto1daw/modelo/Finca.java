/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import proyecto1daw.modelo.accesobd.FincaDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import proyecto1daw.modelo.accesobd.mysql.FincaMysql;
import proyecto1daw.modelo.accesobd.sqlite.FincaSqlite;

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
    private String alias;

    /**
     *
     * @param id
     * @param localidad
     * @param superficie
     * @param fCompra
     * @param fFin
     */
    public Finca(String id, String localidad, int superficie, LocalDate fCompra, LocalDate fFin,String alias) {
        this.id = id;
        this.localidad = localidad;
        this.superficie = superficie;
        this.fCompra = fCompra;
        this.fFin = fFin;
        this.alias = alias;
        
        //Cargar lista encargados
        FincaDAO modeloFinca;
        
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            modeloFinca = new FincaMysql();
        }else{
            modeloFinca = new FincaSqlite();
        }
        
        this.listaEncargados = modeloFinca.recuperarEncargadosFinca(this.id);
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
    public String getLocalidad() {
        return localidad;
    }

    /**
     *
     * @param localidad
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     *
     * @return
     */
    public ArrayList<Encargado> getListaEncargados() {
        return listaEncargados;
    }

    /**
     *
     * @param listaEncargados
     */
    public void setListaEncargados(ArrayList<Encargado> listaEncargados) {
        this.listaEncargados = listaEncargados;
    }
    
    /**
     *
     * @return
     */
    public int getSuperficie() {
        return superficie;
    }

    /**
     *
     * @param superficie
     */
    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    /**
     *
     * @return
     */
    public LocalDate getfCompra() {
        return fCompra;
    }

    /**
     *
     * @param fCompra
     */
    public void setfCompra(LocalDate fCompra) {
        this.fCompra = fCompra;
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
        return id +" - "+ superficie + " ha";
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
 
}
