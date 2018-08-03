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
import proyecto1daw.modelo.Conductor;
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Trabajador;

/**
 *
 * @author Jaime
 */
public class TrabajadorDAO {
    
    /**
     *
     * @param t Es el empleado a actualizar en la base de datos. Puede ser un objeto
     * encargado o conductor
     * @param fFin Es la fecha en la que empieza el contrato como trabajador y 
     * termina el anterior contrato. Si el parámetro es null toma hoy como fecha
     * @return Devuelve true si se ha realizado con éxito el ascenso a trabajador.
     * Devuelve false si ha habido algun error al realizar el UPDATE
     */
    public boolean ascensoTrabajador(Trabajador t, LocalDate fFin) {
        boolean res = true;
        if(fFin==null){
            fFin=LocalDate.now();
        }
        
        String insertarCons = "INSERT INTO TRABAJADOR(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO)"
                + " SELECT * FROM ";//trabajador o encargado WHERE DNI=?";
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        try{
            if((t instanceof Encargado)){                               //Encargados
                insertarCons+="ENCARGADO WHERE DNI = ?";
                this.actualizarCampoTrab(t.getDni(), "F_FIN", Fechas.toString(fFin));
                
            }else if(!(t instanceof Conductor)){                        //Conductor
                insertarCons+="CONDUCTOR WHERE DNI = ?";
                this.actualizarCampoCond(t.getDni(), "F_FIN", Fechas.toString(fFin));   
            }
            
            PreparedStatement st = accesoBD.prepareStatement(insertarCons);
            st.setString(1, t.getDni());
            st.executeUpdate();
            PreparedStatement consultaActContrato = accesoBD.prepareStatement("UPDATE ENCARGADO SET F_CONT=?");
            consultaActContrato.setDate(1, Date.valueOf(LocalDate.now())); 
            consultaActContrato.executeUpdate();    //Actualiza la fecha a la de contratacion a la de hoy
            //Para cambiar la fecha por otra habrá que modificar al encargado desde otra parte
            
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Ascender a trabajador: "+e.getMessage());
            res=false;
        }
        return res;
    }

