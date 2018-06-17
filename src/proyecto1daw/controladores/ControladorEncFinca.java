/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.modelo.TrabajadorDAO;
import proyecto1daw.vistas.JFEncFinca;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;

/**
 *
 * @author Jaime
 */
public class ControladorEncFinca implements ActionListener{
    private ControladorFinca contFinca;
    private JFEncFinca vistaEnc;
    private FincaDAO modeloFinca;
    private ExplotacionDAO modeloExp;
    private TrabajadorDAO modeloTrab;
    private DefaultListModel modPosibles;
    private DefaultListModel modActuales;
    private ArrayList<Encargado> listaParaAniadir;
    private ArrayList<Encargado> listaParaQuitar;
    private Finca finca;

    /**
     *
     * @param contFinca controlador qeu llama a este
     * @param modeloFinca modelo relacionado con el acceso a datos de Finca
     * @param modeloExp modelo relacionado con el acceso a datos de Explotacion
     * @param finca finca a la que asignar los encargados
     */
    public ControladorEncFinca(ControladorFinca contFinca, FincaDAO modeloFinca, ExplotacionDAO modeloExp,Finca finca) {
        this.contFinca = contFinca;
        this.vistaEnc = new JFEncFinca();
        this.modeloFinca = modeloFinca;
        this.modeloExp = modeloExp;
        this.modeloTrab = new TrabajadorDAO();
        this.finca = finca;
        
        this.listaParaAniadir = new ArrayList<Encargado>();
        this.listaParaQuitar = new ArrayList<Encargado>();
        
        //Asociar action listeners a botones
        this.vistaEnc.botonAbajo.addActionListener(this);
        this.vistaEnc.botonArriba.addActionListener(this);
        this.vistaEnc.botonAceptar.addActionListener(this);
        this.vistaEnc.botonCancelar.addActionListener(this);
        //Modelos listas y asociacion
        this.modPosibles = new DefaultListModel<Encargado>();
        this.modActuales = new DefaultListModel<Encargado>();
        this.vistaEnc.jListPosibles.setModel(modPosibles);
        this.vistaEnc.jListActuales.setModel(modActuales);
        //Cargar listas
        cargarListaPosibles();
        cargarListaActuales();
        //Mostrar vista
        this.vistaEnc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaEnc.setLocationRelativeTo(null);
        this.vistaEnc.setVisible(true);
    }

    /**
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaEnc.botonAbajo)){                         //ABAJO
            bajarEncargado();
            
        }else if(ae.getSource().equals(vistaEnc.botonArriba)){                  //ARRIBA
            subirEncargado();
            
        }else if(ae.getSource().equals(vistaEnc.botonAceptar)){                 //ACEPTAR
            if(asignarEncargados()){
                JOptionPane.showMessageDialog(vistaEnc,"Encargados reasignados correctamente.");
                contFinca.actualizarTabla();
                this.vistaEnc.dispose();
            }else{
                JOptionPane.showMessageDialog(vistaEnc,"Error al reasignar los encargados", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(ae.getSource().equals(vistaEnc.botonCancelar)){                //CANCELAR
            if(listaParaAniadir.size() == 0 && listaParaQuitar.size() == 0){
                //No se ha modificado nada, se sale sin preguntar
                this.vistaEnc.dispose();
            }else{
                //Se ha modificado algo, advertir al usuario
                int n=JOptionPane.showConfirmDialog(vistaEnc, "¿Quieres salir y descartar los cambios?");
                if(n == JOptionPane.YES_OPTION){
                    this.vistaEnc.dispose();
                }
            }
        }
    }

    private void bajarEncargado() {
        if(this.vistaEnc.jListPosibles.getSelectedIndex() != -1){
            Object o = vistaEnc.jListPosibles.getSelectedValue();
            Encargado enc = (Encargado) o;

            if(modActuales.contains(enc)){
                JOptionPane.showMessageDialog(vistaEnc, "Este encargado ya está asignado a esta finca.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                modPosibles.removeElement(o);
                modActuales.addElement(o);

                if(listaParaQuitar.contains(enc)){//Si estaba en "para quitar"
                //es porque ya estaba asignado y lo hemos movido para borrar y
                //ahora de vuelta a asignado
                    listaParaQuitar.remove(enc);
                }else{//Era un candidato que hemos asignado nuevo
                    listaParaAniadir.add(enc);    
                }
            }
        }
        
    }

    private void subirEncargado() {
        if(this.vistaEnc.jListActuales.getSelectedIndex() != -1){
            Object o = vistaEnc.jListActuales.getSelectedValue();
            Encargado enc = (Encargado) o;

            if(modPosibles.contains(enc)){
                JOptionPane.showMessageDialog(vistaEnc, "Este encargado ya está sin asignar.(A partir de hoy)", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                modActuales.removeElement(o);
                modPosibles.addElement(o);

                if(listaParaAniadir.contains(enc)){//Si estaba en "para añadir" 
                    //es porque era un candidato que hemos movido para asignar
                    //y ahora movemos de vuelta a posibles candidatos
                    listaParaAniadir.remove(enc);
                }else{//Era un encargado asignado que vamos a quitar
                    listaParaQuitar.add(enc);
                }
            }
        }
    }

    private boolean asignarEncargados() {
        boolean res = true;
        if(listaParaQuitar.size() > 0){
            for (Encargado enc : listaParaQuitar) {
                if(!modeloTrab.finAsignacionFinca(enc.getDni(),finca.getId())){
                    res=false;
                }
            }
        }
        
        if(listaParaAniadir.size() > 0){
            for (Encargado enc : listaParaAniadir) {
                if(!modeloTrab.asignarFinca(enc.getDni(), finca.getId())){
                    res=false;
                }
            }
        }
        
        return res;
    }

    private void cargarListaPosibles() {
        ArrayList<Encargado> listaEnc = modeloTrab.recuperarEncargadosLibres();
        for (Encargado enc : listaEnc) {
            this.modPosibles.addElement(enc);
        }
    }

    private void cargarListaActuales() {
        ArrayList<Encargado> listaEnc = modeloFinca.recuperarEncargadosFinca(finca.getId());
        for (Encargado enc : listaEnc) {
            this.modActuales.addElement(enc);
        }
    }
    
    
}

