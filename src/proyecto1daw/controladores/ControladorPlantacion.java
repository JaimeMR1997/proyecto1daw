/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.PlantacionDAO;
import proyecto1daw.modelo.Venta;
import proyecto1daw.modelo.VentaDAO;
import proyecto1daw.vistas.JFPlantacion;
import proyecto1daw.vistas.JFPlantacionAdd;
import proyecto1daw.vistas.JFVentaAdd;

/**
 *
 * @author Jaime
 */
public class ControladorPlantacion implements ActionListener {

    private JFPlantacion vistaTabla;
    private JFPlantacionAdd vistaAddPlant;
    private JFVentaAdd vistaAddVenta;
    private PlantacionDAO modeloPlant;
    private VentaDAO modeloVenta;
    private DefaultTableModel modTablaPlant;
    private DefaultTableModel modTablaVentas;
    private DefaultComboBoxModel opcsTam;
    private String idExplotacion;

    public ControladorPlantacion(JFPlantacion vistaTabla, PlantacionDAO modeloPlant, VentaDAO modeloVenta, String idExplotacion) {
        this.vistaTabla = vistaTabla;
        this.vistaAddPlant = new JFPlantacionAdd();
        this.vistaAddVenta = new JFVentaAdd();
        this.modeloPlant = modeloPlant;
        this.modeloVenta = modeloVenta;
        this.idExplotacion = idExplotacion;

        //Asociar listener a botones vistaTabla
        this.vistaTabla.botonAddPlant.addActionListener(this);
        this.vistaTabla.botonAddVenta.addActionListener(this);
        this.vistaTabla.botonEliminarPlant.addActionListener(this);
        this.vistaTabla.botonEliminarVent.addActionListener(this);
        this.vistaTabla.botonModPlant.addActionListener(this);
        this.vistaTabla.botonModVenta.addActionListener(this);
        this.vistaTabla.botonBuscar.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);

        //Asociar listener a botones Añadir Plantacion
        this.vistaAddPlant.botonAceptar.addActionListener(this);
        this.vistaAddPlant.botonCancelar.addActionListener(this);
        this.vistaAddPlant.jCheckBoxFFin.addActionListener(this);

        //Asociar listener a botones Añadir Venta
        this.vistaAddVenta.botonAceptar.addActionListener(this);
        this.vistaAddVenta.botonCancelar.addActionListener(this);

        this.modTablaPlant = new DefaultTableModel();
        this.modTablaVentas = new DefaultTableModel();
        //Crear columnas tabla Plantaciones
        this.modTablaPlant.addColumn("ID");
        this.modTablaPlant.addColumn("Tipo");
        this.modTablaPlant.addColumn("Variedad");
        this.modTablaPlant.addColumn("Fecha plant.");
        this.modTablaPlant.addColumn("Fecha arrancar");

        //Crear columnas tabla Ventas
        this.modTablaVentas.addColumn("Fecha");
        this.modTablaVentas.addColumn("Kg");
        this.modTablaVentas.addColumn("€/KG");
        this.modTablaVentas.addColumn("Color");
        this.modTablaVentas.addColumn("Total €");

        //Rellenar tabla Plantaciones
        this.rellenarTablaPlant(this.modeloPlant.recuperarTodas());
        this.rellenarTablaVentas(this.modeloVenta.recuperarTodas());
        this.vistaTabla.jTablePlantaciones.setModel(modTablaPlant);
        this.vistaTabla.jTableVentas.setModel(modTablaVentas);

        //Establecer opciones Tamaño Venta
        opcsTam = new DefaultComboBoxModel();
        opcsTam.addElement("MMM");
        opcsTam.addElement("MM");
        opcsTam.addElement("M");
        opcsTam.addElement("G");
        opcsTam.addElement("GG");

