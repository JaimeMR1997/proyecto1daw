/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.PlantacionDAO;
import proyecto1daw.vistas.JFEstadisticas;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author Jaime
 */
class ControladorEstadisticas implements ActionListener{
    private JFEstadisticas vista;
    private FincaDAO modeloFinca;
    private ExplotacionDAO modeloExp;
    private PlantacionDAO modeloPlant;
    private DefaultComboBoxModel modTipo;
    private DefaultComboBoxModel modSubtipo;
    
    public ControladorEstadisticas(FincaDAO modeloFinca, ExplotacionDAO modeloExp) {
        this.vista = new JFEstadisticas();
        this.modeloFinca = modeloFinca;
        this.modeloExp = modeloExp;
        this.modeloPlant = new PlantacionDAO();
        //Crear modleos jcombo
        this.modTipo = new DefaultComboBoxModel();
        this.modSubtipo = new DefaultComboBoxModel();
        //Asociar modelos a los desplegables
        this.vista.jComboTipo.setModel(modTipo);
        this.vista.jComboSubtipo.setModel(modSubtipo);
        //Asociar listener
        this.vista.botonVolver.addActionListener(this);
        this.vista.jComboTipo.addActionListener(this);
        this.vista.jComboSubtipo.addActionListener(this);
        //Cargar tipo
        this.cargarTipo();
        this.cargarSubtipo();
        //Mostrar
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vista.botonVolver)){                           //VOLVER    
            ControladorInicio contInicio = new ControladorInicio(new JFInicio());
            vista.dispose();
        }else if(ae.getSource().equals(vista.jComboTipo)){
            cargarSubtipo();
        }else if(ae.getSource().equals(vista.jComboSubtipo)){
            cargarEstadisticas();
        }
    }

    private void cargarTipo() {
        ArrayList<Explotacion> listaExp = modeloExp.recuperarTodas();
        for (Explotacion exp : listaExp) {
            this.modTipo.addElement(exp);
        }
    }
    
    private void cargarSubtipo() {
        this.modSubtipo.removeAllElements();
        Explotacion exp = (Explotacion) this.vista.jComboTipo.getSelectedItem();
        ArrayList<Plantacion> listaPlant = modeloPlant.recuperarPorExp(exp.getId());
        for (Plantacion p : listaPlant) {
            this.modSubtipo.addElement(p);
        }
    }

    private void cargarEstadisticas() {
        
    }
    
}
