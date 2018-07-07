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
import java.time.Month;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class VentaDAO {

    /**
     *
     * @param idVenta
     * @param idPlant
     * @return
     */
    public Venta recuperarPorId(String idVenta,String idPlant){
        Venta v = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM VENTA WHERE ID_PLANT = ? AND ID_VENTA = ?");
            st.setString(1, idPlant);
            st.setString(2, idVenta);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                v = new Venta(rs.getString("ID_VENTA"), rs.getInt("KG"),
                        rs.getFloat("PRECIO"), rs.getString("TAMANIO"), rs.getString("COLOR"), rs.getDate("FECHA").toLocalDate(),rs.getString("ID_PLANT"));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta ventas por ID: "+e.getMessage());
        }
        return v;
    }

    /**
     *
     * @param fecha
     * @param idPlant
     * @return
     */
    public ArrayList<Venta> recuperarPorFecha(LocalDate fecha,String idPlant){
        ArrayList<Venta> listaVentas = new ArrayList<Venta>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            boolean hayFecha = true;
            String consulta = "";
            if(fecha == null){
                consulta = "SELECT * FROM VENTA WHERE ID_PLANT = ?";
                hayFecha = false;
            }else{
                consulta = "SELECT * FROM VENTA WHERE ID_PLANT = ? AND FECHA = ?";
            }
            
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, idPlant);
            if(hayFecha){
                st.setDate(2, Date.valueOf(fecha));
            }
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                listaVentas.add(new Venta(rs.getString("ID_VENTA"), rs.getInt("KG"),
                        rs.getFloat("PRECIO"), rs.getString("TAMANIO"), rs.getString("COLOR"), rs.getDate("FECHA").toLocalDate(),rs.getString("ID_PLANT")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta ventas por fecha: "+e.getMessage());
        }
        return listaVentas;
    }
    
    /**
     *
     * @param idPlant
     * @return
     */
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
    
    /**
     *
     * @return
     */
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
    
    /**
     *
     * @param v
     * @return
     */
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
    
    /**
     *
     * @param id
     * @param idPlant
     * @return
     */
    public boolean borrarVenta(String id,String idPlant){
        boolean res = true;
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
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param idPlant
     * @return
     */
    public boolean borrarVentasPlantacion(String idPlant) {
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE FROM VENTA WHERE ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, idPlant);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar ventas por plantacion: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param id
     * @param idPlant
     * @param campo
     * @param nuevoValor
     * @return
     */
    public boolean actualizarCampo(String id, String idPlant, String campo, String nuevoValor){
        boolean res =true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE VENTA SET "+campo+"= ? WHERE ID_VENTA = ? AND ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, nuevoValor);
            st.setString(2, id);
            st.setString(3, idPlant);
            
            if(campo.equalsIgnoreCase("PRECIO")){
                st.setDouble(1, Double.parseDouble(nuevoValor));
            }
            
            st.executeUpdate();
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar venta: "+e.getMessage());
            e.printStackTrace();
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param idVenta
     * @param idPlant
     * @param campo
     * @return
     */
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

    /**
     *
     * @param idPlant
     * @param fecha
     * @return
     */
    public int contarVentas(String idPlant, LocalDate fecha) {
        int res = -1;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT COUNT(*) AS NUM FROM VENTA WHERE FECHA = ? AND ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setDate(1, Date.valueOf(fecha));
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

    /**
     *
     * @param idPlant
     * @return
     */
    public double calcularIngresos(String idPlant) {
        double res = 0;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT SUM(INGRESOS) AS INGRESOS FROM `INGRESOS_VENTAS` WHERE ID_PLANT = ? GROUP BY ID_PLANT";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, idPlant);
            
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                res = rs.getDouble("INGRESOS");
            }
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. calcular ingresos ventas: "+e.getMessage());
        }
        
        /*
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
        */
        
        return res;
    }

    public double calcularQuincAnt(String idPlant) {
        double res = 0;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT SUM(INGRESOS) AS INGRESOS FROM `INGRESOS_VENTAS`"
                + " WHERE ID_PLANT = ? AND FECHA>= ? AND FECHA< ? GROUP BY ID_PLANT";
        
        LocalDate fIn = null;
        LocalDate fFin = null;
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            
            if(LocalDate.now().getDayOfMonth()>15){
                fIn = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
                //Incluye el 15 pero no el 16
                fFin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 16);
            }else{
                if(LocalDate.now().getMonth() == Month.JANUARY){
                    fIn = LocalDate.of(LocalDate.now().getYear()-1, Month.DECEMBER, 16);
                }else{
                    fIn = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().minusMonths(1).getMonth(), 16);
                }
                fFin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
                
                
            }
            st.setString(1, idPlant);
            st.setDate(2, Date.valueOf(fIn));
            st.setDate(3, Date.valueOf(fFin));
            
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                res = rs.getDouble("INGRESOS");
            }
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. calcular quincena anterior: "+e.getMessage());
        }
        return res;
    }

    public double calcularQuincActual(String idPlant) {
        double res = 0;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT SUM(INGRESOS) AS INGRESOS FROM `INGRESOS_VENTAS`"
                + " WHERE ID_PLANT = ? AND FECHA>= ? AND FECHA< ? GROUP BY ID_PLANT";
        
        LocalDate fIn = null;
        LocalDate fFin = null;
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            
            if(LocalDate.now().getDayOfMonth()<=15){
                fIn = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
                //Incluye el 15 pero no el 16
                fFin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 16);
            }else{
                fIn = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 16);
                //Hasta el dia 1 del mes siguiente sin incluirlo
                if(LocalDate.now().getMonth() == Month.DECEMBER){
                    //Si es diciembre el proximo mes seria enero del año proximo
                    fFin = LocalDate.of(LocalDate.now().getYear()+1, Month.JANUARY, 1);
                }else{
                    fFin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().plusMonths(1).getMonth(), 1);
                }
                
            }
            st.setString(1, idPlant);
            st.setDate(2, Date.valueOf(fIn));
            st.setDate(3, Date.valueOf(fFin));
            
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                res = rs.getDouble("INGRESOS");
            }
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. calcular quincena actual: "+e.getMessage());
        }
        return res;
    }
}
