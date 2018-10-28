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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.accesobd.FincaDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.vistas.JFFincaAdd;

/**
 *
 * @author Jaime-Torre
 */
public class ControladorAddFinca implements ActionListener,FocusListener,KeyListener {
    private ControladorFinca contFinca;
    private JFFincaAdd vistaAdd;
    private FincaDAO modeloFinca;
    private Finca finca;

    public ControladorAddFinca(ControladorFinca contFinca, FincaDAO modeloFinca) {
        this.contFinca = contFinca;
        this.vistaAdd = new JFFincaAdd();
        this.modeloFinca = modeloFinca;
        
        //Añadir listener
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jCheckBoxFFin.addActionListener(this);
        //Añadir focus listener a campos
        this.vistaAdd.campoId.addFocusListener(this);
        this.vistaAdd.campoFechaC.addFocusListener(this);
        this.vistaAdd.campoFechaFin.addFocusListener(this);
        this.vistaAdd.campoLocalidad.addFocusListener(this);
        this.vistaAdd.campoSuperficie.addFocusListener(this);
        //Añadir key listener
        this.vistaAdd.addKeyListener(this);
        this.vistaAdd.setFocusable(true);
        //this.vistaAdd.setFocusTraversalKeysEnabled(false);
        this.vistaAdd.campoId.addKeyListener(this);
        this.vistaAdd.campoFechaC.addKeyListener(this);
        this.vistaAdd.campoFechaFin.addKeyListener(this);
        this.vistaAdd.campoLocalidad.addKeyListener(this);
        this.vistaAdd.campoSuperficie.addKeyListener(this);
        this.vistaAdd.campoAlias.addKeyListener(this);
        
        this.vistaAdd.botonAceptar.setText("Aceptar");
        this.vistaAdd.setLocationRelativeTo(null);
        this.vistaAdd.campoFechaFin.setEnabled(false);
        vistaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaAdd.setVisible(true);
    }

    
    
