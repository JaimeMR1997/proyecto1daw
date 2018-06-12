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
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.PlantacionDAO;
import proyecto1daw.modelo.Venta;
import proyecto1daw.modelo.VentaDAO;
import proyecto1daw.vistas.JFPlantacionAdd;
import proyecto1daw.vistas.JFVentaAdd;

/**
 *
 * @author Jaime
 */
public class ControladorAddVenta implements ActionListener,FocusListener{
    private ControladorPlantacion contPlant;
    private JFVentaAdd vistaAddVenta;
    private VentaDAO modeloVenta;
    private String idExplotacion;
    private Finca finca;
    private Plantacion plant;
    private Venta vent;
    private DefaultComboBoxModel opcsTam;

    public ControladorAddVenta(ControladorPlantacion contPlant, VentaDAO modeloVenta, String idExplotacion, Finca finca,Plantacion plant) {
        this.contPlant = contPlant;
        this.vistaAddVenta = new JFVentaAdd();
        this.modeloVenta = modeloVenta;
        this.idExplotacion = idExplotacion;
        this.finca = finca;
        this.plant = plant;
        
        //Establecer opciones Tamaño Venta
        opcsTam = new DefaultComboBoxModel();
        opcsTam.addElement("MMM");
        opcsTam.addElement("MM");
        opcsTam.addElement("M");
        opcsTam.addElement("G");
        opcsTam.addElement("GG");
        this.vistaAddVenta.jComboTam.setModel(opcsTam);
        this.opcsTam.setSelectedItem("G");
        
        //Asociar action listener  
        this.vistaAddVenta.botonAceptar.addActionListener(this);
        this.vistaAddVenta.botonCancelar.addActionListener(this);
        
        //Asociar focus listener a campos
        this.vistaAddVenta.jSpinnerKg.addFocusListener(this);
        this.vistaAddVenta.campoColor.addFocusListener(this);
        this.vistaAddVenta.jComboTam.addFocusListener(this);
        this.vistaAddVenta.campoFecha.addFocusListener(this);
        this.vistaAddVenta.campoPrecio.addFocusListener(this);
        
        this.vistaAddVenta.campoFecha.setText(Fechas.toString(LocalDate.now()));
        
        //Mostrar
        this.vistaAddVenta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAddVenta.setLocationRelativeTo(null);
        this.vistaAddVenta.setVisible(true);
    }
    
