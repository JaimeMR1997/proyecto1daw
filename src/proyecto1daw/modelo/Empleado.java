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
public class Empleado {
 //dni,nombre,apellidos,edad,f contratacion,salario,telefono
    private String dni;
    private String nombre;
    private String apellidos;
    private int edad;
    private LocalDate fNacimiento;
    private int salario;
    private String tlf;
    private LocalDate fContratacion;
    private LocalDate fFin;

    public Empleado(String dni, String nombre, String apellidos, int edad, LocalDate fNacimiento, int salario, String tlf, LocalDate fContratacion, LocalDate fFin) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.fNacimiento = fNacimiento;
        this.salario = salario;
        this.tlf = tlf;
        this.fContratacion = fContratacion;
        this.fFin = fFin;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }
    
    
}
