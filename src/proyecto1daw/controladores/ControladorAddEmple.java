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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Conductor;
import proyecto1daw.modelo.accesobd.CuadrillaDAO;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.accesobd.TrabajadorDAO;
import proyecto1daw.modelo.Tractor;
import proyecto1daw.vistas.JFEmpleAdd;
import proyecto1daw.vistas.JFEmpleados;

/**
 *
 * @author alumno
 */
public class ControladorAddEmple implements ActionListener,FocusListener{
    private ControladorEmpleado contEmple;
    private JFEmpleAdd vistaAdd;
    private TrabajadorDAO modeloTrab;
    private Trabajador emple;

    /**
     *
     * @param contEmple el controlador que llama a este
     * @param modTrab modelo de acceso a datos relacionados con Trabajador, Encargado y Conductor
     */
    public ControladorAddEmple(ControladorEmpleado contEmple, TrabajadorDAO modTrab) {
        this.contEmple = contEmple;
        this.modeloTrab = modTrab;
        this.vistaAdd = new JFEmpleAdd();
        
        //Asignar listeners
        vistaAdd.botonAceptar.addActionListener(this);
        vistaAdd.botonCancelar.addActionListener(this);
        vistaAdd.botonLimpiar.addActionListener(this);
        vistaAdd.jRadioEmpleado.addActionListener(this);
        vistaAdd.jRadioTractorista.addActionListener(this);
        vistaAdd.jRadioEncargado.addActionListener(this);
        vistaAdd.jCheckBoxFFin.addActionListener(this);
        //Asignar focus listener
        vistaAdd.campoNombre.addFocusListener(this);
        vistaAdd.campoApellidos.addFocusListener(this);
        vistaAdd.campoDni.addFocusListener(this);
        vistaAdd.campoFNac.addFocusListener(this);
        vistaAdd.campoFCont.addFocusListener(this);
        vistaAdd.campoFFinCont.addFocusListener(this);
        vistaAdd.campoTlf.addFocusListener(this);
        vistaAdd.campoSalario.addFocusListener(this);
        vistaAdd.campoPermisos.addFocusListener(this);
        vistaAdd.campoVHEmpresa.addFocusListener(this);
        
        //Mostrar ventana
        this.vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.setVisible(true);
    }

    //CONSTRUCTOR MODIFICAR

    /**
     *
     * @param contEmple el controlador que llama a este
     * @param modTrab modelo de acceso a datos relacionados con Trabajador, Encargado y Conductor
     * @param emple empleado a modificar, si se recibe este parámetro se inicia la ventana en
     * modo modificar y se rellenan los campos con su informacion
     */
    public ControladorAddEmple(ControladorEmpleado contEmple, TrabajadorDAO modTrab,Trabajador emple) {
        this(contEmple, modTrab);
        if(emple != null){
            this.emple=emple;
            this.vistaAdd.botonAceptar.setText("Modificar");
            this.vistaAdd.campoDni.setEnabled(false);
            
            //Cargar datos
            this.vistaAdd.campoNombre.setText(emple.getNombre());
            this.vistaAdd.campoApellidos.setText(emple.getApellidos());
            this.vistaAdd.campoDni.setText(emple.getDni());
            this.vistaAdd.campoFNac.setText(Fechas.toString(emple.getfNacimiento()));
            this.vistaAdd.campoTlf.setText(emple.getTlf());
            this.vistaAdd.campoSalario.setText(emple.getSalario()+"");
            this.vistaAdd.campoFCont.setText(Fechas.toString(emple.getfContratacion()));
            this.vistaAdd.campoFFinCont.setText(Fechas.toString(emple.getfFin()));
            if(emple instanceof Conductor){
                this.vistaAdd.jRadioTractorista.setSelected(true);
                this.vistaAdd.campoPermisos.setText(((Conductor) emple).getPermisos());
                this.vistaAdd.campoPermisos.setEnabled(true);
                this.vistaAdd.jLabelPermisos.setEnabled(true);
                
            }else if(emple instanceof Encargado){
                this.vistaAdd.jRadioEncargado.setSelected(true);
                this.vistaAdd.campoVHEmpresa.setText(((Encargado) emple).getVhEmpresa());
                this.vistaAdd.campoVHEmpresa.setEnabled(true);
                this.vistaAdd.jLabelVHEmpresa.setEnabled(true);
                
            }else{
                this.vistaAdd.jRadioEmpleado.setSelected(true);
            }
        }
    }
    
