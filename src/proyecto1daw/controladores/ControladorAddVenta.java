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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.accesobd.PlantacionDAO;
import proyecto1daw.modelo.Venta;
import proyecto1daw.modelo.accesobd.VentaDAO;
import proyecto1daw.vistas.JFPlantacionAdd;
import proyecto1daw.vistas.JFVentaAdd;

/**
 *
 * @author Jaime
 */
public class ControladorAddVenta implements ActionListener,FocusListener,KeyListener{
    private ControladorPlantacion contPlant;
    private JFVentaAdd vistaAdd;
    private VentaDAO modeloVenta;
    private String idExplotacion;
    private Finca finca;
    private Plantacion plant;
    private Venta vent;
    private DefaultComboBoxModel opcsTam;

    /**
     *
     * @param contPlant controlador que llaama a este
     * @param modeloVenta modelo relacionado con el acceso a datos de Venta
     * @param idExplotacion id de la explotacion a la que pertenece la venta
     * @param finca ficna a la que pertenece al venta
     * @param plant plantacion a la que pertenece la venta
     */
    public ControladorAddVenta(ControladorPlantacion contPlant, VentaDAO modeloVenta, String idExplotacion, Finca finca,Plantacion plant) {
        this.contPlant = contPlant;
        this.vistaAdd = new JFVentaAdd();
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
        this.vistaAdd.jComboTam.setModel(opcsTam);
        this.opcsTam.setSelectedItem("G");
        
        //Asociar action listener  
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        
        //Asociar focus listener a campos
        this.vistaAdd.jSpinnerKg.addFocusListener(this);
        this.vistaAdd.campoColor.addFocusListener(this);
        this.vistaAdd.jComboTam.addFocusListener(this);
        this.vistaAdd.campoFecha.addFocusListener(this);
        this.vistaAdd.campoPrecio.addFocusListener(this);
        
        //Asociar key listener a frame y campos
        this.vistaAdd.addKeyListener(this);
        this.vistaAdd.setFocusable(true);
        this.vistaAdd.jSpinnerKg.addKeyListener(this);
        this.vistaAdd.campoColor.addKeyListener(this);
        this.vistaAdd.jComboTam.addKeyListener(this);
        this.vistaAdd.campoFecha.addKeyListener(this);
        this.vistaAdd.campoPrecio.addKeyListener(this);
        
        this.vistaAdd.campoFecha.setText(Fechas.toString(LocalDate.now()));
        
        //Mostrar
        this.vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.setVisible(true);
    }
    
