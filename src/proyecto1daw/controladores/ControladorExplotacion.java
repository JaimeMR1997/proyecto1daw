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
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.accesobd.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.accesobd.FincaDAO;
import proyecto1daw.modelo.accesobd.PlantacionDAO;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.accesobd.VentaDAO;
import proyecto1daw.modelo.accesobd.mysql.FincaMysql;
import proyecto1daw.modelo.accesobd.sqlite.FincaSqlite;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFExplotacionAdd;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;
import proyecto1daw.vistas.JFGasto;
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

    /**
     *
     * @param vistaTabla ventana donde se muestran las explotaciones de la finca
     * @param modeloExp modelo de acceso a datos relacionados con Explotacion
     * @param finca Finca de la que se recuperaran las explotaciones
     */
    public ControladorExplotacion(JFExplotacion vistaTabla, ExplotacionDAO modeloExp, Finca finca) {
        this.vistaTabla = vistaTabla;
        this.vistaAdd = new JFExplotacionAdd();
        this.modeloExp = modeloExp;
        this.finca = finca;

        //Asociar actionListener VentanaTabla
        this.vistaTabla.botonAdd.addActionListener(this);
        this.vistaTabla.botonEliminar.addActionListener(this);
        this.vistaTabla.botonGasto.addActionListener(this);
        this.vistaTabla.botonGestionar.addActionListener(this);
        this.vistaTabla.botonMod.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);

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
        
        //Asociar mouse Listener a Tabla
        this.vistaTabla.jTableExplotaciones.addMouseListener(this);

        //Cargar tabla
        this.modTabla = new DefaultTableModel(){   //Para no poder editar las celdas de la tabla
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        this.vistaTabla.jTableExplotaciones.getTableHeader().setReorderingAllowed(false);
        //Añadir columnas
        this.modTabla.addColumn("ID");
        this.modTabla.addColumn("Alias");
        this.modTabla.addColumn("Plant.");
        this.modTabla.addColumn("Tipo");
        this.modTabla.addColumn("Superficie");
        this.modTabla.addColumn("Fecha Creación");
        this.cargarTabla();
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

    /**
     *
     * @param ae
     */
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

            }else if (boton.equals(this.vistaTabla.botonGasto)) {                       //GASTOS
                abrirGastos();

            } else if (boton.equals(this.vistaTabla.botonVolver)) {                                    
                volver();

            } else if (boton.equals(this.vistaAdd.botonAceptar)) {                     //AÑADIR ACEPTAR

                if (this.validarDatosAdd()) {                      
                    Explotacion exp = getExplotacion();
                    if (boton.getText().equalsIgnoreCase("Aceptar")) {//AÑADIR EXPLOTACION
                        if (modeloExp.addExplotacion(exp)) {
                            JOptionPane.showMessageDialog(vistaAdd, "Explotación añadida correctamente");
                            cargarTabla();
                        } else {
                            JOptionPane.showMessageDialog(vistaTabla, "Error al añadir la explotación", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                        }

                    } else if (boton.getText().equalsIgnoreCase("Modificar")) { //MODIFICAR EXPLOTACION
                        String s = actualizarExplotacion(exp);
                        if (s.charAt(0) == 'E') {//Ha habido error
                            JOptionPane.showMessageDialog(vistaAdd, s, "Error", JOptionPane.ERROR_MESSAGE);
                        } else {//Todo correcto
                            JOptionPane.showMessageDialog(vistaAdd, s);
                            cargarTabla();
                        }
                    }
                }

            }else if (boton.equals(this.vistaAdd.botonCancelar)) {                    //AÑADIR CANCELAR
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

    private void cargarTabla() {
        this.limpiarCamposAdd();
        this.vistaAdd.dispose();
        this.modTabla.setRowCount(0);
        
        ArrayList<Explotacion> listaExp = modeloExp.recuperarPorFinca(finca.getId());
        
        String[] s = new String[6];
        for (Explotacion exp : listaExp) {
            s[0] = exp.getId();
            s[1] = exp.getAlias();
            s[2] = exp.calcularEstado();//Iconos
            s[3] = exp.getTipo();
            s[4] = exp.getSuperficie() + "";
            s[5] = Fechas.toString(exp.getfCreacion());
            this.modTabla.addRow(s);
        }
    }

    private Explotacion getExplotacion() throws NumberFormatException {
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

    private void volver() {
        //VOLVER
        FincaDAO modeloFinca;
        
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            modeloFinca = new FincaMysql();
        }else{
            modeloFinca = new FincaSqlite();
        }
        
        ControladorFinca conFinca = new ControladorFinca(new JFFinca(), modeloFinca, modeloExp);
        this.vistaTabla.dispose();
    }

    private void abrirModExp() {
        int filaSel = this.vistaTabla.jTableExplotaciones.getSelectedRow();
        String idExp = (String) this.vistaTabla.jTableExplotaciones.getValueAt(filaSel, 0);
        String superficie = (String) this.vistaTabla.jTableExplotaciones.getValueAt(filaSel, 4);
        String fechaC = (String) this.vistaTabla.jTableExplotaciones.getValueAt(filaSel, 5);
        
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
            
            cargarTabla();
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
        ControladorPlantacion contPlant = new ControladorPlantacion(new JFPlantacion(),idExp, finca);
        this.vistaTabla.dispose();
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

        if (s.equals("Error:")) {
            s = "Se ha modificado correctamente la explotación.";
        }
        return s;
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

    private void limpiarCamposAdd() {
        this.vistaAdd.campoFechaC.setText("");
        this.vistaAdd.campoSuperficie.setText("");
        this.vistaAdd.campoIdFinca.setText("");
        this.vistaAdd.campoId.setText("");
        this.vistaAdd.jComboBoxTipo.setSelectedIndex(-1);
        this.vistaAdd.jComboBoxSubtipo.setSelectedIndex(-1);

        this.vistaAdd.errTipo.setText(" ");
        this.vistaAdd.errSuperficie.setText(" ");
        this.vistaAdd.errFCreacion.setText(" ");
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

    /**
     *
     * @param me
     */
    public void mouseClicked(MouseEvent me) {

    }

    /**
     *
     * @param me
     */
    public void mousePressed(MouseEvent me) {
        JTable tabla = (JTable) me.getSource();
        int fila = tabla.getSelectedRow();
        double cantidad = 0;

        if (fila != -1 && me.getClickCount() == 2) {
            abrirVentanaPlantaciones();
        }
    }

    /**
     *
     * @param me
     */
    public void mouseReleased(MouseEvent me) {

    }

    /**
     *
     * @param me
     */
    public void mouseEntered(MouseEvent me) {

    }

    /**
     *
     * @param me
     */
    public void mouseExited(MouseEvent me) {

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

    private Object getTipo() {
        int filaSel = this.vistaTabla.jTableExplotaciones.getSelectedRow();
        String s=(String) vistaTabla.jTableExplotaciones.getValueAt(filaSel, 3);
        s=s.substring(0, s.indexOf(" "));
        return s;
    }

    private Object getSubtipo() {
        int filaSel = this.vistaTabla.jTableExplotaciones.getSelectedRow();
        String s=(String) vistaTabla.jTableExplotaciones.getValueAt(filaSel, 3);
        s=s.substring(s.indexOf(" ")+1);
        return s;
    }

    private void abrirGastos() {
        ControladorGasto contGastos = new ControladorGasto(new JFGasto(), vistaTabla, modeloExp, finca);
        this.vistaTabla.dispose();
        this.vistaAdd.dispose();
    }
}
