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
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Cuadrilla;
import proyecto1daw.modelo.CuadrillaDAO;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.modelo.TrabajadorDAO;
import proyecto1daw.modelo.Trabajo;
import proyecto1daw.vistas.JFEmpleados;
import proyecto1daw.vistas.JFTrabajoAdd;

/**
 *
 * @author alumno
 */
public class ControladorAddTrabajo implements ActionListener,FocusListener{
    private JFEmpleados vistaTabla;
    private JFTrabajoAdd vistaAdd;
    private CuadrillaDAO modeloCuad;
    private TrabajadorDAO modeloTrab;
    private FincaDAO modeloFinca;
    private ExplotacionDAO modeloExp;
    private DefaultComboBoxModel modComboCuad;
    private DefaultComboBoxModel modComboFinca;
    private DefaultComboBoxModel modComboExp;

    public ControladorAddTrabajo(JFEmpleados vistaTabla, CuadrillaDAO modeloCuad, TrabajadorDAO modeloTrab) {
        this.vistaTabla = vistaTabla;
        this.vistaAdd = new JFTrabajoAdd();
        this.modeloCuad = modeloCuad;
        this.modeloTrab = modeloTrab;
        
        this.modeloFinca = new FincaDAO();
        this.modeloExp = new ExplotacionDAO();
        this.modComboCuad = new DefaultComboBoxModel();
        this.modComboFinca = new DefaultComboBoxModel();
        this.modComboExp = new DefaultComboBoxModel();
        //Asociar modelos desplegables
        this.vistaAdd.jComboCuad.setModel(modComboCuad);
        this.vistaAdd.jComboFinca.setModel(modComboFinca);
        this.vistaAdd.jComboExp.setModel(modComboExp);
        //Cargar desplegables
        cargarDespCuad();
        cargarDespFinca();
        cargarDespExp();
        //Asociar Actions listeners
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jComboCuad.addActionListener(this);
        this.vistaAdd.jComboFinca.addActionListener(this);
        this.vistaAdd.jComboExp.addActionListener(this);
        //Asociar focus listeners
        this.vistaAdd.campoFecha.addFocusListener(this);
        this.vistaAdd.campoTarea.addFocusListener(this);
        this.vistaAdd.jSpinnerHoras.addFocusListener(this);
        this.vistaAdd.jComboCuad.addFocusListener(this);
        this.vistaAdd.jComboFinca.addFocusListener(this);
        this.vistaAdd.jComboExp.addFocusListener(this);
        //Mostrar ventana
        this.vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.setVisible(true);
    }
    
