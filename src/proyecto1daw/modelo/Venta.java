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
public class Venta {
    private String id;
    private int kg;
    private float precio;
    private String tamanio;
    private String color;
    private LocalDate fecha;
    private String idPlantacion;

    /**
     *
     * @param id
     * @param kg
     * @param precio
     * @param tamanio
     * @param color
     * @param fecha
     * @param idPlantacion
     */
    public Venta(String id, int kg, float precio, String tamanio, String color, LocalDate fecha, String idPlantacion) {
        this.id = id;
        this.kg = kg;
        this.precio = precio;
        this.tamanio = tamanio;
        this.color = color;
        this.fecha = fecha;
        this.idPlantacion = idPlantacion;
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
    public int getKg() {
        return kg;
    }

    /**
     *
     * @param kg
     */
    public void setKg(int kg) {
        this.kg = kg;
    }

    /**
     *
     * @return
     */
    public float getPrecio() {
        return precio;
    }

    /**
     *
     * @param precio
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     *
     * @return
     */
    public String getTamanio() {
        return tamanio;
    }

    /**
     *
     * @param tamanio
     */
    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    /**
     *
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
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
    public String getIdPlantacion() {
        return idPlantacion;
    }

    /**
     *
     * @param idPlantacion
     */
    public void setIdPlantacion(String idPlantacion) {
        this.idPlantacion = idPlantacion;
    }    
}
