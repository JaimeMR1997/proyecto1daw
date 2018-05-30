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

    public Encargado(String dni) {
        super(dni);
    }
    
    public Encargado(String vhEmpresa, String dni, String nombre, String apellidos, LocalDate fNacimiento, LocalDate fContratacion, LocalDate fFin, String tlf, int salario) {
        super(dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        this.vhEmpresa = vhEmpresa;
    }

    

    public String getVhEmpresa() {
        return vhEmpresa;
    }

    public void setVhEmpresa(String vhEmpresa) {
        this.vhEmpresa = vhEmpresa;
    }

    
    
}
