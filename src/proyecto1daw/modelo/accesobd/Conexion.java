/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo.accesobd;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mariadb.jdbc.MariaDbDataSource;
import proyecto1daw.modelo.Configuracion;

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
        }else if(config.getTipoServer().equalsIgnoreCase("mariadb")){
            conn = getConnectionMariaDb();
        }else if(config.getTipoServer().equalsIgnoreCase("sqlite")){
            conn = getConnectionSqlite();
        }
        return conn;
    }

    private Connection getConnectionOracle() {
        
        
        System.err.println("EL SOPORTE PARA ORACLE NO SE ENCUENTRA ACTUALIZADO");
        
        
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
        String nombreBd = config.getBd();
        
        Connection conn = null;
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("jaime");
        dataSource.setPassword("1234");
        dataSource.setServerName(ip);
        dataSource.setPort(puerto);
        dataSource.setDatabaseName(nombreBd);
        
        try{
            dataSource.setUseSSL(false);//Para que no salga aviso
            dataSource.setServerTimezone("UTC");//Si no se especifica da error
            conn = dataSource.getConnection();
        }catch(SQLException e){
            System.out.println("Error al obtener conexion Mysql: "+e);
        }
        return conn;
    }

    private Connection getConnectionMariaDb() {
        Configuracion config = new Configuracion();
        String ip = config.getIP();
        int puerto = Integer.parseInt(config.getPuerto());
        String nombreBd = config.getBd();
        
        Connection conn = null;
        MariaDbDataSource dataSource = new MariaDbDataSource();
        
        try{
            //dataSource.setUser("jaime");
            //dataSource.setPassword("1234");
            dataSource.setUser("root");
            dataSource.setPassword("");
            dataSource.setServerName(ip);
            dataSource.setPort(puerto);
            dataSource.setDatabaseName(nombreBd);
            
            conn = dataSource.getConnection();
        }catch(SQLException e){
            System.out.println("Error al obtener conexion MariaDB: "+e);
        }
        return conn;
    }

    private Connection getConnectionSqlite() {
        Connection conn= null;
        Configuracion config = new Configuracion();
        String nombreBd = config.getBd();
        
        String url = "jdbc:sqlite:" + nombreBd + ".bd";
        
        try{            
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                System.out.println("Conexion establecida");
                
                String script = this.getScriptSqlite();
                System.out.println(script);
                System.out.println("\n-----\n\n");
                try{
                    Statement stmt = conn.createStatement();
                    stmt.execute(script);
                    //stmt.executeUpdate("INSERT INTO FINCA(ID_FINCA) VALUES('hola')");
                    
                    System.out.println("SCRIPT EJECUTADO CORRECTAMENTE");
                }catch (SQLException e){
                    System.out.println("Error al ejecutar script creacion SQLite: "+e);
                }
                
                
            }
        }catch (SQLException e) {
            System.out.println("Error al obtener conexion SQLite: "+e);
        }
        
        return conn;
    }

    private String getScriptSqlite() {
        FileReader fr = null;
        BufferedReader br = null;
        
        File f = new File("ScriptCreateTableSqlite.sql");
        
        StringBuffer StringScript= new StringBuffer("");
        if(f.exists() && f.canRead()){
            try{
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                
                while(br.ready()){
                    StringScript.append(br.readLine()+"\n");
                }
                
            }catch(FileNotFoundException e){
                System.out.println("Error al recuperar el fichero de creacion de la BD");
            } catch (IOException ex) {
                System.out.println("Error al leer el fichero de creacion de la BD");
            }
        }
        return StringScript.toString();
    }
}