    /**
     *
     * @param t Es el empleado a actualizar en la base de datos. Puede ser un objeto
     * encargado o trabajador
     * @param fFin Es la fecha en la que empieza el contrato como conductor y 
     * termina el anterior contrato. Si el parámetro es null toma hoy como fecha
     * @return Devuelve true si se ha realizado con éxito el ascenso a conductor.
     * Devuelve false si ha habido algun error al realizar el UPDATE
     */
    public boolean ascensoConductor(Trabajador t, LocalDate fFin) {
        boolean res = true;
        if(fFin==null){
            fFin=LocalDate.now();
        }
        
        String insertarCons = "INSERT INTO CONDUCTOR(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO)"
                + " SELECT DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO FROM ";//trabajador o encargado WHERE DNI=?";
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        try{
            if((t instanceof Encargado)){                                //Encargados
                insertarCons+="ENCARGADO WHERE DNI = ?";
                this.actualizarCampoEnc(t.getDni(), "F_FIN", Fechas.toString(fFin));
                
            }else if(!(t instanceof Conductor)){                        //Trabajadores
                insertarCons+="TRABAJADOR WHERE DNI = ?";
                this.actualizarCampoTrab(t.getDni(), "F_FIN", Fechas.toString(fFin));   
            }
            
            PreparedStatement st = accesoBD.prepareStatement(insertarCons);
            st.setString(1, t.getDni());
            st.executeUpdate();
            PreparedStatement consultaActContrato = accesoBD.prepareStatement("UPDATE ENCARGADO SET F_CONT=?");
            consultaActContrato.setDate(1, Date.valueOf(fFin)); 
            consultaActContrato.executeUpdate();    //Actualiza la fecha a la de contratacion a la de hoy
            //Para cambiar la fecha por otra habrá que modificar al encargado desde otra parte
            
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Ascender a conductor: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param t Es el empleado a actualizar en la base de datos. Puede ser un objeto
     * conductor o trabajador
     * @param fFin Es la fecha en la que empieza el contrato como encargado y 
     * termina el anterior contrato. Si el parámetro es null toma hoy como fecha
     * @return Devuelve true si se ha realizado con éxito el ascenso a encargado.
     * Devuelve false si ha habido algun error al realizar el UPDATE
     */
    public boolean ascensoEncargado(Trabajador t,LocalDate fFin){
        boolean res = true;
        if(fFin==null){
            fFin=LocalDate.now();
        }
        
        String insertarCons = "INSERT INTO ENCARGADO(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO)"
                + " SELECT * FROM ";//trabajador o ocnductor WHERE DNI=?";
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        try{
            if(!(t instanceof Conductor) && !(t instanceof Encargado)){ //Trabajadores normales
                insertarCons+="TRABAJADOR WHERE DNI = ?";
                this.actualizarCampoTrab(t.getDni(), "F_FIN", Fechas.toString(fFin));
                
            }else if(!(t instanceof Encargado)){                        //Conductores
                insertarCons+="CONDUCTOR WHERE DNI = ?";
                this.actualizarCampoCond(t.getDni(), "F_FIN", Fechas.toString(fFin));   
            }
            
            PreparedStatement st = accesoBD.prepareStatement(insertarCons);
            st.setString(1, t.getDni());
            st.executeUpdate();
            PreparedStatement consultaActContrato = accesoBD.prepareStatement("UPDATE ENCARGADO SET F_CONT=?");
            consultaActContrato.setDate(1, Date.valueOf(LocalDate.now())); 
            consultaActContrato.executeUpdate();    //Actualiza la fecha a la de contratacion a la de hoy
            //Para cambiar la fecha por otra habrá que modificar al encargado desde otra parte
            
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Ascender a encargado: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param dni El dni del encargado a asignar
     * @param idFinca La ficna a la que asignar el encaqrgado
     * @param fInicio  La fecha en la que inicia el contrato. Si el parámetro es null toma hoy como fecha
     * @return Devuelve true si la asignación se ha realizado con exito y
     * false si ha habido un error
     */
    public boolean asignarEncargado(String dni,String idFinca,LocalDate fInicio){
        boolean res = true;
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
            st.setDate(2, Date.valueOf(fInicio));
            st.setString(3, idFinca);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL.Asignar encargado: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param dni El dni del encargado a asignar
     * @param idFinca La ficna a la que asignar el encaqrgado
     * @return Devuelve true si la asignación se ha realizado con exito y
     * false si ha habido un error
     */
    public boolean asignarFinca(String dni,String idFinca){
        return this.asignarEncargado(dni, idFinca,null);
    }
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Trabajador con todos los trabajadores,
     *  encargados y conductores.
     */
    public ArrayList<Trabajador> recuperarTodos(){
        ArrayList<Trabajador> listaTodos = recuperarTrabajadores();
        listaTodos.addAll(this.recuperarConductores());
        listaTodos.addAll(this.recuperarEncargados());
        return listaTodos;
    }

    /**
     *
     * @param dni El dni del trabajador a buscar en la base de datos
     * @return Devuelve un objeto Trabajador que corresponde con el dni pasado por
     * parámetro. Si no coincide ninguno devuelve null
     */
    public Trabajador recuperarTrabajador(String dni) {
        Trabajador trab = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM TRABAJADOR WHERE DNI = ?");
            st.setString(1, dni);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();    
                }
                trab = new Trabajador(rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO"));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta trabajador por DNI: "+e.getMessage());
        }
        return trab;
    }
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Trabajador con todos los trabajadores
     */
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
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();    
                }
                listaTrabajadores.add(new Trabajador(rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas los trabajadores: "+e.getMessage());
        }
        return listaTrabajadores;
    }
    
    /**
     *
     * @param dni El dni del conductor a buscar en la base de datos
     * @return Devuelve un objeto Conductor que corresponde con el dni pasado por
     * parámetro. Si no coincide ninguno devuelve null
     */
    public Conductor recuperarConductor(String dni) {
        Conductor cond = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM CONDUCTOR WHERE DNI = ?");
            st.setString(1, dni);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = null;    
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();    
                }
                cond = new Conductor(rs.getString("PERMISOS"),rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO"));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta conductor por DNI: "+e.getMessage());
        }
        return cond;
    }
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Conductor con todos los conductores
     */
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
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                listaConductores.add(new Conductor(rs.getString("PERMISOS"),rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta todas los conductores: "+e.getMessage());
        }
        return listaConductores;
    }
    
