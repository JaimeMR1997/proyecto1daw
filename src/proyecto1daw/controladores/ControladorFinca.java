/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;

/**
 *
 * @author alumno
 */
public class ControladorFinca implements ActionListener{
    private JFFinca vistaTabla;
    private JFFincaAdd vistaAddFinca;
    private FincaDAO modelo;

    public ControladorFinca(JFFinca vistaTabla, FincaDAO modelo,JFFincaAdd vistaAddFinca) {
        this.vistaTabla = vistaTabla;
        this.modelo = modelo;
        this.vistaAddFinca=vistaAddFinca;
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAddFinca.setLocationRelativeTo(null);
        
        //Asignar action listener a botones
        this.vistaTabla.botonAdd.addActionListener(this);
        this.vistaTabla.botonEliminar.addActionListener(this);
        this.vistaTabla.botonGestionar.addActionListener(this);
        this.vistaTabla.botonInfo.addActionListener(this);
        this.vistaTabla.botonMod.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);
        //Los de AddFinca tambien
        this.vistaAddFinca.botonAceptar.addActionListener(this);
        this.vistaAddFinca.botonCancelar.addActionListener(this);
        this.vistaAddFinca.jRadioButtonEmp.addActionListener(this);
        this.vistaAddFinca.jRadioButtonEnc.addActionListener(this);
        this.vistaAddFinca.jRadioButtonTract.addActionListener(this);
        
        
    }
    
    
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaTabla.botonVolver)){
            System.out.println("Volviendo");
            //Volver a Inicio
        }else if(ae.getSource().equals(vistaTabla.botonGestionar)){
            System.out.println("Gestionan");
            //Abrir ventana gestion fincva seleccionada
            //Si no hay mostrar ventana alerta
        }else if(ae.getSource().equals(vistaTabla.botonAdd)){
            System.out.println("Añadir");
            //Llamar a ventanaAñadir
            vistaAddFinca.setVisible(true);
            vistaAddFinca.setAlwaysOnTop(true);
            vistaAddFinca.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    
        }else if(ae.getSource().equals(vistaTabla.botonMod)){
            System.out.println("Modificar");
            //Llamar ventanaAñadir con datos rellenos
        }else if(ae.getSource().equals(vistaTabla.botonInfo)){
            System.out.println("Informacion");
            //Sustituir por buscar??
        }else if(ae.getSource().equals(vistaTabla.botonEliminar)){
            System.out.println("Elimianr");
            //Preguntar antes de elimianr completamente
        }else if(ae.getSource().equals(vistaAddFinca.botonAceptar)){
            
        }else if(ae.getSource().equals(vistaAddFinca.botonCancelar)){
            this.vistaAddFinca.dispose();
        }else if(ae.getSource().equals(vistaAddFinca.jRadioButtonEmp)){
            //Cargar en Jlist Empleados
            //Si se selecciona uno se cambia al tipo Encargado
        }else if(ae.getSource().equals(vistaAddFinca.jRadioButtonEnc)){
            //Cargar en Jlist Encargados
        }else if(ae.getSource().equals(vistaAddFinca.jRadioButtonTract)){
            //Cargar en Jlist Tractoristas
            //Si se selecciona uno se cambia al tipo Encargado
        }
    }
    
}
