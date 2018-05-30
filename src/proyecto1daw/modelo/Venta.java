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

    public Venta(String id, int kg, float precio, String tamanio, String color, LocalDate fecha, String idPlantacion) {
        this.id = id;
        this.kg = kg;
        this.precio = precio;
        this.tamanio = tamanio;
        this.color = color;
        this.fecha = fecha;
        this.idPlantacion = idPlantacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getIdPlantacion() {
        return idPlantacion;
    }

    public void setIdPlantacion(String idPlantacion) {
        this.idPlantacion = idPlantacion;
    }    
}