    public ControladorAddFinca(ControladorFinca contFinca, FincaDAO modeloFinca, Finca finca) {
        this(contFinca, modeloFinca);
        this.finca = finca;
        this.vistaAdd.botonAceptar.setText("Modificar");
        //Rellena el campo del id en un campo oculto por si se cambiara
        this.vistaAdd.etiquetaId.setText(finca.getId());
        //Rellena los campos del formulñario con los de la finca
        //Que previamente se ha recibido de la BD actualizada
        this.vistaAdd.campoAlias.setText(finca.getAlias());
        this.vistaAdd.campoFechaC.setText(Fechas.toString(finca.getfCompra()));
        this.vistaAdd.campoFechaFin.setText(Fechas.toString(finca.getfFin()));
        this.vistaAdd.campoId.setText(finca.getId());
        this.vistaAdd.campoLocalidad.setText(finca.getLocalidad());
        this.vistaAdd.campoSuperficie.setText(finca.getSuperficie()+"");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaAdd.botonAceptar)){                 //ACEPTAR_AÑADIR
            String nombreBoton=((JButton)ae.getSource()).getText();
            if(this.validarDatos()){
                Finca f = getFincaFromFormulario();
                if(nombreBoton.equalsIgnoreCase("Aceptar")){                   //NUEVA FINCA
                    if(modeloFinca.addFinca(f)){
                        JOptionPane.showMessageDialog(vistaAdd, "Finca añadida correctamente");
                        this.contFinca.actualizarTabla();
                        this.vistaAdd.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al añadir la finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }else if(nombreBoton.equalsIgnoreCase("Modificar")){           //MODIFICAR FINCA                    
                    String s = modificarFinca(f);
                    if(s.charAt(0) == 'E'){//Se ha producido error
                        JOptionPane.showMessageDialog(vistaAdd, s, "Error", JOptionPane.ERROR_MESSAGE);
                    }else{//Todo correcto
                        JOptionPane.showMessageDialog(vistaAdd, s);
                        this.vistaAdd.dispose();
                        this.contFinca.actualizarTabla();
                    }
                    
                }
            }
            
        }else if(ae.getSource().equals(vistaAdd.botonCancelar)){                 //CANCELAR_AÑADIR
            this.vistaAdd.dispose();
            
        }else if(ae.getSource().equals(this.vistaAdd.jCheckBoxFFin)){           //CHECBOX FECHA FIN
            if(this.isFFinSelected()){
                this.vistaAdd.campoFechaFin.setEnabled(true);
            }else{
                this.vistaAdd.campoFechaFin.setEnabled(false);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent fe) {
        
    }

    /**
     *
     * @param fe objeto generado al perder el foco un campo de JFFincaAdd
     * lo uqe hace es validar el campo que ha generado el objeto FocusEvent
     */
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

    /**
     * @return Devuelve un objeto Finca recuperador a aprtir de los campos de
     * la ventana JFFincaAdd
     * @see JFFincaAdd
     */
    private Finca getFincaFromFormulario(){
        //Recupera campos
        String id = this.vistaAdd.campoId.getText();
        String localidad = this.vistaAdd.campoLocalidad.getText();
        int superficie = Integer.parseInt(this.vistaAdd.campoSuperficie.getText());
        LocalDate fCreacion = Fechas.toLocalDate(this.vistaAdd.campoFechaC.getText());
        LocalDate fFin = Fechas.toLocalDate(this.vistaAdd.campoFechaFin.getText());
        String alias = this.vistaAdd.campoAlias.getText();
        Finca f = new Finca(id, localidad, superficie, fCreacion, fFin, alias);
        return f;
    }
    
    /**
     *
     * @return true si todos los campos de JFFincaAdd son correctos y false si 
     * alguno no lo es, está compuesto por submétodos que comprueban cada campo 
     * y si es incorrecto activar un jLabel con un mensaje de error
     */
    public boolean validarDatos(){
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
    
    /**
     *
     * @return true si el Checkbox de Fecha Fin de JFFincaAdd está activado
     */
    public boolean isFFinSelected(){
        boolean res=false;
        if(this.vistaAdd.jCheckBoxFFin.isSelected()){
            res=true;
        }
        return res;
    }
    
    
        protected String modificarFinca(Finca f) {
        //Como mensaje de error la primera letra debe ser 'E' no puede cambiarse
        String res="Error:";
        String idAnt = this.vistaAdd.etiquetaId.getText();
        
        if(!modeloFinca.actualizarCampo(idAnt, "LOCALIDAD", f.getLocalidad())){
            res+="\nAl actualizar la localidad";
        }
        if(!modeloFinca.actualizarCampo(idAnt, "SUPERFICIE", f.getSuperficie()+"")){
            res+="\nAl actualizar la superficie";
        }
        
        Configuracion config = new Configuracion();
        String fCompra;
        String fFin;
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            fCompra = Fechas.toStringMariaDb(f.getfCompra());
            fFin = Fechas.toStringMariaDb(f.getfFin());
        }else{
            fCompra = Fechas.toString(f.getfCompra());
            fFin = Fechas.toString(f.getfFin());
        }
        
        if(!modeloFinca.actualizarCampo(idAnt, "F_COMPRA", fCompra)){
            res+="\nAl actualizar la fecha de compra";
        }
        if(isFFinSelected() && !modeloFinca.actualizarCampo(idAnt, "F_FIN", fFin)){
            //Si no esta FFin seleccionado no se ejecuta el metodo actualizarCampo
            res+="\nAl actualizar la fecha de fin";
        }
        if(!modeloFinca.actualizarCampo(idAnt, "ALIAS", f.getAlias())){
            res+="\nAl actualizar el alias";
        }
        //Si el id ha cambiado(son distintos) lo actualiza
        if(!idAnt.equalsIgnoreCase(f.getId()) && !modeloFinca.actualizarCampo(idAnt, "ID_FINCA", f.getId())){
            res+="\nAl actualizar el ID de la finca";
        }
        
        if(res.equals("Error:")){
            res="La finca se ha actualizado correctamente.";
        }
        return res;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    /**
     * 
     * @param ke Tecla pulsada. Si es enter se envia el formulario
     */
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
