/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo.accesobd.sqlite;

import proyecto1daw.modelo.accesobd.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Finca;

/**
 *
 * @author alumno
 */
public class FincaSqlite extends FincaDAO{

    @Override
    public ArrayList<Finca> recuperarTodas() {
        ArrayList<Finca> listaFincas = new ArrayList<Finca>();
        
        Conexion con = new Conexion();
        Connection accesoBd = con.getConexion();
        try{
            Statement stmt = accesoBd.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_FINCA FROM FINCA");
            if(rs.next()){
                Finca f = new Finca(rs.getString("ID_FINCA"),"no", 0, LocalDate.MIN, LocalDate.MIN);
                listaFincas.add(f);
            }
        }catch(SQLException e){
            System.out.println("Error al consultar la finca sqlite");
        }
        
        return listaFincas;
    }

    @Override
    public Finca recuperarPorId(String idFinca) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Encargado> recuperarEncargadosFinca(String idFinca) {
        return new ArrayList<Encargado>();
    }

    @Override
    public int contarTractores(String idFinca) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addFinca(Finca f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarFinca(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampo(String id, String campo, String nuevoValor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
