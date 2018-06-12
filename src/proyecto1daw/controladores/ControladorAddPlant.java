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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.PlantacionDAO;
import proyecto1daw.modelo.VentaDAO;
import proyecto1daw.vistas.JFPlantacion;
import proyecto1daw.vistas.JFPlantacionAdd;
import proyecto1daw.vistas.JFVentaAdd;

/**
 *
 * @author Jaime
 */
public class ControladorAddPlant implements ActionListener,FocusListener{
    private ControladorPlantacion contPlant;
    private JFPlantacionAdd vistaAddPlant;
    private PlantacionDAO modeloPlant;
    private String idExplotacion;
    private Finca finca;
    private Plantacion plant;

    public ControladorAddPlant(ControladorPlantacion contPlant, PlantacionDAO modeloPlant, String idExplotacion, Finca finca) {
        this.contPlant = contPlant;
        this.vistaAddPlant = new JFPlantacionAdd();
        this.modeloPlant = modeloPlant;
        this.idExplotacion = idExplotacion;
        this.finca = finca;
        
        //Asociar action listener  
        this.vistaAddPlant.botonAceptar.addActionListener(this);
        this.vistaAddPlant.botonCancelar.addActionListener(this);
        this.vistaAddPlant.jCheckBoxFFin.addActionListener(this);
        
        //Asociar focus listener a campos
        this.vistaAddPlant.campoTipo.addFocusListener(this);
        this.vistaAddPlant.campoVariedad.addFocusListener(this);
        this.vistaAddPlant.campoFPlant.addFocusListener(this);
        this.vistaAddPlant.campoFechaFin.addFocusListener(this);
        
        this.vistaAddPlant.campoFPlant.setText(Fechas.toString(LocalDate.now()));
        
        //Mostrar
        this.vistaAddPlant.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAddPlant.setLocationRelativeTo(null);
        this.vistaAddPlant.setVisible(true);
    }
    
