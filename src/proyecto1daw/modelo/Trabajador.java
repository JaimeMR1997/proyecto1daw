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

    /**
     *
     * @param dni
     */
    public Trabajador(String dni) {
        this.dni = dni;
    }
    
    /**
     *
     * @param dni
     * @param nombre
     * @param apellidos
     * @param fNacimiento
     * @param fContratacion
     * @param fFin
     * @param tlf
     * @param salario
     */
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

    /**
     *
     * @return
     */
    public String getDni() {
        return dni;
    }

    /**
     *
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     *
     * @param apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     *
     * @return
     */
    public LocalDate getfNacimiento() {
        return fNacimiento;
    }

    /**
     *
     * @param fNacimiento
     */
    public void setfNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    /**
     *
     * @return
     */
    public LocalDate getfContratacion() {
        return fContratacion;
    }

    /**
     *
     * @param fContratacion
     */
    public void setfContratacion(LocalDate fContratacion) {
        this.fContratacion = fContratacion;
    }

    /**
     *
     * @return
     */
    public LocalDate getfFin() {
        return fFin;
    }

    /**
     *
     * @param fFin
     */
    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    /**
     *
     * @return
     */
    public String getTlf() {
        return tlf;
    }

    /**
     *
     * @param tlf
     */
    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    /**
     *
     * @return
     */
    public int getSalario() {
        return salario;
    }

    /**
     *
     * @param salario
     */
    public void setSalario(int salario) {
        this.salario = salario;
    }

    /**
     *
     * @return
     */
    public String toString() {
        return dni+" - "+nombre + " " + apellidos;
    }

    /**
     *
     * @param o
     * @return
     */
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
