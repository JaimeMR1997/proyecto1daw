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
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.accesobd.ExplotacionDAO;
import proyecto1daw.vistas.JFExplotacionAdd;

/**
 *
 * @author Jaime-Torre
 */
public class ControladorAddExplotacion implements ActionListener,FocusListener,KeyListener{
    private ControladorExplotacion contExplotacion;
    private JFExplotacionAdd vistaAdd;
    private ExplotacionDAO modeloExp;
    private DefaultComboBoxModel modComboTipo;
    private DefaultComboBoxModel modComboSubtipo;
    private Finca finca;
    private Explotacion exp;

    public ControladorAddExplotacion(ControladorExplotacion contExp, ExplotacionDAO modeloExplotacion, Finca finca) {
        this.contExplotacion = contExp;
        this.vistaAdd = new JFExplotacionAdd();
        this.modeloExp = modeloExplotacion;
        this.finca = finca;
        
        //Asociar actionListener Ventana añadir
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jComboBoxTipo.addActionListener(this);
        this.vistaAdd.jComboBoxSubtipo.addActionListener(this);

        //Asociar focus listener
        this.vistaAdd.campoSuperficie.addFocusListener(this);
        this.vistaAdd.campoFechaC.addFocusListener(this);
        this.vistaAdd.jComboBoxTipo.addFocusListener(this);
        this.vistaAdd.jComboBoxSubtipo.addFocusListener(this);
        //Asociar key listener
        this.vistaAdd.addKeyListener(this);
        this.vistaAdd.setFocusable(true);
        this.vistaAdd.campoSuperficie.addKeyListener(this);
        this.vistaAdd.campoFechaC.addKeyListener(this);
        this.vistaAdd.jComboBoxTipo.addKeyListener(this);
        this.vistaAdd.jComboBoxSubtipo.addKeyListener(this);
        this.vistaAdd.campoAlias.addKeyListener(this);
        
        //Cargar opciones tipo Explotacion
        this.modComboTipo = new DefaultComboBoxModel();
        this.modComboSubtipo = new DefaultComboBoxModel();
        cargarComboTipos();
        this.vistaAdd.jComboBoxTipo.setModel(modComboTipo);
        this.vistaAdd.jComboBoxSubtipo.setModel(modComboSubtipo);
        this.vistaAdd.jComboBoxTipo.setSelectedIndex(-1);//Selecciona en blanco
        //Si es una nueva explotacion genera un id
        this.vistaAdd.campoId.setText(this.generarIdExplotacion());
        
        this.vistaAdd.campoId.setEnabled(false);
        this.vistaAdd.campoIdFinca.setEnabled(false);
        
        this.vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.setVisible(true);
    }

    public ControladorAddExplotacion(ControladorExplotacion contExplotacion, ExplotacionDAO modeloExp, Finca finca, Explotacion exp) {
        this(contExplotacion, modeloExp, finca);
        this.exp = exp;
        //Modifica el id de la explotacion porque al ser modificar
        //no nos interesa el nuevo id generado
        this.vistaAdd.campoId.setText(exp.getId());
        this.vistaAdd.botonAceptar.setText("Modificar");
        
        this.vistaAdd.campoAlias.setText(exp.getAlias());
        this.vistaAdd.campoFechaC.setText(Fechas.toString(exp.getfCreacion()));
        this.vistaAdd.campoIdFinca.setText(exp.getIdFinca());
        this.vistaAdd.campoSuperficie.setText(exp.getSuperficie()+"");
        this.modComboTipo.setSelectedItem(this.contExplotacion.getTipo());
        this.modComboSubtipo.setSelectedItem(this.contExplotacion.getSubtipo());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            JButton boton = (JButton) ae.getSource();

            if (boton.equals(this.vistaAdd.botonAceptar)) {                     //AÑADIR ACEPTAR

                if (this.validarDatosAdd()) {                      
                    Explotacion exp = getExplotacion();
                    if (boton.getText().equalsIgnoreCase("Aceptar")) {//AÑADIR EXPLOTACION
                        if (modeloExp.addExplotacion(exp)) {
                            JOptionPane.showMessageDialog(vistaAdd, "Explotación añadida correctamente");
                            this.contExplotacion.cargarTabla();
                            this.close();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al añadir la explotación", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                        }

                    } else if (boton.getText().equalsIgnoreCase("Modificar")) { //MODIFICAR EXPLOTACION
                        String s = actualizarExplotacion(exp);
                        if (s.charAt(0) == 'E') {//Ha habido error
                            JOptionPane.showMessageDialog(vistaAdd, s, "Error", JOptionPane.ERROR_MESSAGE);
                        } else {//Todo correcto
                            JOptionPane.showMessageDialog(vistaAdd, s);
                            this.contExplotacion.cargarTabla();
                            this.close();
                        }
                    }
                }

            }else if (boton.equals(this.vistaAdd.botonCancelar)) {                    //AÑADIR CANCELAR
                this.vistaAdd.dispose();
            }

        } else if (ae.getSource() instanceof JComboBox) {                          //JCombo modificado
            JComboBox cBox = (JComboBox) ae.getSource();
            if (cBox.equals(this.vistaAdd.jComboBoxTipo)) {
                this.cargarComboSubtipos();
            }
        }

    }
    
