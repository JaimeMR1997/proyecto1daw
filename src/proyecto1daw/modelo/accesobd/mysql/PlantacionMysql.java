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
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Plantacion;

/**
 *
 * @author Jaime
 */
public class PlantacionMysql extends PlantacionDAO {

    /**
     *
     * @param fInicio Fecha minima en al que se planto. Busca plantaciones plantadas
     * a partir de esta fecha, incluyendo esta.
     * @param fFin Fecha maxima en al que se planto. Busca plantaciones plantadas
     * antes de esta fecha, incluyendo esta.
     * @param idExp Id de la explotacin en la que hay que buscar las plantaciones
     * @return Devuelve un ArrayList de objetos de tipo Plantacion con
     * objetos Plantacion comprendidos entre las fechas proporcionadas
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
     * @param fFin Fecha maxima en al que se planto. Busca plantaciones plantadas
     * antes de esta fecha, incluyendo esta.
     * @param idExp Id de la explotacin en la que hay que buscar las plantaciones
     * @return Devuelve un ArrayList de objetos de tipo Plantacion con
     * objetos Plantacion comprendidos entre las fechas proporcionadas
     */
    
    public ArrayList<Plantacion> recuperarPorFechaFin(LocalDate fFin,String idExp){
        ArrayList<Plantacion> listaPlantaciones = new ArrayList<Plantacion>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM PLANTACION "
                    + "WHERE ID_EXPLOTACION = ? AND (F_FIN <= ? OR F_FIN IS NULL)");
            st.setString(1, idExp);
            if(fFin != null){
                st.setDate(2, Date.valueOf(fFin));
            }else{
                st.setDate(2, null);
            }
            
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fInicio = rs.getDate("F_INICIO").toLocalDate();
                
                fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                
                listaPlantaciones.add(new Plantacion(rs.getString("ID_PLANT"), rs.getString("TIPO"), 
                        rs.getString("VARIEDAD"), fInicio, fFin, rs.getString("ID_EXPLOTACION")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta plantaciones por fecha fin: "+e.getMessage());
        }
        return listaPlantaciones;
    }
    
    /**
     *
     * @param idPlant Id de pla plantacion por la que buscar
     * @return Un objeto Plantacion con los datos del id proporcionado o un objeto
     * Plantacion null si no conincide con nada en la BD.
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
     * @param idExp Id de la explotacin de la que se van a bsucar las plantaciones
     * @return Un ArrayList de objetos Plantaciñon con todas las plantaciones de
     * la explotacin
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
     * @return Devuelve un ArrayList de objetos Plantacion con todas las plantaciones
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
     * @param p Plantacion para añadir a la BD
     * @return true si se añade correctamente y false si ha habido algun error
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
     * @param id Id de la plantacin a borrar
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
     * @param id Id de la plantacion a actualizar
     * @param campo Campo de  la tabla que se va a actualizar
     * @param nuevoValor Valor nuevo que va a tomar el campo
     * @return True si se ejecuta correctamente y false si ha habido un error
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
     * @param idExplotacion Id de la explotacion que se van a contar sus plantaciones
     * @return El numero de plantaciones que tiene la explotacion pasada por parametro
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
     * @param idExplotacion Id de la explotacion que se va a buscar si tiene 
     * plantaciones activas
     * @return Devuelve true si hay plantaciones activas y false si no las hay
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
    
    /**
     * 
     * @param p Plantacion de la que se va a recuperar las estadisticas de
     * distribucin del color en sus ventas
     * @return De vuelve un hashmap con los colores como clave y los kg por color
     * como valor
     */
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
    
    /**
     * 
     * @param p Plantacion de la que recuperar el precio medio por meses
     * @param anio Año del que se van a buscar las ventas
     * @return Devuelve un hasmap con los meses como clave y como valor el
     * precio medio cada mes
     */
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
                String mes = Fechas.mesIntToString(fInicio.getMonthValue());
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
    /**
     * 
     * @param p Plantacion de la que se van a buscar los kg 
     * @param anio año en el que se van a buscar las ventas
     * @return Un hashmap con los meses como clave y como valor la produccion
     * de la plantacin or cada mes
     */
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
                String mes = Fechas.mesIntToString(fInicio.getMonthValue());
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