    public ControladorAddPlant(ControladorPlantacion contPlant, PlantacionDAO modeloPlant, String idExplotacion, Finca finca,Plantacion plant) {
        this(contPlant, modeloPlant, idExplotacion, finca);
        
        this.plant = plant;
        this.vistaAddPlant.campoTipo.setText(plant.getTipo());
        this.vistaAddPlant.campoVariedad.setText(plant.getVariedad());
        this.vistaAddPlant.campoFPlant.setText(Fechas.toString(plant.getfInicio()));
        if(plant.getfFin() != null){
            this.vistaAddPlant.campoFechaFin.setText(Fechas.toString(plant.getfFin()));
        }
        this.vistaAddPlant.botonAceptar.setText("Modificar");
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaAddPlant.botonAceptar)){                  //ACEPTAR
            JButton boton = (JButton) ae.getSource();
            if(validarDatos()){
                if(boton.getText().equalsIgnoreCase("Aceptar")){            //AÑADIR
                    if(addPlant()){
                        JOptionPane.showMessageDialog(vistaAddPlant, "Plantacion añadida correctamente");
                        this.contPlant.actualizarTablaPlant();
                        this.vistaAddPlant.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAddPlant, "Error al añadir la plantacion", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(boton.getText().equalsIgnoreCase("Modificar")){    //MODIFICAR
                    if(modPlant()){
                        JOptionPane.showMessageDialog(vistaAddPlant, "Plantacion modificada correctamente");
                        this.contPlant.actualizarTablaPlant();
                        this.vistaAddPlant.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAddPlant, "Error al modificar la plantacion", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else if(ae.getSource().equals(vistaAddPlant.botonCancelar)){           //CANCELAR
            vistaAddPlant.dispose();
        }else if(ae.getSource().equals(vistaAddPlant.jCheckBoxFFin)){           //JCHECHBOX
            JCheckBox checkbox = (JCheckBox) ae.getSource();
            if (checkbox.isSelected()) {
                    this.vistaAddPlant.campoFechaFin.setEnabled(true);
                } else {
                    this.vistaAddPlant.campoFechaFin.setEnabled(false);
            }
        }
    }

    private boolean addPlant() {
        Plantacion p = getPlantacion();
        boolean res = true;
        
        if(!modeloPlant.addPlantacion(p)){
            res=false;
        }
        
        return res;
    }

    private boolean modPlant() {
        Plantacion p = getPlantacion();
        boolean res = true;
        
        if(!modeloPlant.actualizarCampo(p.getId(), "TIPO", p.getTipo())){
            res=false;
        }
        if(!modeloPlant.actualizarCampo(p.getId(), "VARIEDAD", p.getVariedad())){
            res=false;
        }
        if(!modeloPlant.actualizarCampo(p.getId(), "F_INICIO", Fechas.toString(p.getfInicio()))){
            res=false;
        }
        if(isFechaFinSelected()){
            if(!modeloPlant.actualizarCampo(p.getId(), "F_FIN", Fechas.toString(p.getfFin()))){
                res=false;
            }
        }
        
        return res;
    }
    
    private boolean validarDatos() {
        boolean res = true;
        res = validarFInicio(res);
        res = validarTipo(res);
        res = validarVariedad(res);
        res = validarFFin(res);
        return res;
    }

    private boolean validarFFin(boolean res) {
        if(this.isFechaFinSelected()){
            if(this.vistaAddPlant.campoFechaFin.getText().equals("") || this.vistaAddPlant.campoFechaFin.getText() == null){
                res=false;
                vistaAddPlant.errFFin.setText("Debes introducir una fecha");
            }else{
                if(Fechas.toLocalDate(vistaAddPlant.campoFechaFin.getText()) == null){
                    res=false;
                    vistaAddPlant.errFFin.setText("Debe tener formato dd/mm/aaaa");
                }else{
                    LocalDate fFin = Fechas.toLocalDate(vistaAddPlant.campoFechaFin.getText());
                    LocalDate fInicio = Fechas.toLocalDate(vistaAddPlant.campoFPlant.getText());
                    if(fInicio != null && fFin.isBefore(fInicio)){
                        res=false;
                        vistaAddPlant.errFFin.setText("No puede ser menor a F.Plant");
                    }else{
                        vistaAddPlant.errFFin.setText(" ");
                    }
                    
                }
            }
        }
        return res;
    }

    private boolean validarVariedad(boolean res) {
        if(this.vistaAddPlant.campoVariedad.getText().equals("")){
            res=false;
            vistaAddPlant.errVariedad.setText("La variedad es obligatoria");
        }else{
            vistaAddPlant.errVariedad.setText(" ");
        }
        return res;
    }

    private boolean validarTipo(boolean res) {
        if(this.vistaAddPlant.campoTipo.getText().equals("")){
            res=false;
            vistaAddPlant.errTipo.setText("El tipo es obligatorio");
        }else{
            String tipo = this.vistaAddPlant.campoTipo.getText();
            for (int i = 0; i < tipo.length(); i++) {
                if(Character.isDigit(tipo.charAt(i))){
                    res=false;
                    vistaAddPlant.errTipo.setText("No puede contener números");
                }
            }
            if(res){
                vistaAddPlant.errTipo.setText(" ");
            }
        }
        return res;
    }

    private boolean validarFInicio(boolean res) {
        if(this.vistaAddPlant.campoFPlant.getText().equals("")){
            res=false;
            vistaAddPlant.errFPlant.setText("La fecha es obligatoria");
        }else{
            if(Fechas.toLocalDate(this.vistaAddPlant.campoFPlant.getText()) == null){
                vistaAddPlant.errFPlant.setText("Debe ser formato dd/mm/aaaa");
                res=false;
            }else{
                vistaAddPlant.errFPlant.setText(" ");
            }
        }
        return res;
    }

    private boolean isFechaFinSelected() {
        return vistaAddPlant.jCheckBoxFFin.isSelected();
    }
    
    private Plantacion getPlantacion() {
        
        String idPlant = null;
        if(this.plant == null){
            idPlant = generarIdPlant();
        }else{
            idPlant = this.plant.getId();
        }
        String tipo = this.vistaAddPlant.campoTipo.getText();
        String variedad = this.vistaAddPlant.campoVariedad.getText();
        String fPlantSt = this.vistaAddPlant.campoFPlant.getText();
        LocalDate fPlant = Fechas.toLocalDate(fPlantSt);
        String fFinSt = this.vistaAddPlant.campoFechaFin.getText();
        LocalDate fFin = Fechas.toLocalDate(fFinSt);
        Plantacion p = new Plantacion(idPlant, tipo, variedad, fPlant, fFin, idExplotacion);
        return p;
    }
    
    
    private String generarIdPlant() {
        String idPlant = null;
        int num = modeloPlant.contarPlant(idExplotacion);
        idPlant = idExplotacion+"-"+num;
        while(modeloPlant.recuperarPorId(idPlant) != null){
            num++;
            idPlant = idExplotacion+"-"+num;
        }
        return idPlant;
    }
    
    public void focusGained(FocusEvent fe) {
        
    }

    public void focusLost(FocusEvent fe) {
        if(fe.getSource().equals(vistaAddPlant.campoTipo)){
            validarTipo(true);
        }else if(fe.getSource().equals(vistaAddPlant.campoVariedad)){
            validarVariedad(true);
        }else if(fe.getSource().equals(vistaAddPlant.campoFPlant)){
            validarFInicio(true);
        }else if(fe.getSource().equals(vistaAddPlant.campoFechaFin)){
            validarFFin(true);
        }
    }
}
