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
 * @author alumno
 */
public class FincaDAO {
    
    public ArrayList<Finca> recuperarTodas(){
        ArrayList<Finca> listaFincas = new ArrayList<Finca>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM FINCA");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCompra = rs.getDate("F_COMPRA").toLocalDate();
                LocalDate fFin = rs.getDate("F_FIN").toLocalDate();
                listaFincas.add(new Finca(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(1),fCompra, fFin));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las Fincas: "+e.getMessage());
        }
        return listaFincas;
    }
    
    public void addFinca(Finca f){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO FINCA(ID_FINCA,LOCALIDAD,SUPERFICIE,F_COMPRA,F_FIN) "
                + "VALUES(?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, f.getId());
            st.setString(2, f.getLocalidad());
            st.setInt(3, f.getSuperficie());
            st.setDate(4, Date.valueOf(f.getfCompra()));
            st.setDate(5, Date.valueOf(f.getfFin()));
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar Finca: "+e.getMessage());
        }
    }
    
    public void borrarFinca(String id){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE * FROM FINCA WHERE ID_FINCA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar Finca: "+e.getMessage());
        }
    }
    
    public void actualizarCampo(String id, String campo, String nuevoValor){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE FINCA SET ?=? WHERE ID_FINCA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, campo);
            st.setString(2, nuevoValor);
            st.setString(3, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar Finca: "+e.getMessage());
        }
    }
}