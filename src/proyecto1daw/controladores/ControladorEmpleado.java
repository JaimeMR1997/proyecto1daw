/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
import proyecto1daw.modelo.Trabajo;
import proyecto1daw.vistas.JFEmpleados;
import proyecto1daw.vistas.JFInicio;

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
        //Asociar listener a botones
        this.vistaTabla.botonVolver.addActionListener(this);
        this.vistaTabla.botonGestionarCuad.addActionListener(this);
        this.vistaTabla.botonAddCuad.addActionListener(this);
        this.vistaTabla.botonModCuad.addActionListener(this);
        this.vistaTabla.botonInfoCuad.addActionListener(this);
        this.vistaTabla.botonEliminarCuad.addActionListener(this);
        this.vistaTabla.botonGestionarEmp.addActionListener(this);
        this.vistaTabla.botonAddEmp.addActionListener(this);
        this.vistaTabla.botonModEmp.addActionListener(this);
        this.vistaTabla.botonInfoEmp.addActionListener(this);
        this.vistaTabla.botonEliminarEmp.addActionListener(this);
        this.vistaTabla.botonAddTrab.addActionListener(this);
        this.vistaTabla.botonModTrab.addActionListener(this);
        this.vistaTabla.botonEliminarTrab.addActionListener(this);
        //Modelos de tablas
        this.modTablaCuad = new DefaultTableModel();
        this.modTablaEmple = new DefaultTableModel();
        this.modTablaTrabajos = new DefaultTableModel();
        //Añadir columnas Cuadrilla
        this.modTablaCuad.addColumn("ID");
        this.modTablaCuad.addColumn("Encargado");
        this.modTablaCuad.addColumn("Fecha inicio");
        this.modTablaCuad.addColumn("Último trabajo");
        //Añadir columnas Empleados
        this.modTablaEmple.addColumn("DNI");
        this.modTablaEmple.addColumn("Nombre");
        this.modTablaEmple.addColumn("Apellidos");
        this.modTablaEmple.addColumn("Tlf");
        this.modTablaEmple.addColumn("F. Inicio");
        this.modTablaEmple.addColumn("F. Fin");
        //Añadir columnas Trabajos
        this.modTablaTrabajos.addColumn("Cuadrilla");
        this.modTablaTrabajos.addColumn("Fecha");
        this.modTablaTrabajos.addColumn("Horas");
        this.modTablaTrabajos.addColumn("Tipo");
        this.modTablaTrabajos.addColumn("Explotacion");
        //Añadir modelos a las tablas
        this.vistaTabla.jTableCuadrilla.setModel(modTablaCuad);
        this.vistaTabla.jTableEmple.setModel(modTablaEmple);
        this.vistaTabla.jTableTrab.setModel(modTablaTrabajos);
        //Cargar tabla Cuadrilla porque es la primera que se ve
        this.cargarTablaCuadrillas();
        //Mostrar vista
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaTabla.setVisible(true);
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
            }else if(boton.equals(this.vistaTabla.botonVolver)){                    //VOLVER A INICIO
                volver();
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
        if(isEmpleadoSelected()){
            listaEmple.addAll(modeloEmple.recuperarTrabajadores());
        }
        if(isTractoristaSelected()){
            listaEmple.addAll(modeloEmple.recuperarConductores());
        }
        if(isEncargadoSelected()){
            listaEmple.addAll(modeloEmple.recuperarEncargados());
        }
        
        String fila[] = new String[6];
        for (Trabajador t : listaEmple) {
            
            if(!isContFinSelected() && t.getfFin()!= null){
                if(t.getfFin().isBefore(LocalDate.now())){
                    continue;//Se salta los empleados con contrato finalizado
                }
            }
            fila[0]=t.getDni();//dni
            fila[1]=t.getNombre(); //nombre
            fila[2]=t.getApellidos();//apellidos
            fila[3]=t.getTlf();//tlf
            fila[4]=Fechas.toString(t.getfContratacion());//f inicio
            fila[5]=Fechas.toString(t.getfFin());//f fin
            this.modTablaEmple.addRow(fila);
        }
    }

    private void cargarTablaTrab(Cuadrilla c) {
        ArrayList<Trabajo> listaTrabajos = this.modeloCuad.recuperarTrabajos(c);
        String fila[] = new String[5];
        for (Trabajo trabajo : listaTrabajos) {
            fila[0]=trabajo.getIdCuadrilla(); //CUADRILLA
            fila[1]=Fechas.toString(trabajo.getFecha());//FECHA
            fila[2]=trabajo.getHoras()+"";//HORAS
            fila[3]=trabajo.getTipo();//TIPO
            fila[4]=trabajo.getIdExplotacion();//EXPLOTACION
            this.modTablaTrabajos.addRow(fila);
        }
    }

    private void cargarTablaTrab() {
        ArrayList<Trabajo> listaTrabajos = this.modeloCuad.recuperarTrabajos();
        String fila[] = new String[5];
        for (Trabajo trabajo : listaTrabajos) {
            fila[0]=trabajo.getIdCuadrilla(); //CUADRILLA
            fila[1]=Fechas.toString(trabajo.getFecha());//FECHA
            fila[2]=trabajo.getHoras()+"";//HORAS
            fila[3]=trabajo.getTipo();//TIPO
            fila[4]=trabajo.getIdExplotacion();//EXPLOTACION
            this.modTablaTrabajos.addRow(fila);
        }
    }
    
    private void abrirAddCuadrilla() {
        ControladorAddCuad contAddCuad = new ControladorAddCuad(false, vistaTabla,modeloCuad);
    }

    private void abrirAddTrabajo() {
        
    }

    private void abrirModCuadrilla() {
        
    }

    private void eliminarCuadrilla(int confirmacion) {
        
    }

    private void abrirVentanaAscenso() {
        
    }

    private void abrirAddEmple() {
        
    }

    private void eliminarTrabajador(int confirmacion) {
        
    }

    private void abrirModTrabajo() {
        
    }

    private void eliminarTrabajo(int confirmacion) {
        
    }

    private void abrirModEmple() {
        
    }

    private boolean isEmpleadoSelected() {
        return this.vistaTabla.jCheckBoxEmple.isSelected();
    }

    private boolean isTractoristaSelected() {
        return this.vistaTabla.jCheckBoxTract.isSelected();
    }

    private boolean isEncargadoSelected() {
        return this.vistaTabla.jCheckBoxEnc.isSelected();
    }

    private boolean isContFinSelected() {
        return this.vistaTabla.jCheckBoxFinalizado.isSelected();
    }

    private void volver() {
        ControladorInicio contInicio = new ControladorInicio(new JFInicio());
    }
}
