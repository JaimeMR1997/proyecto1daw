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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto1daw.modelo.ExplotacionDAO;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.FincaDAO;
import proyecto1daw.modelo.Finca;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.TrabajadorDAO;
import proyecto1daw.vistas.JFExplotacion;
import proyecto1daw.vistas.JFExplotacionAdd;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author alumno
 */
public class ControladorFinca implements ActionListener{
    private JFFinca vistaTabla;
    private JFFincaAdd vistaAdd;
    private FincaDAO modelo;
    private DefaultTableModel modTabla;
    private DefaultListModel modListaAdd;
    private TrabajadorDAO modeloTrab;

    public ControladorFinca(JFFinca vistaTabla, FincaDAO modelo,JFFincaAdd vistaAddFinca) {
        this.vistaTabla = vistaTabla;
        this.modelo = modelo;
        this.vistaAdd=vistaAddFinca;
        this.vistaTabla.setLocationRelativeTo(null);
        this.vistaAdd.setLocationRelativeTo(null);
        this.modeloTrab = new TrabajadorDAO();
        
        //Asignar action listener a botones
        this.vistaTabla.botonAdd.addActionListener(this);
        this.vistaTabla.botonEliminar.addActionListener(this);
        this.vistaTabla.botonGestionar.addActionListener(this);
        this.vistaTabla.botonInfo.addActionListener(this);
        this.vistaTabla.botonMod.addActionListener(this);
        this.vistaTabla.botonVolver.addActionListener(this);
        //Los de AddFinca tambien
        this.vistaAdd.botonAceptar.addActionListener(this);
        this.vistaAdd.botonCancelar.addActionListener(this);
        this.vistaAdd.jRadioButtonEmp.addActionListener(this);
        this.vistaAdd.jRadioButtonEnc.addActionListener(this);
        this.vistaAdd.jRadioButtonTract.addActionListener(this);
        
        //Cargar datos en tabla
        modTabla=new DefaultTableModel();
        modTabla.addColumn("ID");
        modTabla.addColumn("Localización");
        modTabla.addColumn("Superficie");
        modTabla.addColumn("Fecha de Compra");
        modTabla.addColumn("Encargado");
        modTabla.addColumn("NºTractores");
        modTabla.addColumn("NºExplotaciones");
        rellenarTabla(modelo.recuperarTodas());
        this.vistaTabla.jTableFincas.setModel(modTabla);
        
        //Modelo JlistTrabajadores
        modListaAdd= new DefaultListModel();
        this.vistaAdd.jListEmpleados.setModel(modListaAdd);
        
        this.vistaTabla.setVisible(true);
        vistaAddFinca.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void rellenarLista(ArrayList<Trabajador> listaTrabajadores){ //Cargar trabajador en lista
        for (Trabajador t : listaTrabajadores) {
            modListaAdd.addElement(t.getDni()+": "+t.getNombre()+" "+t.getApellidos());
        }
    }
    
    public void rellenarTabla(ArrayList<Finca> listaFincas){
        String[] fila = new String[7];
        for (Finca s : listaFincas) {
            fila[0]=s.getId();
            fila[1]=s.getLocalidad();
            fila[2]=s.getSuperficie()+"";
            fila[3]=Fechas.parseLocalDtoString(s.getfCompra());
            fila[4]=s.getEncargado();
            fila[5]="";//NUM TRACTORES
            fila[6]="";//NUM EXPLOTACIONES
            modTabla.addRow(fila);
        }
        
    }
    
    public boolean validarDatosAdd(){
        boolean res=true;
        if(vistaAdd.campoId.getText().equals("") || vistaAdd.campoId == null){
            res=false;
        }
        if(vistaAdd.campoLocalidad.getText().equals("") || vistaAdd.campoLocalidad == null){
            res=false;
        }
        if(vistaAdd.campoSuperficie.getText().equals("") || vistaAdd.campoSuperficie == null){
            res=false;
        }else{
            try{
                int num=Integer.parseInt(vistaAdd.campoSuperficie.getText());
                if(num<0){
                    res=false;
                }
            }catch(NumberFormatException e){
                res=false;
            }
        }
        if(vistaAdd.campoFechaC.getText().equals("") || vistaAdd.campoFechaC == null){
            res=false;
        }else if(Fechas.parseStringtoLocalDate(vistaAdd.campoFechaC.getText()) == null){
            res=false;
        }
        return res;
    }
    
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(vistaTabla.botonVolver)){                  //VOLVER
            //Volver a Inicio
            ControladorInicio conInicio = new ControladorInicio(new JFInicio());
            this.vistaTabla.dispose();
        }else if(ae.getSource().equals(vistaTabla.botonGestionar)){         //GESTIONAR
            //Abrir ventana gestion fincva seleccionada
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                //Abre explotaciones de esa Finca
                int fila = this.vistaTabla.jTableFincas.getSelectedRow();
                String finca = (String) this.vistaTabla.jTableFincas.getValueAt(fila, 0);
                this.vistaTabla.dispose();
                ControladorExplotacion conExplotacion = new ControladorExplotacion(new JFExplotacion(), new ExplotacionDAO(), new JFExplotacionAdd(),finca);
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getSource().equals(vistaTabla.botonAdd)){               //ABRIR AÑADIR
            //Llamar a ventanaAñadir
            vistaAdd.botonAceptar.setText("Aceptar");
            this.vistaAdd.campoId.setEnabled(true);
            vistaAdd.setVisible(true);
            vistaAdd.setAlwaysOnTop(true);
                    
        }else if(ae.getSource().equals(vistaTabla.botonMod)){               //MODIFICAR
            //Llamar ventanaAñadir con datos rellenos
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                int filaSelec=this.vistaTabla.jTableFincas.getSelectedRow();
                String id=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,0);
                String localizacion=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,1);
                String superficie=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,2);
                String fCreacion=(String) this.vistaTabla.jTableFincas.getValueAt(filaSelec,3);
                this.vistaAdd.campoId.setText(id);
                this.vistaAdd.campoId.setEnabled(false);
                this.vistaAdd.campoLocalidad.setText(localizacion);
                this.vistaAdd.campoSuperficie.setText(superficie);
                this.vistaAdd.campoFechaC.setText(fCreacion);
                
                vistaAdd.botonAceptar.setText("Modificar");
                vistaAdd.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
            
        }else if(ae.getSource().equals(vistaTabla.botonInfo)){              //INFORMACION
            System.out.println("Informacion: AUN EN DESARROLLO");
            //Sustituir por buscar??
        }else if(ae.getSource().equals(vistaTabla.botonEliminar)){          //ELIMINAR
            
            //Preguntar antes de eliminar completamente
            if(this.vistaTabla.jTableFincas.getSelectedRow() != -1){
                int b= JOptionPane.showConfirmDialog(vistaAdd, "¿Estás seguro de eliminar esta finca?");
                if(b==JOptionPane.YES_OPTION){
                    String idFinca=(String) this.vistaTabla.jTableFincas.getValueAt(this.vistaTabla.jTableFincas.getSelectedRow(),0);
                    modelo.borrarFinca(idFinca);
                    
                    this.limpiarCamposAdd();
                    this.vistaAdd.dispose();
                    this.modTabla.setRowCount(0);
                    this.rellenarTabla(modelo.recuperarTodas());
                }
            }else{
                JOptionPane.showMessageDialog(vistaTabla, "Necesitas seleccionar una finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getSource().equals(vistaAdd.botonAceptar)){                 //ACEPTAR_AÑADIR
            String nombreBoton=((JButton)ae.getSource()).getText();
            if(nombreBoton.equalsIgnoreCase("Aceptar") && this.validarDatosAdd()){  //NUEVA FINCA
                String id = this.vistaAdd.campoId.getText(); //Recupera campos
                String localidad = this.vistaAdd.campoLocalidad.getText();
                int superficie = Integer.parseInt(this.vistaAdd.campoSuperficie.getText());
                LocalDate fCreacion = Fechas.parseStringtoLocalDate(this.vistaAdd.campoFechaC.getText());
                
                Finca f = new Finca(id, localidad, superficie, fCreacion, null);
                if(modelo.addFinca(f)){
                    JOptionPane.showMessageDialog(vistaAdd, "Finca añadida correctamente");
                }else{
                    JOptionPane.showMessageDialog(vistaTabla, "Error al añadir la finca", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }
                
                if(this.vistaAdd.jListEmpleados.getSelectedValue() != null){
                    String dni=this.vistaAdd.jListEmpleados.getSelectedValue(); //Coge el DNI
                    dni=dni.substring(0, dni.indexOf(":"));
                    Trabajador t = new Trabajador(dni);
                    modeloTrab.asignarEncargado(dni, f.getId());
                }
                
                this.limpiarCamposAdd();
                this.vistaAdd.dispose();
                this.modTabla.setRowCount(0);
                this.rellenarTabla(modelo.recuperarTodas());
            }else if(nombreBoton.equalsIgnoreCase("Modificar") && this.validarDatosAdd()){ //MODIFICAR FINCA
                
            }
            
        }else if(ae.getSource().equals(vistaAdd.botonCancelar)){                 //CANCELAR
            limpiarCamposAdd();
            this.vistaAdd.dispose();
            
        }else if(ae.getSource().equals(vistaAdd.jRadioButtonEmp)){                 //RADIO TRABAJADOR
            //Cargar en Jlist Empleados
            modListaAdd.clear();
            this.rellenarLista(modeloTrab.recuperarTrabajadores());
            //Si se selecciona uno se cambia al tipo Encargado
        }else if(ae.getSource().equals(vistaAdd.jRadioButtonEnc)){                //RADIO ENCARGADO
            //Cargar en Jlist Encargados
            modListaAdd.clear();
            ArrayList<Trabajador> lista = new ArrayList<Trabajador>();
            lista.addAll(modeloTrab.recuperarEncargados());
            this.rellenarLista(lista);
            
        }else if(ae.getSource().equals(vistaAdd.jRadioButtonTract)){            //RADIO TRACTORISTA
            //Cargar en Jlist Tractoristas
            modListaAdd.clear();
            ArrayList<Trabajador> lista = new ArrayList<Trabajador>();
            lista.addAll(modeloTrab.recuperarConductores());
            this.rellenarLista(lista);
            
        }
    }

    private void limpiarCamposAdd() {
        //CANCELAR_AÑADIR
        this.vistaAdd.campoId.setText("");
        this.vistaAdd.campoLocalidad.setText("");
        this.vistaAdd.campoSuperficie.setText("");
        this.vistaAdd.campoFechaC.setText("");
        modListaAdd.clear();
    }
    
}
