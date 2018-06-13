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
public class Trabajador {
 //dni,nombre,apellidos,edad,f contratacion,salario,telefono
    private String dni;
    private String nombre;
    private String apellidos;
    private LocalDate fNacimiento;
    private LocalDate fContratacion;
    private LocalDate fFin;
    private String tlf;
    private int salario;

    public Trabajador(String dni) {
        this.dni = dni;
    }
    
    public Trabajador(String dni, String nombre, String apellidos, LocalDate fNacimiento, LocalDate fContratacion, LocalDate fFin, String tlf, int salario) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fNacimiento = fNacimiento;
        this.fContratacion = fContratacion;
        this.fFin = fFin;
        this.tlf = tlf;
        this.salario = salario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public LocalDate getfContratacion() {
        return fContratacion;
    }

    public void setfContratacion(LocalDate fContratacion) {
        this.fContratacion = fContratacion;
    }

    public LocalDate getfFin() {
        return fFin;
    }

    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String toString() {
        return dni+" - "+nombre + " " + apellidos;
    }


    public boolean equals(Object o) {
        boolean res = false;
        if (o instanceof Trabajador){
            if(this.dni.equalsIgnoreCase(((Trabajador) o).getDni())){
                res=true;
            }
        }
        return res;
    }

    
}
