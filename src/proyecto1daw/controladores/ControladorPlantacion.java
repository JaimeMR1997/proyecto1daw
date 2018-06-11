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
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.PlantacionDAO;
import proyecto1daw.modelo.Venta;
import proyecto1daw.modelo.VentaDAO;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFPlantacion;
import proyecto1daw.vistas.JFPlantacionAdd;
import proyecto1daw.vistas.JFVentaAdd;

/**
 *
 * @author Jaime
 */
public class ControladorPlantacion implements ActionListener,MouseListener {

    private JFPlantacion vistaTabla;
    private JFVentaAdd vistaAddVenta;
    private PlantacionDAO modeloPlant;
    private VentaDAO modeloVenta;
    private DefaultTableModel modTablaPlant;
    private DefaultTableModel modTablaVentas;
    private DefaultComboBoxModel opcsTam;
    private String idExplotacion;
    private Finca finca;

    public ControladorPlantacion(JFPlantacion vistaTabla,String idExplotacion,Finca finca) {
        this.vistaTabla = vistaTabla;
        this.vistaAddVenta = new JFVentaAdd();
        this.modeloPlant = new PlantacionDAO();
        this.modeloVenta = new VentaDAO();
        this.idExplotacion = idExplotacion;
        this.finca = finca;
        
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
        this.actualizarTablaPlant();
        this.vistaTabla.jTablePlantaciones.setModel(modTablaPlant);
        this.vistaTabla.jTableVentas.setModel(modTablaVentas);
        //Añadir listener raton a tabla Plantaciones
        this.vistaTabla.jTablePlantaciones.addMouseListener(this);

        //Establecer opciones Tamaño Venta
        opcsTam = new DefaultComboBoxModel();
        opcsTam.addElement("MMM");
        opcsTam.addElement("MM");
        opcsTam.addElement("M");
        opcsTam.addElement("G");
        opcsTam.addElement("GG");
        this.vistaAddVenta.jComboTam.setModel(opcsTam);
        
        //Mostar ventana
        this.vistaAddVenta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAddVenta.setLocationRelativeTo(null);
        this.vistaTabla.jLabelExplotacion.setText(this.idExplotacion);
        this.vistaTabla.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        JButton boton = null;
        JCheckBox checkbox = null;
        if (ae.getSource() instanceof JButton) {
            boton = (JButton) ae.getSource();
            if (boton.equals(this.vistaTabla.botonAddPlant)) {                             //AÑADIR                           
                abrirAddPlant();

            } else if (boton.equals(this.vistaTabla.botonVolver)){                         //VOLVER
                volver();
                
            }else if (boton.equals(this.vistaTabla.botonModPlant)) {                       //ABRIR MOD PLANTACION
                if(this.vistaTabla.jTablePlantaciones.getSelectedRow() != -1){
                    abrirModPlant();
                }else{
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una plantacion", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonEliminarPlant)) {               //ELIMINAR PLANTACION

                if (this.vistaTabla.jTablePlantaciones.getSelectedRow() != -1) {
                    int confirmacion = JOptionPane.showConfirmDialog(this.vistaTabla, 
                            "¿Estás seguro de eliminar esta plantación?\nSe eliminarán todas sus ventas");
                    eliminarPlant(confirmacion);
                } else { //No hay fila sel
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una plantacion", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonAddVenta)) {                       //ABRIR AÑADIR VENTA
                if (this.vistaTabla.jTablePlantaciones.getSelectedRow() != -1) {
                    abrirAddVenta();
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una plantación primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonModVenta)) {                       //ABRIR MODIFICAR VENTA
                if (this.vistaTabla.jTableVentas.getSelectedRow() != -1) {
                    abrirModVenta();
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una venta primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonEliminarVent)) {                   //ELIMINAR VENTA
                
                if (this.vistaTabla.jTableVentas.getSelectedRow() != -1) {
                    int confirmacion= JOptionPane.showConfirmDialog(vistaAddVenta, "¿Estás seguro de eliminar esta venta?");
                    eliminarVenta(confirmacion);
                } else { // No venta seleccionada
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar "
                        + "una venta", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaAddVenta.botonAceptar)) {                    //VENTA ACEPTAR
                if(validarDatosVenta()){
                    String nomBoton = boton.getText();
                    Venta v = this.getVenta();
                    
                    if(nomBoton.equalsIgnoreCase("Aceptar")){                   //AÑADIR VENTA
                        if(modeloVenta.addVenta(v)){
                            JOptionPane.showMessageDialog(vistaTabla, "Venta añadida"
                                    + " correctamente");
                            actualizarTablaVentas(v);
                            
                        }else{
                            JOptionPane.showMessageDialog(vistaTabla, "Se ha producido"
                                    + " un error al añadir la venta", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }else if(nomBoton.equalsIgnoreCase("Modificar")){//MODIFICAR VENTA
                        String s =actualizarVenta(v);
                        if(s.charAt(0) == 'E'){//Se ha producido error
                        JOptionPane.showMessageDialog(vistaAddVenta, s, "Error", JOptionPane.ERROR_MESSAGE);
                        }else{//Todo correcto
                            JOptionPane.showMessageDialog(vistaAddVenta, s);
                            actualizarTablaVentas(v);
                        }
                    }
                }
            }else if (boton.equals(this.vistaTabla.botonBuscarPlant)){                      //BUSCAR PLANTACION
                buscarPlantacion();
                
            }else if(boton.equals(this.vistaTabla.botonBuscarVenta)){                       //BUSCAR VENTA
                int fila=this.vistaTabla.jTablePlantaciones.getSelectedRow();
                if(fila != -1){
                    buscarVenta(fila);
                }else{
                    JOptionPane.showMessageDialog(vistaAddVenta, "Necesitas seleccionar una plantacion", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    private void buscarVenta(int fila) {
        LocalDate fVenta = null;
        fVenta= Fechas.toLocalDate(this.vistaTabla.campoFVenta.getText());
        String idPlant = (String) this.vistaTabla.jTablePlantaciones.getValueAt(fila, 0);
        this.modTablaVentas.setRowCount(0);
        this.rellenarTablaVentas(modeloVenta.recuperarPorFecha(fVenta,idPlant));
    }

    private void buscarPlantacion() {
        LocalDate fInicio = null;
        LocalDate fFin = null;
        if(validarFInicio() && validarFFin()){
            fInicio = Fechas.toLocalDate(this.vistaTabla.campoFIn.getText());
            fFin = Fechas.toLocalDate(this.vistaTabla.campoFFin.getText());
        }else if(validarFInicio()){
            fInicio = Fechas.toLocalDate(this.vistaTabla.campoFIn.getText());
            fFin = LocalDate.of(2100, Month.MARCH, 1);
        }else if(validarFFin()){
            fInicio = LocalDate.of(1900, Month.MARCH, 1);
            fFin = Fechas.toLocalDate(this.vistaTabla.campoFFin.getText());
        }else{
            fInicio = LocalDate.of(1900, Month.MARCH, 1);
            fFin = LocalDate.of(2100, Month.MARCH, 1);
        }

        this.modTablaVentas.setRowCount(0);
        this.modTablaPlant.setRowCount(0);
        this.actualizarTablaPlant();
    }

    public void actualizarTablaVentas(Venta v) {
        this.limpiarCamposAddVenta();
        this.vistaAddVenta.dispose();
        vaciarTablaVentas();
        rellenarTablaVentas(modeloVenta.recuperarPorPlant(v.getIdPlantacion()));
    }

    private void eliminarVenta(int confirmacion) {
        if(confirmacion==JOptionPane.YES_OPTION){
            int filaSelVenta = this.vistaTabla.jTableVentas.getSelectedRow();
            int filaSelPlant = this.vistaTabla.jTablePlantaciones.getSelectedRow();
            String idPlant=(String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelPlant,0);
            String idVenta=(String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta,0);
            modeloVenta.borrarVenta(idVenta, idPlant);
            
            this.vaciarTablaVentas();
            this.rellenarTablaVentas(this.modeloVenta.recuperarPorPlant(idPlant));
        }
    }

    private void abrirModVenta() {
        int fila = vistaTabla.jTableVentas.getSelectedRow();
        String idPlant = (String) vistaTabla.jTableVentas.getValueAt(fila, 0);
        Plantacion plant = modeloPlant.recuperarPorId(idPlant);
        //ControladorAddVenta contAddVenta = new ControladorAddPlant(vistaTabla, modeloPlant, idExplotacion, finca, plant);
    }

    private void abrirAddVenta() {
        int filaSelPlant = this.vistaTabla.jTablePlantaciones.getSelectedRow();
        String idPlant = (String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelPlant, 0);
        this.vistaAddVenta.jLabelIdPlant.setText(idPlant);
        this.vistaAddVenta.botonAceptar.setText("Aceptar");
        this.vistaAddVenta.setVisible(true);
    }

    private void eliminarPlant(int confirmacion) {
        if(confirmacion == JOptionPane.YES_OPTION){
            int filaSelVenta = this.vistaTabla.jTablePlantaciones.getSelectedRow();
            String idPlant=(String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelVenta,0);
            if(this.modeloVenta.borrarVentasPlantacion(idPlant)){
                this.modeloPlant.borrarPlantacion(idPlant);
                this.actualizarTablaPlant();
                JOptionPane.showMessageDialog(vistaTabla, "Ventas y plantación borrados correctamente.");
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Ha habido un error al eliminar las plantaciones."
                        + "\nContacta con tu administrador si el problema persiste.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void volver() {
        ControladorExplotacion contExp = new ControladorExplotacion(new JFExplotacion(), new ExplotacionDAO(), this.finca);
        this.vistaTabla.dispose();
    }

    private void abrirAddPlant() {
        ControladorAddPlant contAddPlant = new ControladorAddPlant(this, modeloPlant, idExplotacion, finca);
    }
    
    private void abrirModPlant() {
        int fila = vistaTabla.jTablePlantaciones.getSelectedRow();
        String idPlant = (String) vistaTabla.jTablePlantaciones.getValueAt(fila, 0);
        Plantacion plant = modeloPlant.recuperarPorId(idPlant);
        ControladorAddPlant contAddPlant = new ControladorAddPlant(this, modeloPlant, idExplotacion, finca, plant);
    }

    public void actualizarTablaPlant() {
        this.modTablaPlant.setRowCount(0);
        ArrayList<Plantacion> listaPlant = modeloPlant.recuperarPorExp(idExplotacion);
        String[] s = new String[5];
        for (Plantacion p : listaPlant) {
            s[0] = p.getId();
            s[1] = p.getTipo();
            s[2] = p.getVariedad();
            s[3] = Fechas.toString(p.getfInicio());
            if(p.getfFin() != null){
                s[4] = Fechas.toString(p.getfFin());
            }else{
                s[4]="";
            }
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
        this.vistaAddVenta.errCantidad.setText(" ");
        this.vistaAddVenta.errColor.setText(" ");
        this.vistaAddVenta.errFecha.setText(" ");
        this.vistaAddVenta.errPrecio.setText(" ");
        this.vistaAddVenta.errTam.setText(" ");
    }

    private boolean validarDatosVenta() {
        boolean res = true;
        res = validarFechaVenta(res);
        res = validarColorVenta(res);
        res = validarPrecioVenta(res);
        res = validarTipoVenta(res);
        res = validarCantidad(res);
        return res;
    }

    private boolean validarCantidad(boolean res) {
        int kg = (int) this.vistaAddVenta.jSpinnerKg.getValue();
        if(kg <= 0){
            res=false;
            vistaAddVenta.errCantidad.setText("La cantidad debe ser entera");
        }else{
            vistaAddVenta.errCantidad.setText(" ");
        }
        return res;
    }

    private boolean validarTipoVenta(boolean res) {
        if(this.vistaAddVenta.jComboTam.getSelectedIndex() == -1){
            res=false;
            vistaAddVenta.errTam.setText("El tamaño es obligatorio");
        }else{
            vistaAddVenta.errTam.setText(" ");
        }
        return res;
    }

    private boolean validarPrecioVenta(boolean res) {
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

    private boolean validarColorVenta(boolean res) {
        if(this.vistaAddVenta.campoColor.getText().equals("")){
            res=false;
            vistaAddVenta.errColor.setText("El color es obligatorio");
        }else{
            vistaAddVenta.errColor.setText(" ");
        }
        return res;
    }

    private boolean validarFechaVenta(boolean res) {
        String fecha = this.vistaAddVenta.campoFecha.getText();
        if(fecha.equals("")){
            res=false;
            vistaAddVenta.errFecha.setText("La fecha es obligatoria");
        }else{
            if(Fechas.toLocalDate(fecha) == null){
                res=false;
                vistaAddVenta.errFecha.setText("Debe ser formato dd/mm/aaaa");
            }else{
                vistaAddVenta.errFecha.setText(" ");
            }
        }
        return res;
    }

    private String generarIdVenta() {
        String idVenta = null;
        String idPlant = this.vistaAddVenta.jLabelIdPlant.getText();
        LocalDate fecha = Fechas.toLocalDate(this.vistaAddVenta.campoFecha.getText());
        int num = modeloVenta.contarVentas(idPlant, fecha)+1;
        idVenta=Fechas.toString(fecha)+"-"+num;
        while(modeloVenta.recuperarPorId(idVenta, idPlant) != null){
            num++;
            idVenta=Fechas.toString(fecha)+"-"+num;
        }
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
        if(s.equals("") || s == null){
           res=false; 
        }else if(Fechas.toLocalDate(s) == null){
            res=false;
        }
        return res;
    }

    private boolean validarFFin() {
        boolean res=true;
        String s = this.vistaTabla.campoFFin.getText();
        if(s.equals("") || s == null){
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

    public void mouseClicked(MouseEvent me) {
        
    }

    public void mousePressed(MouseEvent me) {
        JTable tabla = (JTable) me.getSource();
            int fila = tabla.getSelectedRow();
            double cantidad = 0;
            
            if(fila != -1){
                String idPlant = (String) tabla.getValueAt(fila, 0);
                //Carga ventas de la plantación
                modTablaVentas.setRowCount(0);
                rellenarTablaVentas(modeloVenta.recuperarPorPlant(idPlant));
                cantidad = modeloVenta.calcularIngresos(idPlant);

                if(me.getClickCount() == 2){                            //DOBLE CLICK ABRE NUEVA VENTA
                    
                }
            }
            
            vistaTabla.jLabelCantidadIngresos.setText(cantidad+"€");
    }
    
    public void mouseReleased(MouseEvent me) {
        
    }

    public void mouseEntered(MouseEvent me) {
        
    }

    public void mouseExited(MouseEvent me) {
        
    }

    private Venta getVenta() {
        String idPlant = this.vistaAddVenta.jLabelIdPlant.getText();
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
    
}
