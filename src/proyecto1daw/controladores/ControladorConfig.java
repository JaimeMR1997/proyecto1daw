/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.vistas.JFConfiguracion;

/**
 *
 * @author Jaime
 */
class ControladorConfig implements ActionListener{
    private JFConfiguracion vista;
    private DefaultListModel modLista;
    private DefaultComboBoxModel modDespBD;

    public ControladorConfig(JFConfiguracion vista) {
        this.vista = vista;
        this.modLista = new DefaultListModel();
        this.modDespBD = new DefaultComboBoxModel();
        //Asociar modelos a objetos
        this.vista.jListOpcs.setModel(modLista);
        this.vista.jComboBD.setModel(modDespBD);
        //Cargar listas
        this.cargarListaOpcs();
        this.cargarDespBD();
        //Asociar listeners
        this.vista.botonAceptar.addActionListener(this);
        this.vista.botonCancelar.addActionListener(this);
        this.vista.jRadioBD.addActionListener(this);
        this.vista.jRadioFicheros.addActionListener(this);
        this.vista.jComboBD.addActionListener(this);
        //Cargar configuración actual
        this.cargarConfigActual();
        //Mostrar ventana
        this.vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vista.botonAceptar)){                          //ACEPTAR
            if(this.guardarCambios()){
                JOptionPane.showMessageDialog(vista, "Cambios guardados con éxito.");
                vista.dispose();
            }else{
                JOptionPane.showMessageDialog(vista, "Ha ocurrido un error inesperado al guardar los cambios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getSource().equals(vista.botonCancelar)){                   //CANCELAR
            vista.dispose();
        }else if(ae.getSource().equals(vista.jRadioFicheros)){                  //RADIO FICHEROS
            disableCamposBD();
        }else if(ae.getSource().equals(vista.jRadioBD)){                        //RADIO BD
            enableCamposBD();
        }else if(ae.getSource().equals(vista.jComboBD)){                        //DESPLEGABLE BD
            
        }
    }

    private void cargarDespBD() {
        this.modLista.addElement("Almacenamiento");
    }

    private void cargarListaOpcs() {
        this.modDespBD.addElement("MySQL");
        this.modDespBD.addElement("Oracle Express");
    }

    private boolean guardarCambios() {
        boolean res = true;
        
        comprobarConfig();
        
        return res;
    }

    private void disableCamposBD() {
        vista.jComboBD.setEnabled(false);
        vista.campoIp.setEnabled(false);
        vista.campoPuerto.setEnabled(false);
    }

    private void enableCamposBD() {
        vista.jComboBD.setEnabled(true);
        vista.campoIp.setEnabled(true);
        vista.campoPuerto.setEnabled(true);
    }

    private void comprobarConfig() {
        if(isJRadioBDSelected()){
            Configuracion config = new Configuracion();
            String baseDatos = (String) vista.jComboBD.getSelectedItem();
            if(baseDatos.equalsIgnoreCase("MySQL")){                        //MYSQL
                config.setTipoServer("mysql");
            }else{                                                          //ORACLE
                config.setTipoServer("oracle");
            }
            
            if(!vista.campoIp.equals("")){                                   //IP
                String direccion = this.vista.campoIp.getText();
                config.setIp(direccion);
            }else{
                
            }
            
            if(!vista.campoPuerto.equals("")){                               //PUERTO
                String puerto = this.vista.campoPuerto.getText();
                config.setPuerto(puerto);
            }else{
                
            }
            
        }else{
            JOptionPane.showMessageDialog(vista,"El soporte para ficheros está "
                    + "aún en desarrollo" , "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean isJRadioBDSelected() {
        return vista.jRadioBD.isSelected();
    }

    private void cargarConfigActual() {
        Configuracion config = new Configuracion();
        String sistemaBD = config.getTipoServer();
        if(sistemaBD == null){
            
        }else if(sistemaBD.equalsIgnoreCase("mysql")){
            sistemaBD = "MySQL";
        }else if(sistemaBD.equalsIgnoreCase("oracle")){
            sistemaBD = "Oracle Express";
        }
        
        this.vista.jComboBD.setSelectedItem(sistemaBD);
        this.vista.campoIp.setText(config.getIP());
        this.vista.campoPuerto.setText(config.getPuerto());
    }
    
}
