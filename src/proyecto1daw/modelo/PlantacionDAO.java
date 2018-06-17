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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaime
 */
public class PlantacionDAO {

    /**
     *
     * @param fInicio
     * @param fFin
     * @param idExp
     * @return
     */
    public ArrayList<Plantacion> recuperarPorFecha(LocalDate fInicio,LocalDate fFin,String idExp){
        ArrayList<Plantacion> listaPlantaciones = new ArrayList<Plantacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM PLANTACION "
                    + "WHERE ID_EXPLOTACION = ? AND F_INICIO >= ? "
                    + "AND (F_FIN <= ? OR F_FIN IS NULL)");
            st.setString(1, idExp);
            st.setDate(2, Date.valueOf(fInicio));
            if(fFin != null){
                st.setDate(3, Date.valueOf(fFin));
            }else{
                st.setDate(3, null);
            }
            
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
            System.out.println("Excepcion SQL. Consulta plantaciones por fecha: "+e.getMessage());
        }
        return listaPlantaciones;
    }
    
    /**
     *
     * @param idPlant
     * @return
     */
    public Plantacion recuperarPorId(String idPlant){
        Plantacion plant = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM PLANTACION WHERE ID_PLANT = ?");
            st.setString(1, idPlant);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fInicio = rs.getDate("F_INICIO").toLocalDate();
                
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                
                plant =new Plantacion(rs.getString("ID_PLANT"), rs.getString("TIPO"), 
                        rs.getString("VARIEDAD"), fInicio, fFin, rs.getString("ID_EXPLOTACION"));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta plantaciones por explotacion: "+e.getMessage());
        }
        return plant;
    }
    
    /**
     *
     * @param idExp
     * @return
     */
    public ArrayList<Plantacion> recuperarPorExp(String idExp){
        ArrayList<Plantacion> listaPlantaciones = new ArrayList<Plantacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM PLANTACION WHERE ID_EXPLOTACION = ?");
            st.setString(1, idExp);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fInicio = null;
                if(rs.getDate("F_INICIO") != null){
                    fInicio = rs.getDate("F_INICIO").toLocalDate();
                }
                
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
    
    /**
     *
     * @return
     */
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
    
    /**
     *
     * @param p
     * @return
     */
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

    /**
     *
     * @param id
     */
    public void borrarPlantacion(String id){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE FROM PLANTACION WHERE ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar plantacion: "+e.getMessage());
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
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE PLANTACION SET "+campo+"=? WHERE ID_PLANT = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            
            st.setString(1, nuevoValor);
            st.setString(2, id);
            
            st.executeUpdate();
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar plantacion: "+e.getMessage());
            res=false;
        }
        return res;
    }

    /**
     *
     * @param idExplotacion
     * @return
     */
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

    /**
     *
     * @param idExplotacion
     * @return
     */
    public boolean hayPlantSinFinalizar(String idExplotacion) {
        boolean res = false;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT COUNT(*) FROM PLANTACION WHERE ID_EXPLOTACION = ? AND F_FIN IS NULL";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, idExplotacion);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)>0){
                    //Hay plant sin finalizar
                    res=true;
                }else{
                    //No hay plant sin finalizar
                }
            }
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Comprobar si hay plant sin finalizar: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public HashMap estadisticasColorTotal(Plantacion p) {
        HashMap res = new HashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT COLOR,SUM(KG) FROM VENTA WHERE ID_PLANT = ? GROUP BY COLOR";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, p.getId());
            ResultSet rs = st.executeQuery();
            String color = "";
            int cantidad = 0;
            while(rs.next()){
                 color = rs.getString(1);
                 cantidad = rs.getInt(2);
                 res.put(color, cantidad);
            }
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Plantacion estadisticas color: "+e.getMessage());
        }
        return res;
    }
    
    public LinkedHashMap estadisticasPrecioMes(Plantacion p,int anio) {
        LinkedHashMap res = new LinkedHashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        for (int i = 1; i <= 12; i++) {
            
        
            String consulta = "SELECT AVG(PRECIO) FROM VENTA WHERE ID_PLANT = ?"
                    + " AND FECHA>= ? AND FECHA<?  GROUP BY COLOR";
            LocalDate fInicio = LocalDate.of(anio, i, 1);
            LocalDate fFin = null;
            if(i != 12){
                fFin = LocalDate.of(anio, i+1, 1);
            }else{
                fFin = LocalDate.of(anio+1, 1, 1);
            }
            try{
                PreparedStatement st = accesoBD.prepareStatement(consulta);
                st.setString(1, p.getId());
                st.setDate(2, Date.valueOf(fInicio));
                st.setDate(3, Date.valueOf(fFin));
                ResultSet rs = st.executeQuery();
                double precio = 0;
                String mes = Fechas.mesToString(fInicio.getMonthValue());
                while(rs.next()){
                     precio = rs.getDouble(1);
                     res.put(mes, precio);
                }

            }catch(SQLException e){
                System.out.println("Excepcion SQL. Plantacion estadisticas precio/mes: "+e.getMessage());
            }
        }
        try {
            accesoBD.close();
        } catch (SQLException e) {
            System.out.println("Excepcion SQL. Plantacion estadisticas kg/mes: "+e.getMessage());
        }
        
        return res;
    }
    
    public LinkedHashMap estadisticasKgMes(Plantacion p,int anio) {
        LinkedHashMap res = new LinkedHashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        for (int i = 1; i <= 12; i++) {
            
        
            String consulta = "SELECT SUM(KG) FROM VENTA WHERE ID_PLANT = ?"
                    + " AND FECHA>= ? AND FECHA<?";
            LocalDate fInicio = LocalDate.of(anio, i, 1);
            LocalDate fFin = null;
            if(i != 12){
                fFin = LocalDate.of(anio, i+1, 1);
            }else{
                fFin = LocalDate.of(anio+1, 1, 1);
            }
            try{
                PreparedStatement st = accesoBD.prepareStatement(consulta);
                st.setString(1, p.getId());
                st.setDate(2, Date.valueOf(fInicio));
                st.setDate(3, Date.valueOf(fFin));
                ResultSet rs = st.executeQuery();
                String mes = Fechas.mesToString(fInicio.getMonthValue());
                int cantidad = 0;
                if(rs.next()){
                     cantidad = rs.getInt(1);
                     res.put(mes, cantidad);
                }

            }catch(SQLException e){
                System.out.println("Excepcion SQL. Plantacion estadisticas kg/mes: "+e.getMessage());
            }
        }
        try {
            accesoBD.close();
        } catch (SQLException e) {
            System.out.println("Excepcion SQL. Plantacion estadisticas kg/mes: "+e.getMessage());
        }
        return res;
    }
}
