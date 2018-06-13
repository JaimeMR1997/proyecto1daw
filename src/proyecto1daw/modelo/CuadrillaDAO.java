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
public class CuadrillaDAO {
    public ArrayList<Cuadrilla> recuperarTodas(){
        ArrayList<Cuadrilla> listaCuadrillas = new ArrayList<Cuadrilla>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM CUADRILLA");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCreacion = rs.getDate("F_CREACION").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                listaCuadrillas.add(new Cuadrilla(rs.getString(1), fCreacion, fFin));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las cuadrillas: "+e.getMessage());
        }
        return listaCuadrillas;
    }
    
    public Cuadrilla recuperarPorId(String idCuad){
        Cuadrilla cuad = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM CUADRILLA WHERE ID_CUADRILLA = ?");
            st.setString(1, idCuad);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fCreacion = rs.getDate("F_CREACION").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                cuad =new Cuadrilla(rs.getString(1), fCreacion, fFin);
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas las cuadrillas: "+e.getMessage());
        }
        return cuad;
    }
    
    public boolean addCuadrilla(Cuadrilla cuad){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO CUADRILLA(ID_CUADRILLA, F_CREACION, F_FIN) "
                + "VALUES(?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, cuad.getId());
            st.setDate(2, Date.valueOf(cuad.getfInicio()));
            Date fFin = null;
            if(cuad.getfFin() != null){
                fFin = Date.valueOf(cuad.getfFin());
            }
            st.setDate(3, fFin);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar cuadrilla: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public boolean borrarCuadrilla(String id){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "";
        try{
            try{
                consulta="DELETE FROM LIDERA WHERE ID_CUADRILLA=?";
                PreparedStatement st = accesoBD.prepareStatement(consulta);
                st.setString(1, id);
                st.executeUpdate();
            }catch(SQLException e){
                System.out.println("Excepcion SQL. Borrar Cuadrilla - asignacion encargado: "+e.getMessage());
            }
            
            try{
            consulta="DELETE FROM TRABAJA WHERE ID_CUADRILLA=?";
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.executeUpdate();
            }catch(SQLException e){
                System.out.println("Excepcion SQL. Borrar Cuadrilla - trabajos cuadrilla: "+e.getMessage());
            }
            
            try{
            consulta="DELETE FROM FORMA WHERE ID_CUADRILLA=?";
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.executeUpdate();
            }catch(SQLException e){
                System.out.println("Excepcion SQL. Borrar Cuadrilla - asignaciones trabajadores: "+e.getMessage());
            }
            
            consulta = "DELETE FROM CUADRILLA WHERE ID_CUADRILLA = ?";            
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.executeUpdate();
            
            
            
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar cuadrilla: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public boolean actualizarCampoCuadrilla(String id, String campo, String nuevoValor){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE CUADRILLA SET "+campo+"=? WHERE ID_CUADRILLA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, nuevoValor);
            st.setString(2, id);
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar cuadrilla: "+e.getMessage());
            res=false;
        }
        return res;
    }

    public ArrayList<Trabajo> recuperarTrabajos(Cuadrilla cuad) {
        ArrayList<Trabajo> listaTrabajos=  new ArrayList<Trabajo>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM TRABAJA WHERE ID_CUADRILLA=?");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fecha = rs.getDate("FECHA").toLocalDate();
                listaTrabajos.add(new Trabajo(rs.getString("ID_CUADRILLA"), fecha,
                        rs.getInt("HORAS"), rs.getString("TIPO"), rs.getString("ID_EXPLOTACION")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta trabajos por cuadrilla: "+e.getMessage());
        }
        return listaTrabajos;
    }
    
    public Trabajo recuperarUltTrabajo(Cuadrilla cuad){
        Trabajo t = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM (SELECT * FROM TRABAJA"
                        + " WHERE ID_CUADRILLA = ? ORDER BY FECHA DESC)"
                    + " WHERE ROWNUM <= 1");
            st.setString(1, cuad.getId());
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fecha = rs.getDate("FECHA").toLocalDate();
                t = new Trabajo(rs.getString("ID_CUADRILLA"), fecha, rs.getInt("HORAS"),
                        rs.getString("TAREA"), rs.getString("ID_EXPLOTACION"));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta ultimo trabajo cuadrilla: "+e.getMessage());
        }
        return t;
    }
    
    public ArrayList<Trabajo> recuperarTrabajos() {
        ArrayList<Trabajo> listaTrabajos=  new ArrayList<Trabajo>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM TRABAJA");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fecha = rs.getDate("FECHA").toLocalDate();
                listaTrabajos.add(new Trabajo(rs.getString("ID_CUADRILLA"), fecha,
                        rs.getInt("HORAS"), rs.getString("TAREA"), rs.getString("ID_EXPLOTACION")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todos los trabajos: "+e.getMessage());
        }
        return listaTrabajos;
    }
    
    public String getDniEncargado(Cuadrilla cuad) {
        String res = "";
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT DNI FROM CUADRILLA C,LIDERA L "
                    + "WHERE C.ID_CUADRILLA=L.ID_CUADRILLA AND L.ID_CUADRILLA = ?");
            st.setString(1, cuad.getId());
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                res = rs.getString(1);
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todos los trabajos: "+e.getMessage());
        }
        return res;
    }

    public boolean addTrabajo(Trabajo t) {
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO TRABAJA(ID_CUADRILLA,FECHA,HORAS,TAREA,ID_EXPLOTACION) "
                + "VALUES(?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, t.getIdCuadrilla());
            st.setDate(2, Date.valueOf(t.getFecha()));
            st.setInt(3, t.getHoras());
            st.setString(4, t.getTarea());
            st.setString(5, t.getIdExplotacion());
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar trabajo: "+e.getMessage());
            res=false;
        }
        return res;
    }

    public boolean asignarEncargado(Trabajador t, Cuadrilla cuad) {
        boolean res = asignarEncargado(t, cuad, null);
        return res;
    }
    
    public boolean asignarEncargado(Trabajador t, Cuadrilla cuad,LocalDate fecha) {
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO LIDERA(DNI,F_INICIO,ID_CUADRILLA) "
                + "VALUES(?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, t.getDni());
            if(fecha == null){
                fecha = LocalDate.now();
            }
            st.setDate(2, Date.valueOf(fecha));
            st.setString(3, cuad.getId());
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Asignar encargado cuadrilla: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public boolean actualizarCampoTrabajo(String campo,String nuevoValor,Trabajo t) {
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE TRABAJA SET "+campo+" = ? WHERE ID_CUADRILLA = ? "
                + "AND FECHA = ? AND ID_EXPLOTACION = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, nuevoValor);
            st.setString(2, t.getIdCuadrilla());
            st.setDate(3, Date.valueOf(t.getFecha()));
            st.setString(4, t.getIdExplotacion());
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar trabajo: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public int contarCuadrillas(){
        int res = 0;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT COUNT(*) FROM CUADRILLA";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                res=rs.getInt(1);
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. contar cuadrillas: "+e.getMessage());
            res=-1;
        }
        return res;
    }
    
    public Cuadrilla buscarCuadrillaPorId(String id){
        Cuadrilla cuad = null;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT * FROM CUADRILLA WHERE ID_CUADRILLA = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                LocalDate fCreacion = rs.getDate("F_CREACION").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                cuad = new Cuadrilla(rs.getString("ID_CUADRILLA"), fCreacion, fFin);
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. buscar cuadrilla por Id: "+e.getMessage());
        }
        return cuad;
    }

    Encargado recuperarEncargado(String dni) {
        TrabajadorDAO modeloTrab = new TrabajadorDAO();
        return modeloTrab.recuperarEncargado(dni);
    }

    public ArrayList<Trabajador> recuperarTrabajadores(String idCuad) {
        ArrayList<Trabajador> listaTrab = new ArrayList<Trabajador>();
        
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "SELECT DNI FROM FORMA WHERE ID_CUADRILLA = ? AND (F_FIN IS NULL OR F_FIN >= ?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, idCuad);
            //Se le pasa la fecha como parametro y no se usa la funcion SYSDATE de Oracle
            //porque al devolver hora no aparecen en la lista de trabajadores actuales
            //los que han sido cambiados de cuadrilla hoy
            st.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet rs = st.executeQuery();
            TrabajadorDAO modeloTrab = new TrabajadorDAO();
            while(rs.next()){
                listaTrab.add(modeloTrab.recuperarTrabajador(rs.getString(1)));//Recupera DNI y busca por DNI
                //y lo añade a la lista
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. recuperar emples de cuadrilla: "+e.getMessage());
        }
        
        return listaTrab;
    }
    
    public boolean borrarTrabajo(Trabajo t){
        boolean res = true;
        
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE FROM TRABAJA WHERE ID_CUADRILLA = ? "
                + "AND FECHA = ? AND TAREA = ? AND ID_EXPLOTACION = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, t.getIdCuadrilla());
            st.setDate(2, Date.valueOf(t.getFecha()));
            st.setString(3, t.getTarea());
            st.setString(4, t.getIdExplotacion());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. recuperar emples de cuadrilla: "+e.getMessage());
            res=false;
        }
        
        return res;
    }
}
