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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Conductor;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Cuadrilla;
import proyecto1daw.modelo.accesobd.CuadrillaDAO;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.accesobd.TrabajadorDAO;
import proyecto1daw.modelo.Trabajo;
import proyecto1daw.modelo.accesobd.mysql.CuadrillaMysql;
import proyecto1daw.modelo.accesobd.mysql.TrabajadorMysql;
import proyecto1daw.modelo.accesobd.sqlite.CuadrillaSqlite;
import proyecto1daw.modelo.accesobd.sqlite.TrabajadorSqlite;
import proyecto1daw.vistas.JFEmpleados;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author Jaime
 */
public class ControladorEmpleado implements ActionListener,MouseListener{
    private JFEmpleados vistaTabla;
    private CuadrillaDAO modeloCuad;
    private TrabajadorDAO modeloEmple;
    private DefaultTableModel modTablaCuad;
    private DefaultTableModel modTablaEmple;
    private DefaultTableModel modTablaTrabajos;

    /**
     *
     * @param vistaTabla Ventana que tiene las tablas de cuadrilla,empleados y trabajos
     */
    public ControladorEmpleado(JFEmpleados vistaTabla) {
        this.vistaTabla = vistaTabla;
        
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            this.modeloEmple = new TrabajadorMysql();
            this.modeloCuad = new CuadrillaMysql();
        }else{
            this.modeloEmple = new TrabajadorSqlite();
            this.modeloCuad = new CuadrillaSqlite();
        }
        
        
        //Asociar listener a botones
        this.vistaTabla.botonVolver.addActionListener(this);
        this.vistaTabla.botonGestionarCuad.addActionListener(this);
        this.vistaTabla.botonAddCuad.addActionListener(this);
        this.vistaTabla.botonModCuad.addActionListener(this);
        this.vistaTabla.botonAsignarCuad.addActionListener(this);
        this.vistaTabla.botonEliminarCuad.addActionListener(this);
        this.vistaTabla.botonGestionarEmp.addActionListener(this);
        this.vistaTabla.botonAddEmp.addActionListener(this);
        this.vistaTabla.botonModEmp.addActionListener(this);
        this.vistaTabla.botonBuscarEmp.addActionListener(this);
        this.vistaTabla.botonEliminarEmp.addActionListener(this);
        this.vistaTabla.botonAddTrab.addActionListener(this);
        this.vistaTabla.botonModTrab.addActionListener(this);
        this.vistaTabla.botonEliminarTrab.addActionListener(this);
        //Añadir listenes a jcheckbox
        this.vistaTabla.jCheckBoxEmple.addActionListener(this);
        this.vistaTabla.jCheckBoxTract.addActionListener(this);
        this.vistaTabla.jCheckBoxEnc.addActionListener(this);
        this.vistaTabla.jCheckBoxFinalizado.addActionListener(this);
        //Añadir mouse listeners a tablas
        this.vistaTabla.jTableCuadrilla.addMouseListener(this);
        this.vistaTabla.jTableEmple.addMouseListener(this);
        this.vistaTabla.jTableTrab.addMouseListener(this);
        //Modelos de tablas
        this.modTablaCuad = new DefaultTableModel(){   //Para no poder editar las celdas de la tabla
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        this.modTablaEmple = new DefaultTableModel(){   //Para no poder editar las celdas de la tabla
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        this.modTablaTrabajos = new DefaultTableModel(){   //Para no poder editar las celdas de la tabla
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        //Para que el usuario no pueda reordenar las columnas e las tablas
        this.vistaTabla.jTableCuadrilla.getTableHeader().setReorderingAllowed(false);
        this.vistaTabla.jTableEmple.getTableHeader().setReorderingAllowed(false);
        this.vistaTabla.jTableTrab.getTableHeader().setReorderingAllowed(false);
        
        //Añadir columnas Cuadrilla
        this.modTablaCuad.addColumn("ID");
        this.modTablaCuad.addColumn("Encargado");
        this.modTablaCuad.addColumn("Fecha inicio");
        this.modTablaCuad.addColumn("Último trabajo");
        //Añadir columnas Empleados
        this.modTablaEmple.addColumn("Tipo");
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

    /**
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() instanceof JButton){
            JButton boton = (JButton) ae.getSource();
            if(boton.equals(this.vistaTabla.botonGestionarCuad)){                   //GESTIONAR CUADRILLA
                if(getSelFilaCuad() != -1){
                    abrirAddTrabajo();
                }else{
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una cuadrilla", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }
            }else if(boton.equals(this.vistaTabla.botonAddCuad)){                   //AÑADIR CUADRILLA
                abrirAddCuadrilla();
            }else if(boton.equals(this.vistaTabla.botonModCuad)){                   //MODIFICAR CUADRILLA
                if(getSelFilaCuad()!= -1){
                    abrirModCuadrilla();   
                }else{
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una cuadrilla", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }
            }else if(boton.equals(this.vistaTabla.botonAsignarCuad)){                 //ASIGNAR EMPLE CUADRILLA
                if(getSelFilaCuad()!= -1){
                    abrirGestionarCuadrilla();   
                }else{
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una cuadrilla", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }
            }else if(boton.equals(this.vistaTabla.botonEliminarCuad)){              //ELIMINAR CUADRILLA
                if(getSelFilaCuad() != -1){
                    int confirmacion= JOptionPane.showConfirmDialog(vistaTabla, 
                            "¿Estás seguro de eliminar esta Cuadrilla y todos sus trabajos?");
                    eliminarCuadrilla(confirmacion);
                }
                
            }else if(boton.equals(this.vistaTabla.botonGestionarEmp)){              //GESTIONAR EMPLEADO
                abrirVentanaAscenso();
            }else if(boton.equals(this.vistaTabla.botonAddEmp)){                    //AÑADIR EMPLEADO
                abrirAddEmple();
            }else if(boton.equals(this.vistaTabla.botonModEmp)){                    //MODIFICAR EMPLEADO
                if(getSelFilaEmple()!= -1){
                    abrirModEmple();   
                }else{
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar un empleado", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }
            }else if(boton.equals(this.vistaTabla.botonBuscarEmp)){                   //INFORMACION EMPLEADO
                System.out.println("EN DESARROLLO");
            }else if(boton.equals(this.vistaTabla.botonEliminarEmp)){               //ELIMINAR EMPLEADO
                if(getSelFilaEmple()!= -1){
                    int confirmacion= JOptionPane.showConfirmDialog(vistaTabla, 
                            "¿Estás seguro de eliminar este trabajador?");
                    if(confirmacion == JOptionPane.YES_OPTION ){
                        if(eliminarTrabajador()){
                            JOptionPane.showMessageDialog(vistaTabla, "Se ha eliminado el trabajador correctamente");
                        }else{
                            JOptionPane.showMessageDialog(vistaTabla, "Ha habido un error al eliminar al trabajador","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    this.cargarTablaEmple();
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
             //JCHECHBOX EMPLEADOS,ETC
            cargarTablaEmple();
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
    
    /**
     *
     */
    public void cargarTablaCuadrillas() {
        modTablaCuad.setRowCount(0);
        ArrayList<Cuadrilla> listaCuad = this.modeloCuad.recuperarTodas();
        String fila[] = new String[4];
        Trabajo t = null;
        for (Cuadrilla c : listaCuad) {
            t = modeloCuad.recuperarUltTrabajo(c);
            fila[0]=c.getId();
            fila[1]=modeloCuad.getDniEncargado(c); //Encargado
            fila[2]=Fechas.toString(c.getfInicio());
            if(t != null){                          //Ultimo trabajo
                fila[3]=t.getTarea()+" - "+t.getIdExplotacion();
            }else{
                fila[3]="";
            }

            this.modTablaCuad.addRow(fila);
            t = null;
        }
    }

    /**
     *
     */
    public void cargarTablaEmple() {
        this.modTablaEmple.setRowCount(0);
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
        
        String fila[] = new String[7];
        for (Trabajador t : listaEmple) {
            
            String tipo = "";
            if(t instanceof Conductor){
                tipo= "Conductor";
            }else if(t instanceof Encargado){
                tipo = "Encargado";
            }else{
                tipo = "Empleado";
            }
            
            if(!isContFinSelected() && t.getfFin()!= null){
                if(t.getfFin().isBefore(LocalDate.now())){
                    continue;//Se salta los empleados con contrato finalizado
                }
            }
            fila[0]=tipo;//tipo
            fila[1]=t.getDni();//dni
            fila[2]=t.getNombre(); //nombre
            fila[3]=t.getApellidos();//apellidos
            fila[4]=t.getTlf();//tlf
            fila[5]=Fechas.toString(t.getfContratacion());//f inicio
            if(t.getfFin() != null){                              //f fin
                fila[6]=Fechas.toString(t.getfFin());
            }else{
                fila[6]=null;
            }
            this.modTablaEmple.addRow(fila);
        }
    }

    /**
     *
     * @param c
     */
    public void cargarTablaTrab(Cuadrilla c) {
        this.modTablaTrabajos.setRowCount(0);
        ArrayList<Trabajo> listaTrabajos = this.modeloCuad.recuperarTrabajos(c);
        String fila[] = new String[5];
        for (Trabajo trabajo : listaTrabajos) {
            fila[0]=trabajo.getIdCuadrilla(); //CUADRILLA
            fila[1]=Fechas.toString(trabajo.getFecha());//FECHA
            fila[2]=trabajo.getHoras()+"";//HORAS
            fila[3]=trabajo.getTarea();//TIPO
            fila[4]=trabajo.getIdExplotacion();//EXPLOTACION
            this.modTablaTrabajos.addRow(fila);
        }
    }

    /**
     *
     */
    public void cargarTablaTrab() {
        this.modTablaTrabajos.setRowCount(0);
        ArrayList<Trabajo> listaTrabajos = this.modeloCuad.recuperarTrabajos();
        String fila[] = new String[5];
        for (Trabajo trabajo : listaTrabajos) {
            fila[0]=trabajo.getIdCuadrilla(); //CUADRILLA
            fila[1]=Fechas.toString(trabajo.getFecha());//FECHA
            fila[2]=trabajo.getHoras()+"";//HORAS
            fila[3]=trabajo.getTarea();//TIPO
            fila[4]=trabajo.getIdExplotacion();//EXPLOTACION
            this.modTablaTrabajos.addRow(fila);
        }
    }
    
    private void abrirAddCuadrilla() {
        ControladorAddCuad contAddCuad = new ControladorAddCuad(this,modeloCuad);
    }

    private void abrirAddTrabajo() {
        ControladorAddTrabajo contAddTrab = new ControladorAddTrabajo(this, modeloCuad);
    }

    private void abrirModCuadrilla() {
        String idCuad = (String) this.vistaTabla.jTableCuadrilla.getValueAt(getSelFilaCuad(), 0);
        Cuadrilla cuad = modeloCuad.recuperarPorId(idCuad);
        ControladorAddCuad contAddCuad = new ControladorAddCuad(this, modeloCuad, cuad);
    }

    private void abrirGestionarCuadrilla() {
        String idCuad = (String) this.vistaTabla.jTableCuadrilla.getValueAt(getSelFilaCuad(), 0);
        Cuadrilla cuad = modeloCuad.recuperarPorId(idCuad);
        ControladorAsignarCuad contAsigCuad = new ControladorAsignarCuad(this, modeloEmple, modeloCuad, cuad);
    }
    
    private void eliminarCuadrilla(int confirmacion) {
        if(confirmacion == JOptionPane.YES_OPTION){
            String idCuad = (String) vistaTabla.jTableCuadrilla.getValueAt(getSelFilaCuad(), 0);
            if(modeloCuad.borrarCuadrilla(idCuad)){
                JOptionPane.showMessageDialog(vistaTabla, "Cuadrilla eliminada correctamente");
                cargarTablaCuadrillas();
            }
        }
    }

    private void abrirVentanaAscenso() {
        
    }

    private void abrirAddEmple() {
        ControladorAddEmple contAddEmple = new ControladorAddEmple(this, modeloEmple);
    }

    private boolean eliminarTrabajador() {
        boolean res = true;
        
        String tipo = (String) vistaTabla.jTableEmple.getValueAt(getSelFilaEmple(), 0);
        String dni = (String) vistaTabla.jTableEmple.getValueAt(getSelFilaEmple(), 1);
        if(tipo.equalsIgnoreCase("Encargado")){
            if(!this.modeloEmple.borrarEncargado(dni)){
                res=false;
            }
        }else if(tipo.equalsIgnoreCase("Empleado")){
            if(!this.modeloEmple.borrarTrabajador(dni)){
                res=false;
            }
        }else{//Conductor , tractorista
            if(!this.modeloEmple.borrarConductor(dni)){
                res=false;
            }
        }
        
        return res;
    }

    private void abrirModTrabajo() {
        int fila = vistaTabla.jTableTrab.getSelectedRow();
        String idCuadrilla = (String) vistaTabla.jTableTrab.getValueAt(fila, 0);
        String fechaSt = (String) vistaTabla.jTableTrab.getValueAt(fila, 1);
        LocalDate fecha = Fechas.toLocalDate(fechaSt);
        int horas = Integer.parseInt((String) vistaTabla.jTableTrab.getValueAt(fila, 2));
        String tarea = (String) vistaTabla.jTableTrab.getValueAt(fila, 3);
        String idExplotacion = (String) vistaTabla.jTableTrab.getValueAt(fila, 4);
        
        Trabajo trab = new Trabajo(idCuadrilla, fecha, horas, tarea, idExplotacion);
        
        ControladorAddTrabajo contAddTrab = new ControladorAddTrabajo(this, modeloCuad, trab);
    }

    private void eliminarTrabajo(int confirmacion) {
        if(confirmacion == JOptionPane.YES_OPTION){
            int fila = vistaTabla.jTableTrab.getSelectedRow();
            String idCuadrilla = (String) vistaTabla.jTableTrab.getValueAt(fila, 0);
            String fechaSt = (String) vistaTabla.jTableTrab.getValueAt(fila, 1);
            LocalDate fecha = Fechas.toLocalDate(fechaSt);
            int horas = Integer.parseInt((String) vistaTabla.jTableTrab.getValueAt(fila, 2));
            String tarea = (String) vistaTabla.jTableTrab.getValueAt(fila, 3);
            String idExplotacion = (String) vistaTabla.jTableTrab.getValueAt(fila, 4);

            Trabajo trab = new Trabajo(idCuadrilla, fecha, horas, tarea, idExplotacion);
            
            if(modeloCuad.borrarTrabajo(trab)){
                JOptionPane.showMessageDialog(vistaTabla, "Trabajo borrado correctamente");
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Ha habido un error al borrar el trabajo","ERROR",JOptionPane.ERROR_MESSAGE);
            }
            this.cargarTablaTrab();
        }
    }

    private void abrirModEmple() {
        String tipo = (String) vistaTabla.jTableEmple.getValueAt(getSelFilaEmple(), 0);
        String dni = (String) vistaTabla.jTableEmple.getValueAt(getSelFilaEmple(), 1);
        Trabajador emple = null;
        if(tipo.equalsIgnoreCase("Encargado")){
            emple = modeloEmple.recuperarEncargado(dni);
        }else if(tipo.equalsIgnoreCase("Empleado")){
            emple = modeloEmple.recuperarTrabajador(dni);
        }else{//Conductor , tractorista
            emple = modeloEmple.recuperarConductor(dni);
        }
        ControladorAddEmple contAddEmple = new ControladorAddEmple(this, modeloEmple, emple);
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
        if(me.getClickCount() == 2){ //Doble click
            if(me.getSource().equals(vistaTabla.jTableCuadrilla) && getSelFilaCuad() != -1){
                abrirAddTrabajo();
                
            }else if(me.getSource().equals(vistaTabla.jTableEmple) && getSelFilaEmple() != -1){
                abrirModEmple();
                
            }else if(me.getSource().equals(vistaTabla.jTableTrab) && getSelFilaTrab() != -1){
                abrirModTrabajo();
                
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
