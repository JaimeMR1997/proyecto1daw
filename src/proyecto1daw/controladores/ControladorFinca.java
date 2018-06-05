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
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.TrabajadorDAO;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFExplotacionAdd;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author alumno
 */
public class ControladorFinca implements ActionListener,MouseListener{
    private JFFinca vistaTabla;
    private JFFincaAdd vistaAdd;
    private FincaDAO modelo;
    private DefaultTableModel modTabla;

    public ControladorFinca(JFFinca vistaTabla, FincaDAO modelo,JFFincaAdd vistaAddFinca) {
        this.vistaTabla = vistaTabla;
        this.modelo = modelo;
        this.vistaAdd=vistaAddFinca;
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAdd.setLocationRelativeTo(null);
        
        //Asignar action listener a botones
        this.vistaTabla.botonAdd.addActionListener(this);
        this.vistaTabla.botonEliminar.addActionListener(this);
        this.vistaTabla.botonGestionar.addActionListener(this);
        this.vistaTabla.botonInfo.addActionListener(this);
        this.vistaTabla.botonMod.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);
        //Los de AddFinca tambien
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jCheckBoxFFin.addActionListener(this);
        //Añadir mouse listener a tabla
        this.vistaTabla.jTableFincas.addMouseListener(this);
        
        //Cargar datos en tabla
        modTabla=new DefaultTableModel();
        modTabla.addColumn("ID");
        modTabla.addColumn("Localización");
        modTabla.addColumn("Superficie");
        modTabla.addColumn("Fecha de Compra");
        modTabla.addColumn("Encargado");
        modTabla.addColumn("NºTractores");
        modTabla.addColumn("NºExplotaciones");
        rellenarTabla(modelo.recuperarTodas());
        this.vistaTabla.jTableFincas.setModel(modTabla);
        
        this.vistaAdd.campoFechaFin.setEnabled(false);
        
