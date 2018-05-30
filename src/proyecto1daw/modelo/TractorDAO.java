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
public class TractorDAO {
    public ArrayList<Tractor> recuperarTodos(){
        ArrayList<Tractor> listaTractores = new ArrayList<Tractor>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM VENTA");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                listaTractores.add(new Tractor(rs.getString("MATRICULA"), rs.getString("MODELO"), 
                        rs.getInt("POTENCIA"), rs.getInt("ALTURA"), rs.getInt("ANCHO"), rs.getDate("F_ITV").toLocalDate(), rs.getInt("ANIO"),rs.getString("ID_FINCA")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas los tractores: "+e.getMessage());
        }
        return listaTractores;
    }
    
    public void addTractor(Tractor t){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO TRACTOR(MATRICULA,MODELO,POTENCIA,ALTURA,ANCHO,F_ITV,ANIO,ID_FINCA) "
                + "VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, t.getMatricula());
            st.setString(2, t.getModelo());
            st.setInt(3, t.getPotencia());
            st.setInt(4, t.getAltura());
            st.setInt(5, t.getAncho());
            st.setDate(6, Date.valueOf(t.getfItv()));
            st.setInt(7, t.getAnio());
            st.setString(8, t.getIdFinca());
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar tractor: "+e.getMessage());
        }
    }
    
    public void borrarTractor(String matricula){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE * FROM TRACTOR WHERE MATRICULA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, matricula);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar tractor: "+e.getMessage());
        }
    }
    
    public void actualizarCampo(String id, String campo, String nuevoValor){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE TRACTOR SET ?=? WHERE MATRICULA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, campo);
            st.setString(2, nuevoValor);
            st.setString(3, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar tractor: "+e.getMessage());
        }
    }
}
