/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.accesobd.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.accesobd.PlantacionDAO;
import proyecto1daw.modelo.Venta;
import proyecto1daw.modelo.accesobd.VentaDAO;
import proyecto1daw.modelo.accesobd.mysql.ExplotacionMysql;
import proyecto1daw.modelo.accesobd.mysql.PlantacionMysql;
import proyecto1daw.modelo.accesobd.mysql.VentaMysql;
import proyecto1daw.modelo.accesobd.sqlite.ExplotacionSqlite;
import proyecto1daw.modelo.accesobd.sqlite.PlantacionSqlite;
import proyecto1daw.modelo.accesobd.sqlite.VentaSqlite;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFPlantacion;
import proyecto1daw.vistas.JFPlantacionAdd;
import proyecto1daw.vistas.JFVentaAdd;

/**
 *
 * @author Jaime
 */
public class ControladorPlantacion implements ActionListener,MouseListener {

    private JFPlantacion vistaTabla;
    private JFVentaAdd vistaAddVenta;
    private PlantacionDAO modeloPlant;
    private VentaDAO modeloVenta;
    private DefaultTableModel modTablaPlant;
    private DefaultTableModel modTablaVentas;
    private String idExplotacion;
    private Finca finca;

    /**
     *
     * @param vistaTabla ventana donde se cargan las plantaciones y sus respectivas ventas
     * @param idExplotacion explotacion a al que pertenecen las plantaciones
     * @param finca finca a al que pertenencen las plantaciones
     */
    public ControladorPlantacion(JFPlantacion vistaTabla,String idExplotacion,Finca finca) {
        this.vistaTabla = vistaTabla;
        this.vistaAddVenta = new JFVentaAdd();
        this.idExplotacion = idExplotacion;
        this.finca = finca;
        
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            this.modeloPlant = new PlantacionMysql();
            this.modeloVenta = new VentaMysql();
        }else{
            this.modeloPlant = new PlantacionSqlite();
            this.modeloVenta = new VentaSqlite();
        }
        
