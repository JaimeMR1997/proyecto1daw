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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.accesobd.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.accesobd.FincaDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author alumno
 */
public class ControladorFinca implements ActionListener,MouseListener{
    private final int FILA_ID = 0;
    private final int FILA_ALIAS = 1;
    private final int FILA_LOCALIDAD = 2;
    private final int FILA_SUPERFICIE = 3;
    private final int FILA_FCOMPRA = 4;
    private final int FILA_NENCARGADOS = 5;
    private final int FILA_NEXPLOTACIONES = 6;
    private final int FILA_NTRACTORES = 6;
    
    private JFFinca vistaTabla;
    private FincaDAO modeloFinca;
    private ExplotacionDAO modeloExp;
    private DefaultTableModel modTabla;
    private ControladorAddFinca controladorAdd;
    
    /**
     *
     * @param vistaTabla Ventana de tipo JFFinca contiene la tabla con las fincas y
     * botones para gestionar,modificar,añadir y asignar @see Encargado
     * @param modeloFinca Es el modelo de acceso a datos relacionados con Finca
     * @param modeloExp Es el modelo de acceso a datos relacionados con Explotacion
     * @see Finca
     * @see Explotacion
     * @see JFFinca
     */
    public ControladorFinca(JFFinca vistaTabla, FincaDAO modeloFinca,ExplotacionDAO modeloExp) {
        this.vistaTabla = vistaTabla;
        this.modeloFinca = modeloFinca;
        this.modeloExp = modeloExp;
        this.vistaTabla.setLocationRelativeTo(null);
        
        //Asignar action listener a botones
        this.vistaTabla.botonAdd.addActionListener(this);
        this.vistaTabla.botonEliminar.addActionListener(this);
        this.vistaTabla.botonGestionar.addActionListener(this);
        this.vistaTabla.botonEncargados.addActionListener(this);
        this.vistaTabla.botonMod.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);
        //Añadir mouse listener a tabla
        this.vistaTabla.jTableFincas.addMouseListener(this);
        
        //Cargar datos en tabla
        modTabla=new DefaultTableModel(){   //Para no poder editar las celdas de la tabla
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        this.vistaTabla.jTableFincas.getTableHeader().setReorderingAllowed(false);
        modTabla.addColumn("ID");
        modTabla.addColumn("Alias");
        modTabla.addColumn("Localización");
        modTabla.addColumn("Superficie");
        modTabla.addColumn("Fecha de Compra");
        modTabla.addColumn("NºEncargados");
        //modTabla.addColumn("NºTractores");
        modTabla.addColumn("NºExplotaciones");
        rellenarTabla(modeloFinca.recuperarTodas());
        
        this.vistaTabla.jTableFincas.setModel(modTabla);
        this.vistaTabla.setVisible(true);
    }
    
