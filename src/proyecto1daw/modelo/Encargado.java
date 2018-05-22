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
public class Encargado extends Empleado{
    private String vhEmpresa;

    public Encargado(String vhEmpresa, String dni, String nombre, String apellidos, int edad, LocalDate fNacimiento, int salario, String tlf, LocalDate fContratacion, LocalDate fFin) {
        super(dni, nombre, apellidos, edad, fNacimiento, salario, tlf, fContratacion, fFin);
        this.vhEmpresa = vhEmpresa;
    }

    

    public String getVhEmpresa() {
        return vhEmpresa;
    }

    public void setVhEmpresa(String vhEmpresa) {
        this.vhEmpresa = vhEmpresa;
    }

    
    
}
