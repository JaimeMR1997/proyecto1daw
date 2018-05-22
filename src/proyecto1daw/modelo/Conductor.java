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
public class Conductor extends Empleado{
    private String permisos;

    public Conductor(String permisos, String dni, String nombre, String apellidos, int edad, LocalDate fNacimiento, int salario, String tlf, LocalDate fContratacion, LocalDate fFin) {
        super(dni, nombre, apellidos, edad, fNacimiento, salario, tlf, fContratacion, fFin);
        this.permisos = permisos;
    }    

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
    
}