    private boolean validarDatosAdd() {
        boolean res = true;
        res = validarSuperficie(res);
        res = validarFechaInicio(res);
        res = validarTipo(res);
        res = validarSubtipo(res);
        return res;
    }

    private boolean validarSubtipo(boolean res) {
        if (this.vistaAdd.jComboBoxSubtipo.getSelectedIndex() == -1) {
            res = false;
            this.vistaAdd.errTipo.setText("Debes elegir un tipo y un subtipo");
        }else{
            this.vistaAdd.errTipo.setText(" ");
        }
        return res;
    }

    private boolean validarTipo(boolean res) {
        if (this.vistaAdd.jComboBoxTipo.getSelectedIndex() == -1) {
            res = false;
            this.vistaAdd.errTipo.setText("Debes elegir un tipo");
        }else{
            
        }
        return res;
    }

    private boolean validarFechaInicio(boolean res) {
        if (this.vistaAdd.campoFechaC.getText().equals("")) {
            res = false;
            this.vistaAdd.errFCreacion.setText("La fecha es obligatoria");
        }else if(Fechas.toLocalDate(this.vistaAdd.campoFechaC.getText()) != null){
            LocalDate fecha = Fechas.toLocalDate(this.vistaAdd.campoFechaC.getText());
            this.vistaAdd.errFCreacion.setText(" ");
        }else{
            this.vistaAdd.errFCreacion.setText("La fecha debe ser dd/mm/aaaa");
            res = false;
        }
        return res;
    }

    private boolean validarSuperficie(boolean res) {
        if (this.vistaAdd.campoSuperficie.getText().equals("")) {
            res = false;
            this.vistaAdd.errSuperficie.setText("La superficie es obligatoria");
        }else{
            try{
                int supExp = Integer.parseInt(this.vistaAdd.campoSuperficie.getText());
                if(supExp>finca.getSuperficie()){
                    res = false;
                    this.vistaAdd.errSuperficie.setText("La superficie es más grande que la finca");
                }else{
                    ArrayList<Explotacion> listaExp = modeloExp.recuperarPorFinca(finca.getId());
                    int sumaSupExp = 0;
                    Explotacion estaExp = getExplotacion();
                    for (Explotacion exp : listaExp) {
                        if(exp.getfFin() != null && exp.getfFin().isBefore(LocalDate.now())){
                            //Las explotaciones que ya no existen se ignoran
                            //Por si se ha tirado un invernadero o un campo se ha hecho un invernadero
                            continue;
                        }else if(modeloExp.recuperarPorId(estaExp.getId()) != null){
                            //Si esta explotacion ya estaba es porque se está modificando
                            continue;
                        }
                        sumaSupExp += exp.getSuperficie();
                    }
                    
                    if((sumaSupExp+supExp)>=finca.getSuperficie()){
                        res=false;
                        this.vistaAdd.errSuperficie.setText("Superficie demasiado grande");
                    }
                }
            }catch(NumberFormatException e){
                res=false;
                this.vistaAdd.errSuperficie.setText("Debe ser un entero");
            }
            if(res){
                this.vistaAdd.errSuperficie.setText(" ");
            }
        }
        return res;
    }

