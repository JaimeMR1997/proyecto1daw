/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import proyecto1daw.modelo.CuadrillaDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.TrabajadorDAO;
import proyecto1daw.vistas.JFCuadAdd;
import proyecto1daw.vistas.JFEmpleados;

/**
 *
 * @author Jaime
 */
public class ControladorAddCuad implements ActionListener, FocusListener {
    private JFCuadAdd vistaAdd;
    private JFEmpleados vistaTabla;
    private CuadrillaDAO modeloCuad;
    private TrabajadorDAO modeloTrab;
    private DefaultListModel modLista;
    private boolean accionEsMod;
    
    /**
     *
     * @param mod Si es false la ventana será la de añadir y si es true la ventana será la de modificar
     * @param vistaTabla
     * @param modelo
     */
    public ControladorAddCuad(boolean mod, JFEmpleados vistaTabla, CuadrillaDAO modelo){
        this.vistaAdd = new JFCuadAdd();
        this.vistaTabla = vistaTabla;
        this.modeloCuad = modelo;
        this.modLista = new DefaultListModel();
        this.accionEsMod = mod;
        
        if(mod){//Modificar
            this.vistaAdd.botonAceptar.setText("Modificar");
        }else{//Añadir
            this.vistaAdd.botonAceptar.setText("Añadir");
        }
        //Asociar Action listeners
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jCheckBoxFFin.addActionListener(this);
        //Asociar focus listeners
        this.vistaAdd.campoFInicio.addFocusListener(this);
        //Asociar modelo a lsita
        this.vistaAdd.jList.setModel(modLista);
        //Mostrar vista
        this.ocultarLabelErr();
        this.vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(this.vistaAdd.botonAceptar)){                  //ACEPTAR
            if(validarCampos()){
                if(accionEsMod){
                    String err = modCuadrilla();
                }else{
                    String err = addCuadrilla();
                }
            }
            
        }else if(ae.getSource().equals(this.vistaAdd.botonCancelar)){            //CANCELAR
            this.vistaAdd.dispose();
            
        }else if(ae.getSource().equals(this.vistaAdd.jCheckBoxFFin)){           //JCHECKBOX FECHA FIN
            if(isFFinSelected()){
                this.vistaAdd.campoFFin.setEnabled(true);
            }else{
                this.vistaAdd.campoFFin.setEnabled(false);
            }
            
        }else{//JRadio
            actualizarLista();
        }
    }

    public void focusGained(FocusEvent fe) {
        
    }

    public void focusLost(FocusEvent fe) {
        validarCampos();
    }

    private boolean validarCampos() {
        boolean res = true;
        String FInicio = this.vistaAdd.campoFInicio.getText();
        String FFin = this.vistaAdd.campoFFin.getText();
        
        this.ocultarLabelErr();
        
        if(this.vistaAdd.campoFInicio == null){
            res=false;
            this.vistaAdd.jErrorFCreacion.setText("Debes introducir una fecha de inicio");
            this.vistaAdd.jErrorFCreacion.setVisible(true);
            
        }else if(FInicio != null && Fechas.toLocalDate(FInicio) == null){
            res=false;
            this.vistaAdd.jErrorFCreacion.setText("Debe tener formato dd/mm/aaaa");
            this.vistaAdd.jErrorFCreacion.setVisible(true);
        }
        
        if(isFFinSelected() && FFin == null){
            res=false;
            this.vistaAdd.jErrorFechaFin.setText("Debes introducir una fecha de fin");
            this.vistaAdd.jErrorFechaFin.setVisible(true);
            
        }else if(isFFinSelected() && Fechas.toLocalDate(FFin) == null){
            res=false;
            this.vistaAdd.jErrorFechaFin.setText("Debe tener formato dd/mm/aaaa");
            this.vistaAdd.jErrorFechaFin.setVisible(true);
        }
        
        if(this.vistaAdd.jList.getSelectedIndex() == -1){
            res = false;
            this.vistaAdd.jErrorEncargado.setText("Debes elegir un empleado como encargado");
            this.vistaAdd.jErrorEncargado.setVisible(true);
        }
        
        return res;
    }

    private String addCuadrilla() {
        String res = "Error:";
        System.out.println("AÑADIENDO BIEN WIIIII");
        if(res.equals("Error:")){
            res="";
        }
        return res;
    }

    private void actualizarLista() {
        ArrayList<Trabajador> listaTrabajadores = new ArrayList<Trabajador>();
        if(isEmpleSelected()){
            listaTrabajadores.addAll(modeloTrab.recuperarTrabajadores());
        }else if(isTractSelected()){
            listaTrabajadores.addAll(modeloTrab.recuperarConductores());
        }else if(isEncSelected()){
            listaTrabajadores.addAll(modeloTrab.recuperarEncargados());
        }
        
        this.modLista.clear();
        for (Trabajador t : listaTrabajadores) {
            this.modLista.addElement(t);
        }
    }
    
    private boolean isFFinSelected() {
        return this.vistaAdd.jCheckBoxFFin.isSelected();
    }

    private boolean isEmpleSelected() {
        return this.vistaAdd.jRadioEmpleado.isSelected();
    }

    private boolean isTractSelected() {
        return this.vistaAdd.jRadioTractorista.isSelected();
    }

    private boolean isEncSelected() {
        return this.vistaAdd.jRadioEncargado.isSelected();
    }

    private void ocultarLabelErr() {
        this.vistaAdd.jErrorFCreacion.setText("");
        this.vistaAdd.jErrorFechaFin.setText("");
        this.vistaAdd.jErrorEncargado.setText("");
    }

    private String modCuadrilla() {
        String res = "Error:";
        System.out.println("MODIFICANDO BIEN WIIIII");
        if(res.equals("Error:")){
            res="";
        }
        return res;
    }
}
