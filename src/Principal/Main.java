/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import proyecto1daw.controladores.ControladorFinca;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;

/**
 *
 * @author alumno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFFinca vistaFinca = new JFFinca();
        FincaDAO modeloFincaDao = new FincaDAO();
        JFFincaAdd vistaFincaAdd = new JFFincaAdd();
        ControladorFinca controlador = new ControladorFinca(vistaFinca, modeloFincaDao,vistaFincaAdd);
        vistaFinca.setVisible(true);
    }
    
}
