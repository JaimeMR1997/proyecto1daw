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
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author alumno
 */
public class ControladorFinca implements ActionListener,MouseListener,FocusListener{
    private JFFinca vistaTabla;
    private JFFincaAdd vistaAdd;
    private FincaDAO modeloFinca;
    private ExplotacionDAO modeloExp;
    private DefaultTableModel modTabla;

    public ControladorFinca(JFFinca vistaTabla, FincaDAO modeloFinca,ExplotacionDAO modeloExp) {
        this.vistaTabla = vistaTabla;
        this.modeloFinca = modeloFinca;
        this.modeloExp = modeloExp;
        this.vistaAdd= new JFFincaAdd();
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAdd.setLocationRelativeTo(null);
        
        //Asignar action listener a botones
        this.vistaTabla.botonAdd.addActionListener(this);
        this.vistaTabla.botonEliminar.addActionListener(this);
        this.vistaTabla.botonGestionar.addActionListener(this);
        this.vistaTabla.botonEncargados.addActionListener(this);
        this.vistaTabla.botonMod.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);
        //Los de AddFinca tambien
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jCheckBoxFFin.addActionListener(this);
        //Añadir focus listener a campos
        this.vistaAdd.campoId.addFocusListener(this);
        this.vistaAdd.campoFechaC.addFocusListener(this);
        this.vistaAdd.campoFechaFin.addFocusListener(this);
        this.vistaAdd.campoLocalidad.addFocusListener(this);
        this.vistaAdd.campoSuperficie.addFocusListener(this);
        //Añadir mouse listener a tabla
        this.vistaTabla.jTableFincas.addMouseListener(this);
        
        //Cargar datos en tabla
        modTabla=new DefaultTableModel();
        modTabla.addColumn("ID");
        modTabla.addColumn("Localización");
        modTabla.addColumn("Superficie");
        modTabla.addColumn("Fecha de Compra");
        modTabla.addColumn("NºEncargados");
        //modTabla.addColumn("NºTractores");
        modTabla.addColumn("NºExplotaciones");
        rellenarTabla(modeloFinca.recuperarTodas());
        this.vistaTabla.jTableFincas.setModel(modTabla);
        
        this.vistaAdd.campoFechaFin.setEnabled(false);
        
