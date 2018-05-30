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
                listaVentas.add(new Venta(rs.getString("ID_VENTA"), rs.getInt("KG"),
                        rs.getFloat("PRECIO"), rs.getString("TAMANIO"), rs.getString("COLOR"), rs.getDate("FECHA").toLocalDate(),rs.getString("ID_PLANT")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las ventas: "+e.getMessage());
        }
        return listaVentas;
    }
    
    public boolean addVenta(Venta v){
        boolean res = true;
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
            res = false;
        }
        return res;
    }
    
    public void borrarVenta(String id,String idPlant){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE * FROM VENTA WHERE ID_VENTA = ? AND ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.setString(2, idPlant);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar venta: "+e.getMessage());
        }
    }
    
    public void actualizarCampo(String id, String idPlant, String campo, String nuevoValor){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE VENTA SET ?=? WHERE ID_VENTA = ? AND ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, campo);
            st.setString(2, idPlant);
            st.setString(3, nuevoValor);
            st.setString(4, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar venta: "+e.getMessage());
        }
    }
    
    public String buscar(String idVenta, String idPlant, String campo){
        String s=null;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT ? FROM VENTA WHERE ID_VENTA = ? AND ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, campo);
            st.setString(2, idVenta);
            st.setString(3, idPlant);
            
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                s = rs.getString(1);
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Buscar venta: "+e.getMessage());
        }
        return s;
    }
}
