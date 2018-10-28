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
public class ControladorExplotacion implements ActionListener, MouseListener {

    private JFExplotacion vistaTabla;
    private ExplotacionDAO modeloExp;
    private DefaultTableModel modTabla;
    private Finca finca;
    private ControladorAddExplotacion controladorAdd;

    /**
     *
     * @param vistaTabla ventana donde se muestran las explotaciones de la finca
     * @param modeloExp modelo de acceso a datos relacionados con Explotacion
     * @param finca Finca de la que se recuperaran las explotaciones
     */
    public ControladorExplotacion(JFExplotacion vistaTabla, ExplotacionDAO modeloExp, Finca finca) {
        this.vistaTabla = vistaTabla;
        this.modeloExp = modeloExp;
        this.finca = finca;

        //Asociar actionListener VentanaTabla
        this.vistaTabla.botonAdd.addActionListener(this);
        this.vistaTabla.botonEliminar.addActionListener(this);
        this.vistaTabla.botonGasto.addActionListener(this);
        this.vistaTabla.botonGestionar.addActionListener(this);
        this.vistaTabla.botonMod.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);

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

        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaTabla.jLabelFincaId.setText("Finca - " + finca.getId());
        this.vistaTabla.setVisible(true);
    }

    /**
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            JButton boton = (JButton) ae.getSource();

            if (boton.equals(this.vistaTabla.botonEliminar)) {                  //ELIMINAR
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

            }else if (boton.equals(this.vistaTabla.botonAdd)) {                       //NUEVA EXP
                abrirAddExp();
            }else if (boton.equals(this.vistaTabla.botonMod)) {                       //MODIFICAR EXP
                if(this.vistaTabla.jTableExplotaciones.getSelectedRow() != -1){
                    abrirModExp();
                }
            }else if (boton.equals(this.vistaTabla.botonGasto)) {                       //GASTOS
                abrirGastos();

            } else if (boton.equals(this.vistaTabla.botonVolver)) {                     //VOLVER
                volver();
            }
        }
    }

    protected void cargarTabla() {
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
        Explotacion exp = modeloExp.recuperarPorId(idExp);
        
        if(this.controladorAdd == null){
            this.controladorAdd = new ControladorAddExplotacion(this, modeloExp, finca, exp);
        }else if(this.controladorAdd.isVentanaAbierta()){
            this.controladorAdd.setFocused();
        }else{
            this.controladorAdd = new ControladorAddExplotacion(this, modeloExp, finca, exp);
        }
    }

    private void eliminarExp(){ // throws HeadlessException 
        int b = JOptionPane.showConfirmDialog(vistaTabla, "¿Estás seguro de eliminar esta explotación?");
        if (b == JOptionPane.YES_OPTION) {
            String idExp = (String) this.vistaTabla.jTableExplotaciones.getValueAt(this.vistaTabla.jTableExplotaciones.getSelectedRow(), 0);
            modeloExp.borrarExplotacion(idExp);
            
            cargarTabla();
        }
    }

    private void abrirAddExp(){
        if(this.controladorAdd == null){
            this.controladorAdd = new ControladorAddExplotacion(this, modeloExp, finca);
        }else if(this.controladorAdd.isVentanaAbierta()){
            this.controladorAdd.setFocused();
        }else{
            this.controladorAdd = new ControladorAddExplotacion(this, modeloExp, finca);
        }
    }

    private void abrirVentanaPlantaciones() {
        int filaSel = vistaTabla.jTableExplotaciones.getSelectedRow();
        String idExp = (String) vistaTabla.jTableExplotaciones.getValueAt(filaSel, 0);
        ControladorPlantacion contPlant = new ControladorPlantacion(new JFPlantacion(),idExp, finca);
        this.vistaTabla.dispose();
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

    protected Object getTipo() {
        int filaSel = this.vistaTabla.jTableExplotaciones.getSelectedRow();
        String s=(String) vistaTabla.jTableExplotaciones.getValueAt(filaSel, 3);
        s=s.substring(0, s.indexOf(" "));
        return s;
    }

    protected Object getSubtipo() {
        int filaSel = this.vistaTabla.jTableExplotaciones.getSelectedRow();
        String s=(String) vistaTabla.jTableExplotaciones.getValueAt(filaSel, 3);
        s=s.substring(s.indexOf(" ")+1);
        return s;
    }

    private void abrirGastos() {
        if(this.controladorAdd != null){
            this.controladorAdd.setFocused();
        }else{
            ControladorGasto contGastos = new ControladorGasto(new JFGasto(), vistaTabla, modeloExp, finca);
            this.vistaTabla.dispose();
        }
    }
}