        this.vistaTabla.setVisible(true);
        vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
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
                String idFinca = (String) this.vistaTabla.jTableFincas.getValueAt(fila, 0);
                Finca finca = modeloFinca.recuperarPorId(idFinca);
                ControladorEncFinca contEnc = new ControladorEncFinca(vistaTabla, vistaAdd, modeloFinca, modeloExp, finca);
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                        + " una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
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
                    if(modeloFinca.addFinca(f)){
                        JOptionPane.showMessageDialog(vistaAdd, "Finca añadida correctamente");
                        actualizarTabla();
                    }else{
                        JOptionPane.showMessageDialog(vistaTabla, "Error al añadir la finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                    }
                    
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

    private void abrirAdd() {
        //ABRIR AÑADIR
        //Llamar a ventanaAñadir
        this.vistaAdd.campoId.setEnabled(true);
        vistaAdd.botonAceptar.setText("Aceptar");
        this.limpiarCamposAdd();
        vistaAdd.setVisible(true);
        vistaAdd.setAlwaysOnTop(true);
    }

    private Finca getFinca(){
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
        this.rellenarTabla(modeloFinca.recuperarTodas());
    }

    private void eliminarFinca(int confirmacion) {
        if(confirmacion==JOptionPane.YES_OPTION){
            String idFinca=(String) this.vistaTabla.jTableFincas.getValueAt(this.vistaTabla.jTableFincas.getSelectedRow(),0);
            modeloFinca.borrarFinca(idFinca);
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
        this.vistaAdd.campoId.setEnabled(false);
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
        String idFinca = (String) this.vistaTabla.jTableFincas.getValueAt(fila, 0);
        Finca finca = modeloFinca.recuperarPorId(idFinca);
        ControladorExplotacion conExplotacion = new ControladorExplotacion(new JFExplotacion(),modeloExp, finca);
        this.vistaTabla.dispose();
    }

    private String modificarFinca(Finca f) {
        //Como mensaje de error la primera letra debe ser 'E' no puede cambiarse
        String res="Error:";
        String idAnt = this.vistaAdd.etiquetaId.getText();
        
        if(!modeloFinca.actualizarCampo(idAnt, "LOCALIDAD", f.getLocalidad())){
            res+="\nAl actualizar la localidad";
        }
        if(!modeloFinca.actualizarCampo(idAnt, "SUPERFICIE", f.getSuperficie()+"")){
            res+="\nAl actualizar la superficie";
        }
        if(!modeloFinca.actualizarCampo(idAnt, "F_COMPRA", Fechas.toString(f.getfCompra()))){
            res+="\nAl actualizar la fecha de compra";
        }
        if(isFFinSelected() && !modeloFinca.actualizarCampo(idAnt, "F_FIN", Fechas.toString(f.getfFin()))){
            //Si no esta FFin seleccionado no se ejecuta el metodo actualizarCampo
            res+="\nAl actualizar la fecha de fin";
        }
        if(!modeloFinca.actualizarCampo(idAnt, "ID_FINCA", f.getId())){
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
        
        this.vistaAdd.errFFin.setText(" ");
        this.vistaAdd.errFInicio.setText(" ");
        this.vistaAdd.errId.setText(" ");
        this.vistaAdd.errLocalidad.setText(" ");
        this.vistaAdd.errSuperficie.setText(" ");
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
        for (Finca f : listaFincas) {
            fila[0]=f.getId();
            fila[1]=f.getLocalidad();
            fila[2]=f.getSuperficie()+"";
            fila[3]=Fechas.toString(f.getfCompra());
            fila[4]=f.getListaEncargados().size()+"";//Num encargados
            //fila[5]="";//NUM TRACTORES
            fila[5]=modeloExp.contarPorFinca(f.getId())+"";//NUM EXPLOTACIONES
            modTabla.addRow(fila);
        }
        
    }
    
    public boolean validarDatosAdd(){
        boolean res=true;
        res = validarId(res);
        res = validarLocalidad(res);
        res = validarSuperficie(res);
        res = validarFInicio(res);
        res = validarFFin(res);
        return res;
    }

    private boolean validarFFin(boolean res) {
        if(this.isFFinSelected()){
            if(this.vistaAdd.campoFechaFin.equals("") || vistaAdd.campoFechaFin == null){
                res=false;
                vistaAdd.errFFin.setText("Escribe una fecha o desmarca la opción");
            }else if(Fechas.toLocalDate(vistaAdd.campoFechaFin.getText()) == null){
                res=false;
                vistaAdd.errFFin.setText("La fecha debe ser formato dd/mm/aaaa");
            }else{
                vistaAdd.errFFin.setText(" ");
            }
        }
        return res;
    }

    private boolean validarFInicio(boolean res) {
        if(vistaAdd.campoFechaC.getText().equals("") || vistaAdd.campoFechaC == null){
            res=false;
            vistaAdd.errFInicio.setText("La fecha es obligatoria");
        }else if(Fechas.toLocalDate(vistaAdd.campoFechaC.getText()) == null){
            res=false;
            vistaAdd.errFInicio.setText("La fecha debe ser formato dd/mm/aaaa");
        }else{
            vistaAdd.errFInicio.setText(" ");
        }
        return res;
    }

    private boolean validarSuperficie(boolean res) {
        if(vistaAdd.campoSuperficie.getText().equals("") || vistaAdd.campoSuperficie == null){
            res=false;
            vistaAdd.errSuperficie.setText("La superficie es obligatoria");
        }else{
            try{
                int num=Integer.parseInt(vistaAdd.campoSuperficie.getText());
                if(num<1){
                    res=false;
                }
            }catch(NumberFormatException e){
                res=false;
                vistaAdd.errSuperficie.setText("La superficie debe ser un entero");
            }
        }
        if(res){
            vistaAdd.errSuperficie.setText(" ");
        }
        return res;
    }

    private boolean validarLocalidad(boolean res) {
        if(vistaAdd.campoLocalidad.getText().equals("") || vistaAdd.campoLocalidad == null){
            res=false;
            vistaAdd.errLocalidad.setText("Localidad es obligatoria");
        }else if(vistaAdd.campoLocalidad.getText().length() >20){
            vistaAdd.errLocalidad.setText("Localidad debe tener menos de 20 caracteres");
        }else{
            String s = vistaAdd.campoLocalidad.getText();
            for (int i = 0; i < s.length(); i++) {
                if(!Character.isAlphabetic(s.charAt(i))){
                    if(s.charAt(i) != '-' || s.charAt(i) != ' '){
                        res=false;
                        vistaAdd.errLocalidad.setText("Localidad contiene caracteres no permitidos");
                        break;
                    }
                }
            }
        }
        if(res){
            vistaAdd.errLocalidad.setText(" ");
        }
        return res;
    }

    private boolean validarId(boolean res) {
        if(vistaAdd.campoId.getText().equals("") || vistaAdd.campoId == null){
            res=false;
            vistaAdd.errId.setText("El ID es obligatorio");
        }else if(vistaAdd.campoId.getText().length()>20){
            vistaAdd.errId.setText("ID debe tener menos de 20 caracteres");
            res=false;
        }else{
            String id = vistaAdd.campoId.getText();
            for (int i = 0; i < id.length(); i++) {
                //if(Character.)
            }
            vistaAdd.errId.setText(" ");
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

    public void focusGained(FocusEvent fe) {
        
    }

    public void focusLost(FocusEvent fe) {
        if(fe.getSource().equals(vistaAdd.campoId)){
            validarId(true);
        }else if(fe.getSource().equals(vistaAdd.campoFechaC)){
            validarFInicio(true);
        }else if(fe.getSource().equals(vistaAdd.campoFechaFin)){
            validarFFin(true);
        }else if(fe.getSource().equals(vistaAdd.campoLocalidad)){
            validarLocalidad(true);
        }else if(fe.getSource().equals(vistaAdd.campoSuperficie)){
            validarSuperficie(true);
        }
    }
}