        //Mostar ventana
        this.vistaAddPlant.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAddVenta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaTabla.setVisible(true);
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAddPlant.setLocationRelativeTo(null);
        this.vistaAddVenta.setLocationRelativeTo(null);
        this.vistaTabla.jLabelExplotacion.setText(this.idExplotacion);
    }

    public void actionPerformed(ActionEvent ae) {
        JButton boton = null;
        JCheckBox checkbox = null;
        if (ae.getSource() instanceof JButton) {
            boton = (JButton) ae.getSource();
            if (boton.equals(this.vistaTabla.botonAddPlant)) {                             //AÑADIR PLANTACION
                this.vistaAddPlant.campoFechaFin.setEnabled(false);
                this.vistaAddPlant.botonAceptar.setText("Aceptar");
                this.vistaAddPlant.setVisible(true);

            } else if (boton.equals(this.vistaTabla.botonModPlant)) {                       //MODIFICAR PLANTACION
                int filaSel = this.vistaTabla.jTablePlantaciones.getSelectedRow();
                if (filaSel == -1) {
                    String idPlant = (String)this.vistaTabla.jTablePlantaciones.getValueAt(filaSel, 0);
                    String tipo = (String)this.vistaTabla.jTablePlantaciones.getValueAt(filaSel, 1);
                    String variedad = (String)this.vistaTabla.jTablePlantaciones.getValueAt(filaSel, 2);
                    String fPlant = (String)this.vistaTabla.jTablePlantaciones.getValueAt(filaSel, 3);
                    
                    this.vistaAddPlant.jLabelIdPlant.setText(idPlant);//Está oculta siempre
                    this.vistaAddPlant.campoTipo.setText(tipo);
                    this.vistaAddPlant.campoVariedad.setText(variedad);
                    this.vistaAddPlant.campoFPlant.setText(fPlant);
                    this.vistaAddPlant.campoFechaFin.setEnabled(false);
                    this.vistaAddPlant.botonAceptar.setText("Modificar");
                    this.vistaAddPlant.setVisible(true);
                    
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una explotación", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonEliminarPlant)) {                  //ELIMINAR PLANTACION
                if (this.vistaTabla.jTablePlantaciones.getSelectedRow() == -1) {
                    int resp = JOptionPane.showConfirmDialog(this.vistaTabla, "¿Estás seguro de eliminar esta plantación?\nSe eliminarán todas sus ventas");
                    if(resp == JOptionPane.YES_OPTION){
                        //borrar
                    }
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una explotación", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonAddVenta)) {                       //AÑADIR VENTA
                if (this.vistaTabla.jTablePlantaciones.getSelectedRow() == -1) {
                    this.vistaAddVenta.botonAceptar.setText("Aceptar");
                    this.vistaAddVenta.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una plantación primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonModVenta)) {                       //MODIFICAR VENTA
                if (this.vistaTabla.jTableVentas.getSelectedRow() == -1) {
                    int filaSelVenta = this.vistaTabla.jTableVentas.getSelectedRow();
                    int filaSelPlant = this.vistaTabla.jTablePlantaciones.getSelectedRow();
                    String idVenta = (String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta, 0);
                    String idPlant = (String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelPlant, 0);
                    String color = (String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta, 1);
                    String kg = (String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta, 2);
                    String fecha = (String) this.vistaTabla.jTableVentas.getValueAt(filaSelPlant, 5);
                    //Cargar valores en campos
                    this.vistaAddVenta.jLabelIdVenta.setText(idVenta);
                    this.vistaAddVenta.jLabelIdPlant.setText(idPlant);
                    this.vistaAddVenta.jSpinnerKg.setValue(kg);
                    this.vistaAddVenta.campoFecha.setText(fecha);
                    this.vistaAddVenta.campoColor.setText(modeloVenta.buscar(idVenta, idPlant, "COLOR"));
                    //Mostrar
                    this.vistaAddVenta.botonAceptar.setText("Modificar");
                    this.vistaAddVenta.setVisible(true);
                    
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una venta primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonEliminarVent)) {                   //ELIMINAR VENTA
                if (this.vistaTabla.jTableVentas.getSelectedRow() == -1) {
                    int b= JOptionPane.showConfirmDialog(vistaAddVenta, "¿Estás seguro de eliminar esta venta?");
                    if(b==JOptionPane.YES_OPTION){
                        int filaSelVenta = this.vistaTabla.jTableVentas.getSelectedRow();
                        int filaSelPlant = this.vistaTabla.jTablePlantaciones.getSelectedRow();
                        String idPlant=(String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelPlant,0);
                        String idVenta=(String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta,0);
                        modeloVenta.borrarVenta(idVenta, idPlant);

                        this.vaciarTablaVentas();
                        this.rellenarTablaVentas(this.modeloVenta.recuperarTodas());
                    }
                } else { // No venta seleccionada
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar "
                        + "una venta", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaAddPlant.botonAceptar)) {                    //PLANTACION ACEPTAR
                if(validarDatosPlant()){
                    String tipo = this.vistaAddPlant.campoTipo.getText();
                    String variedad = this.vistaAddPlant.campoVariedad.getText();
                    String fPlantSt = this.vistaAddPlant.campoFPlant.getText();
                    LocalDate fPlant=null;

                    if(Fechas.parseStringtoLocalDate(fPlantSt) != null){
                        fPlant = Fechas.parseStringtoLocalDate(fPlantSt);
                    }
                    String fFinSt = this.vistaAddPlant.campoFechaFin.getText();
                    LocalDate fFin = null;
                    if(isFechaFinSelected() && (Fechas.parseStringtoLocalDate(fFinSt) != null)){
                        fFin = Fechas.parseStringtoLocalDate(fFinSt);
                    }
                    Plantacion p = new Plantacion(generarIdPlant(), tipo, variedad, fPlant, fFin, idExplotacion);
                    
                    //Comprobar si es mod o add
                    if(boton.getText().equalsIgnoreCase("Aceptar")){ //Aceptar
                        modeloPlant.addPlantacion(p);
                        
                    }else if(boton.getText().equalsIgnoreCase("Modificar")){//Mod
                        String errMsg="";
                        if(!modeloPlant.actualizarCampo(p.getId(), "TIPO", p.getTipo())){
                            errMsg+="\nError al modificar el tipo";
                        }
                        if(!modeloPlant.actualizarCampo(p.getId(), "VARIEDAD", p.getVariedad())){
                            errMsg+="\nError al modificar la variedad";
                        }
                        if(!modeloPlant.actualizarCampo(p.getId(), "F_INICIO", Fechas.parseLocalDtoString(p.getfInicio()))){
                            errMsg+="\nError al modificar la fecha de plant.";
                        }
                        if(isFechaFinSelected()){
                            if(modeloPlant.actualizarCampo(p.getId(), "F_FIN", Fechas.parseLocalDtoString(p.getfFin()))){
                                errMsg+="E\nrror al modificar la fecha de fin";
                            }
                        }
                        
                        if(errMsg.equals("")){
                            JOptionPane.showMessageDialog(vistaTabla, errMsg, "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(this.vistaTabla, "Plantacion modificada correctamente");
                        }
                        
                    }
                }
            } else if (boton.equals(this.vistaAddPlant.botonCancelar)) {                    //PLANTACION CANCELAR
                limpiarCamposAddPlant();
                this.vistaAddPlant.dispose();
                
            } else if (boton.equals(this.vistaAddVenta.botonAceptar)) {                    //VENTA ACEPTAR
                if(validarDatosVenta()){
                    String nomBoton = boton.getText();
                    String idPlant = this.vistaAddPlant.jLabelIdPlant.getText();
                    String idVenta = this.generarIdVenta();
                    int cantidad = (int) this.vistaAddVenta.jSpinnerKg.getValue();
                    String color = (String) this.vistaAddVenta.campoColor.getText();
                    String tamanio = (String) this.vistaAddVenta.jComboTam.getSelectedItem();
                    String fechaSt = this.vistaAddVenta.campoFecha.getText();
                    float precio = Float.parseFloat(this.vistaAddVenta.campoPrecio.getText());
                    LocalDate fecha = null;
                    if(Fechas.parseStringtoLocalDate(fechaSt) != null){
                        fecha=Fechas.parseStringtoLocalDate(fechaSt);
                    }
                    Venta v = new Venta(idVenta, cantidad, precio, tamanio, color, fecha, idPlant);
                    if(nomBoton.equalsIgnoreCase("Aceptar")){   //AÑADIR
                        if(modeloVenta.addVenta(v)){
                            JOptionPane.showMessageDialog(vistaTabla, "Venta añadida correctamente");
                        }else{
                            JOptionPane.showMessageDialog(vistaTabla, "Se ha producido un error al añadir la venta", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }else if(nomBoton.equalsIgnoreCase("Modificar")){//MODIFICAR
                        
                        
                    }
                }
            } else if (boton.equals(this.vistaAddVenta.botonCancelar)) {                    //VENTA CANCELAR
                limpiarCamposAddVenta();
                this.vistaAddVenta.dispose();
            }
        } else if (ae.getSource() instanceof JCheckBox) {
            checkbox = (JCheckBox) ae.getSource();
            if (checkbox.equals(this.vistaAddPlant.jCheckBoxFFin)) {                  //Permitir Fecha Fin
                if (checkbox.isSelected()) {
                    this.vistaAddPlant.campoFechaFin.setEnabled(true);
                } else {
                    this.vistaAddPlant.campoFechaFin.setEnabled(false);
                }
            }//else if(checkbox.equals(this.vistaTabla.jCheckBoxTerm){}
        }

    }

    public boolean isFechaFinSelected() {
        return this.vistaAddPlant.jCheckBoxFFin.isSelected();
    }

    private void rellenarTablaPlant(ArrayList<Plantacion> listaPlant) {
        String[] s = new String[5];
        for (Plantacion p : listaPlant) {
            s[0] = p.getId();
            s[1] = p.getTipo();
            s[2] = p.getVariedad();
            s[3] = Fechas.parseLocalDtoString(p.getfInicio());
            s[4] = Fechas.parseLocalDtoString(p.getfFin());
            modTablaPlant.addRow(s);
        }
    }

    private void rellenarTablaVentas(ArrayList<Venta> listaVentas) {
        String[] s = new String[5];
        for (Venta v : listaVentas) {
            s[0] = Fechas.parseLocalDtoString(v.getFecha());
            s[1] = v.getKg() + "";
            s[2] = v.getPrecio() + "";
            s[3] = v.getColor() + " " + v.getTamanio();
            s[4] = (v.getKg() * v.getPrecio()) + "";
            modTablaPlant.addRow(s);
        }
    }

    private void vaciarTablaPlant() {
        this.modTablaPlant.setRowCount(0);
    }
    
    private void vaciarTablaVentas() {
        this.modTablaVentas.setRowCount(0);
    }
    
    private void limpiarCamposAddVenta() {
        this.vistaAddVenta.campoFecha.setText("");
        this.vistaAddVenta.campoColor.setText("");
        this.vistaAddVenta.jComboTam.setSelectedIndex(-1);
    }

    private void limpiarCamposAddPlant() {
        this.vistaAddPlant.jLabelIdPlant.setText("");//Está oculta siempre
        this.vistaAddPlant.campoTipo.setText("");
        this.vistaAddPlant.campoVariedad.setText("");
        this.vistaAddPlant.campoFPlant.setText("");
        this.vistaAddPlant.campoFechaFin.setText("");
    }

    private boolean validarDatosPlant() {
        boolean res = true;
        if(this.vistaAddPlant.campoFPlant.getText().equals("")){
            res=false;
        }
        if(this.vistaAddPlant.campoTipo.getText().equals("")){
            res=false;
        }
        if(this.vistaAddPlant.campoVariedad.getText().equals("")){
            res=false;
        }
        if(this.vistaAddPlant.jCheckBoxFFin.isSelected()){
            if(this.vistaAddPlant.campoFechaFin.getText().equals("")){
                res=false;
            }
        }
        return res;
    }

    private boolean validarDatosVenta() {
        boolean res = true;
        String fecha = this.vistaAddVenta.campoFecha.getText(); 
        if(fecha.equals("")){
            res=false;
            
        }else{
            if(Fechas.parseStringtoLocalDate(fecha) == null){
                res=false;
            }
        }
        if(this.vistaAddVenta.campoColor.getText().equals("")){
            res=false;
        }
        if(this.vistaAddVenta.campoPrecio.getText().equals("")){
            res=false;
        }
        if(this.vistaAddVenta.jComboTam.getSelectedIndex() == -1){
            res=false;
        }
        
        int kg = (int) this.vistaAddVenta.jSpinnerKg.getValue();
        if(kg <= 0){
            res=false;
        }
        return res;
    }

    private String generarIdPlant() {
        String idPlant = null;
        int num = modeloPlant.contarPlant(idExplotacion);
        idPlant = idExplotacion+"-"+num;
        return idPlant;
    }

    private String generarIdVenta() {
        String idVenta = null;
        
        return idVenta;
    }
}
