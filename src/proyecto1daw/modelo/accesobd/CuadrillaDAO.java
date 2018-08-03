/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo.accesobd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import proyecto1daw.modelo.Cuadrilla;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.Trabajo;

/**
 *
 * @author Jaime
 */
public class CuadrillaDAO {

    /**
     *
     * @return Un ArrayList de objetos Cuadrilla con todas las cuadrillas
     */
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
    
    /**
     *
     * @param idCuad id por el cual buscar uan cuadrilla
     * @return Un objeto cuadrilla con el id especificado o null 
     */
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
    
    /**
     *
     * @param cuad cuadrilla a añadir a la BD
     * @return true si se ha añadiddo correctamente la cuadrilla o false si ha habido un error
     */
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
    
    /**
     *
     * @param id Id de la cuadrilla a eliminar así como las relaciones de los 
     * empleados y encargados con ella
     * @return true si se elimina correctamente ,false si ha habido error
     */
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
    
    /**
     *
     * @param id Id de la cuadrilla por el cual se identifica
     * @param campo Campo o columna de la tabla a actualizar
     * @param nuevoValor nuevo valor
     * @return true si se actualiza correctamente false si hay error
     */
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

    /**
     *
     * @param cuad cuadrilla de la que buscar los trabajos
     * @return arraylist de trabajos realizados por la cuadrilla especificada
     */
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
    
    /**
     *
     * @param cuad cuadrilla de al que buscar el ultimo trabajo
     * @return un objeto trabajo, el ultimo realizado por la cuadrilla pasada por parametro
     */
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
    
    /**
     *
     * @return arraylist de todos los trabajos de todas las cuadrillas
     */
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
    
    /**
     *
     * @param cuad cuadrilla de la que recuperar su encargado
     * @return el dni del encargado del a cuadrilla pasada por parametro
     */
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

    /**
     *
     * @param t trabajo a añadir a la BD
     * @return true si se alade correctamente y faalse si hay error
     */
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

    /**
     *
     * @param emple empleado a asignar a la cuadrilla
     * @param cuad cuadrilal al a que asignar el encargado
     * @return true si se ejecuta correctaente false si hay un error al inserar
     */
    public boolean asignarEncargado(Trabajador emple, Cuadrilla cuad) {
        boolean res = asignarEncargado(emple, cuad, null);
        return res;
    }
    
    /**
     *
     * @param t empleado a asignar a la cuadrilla
     * @param cuad cuadrilal al a que asignar el encargado
     * @param fecha fecha de inicio en la que el empleado empieza a ser el encargado de la cuadrilla
     * @return true si se ejecuta correctaente false si hay un error al inserar
     */
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
    
    /**
     *
     * @param campo campo o columna a actualiar
     * @param nuevoValor nueo valor
     * @param t trabajo a actualizar
     * @return true si se ejecuta correctamente false si hay error
     */
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
    
    /**
     *
     * @return el numero de cuadrillas que existen
     */
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
    
    /**
     *
     * @param id id de la cuadrilla que buscar
     * @return la cuadrilla que coincide con el id o null
     */
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

    public Encargado recuperarEncargado(String dni) {
        TrabajadorDAO modeloTrab = new TrabajadorDAO();
        return modeloTrab.recuperarEncargado(dni);
    }

    /**
     *
     * @param idCuad cuadrilla de la que recuperar los trabajadores
     * @return arraylist de los trabajadores de la cuadrilla
     */
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
    
    /**
     *
     * @param t trabajo a borrar
     * @return true si se ejecuta correctaemnte false si hay error
     */
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
