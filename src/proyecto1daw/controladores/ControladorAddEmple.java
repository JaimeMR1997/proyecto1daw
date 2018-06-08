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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Conductor;
import proyecto1daw.modelo.CuadrillaDAO;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.TrabajadorDAO;
import proyecto1daw.vistas.JFEmpleAdd;
import proyecto1daw.vistas.JFEmpleados;

/**
 *
 * @author alumno
 */
public class ControladorAddEmple implements ActionListener,FocusListener{
    private JFEmpleados vistaTabla;
    private JFEmpleAdd vistaAdd;
    private CuadrillaDAO modeloCuad;
    private TrabajadorDAO modTrab;

    public ControladorAddEmple(JFEmpleados vistaTabla, CuadrillaDAO modeloCuad, TrabajadorDAO modTrab) {
        this.vistaTabla = vistaTabla;
        this.modeloCuad = modeloCuad;
        this.modTrab = modTrab;
        this.vistaAdd = new JFEmpleAdd();
        
        //Asignar listeners
        vistaAdd.botonAceptar.addActionListener(this);
        vistaAdd.botonCancelar.addActionListener(this);
        vistaAdd.botonLimpiar.addActionListener(this);
        //Mostrar ventana
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.setVisible(true);
    }

    public ControladorAddEmple(JFEmpleados vistaTabla, CuadrillaDAO modeloCuad, TrabajadorDAO modTrab,boolean mod) {
        this(vistaTabla, modeloCuad, modTrab);
        if(mod){
            this.vistaAdd.botonAceptar.setText("Modificar");
            //Bloquear radio button
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaAdd.botonAceptar)){              //ACEPTAR
            if(validarDatos()){
                JButton boton = (JButton) ae.getSource();
                if(boton.getText().equalsIgnoreCase("Aceptar")){                //AÑADIR
                    if(addEmple()){
                        JOptionPane.showMessageDialog(boton, "El empleado ha sido añadido correctamente");
                   }else{
                       JOptionPane.showMessageDialog(vistaAdd, "Error al añadir el empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
                   }   
                }else{                                                          //MODIFICAR
                    if(modEmple()){
                    JOptionPane.showMessageDialog(boton, "El empleado ha sido modificado correctamente");
                }else{
                    JOptionPane.showMessageDialog(vistaAdd, "Error al modificar el empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                }
            }
        }else if(ae.getSource().equals(vistaAdd.botonCancelar)){                //CANCELAR
            this.vistaAdd.dispose();
        }else if(ae.getSource().equals(vistaAdd.botonLimpiar)){                 //LIMPIAR   
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        vistaAdd.campoNombre.setText("");
        vistaAdd.campoApellido.setText("");
        vistaAdd.campoDni.setText("");
        vistaAdd.campoFNac.setText("");
        vistaAdd.campoFCont.setText("");
        vistaAdd.campoFFin.setText("");
        vistaAdd.campoTlf.setText("");
        vistaAdd.campoSalario.setText("");
        vistaAdd.campoPermisos.setText("");
        vistaAdd.campoVHEmpresa.setText("");
        //Reset radio button a empleado
        //Desmarcar fecha fin
    }

    private boolean validarDatos() {
        boolean res = true;
        if(vistaAdd.campoNombre.getText() == null){
            res=false;
        }else{
            String nom = vistaAdd.campoNombre.getText();
            for (int i = 0; i < nom.length(); i++) {
                if(!Character.isAlphabetic(nom.charAt(i))){
                    res=false;
                }
            }
        }
        if(vistaAdd.campoApellido.getText() == null){
            res=false;
        }else{
            String nom = vistaAdd.campoNombre.getText();
            for (int i = 0; i < nom.length(); i++) {
                if(!Character.isAlphabetic(nom.charAt(i))){
                    res=false;
                }
            }
        }
        if(vistaAdd.campoDni.getText() == null){
            res=false;
        }else{
            Fechas.validarDni();
        }
        if(vistaAdd.campoFNac.getText() == null){
            res=false;
        }else{
            String fecha = vistaAdd.campoFNac.getText();
            if(Fechas.toLocalDate(fecha) == null){
                res=false;
            }
        }
        if(vistaAdd.campoFCont.getText() == null){
            res=false;
        }else{
            String fecha = vistaAdd.campoFCont.getText();
            if(Fechas.toLocalDate(fecha) == null){
                res=false;
            }
        }
        if(isFechaFinSelected() && vistaAdd.campoFFin.getText() == null){
            res=false;
        }else{
            String fecha = vistaAdd.campoFFin.getText();
            if(Fechas.toLocalDate(fecha) == null){
                res=false;
            }
        }
        if(vistaAdd.campoTlf.getText() == null){
            res=false;
        }else{
            try{
                Integer.parseInt(vistaAdd.campoTlf.getText());
            }catch(NumberFormatException e){
                res=false;
            }
        }
        if(vistaAdd.campoSalario.getText() == null){
            res=false;
        }else{
            try{
                Integer.parseInt(vistaAdd.campoTlf.getText());
            }catch(NumberFormatException e){
                res=false;
            }
        }
        if(isTractoristaSelected() && vistaAdd.campoPermisos.getText() == null){
            res=false;
        }
        if(isEncargadoSelected() && vistaAdd.campoVHEmpresa.getText() == null){
            res=false;
        }
        
        return res;
    }

    private boolean isFechaFinSelected() {
        boolean res = true;
        return res;
    }

    private boolean isTractoristaSelected() {
        boolean res = true;
        return res;
    }

    private boolean isEncargadoSelected() {
        boolean res = true;
        return res;
    }

    private boolean addEmple() {
        boolean res = true;
        if(isEmpleadoSelected()){
            res=this.modTrab.addTrabajador(getEmpleado());
        }else if(isTractoristaSelected()){
            res=this.modTrab.addConductor(getConductor());
        }else{
            res=this.modTrab.addEncargado(getEncargado());
        }
        
        return res;
    }

    private boolean modEmple() {
        boolean res = true;
        return res;
    }

    private boolean isEmpleadoSelected() {
        boolean res = true;
        return res;
    }

    private Trabajador getEmpleado() {
        String dni = vistaAdd.campoDni.getText();
        String nombre = vistaAdd.campoNombre.getText();
        String apellidos = vistaAdd.campoApellido.getText();
        LocalDate fNacimiento = Fechas.toLocalDate(vistaAdd.campoFNac.getText());
        LocalDate fContratacion = Fechas.toLocalDate(vistaAdd.campoFCont.getText());
        LocalDate fFin = Fechas.toLocalDate(vistaAdd.campoFFin.getText());
        String tlf = vistaAdd.campoTlf.getText();
        int salario = Integer.parseInt(vistaAdd.campoSalario.getText());
        
        Trabajador t = new Trabajador(dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        return t;
    }
    
    private Conductor getConductor() {
        String dni = vistaAdd.campoDni.getText();
        String nombre = vistaAdd.campoNombre.getText();
        String apellidos = vistaAdd.campoApellido.getText();
        LocalDate fNacimiento = Fechas.toLocalDate(vistaAdd.campoFNac.getText());
        LocalDate fContratacion = Fechas.toLocalDate(vistaAdd.campoFCont.getText());
        LocalDate fFin = Fechas.toLocalDate(vistaAdd.campoFFin.getText());
        String tlf = vistaAdd.campoTlf.getText();
        int salario = Integer.parseInt(vistaAdd.campoSalario.getText());
        String permisos = vistaAdd.campoPermisos.getText();
        
        Conductor c = new Conductor(permisos, dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        return c;
    }
    
    private Encargado getEncargado() {
        String dni = vistaAdd.campoDni.getText();
        String nombre = vistaAdd.campoNombre.getText();
        String apellidos = vistaAdd.campoApellido.getText();
        LocalDate fNacimiento = Fechas.toLocalDate(vistaAdd.campoFNac.getText());
        LocalDate fContratacion = Fechas.toLocalDate(vistaAdd.campoFCont.getText());
        LocalDate fFin = Fechas.toLocalDate(vistaAdd.campoFFin.getText());
        String tlf = vistaAdd.campoTlf.getText();
        int salario = Integer.parseInt(vistaAdd.campoSalario.getText());
        String VhEmpresa = vistaAdd.campoVHEmpresa.getText();
        
        Encargado enc = new Encargado(VhEmpresa, dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        return enc;
    }

    public void focusGained(FocusEvent fe) {
        
    }

    public void focusLost(FocusEvent fe) {
        validarDatos();
    }
}
