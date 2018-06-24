/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alumno
 */
public class Conexion {

    /**
     *
     */
    public Conexion() {
    }
    
    /**
     *
     * @return Un objeto Connection con la base de datos especificado en el método
     */
    public Connection getConexion(){
        Connection conn=null;
        Configuracion config = new Configuracion();
        
        if(config.getTipoServer().equalsIgnoreCase("oracle")){
            conn = getConnectionOracle();
        }else if(config.getTipoServer().equalsIgnoreCase("mysql")){
            conn = getConnectionMysql();
        }
        return conn;
    }

    private Connection getConnectionOracle() {
        Connection conn = null;
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            String usuario="jaime";
            String pass="1234";
            String connectionUrl = "jdbc:oracle:thin:"+usuario+"/"+pass+"@localhost:1521:XE";
            conn = DriverManager.getConnection(connectionUrl);
        }catch(ClassNotFoundException e){
            System.out.println("Clase no encontrada: "+e.getMessage());
        }catch(SQLException e){
            System.out.println("Error al obtener conexion oracle: "+e.getMessage());
        }
        return conn;
    }
    
    /**
     *
     * @param conn Cierra la conexion con el objeto Connection pasado
     */
    public void cerrarConexion(Connection conn){
        try{
            conn.close();
        }catch(SQLException e){
            System.out.println("Excepción SQL al cerrar la conexion: "+e.getMessage());
        }
    }

    private Connection getConnectionMysql() {
        Configuracion config = new Configuracion();
        String ip = config.getIP();
        int puerto = Integer.parseInt(config.getPuerto());
        
        Connection conn = null;
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("jaime");
        dataSource.setPassword("1234");
        dataSource.setServerName(ip);
        dataSource.setPort(puerto);
        dataSource.setDatabaseName("agricola");
        
        try{
            dataSource.setUseSSL(false);//Para que no salga aviso
            dataSource.setServerTimezone("UTC");//Si no se especifica da error
            conn = dataSource.getConnection();
        }catch(SQLException e){
            System.out.println("Error al obtener conexion Mysql: "+e);
        }
        return conn;
    }
}
