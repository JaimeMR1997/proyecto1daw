/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import proyecto1daw.controladores.ControladorInicio;
import proyecto1daw.vistas.JFInicio;



/**
 *
 * @author alumno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControladorInicio contIni = new ControladorInicio(new JFInicio());
    }
    
}
