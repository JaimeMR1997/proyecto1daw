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
public class Conductor extends Trabajador{
    private String permisos;

    public Conductor(String dni) {
        super(dni);
    }
    
    public Conductor(String permisos, String dni, String nombre, String apellidos, LocalDate fNacimiento, LocalDate fContratacion, LocalDate fFin, String tlf, int salario) {
        super(dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        this.permisos = permisos;
    } 

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
    
    public String toString() {
        return super.toString()+" - Conductor";
    }
}
