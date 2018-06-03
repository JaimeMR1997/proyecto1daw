/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class PlantacionDAO {
    public ArrayList<Plantacion> recuperarPorFecha(LocalDate fInicio,LocalDate fFin,String idExp){
        ArrayList<Plantacion> listaPlantaciones = new ArrayList<Plantacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM PLANTACION WHERE"
                    + " ID_EXPLOTACION = ? AND F_INICIO>? AND F_FIN<?");
            st.setString(1, idExp);
            st.setDate(2, Date.valueOf(fInicio));
            st.setDate(3, Date.valueOf(fFin));
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                fInicio = rs.getDate("F_INICIO").toLocalDate();
                
                fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                
                listaPlantaciones.add(new Plantacion(rs.getString("ID_PLANT"), rs.getString("TIPO"), 
                        rs.getString("VARIEDAD"), fInicio, fFin, rs.getString("ID_EXPLOTACION")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta plantaciones por explotacion: "+e.getMessage());
        }
        return listaPlantaciones;
    }
            
    public ArrayList<Plantacion> recuperarPorExp(String idExp){
        ArrayList<Plantacion> listaPlantaciones = new ArrayList<Plantacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM PLANTACION WHERE ID_EXPLOTACION = ?");
            st.setString(1, idExp);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fInicio = rs.getDate("F_INICIO").toLocalDate();
                
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                
                listaPlantaciones.add(new Plantacion(rs.getString("ID_PLANT"), rs.getString("TIPO"), 
                        rs.getString("VARIEDAD"), fInicio, fFin, rs.getString("ID_EXPLOTACION")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta plantaciones por explotacion: "+e.getMessage());
        }
        return listaPlantaciones;
    }
    
    public ArrayList<Plantacion> recuperarTodas(){
        ArrayList<Plantacion> listaPlantaciones = new ArrayList<Plantacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM PLANTACION");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fInicio = rs.getDate("F_INICIO").toLocalDate();
                LocalDate fFin = rs.getDate("F_FIN").toLocalDate();
                listaPlantaciones.add(new Plantacion(rs.getString("ID_PLANT"), rs.getString("TIPO"), 
                        rs.getString("VARIEDAD"), fInicio, fFin, rs.getString("ID_EXPLOTACION")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las plantaciones: "+e.getMessage());
        }
        return listaPlantaciones;
    }
    
    public boolean addPlantacion(Plantacion p){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO PLANTACION(ID_PLANT,TIPO,VARIEDAD,F_INICIO,F_FIN,ID_EXPLOTACION) "
                + "VALUES(?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, p.getId());
            st.setString(2, p.getTipo());
            st.setString(3, p.getVariedad());
            st.setDate(4, Date.valueOf(p.getfInicio()));
            
            if(p.getfFin() != null){
                st.setDate(5, Date.valueOf(p.getfFin()));
            }else{
                 st.setDate(5, null);
            }
            
            st.setString(6, p.getIdExplotacion());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar plantacion: "+e.getMessage());
            res=false;
        }
        return res;
    }

    public void borrarExplotacion(String id){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE * FROM PLANTACION WHERE ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar plantacion: "+e.getMessage());
        }
    }
    
    public boolean actualizarCampo(String id, String campo, String nuevoValor){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE PLANTACION SET ?=? WHERE ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, campo);
            st.setString(2, nuevoValor);
            st.setString(3, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar plantacion: "+e.getMessage());
            res=false;
        }
        return res;
    }

    public int contarPlant(String idExplotacion) {
        int res = -1;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT COUNT(*) AS NUM FROM PLANTACION WHERE ID_EXPLOTACION = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, idExplotacion);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                res = rs.getInt("NUM")+1;
            }
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Contar plantaciones: "+e.getMessage());
        }
        return res;
    }
}