    /**
     *
     * @param contPlant controlador que llaama a este
     * @param modeloVenta modelo relacionado con el acceso a datos de Venta
     * @param idExplotacion id de la explotacion a la que pertenece la venta
     * @param finca ficna a la que pertenece al venta
     * @param plant plantacion a la que pertenece la venta
     * @param vent venta a modificar
     */
    public ControladorAddVenta(ControladorPlantacion contPlant, VentaDAO modeloVenta, String idExplotacion, Finca finca, Plantacion plant, Venta vent) {
        this(contPlant, modeloVenta, idExplotacion, finca,plant);
        
        this.vent = vent;
        if(this.vent != null){
            this.vistaAdd.jSpinnerKg.setValue(Integer.valueOf(vent.getKg()));
            this.vistaAdd.campoColor.setText(vent.getColor());
            this.vistaAdd.jComboTam.setSelectedItem(vent.getTamanio());
            this.vistaAdd.campoFecha.setText(Fechas.toString(vent.getFecha()));
            this.vistaAdd.campoPrecio.setText(vent.getPrecio()+"");
            this.vistaAdd.botonAceptar.setText("Modificar");
        }else{
            this.vistaAdd.dispose();
            JOptionPane.showMessageDialog(null, "Error inesperado al cargar la venta", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaAdd.botonAceptar)){                  //ACEPTAR
            JButton boton = (JButton) ae.getSource();
            if(validarDatos()){
                if(boton.getText().equalsIgnoreCase("Aceptar")){            //AÑADIR
                    if(addVenta()){
                        JOptionPane.showMessageDialog(vistaAdd, "Plantacion añadida correctamente");
                        this.contPlant.actualizarTablaVentas();
                        this.contPlant.actualizarEtiquetasIngresos();
                        this.vistaAdd.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAdd, "Error al añadir la plantacion", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(boton.getText().equalsIgnoreCase("Modificar")){    //MODIFICAR
                    if(modVenta()){
                        JOptionPane.showMessageDialog(vistaAdd, "Plantacion modificada correctamente");
                        this.contPlant.actualizarTablaVentas();
                        this.vistaAdd.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAdd, "Error al modificar la plantacion", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else if(ae.getSource().equals(vistaAdd.botonCancelar)){           //CANCELAR
            vistaAdd.dispose();
        }
    }

    private boolean addVenta() {
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
        int kg = (int) this.vistaAdd.jSpinnerKg.getValue();
        if(kg <= 0){
            res=false;
            vistaAdd.errCantidad.setText("La cantidad debe ser entera");
        }else if(kg >100000){
            vistaAdd.errCantidad.setText("La cantidad es demasiado grande");
        }else{
            vistaAdd.errCantidad.setText(" ");
        }
        return res;
    }

    private boolean validarTam(boolean res) {
        if(this.vistaAdd.jComboTam.getSelectedIndex() == -1){
            res=false;
            vistaAdd.errTam.setText("El tamaño es obligatorio");
        }else{
            vistaAdd.errTam.setText(" ");
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
        Configuracion conf = new Configuracion();
        String fecha = "";
        if(conf.getTipoServer().equalsIgnoreCase("MariaDB")){
            fecha = Fechas.toStringMariaDb(v.getFecha());
        }else{
            fecha = Fechas.toString(v.getFecha());
        }
        
        if(!modeloVenta.actualizarCampo(v.getId(), v.getIdPlantacion(), "FECHA", fecha)){
                res=false;
        }
        
        return res;
    }

    
    private boolean validarPrecio(boolean res) {
        if(this.vistaAdd.campoPrecio.getText().equals("")){
            res=false;
            vistaAdd.errPrecio.setText("El precio es obligatorio");
        }else{
            try{
                double num = Double.parseDouble(this.vistaAdd.campoPrecio.getText());
            }catch(NumberFormatException e){
                res=false;
                vistaAdd.errPrecio.setText("El precio debe ser decimal");
            }
            if(res){
                vistaAdd.errPrecio.setText(" ");
            }
        }
        return res;
    }

    private boolean validarColor(boolean res) {
        if(this.vistaAdd.campoColor.getText().equals("")){
            res=false;
            vistaAdd.errColor.setText("El color es obligatorio");
        }else if(this.vistaAdd.campoColor.getText().length()>20){
            vistaAdd.errColor.setText("Máximo 20 caracteres");
        }else{
            vistaAdd.errColor.setText(" ");
        }
        return res;
    }

    private boolean validarFecha(boolean res) {
        String fechaSt = this.vistaAdd.campoFecha.getText();
        if(fechaSt.equals("") ||fechaSt == null){
            res=false;
            vistaAdd.errFecha.setText("La fecha es obligatoria");
        }else{
            if(Fechas.toLocalDate(fechaSt) == null){
                res=false;
                vistaAdd.errFecha.setText("Debe ser formato dd/mm/aaaa");
            }else{
                LocalDate fecha = Fechas.toLocalDate(fechaSt);
                if(plant.getfFin() != null && fecha.isAfter(plant.getfFin().plusMonths(1))){
                    //Se le suma un mes porque puede que haya alguna ventaq después de arrancar
                    //la plantación pero no más tarde de 1 mes
                    res=false;
                    vistaAdd.errFecha.setText("La fecha es después de la F.Fin");
                }else{
                    vistaAdd.errFecha.setText(" ");
                }
                
            }
        }
        return res;
    }
    
    /** 
     *@return Devuelve un objeto venta a partir de los campos del formulario.
     * Genera un nuevo Id de venta si es para añadir una nueva venta y mantiene
     * el anterior si es modificando la venta.
    */
    private Venta getVenta() {
        String idPlant = this.plant.getId();
        String idVenta = "";
        if(this.vent == null){ //Nueva venta
            idVenta = this.generarIdVenta();
        }else{ //Modificar venta
            idVenta = this.vent.getId();
        }
        int cantidad = (int) this.vistaAdd.jSpinnerKg.getValue();
        String color = (String) this.vistaAdd.campoColor.getText();
        String tamanio = (String) this.vistaAdd.jComboTam.getSelectedItem();
        String fechaSt = this.vistaAdd.campoFecha.getText();
        float precio = Float.parseFloat(this.vistaAdd.campoPrecio.getText());
        LocalDate fecha=Fechas.toLocalDate(fechaSt);
        Venta v = new Venta(idVenta, cantidad, precio, tamanio, color, fecha, idPlant);
        return v;
    }
    
    private String generarIdVenta() {
        String idVenta = null;
        String idPlant = plant.getId();
        LocalDate fecha = Fechas.toLocalDate(this.vistaAdd.campoFecha.getText());
        int num = modeloVenta.contarVentas(idPlant, fecha)+1;
        idVenta=Fechas.toString(fecha)+"-"+num;
        while(modeloVenta.recuperarPorId(idVenta, idPlant) != null){
            num++;
            idVenta=Fechas.toString(fecha)+"-"+num;
        }
        return idVenta;
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
        if(fe.getSource().equals(vistaAdd.jSpinnerKg)){
            validarCantidad(true);
        }else if(fe.getSource().equals(vistaAdd.campoColor)){
            validarColor(true);
        }else if(fe.getSource().equals(vistaAdd.jComboTam)){
            validarTam(true);
        }else if(fe.getSource().equals(vistaAdd.campoFecha)){
            validarFecha(true);
        }else if(fe.getSource().equals(vistaAdd.campoPrecio)){
            validarPrecio(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode()==KeyEvent.VK_ENTER){
            //Invoca el metodo que maneja los eventos y le pasa un "ActionEvent"
            // cuyo origen es el boton Aceptar/Modificar segun el caso
            //Son el mismo objeto solo cambia el texto que se le muestra al usuario
            actionPerformed(new ActionEvent(this.vistaAdd.botonAceptar, 0, ""));
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
    /**
     * Hace que la ventana JFFincaAdd gane el foco y se ponga en primer plano
     */
    public void setFocused(){
        this.vistaAdd.requestFocus();
    }
    /**
     * 
     * @return Devuelve true si la ventana es visible y false si no lo es(está cerrada)
     */
    public boolean isVentanaAbierta(){
        boolean res = this.vistaAdd.isVisible();
        return res;
    }
    /**
     * Realiza un dispose a la ventana JFFincaAdd
     */
    public void close(){
        this.vistaAdd.dispose();
    }
}