    /**
     *
     * @param ae es el objeto generado por la accion.Según el botón usado 
     * se realiza un tratamiento
     */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaTabla.botonVolver)){                      //VOLVER
            volver();
        }else if(ae.getSource().equals(vistaTabla.botonGestionar)){              //GESTIONAR
            //Abrir ventana gestion finca seleccionada
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                abrirVentanaExplotaciones();
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                        + " una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getSource().equals(vistaTabla.botonAdd)){                           
            abrirAdd();
            
        }else if(ae.getSource().equals(vistaTabla.botonMod)){                //MODIFICAR
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                abrirModificar();
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                        + " una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(ae.getSource().equals(vistaTabla.botonEncargados)){              //ABRIR ENCARGADOS
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                int fila = this.vistaTabla.jTableFincas.getSelectedRow();
                String idFinca = (String) this.vistaTabla.jTableFincas.getValueAt(fila, FILA_ID);
                Finca finca = modeloFinca.recuperarPorId(idFinca);
                ControladorEncFinca contEnc = new ControladorEncFinca(this, modeloFinca, modeloExp, finca);
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                        + " una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(ae.getSource().equals(vistaTabla.botonEliminar)){          //ELIMINAR
            //Pregunta antes de eliminar completamente
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                int confirmacion = JOptionPane.showConfirmDialog(vistaTabla,
                        "¿Estás seguro de eliminar esta finca?");
                eliminarFinca(confirmacion);
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                        + " una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * @return Crea un nuevo contgorladorAddFinca que abre una ventna con un formulario
     * @see JFFincaAdd
     * @see ControladorAddFinca
     */
    private void abrirAdd() {
        //ABRIR AÑADIR
        //Llamar a ventana Añadir
        if(this.controladorAdd == null){
            this.controladorAdd = new ControladorAddFinca(this, modeloFinca);
        }else if(this.controladorAdd.isVentanaAbierta()){
            this.controladorAdd.setFocused();
        }else{
            this.controladorAdd = new ControladorAddFinca(this, modeloFinca);
        }
        
    }
    
    /**
     * @return Recarga la tabla de la ventana JFFinca
     * @see JFFinca
     */
    public void actualizarTabla() {
        this.modTabla.setRowCount(0);
        this.rellenarTabla(modeloFinca.recuperarTodas());
    }

    /**
     * @return Elimina la finca seleccionada
     * @param Confirmacion pedida al usuario con un JOptionPane
     */
    
    private void eliminarFinca(int confirmacion) {
        if(confirmacion==JOptionPane.YES_OPTION){
            String idFinca=(String) this.vistaTabla.jTableFincas.getValueAt(this.vistaTabla.jTableFincas.getSelectedRow(),FILA_ID);
            modeloFinca.borrarFinca(idFinca);
            actualizarTabla();
        }
    }
    /**
     * @return Crea un nuevo contgorladorAddFinca que abre una ventana con un formulario
     * con  los datos de la finca recien recuperada de la BD con la información actualizada.
     * @see JFFincaAdd
     * @see ControladorAddFinca
     */

    private void abrirModificar() {
        //Carga los datos de la tabla al formulario
        int filaSelec=this.vistaTabla.jTableFincas.getSelectedRow();
        String id=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,FILA_ID);
        Finca f = modeloFinca.recuperarPorId(id);
               
        if(this.controladorAdd == null){
            this.controladorAdd = new ControladorAddFinca(this, modeloFinca,f);
        }else if(this.controladorAdd.isVentanaAbierta()){
            this.controladorAdd.setFocused();
        }else{
            this.controladorAdd = new ControladorAddFinca(this, modeloFinca,f);
        }
    }

    /**
     * @return Vuelve a la ventana de inicio
     * @see JFInicio
     */
    
    private void volver() {
        //Volver a Inicio
        ControladorInicio conInicio = new ControladorInicio(new JFInicio());
        this.vistaTabla.dispose();
    }

    /**
     * @return Recarga la tabla de la ventana JFFinca
     * @see JFFinca
     */
    private void abrirVentanaExplotaciones() {
        //Abre explotaciones de esa Finca
        int fila = this.vistaTabla.jTableFincas.getSelectedRow();
        String idFinca = (String) this.vistaTabla.jTableFincas.getValueAt(fila, FILA_ID);
        Finca finca = modeloFinca.recuperarPorId(idFinca);
        ControladorExplotacion conExplotacion = new ControladorExplotacion(new JFExplotacion(),modeloExp, finca);
        this.vistaTabla.dispose();
    }

      
    /**
     *
     * @param listaFincas La lista de fincas a ser cargada en la tabla
     * de la ventana JFFinca
     */
    public void rellenarTabla(ArrayList<Finca> listaFincas){
        String[] fila = new String[7];
        for (Finca f : listaFincas) {
            fila[FILA_ID]=f.getId();
            fila[FILA_ALIAS]=f.getAlias();
            fila[FILA_LOCALIDAD]=f.getLocalidad();
            fila[FILA_SUPERFICIE]=f.getSuperficie()+"";
            fila[FILA_FCOMPRA]=Fechas.toString(f.getfCompra());
            fila[FILA_NENCARGADOS]=f.getListaEncargados().size()+"";//Num encargados
            //fila[FILA_NTRACTORES]="";//NUM TRACTORES
            fila[FILA_NEXPLOTACIONES]=modeloExp.contarPorFinca(f.getId())+"";//NUM EXPLOTACIONES
            modTabla.addRow(fila);
        }
        
    }
    
    /**
     *
     * @param me evento generado por el click del raton
     */
    public void mouseClicked(MouseEvent me) {
        
    }

    /**
     *
     * @param me evento generado por el click del raton
     */
    public void mousePressed(MouseEvent me) {
        JTable tabla = (JTable) me.getSource();
            int fila = tabla.getSelectedRow();
            double cantidad = 0;
            
            if(fila != -1 && me.getClickCount() == 2){
                abrirVentanaExplotaciones();
            }
    }

    /**
     *
     * @param me evento generado por el click del raton
     */
    public void mouseReleased(MouseEvent me) {
        
    }

    /**
     *
     * @param me evento generado por el click del raton
     */
    public void mouseEntered(MouseEvent me) {
        
    }

    /**
     *
     * @param me evento generado por el click del raton
     */
    public void mouseExited(MouseEvent me) {
        
    }

}
