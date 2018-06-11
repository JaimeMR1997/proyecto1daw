/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.modelo.PlantacionDAO;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.VentaDAO;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFExplotacionAdd;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;
import proyecto1daw.vistas.JFPlantacion;

/**
 *
 * @author Jaime
 */
public class ControladorExplotacion implements ActionListener, MouseListener,FocusListener {

    private JFExplotacion vistaTabla;
    private JFExplotacionAdd vistaAdd;
    private ExplotacionDAO modeloExp;
    private DefaultTableModel modTabla;
    private DefaultComboBoxModel modComboTipo;
    private DefaultComboBoxModel modComboSubtipo;
    private Finca finca;

    public ControladorExplotacion(ExplotacionDAO modeloExp, Finca finca) {
        this.vistaTabla = new JFExplotacion();
        this.vistaAdd = new JFExplotacionAdd();
        this.modeloExp = modeloExp;
        this.finca = finca;

        //Asociar actionListener VentanaTabla
        this.vistaTabla.botonAdd.addActionListener(this);
        this.vistaTabla.botonEliminar.addActionListener(this);
        this.vistaTabla.botonGestionar.addActionListener(this);
        this.vistaTabla.botonMod.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);

        //Asociar actionListener Ventana añadir
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jComboBoxTipo.addActionListener(this);
        this.vistaAdd.jComboBoxSubtipo.addActionListener(this);

        //Asociar mouse Listener a Tabla
        this.vistaTabla.jTableExplotaciones.addMouseListener(this);

        //Cargar tabla
        this.modTabla = new DefaultTableModel();
        this.modTabla.addColumn("ID");
        this.modTabla.addColumn("Plant.");
        this.modTabla.addColumn("Tipo");
        this.modTabla.addColumn("Superficie");
        this.modTabla.addColumn("Fecha Creación");
        this.rellenarTabla(this.modeloExp.recuperarPorFinca(this.finca.getId()));
        this.vistaTabla.jTableExplotaciones.setModel(modTabla);

        //Cargar opciones tipo Explotacion
        this.modComboTipo = new DefaultComboBoxModel();
        this.modComboSubtipo = new DefaultComboBoxModel();
        cargarComboTipos();
        this.vistaAdd.jComboBoxTipo.setModel(modComboTipo);
        this.vistaAdd.jComboBoxSubtipo.setModel(modComboSubtipo);
        this.vistaAdd.jComboBoxTipo.setSelectedIndex(-1);//Selecciona en blanco

        //Poner visible
        this.vistaAdd.campoId.setEnabled(false);
        this.vistaAdd.campoIdFinca.setEnabled(false);
        this.vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaTabla.jLabelFincaId.setText("Finca - " + finca.getId());
        this.limpiarCamposAdd();
        this.vistaTabla.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            JButton boton = (JButton) ae.getSource();

