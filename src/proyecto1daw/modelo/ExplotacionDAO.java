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
public class ExplotacionDAO {
    public ArrayList<Explotacion> recuperarTodas(){
        ArrayList<Explotacion> listaExplotaciones = new ArrayList<Explotacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM EXPLOTACION");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCreacion = rs.getDate("F_CREACION").toLocalDate();
                LocalDate fFin = rs.getDate("F_FIN").toLocalDate();
                listaExplotaciones.add(new Explotacion(rs.getString("ID_EXPLOTACION"), rs.getInt("SUPERFICIE"),
                        rs.getString("TIPO"),fCreacion, fFin,rs.getString("ID_FINCA")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las explotaciones: "+e.getMessage());
        }
        return listaExplotaciones;
    }
    
    public boolean addExplotacion(Explotacion exp){
        boolean res=true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO EXPLOTACION(ID_EXPLOTACION,SUPERFICIE,TIPO,F_CREACION,F_FIN,ID_FINCA) "
                + "VALUES(?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, exp.getId());
            st.setInt(2, exp.getSuperficie());
            st.setString(3, exp.getTipo());
            st.setDate(4, Date.valueOf(exp.getfCreacion()));
            //st.setDate(5, Date.valueOf(exp.getfFin()));
            st.setDate(5, null);
            st.setString(6, exp.getIdFinca());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar explotacion: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public void borrarExplotacion(String id){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE FROM EXPLOTACION WHERE ID_EXPLOTACION = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar explotacion: "+e.getMessage());
        }
    }

    public void actualizarCampo(String id, String campo, String nuevoValor){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE EXPLOTACION SET ?=? WHERE ID_EXPLOTACION = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, campo);
            st.setString(2, nuevoValor);
            st.setString(3, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar explotacion: "+e.getMessage());
        }
    }
    
    public ArrayList<Explotacion> recuperarPorFinca(String idFinca){
        ArrayList<Explotacion> listaExplotaciones = new ArrayList<Explotacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM EXPLOTACION WHERE ID_FINCA = ?");
            st.setString(1, idFinca);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCreacion = rs.getDate("F_CREACION").toLocalDate();
                LocalDate fFin = null;
                if (rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                listaExplotaciones.add(new Explotacion(rs.getString("ID_EXPLOTACION"), rs.getInt("SUPERFICIE"),
                        rs.getString("TIPO"),fCreacion, fFin,rs.getString("ID_FINCA")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta explotaciones por IdFinca: "+e.getMessage());
        }
        return listaExplotaciones;
    }
    
}