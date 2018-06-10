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
        this.modeloTrab = new TrabajadorDAO();
        this.modLista = new DefaultListModel<Trabajador>();
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
        this.vistaAdd.jRadioEmpleado.addActionListener(this);
        this.vistaAdd.jRadioTractorista.addActionListener(this);
        this.vistaAdd.jRadioEncargado.addActionListener(this);
        //Asociar focus listeners
        this.vistaAdd.campoFInicio.addFocusListener(this);
        //Asociar modelo a lista
        this.vistaAdd.jList.setModel(modLista);
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

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(this.vistaAdd.botonAceptar)){                  //ACEPTAR
            if(validarCampos()){
                if(accionEsMod){
                    String err = modCuadrilla();                                //MODIFICAR
                    if(err.charAt(0) == 'E'){ //Ha habido error
                        JOptionPane.showMessageDialog(vistaAdd, err, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{// Sin error
                        JOptionPane.showMessageDialog(vistaAdd, "Se ha modificcado correctamente la cuadrilla");
                        ControladorEmpleado contEmple = new ControladorEmpleado(vistaTabla);
                        this.vistaAdd.dispose();
                    }
                }else{
                    String err = addCuadrilla();                                //AÑADIR NUEVA
                    if(err.charAt(0) == 'E'){ //Ha habido error
                        JOptionPane.showMessageDialog(vistaAdd, err, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{// Sin error
                        JOptionPane.showMessageDialog(vistaAdd, "Se ha añadido correctamente la cuadrilla\ny se ha asignado el encargado");
                        ControladorEmpleado contEmple = new ControladorEmpleado(vistaTabla);
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
        String res = "Error ";
        Cuadrilla cuad = getCuadrilla();
        if(!modeloCuad.addCuadrilla(cuad)){
            res+=" al añadir la cuadrilla";
        }
        //AÑADIR ENCARGADO O ASCENDER 
        Object o = vistaAdd.jList.getSelectedValue();
        
//        String s = vistaAdd.jList.getSelectedValue();
//        String tipo = s.substring(s.lastIndexOf(" "));
//        String dni = s.substring(0, s.indexOf(" "));
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
        if(!modeloCuad.asignarEncargado(t,cuad)){
            res+="\nAl asignar el encargado";
        }
        if(res.equals("Error ")){ //Si no ha habido error el texto sera solo "Error "
            if(!modeloCuad.asignarEncargado(t,cuad)){ //Si hay error se almacena
                res+="\nAl asignar el encargado";
            }else{
                res="";//No ha habido ningun error
            }
        }
        return res;
    }

    private void actualizarLista() {
        ArrayList<Trabajador> listaTrabajadores = new ArrayList<Trabajador>();
        if(isEmpleSelected()){
            if(modeloTrab.recuperarTrabajadores().size() > 0){
                listaTrabajadores.addAll(modeloTrab.recuperarTrabajadores());
            }
        }else if(isTractSelected()){
            if(modeloTrab.recuperarConductores().size() > 0){
                listaTrabajadores.addAll(modeloTrab.recuperarConductores());
            }
        }else if(isEncSelected()){
            if(modeloTrab.recuperarEncargados().size() > 0){
                listaTrabajadores.addAll(modeloTrab.recuperarEncargados());
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
        if(res.equals("Error:")){
            res="";
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
    
    public void focusGained(FocusEvent fe) {
        
    }

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