            if (boton.equals(this.vistaTabla.botonAdd)) {                           //ABRIR ADD
                abrirAdd();
                
            } else if (boton.equals(this.vistaTabla.botonEliminar)) {                  //ELIMINAR
                if (this.vistaTabla.jTableExplotaciones.getSelectedRow() != -1) {
                    eliminarExp();
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una explotación", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }
                
            } else if (boton.equals(this.vistaTabla.botonGestionar)) {                  //GESTIONAR
                if (this.vistaTabla.jTableExplotaciones.getSelectedRow() != -1) {
                    abrirVentanaPlantaciones();
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una explotación", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonMod)) {                       //MODIFICAR
                if (this.vistaTabla.jTableExplotaciones.getSelectedRow() != -1) {
                    abrirModExp();
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una explotación", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonVolver)) {                                    
                volver();

            } else if (boton.equals(this.vistaAdd.botonAceptar)) {                     //AÑADIR ACEPTAR

                if (this.validarDatosAdd()) {                      
                    Explotacion exp = getExplotacion();
                    if (boton.getText().equalsIgnoreCase("Aceptar")) {//AÑADIR EXPLOTACION
                        if (modeloExp.addExplotacion(exp)) {
                            JOptionPane.showMessageDialog(vistaAdd, "Explotación añadida correctamente");
                            actualizarTabla();
                        } else {
                            JOptionPane.showMessageDialog(vistaTabla, "Error al añadir la explotación", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                        }

                    } else if (boton.getText().equalsIgnoreCase("Modificar")) { //MODIFICAR EXPLOTACION
                        String s = actualizarExplotacion(exp);
                        if (s.charAt(0) == 'E') {//Ha habido error
                            JOptionPane.showMessageDialog(vistaAdd, s, "Error", JOptionPane.ERROR_MESSAGE);
                        } else {//Todo correcto
                            JOptionPane.showMessageDialog(vistaAdd, s);
                            actualizarTabla();
                        }
                    }
                }

            } else if (boton.equals(this.vistaAdd.botonCancelar)) {                    //AÑADIR CANCELAR
                limpiarCamposAdd();
                this.vistaAdd.dispose();
            }

        } else if (ae.getSource() instanceof JComboBox) {                          //JCombo modificado
            JComboBox cBox = (JComboBox) ae.getSource();
            if (cBox.equals(this.vistaAdd.jComboBoxTipo)) {
                this.cargarComboSubtipos();
            }
        }

    }

    private void actualizarTabla() {
        this.limpiarCamposAdd();
        this.vistaAdd.dispose();
        this.modTabla.setRowCount(0);
        this.rellenarTabla(modeloExp.recuperarPorFinca(finca.getId()));
    }

    private Explotacion getExplotacion() throws NumberFormatException {
        //NUEVA FINCA
        //Recupera campos
        String id = this.vistaAdd.campoId.getText();
        int superficie = Integer.parseInt(this.vistaAdd.campoSuperficie.getText());
        String tipo = (String) this.vistaAdd.jComboBoxTipo.getSelectedItem();
        tipo = tipo + " " + (String) this.vistaAdd.jComboBoxSubtipo.getSelectedItem();
        LocalDate fCreacion = Fechas.toLocalDate(this.vistaAdd.campoFechaC.getText());
        Explotacion exp = new Explotacion(id, superficie, tipo, fCreacion, null, finca.getId());
        return exp;
    }

    private void volver() {
        //VOLVER
        ControladorFinca conFinca = new ControladorFinca(new JFFinca(), new FincaDAO(), modeloExp);
        this.vistaTabla.dispose();
    }

    private void abrirModExp() {
        int filaSel = this.vistaTabla.jTableExplotaciones.getSelectedRow();
        String idExp = (String) this.vistaTabla.jTableExplotaciones.getValueAt(filaSel, 0);
        String superficie = (String) this.vistaTabla.jTableExplotaciones.getValueAt(filaSel, 3);
        String fechaC = (String) this.vistaTabla.jTableExplotaciones.getValueAt(filaSel, 4);
        
        this.vistaAdd.campoId.setText(idExp);
        this.vistaAdd.campoSuperficie.setText(superficie);
        this.vistaAdd.campoFechaC.setText(fechaC);
        this.vistaAdd.campoIdFinca.setText(finca.getId());
        
        this.vistaAdd.jComboBoxTipo.setSelectedItem(getTipo());
        this.vistaAdd.jComboBoxSubtipo.setSelectedItem(getSubtipo());
        
        this.vistaAdd.botonAceptar.setText("Modificar");
        this.vistaAdd.setVisible(true);
    }

    private void eliminarExp() throws HeadlessException {
        int b = JOptionPane.showConfirmDialog(vistaAdd, "¿Estás seguro de eliminar esta explotación?");
        if (b == JOptionPane.YES_OPTION) {
            String idExp = (String) this.vistaTabla.jTableExplotaciones.getValueAt(this.vistaTabla.jTableExplotaciones.getSelectedRow(), 0);
            modeloExp.borrarExplotacion(idExp);
            
            actualizarTabla();
        }
    }

    private void abrirAdd(){
        vistaAdd.botonAceptar.setText("Aceptar");
        this.vistaAdd.campoId.setText(this.generarIdExplotacion());
        this.vistaAdd.campoIdFinca.setText(finca.getId());

        vistaAdd.setVisible(true);
        vistaAdd.setAlwaysOnTop(true);
    }

    private void abrirVentanaPlantaciones() {
        int filaSel = vistaTabla.jTableExplotaciones.getSelectedRow();
        String idExp = (String) vistaTabla.jTableExplotaciones.getValueAt(filaSel, 0);
        ControladorPlantacion contPlant = new ControladorPlantacion(idExp, finca);
        this.vistaTabla.dispose();
    }

    private String actualizarExplotacion(Explotacion exp) {
        String s = "Error:";
        if (!modeloExp.actualizarCampo(exp.getId(), "SUPERFICIE", exp.getSuperficie() + "")) {
            s += "\nAl modificar la superficie";
        }
        if (!modeloExp.actualizarCampo(exp.getId(), "F_CREACION", Fechas.toString(exp.getfCreacion()))) {
            s += "\nAl modificar la fecha de creación";
        }
        if (!modeloExp.actualizarCampo(exp.getId(), "TIPO", exp.getTipo())) {
            s += "\nAl modificar el tipo";
        }

        if (s.equals("Error:")) {
            s = "Se ha modificado correctamente la explotación.";
        }
        return s;
    }

    private boolean validarDatosAdd() {
        boolean res = true;
        res = validarSuperficie(res);
        if (this.vistaAdd.campoFechaC.getText().equals("")) {
            res = false;
            this.vistaAdd.errFCreacion.setText("La fecha es obligatoria");
        }else if(Fechas.toLocalDate(this.vistaAdd.campoFechaC.getText()) != null){
            LocalDate fecha = Fechas.toLocalDate(this.vistaAdd.campoFechaC.getText());
        }
        if (this.vistaAdd.jComboBoxTipo.getSelectedIndex() == -1) {
            res = false;
            this.vistaAdd.errTipo.setText("Debes elegir un tipo");
        }
        if (this.vistaAdd.jComboBoxSubtipo.getSelectedIndex() == -1) {
            res = false;
            this.vistaAdd.errTipo.setText("Debes elegir un tipo y un subtipo");
        }
        return res;
    }

    private boolean validarSuperficie(boolean res) {
        if (this.vistaAdd.campoSuperficie.getText().equals("")) {
            res = false;
            this.vistaAdd.errSuperficie.setText("La superficie es obligatoria");
        }else{
            try{
                Integer.parseInt(this.vistaAdd.campoSuperficie.getText());
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

    private void limpiarCamposAdd() {
        this.vistaAdd.campoFechaC.setText("");
        this.vistaAdd.campoSuperficie.setText("");
        this.vistaAdd.campoIdFinca.setText("");
        this.vistaAdd.campoId.setText("");
        this.vistaAdd.jComboBoxTipo.setSelectedIndex(-1);
        this.vistaAdd.jComboBoxSubtipo.setSelectedIndex(-1);
    }

    private String generarIdExplotacion() {
        String id = this.finca.getId() + "-";
        int num = modeloExp.recuperarPorFinca(finca.getId()).size() + 1; //Obtiene numero de explotacion
        id += Integer.toString(num);
        return id;
    }

    public void rellenarTabla(ArrayList<Explotacion> listaExp) {
        String[] s = new String[5];
        for (Explotacion exp : listaExp) {
            s[0] = exp.getId();
            s[1] = exp.calcularEstado();//Iconos
            s[2] = exp.getTipo();
            s[3] = exp.getSuperficie() + "";
            s[4] = Fechas.toString(exp.getfCreacion());
        }
        this.modTabla.addRow(s);
    }

    public void cargarComboTipos() {
        //Cargar opciones Tipo
        this.modComboTipo.removeAllElements();
        this.modComboTipo.addElement("Invernadero");
        this.modComboTipo.addElement("Campo");
    }

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

    public void mouseClicked(MouseEvent me) {

    }

    public void mousePressed(MouseEvent me) {
        JTable tabla = (JTable) me.getSource();
        int fila = tabla.getSelectedRow();
        double cantidad = 0;

        if (fila != -1 && me.getClickCount() == 2) {
            abrirVentanaPlantaciones();
        }
    }

    public void mouseReleased(MouseEvent me) {

    }

    public void mouseEntered(MouseEvent me) {

    }

    public void mouseExited(MouseEvent me) {

    }

    public void focusGained(FocusEvent fe) {
        
    }

    public void focusLost(FocusEvent fe) {
        if(fe.getSource().equals(vistaAdd.campoSuperficie)){
            validarSuperficie(true);
        }else if(fe.getSource().equals(vistaAdd.campoFechaC)){
            
        }
    }

    private Object getTipo() {
        int filaSel = this.vistaTabla.jTableExplotaciones.getSelectedRow();
        String s=(String) vistaTabla.jTableExplotaciones.getValueAt(filaSel, 2);
        s=s.substring(0, s.indexOf(" "));
        return s;
    }

    private Object getSubtipo() {
        int filaSel = this.vistaTabla.jTableExplotaciones.getSelectedRow();
        String s=(String) vistaTabla.jTableExplotaciones.getValueAt(filaSel, 2);
        s=s.substring(s.indexOf(" ")+1);
        return s;
    }
}