    /**
     * 
     * @param p Objeto Plantacion de la que recuperar las estadísticas
     * @param anios El numero de años hacia atrás en los que se buscarán datos
     * @return Devuelve un objeto LinkedHashMap con los años como clave
     * y como valor el numero de kg de la plantacion de todo el año
     */
    public LinkedHashMap estadisticasKgAnio(Plantacion p, int anios) {
        LinkedHashMap res = new LinkedHashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        for (int i = 0; i < anios; i++) {
            
        
            String consulta = "SELECT SUM(KG) FROM VENTA WHERE ID_PLANT = ?"
                    + " AND FECHA>= ? AND FECHA<?";
            
            int anio = LocalDate.now().getYear() -i;
            
            LocalDate fInicio = LocalDate.of(anio, 1, 1);
            LocalDate fFin = LocalDate.of(anio+1, 1, 1);
            try{
                PreparedStatement st = accesoBD.prepareStatement(consulta);
                st.setString(1, p.getId());
                st.setDate(2, Date.valueOf(fInicio));
                st.setDate(3, Date.valueOf(fFin));
                ResultSet rs = st.executeQuery();
                int cantidad = 0;
                if(rs.next()){
                    cantidad = rs.getInt(1);
                    res.put(anio, cantidad);
                }

            }catch(SQLException e){
                System.out.println("Excepcion SQL. Plantacion estadisticas kg/año: "+e.getMessage());
            }
        }
        try {
            accesoBD.close();
        } catch (SQLException e) {
            System.out.println("Excepcion SQL. Plantacion estadisticas kg/año: "+e.getMessage());
        }
        return res;
    }

    /**
     * @param p La plantacion de la que se recuperarán las ventas
     * @param anio El año en el qeu se buscaran als ventas
     * @return Devuelve un LinkedHashMap con el mes como clave y como resultado otro
     * LinkedHashMap con el color como cave y el precio medio como valor.
     * Enero -> {(Color ->0.324),(Verde ->0.6)}
     */
    public LinkedHashMap estadisticasPrecioColorMes(Plantacion p, int anio) {
        LinkedHashMap res = new LinkedHashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        for (int i = 1; i <= 12; i++) {
            
        
            String consulta = "SELECT AVG(PRECIO),COLOR FROM VENTA WHERE ID_PLANT = ? "
                    + "AND FECHA>= ? AND FECHA<? AND COLOR <> 'RETENCION' GROUP BY COLOR";
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
                String mes = Fechas.mesIntToString(fInicio.getMonthValue());
                String color = "";
                LinkedHashMap parejaPrecioColor = new LinkedHashMap();
                
                while(rs.next()){
                     precio = rs.getDouble(1);
                     color = rs.getString(2);
                     
                     parejaPrecioColor.put(color, precio);
                     res.put(mes, parejaPrecioColor);
                }

            }catch(SQLException e){
                System.out.println("Excepcion SQL. Plantacion estadisticas precio medio mes color: "+e.getMessage());
            }
        }
        try {
            accesoBD.close();
        } catch (SQLException e) {
            System.out.println("Excepcion SQL. Plantacion estadisticas precio medio mes color: "+e.getMessage());
        }
        
