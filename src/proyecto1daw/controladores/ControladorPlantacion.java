/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.PlantacionDAO;
import proyecto1daw.modelo.Venta;
import proyecto1daw.modelo.VentaDAO;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFExplotacionAdd;
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
    private String idFinca;

    public ControladorPlantacion(JFPlantacion vistaTabla, PlantacionDAO modeloPlant, VentaDAO modeloVenta, String idExplotacion,String idFinca) {
        this.vistaTabla = vistaTabla;
        this.vistaAddPlant = new JFPlantacionAdd();
        this.vistaAddVenta = new JFVentaAdd();
        this.modeloPlant = modeloPlant;
        this.modeloVenta = modeloVenta;
        this.idExplotacion = idExplotacion;
        this.idFinca = idFinca;

        //Asociar listener a botones vistaTabla
        this.vistaTabla.botonAddPlant.addActionListener(this);
        this.vistaTabla.botonAddVenta.addActionListener(this);
        this.vistaTabla.botonEliminarPlant.addActionListener(this);
        this.vistaTabla.botonEliminarVent.addActionListener(this);
        this.vistaTabla.botonModPlant.addActionListener(this);
        this.vistaTabla.botonModVenta.addActionListener(this);
        this.vistaTabla.botonBuscarPlant.addActionListener(this);
        this.vistaTabla.botonBuscarVenta.addActionListener(this);
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
        this.modTablaVentas.addColumn("Id-Venta");
        this.modTablaVentas.addColumn("Kg");
        this.modTablaVentas.addColumn("€/KG");
        this.modTablaVentas.addColumn("Color");
        this.modTablaVentas.addColumn("Total €");
        this.modTablaVentas.addColumn("Fecha");

        //Rellenar tabla Plantaciones
        this.rellenarTablaPlant(this.modeloPlant.recuperarPorExp(this.idExplotacion));
        this.vistaTabla.jTablePlantaciones.setModel(modTablaPlant);
        this.vistaTabla.jTableVentas.setModel(modTablaVentas);
        //Añadir listener raton a tabla Plantaciones
        this.vistaTabla.jTablePlantaciones.addMouseListener(new OyenteRaton());

        //Establecer opciones Tamaño Venta
        opcsTam = new DefaultComboBoxModel();
        opcsTam.addElement("MMM");
        opcsTam.addElement("MM");
        opcsTam.addElement("M");
        opcsTam.addElement("G");
        opcsTam.addElement("GG");
        this.vistaAddVenta.jComboTam.setModel(opcsTam);
        
        //Mostar ventana
        this.vistaAddPlant.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAddVenta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAddPlant.setLocationRelativeTo(null);
        this.vistaAddVenta.setLocationRelativeTo(null);
        this.vistaTabla.jLabelExplotacion.setText(this.idExplotacion);
        //Oculta jlabel auxiliares
        this.vistaAddPlant.jLabelIdPlant.setVisible(false);
        this.vistaAddVenta.jLabelIdPlant.setVisible(false);
        this.vistaAddVenta.jLabelIdVenta.setVisible(false);
        this.vistaTabla.setVisible(true);
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

            } else if (boton.equals(this.vistaTabla.botonVolver)){              //VOLVER
                this.vistaTabla.dispose();
                ControladorExplotacion contExp = new ControladorExplotacion(new JFExplotacion(),new ExplotacionDAO(), new JFExplotacionAdd(), idFinca);
                
            }else if (boton.equals(this.vistaTabla.botonModPlant)) {                       //MODIFICAR PLANTACION
                int filaSel = this.vistaTabla.jTablePlantaciones.getSelectedRow();
                if (filaSel != -1) {
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
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una plantacion", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonEliminarPlant)) {               //ELIMINAR PLANTACION
                if (this.vistaTabla.jTablePlantaciones.getSelectedRow() != -1) {
                    int resp = JOptionPane.showConfirmDialog(this.vistaTabla, "¿Estás seguro de eliminar esta plantación?\nSe eliminarán todas sus ventas");
                    if(resp == JOptionPane.YES_OPTION){
                        //borrar
                    }
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una plantacion", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonAddVenta)) {                       //AÑADIR VENTA
                if (this.vistaTabla.jTablePlantaciones.getSelectedRow() != -1) {
                    int filaSelPlant = this.vistaTabla.jTablePlantaciones.getSelectedRow();
                    String idPlant = (String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelPlant, 0);
                    this.vistaAddVenta.jLabelIdPlant.setText(idPlant);
                    
                    this.vistaAddVenta.botonAceptar.setText("Aceptar");
                    this.vistaAddVenta.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una plantación primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonModVenta)) {                       //MODIFICAR VENTA
                if (this.vistaTabla.jTableVentas.getSelectedRow() != -1) {
                    int filaSelVenta = this.vistaTabla.jTableVentas.getSelectedRow();
                    int filaSelPlant = this.vistaTabla.jTablePlantaciones.getSelectedRow();
                    String idVenta = (String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta, 0);
                    String idPlant = (String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelPlant, 0);
                    String kg = (String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta, 1);
                    float precio = Float.parseFloat((String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta, 2));
                    String color = (String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta, 3);
                    String fecha = (String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta, 5);
                    //Cargar valores en campos
                    this.vistaAddVenta.jLabelIdVenta.setText(idVenta);
                    this.vistaAddVenta.jLabelIdPlant.setText(idPlant);
                    this.vistaAddVenta.jSpinnerKg.setValue(Integer.valueOf(kg));
                    this.vistaAddVenta.campoFecha.setText(fecha);
                    this.vistaAddVenta.campoColor.setText(modeloVenta.buscar(idVenta, idPlant, "COLOR"));
                    //Mostrar
                    this.vistaAddVenta.botonAceptar.setText("Modificar");
                    this.vistaAddVenta.setVisible(true);
                    
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una venta primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonEliminarVent)) {                   //ELIMINAR VENTA
                if (this.vistaTabla.jTableVentas.getSelectedRow() != -1) {
                    int b= JOptionPane.showConfirmDialog(vistaAddVenta, "¿Estás seguro de eliminar esta venta?");
                    if(b==JOptionPane.YES_OPTION){
                        int filaSelVenta = this.vistaTabla.jTableVentas.getSelectedRow();
                        int filaSelPlant = this.vistaTabla.jTablePlantaciones.getSelectedRow();
                        String idPlant=(String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelPlant,0);
                        String idVenta=(String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta,0);
                        modeloVenta.borrarVenta(idVenta, idPlant);

                        this.vaciarTablaVentas();
                        this.rellenarTablaVentas(this.modeloVenta.recuperarPorPlant(idPlant));
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
                    LocalDate fPlant = Fechas.toLocalDate(fPlantSt);
                    String fFinSt = this.vistaAddPlant.campoFechaFin.getText();
                    LocalDate fFin = Fechas.toLocalDate(fFinSt);
                    
                    Plantacion p = new Plantacion(generarIdPlant(), tipo, variedad, fPlant, fFin, idExplotacion);
                    
                    //Comprobar si es mod o add
                    if(boton.getText().equalsIgnoreCase("Aceptar")){ //NUEVA PLANTACION
                        if(modeloPlant.addPlantacion(p)){
                            JOptionPane.showMessageDialog(vistaTabla, "Plantación añadida correctamente.");
                            this.limpiarCamposAddPlant();
                            this.vistaAddPlant.dispose();
                            this.modTablaPlant.setRowCount(0);
                            this.rellenarTablaPlant(modeloPlant.recuperarPorExp(idExplotacion));
                        }else{//error
                            JOptionPane.showMessageDialog(vistaTabla, "Se ha prodcido un error al añadir la plantación.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }else if(boton.getText().equalsIgnoreCase("Modificar")){//MODIFICAR PLANTACION
                        String s = actualizarPlant(p);
                        if(s.charAt(0) == 'E'){ //Ha habido error
                            JOptionPane.showMessageDialog(vistaTabla, s, "Error", JOptionPane.ERROR_MESSAGE);
                        }else{//Todo correcto
                            JOptionPane.showMessageDialog(this.vistaTabla, "Plantacion modificada correctamente");
                            this.limpiarCamposAddPlant();
                            this.vistaAddPlant.dispose();
                            this.modTablaPlant.setRowCount(0);
                            this.rellenarTablaPlant(modeloPlant.recuperarPorExp(idExplotacion));
                        }
                        
                    }
                }
            } else if (boton.equals(this.vistaAddPlant.botonCancelar)) {                    //PLANTACION CANCELAR
                limpiarCamposAddPlant();
                this.vistaAddPlant.dispose();
                
            } else if (boton.equals(this.vistaAddVenta.botonAceptar)) {                    //VENTA ACEPTAR
                if(validarDatosVenta()){
                    String nomBoton = boton.getText();
                    String idPlant = this.vistaAddVenta.jLabelIdPlant.getText();
                    String idVenta = this.generarIdVenta();
                    int cantidad = (int) this.vistaAddVenta.jSpinnerKg.getValue();
                    String color = (String) this.vistaAddVenta.campoColor.getText();
                    String tamanio = (String) this.vistaAddVenta.jComboTam.getSelectedItem();
                    String fechaSt = this.vistaAddVenta.campoFecha.getText();
                    float precio = Float.parseFloat(this.vistaAddVenta.campoPrecio.getText());
                    LocalDate fecha=Fechas.toLocalDate(fechaSt);
                    Venta v = new Venta(idVenta, cantidad, precio, tamanio, color, fecha, idPlant);
                    
                    if(nomBoton.equalsIgnoreCase("Aceptar")){   //AÑADIR VENTA
                        if(modeloVenta.addVenta(v)){
                            JOptionPane.showMessageDialog(vistaTabla, "Venta añadida correctamente");
                            rellenarTablaVentas(modeloVenta.recuperarPorPlant(idPlant));
                            this.limpiarCamposAddVenta();
                            this.vistaAddVenta.dispose();
                            modTablaVentas.setRowCount(0);
                            rellenarTablaVentas(modeloVenta.recuperarPorPlant(idPlant));
                        }else{
                            JOptionPane.showMessageDialog(vistaTabla, "Se ha producido un error al añadir la venta", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }else if(nomBoton.equalsIgnoreCase("Modificar")){//MODIFICAR VENTA
                        String s =actualizarVenta(v);
                        if(s.charAt(0) == 'E'){//Se ha producido error
                        JOptionPane.showMessageDialog(vistaAddVenta, s, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{//Todo correcto
                        JOptionPane.showMessageDialog(vistaAddVenta, s);
                        this.limpiarCamposAddVenta();
                        this.vistaAddVenta.dispose();
                        this.modTablaVentas.setRowCount(0);
                        this.rellenarTablaVentas(modeloVenta.recuperarPorPlant(idPlant));
                    }
                        
                    }
                }
            } else if (boton.equals(this.vistaAddVenta.botonCancelar)) {                    //VENTA CANCELAR
                limpiarCamposAddVenta();
                this.vistaAddVenta.dispose();
            }else if (boton.equals(this.vistaTabla.botonBuscarPlant)){                       //BUSCAR
                LocalDate fInicio = null;
                LocalDate fFin = null;
                if(validarFInicio() && validarFFin()){
                    fInicio = Fechas.toLocalDate(this.vistaTabla.campoFIn.getText());
                    fFin = Fechas.toLocalDate(this.vistaTabla.campoFFin.getText());
                }else if(validarFInicio()){
                    fInicio = Fechas.toLocalDate(this.vistaTabla.campoFIn.getText());
                    fFin = LocalDate.of(2100, Month.MARCH, 1);
                }else{
                    fInicio = LocalDate.of(1900, Month.MARCH, 1);
                    fFin = Fechas.toLocalDate(this.vistaTabla.campoFFin.getText());
                }
                
                
                if(fInicio != null && fFin != null){
                    this.modTablaVentas.setRowCount(0);
                    this.modTablaPlant.setRowCount(0);
                    this.rellenarTablaPlant(modeloPlant.recuperarPorFecha(fInicio,fFin,idExplotacion));
                }
                
            }else if(boton.equals(this.vistaTabla.botonBuscarVenta)){
                int fila=this.vistaTabla.jTablePlantaciones.getSelectedRow();
                if(fila != -1){
                    LocalDate fVenta = null;
                    fVenta= Fechas.toLocalDate(this.vistaTabla.campoFVenta.getText());
                    String idPlant = (String) this.vistaTabla.jTablePlantaciones.getValueAt(fila, 0);
                    this.modTablaVentas.setRowCount(0);
                    this.rellenarTablaVentas(modeloVenta.recuperarPorFecha(fVenta,idPlant));
                }else{
                    JOptionPane.showMessageDialog(vistaAddVenta, "Necesitas seleccionar una plantacion", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (ae.getSource() instanceof JCheckBox) {
            checkbox = (JCheckBox) ae.getSource();
            if (this.isFechaFinSelected()) {                  //Permitir Fecha Fin
                if (checkbox.isSelected()) {
                    this.vistaAddPlant.campoFechaFin.setEnabled(true);
                } else {
                    this.vistaAddPlant.campoFechaFin.setEnabled(false);
                }
            }//else if(checkbox.equals(this.vistaTabla.jCheckBoxTerm){}
        }

    }

    private String actualizarPlant(Plantacion p) {
        String s = "Error:";
        if(!modeloPlant.actualizarCampo(p.getId(), "TIPO", p.getTipo())){
            s+="\nAl modificar el tipo";
        }
        if(!modeloPlant.actualizarCampo(p.getId(), "VARIEDAD", p.getVariedad())){
            s+="\nAl modificar la variedad";
        }
        if(!modeloPlant.actualizarCampo(p.getId(), "F_INICIO", Fechas.toString(p.getfInicio()))){
            s+="\nAl modificar la fecha de plant.";
        }
        if(isFechaFinSelected()){
            if(modeloPlant.actualizarCampo(p.getId(), "F_FIN", Fechas.toString(p.getfFin()))){
                s+="\nAl modificar la fecha de fin";
            }
        }
        
        if(s.equals("Error:")){
            s="Plantación actualizada correctamente.";
        }
        return s;
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
            s[3] = Fechas.toString(p.getfInicio());
            if(p.getfFin() != null)
            s[4] = Fechas.toString(p.getfFin());
            modTablaPlant.addRow(s);
        }
    }

    private void rellenarTablaVentas(ArrayList<Venta> listaVentas) {
        String[] s = new String[6];
        for (Venta v : listaVentas) {
            s[0] = v.getId();
            s[1] = v.getKg() + "";
            s[2] = v.getPrecio() + "";
            s[3] = v.getColor() + " " + v.getTamanio();
            s[4] = (v.getKg() * v.getPrecio()) + "";
            s[5] = Fechas.toString(v.getFecha());
            modTablaVentas.addRow(s);
        }
    }

    private void vaciarTablaPlant() {
        this.modTablaPlant.setRowCount(0);
    }
    
    private void vaciarTablaVentas() {
        this.modTablaVentas.setRowCount(0);
    }
    
    private void limpiarCamposAddVenta() {
        this.vistaAddVenta.jSpinnerKg.setValue(Integer.parseInt("0"));
        this.vistaAddVenta.campoFecha.setText("");
        this.vistaAddVenta.campoColor.setText("");
        this.vistaAddVenta.jComboTam.setSelectedIndex(-1);
        this.vistaAddVenta.campoPrecio.setText("");
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
        if(this.isFechaFinSelected()){
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
            if(Fechas.toLocalDate(fecha) == null){
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
        String idPlant = this.vistaAddVenta.jLabelIdPlant.getText();
        String fecha = this.vistaAddVenta.campoFecha.getText();
        int num = modeloVenta.contarVentas(idPlant, fecha);
        idVenta=fecha+"-"+num;
        return idVenta;
    }

    private String actualizarVenta(Venta v) {
        String s = "Error:";
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "KG", v.getKg()+"")){
            s+="\nAl modificar la cantidad";
        }
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "PRECIO", v.getPrecio()+"")){
            s+="\nAl modificar el precio";
        }
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "TAMANIO", v.getTamanio())){
            s+="\nAl modificar el tamaño";
        }
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "COLOR", v.getColor())){
            s+="\nAl modificar el color";
        }
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "FECHA", Fechas.toString(v.getFecha()))){
            s+="\nAl modificar la fecha";
        }
        
        if(s.equals("Error:")){
            s="Venta actualizada correctamente.";
        }
        return s;
    }

    private boolean validarFInicio() {
        boolean res=true;
        String s = this.vistaTabla.campoFIn.getText();
        if(s.equals("")){
           res=false; 
        }else if(Fechas.toLocalDate(s) == null){
            res=false;
        }
        return res;
    }

    private boolean validarFFin() {
        boolean res=true;
        String s = this.vistaTabla.campoFFin.getText();
        if(s.equals("")){
           res=false; 
        }else if(Fechas.toLocalDate(s) == null){
            res=false;
        }
        return res;
    }

    private boolean validarFVenta() {
        boolean res=true;
        String s = this.vistaTabla.campoFVenta.getText();
        if(s.equals("")){
           res=false; 
        }else if(Fechas.toLocalDate(s) == null){
            res=false;
        }
        return res;
    }
    
    private class OyenteRaton implements MouseListener{
        //Asociado solo a tabla plantaciones
        public void mouseClicked(MouseEvent me) {
            JTable tabla = (JTable) me.getSource();
            int fila = tabla.getSelectedRow();
            double cantidad = 0;
            
            if(fila != -1){
            String idPlant = (String) tabla.getValueAt(fila, 0);
            //Carga ventas de la plantación
            modTablaVentas.setRowCount(0);
            rellenarTablaVentas(modeloVenta.recuperarPorPlant(idPlant));
            cantidad = modeloVenta.calcularIngresos(idPlant);
            }
            
            vistaTabla.jLabelCantidadIngresos.setText(cantidad+"€");
        }

        
        public void mousePressed(MouseEvent me) {
            
        }

        
        public void mouseReleased(MouseEvent me) {
            
        }

        
        public void mouseEntered(MouseEvent me) {
            
        }

        public void mouseExited(MouseEvent me) {
            
        }
    
    }
}
