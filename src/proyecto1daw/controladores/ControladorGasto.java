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
import java.time.Month;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Gasto;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.accesobd.ExplotacionDAO;
import proyecto1daw.modelo.accesobd.GastoDAO;
import proyecto1daw.modelo.accesobd.PlantacionDAO;
import proyecto1daw.modelo.accesobd.VentaDAO;
import proyecto1daw.modelo.accesobd.mysql.GastoMysql;
import proyecto1daw.modelo.accesobd.mysql.PlantacionMysql;
import proyecto1daw.modelo.accesobd.mysql.VentaMysql;
import proyecto1daw.modelo.accesobd.sqlite.GastoSqlite;
import proyecto1daw.modelo.accesobd.sqlite.PlantacionSqlite;
import proyecto1daw.modelo.accesobd.sqlite.VentaSqlite;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFExplotacionAdd;
import proyecto1daw.vistas.JFGasto;

/**
 *
 * @author Jaime
 */
public class ControladorGasto implements ActionListener,MouseListener,ListSelectionListener{
    private JFGasto vista;
    private JFExplotacion vistaTablaExp;
    private ExplotacionDAO modeloExp;
    private PlantacionDAO modeloPlant;
    private VentaDAO modeloVenta;
    private GastoDAO modeloGasto;
    private DefaultTableModel modTabla;
    private DefaultListModel modLista;
    private DefaultComboBoxModel modComboPeriodo;
    private DefaultComboBoxModel modComboAnio;
    private Finca finca;
    private ArrayList<Explotacion> listaExp;

    public ControladorGasto(JFGasto vista, JFExplotacion vistaTablaExp, ExplotacionDAO modeloExp, Finca finca) {
        this.vista = vista;
        this.vistaTablaExp = vistaTablaExp;
        this.modeloExp = modeloExp;
        this.finca = finca;
        
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            this.modeloPlant = new PlantacionMysql();
            this.modeloVenta = new VentaMysql();
            this.modeloGasto = new GastoMysql();
        }else{
            this.modeloPlant = new PlantacionSqlite();
            this.modeloVenta = new VentaSqlite();
            this.modeloGasto = new GastoSqlite();
        }
        
        //Crear modelo lista y asociarlo
        this.modLista = new DefaultListModel();
        this.vista.jListPeriodos.setModel(modLista);
        
        //Crear modelos jcombo y asociarlos
        this.modComboAnio = new DefaultComboBoxModel<>();
        this.modComboPeriodo = new DefaultComboBoxModel<>();
        this.modComboPeriodo.addElement("Año");
        this.modComboPeriodo.addElement("Mes");
        this.modComboPeriodo.addElement("Quincena");
        this.cargarListaAnios();
        this.vista.jComboTipoPer.setModel(modComboPeriodo);
        this.vista.jComboAnio.setModel(modComboAnio);
        this.modComboAnio.setSelectedItem(LocalDate.now().getYear());
        this.modComboPeriodo.setSelectedItem("Quincena");
        
        //Crear modelo tabla y asociarlo
        this.modTabla = new DefaultTableModel();
        this.modTabla.addColumn("Fecha");
        this.modTabla.addColumn("Importe");
        this.modTabla.addColumn("Concepto");
        this.modTabla.addColumn("Id");
        this.vista.jTableVentas.setModel(modTabla);
        
        //Añadir listener a botones
        this.vista.botonAddGasto.addActionListener(this);
        this.vista.botonEliminarGasto.addActionListener(this);
        this.vista.botonModGasto.addActionListener(this);
        this.vista.botonVolver.addActionListener(this);
        this.vista.jComboTipoPer.addActionListener(this);
        this.vista.jComboAnio.addActionListener(this);
        this.vista.jListPeriodos.addListSelectionListener(this);
        //Asociar mouseListeners
        this.vista.jTableVentas.addMouseListener(this);
        
        //Cargar Explotaciones
        this.listaExp = new ArrayList<Explotacion>();
        listaExp = this.modeloExp.recuperarPorFinca(this.finca.getId());
        
