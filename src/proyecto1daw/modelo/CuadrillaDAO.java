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
public class CuadrillaDAO {
    public ArrayList<Cuadrilla> recuperarTodas(){
        ArrayList<Cuadrilla> listaCuadrillas = new ArrayList<Cuadrilla>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM CUADRILLA");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCompra = rs.getDate("F_COMPRA").toLocalDate();
                LocalDate fFin = rs.getDate("F_FIN").toLocalDate();
                listaCuadrillas.add(new Cuadrilla(rs.getString(1), fFin, fFin));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las cuadrillas: "+e.getMessage());
        }
        return listaCuadrillas;
    }
    
    public void addCuadrilla(Cuadrilla cuad){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO CUADRILLA(ID_CUADRILLA,F_CREACION,F_FIN) "
                + "VALUES(?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, cuad.getId());
            st.setDate(4, Date.valueOf(cuad.getfInicio()));
            st.setDate(5, Date.valueOf(cuad.getfFin()));
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar cuadrilla: "+e.getMessage());
        }
    }
    
    public void borrarCuadrilla(String id){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE * FROM CUADRILLA WHERE ID_CUADRILLA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar cuadrilla: "+e.getMessage());
        }
    }
    
    public void actualizarCampo(String id, String campo, String nuevoValor){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE CUADRILLA SET ?=? WHERE ID_CUADRILLA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, campo);
            st.setString(2, nuevoValor);
            st.setString(3, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar cuadrilla: "+e.getMessage());
        }
    }
}
