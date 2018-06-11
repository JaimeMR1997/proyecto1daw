/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.vistas.JFEmpleados;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author Jaime
 */
public class ControladorInicio implements ActionListener{
    private JFInicio vista;

    public ControladorInicio(JFInicio vista) {
        this.vista = vista;
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        
        //Asociar listener a botones
        this.vista.jButtonEstadisticas.addActionListener(this);
        this.vista.jButtonFincas.addActionListener(this);
        this.vista.jButtonTrabajadores.addActionListener(this);
        this.vista.jButtonTractores.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        JButton boton =(JButton)ae.getSource();
        if(boton.equals(this.vista.jButtonTractores)){
            
        }else if(boton.equals(this.vista.jButtonFincas)){                       //VENTANA FINCAS
            ControladorFinca conFinca = new ControladorFinca(new JFFinca(), new FincaDAO(), new ExplotacionDAO());
            this.vista.dispose();
            
        }else if(boton.equals(this.vista.jButtonTrabajadores)){                 //VENTANA TRABAJADORES
            ControladorEmpleado contEmp = new ControladorEmpleado(new JFEmpleados());
            this.vista.dispose();
            
        }else if(boton.equals(this.vista.jButtonEstadisticas)){
            
        }
    }
    
}
