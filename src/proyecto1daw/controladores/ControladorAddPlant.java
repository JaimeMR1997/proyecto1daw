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
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.accesobd.PlantacionDAO;
import proyecto1daw.modelo.accesobd.VentaDAO;
import proyecto1daw.vistas.JFPlantacion;
import proyecto1daw.vistas.JFPlantacionAdd;
import proyecto1daw.vistas.JFVentaAdd;

/**
 *
 * @author Jaime
 */
public class ControladorAddPlant implements ActionListener,FocusListener,KeyListener{
    private ControladorPlantacion contPlant;
    private JFPlantacionAdd vistaAdd;
    private PlantacionDAO modeloPlant;
    private String idExplotacion;
    private Finca finca;
    private Plantacion plant;

    /**
     *
     * @param contPlant el controlador que llama a este
     * @param modeloPlant modelo de acceso a datos relacionados con Plantacion
     * @param idExplotacion Id de la explotacion que se cargaán sus plantaciones y ventas
     * @param finca Finca a la que pertenece la explotacion
     */
    public ControladorAddPlant(ControladorPlantacion contPlant, PlantacionDAO modeloPlant, String idExplotacion, Finca finca) {
        this.contPlant = contPlant;
        this.vistaAdd = new JFPlantacionAdd();
        this.modeloPlant = modeloPlant;
        this.idExplotacion = idExplotacion;
        this.finca = finca;
        
        //Asociar action listener  
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jCheckBoxFFin.addActionListener(this);
        
        //Asociar focus listener a campos
        this.vistaAdd.campoTipo.addFocusListener(this);
        this.vistaAdd.campoVariedad.addFocusListener(this);
        this.vistaAdd.campoFPlant.addFocusListener(this);
        this.vistaAdd.campoFechaFin.addFocusListener(this);
        
        //Asociar keylistener a frame y campos
        this.vistaAdd.addKeyListener(this);
        this.vistaAdd.setFocusable(true);
        this.vistaAdd.campoTipo.addKeyListener(this);
        this.vistaAdd.campoVariedad.addKeyListener(this);
        this.vistaAdd.campoFPlant.addKeyListener(this);
        this.vistaAdd.campoFechaFin.addKeyListener(this);
        
        this.vistaAdd.campoFPlant.setText(Fechas.toString(LocalDate.now()));
        
        //Mostrar
        this.vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.setVisible(true);
    }
    
