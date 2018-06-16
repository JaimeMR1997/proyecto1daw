/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import proyecto1daw.modelo.Explotacion;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.modelo.Plantacion;
import proyecto1daw.modelo.PlantacionDAO;
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
        this.modeloPlant = new PlantacionDAO();
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
        //Cargar lista
        this.cargarLista();
        this.vista.jListOpciones.setSelectedIndex(0);
        //Cargar desplegables
        this.cargarTipo();
        this.cargarSubtipo();
        //Mostrar
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vista.botonVolver)){                           //VOLVER    
            ControladorInicio contInicio = new ControladorInicio(new JFInicio());
            vista.dispose();
        }else if(ae.getSource().equals(vista.jComboTipo)){
            cargarSubtipo();
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
        }else if(vista.jListOpciones.getSelectedValue().equals("Kg por mes")){
            cargarGrafKgMes();
        }else if(vista.jListOpciones.getSelectedValue().equals("Precio por mes")){
            cargarGrafPrecioMes();
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

    private void cargarLista() {
        this.modLista.addElement("Distribucion colores");
        this.modLista.addElement("Precio por mes");
        this.modLista.addElement("Kg por mes");
    }

    private boolean isListaSelected() {
        return (vista.jListOpciones.getSelectedIndex() != -1);
    }

    private void cargarGrafKgMes() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        HashMap valores = modeloPlant.estadisticasKgMes(p, 2017);
        
        Iterator it = valores.keySet().iterator();
        String clave = "";
        while(it.hasNext()){
            clave = (String) it.next();
            datosGraf.setValue((int) valores.get(clave),"KG", clave);
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Cantidad(Kg) por mes", "Meses", "Cantidad(Kg)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }

    private void cargarGrafPrecioMes() {
        DefaultCategoryDataset datosGraf = new DefaultCategoryDataset();
        
        Plantacion p = (Plantacion) vista.jComboSubtipo.getSelectedItem();
        HashMap valores = modeloPlant.estadisticasPrecioMes(p, 2017);
        
        Iterator it = valores.keySet().iterator();
        String clave = "";
        while(it.hasNext()){
            clave = (String) it.next();
            datosGraf.setValue((double) valores.get(clave),"Precio(€)", clave);
        }
        
        JFreeChart grafico = ChartFactory.createBarChart("Precio(€/Kg) por mes", "Meses", "Precio(€)", datosGraf);
        
        this.vista.cargarGrafico(grafico);
    }
    
    
}
