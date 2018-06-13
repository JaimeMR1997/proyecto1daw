/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.time.LocalDate;
import java.util.Objects;



/**
 *
 * @author Jaime
 */
public class Conductor extends Trabajador{
    private String permisos;

    /**
     *
     * @param dni
     */
    public Conductor(String dni) {
        super(dni);
    }
    
    /**
     *
     * @param permisos
     * @param dni
     * @param nombre
     * @param apellidos
     * @param fNacimiento
     * @param fContratacion
     * @param fFin
     * @param tlf
     * @param salario
     */
    public Conductor(String permisos, String dni, String nombre, String apellidos, LocalDate fNacimiento, LocalDate fContratacion, LocalDate fFin, String tlf, int salario) {
        super(dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        this.permisos = permisos;
    } 

    /**
     *
     * @return
     */
    public String getPermisos() {
        return permisos;
    }

    /**
     *
     * @param permisos
     */
    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
    
    /**
     *
     * @return
     */
    public String toString() {
        return super.toString()+" - Conductor";
    }

    /**
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        return super.equals(o);
    }
    
    
}