        this.vistaTabla.setVisible(true);
        vistaAddFinca.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaTabla.botonVolver)){                      //VOLVER
            volver();
        }else if(ae.getSource().equals(vistaTabla.botonGestionar)){              //GESTIONAR
            //Abrir ventana gestion finca seleccionada
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                abrirVentanaExplotaciones();
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getSource().equals(vistaTabla.botonAdd)){               //ABRIR AÑADIR 
            //Llamar a ventanaAñadir
            vistaAdd.botonAceptar.setText("Aceptar");
            this.limpiarCamposAdd();
            vistaAdd.setVisible(true);
            vistaAdd.setAlwaysOnTop(true);
                    
        }else if(ae.getSource().equals(vistaTabla.botonMod)){                //MODIFICAR
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                abrirModificar();
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(ae.getSource().equals(vistaTabla.botonInfo)){              //INFORMACION
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                System.out.println("En desarrollo");
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(ae.getSource().equals(vistaTabla.botonEliminar)){          //ELIMINAR
            
            //Pregunta antes de eliminar completamente
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                int confirmacion = JOptionPane.showConfirmDialog(vistaAdd,
                        "¿Estás seguro de eliminar esta finca?");
                eliminarFinca(confirmacion);
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                        + " una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getSource().equals(vistaAdd.botonAceptar)){                 //ACEPTAR_AÑADIR
            String nombreBoton=((JButton)ae.getSource()).getText();
            if(this.validarDatosAdd()){
                Finca f = getFinca();
                if(nombreBoton.equalsIgnoreCase("Aceptar")){        //NUEVA FINCA
                    if(modelo.addFinca(f)){
                        JOptionPane.showMessageDialog(vistaAdd, "Finca añadida correctamente");
                    }else{
                        JOptionPane.showMessageDialog(vistaTabla, "Error al añadir la finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                    }

                    actualizarTabla();
                }else if(nombreBoton.equalsIgnoreCase("Modificar")){    //MODIFICAR FINCA                    
                    String s = modificarFinca(f);
                    if(s.charAt(0) == 'E'){//Se ha producido error
                        JOptionPane.showMessageDialog(vistaAdd, s, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{//Todo correcto
                        JOptionPane.showMessageDialog(vistaAdd, s);
                        actualizarTabla();
                    }
                    
                }
            }
            
        }else if(ae.getSource().equals(vistaAdd.botonCancelar)){                 //CANCELAR_AÑADIR
            limpiarCamposAdd();
            this.vistaAdd.dispose();
            
        }else if(ae.getSource().equals(this.vistaAdd.jCheckBoxFFin)){           //CHECBOX FECHA FIN
            if(this.isFFinSelected()){
                this.vistaAdd.campoFechaFin.setEnabled(true);
            }else{
                this.vistaAdd.campoFechaFin.setEnabled(false);
            }
        }
    }

    private Finca getFinca() throws NumberFormatException {
        //Recupera campos
        String id = this.vistaAdd.campoId.getText();
        String localidad = this.vistaAdd.campoLocalidad.getText();
        int superficie = Integer.parseInt(this.vistaAdd.campoSuperficie.getText());
        LocalDate fCreacion = Fechas.toLocalDate(this.vistaAdd.campoFechaC.getText());
        LocalDate fFin = Fechas.toLocalDate(this.vistaAdd.campoFechaFin.getText());
        Finca f = new Finca(id, localidad, superficie, fCreacion, fFin);
        return f;
    }

    private void actualizarTabla() {
        this.vistaAdd.dispose();
        this.modTabla.setRowCount(0);
        this.rellenarTabla(modelo.recuperarTodas());
    }

    private void eliminarFinca(int confirmacion) {
        if(confirmacion==JOptionPane.YES_OPTION){
            String idFinca=(String) this.vistaTabla.jTableFincas.getValueAt(this.vistaTabla.jTableFincas.getSelectedRow(),0);
            modelo.borrarFinca(idFinca);
            this.limpiarCamposAdd();
            actualizarTabla();
        }
    }

    private void abrirModificar() {
        //Carga los datos de la tabla al formulario
        int filaSelec=this.vistaTabla.jTableFincas.getSelectedRow();
        String id=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,0);
        String localizacion=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,1);
        String superficie=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,2);
        String fCreacion=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,3);
        this.vistaAdd.campoId.setText(id);
        this.vistaAdd.campoLocalidad.setText(localizacion);
        this.vistaAdd.campoSuperficie.setText(superficie);
        this.vistaAdd.campoFechaC.setText(fCreacion);
        this.vistaAdd.etiquetaId.setText(id);
        
        vistaAdd.botonAceptar.setText("Modificar");
        vistaAdd.setVisible(true);
    }

    private void volver() {
        //VOLVER
        //Volver a Inicio
        ControladorInicio conInicio = new ControladorInicio(new JFInicio());
        this.vistaTabla.dispose();
    }

    private void abrirVentanaExplotaciones() {
        //Abre explotaciones de esa Finca
        int fila = this.vistaTabla.jTableFincas.getSelectedRow();
        String finca = (String) this.vistaTabla.jTableFincas.getValueAt(fila, 0);
        ControladorExplotacion conExplotacion = new ControladorExplotacion(new JFExplotacion(), new ExplotacionDAO(), new JFExplotacionAdd(),finca);
        this.vistaTabla.dispose();
    }

    private String modificarFinca(Finca f) {
        String res="Error:";
        String idAnt = this.vistaAdd.etiquetaId.getText();
        
        if(!modelo.actualizarCampo(idAnt, "LOCALIDAD", f.getLocalidad())){
            res+="\nAl actualizar la localidad";
        }
        if(!modelo.actualizarCampo(idAnt, "SUPERFICIE", f.getSuperficie()+"")){
            res+="\nAl actualizar la superficie";
        }
        if(!modelo.actualizarCampo(idAnt, "F_COMPRA", Fechas.toString(f.getfCompra()))){
            res+="\nAl actualizar la fecha de compra";
        }
        if(isFFinSelected() && !modelo.actualizarCampo(idAnt, "F_FIN", Fechas.toString(f.getfFin()))){
            //Si no esta FFin seleccionado no se ejecuta el metodo actualizarCampo
            res+="\nAl actualizar la fecha de fin";
        }
        if(!modelo.actualizarCampo(idAnt, "ID_FINCA", f.getId())){
            res+="\nAl actualizar el ID de la finca";
        }
        
        if(res.equals("Error:")){
            res="La finca se ha actualizado correctamente.";
        }
        return res;
    }

    private void limpiarCamposAdd() {
        //CANCELAR_AÑADIR
        this.vistaAdd.campoId.setText("");
        this.vistaAdd.campoLocalidad.setText("");
        this.vistaAdd.campoSuperficie.setText("");
        this.vistaAdd.campoFechaC.setText("");
        this.vistaAdd.campoFechaFin.setText("");
        this.vistaAdd.etiquetaId.setText("");
        this.vistaAdd.jCheckBoxFFin.setSelected(false);
    }
    
    public boolean isFFinSelected(){
        boolean res=false;
        if(this.vistaAdd.jCheckBoxFFin.isSelected()){
            res=true;
        }
        return res;
    }
    
    public void rellenarTabla(ArrayList<Finca> listaFincas){
        String[] fila = new String[7];
        for (Finca s : listaFincas) {
            fila[0]=s.getId();
            fila[1]=s.getLocalidad();
            fila[2]=s.getSuperficie()+"";
            fila[3]=Fechas.toString(s.getfCompra());
            fila[4]=s.getEncargado();
            fila[5]="";//NUM TRACTORES
            fila[6]="";//NUM EXPLOTACIONES
            modTabla.addRow(fila);
        }
        
    }
    
    public boolean validarDatosAdd(){
        boolean res=true;
        if(vistaAdd.campoId.getText().equals("") || vistaAdd.campoId == null){
            res=false;
        }
        if(vistaAdd.campoLocalidad.getText().equals("") || vistaAdd.campoLocalidad == null){
            res=false;
        }
        if(vistaAdd.campoSuperficie.getText().equals("") || vistaAdd.campoSuperficie == null){
            res=false;
        }else{
            try{
                int num=Integer.parseInt(vistaAdd.campoSuperficie.getText());
                if(num<0){
                    res=false;
                }
            }catch(NumberFormatException e){
                res=false;
            }
        }
        if(vistaAdd.campoFechaC.getText().equals("") || vistaAdd.campoFechaC == null){
            res=false;
        }else if(Fechas.toLocalDate(vistaAdd.campoFechaC.getText()) == null){
            res=false;
        }
        if(this.isFFinSelected()){
            if(this.vistaAdd.campoFechaFin.equals("") || vistaAdd.campoFechaFin == null){
                res=false;
            }else if(Fechas.toLocalDate(vistaAdd.campoFechaFin.getText()) == null){
                res=false;
            }
        }
        return res;
    }

    
    public void mouseClicked(MouseEvent me) {
        
    }

    
    public void mousePressed(MouseEvent me) {
        JTable tabla = (JTable) me.getSource();
            int fila = tabla.getSelectedRow();
            double cantidad = 0;
            
            if(fila != -1 && me.getClickCount() == 2){
                abrirVentanaExplotaciones();
            }
    }

    
    public void mouseReleased(MouseEvent me) {
        
    }

    
    public void mouseEntered(MouseEvent me) {
        
    }

    
    public void mouseExited(MouseEvent me) {
        
    }
}