    private String generarIdExplotacion() {
        String id = this.finca.getId() + "-";
        int num = modeloExp.recuperarPorFinca(finca.getId()).size() + 1; //Obtiene numero de explotacion
        id += Integer.toString(num);
        return id;
    }

    /**
     *
     */
    public void cargarComboTipos() {
        //Cargar opciones Tipo
        this.modComboTipo.removeAllElements();
        this.modComboTipo.addElement("Invernadero");
        this.modComboTipo.addElement("Campo");
    }

    /**
     *
     */
    public void cargarComboSubtipos() {
        //Cargar opciones Subtipo segun el tipo elegido
        String sel = (String) this.vistaAdd.jComboBoxTipo.getSelectedItem();
        if (sel != null) {
            if (sel.equalsIgnoreCase("Invernadero")) {
                this.modComboSubtipo.removeAllElements();
                this.modComboSubtipo.addElement("Malla");
                this.modComboSubtipo.addElement("Tunel");

            } else if (sel.equalsIgnoreCase("Campo")) {
                this.modComboSubtipo.removeAllElements();
                this.modComboSubtipo.addElement("Regadío");
                this.modComboSubtipo.addElement("Secano");
            } else {
                this.modComboSubtipo.removeAllElements();
            }
            this.vistaAdd.jComboBoxSubtipo.setSelectedIndex(-1);
        }
    }
    
    private Explotacion getExplotacion(){ // throws NumberFormatException 
        //NUEVA FINCA
        //Recupera campos
        String id = this.vistaAdd.campoId.getText();
        int superficie = Integer.parseInt(this.vistaAdd.campoSuperficie.getText());
        String tipo = (String) this.vistaAdd.jComboBoxTipo.getSelectedItem();
        tipo = tipo + " " + (String) this.vistaAdd.jComboBoxSubtipo.getSelectedItem();
        LocalDate fCreacion = Fechas.toLocalDate(this.vistaAdd.campoFechaC.getText());
        String alias = this.vistaAdd.campoAlias.getText();
        Explotacion exp = new Explotacion(id, superficie, tipo, fCreacion, null, finca.getId(),alias);
        
        return exp;
    }
    
    private String actualizarExplotacion(Explotacion exp) {
        String s = "Error:";
        if (!modeloExp.actualizarCampo(exp.getId(), "SUPERFICIE", exp.getSuperficie() + "")) {
            s += "\nAl modificar la superficie";
        }
        
        String fecha = "";
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            fecha = Fechas.toStringMariaDb(exp.getfCreacion());
        }else{
            fecha = Fechas.toString(exp.getfCreacion());
        }
        if (!modeloExp.actualizarCampo(exp.getId(), "F_CREACION", fecha)) {
            s += "\nAl modificar la fecha de creación";
        }
        
        if (!modeloExp.actualizarCampo(exp.getId(), "TIPO", exp.getTipo())) {
            s += "\nAl modificar el tipo";
        }
        
        if (!modeloExp.actualizarCampo(exp.getId(), "ALIAS", exp.getAlias())) {
            s += "\nAl modificar el alias";
        }

        if (s.equals("Error:")) {
            s = "Se ha modificado correctamente la explotación.";
        }
        return s;
    }
    
    @Override
    public void focusGained(FocusEvent fe) {
        
    }

    /**
     *
     * @param fe
     */
    public void focusLost(FocusEvent fe) {
        if(fe.getSource().equals(vistaAdd.campoSuperficie)){
            validarSuperficie(true);
        }else if(fe.getSource().equals(vistaAdd.campoFechaC)){
            validarFechaInicio(true);
        }else if(fe.getSource().equals(vistaAdd.jComboBoxTipo)){
            validarTipo(true);
        }else if(fe.getSource().equals(vistaAdd.jComboBoxSubtipo)){
            validarSubtipo(true);
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
     * Hace que la ventana JFExplotacionAdd gane el foco y se ponga en primer plano
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
     * Realiza un dispose a la ventana JFExplotacionAdd
     */
    public void close(){
        this.vistaAdd.dispose();
    }
}
