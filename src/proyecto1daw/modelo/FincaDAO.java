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
 * @author alumno
 */
public class FincaDAO {
    
    public ArrayList<Finca> recuperarTodas(){
        ArrayList<Finca> listaFincas = new ArrayList<Finca>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM FINCA");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCompra = rs.getDate("F_COMPRA").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") !=null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                listaFincas.add(new Finca(rs.getString("ID_FINCA"), rs.getString("LOCALIDAD"),
                        rs.getInt("SUPERFICIE"),fCompra, fFin));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las Fincas: "+e.getMessage());
            e.printStackTrace();
        }
        return listaFincas;
    }
    
    public Finca recuperarPorId(String idFinca) {
        Finca finca = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM FINCA WHERE ID_FINCA = ?");
            st.setString(1, idFinca);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCompra = rs.getDate("F_COMPRA").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") !=null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                finca = new Finca(rs.getString("ID_FINCA"), rs.getString("LOCALIDAD"),
                        rs.getInt("SUPERFICIE"),fCompra, fFin);
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta finca por Id: "+e.getMessage());
            e.printStackTrace();
        }
        return finca;
    }
    
    public ArrayList<Encargado> recuperarEncargadosFinca(String idFinca) {
        ArrayList<Encargado> listaEncargados = new ArrayList<Encargado>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM ENCARGADO"
                    + " WHERE DNI = (SELECT DNI FROM DIRIGE "
                                    + "WHERE ID_FINCA = ? AND F_FIN>SYSDATE OR F_FIN IS NULL)");
            st.setString(1, idFinca);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                listaEncargados.add(new Encargado(rs.getString("VH_EMPRESA"),rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Recuperar encargados por finca: "+e.getMessage());
        }
        return listaEncargados;
    }
    
    public int contarTractores(String idFinca){
        int cont=0;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Finca contar tractores: "+e.getMessage());
        }
        return cont;
    }
    
    public boolean addFinca(Finca f){
        boolean res=true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO FINCA(ID_FINCA,LOCALIDAD,SUPERFICIE,F_COMPRA,F_FIN) "
                + "VALUES(?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, f.getId());
            st.setString(2, f.getLocalidad());
            st.setInt(3, f.getSuperficie());
            st.setDate(4, Date.valueOf(f.getfCompra()));
//          st.setDate(5, Date.valueOf(f.getfFin()));
            st.setDate(5, null);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar Finca: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public void borrarFinca(String id){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE FROM FINCA WHERE ID_FINCA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar Finca: "+e.getMessage());
        }
    }
    
    public boolean actualizarCampo(String id, String campo, String nuevoValor){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE FINCA SET "+campo+"=? WHERE ID_FINCA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            //st.setString(1, campo);
            st.setString(1, nuevoValor);
            st.setString(2, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar Finca: "+e.getMessage());
            res=false;
        }
        return res;
    }
}
