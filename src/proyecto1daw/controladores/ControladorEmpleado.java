/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Cuadrilla;
import proyecto1daw.modelo.CuadrillaDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.TrabajadorDAO;
import proyecto1daw.vistas.JFEmpleados;

/**
 *
 * @author Jaime
 */
public class ControladorEmpleado implements ActionListener{
    private JFEmpleados vistaTabla;
    private CuadrillaDAO modeloCuad;
    private TrabajadorDAO modeloEmple;
    private DefaultTableModel modTablaCuad;
    private DefaultTableModel modTablaEmple;
    private DefaultTableModel modTablaTrabajos;

    public ControladorEmpleado(JFEmpleados vistaTabla) {
        this.vistaTabla = vistaTabla;
        this.modeloCuad = new CuadrillaDAO();
        this.modeloEmple = new TrabajadorDAO();
        //Modelos de tablas
        this.modTablaCuad = new DefaultTableModel();
        this.modTablaEmple = new DefaultTableModel();
        this.modTablaTrabajos = new DefaultTableModel();
        //Añadir columnas Cuadrilla
        this.modTablaCuad.addColumn("");
        this.modTablaCuad.addColumn("");
        this.modTablaCuad.addColumn("");
        this.modTablaCuad.addColumn("");
        //Añadir columnas Empleados
        this.modTablaEmple.addColumn("");
        this.modTablaEmple.addColumn("");
        this.modTablaEmple.addColumn("");
        this.modTablaEmple.addColumn("");
        //Añadir columnas Trabajos
        this.modTablaTrabajos.addColumn("");
        this.modTablaTrabajos.addColumn("");
        this.modTablaTrabajos.addColumn("");
        this.modTablaTrabajos.addColumn("");
        //Añadir modelos a las tablas
        this.vistaTabla.jTableCuadrilla.setModel(modTablaCuad);
        this.vistaTabla.jTableEmple.setModel(modTablaEmple);
        this.vistaTabla.jTableTrab.setModel(modTablaTrabajos);
        //Cargar tabla Cuadrilla porque es la primera que se ve
        this.cargarTablaCuadrillas();
        //Mostrar vista
        
        //Cargar otras 2 tablas
        this.cargarTablaEmple();
        this.cargarTablaTrab();
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() instanceof JButton){
            JButton boton = (JButton) ae.getSource();
            if(boton.equals(this.vistaTabla.botonGestionarCuad)){                   //GESTIONAR CUADRILLA
                abrirAddTrabajo();
            }else if(boton.equals(this.vistaTabla.botonAddCuad)){                   //AÑADIR CUADRILLA
                abrirAddCuadrilla();
            }else if(boton.equals(this.vistaTabla.botonModCuad)){                   //MODIFICAR CUACRILLA
                if(getSelFilaTrab()!= -1){
                    abrirModCuadrilla();   
                }
            }else if(boton.equals(this.vistaTabla.botonInfoCuad)){                  //INFORMACION CUADRILLA
                System.out.println("EN DESARROLLO");
            }else if(boton.equals(this.vistaTabla.botonEliminarCuad)){              //ELIMINAR CUADRILLA
                if(getSelFilaCuad() != -1){
                    int confirmacion= JOptionPane.showConfirmDialog(vistaTabla, 
                            "¿Estás seguro de eliminar esta Cuadrilla y todos sus trabajos?");
                    eliminarCuadrilla(confirmacion);
                }else{
                    
                }
            }else if(boton.equals(this.vistaTabla.botonGestionarEmp)){              //GESTIONAR EMPLEADO
                abrirVentanaAscenso();
            }else if(boton.equals(this.vistaTabla.botonAddEmp)){                    //AÑADIR EMPLEADO
                abrirAddEmple();
            }else if(boton.equals(this.vistaTabla.botonModEmp)){                    //MODIFICAR EMPLEADO
                if(getSelFilaTrab()!= -1){
                    abrirModEmple();   
                }
            }else if(boton.equals(this.vistaTabla.botonInfoEmp)){                   //INFORMACION EMPLEADO
                System.out.println("EN DESARROLLO");
            }else if(boton.equals(this.vistaTabla.botonEliminarEmp)){               //ELIMINAR EMPLEADO
                if(getSelFilaEmple()!= -1){
                    int confirmacion= JOptionPane.showConfirmDialog(vistaTabla, 
                            "¿Estás seguro de eliminar este trabajador?");
                    eliminarTrabajador(confirmacion);
                }
            }else if(boton.equals(this.vistaTabla.botonAddTrab)){                   //AÑADIR TRABAJO
                abrirAddTrabajo();
            }else if(boton.equals(this.vistaTabla.botonModTrab)){                   //MODIFICAR TRABAJO
                if(getSelFilaTrab()!= -1){
                    abrirModTrabajo();   
                }
            }else if(boton.equals(this.vistaTabla.botonEliminarTrab)){              //ELIMINAR TRABAJO
                if(getSelFilaTrab()!= -1){
                    int confirmacion= JOptionPane.showConfirmDialog(vistaTabla, 
                            "¿Estás seguro de eliminar este trabajo?");
                    eliminarTrabajo(confirmacion);
                }
            }
        }else if(ae.getSource() instanceof  JCheckBox){
            JCheckBox checkbox = (JCheckBox) ae.getSource();
            if(checkbox.equals(this.vistaTabla.jCheckBoxEmple)){                    //JCHECHBOX EMPLEADO
                
            }else if(checkbox.equals(this.vistaTabla.jCheckBoxTract)){              //JCHECHBOX TRACTORISTA
                
            }else if(checkbox.equals(this.vistaTabla.jCheckBoxEnc)){                //JCHECHBOX ENCARGADO
                
            }else if(checkbox.equals(this.vistaTabla.jCheckBoxFinalizado)){         //JCHECHBOX CONT FINALIZADOS
                
            }
        }
    }

    private int getSelFilaCuad() {
        return this.vistaTabla.jTableCuadrilla.getSelectedRow();
    }
    
    private int getSelFilaEmple() {
        return this.vistaTabla.jTableEmple.getSelectedRow();
    }
    
    private int getSelFilaTrab() {
        return this.vistaTabla.jTableTrab.getSelectedRow();
    }
    
    private void cargarTablaCuadrillas() {
        ArrayList<Cuadrilla> listaCuad = this.modeloCuad.recuperarTodas();
        String fila[] = new String[4];
        for (Cuadrilla c : listaCuad) {
            fila[0]=c.getId();
            fila[1]=""; //Encargado
            fila[2]=Fechas.toString(c.getfInicio());
            fila[3]="";//Ultimo trabajo
            this.modTablaCuad.addRow(fila);
        }
    }

    private void cargarTablaEmple() {
        ArrayList<Trabajador> listaEmple = new ArrayList<>();
        if(isContFinSelected()){
            if(isEmpleadoSelected()){
            
            }
            if(isTractoristaSelected()){

            }
            if(isEncargadoSelected()){

            }
        }else{
            if(isEmpleadoSelected()){
            
            }
            if(isTractoristaSelected()){

            }
            if(isEncargadoSelected()){

            }
        }
        
        String fila[] = new String[6];
        for (Trabajador t : listaEmple) {
            fila[0]=t.getDni();//dni
            fila[1]=t.getNombre(); //nombre
            fila[2]=t.getApellidos();//apellidos
            fila[3]=t.getTlf();//tlf
            fila[4]=Fechas.toString(t.getfContratacion());//f inicio
            fila[5]=Fechas.toString(t.getfFin());//f fin
            this.modTablaCuad.addRow(fila);
        }
    }

    private void cargarTablaTrab() {
        
    }

    private void abrirAddCuadrilla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void abrirAddTrabajo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void abrirModCuadrilla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void eliminarCuadrilla(int confirmacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void abrirVentanaAscenso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void abrirAddEmple() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void eliminarTrabajador(int confirmacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void abrirModTrabajo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void eliminarTrabajo(int confirmacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void abrirModEmple() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isEmpleadoSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isTractoristaSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isEncargadoSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isContFinSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