    public ControladorAddVenta(ControladorPlantacion contPlant, VentaDAO modeloVenta, String idExplotacion, Finca finca, Plantacion plant, Venta vent) {
        this(contPlant, modeloVenta, idExplotacion, finca,plant);
        
        this.vent = vent;
        if(this.vent != null){
            this.vistaAddVenta.jSpinnerKg.setValue(Integer.valueOf(vent.getKg()));
            this.vistaAddVenta.campoColor.setText(vent.getColor());
            this.vistaAddVenta.jComboTam.setSelectedItem(vent.getTamanio());
            this.vistaAddVenta.campoFecha.setText(Fechas.toString(vent.getFecha()));
            this.vistaAddVenta.campoPrecio.setText(vent.getPrecio()+"");
            this.vistaAddVenta.botonAceptar.setText("Modificar");
        }else{
            this.vistaAddVenta.dispose();
            JOptionPane.showMessageDialog(null, "Error inesperado al cargar la venta", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaAddVenta.botonAceptar)){                  //ACEPTAR
            JButton boton = (JButton) ae.getSource();
            if(validarDatos()){
                if(boton.getText().equalsIgnoreCase("Aceptar")){            //AÑADIR
                    if(addPlant()){
                        JOptionPane.showMessageDialog(vistaAddVenta, "Plantacion añadida correctamente");
                        this.contPlant.actualizarTablaVentas();
                        this.vistaAddVenta.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAddVenta, "Error al añadir la plantacion", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(boton.getText().equalsIgnoreCase("Modificar")){    //MODIFICAR
                    if(modVenta()){
                        JOptionPane.showMessageDialog(vistaAddVenta, "Plantacion modificada correctamente");
                        this.contPlant.actualizarTablaVentas();
                        this.vistaAddVenta.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAddVenta, "Error al modificar la plantacion", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else if(ae.getSource().equals(vistaAddVenta.botonCancelar)){           //CANCELAR
            vistaAddVenta.dispose();
        }
    }

    private boolean addPlant() {
        Venta v = getVenta();
        boolean res = true;
        
        if(!modeloVenta.addVenta(v)){
            res=false;
        }
        
        return res;
    }
    
        private boolean validarDatos() {
        boolean res = true;
        res = validarFecha(res);
        res = validarColor(res);
        res = validarPrecio(res);
        res = validarTam(res);
        res = validarCantidad(res);
        return res;
    }

    private boolean validarCantidad(boolean res) {
        int kg = (int) this.vistaAddVenta.jSpinnerKg.getValue();
        if(kg <= 0){
            res=false;
            vistaAddVenta.errCantidad.setText("La cantidad debe ser entera");
        }else if(kg >100000){
            vistaAddVenta.errCantidad.setText("La cantidad es demasiado grande");
        }else{
            vistaAddVenta.errCantidad.setText(" ");
        }
        return res;
    }

    private boolean validarTam(boolean res) {
        if(this.vistaAddVenta.jComboTam.getSelectedIndex() == -1){
            res=false;
            vistaAddVenta.errTam.setText("El tamaño es obligatorio");
        }else{
            vistaAddVenta.errTam.setText(" ");
        }
        return res;
    }

    private boolean modVenta() {
        boolean res = true;
        Venta v = getVenta();
        
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "KG", v.getKg()+"")){
            res=false;
        }
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "PRECIO", v.getPrecio()+"")){
            res=false;
        }
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "TAMANIO", v.getTamanio())){
            res=false;
        }
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "COLOR", v.getColor())){
            res=false;
        }
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "FECHA", Fechas.toString(v.getFecha()))){
            res=false;
        }
        
        return res;
    }

    
    private boolean validarPrecio(boolean res) {
        if(this.vistaAddVenta.campoPrecio.getText().equals("")){
            res=false;
            vistaAddVenta.errPrecio.setText("El precio es obligatorio");
        }else{
            try{
                double num = Double.parseDouble(this.vistaAddVenta.campoPrecio.getText());
            }catch(NumberFormatException e){
                res=false;
                vistaAddVenta.errPrecio.setText("El precio debe ser decimal");
            }
            if(res){
                vistaAddVenta.errPrecio.setText(" ");
            }
        }
        return res;
    }

    private boolean validarColor(boolean res) {
        if(this.vistaAddVenta.campoColor.getText().equals("")){
            res=false;
            vistaAddVenta.errColor.setText("El color es obligatorio");
        }else if(this.vistaAddVenta.campoColor.getText().length()>20){
            vistaAddVenta.errColor.setText("Máximo 20 caracteres");
        }else{
            vistaAddVenta.errColor.setText(" ");
        }
        return res;
    }

    private boolean validarFecha(boolean res) {
        String fechaSt = this.vistaAddVenta.campoFecha.getText();
        if(fechaSt.equals("") ||fechaSt == null){
            res=false;
            vistaAddVenta.errFecha.setText("La fecha es obligatoria");
        }else{
            if(Fechas.toLocalDate(fechaSt) == null){
                res=false;
                vistaAddVenta.errFecha.setText("Debe ser formato dd/mm/aaaa");
            }else{
                LocalDate fecha = Fechas.toLocalDate(fechaSt);
                if(plant.getfFin() != null && fecha.isAfter(plant.getfFin().plusMonths(1))){
                    //Se le suma un mes porque puede que haya alguna ventaq después de arrancar
                    //la plantación pero no más tarde de 1 mes
                    res=false;
                    vistaAddVenta.errFecha.setText("La fecha es después de la F.Fin");
                }else{
                    vistaAddVenta.errFecha.setText(" ");
                }
                
            }
        }
        return res;
    }
    
    private Venta getVenta() {
        String idPlant = plant.getId();
        String idVenta = this.generarIdVenta();
        int cantidad = (int) this.vistaAddVenta.jSpinnerKg.getValue();
        String color = (String) this.vistaAddVenta.campoColor.getText();
        String tamanio = (String) this.vistaAddVenta.jComboTam.getSelectedItem();
        String fechaSt = this.vistaAddVenta.campoFecha.getText();
        float precio = Float.parseFloat(this.vistaAddVenta.campoPrecio.getText());
        LocalDate fecha=Fechas.toLocalDate(fechaSt);
        Venta v = new Venta(idVenta, cantidad, precio, tamanio, color, fecha, idPlant);
        return v;
    }
    
    private String generarIdVenta() {
        String idVenta = null;
        String idPlant = plant.getId();
        LocalDate fecha = Fechas.toLocalDate(this.vistaAddVenta.campoFecha.getText());
        int num = modeloVenta.contarVentas(idPlant, fecha)+1;
        idVenta=Fechas.toString(fecha)+"-"+num;
        while(modeloVenta.recuperarPorId(idVenta, idPlant) != null){
            num++;
            idVenta=Fechas.toString(fecha)+"-"+num;
        }
        return idVenta;
    }
    
    public void focusGained(FocusEvent fe) {
        
    }

    public void focusLost(FocusEvent fe) {
        if(fe.getSource().equals(vistaAddVenta.jSpinnerKg)){
            validarCantidad(true);
        }else if(fe.getSource().equals(vistaAddVenta.campoColor)){
            validarColor(true);
        }else if(fe.getSource().equals(vistaAddVenta.jComboTam)){
            validarTam(true);
        }else if(fe.getSource().equals(vistaAddVenta.campoFecha)){
            validarFecha(true);
        }else if(fe.getSource().equals(vistaAddVenta.campoPrecio)){
            validarPrecio(true);
        }
    }
    
    
}