        //Asociar listener a botones vistaTabla
        this.vistaTabla.botonAddPlant.addActionListener(this);
        this.vistaTabla.botonAddVenta.addActionListener(this);
        this.vistaTabla.botonEliminarPlant.addActionListener(this);
        this.vistaTabla.botonEliminarVent.addActionListener(this);
        this.vistaTabla.botonModPlant.addActionListener(this);
        this.vistaTabla.botonModVenta.addActionListener(this);
        this.vistaTabla.botonBuscarPlant.addActionListener(this);
        this.vistaTabla.botonBuscarVenta.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);

        this.modTablaPlant = new DefaultTableModel(){   //Para no poder editar las celdas de la tabla
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
            
        };
        this.modTablaVentas = new DefaultTableModel(){   //Para no poder editar las celdas de la tabla
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int i) {
                if(this.findColumn("Fecha") == i){
                    return LocalDate.class;
                }
                return super.getColumnClass(i);
            }
            
            
        };
        //Crear columnas tabla Plantaciones
        this.vistaTabla.jTablePlantaciones.getTableHeader().setReorderingAllowed(false);
        this.modTablaPlant.addColumn("ID");
        this.modTablaPlant.addColumn("Tipo");
        this.modTablaPlant.addColumn("Variedad");
        this.modTablaPlant.addColumn("Fecha plant.");
        this.modTablaPlant.addColumn("Fecha arrancar");

        //Crear columnas tabla Ventas
        this.vistaTabla.jTableVentas.getTableHeader().setReorderingAllowed(false);
        this.modTablaVentas.addColumn("Id-Venta");
        this.modTablaVentas.addColumn("Kg");
        this.modTablaVentas.addColumn("€/KG");
        this.modTablaVentas.addColumn("Color");
        this.modTablaVentas.addColumn("Total €");
        this.modTablaVentas.addColumn("Fecha");

        //Rellenar tabla Plantaciones
        this.actualizarTablaPlant();
        this.vistaTabla.jTablePlantaciones.setModel(modTablaPlant);
        //Asignar modelo ,crear renderer para ordenar para fecha
        FechaSorter ordenaFecha = new FechaSorter();
        this.vistaTabla.jTableVentas.setModel(modTablaVentas);
        this.vistaTabla.jTableVentas.setDefaultRenderer(LocalDate.class, ordenaFecha);
        this.vistaTabla.jTableVentas.setAutoCreateRowSorter(true);
        //Añadir listener raton a tablas
        this.vistaTabla.jTablePlantaciones.addMouseListener(this);
        this.vistaTabla.jTableVentas.addMouseListener(this);
        
        //Mostar ventana
        this.vistaAddVenta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAddVenta.setLocationRelativeTo(null);
        this.vistaTabla.jLabelExplotacion.setText(this.idExplotacion);
        this.vistaTabla.setVisible(true);
    }

    /**
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        JButton boton = null;
        JCheckBox checkbox = null;
        if (ae.getSource() instanceof JButton) {
            boton = (JButton) ae.getSource();
            if (boton.equals(this.vistaTabla.botonAddPlant)) {                             //ABRIR AÑADIR PLANTACION
                if(!modeloPlant.hayPlantSinFinalizar(idExplotacion)){
                    abrirAddPlant();
                }else{
                    String msg = "Hay una plantación sin fecha de fin.\n"
                            + "Debes finalizarla antes de añadir una nueva.";
                    JOptionPane.showMessageDialog(vistaTabla, msg, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonVolver)){                         //VOLVER
                volver();
                
            }else if (boton.equals(this.vistaTabla.botonModPlant)) {                       //ABRIR MOD PLANTACION
                if(this.vistaTabla.jTablePlantaciones.getSelectedRow() != -1){
                    abrirModPlant();
                }else{
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una plantacion", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonEliminarPlant)) {               //ELIMINAR PLANTACION

                if (this.vistaTabla.jTablePlantaciones.getSelectedRow() != -1) {
                    int confirmacion = JOptionPane.showConfirmDialog(this.vistaTabla, 
                            "¿Estás seguro de eliminar esta plantación?\nSe eliminarán todas sus ventas");
                    eliminarPlant(confirmacion);
                } else { //No hay fila sel
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una plantacion", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonAddVenta)) {                       //ABRIR AÑADIR VENTA
                if (this.vistaTabla.jTablePlantaciones.getSelectedRow() != -1) {
                    abrirAddVenta();
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una plantación primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonModVenta)) {                       //ABRIR MODIFICAR VENTA
                if (this.vistaTabla.jTableVentas.getSelectedRow() != -1) {
                    abrirModVenta();
                } else {
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar"
                            + " una venta primero", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonEliminarVent)) {                   //ELIMINAR VENTA
                
                if (this.vistaTabla.jTableVentas.getSelectedRow() != -1) {
                    int confirmacion= JOptionPane.showConfirmDialog(vistaAddVenta, "¿Estás seguro de eliminar esta venta?");
                    eliminarVenta(confirmacion);
                } else { // No venta seleccionada
                    JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar "
                        + "una venta", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }

            } else if (boton.equals(this.vistaTabla.botonBuscarPlant)){                      //BUSCAR PLANTACION
                buscarPlantacion();
                
            }else if(boton.equals(this.vistaTabla.botonBuscarVenta)){                       //BUSCAR VENTA
                int fila=this.vistaTabla.jTablePlantaciones.getSelectedRow();
                if(fila != -1){
                    buscarVenta(fila);
                }else{
                    JOptionPane.showMessageDialog(vistaAddVenta, "Necesitas seleccionar una plantacion", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    private void buscarVenta(int fila) {
        LocalDate fVentaInicio = null;
        LocalDate fVentaFin = null;
        fVentaInicio= Fechas.toLocalDate(this.vistaTabla.campoFVentaInicio.getText());
        fVentaFin= Fechas.toLocalDate(this.vistaTabla.campoFVentaFin.getText());
        String idPlant = (String) this.vistaTabla.jTablePlantaciones.getValueAt(fila, 0);
        
        //Vacia la tabla
        this.modTablaVentas.setRowCount(0);
        //Busca por las fechas
        ArrayList<Venta> listaVentas = null;
        if(fVentaFin==null && fVentaInicio!= null){                  //F INICIO SOLO
            listaVentas = modeloVenta.recuperarPorFecha(fVentaInicio,idPlant);
        }else if(fVentaInicio == null && fVentaFin != null){          //F FIN SOLO
            listaVentas = modeloVenta.recuperarPorFecha(fVentaFin,idPlant);
        }else if(fVentaInicio != null && fVentaFin != null){         //F INICIO Y F FIN
            listaVentas = modeloVenta.recuperarPorFechas(fVentaInicio,fVentaFin,idPlant);
        }

        if(listaVentas != null){
            String[] s = new String[6];
            int i = 0;
            for (Venta v : listaVentas) {
                s[0] = v.getId();
                s[1] = v.getKg() + "";
                s[2] = v.getPrecio() + "";
                s[3] = v.getColor() + " " + v.getTamanio();
                s[4] = (v.getKg() * v.getPrecio()) + "";
                //s[5] = Fechas.toString(v.getFecha());
                modTablaVentas.addRow(s);
                modTablaVentas.setValueAt(v.getFecha(), i, 5);//Se añade como objeto LocalDate
                
                i++;
            }
            double cantidad=0;
            if(fVentaFin != null){
                if(fVentaInicio!=null){//F INI y F FIN
                    cantidad=this.modeloVenta.calcularIngresos(fVentaInicio,fVentaFin,idPlant);
                    
                }else{//Solo F FIN
                    cantidad=this.modeloVenta.calcularIngresos(LocalDate.of(1950, Month.MARCH, 1),fVentaFin,idPlant);
                }
            }else{
                if(fVentaInicio !=null){//Solo F INI
                    cantidad=this.modeloVenta.calcularIngresos(fVentaInicio,LocalDate.of(2200, Month.MARCH, 1),idPlant);
                    
                }
            }
            actLabelIngresos(cantidad);
        }else{
            actualizarTablaVentas();
        }
    }

    private void buscarPlantacion() {
        LocalDate fInicio = null;
        LocalDate fFin = null;
        if(validarFInicio() && validarFFin()){
            fInicio = Fechas.toLocalDate(this.vistaTabla.campoFIn.getText());
            fFin = Fechas.toLocalDate(this.vistaTabla.campoFFin.getText());
        }else if(validarFInicio()){
            fInicio = Fechas.toLocalDate(this.vistaTabla.campoFIn.getText());
            fFin = LocalDate.of(2100, Month.MARCH, 1);
        }else if(validarFFin()){
            fInicio = LocalDate.of(1900, Month.MARCH, 1);
            fFin = Fechas.toLocalDate(this.vistaTabla.campoFFin.getText());
        }else{
            fInicio = LocalDate.of(1900, Month.MARCH, 1);
            fFin = LocalDate.of(2100, Month.MARCH, 1);
        }

        this.modTablaVentas.setRowCount(0);
        this.modTablaPlant.setRowCount(0);
        this.actualizarTablaPlant(fInicio, fFin);
    }    

    private void eliminarVenta(int confirmacion) {
        if(confirmacion==JOptionPane.YES_OPTION){
            int filaSelVenta = this.vistaTabla.jTableVentas.getSelectedRow();
            int filaSelPlant = this.vistaTabla.jTablePlantaciones.getSelectedRow();
            String idPlant=(String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelPlant,0);
            String idVenta=(String) this.vistaTabla.jTableVentas.getValueAt(filaSelVenta,0);
            modeloVenta.borrarVenta(idVenta, idPlant);
            
            this.actualizarTablaVentas();
            this.actualizarEtiquetasIngresos();
        }
    }

    private void abrirModVenta() {
        int filaPlant = vistaTabla.jTablePlantaciones.getSelectedRow();
        String idPlant = (String) vistaTabla.jTablePlantaciones.getValueAt(filaPlant, 0);
        Plantacion plant = modeloPlant.recuperarPorId(idPlant);
        
        int filaVenta = vistaTabla.jTableVentas.getSelectedRow();
        String idVenta = (String) vistaTabla.jTableVentas.getValueAt(filaVenta, 0);
        Venta vent = modeloVenta.recuperarPorId(idVenta, idPlant);
        
        ControladorAddVenta contAddVenta = new ControladorAddVenta(this, modeloVenta, idExplotacion, finca,plant, vent);
    }

    private void abrirAddVenta() {
        int fila = vistaTabla.jTablePlantaciones.getSelectedRow();
        String idPlant = (String) vistaTabla.jTablePlantaciones.getValueAt(fila, 0);
        Plantacion plant = modeloPlant.recuperarPorId(idPlant);
        ControladorAddVenta contAddVenta = new ControladorAddVenta(this, modeloVenta, idExplotacion, finca,plant);
    }

    private void eliminarPlant(int confirmacion) {
        if(confirmacion == JOptionPane.YES_OPTION){
            int filaSelVenta = this.vistaTabla.jTablePlantaciones.getSelectedRow();
            String idPlant=(String) this.vistaTabla.jTablePlantaciones.getValueAt(filaSelVenta,0);
            if(this.modeloVenta.borrarVentasPlantacion(idPlant)){
                this.modeloPlant.borrarPlantacion(idPlant);
                this.actualizarTablaPlant();
                JOptionPane.showMessageDialog(vistaTabla, "Ventas y plantación borrados correctamente.");
                this.actualizarTablaVentas();
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Ha habido un error al eliminar las plantaciones."
                        + "\nContacta con tu administrador si el problema persiste.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void volver() {
        ExplotacionDAO modeloExp;
        
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            modeloExp = new ExplotacionMysql();
        }else{
            modeloExp = new ExplotacionSqlite();
        }
        
        ControladorExplotacion contExp = new ControladorExplotacion(new JFExplotacion(), modeloExp, this.finca);
        this.vistaTabla.dispose();
    }

    private void abrirAddPlant() {
        ControladorAddPlant contAddPlant = new ControladorAddPlant(this, modeloPlant, idExplotacion, finca);
    }
    
    private void abrirModPlant() {
        int fila = vistaTabla.jTablePlantaciones.getSelectedRow();
        String idPlant = (String) vistaTabla.jTablePlantaciones.getValueAt(fila, 0);
        Plantacion plant = modeloPlant.recuperarPorId(idPlant);
        ControladorAddPlant contAddPlant = new ControladorAddPlant(this, modeloPlant, idExplotacion, finca, plant);
    }

    /**
     *
     */
    public void actualizarTablaPlant() {
        this.modTablaPlant.setRowCount(0);
        ArrayList<Plantacion> listaPlant = modeloPlant.recuperarPorExp(idExplotacion);
        String[] s = new String[5];
        for (Plantacion p : listaPlant) {
            s[0] = p.getId();
            s[1] = p.getTipo();
            s[2] = p.getVariedad();
            s[3] = Fechas.toString(p.getfInicio());
            if(p.getfFin() != null){
                s[4] = Fechas.toString(p.getfFin());
            }else{
                s[4]="";
            }
            modTablaPlant.addRow(s);
        }
    }
    
    public void actualizarTablaPlant(LocalDate fInicio, LocalDate fFin) {
        this.modTablaPlant.setRowCount(0);
        ArrayList<Plantacion> listaPlant = modeloPlant.recuperarPorFecha(fInicio, fFin, idExplotacion);
        String[] s = new String[5];
        for (Plantacion p : listaPlant) {
            s[0] = p.getId();
            s[1] = p.getTipo();
            s[2] = p.getVariedad();
            s[3] = Fechas.toString(p.getfInicio());
            if(p.getfFin() != null){
                s[4] = Fechas.toString(p.getfFin());
            }else{
                s[4]="";
            }
            modTablaPlant.addRow(s);
        }
    }

    /**
     *
     */
    public void actualizarTablaVentas() {
        this.modTablaVentas.setRowCount(0);
        int filaSel = vistaTabla.jTablePlantaciones.getSelectedRow();
        if(filaSel != -1) {
            String idPlant = (String) vistaTabla.jTablePlantaciones.getValueAt(filaSel, 0);
            ArrayList<Venta> listaVentas = modeloVenta.recuperarPorPlant(idPlant);

            Object[] s = new String[6];
            int i = 0;
            for (Venta v : listaVentas) {
                s[0] = v.getId();
                s[1] = v.getKg() + "";
                s[2] = v.getPrecio() + "";
                s[3] = v.getColor() + " " + v.getTamanio();
                s[4] = (v.getKg() * v.getPrecio()) + "";
                //s[5] = Fechas.toString(v.getFecha());
                modTablaVentas.addRow(s);
                modTablaVentas.setValueAt(v.getFecha(), i, 5);
                i++;
            }
            this.vistaTabla.jTableVentas.getRowSorter().toggleSortOrder(5);
        }
    }
    
   
    
    private boolean validarFInicio() {
        boolean res=true;
        String s = this.vistaTabla.campoFIn.getText();
        if(s.equals("") || s == null){
           res=false; 
        }else if(Fechas.toLocalDate(s) == null){
            res=false;
        }
        return res;
    }

    private boolean validarFFin() {
        boolean res=true;
        String s = this.vistaTabla.campoFFin.getText();
        if(s.equals("") || s == null){
           res=false; 
        }else if(Fechas.toLocalDate(s) == null){
            res=false;
        }
        return res;
    }

    private boolean validarFVenta() {
        boolean res=true;
        String s = this.vistaTabla.campoFVentaInicio.getText();
        if(s.equals("")){
           res=false; 
        }else if(Fechas.toLocalDate(s) == null){
            res=false;
        }
        return res;
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
        if(tabla.equals(vistaTabla.jTablePlantaciones)){
            if(fila != -1){
                if(me.getClickCount() == 2){                            //DOBLE CLICK ABRE NUEVA VENTA
                    abrirModPlant();
                }else{
                    actualizarEtiquetasIngresos();
                }
            }
        }else{
            if(me.getClickCount() == 2){                            //DOBLE CLICK ABRE NUEVA VENTA
                    abrirModVenta();
                }
        }
            
    }

    /**
     *
     * @return actaliza el jLabel con los ingresos de la plantacion
     */
    public void actualizarEtiquetasIngresos() {
        double cantidad;
        int fila = vistaTabla.jTablePlantaciones.getSelectedRow();
        String idPlant = (String) vistaTabla.jTablePlantaciones.getValueAt(fila, 0);//ID_EXP
        //Carga ventas de la plantación
        actualizarTablaVentas();
        actLabelIngresos(idPlant);
        actLabelQuincAnt(idPlant);
        actLabelQuincActual(idPlant);
    }

    private void actLabelIngresos(String idPlant) {
        double cantidad;
        cantidad = modeloVenta.calcularIngresos(idPlant);
        vistaTabla.jLabelCantidadIngresos.setText(cantidad+"€");
    }
    
    private void actLabelIngresos(double cantidad) {
        vistaTabla.jLabelCantidadIngresos.setText(cantidad+"€");
    }
    
    private void actLabelQuincAnt(String idPlant) {
        double cantidad;
        cantidad = modeloVenta.calcularQuincAnt(idPlant);
        vistaTabla.jLabelCantidadQAnt.setText(cantidad+"€");
    }
    
    private void actLabelQuincActual(String idPlant) {
        double cantidad;
        cantidad = modeloVenta.calcularQuincActual(idPlant);
        vistaTabla.jLabelCantidadQActual.setText(cantidad+"€");
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
    
    public class FechaSorter extends DefaultTableCellRenderer{
        public void setValue(Object value) {
            if(value instanceof LocalDate){
                LocalDate fecha = (LocalDate) value;
                this.setText(Fechas.toString(fecha));
            }else{
                this.setText((String) value);
            }
        }
    }
}
