/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 *
 * @author Jaime
 */
public class Fechas {

    /**
     *
     * @param fecha
     * @return
     */
    public static String toString(LocalDate fecha) {
        String s = null;
        if(fecha != null){
            s = fecha.getDayOfMonth() + "/" + fecha.getMonthValue() + "/" + fecha.getYear();
        }
        return s;
    }

    /**
     *
     * @param fecha
     * @return
     */
    public static LocalDate toLocalDate(String fecha) {
        LocalDate f = null;
        String diaString = "";
        String mesString = "";
        String anioString = "";
        int i = 0;
        if (!fecha.equals("") && fecha != null) {
            while (i < fecha.length() && Character.isDigit(fecha.charAt(i))) {
                diaString += fecha.charAt(i);
                i++;
            }
            if (diaString.length()>0 && Integer.parseInt(diaString) >= 1 && Integer.parseInt(diaString) <= 31) {
                i++;
                while (i < fecha.length() && Character.isDigit(fecha.charAt(i))) {
                    mesString += fecha.charAt(i);
                    i++;
                }
                if (mesString.length()>0 &&Integer.parseInt(mesString) >= 1 && Integer.parseInt(mesString) <= 12) {
                    i++;
                    while (i < fecha.length() && Character.isDigit(fecha.charAt(i))) {
                        anioString += fecha.charAt(i);
                        i++;
                    }
                        //AÑO ENTRE 1900 Y 2200
                    if (anioString.length()>0 && Integer.parseInt(anioString) >= 1900 && Integer.parseInt(anioString) <= 2200) {
                        int anio, mes, dia;
                        anio = Integer.parseInt(anioString);
                        mes = Integer.parseInt(mesString);
                        dia = Integer.parseInt(diaString);
                        try{
                            f = LocalDate.of(anio, mes, dia);
                        }catch(DateTimeException e){
                            
                        }
                    }
                }
            }
        }

        return f;
    }

    /**
     *
     * @param nif
     * @return
     */
    public static boolean validarDni(String nif) {
        boolean res = true;
        nif=nif.toUpperCase();
        if(nif.length() == 9){
            char ultimoCar = nif.charAt(nif.length()-1);
            if(!Character.isAlphabetic(ultimoCar)){
                res=false;   
            }else{
                if(nif.charAt(0) == 'X'){                       //NIE
                    //Quita el primer caracter y el ultimo
                    nif = nif.substring(1, nif.length()-1);
                }else{                                          //DNI
                    nif = nif.substring(0, nif.length()-1);
                    //Quita el ultimo caracter
                }
                for (int i = 0; i < nif.length(); i++) {
                    if(!Character.isDigit(nif.charAt(i))){
                        res=false;
                        break;
                    }
                }
            }
            
        }else if(nif.length() == 10){                           //NIE de 10 caracteres
            char ultimoCar = nif.charAt(nif.length()-1);
            if(!Character.isAlphabetic(ultimoCar)){
                res=false;   
            }else{
                if(nif.charAt(0) == 'X'){
                    //Quita el primer caracter y el ultimo
                    nif = nif.substring(1, nif.length()-1);
                    for (int i = 0; i < nif.length(); i++) {
                        if(!Character.isDigit(nif.charAt(i))){
                            res=false;
                            break;
                        }
                    }
                }else{
                    res=false;
                }
            }
        }else{
            res = false;
        }
        return res;
    }
}