    //Constructor Modificar
    public ControladorAddTrabajo(JFEmpleados vistaTabla, CuadrillaDAO modeloCuad, TrabajadorDAO modeloTrab,boolean mod) {
        this(vistaTabla, modeloCuad, modeloTrab);
        if(mod){                                                                //Constructor Modificar
            this.vistaAdd.botonAceptar.setText("Modificar");
            this.vistaAdd.jComboCuad.setEnabled(false);
            this.vistaAdd.jComboFinca.setEnabled(false);
            this.vistaAdd.jComboExp.setEnabled(false);
            this.vistaAdd.campoFecha.setEnabled(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaAdd.botonAceptar)){
            if(validarCampos()){
                JButton boton = (JButton) ae.getSource();
                if(boton.getText().equalsIgnoreCase("Aceptar")){                    //AÑADIR NUEVO
                    if(addTrabajo()){
                        JOptionPane.showMessageDialog(boton, "El trabajo ha sido añadido correctamente");
                        ControladorEmpleado contEmple = new ControladorEmpleado(vistaTabla);
                        this.vistaAdd.dispose();
                   }else{
                       JOptionPane.showMessageDialog(vistaAdd, "Error al añadir el trabajo", "ERROR", JOptionPane.ERROR_MESSAGE);
                   }   
                    
                }else{                                                              //MODIFICAR
                    if(modTrabajo()){
                        JOptionPane.showMessageDialog(boton, "El trabajo ha sido modificado correctamente");
                        ControladorEmpleado contEmple = new ControladorEmpleado(vistaTabla);
                        this.vistaAdd.dispose();
                   }else{
                        JOptionPane.showMessageDialog(vistaAdd, "Error al modificar el trabajo", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
        }else if(ae.getSource().equals(vistaAdd.botonCancelar)){                    //CANCELAR
            this.vistaAdd.dispose();
            
        }else if(ae.getSource().equals(vistaAdd.jComboCuad)){                       //CUADRILLA SELECCIONADA
            
        }else if(ae.getSource().equals(vistaAdd.jComboFinca)){                      //FINCA SELECCIONADA
            cargarDespExp();
        }
    }
    
    private boolean validarCampos(){
        boolean res= true;
        res = validarCuadrilla(res);
        res = validarFinca(res);
        res = validarExplotacion(res);
        res = validarFecha(res);
        res = validarTarea(res);
        res = validarHoras(res);
        return res;
    }

    private boolean validarHoras(boolean res) {
        if(getHoras() < 1 || getHoras() >9){
            res=false;
            vistaAdd.errHoras.setText("Las horas deben estar entre 1 y 9");
        }else{
            vistaAdd.errHoras.setText(" ");
        }
        return res;
    }

    private boolean validarTarea(boolean res) {
        if(vistaAdd.campoTarea.getText() == null){
            res=false;
            vistaAdd.errTarea.setText("Debes introducir una tarea");
        }else{
            String tarea = vistaAdd.campoTarea.getText();
            for (int i = 0; i < tarea.length(); i++) {
                if(!Character.isAlphabetic(tarea.charAt(i))){
                    if(tarea.charAt(i) != '-' || tarea.charAt(i) != ' '){
                        res=false;
                        vistaAdd.errTarea.setText("Tarea contiene caracteres no permitidos");
                        break;
                    }
                }
            }
        }
        if(res){
            vistaAdd.errTarea.setText(" ");
        }
        return res;
    }

    private boolean validarFecha(boolean res) {
        if(vistaAdd.campoFecha.getText() == null){
            res=false;
            vistaAdd.errFecha.setText("Fecha necesaria dd/mm/aaaa");
        }else{
            String fecha = vistaAdd.campoFecha.getText();
            if(Fechas.toLocalDate(fecha) == null){
                res=false;
                vistaAdd.errFecha.setText("Fecha necesaria dd/mm/aaaa");
            }else{
                vistaAdd.errFecha.setText(" ");
            }
        }
        return res;
    }

    private boolean validarExplotacion(boolean res) {
        if(!isExpSel()){
            res=false;
            vistaAdd.errExplotacion.setText("Debes seleccionar una explotacion");
        }else{
            vistaAdd.errExplotacion.setText(" ");
        }
        return res;
    }

    private boolean validarFinca(boolean res) {
        if(!isFincaSel()){
            res=false;
            vistaAdd.errFinca.setText("Debes seleccionar una finca");
            vistaAdd.errExplotacion.setText("Debes seleccionar una explotacion");
        }else{
            vistaAdd.errFinca.setText(" ");
        }
        return res;
    }

    private boolean validarCuadrilla(boolean res) {
        if(!isCuadSel()){
            res=false;
            vistaAdd.errCuad.setText("Debes seleccionar una cuadrilla");
        }else{
            vistaAdd.errCuad.setText(" ");
        }
        return res;
    }
    
    private boolean isCuadSel(){
            boolean res = true;
            if(vistaAdd.jComboCuad.getSelectedIndex() == -1){
                res=false;
            }
            return res;
    }

    private boolean isFincaSel(){
        boolean res = true;
        if(vistaAdd.jComboFinca.getSelectedIndex() == -1){
            res=false;
        }
        return res;
    }
    
    private boolean isExpSel(){
        boolean res = true;
        if(vistaAdd.jComboExp.getSelectedIndex() == -1){
            res=false;
        }
        return res;
    }

    private void cargarDespCuad() {
        modComboCuad.removeAllElements();
        ArrayList<Cuadrilla> listaCuad = modeloCuad.recuperarTodas();
        for (Cuadrilla c : listaCuad) {
            modComboCuad.addElement(c);
        }
    }

    private void cargarDespFinca() {
        modComboFinca.removeAllElements();
        ArrayList<Finca> listaFincas = modeloFinca.recuperarTodas();
        for (Finca f: listaFincas) {
            modComboFinca.addElement(f);
        }
    }

    private void cargarDespExp() {
        modComboExp.removeAllElements();
        if(vistaAdd.jComboFinca.getSelectedIndex() != -1){
            Finca fSel = (Finca) vistaAdd.jComboFinca.getSelectedItem();
            ArrayList<Explotacion> listaExp = modeloExp.recuperarPorFinca(fSel.getId());
            for (Explotacion exp : listaExp) {
                modComboExp.addElement(exp);
            }
        }
    }
    
    private int getHoras(){
        return ((Integer) vistaAdd.jSpinnerHoras.getValue());
    }

    private boolean addTrabajo() {
        Cuadrilla cuad = (Cuadrilla) vistaAdd.jComboCuad.getSelectedItem();
        String stFecha = vistaAdd.campoFecha.getText();
        LocalDate fecha = Fechas.toLocalDate(stFecha);
        int horas = getHoras();
        String tarea = vistaAdd.campoTarea.getText();
        Explotacion exp = getExp();
        Trabajo t = new Trabajo(cuad.getId(), fecha, horas, tarea, exp.getId());
        
        boolean res=modeloCuad.addTrabajo(t);
        
        return res;
    }

    private boolean modTrabajo() {
        Cuadrilla cuad = (Cuadrilla) vistaAdd.jComboCuad.getSelectedItem();
        String stFecha = vistaAdd.campoFecha.getText();
        LocalDate fecha = Fechas.toLocalDate(stFecha);
        int horas = getHoras();
        String tarea = vistaAdd.campoTarea.getText();
        Explotacion exp = getExp();
        Trabajo t = new Trabajo(cuad.getId(), fecha, horas, tarea, exp.getId());
        
        boolean res = true;
        String errMensaje="Error:";
        if(!modeloCuad.actualizarCampoTrabajo("HORAS",t.getHoras()+"",t)){
            res=false;
            errMensaje+="\nAl actualizar las horas";
        }
        if(!modeloCuad.actualizarCampoTrabajo("TAREA",t.getTarea(),t)){
            res=false;
            errMensaje+="\nAl actualizar la tarea";
        }
        if(!res){
            JOptionPane.showMessageDialog(vistaAdd, errMensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return res;
    }

    private Explotacion getExp() {
        Explotacion exp = null;
        if(isExpSel()){
            exp = (Explotacion) vistaAdd.jComboExp.getSelectedItem();
        }
        return exp;
    }

    public void focusGained(FocusEvent fe) {
        
    }

    public void focusLost(FocusEvent fe) {
        if(fe.getSource().equals(vistaAdd.jComboCuad)){
            validarCuadrilla(true);
        }else if(fe.getSource().equals(vistaAdd.campoFecha)){
            validarFecha(true);
        }else if(fe.getSource().equals(vistaAdd.jSpinnerHoras)){
            validarHoras(true);
        }else if(fe.getSource().equals(vistaAdd.campoTarea)){
            validarTarea(true);
        }else if(fe.getSource().equals(vistaAdd.jComboFinca)){
            validarFinca(true);
        }if(fe.getSource().equals(vistaAdd.jComboExp)){
            validarExplotacion(true);
        }
    }
}
