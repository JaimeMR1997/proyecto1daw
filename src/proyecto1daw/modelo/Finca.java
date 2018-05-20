/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

/**
 *
 * @author Jaime-torre
 */
public class Finca {
    private String id;
    private String localizacion;
    private String encargado;
    private int superficie;

    public Finca() {
    }

    public Finca(String id, String localizacion, String encargado, int superficie) {
        this.id = id;
        this.localizacion = localizacion;
        this.encargado = encargado;
        this.superficie = superficie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }
    
    
}
