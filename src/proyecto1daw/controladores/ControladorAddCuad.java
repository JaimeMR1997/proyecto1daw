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
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Conductor;
import proyecto1daw.modelo.Cuadrilla;
import proyecto1daw.modelo.CuadrillaDAO;
import proyecto1daw.modelo.Encargado;
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
    private ControladorEmpleado contEmple;
    private CuadrillaDAO modeloCuad;
    private TrabajadorDAO modeloTrab;
    private DefaultListModel modLista;
    private boolean accionEsMod;
    private Cuadrilla cuad;
    
    /**
     *
     * @param contEmple Recibe el controlador que invocó a este como parametro 
     * @param modelo Modelo de acceso a datos relacinados con la clase cuadrilla
     */
    public ControladorAddCuad(ControladorEmpleado contEmple, CuadrillaDAO modelo){
        this.vistaAdd = new JFCuadAdd();
        this.contEmple = contEmple;
        this.modeloCuad = modelo;
        this.modeloTrab = new TrabajadorDAO();
        this.modLista = new DefaultListModel<Trabajador>();
        boolean accionEsMod = false;
        
        //Asociar Action listeners
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jCheckBoxFFin.addActionListener(this);
        this.vistaAdd.jRadioEmpleado.addActionListener(this);
        this.vistaAdd.jRadioTractorista.addActionListener(this);
        this.vistaAdd.jRadioEncargado.addActionListener(this);
        //Asociar focus listeners
        this.vistaAdd.campoFInicio.addFocusListener(this);
        //Asociar modelo a lista
        this.vistaAdd.jList.setModel(modLista);
        
        this.vistaAdd.botonAceptar.setText("Añadir");
        //Rellenar datos
        this.vistaAdd.campoId.setText(generarIdCuad());
        this.vistaAdd.campoFInicio.setText(Fechas.toString(LocalDate.now()));
        //Cargar lista trabajadores
        this.actualizarLista();
        //Mostrar vista
        this.vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.setVisible(true);
    }

    /**
     *
     * @param contEmple El controlador que invoco a este
     * @param modeloCuad Modelo de acceso a datos relacinados con la clase cuadrilla
     * @param cuad Cuadrilla a modificar,se carga´ran lso datos de esta en los campos
     * de la ventana 
     * @see Cuadrilla
     * @see JFCuadAdd
     */
    public ControladorAddCuad(ControladorEmpleado contEmple, CuadrillaDAO modeloCuad,Cuadrilla cuad) {
        this(contEmple, modeloCuad);
        
        this.cuad = cuad;
        this.accionEsMod = true;
        this.vistaAdd.botonAceptar.setText("Modificar");
        this.vistaAdd.campoId.setText(cuad.getId());
        this.vistaAdd.campoFInicio.setText(Fechas.toString(cuad.getfInicio()));
        this.vistaAdd.campoFInicio.setEnabled(false);
        if(cuad.getfFin() != null){
            this.vistaAdd.campoFFin.setText(Fechas.toString(cuad.getfFin()));
        }
        this.vistaAdd.jRadioEmpleado.setEnabled(false);
        this.vistaAdd.jRadioTractorista.setEnabled(false);
        this.vistaAdd.jRadioEncargado.setEnabled(false);
        this.vistaAdd.jRadioEncargado.setSelected(true);
        this.actualizarLista();
        this.vistaAdd.jList.setEnabled(false);
        
    }
    
    /**
     *
     * @param ae objeto generado por la interaccion del usuario con los botones
     */
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(this.vistaAdd.botonAceptar)){                  //ACEPTAR
            if(validarCampos()){
                if(accionEsMod){
                    String err = modCuadrilla();                                //MODIFICAR
                    if(err.charAt(0) == 'E'){ //Ha habido error
                        JOptionPane.showMessageDialog(vistaAdd, err, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{// Sin error
                        JOptionPane.showMessageDialog(vistaAdd, "Se ha modificcado correctamente la cuadrilla");
                        contEmple.cargarTablaCuadrillas();
                        this.vistaAdd.dispose();
                    }
                }else{
                    String err = addCuadrilla();                                //AÑADIR NUEVA
                    if(err.charAt(0) == 'E'){ //Ha habido error
                        JOptionPane.showMessageDialog(vistaAdd, err, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{// Sin error
                        JOptionPane.showMessageDialog(vistaAdd, "Se ha añadido correctamente la cuadrilla\ny se ha asignado el encargado");
                        contEmple.cargarTablaCuadrillas();
                        this.vistaAdd.dispose();
                    }
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

    private boolean validarCampos() {
        boolean res = true;
        String FInicio = this.vistaAdd.campoFInicio.getText();
        String FFin = this.vistaAdd.campoFFin.getText();
        
        if(this.vistaAdd.campoFInicio == null){
            res=false;
            this.vistaAdd.jErrorFCreacion.setText("Debes introducir una fecha de inicio");
            
        }else if(FInicio != null && Fechas.toLocalDate(FInicio) == null){
            res=false;
            this.vistaAdd.jErrorFCreacion.setText("Debe tener formato dd/mm/aaaa");
        }else{
            this.vistaAdd.jErrorFCreacion.setText(" ");
        }
        
        if(isFFinSelected() && FFin == null){
            res=false;
            this.vistaAdd.jErrorFechaFin.setText("Debes introducir una fecha de fin");
            
        }else if(isFFinSelected() && Fechas.toLocalDate(FFin) == null){
            res=false;
            this.vistaAdd.jErrorFechaFin.setText("Debe tener formato dd/mm/aaaa");
        }else{
            this.vistaAdd.jErrorFechaFin.setText(" ");
        }
        
        if(!accionEsMod){
            if(this.vistaAdd.jList.getSelectedIndex() == -1){
                res = false;
                this.vistaAdd.jErrorEncargado.setText("Debes un empleado como encargado");
            }else{
                this.vistaAdd.jErrorEncargado.setText(" ");
            }
        }
        
        return res;
    }

    private String addCuadrilla() {
        String res = "Error ";
        Cuadrilla cuad = getCuadrilla();
        if(!modeloCuad.addCuadrilla(cuad)){
            res+=" al añadir la cuadrilla";
        }
        //AÑADIR ENCARGADO O ASCENDER 
        Object o = vistaAdd.jList.getSelectedValue();
        Trabajador t = (Trabajador) o;
        
        if(t instanceof Conductor){
            //t = new Conductor(dni);
            if(!modeloTrab.ascensoEncargado(t, LocalDate.now())){
                res+="\nAl ascender al conductor a encargado";
            }
        }else if(t instanceof Encargado){
            //t = new Encargado(dni);
            //modeloTrab.as
        }else{
           // t = new Trabajador(dni);
            if(!modeloTrab.ascensoEncargado(t, LocalDate.now())){
                res+="\nAl ascender al trabajador a encargado";
            }
        }
        if(res.equals("Error ")){ //Si no ha habido error el texto sera solo "Error "
            if(!modeloCuad.asignarEncargado(t,cuad)){ //Si hay error se almacena
                res+="\nAl asignar el encargado";
            }else{
                res=" ";//No ha habido ningun error
            }
        }
        return res;
    }

    private void actualizarLista() {
        ArrayList<Trabajador> listaTrabajadores = new ArrayList<Trabajador>();
        if(isEmpleSelected()){
            if(modeloTrab.recuperarTrabajadores().size() > 0){
                listaTrabajadores.addAll(modeloTrab.recuperarTrabajadoresLibres());
            }
        }else if(isTractSelected()){
            if(modeloTrab.recuperarConductores().size() > 0){
                listaTrabajadores.addAll(modeloTrab.recuperarConductores());
            }
        }else if(isEncSelected()){
            if(modeloTrab.recuperarEncargados().size() > 0){
                listaTrabajadores.addAll(modeloTrab.recuperarEncargadosLibres());
            }
        }
        
        this.modLista.clear();
        for (Trabajador t : listaTrabajadores) {
            if(t.getfFin() != null && t.getfFin().isBefore(LocalDate.now())){
                    continue;//Se salta los empleados con contrato finalizado
                }
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

    private String modCuadrilla() {
        String res = "Error:";
        Cuadrilla c = getCuadrilla();
        
        if(!modeloCuad.actualizarCampoCuadrilla(c.getId(), "F_CREACION", Fechas.toString(c.getfInicio()))){
            res+="\nAl actualizar la fecha de creacion";
        }
        if(isFFinSelected()){
            if(!modeloCuad.actualizarCampoCuadrilla(c.getId(), "F_FIN", Fechas.toString(c.getfFin()))){
                res+="\nAl actualizar la fecha de fin";
            }
        }
        
        if(res.equals("Error:")){
            res=" ";
        }
        return res;
    }

    private String generarIdCuad() {
        int n = modeloCuad.contarCuadrillas()+1;
        while(modeloCuad.buscarCuadrillaPorId(n+"") != null){
            n++;
        }
        return n+"";
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
        validarCampos();
    }

    private Cuadrilla getCuadrilla() {
        String idCuadrilla = vistaAdd.campoId.getText();
        LocalDate fInicio = Fechas.toLocalDate(vistaAdd.campoFInicio.getText());
        LocalDate fFin = Fechas.toLocalDate(vistaAdd.campoFFin.getText());
        
        Cuadrilla c = new Cuadrilla(idCuadrilla, fInicio, fFin);
        return c;
    }
}