    /**
     *
     * @param contPlant el controlador que llama a este
     * @param modeloPlant modelo de acceso a datos relacionados con Plantacion
     * @param idExplotacion Id de la explotacion que se cargaán sus plantaciones y ventas
     * @param finca Finca a la que pertenece la explotacion
     * @param plant plantacion a modificar, se abre la ventana en modo modifcar
     * y carga sus datos en los campos
     */
    public ControladorAddPlant(ControladorPlantacion contPlant, PlantacionDAO modeloPlant, String idExplotacion, Finca finca,Plantacion plant) {
        this(contPlant, modeloPlant, idExplotacion, finca);
        
        this.plant = plant;
        this.vistaAdd.campoTipo.setText(plant.getTipo());
        this.vistaAdd.campoVariedad.setText(plant.getVariedad());
        this.vistaAdd.campoFPlant.setText(Fechas.toString(plant.getfInicio()));
        if(plant.getfFin() != null){
            this.vistaAdd.campoFechaFin.setText(Fechas.toString(plant.getfFin()));
        }
        this.vistaAdd.botonAceptar.setText("Modificar");
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
                    if(addPlant()){
                        JOptionPane.showMessageDialog(vistaAdd, "Plantacion añadida correctamente");
                        this.contPlant.actualizarTablaPlant();
                        this.vistaAdd.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAdd, "Error al añadir la plantacion", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }else if(boton.getText().equalsIgnoreCase("Modificar")){    //MODIFICAR
                    if(modPlant()){
                        JOptionPane.showMessageDialog(vistaAdd, "Plantacion modificada correctamente");
                        this.contPlant.actualizarTablaPlant();
                        this.vistaAdd.dispose();
                    }else{
                        JOptionPane.showMessageDialog(vistaAdd, "Error al modificar la plantacion", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else if(ae.getSource().equals(vistaAdd.botonCancelar)){           //CANCELAR
            vistaAdd.dispose();
        }else if(ae.getSource().equals(vistaAdd.jCheckBoxFFin)){           //JCHECHBOX
            JCheckBox checkbox = (JCheckBox) ae.getSource();
            if (checkbox.isSelected()) {
                    this.vistaAdd.campoFechaFin.setEnabled(true);
                } else {
                    this.vistaAdd.campoFechaFin.setEnabled(false);
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
        
        Configuracion conf = new Configuracion();
        String fecha = "";
        if(conf.getTipoServer().equalsIgnoreCase("MariaDB")){
            fecha = Fechas.toStringMariaDb(p.getfInicio());
        }else{
            fecha = Fechas.toString(p.getfInicio());
        }
        
        if(!modeloPlant.actualizarCampo(p.getId(), "F_INICIO", fecha)){
            res=false;
        }
        
        fecha = "";
        if(conf.getTipoServer().equalsIgnoreCase("MariaDB")){
            fecha = Fechas.toStringMariaDb(p.getfFin());
        }else{
            fecha = Fechas.toString(p.getfFin());
        }
        
        if(isFechaFinSelected()){
            if(!modeloPlant.actualizarCampo(p.getId(), "F_FIN", fecha)){
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
            if(this.vistaAdd.campoFechaFin.getText().equals("") || this.vistaAdd.campoFechaFin.getText() == null){
                res=false;
                vistaAdd.errFFin.setText("Debes introducir una fecha");
            }else{
                if(Fechas.toLocalDate(vistaAdd.campoFechaFin.getText()) == null){
                    res=false;
                    vistaAdd.errFFin.setText("Debe tener formato dd/mm/aaaa");
                }else{
                    LocalDate fFin = Fechas.toLocalDate(vistaAdd.campoFechaFin.getText());
                    LocalDate fInicio = Fechas.toLocalDate(vistaAdd.campoFPlant.getText());
                    if(fInicio != null && fFin.isBefore(fInicio)){
                        res=false;
                        vistaAdd.errFFin.setText("No puede ser menor a F.Plant");
                    }else{
                        vistaAdd.errFFin.setText(" ");
                    }
                    
                }
            }
        }
        return res;
    }

    private boolean validarVariedad(boolean res) {
        if(this.vistaAdd.campoVariedad.getText().equals("")){
            res=false;
            vistaAdd.errVariedad.setText("La variedad es obligatoria");
        }else{
            vistaAdd.errVariedad.setText(" ");
        }
        return res;
    }

    private boolean validarTipo(boolean res) {
        if(this.vistaAdd.campoTipo.getText().equals("")){
            res=false;
            vistaAdd.errTipo.setText("El tipo es obligatorio");
        }else{
            String tipo = this.vistaAdd.campoTipo.getText();
            for (int i = 0; i < tipo.length(); i++) {
                if(Character.isDigit(tipo.charAt(i))){
                    res=false;
                    vistaAdd.errTipo.setText("No puede contener números");
                }
            }
            if(res){
                vistaAdd.errTipo.setText(" ");
            }
        }
        return res;
    }

    private boolean validarFInicio(boolean res) {
        if(this.vistaAdd.campoFPlant.getText().equals("")){
            res=false;
            vistaAdd.errFPlant.setText("La fecha es obligatoria");
        }else{
            if(Fechas.toLocalDate(this.vistaAdd.campoFPlant.getText()) == null){
                vistaAdd.errFPlant.setText("Debe ser formato dd/mm/aaaa");
                res=false;
            }else{
                vistaAdd.errFPlant.setText(" ");
            }
        }
        return res;
    }

    private boolean isFechaFinSelected() {
        return vistaAdd.jCheckBoxFFin.isSelected();
    }
    
    private Plantacion getPlantacion() {
        
        String idPlant = null;
        if(this.plant == null){
            idPlant = generarIdPlant();
        }else{
            idPlant = this.plant.getId();
        }
        String tipo = this.vistaAdd.campoTipo.getText();
        String variedad = this.vistaAdd.campoVariedad.getText();
        String fPlantSt = this.vistaAdd.campoFPlant.getText();
        LocalDate fPlant = Fechas.toLocalDate(fPlantSt);
        String fFinSt = this.vistaAdd.campoFechaFin.getText();
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
        if(fe.getSource().equals(vistaAdd.campoTipo)){
            validarTipo(true);
        }else if(fe.getSource().equals(vistaAdd.campoVariedad)){
            validarVariedad(true);
        }else if(fe.getSource().equals(vistaAdd.campoFPlant)){
            validarFInicio(true);
        }else if(fe.getSource().equals(vistaAdd.campoFechaFin)){
            validarFFin(true);
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
