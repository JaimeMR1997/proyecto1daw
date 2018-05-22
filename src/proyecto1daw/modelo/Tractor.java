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
public class Tractor {
    private String matricula;
    private String modelo;
    private int potencia;
    private int altura;
    private int ancho;
    private LocalDate fItv;
    private int anio;

    public Tractor(String matricula, String modelo, int potencia, int altura, int ancho, LocalDate fItv, int anio) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.potencia = potencia;
        this.altura = altura;
        this.ancho = ancho;
        this.fItv = fItv;
        this.anio = anio;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public LocalDate getfItv() {
        return fItv;
    }

    public void setfItv(LocalDate fItv) {
        this.fItv = fItv;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    
}
