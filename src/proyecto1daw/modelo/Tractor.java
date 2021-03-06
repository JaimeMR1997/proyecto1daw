/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.time.LocalDate;
import java.time.Month;

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
    private String idFinca;

    /**
     *
     * @param matricula
     * @param modelo
     * @param potencia
     * @param altura
     * @param ancho
     * @param fItv
     * @param anio
     */
    public Tractor(String matricula, String modelo, int potencia, int altura, int ancho, LocalDate fItv, int anio) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.potencia = potencia;
        this.altura = altura;
        this.ancho = ancho;
        this.fItv = fItv;
        this.anio = anio;
    }

    /**
     *
     * @param matricula
     * @param modelo
     * @param potencia
     * @param altura
     * @param ancho
     * @param fItv
     * @param anio
     * @param idFinca
     */
    public Tractor(String matricula, String modelo, int potencia, int altura, int ancho, LocalDate fItv, int anio, String idFinca) {
        this(matricula, modelo, potencia, altura, ancho, fItv, anio);
        this.idFinca = idFinca;
    }

    /**
     *
     * @return
     */
    public LocalDate calcularItv() {
        LocalDate calculoProxItv = fItv;
        int antiguedad = fItv.minusYears(anio).getYear();
        if (antiguedad == 0 && fItv == null) {
            //Para tractores nuevos la 1 ITV es a los 4 años
            calculoProxItv = LocalDate.of(anio + 4, LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());

        } else if (antiguedad < 16) {
            //Para tractores con mas de 4 años la ITV es cada 2 años
            calculoProxItv = LocalDate.of(fItv.getYear() + 2, fItv.getMonth(), fItv.getDayOfMonth());
        } else {
            //Para tractores con mas de 16 años la ITV es cada año
            calculoProxItv = LocalDate.of(fItv.getYear() + 1, fItv.getMonth(), fItv.getDayOfMonth());
        }
        return calculoProxItv;
    }

    /**
     *
     * @param m
     * @return
     */
    public static boolean validarMatriculaTractor(String m) {
        boolean res = false;
        m=m.toUpperCase();
        m=m.trim();
        if (m.length() == 8) {
            char c = m.charAt(0);
            if (Character.isLetter(c)) { //Primer caracter
                c = m.charAt(m.length() - 1);

                if (Character.isLetter(c)) { //Ultimo caracter
                    c = m.charAt(m.length() - 2);

                    if (Character.isLetter(c)) {    //Penultimo caracter
                        res = true;
                        for (int i = 1; i <= 4; i++) {
                            if (Character.isDigit(m.charAt(i))) {       //Comprobar que sean numeros
                                res = false;
                            }
                        }
                        c = m.charAt(m.length() - 3);//Antepenultimo caracter
                        if (!Character.isDigit(c) && !Character.isLetter(c)) {
                            res = false;              //Matriculas Nuevas E9999ZZZ
                                                      //Matriculas Viejas provincia corta A99999ZZ
                        }
                    }
                }
            }
        } else if (m.length() == 9) {
            char c = m.charAt(0);
            if (Character.isLetter(c)) { //Primer caracter
                c = m.charAt(1);

                if (Character.isLetter(c)) { //Segundo caracter
                    c = m.charAt(m.length() - 1);

                    if (Character.isLetter(c)) { //Ultimo caracter
                        res = true;
                        for (int i = 1; i <= 4; i++) {
                            if (Character.isDigit(m.charAt(i))) {       //Comprobar que sean numeros
                                res = false;
                            }
                        }
                        c = m.charAt(m.length() - 2);//Penultimo caracter
                        if (Character.isLetter(c)) {
                            res = false;              
                            //Matriculas Viejas provincia larga MU99999ZZ
                        }
                    }
                }
            }
        }
        return res;
    }
    
    /**
     *
     * @param m
     * @return
     */
    public static boolean validarMatriculaCoche(String m) {
        boolean res = false;
        m=m.toUpperCase();
        m=m.trim();
        if (m.length() == 7) {
            for (int i = 0; i < 4; i++) {
                if (!Character.isDigit(m.charAt(i))) {       //Comprobar que 4 primeros sean numeros
                        res = false;
                }
            }
            for (int i = 4; i < m.length(); i++) {
                if (!Character.isLetter(m.charAt(i))) {       //Comprobar que 3 ultimos sean letras
                        res = false;
                }
            }
        } 
        return res;
    }

    /**
     *
     * @return
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     *
     * @param matricula
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     *
     * @return
     */
    public String getModelo() {
        return modelo;
    }

    /**
     *
     * @param modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     *
     * @return
     */
    public int getPotencia() {
        return potencia;
    }

    /**
     *
     * @param potencia
     */
    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    /**
     *
     * @return
     */
    public int getAltura() {
        return altura;
    }

    /**
     *
     * @param altura
     */
    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     *
     * @return
     */
    public int getAncho() {
        return ancho;
    }

    /**
     *
     * @param ancho
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    /**
     *
     * @return
     */
    public LocalDate getfItv() {
        return fItv;
    }

    /**
     *
     * @param fItv
     */
    public void setfItv(LocalDate fItv) {
        this.fItv = fItv;
    }

    /**
     *
     * @return
     */
    public int getAnio() {
        return anio;
    }

    /**
     *
     * @param anio
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     *
     * @return
     */
    public String getIdFinca() {
        return idFinca;
    }

    /**
     *
     * @param idFinca
     */
    public void setIdFinca(String idFinca) {
        this.idFinca = idFinca;
    }
    
    
}
