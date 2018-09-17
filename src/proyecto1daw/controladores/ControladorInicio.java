/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import proyecto1daw.modelo.accesobd.Conexion;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.accesobd.ExplotacionDAO;
import proyecto1daw.modelo.accesobd.FincaDAO;
import proyecto1daw.modelo.accesobd.mysql.ExplotacionMysql;
import proyecto1daw.modelo.accesobd.mysql.FincaMysql;
import proyecto1daw.modelo.accesobd.sqlite.ExplotacionSqlite;
import proyecto1daw.modelo.accesobd.sqlite.FincaSqlite;
import proyecto1daw.vistas.JFConfiguracion;
import proyecto1daw.vistas.JFEmpleados;
import proyecto1daw.vistas.JFFinca;
import proyecto1daw.vistas.JFFincaAdd;
import proyecto1daw.vistas.JFInicio;

/**
 *
 * @author Jaime
 */
public class ControladorInicio implements ActionListener{
    private JFInicio vista;
    private FincaDAO modeloFinca;
    private ExplotacionDAO modeloExp;

    /**
     *
     * @param vista Ventana de tipo JFInicio
     */
    public ControladorInicio(JFInicio vista) {
        this.vista = vista;
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        
        //Asociar listener a botones
        this.vista.botonEstadisticas.addActionListener(this);
        this.vista.botonFincas.addActionListener(this);
        this.vista.botonTrabajadores.addActionListener(this);
        this.vista.botonTractores.addActionListener(this);
        this.vista.botonConfig.addActionListener(this);
        
        //Crear modelo;
        Configuracion config = new Configuracion();
        if(config.getTipoServer().equalsIgnoreCase("mysql") || config.getTipoServer().equalsIgnoreCase("mariadb")){
            this.modeloFinca = new FincaMysql();
            this.modeloExp = new ExplotacionMysql();
        }else{
            this.modeloFinca = new FincaSqlite();
            this.modeloExp = new ExplotacionSqlite();
        }
    }

    /**
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        JButton boton =(JButton)ae.getSource();
        Conexion con = new Conexion();
        if(boton.equals(this.vista.botonConfig)){                        //VENTANA ESTADISTICAS  
                ControladorConfig contConfig = new ControladorConfig(new JFConfiguracion());
        
        }else if(con.getConexion() != null){
            if(boton.equals(this.vista.botonTractores)){                          //VENTANA TRACTORES

            }else if(boton.equals(this.vista.botonFincas)){                       //VENTANA FINCAS
                ControladorFinca conFinca = new ControladorFinca(new JFFinca(), modeloFinca, modeloExp);
                this.vista.dispose();

            }else if(boton.equals(this.vista.botonTrabajadores)){                 //VENTANA TRABAJADORES
                ControladorEmpleado contEmp = new ControladorEmpleado(new JFEmpleados());
                this.vista.dispose();

            }else if(boton.equals(this.vista.botonEstadisticas)){                 //VENTANA ESTADISTICAS  
                ControladorEstadisticas contEst = new ControladorEstadisticas(modeloFinca,modeloExp);
                this.vista.dispose();
            }
            
        }else{
            Configuracion config = new Configuracion();
            String errMsg = "No se ha podido establecer conexión con la base de datos.\n"
                    + "IP:     "+config.getIP()+":"+config.getPuerto();
            JOptionPane.showMessageDialog(vista, errMsg, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