    /**
     *
     * @param ae objeto generado por la interaccion del usuario con los botones
     */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaAdd.botonAceptar)){              //ACEPTAR
            if(validarDatos()){
                JButton boton = (JButton) ae.getSource();
                if(boton.getText().equalsIgnoreCase("Aceptar")){                //AÑADIR
                    if(addEmple()){
                        JOptionPane.showMessageDialog(vistaAdd, "El empleado ha sido añadido correctamente");
                        contEmple.cargarTablaEmple();
                        this.vistaAdd.dispose();
                   }else{
                       JOptionPane.showMessageDialog(vistaAdd, "Error al añadir el empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
                   }   
                }else{                                                          //MODIFICAR
                    if(modEmple()){
                        JOptionPane.showMessageDialog(vistaAdd, "El empleado ha sido modificado correctamente");
                        contEmple.cargarTablaEmple();
                        this.vistaAdd.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAdd, "Error al modificar el empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else if(ae.getSource().equals(vistaAdd.botonCancelar)){                //CANCELAR
            this.vistaAdd.dispose();
        }else if(ae.getSource().equals(vistaAdd.botonLimpiar)){                 //LIMPIAR   
            limpiarCampos();
        }else if(ae.getSource().equals(vistaAdd.jRadioEmpleado)){               //SELECCIONAR EMPLEADO
            vistaAdd.campoPermisos.setEnabled(false);
            vistaAdd.campoPermisos.setText("");
            vistaAdd.campoVHEmpresa.setEnabled(false);
            vistaAdd.campoVHEmpresa.setText("");
            vistaAdd.jLabelPermisos.setEnabled(false);
            vistaAdd.jLabelVHEmpresa.setEnabled(false);
            
        }else if(ae.getSource().equals(vistaAdd.jRadioTractorista)){            //SELECCIONAR TRACTORISTA
            vistaAdd.campoPermisos.setEnabled(true);
            vistaAdd.campoPermisos.setText("");
            vistaAdd.campoVHEmpresa.setEnabled(false);
            vistaAdd.campoVHEmpresa.setText("");
            vistaAdd.jLabelPermisos.setEnabled(true);
            vistaAdd.jLabelVHEmpresa.setEnabled(false);
            
        }else if(ae.getSource().equals(vistaAdd.jRadioEncargado)){              //SELECCIONAR ENCARGADO
            vistaAdd.campoPermisos.setEnabled(false);
            vistaAdd.campoPermisos.setText("");
            vistaAdd.campoVHEmpresa.setEnabled(true);
            vistaAdd.campoVHEmpresa.setText("");
            vistaAdd.jLabelPermisos.setEnabled(false);
            vistaAdd.jLabelVHEmpresa.setEnabled(true);
            
        }else if(ae.getSource().equals(vistaAdd.jCheckBoxFFin)){
            if(isFechaFinSelected()){
                vistaAdd.campoFFinCont.setEnabled(true);
            }else{
                vistaAdd.campoFFinCont.setEnabled(false);
                vistaAdd.campoFFinCont.setText("");
            }
        }
    }

    private void limpiarCampos() {
        vistaAdd.campoNombre.setText("");
        vistaAdd.campoApellidos.setText("");
        vistaAdd.campoDni.setText("");
        vistaAdd.campoFNac.setText("");
        vistaAdd.campoFCont.setText("");
        vistaAdd.campoFFinCont.setText("");
        vistaAdd.campoTlf.setText("");
        vistaAdd.campoSalario.setText("");
        vistaAdd.campoPermisos.setText("");
        vistaAdd.campoVHEmpresa.setText("");
        //Reset radio button a empleado
        //Desmarcar fecha fin
    }

    private boolean validarDatos() {
        boolean res = true;
        res = validarNombre(res);
        res = validarApellidos(res);
        res = validarDni(res);
        res = validarFNac(res);
        res = validarFCont(res);
        res = ValidarFFin(res);
        res = validarTlf(res);
        res = validarSalario(res);
        res = validarPermisos(res);
        res = validarVhEmpresa(res);
        
        return res;
    }

    private boolean validarVhEmpresa(boolean res) {
        if(isEncargadoSelected() && (vistaAdd.campoVHEmpresa.getText() == null || vistaAdd.campoPermisos.getText().equals(""))){
            vistaAdd.errVhEmpresa.setText("Si no tiene vehiculo dejalo en blanco");
        }else if(isEncargadoSelected()){//Si hay texto
            String m = vistaAdd.campoVHEmpresa.getText();
            if(Tractor.validarMatriculaCoche(m)){
                vistaAdd.errVhEmpresa.setText(" ");
            }else{
                vistaAdd.errVhEmpresa.setText("La matrícula debe ser NNNNLLL");
                res=false;
            }
        }
        return res;
    }

    private boolean validarPermisos(boolean res) {
        if(isTractoristaSelected() && (vistaAdd.campoPermisos.getText() == null || vistaAdd.campoPermisos.getText().equals(""))){
            res=false;
            vistaAdd.errPermisos.setText("Si no tiene permisos introduce 'No'");
        }else{
            vistaAdd.errPermisos.setText(" ");
        }
        return res;
    }

    private boolean validarSalario(boolean res) {
        if(vistaAdd.campoSalario.getText() == null || vistaAdd.campoSalario.getText().equals("")){
            res=false;
            vistaAdd.errSalario.setText("El salario es obligatorio");
        }else{
            try{
                Integer.parseInt(vistaAdd.campoSalario.getText());
            }catch(NumberFormatException e){
                res=false;
                vistaAdd.errSalario.setText("Solo números y sin decimales");
            }
            vistaAdd.errSalario.setText(" ");
        }
        return res;
    }

    private boolean validarTlf(boolean res) {
        if(vistaAdd.campoTlf.getText() == null || vistaAdd.campoTlf.getText().equals("")){
            res=false;
            vistaAdd.errTlf.setText("El tlf es obligatorio");
        }else{
            String num = vistaAdd.campoTlf.getText();
            if(num.length() == 9){
                try{
                    Integer.parseInt(vistaAdd.campoTlf.getText());
                }catch(NumberFormatException e){
                    res=false;
                    vistaAdd.errTlf.setText("Solo puede contener numeros");
                }
                vistaAdd.errTlf.setText(" ");
            }else{
                vistaAdd.errTlf.setText("El num debe tener 9 digitos");
            }
        }
        return res;
    }

    private boolean ValidarFFin(boolean res) {
        if(isFechaFinSelected() && (vistaAdd.campoFFinCont.getText() == null ||vistaAdd.campoFFinCont.getText().equals(""))){
            res=false;
            vistaAdd.errFFin.setText("Debes introducir una fecha");
        }else if(!isFechaFinSelected()){
            vistaAdd.errFFin.setText(" ");
        }else{
            String fecha = vistaAdd.campoFFinCont.getText();
            if(Fechas.toLocalDate(fecha) == null){
                res=false;
                vistaAdd.errFFin.setText("La fecha debe ser dd/mm/aaaa");
            }else{
                vistaAdd.errFFin.setText(" ");
            }
        }
        return res;
    }

    private boolean validarFCont(boolean res) {
        if(vistaAdd.campoFCont.getText() == null ||vistaAdd.campoFCont.getText().equals("")){
            res=false;
            vistaAdd.errFCont.setText("La fecha de cont es obligatoria");
        }else{
            String fechaSt = vistaAdd.campoFCont.getText();
            if(Fechas.toLocalDate(fechaSt) == null){
                res=false;
                vistaAdd.errFCont.setText("La fecha debe ser dd/mm/aaaa");
            }else{
                LocalDate fecha = Fechas.toLocalDate(vistaAdd.campoFCont.getText());
                if(validarFNac(res)){
                    LocalDate fechaNac = Fechas.toLocalDate(vistaAdd.campoFNac.getText());
                    if(fecha.isBefore(fechaNac.plusYears(16))){
                        //Si tiene meno de 16 años no es válido
                        res=false;
                        vistaAdd.errFCont.setText("Es menor de 16");
                    }else{
                        vistaAdd.errFCont.setText(" ");
                    }
                }else{
                    vistaAdd.errFCont.setText(" ");
                }
            }
        }
        return res;
    }

    private boolean validarFNac(boolean res) {
        if(vistaAdd.campoFNac.getText() == null){
            res=false;
            vistaAdd.errFNac.setText("La fecha nac es obligatoria");
        }else{
            String fecha = vistaAdd.campoFNac.getText();
            if(Fechas.toLocalDate(fecha) == null){
                res=false;
                vistaAdd.errFNac.setText("La fecha debe ser dd/mm/aaaa");
            }else{
                vistaAdd.errFNac.setText(" ");
            }
        }
        return res;
    }

    private boolean validarDni(boolean res) {
        if(vistaAdd.campoDni.getText() == null){
            res=false;
            vistaAdd.errDni.setText("El DNI es obligatorio");
        }else{
            if(Fechas.validarDni(vistaAdd.campoDni.getText())){
                vistaAdd.errDni.setText(" ");
            }else{
                res=false;
                vistaAdd.errDni.setText("DNI no válido");
            }
        }
        return res;
    }

    private boolean validarApellidos(boolean res) {
        if(vistaAdd.campoApellidos.getText() == null || vistaAdd.campoApellidos.getText().equals("")){
            res=false;
            vistaAdd.errApellidos.setText("Los apellidos son obligatorios");
        }else{
            String nom = vistaAdd.campoNombre.getText();
            for (int i = 0; i < nom.length(); i++) {
                if(!Character.isAlphabetic(nom.charAt(i))){
                    res=false;
                }
                if(!res){
                    vistaAdd.errApellidos.setText("Solo se permiten carac. alfabéticos");
                }else{
                    vistaAdd.errApellidos.setText(" ");
                }
            }
        }
        return res;
    }

    private boolean validarNombre(boolean res) {
        if(vistaAdd.campoNombre.getText() == null || vistaAdd.campoNombre.getText().equals("")){
            res=false;
            vistaAdd.errNombre.setText("El nombre es obligatorio");
        }else{
            String nom = vistaAdd.campoNombre.getText();
            for (int i = 0; i < nom.length(); i++) {
                if(!Character.isAlphabetic(nom.charAt(i))){
                    res=false;
                }
                if(!res){
                    vistaAdd.errNombre.setText("Solo se permiten carac. alfabéticos");
                }else{
                    vistaAdd.errNombre.setText(" ");
                }
            }
        }
        return res;
    }

    private boolean isFechaFinSelected() {
        return vistaAdd.jCheckBoxFFin.isSelected();
    }

    private boolean isEmpleadoSelected() {
        return vistaAdd.jRadioEmpleado.isSelected();
    }
    
    private boolean isTractoristaSelected() {
        return vistaAdd.jRadioTractorista.isSelected();
    }

    private boolean isEncargadoSelected() {
        return vistaAdd.jRadioEncargado.isSelected();
    }

    private boolean addEmple() {
        boolean res = true;
        if(isEmpleadoSelected()){
            res=this.modeloTrab.addTrabajador(getEmpleado());
        }else if(isTractoristaSelected()){
            res=this.modeloTrab.addConductor(getConductor());
        }else{
            res=this.modeloTrab.addEncargado(getEncargado());
        }
        
        return res;
    }

    private boolean modEmple() {
        boolean res = true;
        
        boolean cambioPuesto = false;
        if(isEmpleadoSelected() && !(emple instanceof Trabajador)){
            cambioPuesto = true;
        }else if(isTractoristaSelected() && !(emple instanceof Conductor)){
            cambioPuesto = true;
        }else if(isEncargadoSelected() && !(emple instanceof Encargado)){
            cambioPuesto = true;
        }
        
        if(cambioPuesto){
            //Hay que realizar un cambio en las tablas
            //Preguntar por fFin
            LocalDate fFinContrato = null;
            if(vistaAdd.campoFFinCont.getText() == null){
                JOptionPane.showMessageDialog(vistaAdd, "No has puesto fecha de fin de contrato"
                        + "\nSe usará la de hoy. Puedes modificarlo más tarde");
            }else{
                fFinContrato = Fechas.toLocalDate(vistaAdd.campoFFinCont.getText());
            }
            
            //Carga el objeto trabajador,conductor o encargado
            //El metodo de la clase DAO se encarga de diferenciar que clase de objeto es
            //ejecutando un codigo u otro
            
            //Se ejecutan los métodos de actualizar antes de cambiar de clase por
            //si se ha modificado algo mas
            
            //Y se borra antes de realizar el cambio por si ya ejerció en ese puesto
            if(isEmpleadoSelected()){
                actualizarEmpleado();
                if(emple instanceof Conductor){
                    emple=getConductor();
                }else{//Encargado
                    emple=getEncargado();
                }
                if(!modeloTrab.borrarTrabajador(emple.getDni())){
                    res = false;
                }
                if(!modeloTrab.ascensoTrabajador(emple, fFinContrato)){
                    res=false;
                }
                
            }else if(isTractoristaSelected()){
                actualizarConductor();
                if(emple instanceof Encargado){
                    emple=getEncargado();
                }else{//Empleado
                    emple=getEncargado();
                }
                if(!modeloTrab.borrarConductor(emple.getDni())){
                    res = false;
                }
                if(!modeloTrab.ascensoConductor(emple, fFinContrato)){
                    res=false;
                }
                
            }else if(isEncargadoSelected()){
                actualizarEncargado();
                if(emple instanceof Conductor){
                    emple=getConductor();
                }else{//Empleado
                    emple = getEmpleado();
                }
                if(!modeloTrab.borrarEncargado(emple.getDni())){
                    res = false;
                }
                if(!modeloTrab.ascensoEncargado(emple, fFinContrato)){
                    res=false;
                }
            }
            
        //SOLO ES MODIFICAR DATOS
        }else{
            if(isEmpleadoSelected()){
                res=actualizarEmpleado();
            }else if(isTractoristaSelected()){
                res=actualizarConductor();
            }else if(isEncargadoSelected()){
                res=actualizarEncargado();
            }
        }
        
        return res;
    }

    private boolean actualizarEncargado() {
        boolean res = true;
        emple = getEncargado();
        if(!modeloTrab.actualizarCampoEnc(emple.getDni(), "NOMBRE", emple.getNombre())){
        	res=false;
        }
        if(!modeloTrab.actualizarCampoEnc(emple.getDni(), "APELLIDOS", emple.getApellidos())){
            res=false;
        }
        if(!modeloTrab.actualizarCampoEnc(emple.getDni(), "F_NAC", Fechas.toString(emple.getfNacimiento()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoEnc(emple.getDni(), "F_CONT", Fechas.toString(emple.getfContratacion()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoEnc(emple.getDni(), "F_FIN", Fechas.toString(emple.getfFin()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoEnc(emple.getDni(), "TLF", emple.getTlf()+"")){
            res=false;
        }
        if(!modeloTrab.actualizarCampoEnc(emple.getDni(), "SALARIO", emple.getSalario()+"")){
            res=false;
        }
        if(!modeloTrab.actualizarCampoEnc(emple.getDni(), "VH_EMPRESA", ((Encargado)emple).getVhEmpresa())){
            res=false;
        }
        return res;
    }

    private boolean actualizarConductor() {
        boolean res = true;
        emple = getConductor();
        if(!modeloTrab.actualizarCampoCond(emple.getDni(), "NOMBRE", emple.getNombre())){
            res=false;
        }
        if(!modeloTrab.actualizarCampoCond(emple.getDni(), "APELLIDOS", emple.getApellidos())){
            res=false;
        }
        if(!modeloTrab.actualizarCampoCond(emple.getDni(), "F_NAC", Fechas.toString(emple.getfNacimiento()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoCond(emple.getDni(), "F_CONT", Fechas.toString(emple.getfContratacion()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoCond(emple.getDni(), "F_FIN", Fechas.toString(emple.getfFin()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoCond(emple.getDni(), "TLF", emple.getTlf()+"")){
            res=false;
        }
        if(!modeloTrab.actualizarCampoCond(emple.getDni(), "SALARIO", emple.getSalario()+"")){
            res=false;
        }
        if(!modeloTrab.actualizarCampoCond(emple.getDni(), "PERMISOS", ((Conductor)emple).getPermisos())){
            res=false;
        }
        return res;
    }

    private boolean actualizarEmpleado() {
        boolean res = true;
        emple = getEmpleado();
        if(!modeloTrab.actualizarCampoTrab(emple.getDni(), "NOMBRE", emple.getNombre())){
            res=false;
        }
        if(!modeloTrab.actualizarCampoTrab(emple.getDni(), "APELLIDOS", emple.getApellidos())){
            res=false;
        }
        if(!modeloTrab.actualizarCampoTrab(emple.getDni(), "F_NAC", Fechas.toString(emple.getfNacimiento()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoTrab(emple.getDni(), "F_CONT", Fechas.toString(emple.getfContratacion()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoTrab(emple.getDni(), "F_FIN", Fechas.toString(emple.getfFin()))){
            res=false;
        }
        if(!modeloTrab.actualizarCampoTrab(emple.getDni(), "TLF", emple.getTlf()+"")){
            res=false;
        }
        if(!modeloTrab.actualizarCampoTrab(emple.getDni(), "SALARIO", emple.getSalario()+"")){
            res=false;
        }
        return res;
    }

    private Trabajador getEmpleado() {
        String dni = vistaAdd.campoDni.getText();
        String nombre = vistaAdd.campoNombre.getText();
        String apellidos = vistaAdd.campoApellidos.getText();
        LocalDate fNacimiento = Fechas.toLocalDate(vistaAdd.campoFNac.getText());
        LocalDate fContratacion = Fechas.toLocalDate(vistaAdd.campoFCont.getText());
        LocalDate fFin = Fechas.toLocalDate(vistaAdd.campoFFinCont.getText());
        String tlf = vistaAdd.campoTlf.getText();
        int salario = Integer.parseInt(vistaAdd.campoSalario.getText());
        
        Trabajador t = new Trabajador(dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        return t;
    }
    
    private Conductor getConductor() {
        String dni = vistaAdd.campoDni.getText();
        String nombre = vistaAdd.campoNombre.getText();
        String apellidos = vistaAdd.campoApellidos.getText();
        LocalDate fNacimiento = Fechas.toLocalDate(vistaAdd.campoFNac.getText());
        LocalDate fContratacion = Fechas.toLocalDate(vistaAdd.campoFCont.getText());
        LocalDate fFin = Fechas.toLocalDate(vistaAdd.campoFFinCont.getText());
        String tlf = vistaAdd.campoTlf.getText();
        int salario = Integer.parseInt(vistaAdd.campoSalario.getText());
        String permisos = vistaAdd.campoPermisos.getText();
        
        Conductor c = new Conductor(permisos, dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        return c;
    }
    
    private Encargado getEncargado() {
        String dni = vistaAdd.campoDni.getText();
        String nombre = vistaAdd.campoNombre.getText();
        String apellidos = vistaAdd.campoApellidos.getText();
        LocalDate fNacimiento = Fechas.toLocalDate(vistaAdd.campoFNac.getText());
        LocalDate fContratacion = Fechas.toLocalDate(vistaAdd.campoFCont.getText());
        LocalDate fFin = Fechas.toLocalDate(vistaAdd.campoFFinCont.getText());
        String tlf = vistaAdd.campoTlf.getText();
        int salario = Integer.parseInt(vistaAdd.campoSalario.getText());
        String VhEmpresa = vistaAdd.campoVHEmpresa.getText();
        
        Encargado enc = new Encargado(VhEmpresa, dni, nombre, apellidos, fNacimiento, fContratacion, fFin, tlf, salario);
        return enc;
    }

    /**
     *
     * @param fe
     */
    public void focusGained(FocusEvent fe) {
        
    }

    /**
     *
     * @param fe
     */
    public void focusLost(FocusEvent fe) {
        if(fe.getSource().equals(vistaAdd.campoNombre)){
            validarNombre(true);
        }else if(fe.getSource().equals(vistaAdd.campoApellidos)){
            validarApellidos(true);
        }else if(fe.getSource().equals(vistaAdd.campoDni)){
            validarDni(true);
        }else if(fe.getSource().equals(vistaAdd.campoFNac)){
            validarFNac(true);
        }else if(fe.getSource().equals(vistaAdd.campoFCont)){
            validarFCont(true);
        }else if(fe.getSource().equals(vistaAdd.campoFFinCont)){
            ValidarFFin(true);
        }else if(fe.getSource().equals(vistaAdd.campoTlf)){
            validarTlf(true);
        }else if(fe.getSource().equals(vistaAdd.campoSalario)){
            validarSalario(true);
        }else if(fe.getSource().equals(vistaAdd.campoPermisos)){
            validarPermisos(true);
        }else if(fe.getSource().equals(vistaAdd.campoVHEmpresa)){
            validarVhEmpresa(true);
        }
    }
}
