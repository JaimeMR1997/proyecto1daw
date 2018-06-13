/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

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
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            String usuario="jaime";
            String pass="1234";
            String connectionUrl = "jdbc:oracle:thin:"+usuario+"/"+pass+"@localhost:1521:XE";
            conn = DriverManager.getConnection(connectionUrl);
        }catch(ClassNotFoundException e){
            System.out.println("Clase no encontrada: "+e.getMessage());
        }catch(SQLException e){
            System.out.println("Excepción SQL: "+e.getMessage());
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
            System.out.println("Excepción SQL: "+e.getMessage());
        }
    }
}
