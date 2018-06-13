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
public class Encargado extends Trabajador{
    private String vhEmpresa;

    /**
     *
     * @param dni
     */
    public Encargado(String dni) {
        super(dni);
    }
    
    /**
     *
     * @param vhEmpresa
     * @param dni
     * @param nombre
     * @param apellidos
     * @param fNacimiento
     * @param fContratacion
     * @param fFin
     * @param tlf
     * @param salario
     */
    public Encargado(String vhEmpresa, String dni, String nombre, String apellidos, LocalDate fNacimiento, LocalDate fContratacion, LocalDate fFin, String tlf, int salario) {
        super(dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        this.vhEmpresa = vhEmpresa;
    }

    /**
     *
     * @return
     */
    public String getVhEmpresa() {
        return vhEmpresa;
    }

    /**
     *
     * @param vhEmpresa
     */
    public void setVhEmpresa(String vhEmpresa) {
        this.vhEmpresa = vhEmpresa;
    }

    /**
     *
     * @return
     */
    public String toString() {
        return super.toString()+" - Encargado";
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