        return res;
    }

    public LinkedHashMap estadisticasKgColorMes(Plantacion p, int anio) {
        LinkedHashMap res = new LinkedHashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        for (int i = 1; i <= 12; i++) {
            
        
            String consulta = "SELECT SUM(KG),COLOR FROM VENTA WHERE ID_PLANT = ? "
                    + "AND FECHA>= ? AND FECHA<? AND COLOR <> 'RETENCION' GROUP BY COLOR";
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
                String mes = Fechas.mesIntToString(fInicio.getMonthValue());
                String color = "";
                LinkedHashMap parejaPrecioColor = new LinkedHashMap();
                
                while(rs.next()){
                     precio = rs.getDouble(1);
                     color = rs.getString(2);
                     
                     parejaPrecioColor.put(color, precio);
                     res.put(mes, parejaPrecioColor);
                }

            }catch(SQLException e){
                System.out.println("Excepcion SQL. Plantacion estadisticas kg mes color: "+e.getMessage());
            }
        }
        try {
            accesoBD.close();
        } catch (SQLException e) {
            System.out.println("Excepcion SQL. Plantacion estadisticas kg mes color: "+e.getMessage());
        }
        
        return res;
    }

    public LinkedHashMap estadisticasKgColorQuincena(Plantacion p, int anio) {
        LinkedHashMap res = new LinkedHashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        
        
        /*
        this.modLista.addElement(Fechas.toString(LocalDate.of(anio, i, 1))+" - "+Fechas.toString(LocalDate.of(anio, i, 15)));
        this.modLista.addElement(Fechas.toString(LocalDate.of(anio, i, 16))+" - "
                    +Fechas.toString(LocalDate.of(anio, i, 1).plusMonths(1).minusDays(1)));
        */
        
        int j = 1;
        for (int i = 1; i <= 24; i++) {
            
        
            String consulta = "SELECT SUM(KG),COLOR FROM VENTA WHERE ID_PLANT = ? "
                    + "AND FECHA>= ? AND FECHA<? AND COLOR <> 'RETENCION' GROUP BY COLOR";
            LocalDate fInicio = null;
            LocalDate fFin = null;
            if(i%2!=0){//Es primera quincena
                fInicio = LocalDate.of(anio, j, 1);
                fFin = LocalDate.of(anio, j, 16);
                
            }else{//Es segunda quincena
                fInicio = LocalDate.of(anio, j, 16);
                fFin = LocalDate.of(anio, j, 1).plusMonths(1);
            }
            try{
                PreparedStatement st = accesoBD.prepareStatement(consulta);
                st.setString(1, p.getId());
                st.setDate(2, Date.valueOf(fInicio));
                st.setDate(3, Date.valueOf(fFin));
                ResultSet rs = st.executeQuery();
                double precio = 0;
                String quincena = Fechas.mesIntToString(fInicio.getMonthValue());
             
                if(i%2!=0){//Es primera quincena
                    quincena = "1ºQ "+quincena;
                }else{//Es segunda quincena
                    quincena = "2ºQ "+quincena;
                }
                
                String color = "";
                LinkedHashMap parejaPrecioColor = new LinkedHashMap();
                
                while(rs.next()){
                     precio = rs.getDouble(1);
                     color = rs.getString(2);
                     
                     parejaPrecioColor.put(color, precio);
                     res.put(quincena, parejaPrecioColor);
                }

            }catch(SQLException e){
                System.out.println("Excepcion SQL. Plantacion estadisticas kg quincena color: "+e.getMessage());
            }
        
            if(i%2==0){//Cuando i es par es la segunda quincena del mes
                //Aumentamos j para  que cambie de mes
                j++;
            }
        
        }
        try {
            accesoBD.close();
        } catch (SQLException e) {
            System.out.println("Excepcion SQL. Plantacion estadisticas kg quincena color: "+e.getMessage());
        }
        
        return res;
    }

    public LinkedHashMap estadisticasKgQuincena(Plantacion p, int anio) {
        LinkedHashMap res = new LinkedHashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        
        int j = 1;
        for (int i = 1; i <= 24; i++) {
            
        
            String consulta = "SELECT SUM(KG) FROM VENTA WHERE ID_PLANT = ?"
                    + " AND FECHA>= ? AND FECHA<?";
            LocalDate fInicio = null;
            LocalDate fFin = null;
            
            if(i%2!=0){//Es primera quincena
                fInicio = LocalDate.of(anio, j, 1);
                fFin = LocalDate.of(anio, j, 16);
                
            }else{//Es segunda quincena
                fInicio = LocalDate.of(anio, j, 16);
                fFin = LocalDate.of(anio, j, 1).plusMonths(1);
            }
            
            try{
                PreparedStatement st = accesoBD.prepareStatement(consulta);
                st.setString(1, p.getId());
                st.setDate(2, Date.valueOf(fInicio));
                st.setDate(3, Date.valueOf(fFin));
                ResultSet rs = st.executeQuery();
                
                String quincena = Fechas.mesIntToStringAbrv(fInicio.getMonthValue());
                
                if(i%2!=0){//Es primera quincena
                    quincena = "1"+quincena;
                }else{//Es segunda quincena
                    quincena = "2"+quincena;
                }
                
                int cantidad = 0;
                if(rs.next()){
                    cantidad = rs.getInt(1);
                    if(cantidad!=0) res.put(quincena, cantidad);
                }

            }catch(SQLException e){
                System.out.println("Excepcion SQL. Plantacion estadisticas kg/quincena: "+e.getMessage());
            }
            
            if(i%2==0){//Cuando i es par es la segunda quincena del mes
                //Aumentamos j para  que cambie de mes
                j++;
            }
        }
        try {
            accesoBD.close();
        } catch (SQLException e) {
            System.out.println("Excepcion SQL. Plantacion estadisticas kg/quincena: "+e.getMessage());
        }
        return res;
    }

    public LinkedHashMap estadisticasPrecioColorQuincena(Plantacion p, int anio) {
        LinkedHashMap res = new LinkedHashMap();
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        
        int j = 1;
        for (int i = 1; i <= 24; i++) {
            
        
            String consulta = "SELECT AVG(PRECIO),COLOR FROM VENTA WHERE ID_PLANT = ? "
                    + "AND FECHA>= ? AND FECHA<? AND COLOR <> 'RETENCION' GROUP BY COLOR";
            LocalDate fInicio = null;
            LocalDate fFin = null;
            
            if(i%2!=0){//Es primera quincena
                fInicio = LocalDate.of(anio, j, 1);
                fFin = LocalDate.of(anio, j, 16);
                
            }else{//Es segunda quincena
                fInicio = LocalDate.of(anio, j, 16);
                fFin = LocalDate.of(anio, j, 1).plusMonths(1);
            }
            
            try{
                PreparedStatement st = accesoBD.prepareStatement(consulta);
                st.setString(1, p.getId());
                st.setDate(2, Date.valueOf(fInicio));
                st.setDate(3, Date.valueOf(fFin));
                ResultSet rs = st.executeQuery();
                double precio = 0;
                
                String quincena = Fechas.mesIntToStringAbrv(fInicio.getMonthValue());
                if(i%2!=0){//Es primera quincena
                    quincena = "1Q "+quincena;
                }else{//Es segunda quincena
                    quincena = "2Q "+quincena;
                }
                
                String color = "";
                LinkedHashMap parejaPrecioColor = new LinkedHashMap();
                
                while(rs.next()){
                     precio = rs.getDouble(1);
                     color = rs.getString(2);
                     
                     parejaPrecioColor.put(color, precio);
                     res.put(quincena, parejaPrecioColor);
                }

            }catch(SQLException e){
                System.out.println("Excepcion SQL. Plantacion estadisticas precio medio quincena color: "+e.getMessage());
            }
            
            if(i%2==0){//Cuando i es par es la segunda quincena del mes
                //Aumentamos j para  que cambie de mes
                j++;
            }
            
        }
        try {
            accesoBD.close();
        } catch (SQLException e) {
            System.out.println("Excepcion SQL. Plantacion estadisticas precio medio quincena color: "+e.getMessage());
        }
        
        return res;
    }
}