    /**
     *
     * @param dni El dni del encargado a buscar en la base de datos
     * @return Devuelve un objeto Encargado que corresponde con el dni pasado por
     * parámetro. Si no coincide ninguno devuelve null
     */
    public Encargado recuperarEncargado(String dni) {
        Encargado enc = null;
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM ENCARGADO WHERE DNI = ?");
            st.setString(1, dni);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                enc =new Encargado(rs.getString("VH_EMPRESA"),rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO"));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta encargado por DNI: "+e.getMessage());
        }
        return enc;
    }
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Encargado con todos los encargados
     */
    public ArrayList<Encargado> recuperarEncargados(){         //Encargados incluidos contrato finalizado
        ArrayList<Encargado> listaEncargados = new ArrayList<Encargado>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            PreparedStatement st = accesoBD.prepareStatement("SELECT * FROM ENCARGADO");
            ResultSet rs = st.executeQuery();
            while(rs.next()){
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
            System.out.println("Excepcion SQL. Consulta todas los encargados: "+e.getMessage());
        }
        return listaEncargados;
    }
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Encargado con todos los encargados 
     * activos, es decir, que tienen un contrato que no ha finalizado aún
     */
    public ArrayList<Encargado> recuperarEncargadosVigentes(){         //Encargados con contrato
        ArrayList<Encargado> listaEncargados = new ArrayList<Encargado>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            String consulta = "";
            Configuracion config = new Configuracion();
            if(config.getTipoServer().equalsIgnoreCase("oracle")){
                consulta = "SELECT * FROM ENCARGADO WHERE F_FIN >= SYSDATE OR F_FIN IS NULL";
            }else if(config.getTipoServer().equalsIgnoreCase("mysql")){
                consulta = "SELECT * FROM ENCARGADO WHERE F_FIN >= SYSDATE() OR F_FIN IS NULL";
            }
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
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
            System.out.println("Excepcion SQL. Consulta encargados cont vigente: "+e.getMessage());
        }
        return listaEncargados;
    }
    
    /**
     *
     * @param t
     * @return 
     */
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
            Date fechaFin = null;
            if(t.getfFin() != null){
                fechaFin = Date.valueOf(t.getfFin());
            }
            st.setDate(6,fechaFin);
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
    
    /**
     *
     * @param conduct
     * @return
     */
    public boolean addConductor(Conductor conduct){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO CONDUCTOR(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO,PERMISOS) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, conduct.getDni());
            st.setString(2, conduct.getNombre());
            st.setString(3, conduct.getApellidos());
            st.setDate(4, Date.valueOf(conduct.getfNacimiento()));
            st.setDate(5, Date.valueOf(conduct.getfContratacion()));
            if(conduct.getfFin() != null){
                st.setDate(6, Date.valueOf(conduct.getfFin()));
            }else{
                st.setDate(6, null);
            }
            st.setString(7, conduct.getTlf());
            st.setInt(8, conduct.getSalario());
            st.setString(9, conduct.getPermisos());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar conductor: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param enc
     * @return
     */
    public boolean addEncargado(Encargado enc){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO ENCARGADO(DNI,NOMBRE,APELLIDOS,F_NAC,F_CONT,F_FIN,TLF,SALARIO,VH_EMPRESA) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, enc.getDni());
            st.setString(2, enc.getNombre());
            st.setString(3, enc.getApellidos());
            st.setDate(4, Date.valueOf(enc.getfNacimiento()));
            st.setDate(5, Date.valueOf(enc.getfContratacion()));
            Date fFin = null;
            if(enc.getfFin() != null){
                fFin = Date.valueOf(enc.getfFin());
            }
            st.setDate(6, fFin);
            st.setString(7, enc.getTlf());
            st.setInt(8, enc.getSalario());
            st.setString(9, enc.getVhEmpresa());
            
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Insertar encargado: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param dni
     * @return
     */
    public boolean borrarTrabajador(String dni){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE FROM TRABAJADOR WHERE DNI = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, dni);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar trabajador: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param dni
     * @return
     */
    public boolean borrarEncargado(String dni){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE FROM ENCARGADO WHERE DNI = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, dni);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar encargado: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param dni
     * @return
     */
    public boolean borrarConductor (String dni){
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "DELETE FROM CONDUCTOR WHERE DNI = ?";
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, dni);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Borrar conductor: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    private void actualizarCampo(String tabla,String dni, String campo, String nuevoValor) throws SQLException{
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE "+tabla+" SET "+campo+"=? WHERE DNI = ?";
        PreparedStatement st = accesoBD.prepareStatement(consulta);
        st.setString(1, nuevoValor);
        st.setString(2, dni);

        st.executeUpdate();
        accesoBD.close();
    }
    
    /**
     *
     * @param dni
     * @param campo
     * @param nuevoValor
     * @return
     */
    public boolean actualizarCampoTrab(String dni, String campo, String nuevoValor){
        boolean res = true;
        try{
            this.actualizarCampo("TRABAJADOR", dni, campo, nuevoValor);
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar trabajador: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param id
     * @param campo
     * @param nuevoValor
     * @return
     */
    public boolean actualizarCampoEnc(String id, String campo, String nuevoValor){
        boolean res = true;
        try{
            this.actualizarCampo("ENCARGADO", id, campo, nuevoValor);
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar encargado: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param dni
     * @param campo
     * @param nuevoValor
     * @return
     */
    public boolean actualizarCampoCond(String dni, String campo, String nuevoValor){
        boolean res = true;
        try{
            this.actualizarCampo("CONDUCTOR", dni, campo, nuevoValor);
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Actualizar conductor: "+e.getMessage());
            res=false;
        }
        return res;
    }

    /**
     *
     * @return Devuelve un ArrayList de objetos Encargado con los encargados 
     * que no extán asignados a ninguna finca ni cuadrilla y tienen contrato vigente
     */
    public ArrayList<Encargado> recuperarEncargadosLibres() {
        ArrayList<Encargado> listaEncargados = this.recuperarEncargadosVigentes();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            
            //Encargados asignados a una cuadrilla sin fecha de fin o posterior a hoy
            
            String consulta = "";
            Configuracion config = new Configuracion();
            if(config.getTipoServer().equalsIgnoreCase("oracle")){
                consulta = "SELECT * FROM ENCARGADO WHERE DNI IN "
                    + "(SELECT DNI FROM LIDERA WHERE F_FIN > SYSDATE OR F_FIN IS NULL)";
            }else if(config.getTipoServer().equalsIgnoreCase("mysql")){
                consulta = "SELECT * FROM ENCARGADO WHERE DNI IN "
                    + "(SELECT DNI FROM LIDERA WHERE F_FIN > SYSDATE() OR F_FIN IS NULL)";
            }
            
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            ResultSet rs = st.executeQuery();
            
            ArrayList<Encargado> listaRestar = new ArrayList<Encargado>();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                listaRestar.add(new Encargado(rs.getString("VH_EMPRESA"),rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            
            //Restamos los que si estan asignados a nuestra lista con todos los
            //encargados para ir quedandonos solo con los libres
            listaEncargados.removeAll(listaRestar);
            listaRestar.clear();
            
            if(config.getTipoServer().equalsIgnoreCase("oracle")){
                consulta = "SELECT * FROM ENCARGADO WHERE DNI = (SELECT DNI FROM DIRIGE WHERE F_FIN > SYSDATE OR F_FIN IS NULL)";
            }else if(config.getTipoServer().equalsIgnoreCase("mysql")){
                consulta = "SELECT * FROM ENCARGADO WHERE DNI = (SELECT DNI FROM DIRIGE WHERE F_FIN > SYSDATE() OR F_FIN IS NULL)";
            }
            
            st = accesoBD.prepareStatement(consulta);
            rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();
                }
                listaRestar.add(new Encargado(rs.getString("VH_EMPRESA"),rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            
            //Restamos los que si estan asignados a nuestra lista con todos los
            //encargados para ir quedandonos solo con los libres
            listaEncargados.removeAll(listaRestar);
            listaRestar.clear();
            
            //Ya solo estarian los libres y con contrato vigente
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta encargados libres: "+e.getMessage());
        }
        return listaEncargados;
    }

    /**
     *
     * @param dni Dni del encargado para modificar
     * @param idFinca Id de la finca a modificar
     * @param fFin Fecha en la que acaba la asignación del encargado a la finca.
     * Si la fecha es null se usa la fecha de hoy.
     * @return Devuelve true si la operación de modificación slae bien y false si
     * ha habido algún error.
     */
    public boolean finAsignacionFinca(String dni, String idFinca,LocalDate fFin) {
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE DIRIGE SET F_FIN= ? WHERE DNI=? AND ID_FINCA = ?";
        if(fFin==null){
            fFin=LocalDate.now(); //Para cuando se asigna el encargado al crear una nueva finca
        }
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setDate(1, Date.valueOf(fFin));
            st.setString(2, dni);
            st.setString(3, idFinca);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL.(Finca)Fin asignacion encargado: "+e.getMessage());
            res=false;
        }
        return res;
    }
    
    /**
     *
     * @param dni Dni del encargado para modificar
     * @param idFinca Id de la finca a modificar
     * @return Devuelve true si la operación de modificación slae bien y false si
     * ha habido algún error.
     */
    public boolean finAsignacionFinca(String dni, String idFinca) {
        return this.finAsignacionFinca(dni, idFinca, null);
    }

    /**
     *
     * @return Devuelve un ArrayList de objetos Trabajador con los trabajadores 
     * que no están asignados a ninguna finca ni cuadrilla y tienen contrato vigente
     */
    public ArrayList<Trabajador> recuperarTrabajadoresLibres() {
        ArrayList<Trabajador> listaTodos = new ArrayList<Trabajador>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            
            String consulta = "";
            Configuracion config = new Configuracion();
            if(config.getTipoServer().equalsIgnoreCase("oracle")){
                consulta = "SELECT * FROM TRABAJADOR WHERE F_FIN IS NULL OR F_FIN>SYSDATE";
            }else if(config.getTipoServer().equalsIgnoreCase("mysql")){
                consulta = "SELECT * FROM TRABAJADOR WHERE F_FIN IS NULL OR F_FIN>SYSDATE()";
            }
            
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();    
                }
                listaTodos.add(new Trabajador(rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta trabajadores libres(trab cont finalizado): "+e.getMessage());
        }
        ArrayList<Trabajador> listaQuitar = new ArrayList<Trabajador>();
        try{
            Conexion c = new Conexion();
            Connection accesoBD = c.getConexion();
            String consulta = "";
            
            Configuracion config = new Configuracion();
            if(config.getTipoServer().equalsIgnoreCase("oracle")){
                consulta = "SELECT * FROM TRABAJADOR WHERE DNI IN "
                    + "(SELECT DNI FROM FORMA WHERE F_FIN >= SYSDATE OR F_FIN IS NULL)";
            }else if(config.getTipoServer().equalsIgnoreCase("mysql")){
                consulta = "SELECT * FROM TRABAJADOR WHERE DNI IN "
                    + "(SELECT DNI FROM FORMA WHERE F_FIN >= SYSDATE() OR F_FIN IS NULL)";
            }
            
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                LocalDate fNac = rs.getDate("F_NAC").toLocalDate();
                LocalDate fCont = rs.getDate("F_CONT").toLocalDate();
                LocalDate fFin = null;
                if(rs.getDate("F_FIN") != null){
                    fFin = rs.getDate("F_FIN").toLocalDate();    
                }
                listaQuitar.add(new Trabajador(rs.getString("DNI"), rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"), fNac, fCont, fFin, rs.getString("TLF"), rs.getInt("SALARIO")));
            }
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL. Consulta trabajadores libres(trab ocupados): "+e.getMessage());
        }

        //Se restan los que estan asignados
        listaTodos.removeAll(listaQuitar);
        return listaTodos;
    }

    /**
     *
     * @param dni Dni del encargado a modificar
     * @param idCuad Id de la finca a modificar
     * @return Devuelve true si se realiza la modificación correctamente y false
     * si ha habido un error. La fecha que se toma es la de hoy
     */
    public boolean finAsigCuadrilla(String dni, String idCuad) {
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "UPDATE FORMA SET F_FIN= ? WHERE DNI=? AND ID_CUADRILLA = ?";
        LocalDate fFin=LocalDate.now();
        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setDate(1, Date.valueOf(fFin));
            st.setString(2, dni);
            st.setString(3, idCuad);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL.Fin asignacion empleado: "+e.getMessage());
            res=false;
        }
        return res;
    }

    /**
     *
     * @param dni Dni del encargado a modificar
     * @param idCuad Id de la finca a modificar
     * @return Devuelve true si se realiza la asignación correctamente y false
     * si ha habido un error. La fecha que se toma es la de hoy
     */
    public boolean asignarCuad(String dni, String idCuad) {
        boolean res = true;
        Conexion c = new Conexion();
        Connection accesoBD = c.getConexion();
        String consulta = "INSERT INTO FORMA(DNI,F_INICIO,ID_CUADRILLA) "
                + "VALUES(?,?,?)";        
            LocalDate fInicio=LocalDate.now();

        try{
            PreparedStatement st = accesoBD.prepareStatement(consulta);
            st.setString(1, dni);
            st.setDate(2, Date.valueOf(fInicio));
            st.setString(3, idCuad);
            st.executeUpdate();
            accesoBD.close();
        }catch(SQLException e){
            System.out.println("Excepcion SQL.Asignar trabajador: "+e.getMessage());
            res=false;
        }
        return res;
    }
}
