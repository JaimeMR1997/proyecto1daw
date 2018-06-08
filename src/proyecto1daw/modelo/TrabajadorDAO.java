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
public class TrabajadorDAO {
    
    public void ascensoEncargado(Trabajador t,LocalDate fFin){
        if(fFin==null){
            fFin=LocalDate.now();
        }
        
        String insertarCons = "INSERT INTO ENCARGADO(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO)"
                + " SELECT * FROM ? WHERE DNI=?";
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        try{
            PreparedStatement consultaInsertar = accesoBD.prepareStatement(insertarCons);
            consultaInsertar.setString(2, t.getDni());
            
            if(!(t instanceof Conductor) &&!(t instanceof Encargado)){ //Trabajadores normales
                consultaInsertar.setString(1, "TRABAJADOR");
                this.actualizarCampoTrab(t.getDni(), "F_FIN", Fechas.toString(fFin));
                
            }else if(!(t instanceof Encargado)){                        //Conductores
                consultaInsertar.setString(1, "CONDUCTOR");
                this.actualizarCampoCond(t.getDni(), "F_FIN", Fechas.toString(fFin));   
            }
            
            consultaInsertar.executeUpdate();
            PreparedStatement consultaActContrato = accesoBD.prepareStatement("UPDATE ENCARGADO SET F_CONT=?");
            consultaActContrato.setDate(1, Date.valueOf(LocalDate.now())); 
            consultaActContrato.executeUpdate();    //Actualiza la fecha a la de contratacion a la de hoy
            //Para cambiar la fecha por otra habrá que modificar al encargado desde otra parte
            
        }catch(SQLException e){
            System.out.println("Excepcion SQL.(Finca)ascender encargado: "+e.getMessage());
        }
    }
    
    public void asignarEncargado(String dni,String idFinca,LocalDate fInicio){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO DIRIGE(DNI,F_INICIO,ID_FINCA) "
                + "VALUES(?,?,?)";
        if(fInicio==null){
            fInicio=LocalDate.now(); //Para cuando se asigna el encargado al crear una nueva finca
        }
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, dni);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL.(Finca)Asignar encargado: "+e.getMessage());
        }
    }
    
    public void asignarFinca(String dni,String idFinca){
        this.asignarEncargado(dni, idFinca,null);
    }
    
    public ArrayList<Trabajador> recuperarTodos(){
        ArrayList<Trabajador> listaTodos = recuperarTrabajadores();
        listaTodos.addAll(this.recuperarConductores());
        listaTodos.addAll(this.recuperarEncargados());
        return listaTodos;
    }
    
    public ArrayList<Trabajador> recuperarTrabajadores(){
        ArrayList<Trabajador> listaTrabajadores = new ArrayList<Trabajador>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM TRABAJADOR");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = rs.getDate("F_FIN").toLocalDate();
                listaTrabajadores.add(new Trabajador(rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas los trabajadores: "+e.getMessage());
        }
        return listaTrabajadores;
    }
    
    public ArrayList<Conductor> recuperarConductores(){         //Tractoristas-Conductores
        ArrayList<Conductor> listaConductores = new ArrayList<Conductor>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM CONDUCTOR");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = rs.getDate("F_FIN").toLocalDate();
                listaConductores.add(new Conductor(rs.getString("PERMISOS"),rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas los conductores: "+e.getMessage());
        }
        return listaConductores;
    }
    
    public ArrayList<Encargado> recuperarEncargados(){         //Encargados
        ArrayList<Encargado> listaEncargados = new ArrayList<Encargado>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM ENCARGADO");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = rs.getDate("F_FIN").toLocalDate();
                listaEncargados.add(new Encargado(rs.getString("VH_EMPRESA"),rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas los encargados: "+e.getMessage());
        }
        return listaEncargados;
    }
    
    public boolean addTrabajador(Trabajador t){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO TRABAJADOR(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, t.getDni());
            st.setString(2, t.getNombre());
            st.setString(3, t.getApellidos());
            st.setDate(4, Date.valueOf(t.getfNacimiento()));
            st.setDate(5, Date.valueOf(t.getfContratacion()));
            st.setDate(6, Date.valueOf(t.getfFin()));
            st.setString(7, t.getTlf());
            st.setInt(8, t.getSalario());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar trabajadores: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public boolean addConductor(Conductor conduct){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO TRABAJADOR(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO,PERMISOS) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, conduct.getDni());
            st.setString(2, conduct.getNombre());
            st.setString(3, conduct.getApellidos());
            st.setDate(4, Date.valueOf(conduct.getfNacimiento()));
            st.setDate(5, Date.valueOf(conduct.getfContratacion()));
            st.setDate(6, Date.valueOf(conduct.getfFin()));
            st.setString(7, conduct.getTlf());
            st.setInt(8, conduct.getSalario());
            st.setString(9, conduct.getPermisos());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar trabajadores: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public boolean addEncargado(Encargado enc){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO TRABAJADOR(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO,VH_EMPRESA) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, enc.getDni());
            st.setString(2, enc.getNombre());
            st.setString(3, enc.getApellidos());
            st.setDate(4, Date.valueOf(enc.getfNacimiento()));
            st.setDate(5, Date.valueOf(enc.getfContratacion()));
            st.setDate(6, Date.valueOf(enc.getfFin()));
            st.setString(7, enc.getTlf());
            st.setInt(8, enc.getSalario());
            st.setString(9, enc.getVhEmpresa());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar trabajadores: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    public void borrarTrabajador(String id){
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE * FROM TRABAJADOR WHERE DNI = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, id);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar trabajadores: "+e.getMessage());
        }
    }
    
    private void actualizarCampo(String tabla,String id, String campo, String nuevoValor) throws SQLException{
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE ? SET ?=? WHERE DNI = ?";
        PreparedStatement st = accesoBD.prepareStatement(consulta);
        st.setString(1, tabla);
        st.setString(2, campo);
        st.setString(3, nuevoValor);
        st.setString(4, id);

        st.executeUpdate();
        accesoBD.close();
    }
    
    public void actualizarCampoTrab(String id, String campo, String nuevoValor){
        try{
            this.actualizarCampo("TRABAJADOR", id, campo, nuevoValor);
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar trabajador: "+e.getMessage());
        }
    }
    
    public void actualizarCampoEnc(String id, String campo, String nuevoValor){
        try{
            this.actualizarCampo("ENCARGADO", id, campo, nuevoValor);
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar encargado: "+e.getMessage());
        }
    }
    
    public void actualizarCampoCond(String id, String campo, String nuevoValor){
        try{
            this.actualizarCampo("CONDUCTOR", id, campo, nuevoValor);
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar conductor: "+e.getMessage());
        }
    }
}
