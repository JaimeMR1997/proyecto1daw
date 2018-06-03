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
    public ArrayList<Venta> recuperarPorFecha(LocalDate fecha,String idPlant){
        ArrayList<Venta> listaVentas = new ArrayList<Venta>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM VENTA WHERE ID_PLANT = ? AND FECHA=?");
            st.setString(1, idPlant);
            st.setDate(2, Date.valueOf(fecha));
            
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
    
    
    public ArrayList<Venta> recuperarPorPlant(String idPlant){
        ArrayList<Venta> listaVentas = new ArrayList<Venta>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM VENTA WHERE ID_PLANT = ?");
            st.setString(1, idPlant);
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
        String consulta = "INSERT INTO VENTA(ID_VENTA,KG,PRECIO,TAMANIO,COLOR,FECHA,ID_PLANT) "
                + "VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, v.getId());
            st.setInt(2, v.getKg());
            st.setFloat(3, v.getPrecio());
            st.setString(4, v.getTamanio());
            st.setString(5, v.getColor());
            st.setDate(6, Date.valueOf(v.getFecha()));
            st.setString(7, v.getIdPlantacion());
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
        String consulta = "DELETE FROM VENTA WHERE ID_VENTA = ? AND ID_PLANT = ?";
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
    
    public boolean actualizarCampo(String id, String idPlant, String campo, String nuevoValor){
        boolean res =true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE VENTA SET "+campo+"=? WHERE ID_VENTA = ? AND ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, nuevoValor);
            st.setString(2, id);
            st.setString(3, idPlant);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar venta: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public String buscar(String idVenta, String idPlant, String campo){
        String res=null;
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
                res = rs.getString(1);
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Buscar venta: "+e.getMessage());
        }
        return res;
    }

    public int contarVentas(String idPlant, String fecha) {
        int res = -1;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT COUNT(*) AS NUM FROM VENTA WHERE FECHA = ? AND ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, fecha);
            st.setString(2, idPlant);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                res = rs.getInt("NUM")+1;
            }
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. contar ventas: "+e.getMessage());
        }
        return res;
    }

    public double calcularIngresos(String idPlant) {
        double res = 0;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT INGRESOS FROM INGRESOS_VENTAS WHERE ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, idPlant);
            
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                res += rs.getInt("INGRESOS");
            }
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. calcular ingresos ventas: "+e.getMessage());
        }
        return res;
    }
}
