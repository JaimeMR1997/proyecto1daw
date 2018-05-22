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
public class VentaDAO {
    public ArrayList<Venta> recuperarTodas(){
        ArrayList<Venta> listaVentas = new ArrayList<Venta>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM VENTA");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCompra = rs.getDate("F_COMPRA").toLocalDate();
                LocalDate fFin = rs.getDate("F_FIN").toLocalDate();
                listaVentas.add(new Venta(rs.getString("ID_VENTA"), rs.getInt("KG"),
                        rs.getFloat("PRECIO"), rs.getString("TAMANIO"), rs.getString("COLOR"), rs.getDate("FECHA").toLocalDate()));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las ventas: "+e.getMessage());
        }
        return listaVentas;
    }
    
    public void addVenta(Venta v){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO VENTA(ID_VENTA,KG,PRECIO,TAMANIO,COLOR,FECHA) "
                + "VALUES(?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, v.getId());
            st.setInt(2, 0);
            st.setFloat(3, 0);
            st.setString(4, "");
            st.setString(5, "");
            st.setDate(6, Date.valueOf(v.getFecha()));
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar venta: "+e.getMessage());
        }
    }
    
    public void borrarVenta(String id){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE * FROM VENTA WHERE ID_VENTA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar venta: "+e.getMessage());
        }
    }
    
    public void actualizarCampo(String id, String campo, String nuevoValor){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE VENTA SET ?=? WHERE VENTA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, campo);
            st.setString(2, nuevoValor);
            st.setString(3, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar venta: "+e.getMessage());
        }
    }
}
