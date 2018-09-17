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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.accesobd.ExplotacionDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.accesobd.FincaDAO;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.accesobd.PlantacionDAO;
import proyecto1daw.modelo.accesobd.mysql.PlantacionMysql;
import proyecto1daw.modelo.accesobd.sqlite.PlantacionSqlite;
import proyecto1daw.vistas.JFEstadisticas;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author Jaime
 */
class ControladorEstadisticas implements ActionListener, ListSelectionListener{
    private JFEstadisticas vista;
    private FincaDAO modeloFinca;
    private ExplotacionDAO modeloExp;
    private PlantacionDAO modeloPlant;
    private DefaultComboBoxModel modTipo;
    private DefaultComboBoxModel modSubtipo;
    private DefaultListModel modLista;
    
    public ControladorEstadisticas(FincaDAO modeloFinca, ExplotacionDAO modeloExp) {
        this.vista = new JFEstadisticas();
        this.modeloFinca = modeloFinca;
        this.modeloExp = modeloExp;
        
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            this.modeloPlant = new PlantacionMysql();
        }else{
            this.modeloPlant = new PlantacionSqlite();
        }
        
        
        //Crear modelos jcombo
        this.modTipo = new DefaultComboBoxModel();
        this.modSubtipo = new DefaultComboBoxModel();
        //Asociar modelos a los desplegables
        this.vista.jComboTipo.setModel(modTipo);
        this.vista.jComboSubtipo.setModel(modSubtipo);
        //Crear modelo lista y asociarlo
        this.modLista = new DefaultListModel();
        this.vista.jListOpciones.setModel(modLista);
        //Asociar listener
        this.vista.botonVolver.addActionListener(this);
        this.vista.jComboTipo.addActionListener(this);
        this.vista.jComboSubtipo.addActionListener(this);
        this.vista.jListOpciones.addListSelectionListener(this);
        //Cargar lista posibles estadisticas
        this.cargarListaEstad();
        this.vista.jListOpciones.setSelectedIndex(0);
        //Cargar desplegables
        this.cargarTipo();
        if(isTipoSelected()){
            this.cargarSubtipo();
        }
        //Mostrar
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vista.botonVolver)){                           //VOLVER    
            ControladorInicio contInicio = new ControladorInicio(new JFInicio());
            vista.dispose();
        }else if(ae.getSource().equals(vista.jComboTipo)){
            if(isTipoSelected()){
                cargarSubtipo();
            }
        }else if(ae.getSource().equals(vista.jComboSubtipo)){
            if(isSubtipoSelected() && isListaSelected()){
                cargarEstadisticas();
            }
        }
    }

    public boolean isSubtipoSelected() {
        return (vista.jComboSubtipo.getSelectedIndex() != -1);
    }

    private void cargarTipo() {
        ArrayList<Explotacion> listaExp = modeloExp.recuperarTodas();
        for (Explotacion exp : listaExp) {
            this.modTipo.addElement(exp);
        }
    }
    
    private void cargarSubtipo() {
        this.modSubtipo.removeAllElements();
        Explotacion exp = (Explotacion) this.vista.jComboTipo.getSelectedItem();
        ArrayList<Plantacion> listaPlant = modeloPlant.recuperarPorExp(exp.getId());
        for (Plantacion p : listaPlant) {
            this.modSubtipo.addElement(p);
        }
    }

    //Cuando cambia el valor de la lista
    public void valueChanged(ListSelectionEvent lse) {
        if(isSubtipoSelected() && isListaSelected()){
            cargarEstadisticas();
        }
    }
    
    private void cargarEstadisticas() {
        if(vista.jListOpciones.getSelectedValue().equals("Distribucion colores")){
            cargarGrafColoresQuesito();
        }else if(vista.jListOpciones.getSelectedValue().equals("Kg por color al mes")){
            cargarGrafKgColorMes();
        }else if(vista.jListOpciones.getSelectedValue().equals("Kg/Color por quincena")){
            cargarGrafKgColorQuincena();
        }else if(vista.jListOpciones.getSelectedValue().equals("Precio medio total por mes")){
            cargarGrafPrecioMedioMes();
        }else if(vista.jListOpciones.getSelectedValue().equals("Precio medio color por mes")){
            cargarGrafPrecioMedioColorMes();
        }else if(vista.jListOpciones.getSelectedValue().equals("Precio medio color por quincena")){
            cargarGrafPrecioMedioColorQuincena();
        }else if(vista.jListOpciones.getSelectedValue().equals("Kg por año")){
            cargarGrafKgAnio();
        }else if(vista.jListOpciones.getSelectedValue().equals("Kg por mes")){
            cargarGrafKgMes();
        }else if(vista.jListOpciones.getSelectedValue().equals("Kg por quincena")){
            cargarGrafKgQuincena();
        }
    }

    public void cargarGrafColoresQuesito() {
        DefaultPieDataset datosGraf = new DefaultPieDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        HashMap valores = modeloPlant.estadisticasColorTotal(p);
        
        Iterator it = valores.keySet().iterator();
        String clave = "";
        while(it.hasNext()){
            clave = (String) it.next();
            datosGraf.setValue(clave, (int) valores.get(clave));
        }
        
        JFreeChart grafico = ChartFactory.createPieChart3D("Distribución colores", datosGraf,true,true, false);
        
        this.vista.cargarGrafico(grafico);
    }

    private void cargarListaEstad() {
        this.modLista.addElement("Distribucion colores");
        this.modLista.addElement("Kg por color al mes");
        this.modLista.addElement("Kg/Color por quincena");
        this.modLista.addElement("Precio medio total por mes");
        this.modLista.addElement("Precio medio color por mes");
        this.modLista.addElement("Precio medio color por quincena");
        this.modLista.addElement("Kg por año");
        this.modLista.addElement("Kg por mes");
        this.modLista.addElement("Kg por quincena");
        
    }

    private boolean isListaSelected() {
        return (vista.jListOpciones.getSelectedIndex() != -1);
    }

    private void cargarGrafKgMes() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        LinkedHashMap valores = modeloPlant.estadisticasKgMes(p, LocalDate.now().getYear());
        
        Iterator it = valores.keySet().iterator();
        String clave = "";
        while(it.hasNext()){
            clave = (String) it.next();
            datosGraf.setValue((int) valores.get(clave),"KG", clave);
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Cantidad(Kg) por mes", "Meses", "Cantidad(Kg)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }

    private void cargarGrafPrecioMedioMes() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        LinkedHashMap valores = modeloPlant.estadisticasPrecioMes(p, LocalDate.now().getYear());
        
        Iterator it = valores.keySet().iterator();
        String clave = "";
        while(it.hasNext()){
            clave = (String) it.next();
            datosGraf.setValue((double) valores.get(clave),"Precio(€)", clave);
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Precio(€/Kg) por mes", "Meses", "Precio(€)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }

    private boolean isTipoSelected() {
        return (vista.jComboTipo.getSelectedIndex() != -1);
    }

    private void cargarGrafKgAnio() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        LinkedHashMap valores = modeloPlant.estadisticasKgAnio(p, 10);
        
        Iterator it = valores.keySet().iterator();
        int clave = 0;
        while(it.hasNext()){
            clave = (int) it.next();
            datosGraf.setValue((int) valores.get(clave),"KG", clave+"");
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Cantidad(Kg) por año", "Años", "Cantidad(Kg)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }

    private void cargarGrafPrecioMedioColorMes() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        LinkedHashMap valores = modeloPlant.estadisticasPrecioColorMes(p, LocalDate.now().getYear());
        
        Iterator it = valores.keySet().iterator();
        String mes = "";
        while(it.hasNext()){
            mes = (String) it.next();
            String color = "";
            LinkedHashMap colores = (LinkedHashMap) valores.get(mes);
            Iterator itColor = colores.keySet().iterator();
            while(itColor.hasNext()){
                color = (String) itColor.next();
                datosGraf.setValue((double)colores.get(color), color, mes);
            }
            
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Precio(€/Kg) por mes", "Meses", "Precio(€)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }

    private void cargarGrafKgColorMes() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        LinkedHashMap valores = modeloPlant.estadisticasKgColorMes(p, LocalDate.now().getYear());
        
        Iterator it = valores.keySet().iterator();
        String mes = "";
        while(it.hasNext()){
            mes = (String) it.next();
            String color = "";
            LinkedHashMap colores = (LinkedHashMap) valores.get(mes);
            Iterator itColor = colores.keySet().iterator();
            while(itColor.hasNext()){
                color = (String) itColor.next();
                datosGraf.setValue((double)colores.get(color), color, mes);
            }
            
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Precio(€/Kg) por mes", "Meses", "Precio(€)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }

    private void cargarGrafKgColorQuincena() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        LinkedHashMap valores = modeloPlant.estadisticasKgColorQuincena(p, LocalDate.now().getYear());
        
        Iterator it = valores.keySet().iterator();
        String mes = "";
        while(it.hasNext()){
            mes = (String) it.next();
            String color = "";
            LinkedHashMap colores = (LinkedHashMap) valores.get(mes);
            Iterator itColor = colores.keySet().iterator();
            while(itColor.hasNext()){
                color = (String) itColor.next();
                datosGraf.setValue((double)colores.get(color), color, mes);
            }
            
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Precio(€/Kg) por quincena", "Quincenas", "Precio(€)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }

    private void cargarGrafPrecioMedioColorQuincena() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        LinkedHashMap valores = modeloPlant.estadisticasPrecioColorQuincena(p, LocalDate.now().getYear());
        
        Iterator it = valores.keySet().iterator();
        String mes = "";
        while(it.hasNext()){
            mes = (String) it.next();
            String color = "";
            LinkedHashMap colores = (LinkedHashMap) valores.get(mes);
            Iterator itColor = colores.keySet().iterator();
            while(itColor.hasNext()){
                color = (String) itColor.next();
                datosGraf.setValue((double)colores.get(color), color, mes);
            }
            
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Precio(€/Kg) por quincena", "Quincenas", "Precio(€)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }

    private void cargarGrafKgQuincena() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        LinkedHashMap valores = modeloPlant.estadisticasKgQuincena(p, LocalDate.now().getYear());
        
        Iterator it = valores.keySet().iterator();
        String clave = "";
        while(it.hasNext()){
            clave = (String) it.next();
            datosGraf.setValue((int) valores.get(clave),"KG", clave);
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Cantidad(Kg) por quincena", "Quincenas", "Cantidad(Kg)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }
    
    
}

