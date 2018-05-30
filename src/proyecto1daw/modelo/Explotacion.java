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
public class Explotacion {
    private String id;
    private int superficie;
    private String tipo;
    private LocalDate fCreacion;
    private LocalDate fFin;
    private String idFinca;

    public Explotacion(String id, int superficie, String tipo, LocalDate fCreacion, LocalDate fFin, String idFinca) {
        this.id = id;
        this.superficie = superficie;
        this.tipo = tipo;
        this.fCreacion = fCreacion;
        this.fFin = fFin;
        this.idFinca = idFinca;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getfCreacion() {
        return fCreacion;
    }

    public void setfCreacion(LocalDate fCreacion) {
        this.fCreacion = fCreacion;
    }

    public LocalDate getfFin() {
        return fFin;
    }

    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public String getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(String idFinca) {
        this.idFinca = idFinca;
    }

    
}
