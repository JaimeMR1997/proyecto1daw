/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo.accesobd.mysql;

import proyecto1daw.modelo.accesobd.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import proyecto1daw.modelo.Explotacion;

/**
 *
 * @author Jaime
 */
public class ExplotacionMysql extends ExplotacionDAO {

    /**
     *
     * @return
     */
    public ArrayList<Explotacion> recuperarTodas(){
        ArrayList<Explotacion> listaExplotaciones = new ArrayList<Explotacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM EXPLOTACION");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCreacion = rs.getDate("F_CREACION").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                listaExplotaciones.add(new Explotacion(rs.getString("ID_EXPLOTACION"), rs.getInt("SUPERFICIE"),
                        rs.getString("TIPO"),fCreacion, fFin,rs.getString("ID_FINCA"),rs.getString("ALIAS")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las explotaciones: "+e.getMessage());
        }
        return listaExplotaciones;
    }
    
    /**
     *
     * @param exp
     * @return
     */
    public boolean addExplotacion(Explotacion exp){
        boolean res=true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO EXPLOTACION(ID_EXPLOTACION,SUPERFICIE,TIPO,F_CREACION,F_FIN,ID_FINCA,ALIAS) "
                + "VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, exp.getId());
            st.setInt(2, exp.getSuperficie());
            st.setString(3, exp.getTipo());
            st.setDate(4, Date.valueOf(exp.getfCreacion()));
            //st.setDate(5, Date.valueOf(exp.getfFin()));
            st.setDate(5, null);
            st.setString(6, exp.getIdFinca());
            st.setString(7,exp.getAlias());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar explotacion: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param id
     */
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

    /**
     *
     * @param id
     * @param campo
     * @param nuevoValor
     * @return
     */
    public boolean actualizarCampo(String id, String campo, String nuevoValor){
        boolean res=true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE EXPLOTACION SET "+campo+"=? WHERE ID_EXPLOTACION = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, nuevoValor);
            st.setString(2, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar explotacion: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param idFinca
     * @return
     */
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
                        rs.getString("TIPO"),fCreacion, fFin,rs.getString("ID_FINCA"),rs.getString("ALIAS")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta explotaciones por IdFinca: "+e.getMessage());
        }
        return listaExplotaciones;
    }
    
    /**
     *
     * @param idFinca
     * @return
     */
    public int contarPorFinca(String idFinca){
        int n = 0;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT COUNT(*) FROM EXPLOTACION WHERE ID_FINCA = ?");
            st.setString(1, idFinca);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                n=rs.getInt(1);
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Contar explotaciones por IdFinca: "+e.getMessage());
            n=-1;
        }
        return n;
    }

    /**
     *
     * @param idExp
     * @return
     */
    public Explotacion recuperarPorId(String idExp) {
        Explotacion exp = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM EXPLOTACION WHERE ID_EXPLOTACION = ?");
            st.setString(1, idExp);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCreacion = rs.getDate("F_CREACION").toLocalDate();
                LocalDate fFin = null;
                if (rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                exp = new Explotacion(rs.getString("ID_EXPLOTACION"), rs.getInt("SUPERFICIE"),
                        rs.getString("TIPO"),fCreacion, fFin,rs.getString("ID_FINCA"),rs.getString("ALIAS"));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta explotacion por IdExp: "+e.getMessage());
        }
        return exp;
    }
    
}
