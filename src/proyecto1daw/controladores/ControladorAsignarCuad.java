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
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.Cuadrilla;
import proyecto1daw.modelo.CuadrillaDAO;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.TrabajadorDAO;
import proyecto1daw.vistas.JFAsignarCuad;

/**
 *
 * @author Jaime
 */
public class ControladorAsignarCuad implements ActionListener,MouseListener{
    private JFAsignarCuad vista;
    private ControladorEmpleado contEmple;
    private TrabajadorDAO modeloTrab;
    private CuadrillaDAO modeloCuad;
    private DefaultListModel modPosibles;
    private DefaultListModel modActuales;
    private ArrayList<Trabajador> listaParaAniadir;
    private ArrayList<Trabajador> listaParaQuitar;
    private Cuadrilla cuad;

    /**
     *
     * @param contEmple controlador que llama a este
     * @param modeloTrab modelo relacionado con el acceso a datos de Trabajador,Conductor,Encargado
     * @param modeloCuad modelo relacionado con el acceso a datos de Cuadrilla
     * @param cuad cuadrilla a la que se asignaran los empleados
     */
    public ControladorAsignarCuad(ControladorEmpleado contEmple, TrabajadorDAO modeloTrab, CuadrillaDAO modeloCuad,Cuadrilla cuad) {
        this.contEmple = contEmple;
        this.modeloTrab = modeloTrab;
        this.modeloCuad = modeloCuad;
        this.cuad = cuad;
        this.vista = new JFAsignarCuad();
        this.modActuales = new DefaultListModel<Trabajador>();
        this.modPosibles = new DefaultListModel<Trabajador>();
        this.listaParaAniadir = new ArrayList<Trabajador>();
        this.listaParaQuitar = new ArrayList<Trabajador>();        
        //Asociar modelos lista a listas
        this.vista.jListActuales.setModel(modActuales);
        this.vista.jListPosibles.setModel(modPosibles);
        //Cargar listas
        this.cargarListaActuales();
        this.cargarListaPosibles();
        //Asociar action listeners
        this.vista.botonAbajo.addActionListener(this);
        this.vista.botonArriba.addActionListener(this);
        this.vista.botonAceptar.addActionListener(this);
        this.vista.botonCancelar.addActionListener(this);
        //Asociar mouse listeners
        this.vista.jListPosibles.addMouseListener(this);
        this.vista.jListActuales.addMouseListener(this);
        //Mostrar vista
        this.vista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
    }

    /**
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vista.botonAbajo)){                         //ABAJO
            if(vista.jListPosibles.getSelectedIndex() != -1){
                bajarTrabajador();
            }
            
        }else if(ae.getSource().equals(vista.botonArriba)){                  //ARRIBA
            if(vista.jListActuales.getSelectedIndex() != -1){
                subirTrabajador();
            }
            
        }else if(ae.getSource().equals(vista.botonAceptar)){                 //ACEPTAR
            if(asignarTrabajadores()){
                JOptionPane.showMessageDialog(vista,"Empleados reasignados correctamente.");
                this.vista.dispose();
            }else{
                JOptionPane.showMessageDialog(vista,"Error al reasignar los Empleados", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(ae.getSource().equals(vista.botonCancelar)){                //CANCELAR
            if(listaParaAniadir.size() == 0 && listaParaQuitar.size() == 0){
                //No se ha modificado nada, se sale sin preguntar
                this.vista.dispose();
            }else{
                //Se ha modificado algo, advertir al usuario
                int n=JOptionPane.showConfirmDialog(vista, "¿Quieres salir y descartar los cambios?");
                if(n == JOptionPane.YES_OPTION){
                    this.vista.dispose();
                }
            }
        }
    }

     private void bajarTrabajador() {
        Object o = vista.jListPosibles.getSelectedValue();
        Trabajador emple = (Trabajador) o;
        
        if(modActuales.contains(emple)){
            JOptionPane.showMessageDialog(vista, "Este empleado ya está asignado a esta cuadrilla.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }else{
            modPosibles.removeElement(o);
            modActuales.addElement(o);

            if(listaParaQuitar.contains(emple)){//Si estaba en "para quitar"
            //es porque ya estaba asignado y lo hemos movido para borrar y
            //ahora de vuelta a asignado
                listaParaQuitar.remove(emple);
            }else{//Era un candidato que hemos asignado nuevo
                listaParaAniadir.add(emple);    
            }
        }
        
    }

    private void subirTrabajador() {
        Object o = vista.jListActuales.getSelectedValue();
        Trabajador emple = (Trabajador) o;
        
        if(modPosibles.contains(emple)){
            JOptionPane.showMessageDialog(vista, "Este empleado ya está sin asignar.(A partir de hoy)", "ERROR", JOptionPane.ERROR_MESSAGE);
        }else{
            modActuales.removeElement(o);
            modPosibles.addElement(o);
            
            if(listaParaAniadir.contains(emple)){//Si estaba en "para añadir" 
                //es porque era un candidato que hemos movido para asignar
                //y ahora movemos de vuelta a posibles candidatos
                listaParaAniadir.remove(emple);
            }else{//Era un encargado asignado que vamos a quitar
                listaParaQuitar.add(emple);
            }
        }
    }

    private boolean asignarTrabajadores() {
        boolean res = true;
        if(listaParaQuitar.size() != 0){
            for (Trabajador emple : listaParaQuitar) {
                if(!modeloTrab.finAsigCuadrilla(emple.getDni(),cuad.getId())){
                    res=false;
                }
            }
        }
        
        if(listaParaAniadir.size() != 0){
            for (Trabajador emple : listaParaAniadir) {
                if(!modeloTrab.asignarCuad(emple.getDni(), cuad.getId())){
                    res=false;
                }
            }
        }
        
        return res;
    }

    private void cargarListaPosibles() {
        ArrayList<Trabajador> listaEmple = modeloTrab.recuperarTrabajadoresLibres();
        for (Trabajador emple : listaEmple) {
            this.modPosibles.addElement(emple);
        }
    }

    private void cargarListaActuales() {
        ArrayList<Trabajador> listaEmple = modeloCuad.recuperarTrabajadores(cuad.getId());
        for (Trabajador emple : listaEmple) {
            this.modActuales.addElement(emple);
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
        if(me.getClickCount() == 2){//Doble click
            if(me.getSource().equals(vista.jListPosibles) && vista.jListPosibles.getSelectedIndex() != -1){
                bajarTrabajador();
            }else if(me.getSource().equals(vista.jListActuales) && vista.jListActuales.getSelectedIndex() != -1){
                subirTrabajador();
            }
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
    
}