        //Mostrar ventana
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
        
        //Cargar datos
        cargarPeriodos();
        this.vista.jListPeriodos.setSelectedIndex(0);
        cargarGastos();
        actualizarIngresos();
        actualizarGastos();
        actualizarBeneficios();
    }

    private void cargarListaAnios() {
        int anioInicio = finca.getfCompra().getYear();
        int anioFin = LocalDate.now().getYear();
        
        if(finca.getfFin() != null){
            anioFin = finca.getfFin().getYear();
        }
        
        for (int i = anioInicio; i <= anioFin; i++) {
            modComboAnio.addElement(i);
        }
    }

    private void actualizarIngresos() {
        double cantidad=0;
        ArrayList<Plantacion> listaPlant = null;
        //Estos métodos se encargan de saber si es por quincena,mes o año
        LocalDate fechaFin = this.getFechaFin();
        LocalDate fechaInicio = this.getFechaInicio();        
        
        if(vista.jListPeriodos.getSelectedIndex() != -1){
            //Recupera de cada explotacion sus plantaciones 
            for (Explotacion exp : listaExp) {
                //Se le suman dos meses porque se pueden realizar ventas aunque la plantacion se haya arrancado
                listaPlant = modeloPlant.recuperarPorFechaFin(fechaFin.plusMonths(2), exp.getId());
            //De cada plantacion recupera los ingresos del periodo abarcado
                for (Plantacion p : listaPlant) {
                    cantidad+=modeloVenta.calcularIngresos(fechaInicio, fechaFin, p.getId());
                }
            }
        }
        
        this.vista.jLabelCantidadIngresos.setText(cantidad+"€");
    }

    private void actualizarGastos() {
        double cantidad=0;
        //Estos métodos se encargan de saber si es por quincena,mes o año
        LocalDate fechaFin = this.getFechaFin();
        LocalDate fechaInicio = this.getFechaInicio();
        
        this.vista.jLabelCantidadGastos.setText(cantidad+"€");
    }
    
    private void actualizarBeneficios() {
        double ingresos = Double.parseDouble(this.vista.jLabelCantidadIngresos.getText().replace("€",""));
        double gastos = Double.parseDouble(this.vista.jLabelCantidadBeneficio.getText().replace("€",""));
        double beneficio = ingresos-gastos;
        
        this.vista.jLabelCantidadBeneficio.setText(beneficio+"€");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vista.botonVolver)){
            volver();
        }else if(ae.getSource().equals(vista.botonAddGasto)){
            abrirAddGasto();
        }else if(ae.getSource().equals(vista.botonEliminarGasto)){
            int fila = vista.jTableVentas.getSelectedRow();
            if(fila != -1){
                eliminarGasto();
            }else{
                JOptionPane.showMessageDialog(vista, "Necesitas seleccionar"
                            + " un gasto", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getSource().equals(vista.botonModGasto)){
            int fila = vista.jTableVentas.getSelectedRow();
            if(fila != -1){
                abrirModGasto();
            }else{
                JOptionPane.showMessageDialog(vista, "Necesitas seleccionar"
                            + " un gasto", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getSource().equals(vista.jComboAnio)){
            cargarPeriodos();
        }else if(ae.getSource().equals(vista.jComboTipoPer)){
            cargarPeriodos();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        JTable tabla = (JTable) me.getSource();
        int fila = tabla.getSelectedRow();
        double cantidad = 0;

        if (fila != -1 && me.getClickCount() == 2) {
            abrirModGasto();
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if(lse.getSource().equals(vista.jListPeriodos) && !vista.jListPeriodos.isSelectionEmpty()){
            actualizarIngresos();
            actualizarGastos();
            actualizarBeneficios();
            cargarGastos();
        }
    }

    //Estos métodos se encargan de saber si es por quincena,mes o año
    
    private LocalDate getFechaInicio() {
        LocalDate fechaInicio = null;
        String periodo = vista.jListPeriodos.getSelectedValue();
        
        if(vista.jComboTipoPer.getSelectedItem().equals("Quincena")){
            String fecha = periodo.substring(0,periodo.indexOf(" "));
            fechaInicio = Fechas.toLocalDate(fecha);
            
        }else if(vista.jComboTipoPer.getSelectedItem().equals("Mes")){
            String mes = periodo;
            int anio = (int) vista.jComboAnio.getSelectedItem();
            fechaInicio = LocalDate.of(anio, Fechas.mesToInt(mes), 1);
            
        }else if(vista.jComboTipoPer.getSelectedItem().equals("Año")){
            int anio = (int) vista.jComboAnio.getSelectedItem();
            fechaInicio = LocalDate.of(anio, Month.JANUARY, 1);
            
        }
        
        return fechaInicio;
    }
    
    private LocalDate getFechaFin() {
        LocalDate fechaFin = null;
        String periodo = vista.jListPeriodos.getSelectedValue();
        
        if(periodo != null){
            if(vista.jComboTipoPer.getSelectedItem().equals("Quincena")){
                String fecha = periodo.substring(periodo.lastIndexOf(" ")+1);
                fechaFin = Fechas.toLocalDate(fecha);

            }else if(vista.jComboTipoPer.getSelectedItem().equals("Mes")){
                String mes = periodo;
                int anio = (int) vista.jComboAnio.getSelectedItem();
                LocalDate fAux = LocalDate.of(anio, Fechas.mesToInt(mes), 1).plusMonths(1);
                fechaFin = fAux.minusDays(1);

            }else if(vista.jComboTipoPer.getSelectedItem().equals("Año")){
                int anio = (int) vista.jComboAnio.getSelectedItem();
                fechaFin = LocalDate.of(anio, Month.DECEMBER, 31);

            }
        }
        
        return fechaFin;
    }

    private void abrirModGasto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void volver() {
        new ControladorExplotacion(new JFExplotacion(), modeloExp, finca);
        this.vista.dispose();
    }

    private void abrirAddGasto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void eliminarGasto() {
        int b = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de eliminar este gasto?");
        if (b == JOptionPane.YES_OPTION) {
            String idGasto = (String) this.vista.jTableVentas.getValueAt(this.vista.jTableVentas.getSelectedRow(), 0);
            modeloGasto.borrarGasto(idGasto);
            
            cargarGastos();
        }
    }

    private void cargarGastos() {
        ArrayList<Gasto> listaGastos = this.modeloGasto.recuperarPorFinca(finca.getId());
        
        int i = 0;
        for (Gasto g : listaGastos) {
            String[] fila = new String[3];
            //Añadir datos del gasto
            fila[0] = g.getConcepto();
            fila[1] =g.getImporte()+"";
            fila[2] =g.getIdGasto();
            //Añadir fecha
            this.vista.jTableVentas.setValueAt(g.getFecha(), i, 3);//No es seguro el 4
            i++;
        }
    }

    private void cargarPeriodos() {
        this.modLista.clear();
                
        if(vista.jComboTipoPer.getSelectedItem().equals("Quincena")){
            for (int i = 1; i <= 12; i++) {
                int anio = (int) vista.jComboAnio.getSelectedItem();
                this.modLista.addElement(Fechas.toString(LocalDate.of(anio, i, 1))+" - "+Fechas.toString(LocalDate.of(anio, i, 15)));
                this.modLista.addElement(Fechas.toString(LocalDate.of(anio, i, 16))+" - "
                        +Fechas.toString(LocalDate.of(anio, i, 1).plusMonths(1).minusDays(1)));
            }
        }else if(vista.jComboTipoPer.getSelectedItem().equals("Mes")){
            for (int i = 1; i <= 12; i++) {
                this.modLista.addElement(Fechas.mesIntToString(i));
            }
        }else if(vista.jComboTipoPer.getSelectedItem().equals("Año")){
            int anio = (int) vista.jComboAnio.getSelectedItem();
            this.modLista.addElement(anio+"");
        }
    }
    
    
}
