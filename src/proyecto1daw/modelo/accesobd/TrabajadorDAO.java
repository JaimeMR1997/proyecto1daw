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
public abstract class TrabajadorDAO {
    
    /**
     *
     * @param t Es el empleado a actualizar en la base de datos. Puede ser un objeto
     * encargado o conductor
     * @param fFin Es la fecha en la que empieza el contrato como trabajador y 
     * termina el anterior contrato. Si el parámetro es null toma hoy como fecha
     * @return Devuelve true si se ha realizado con éxito el ascenso a trabajador.
     * Devuelve false si ha habido algun error al realizar el UPDATE
     */
    public abstract boolean ascensoTrabajador(Trabajador t, LocalDate fFin);
    //trabajador o encargado WHERE DNI=?";
    //Encargados
    //Conductor
    //Actualiza la fecha a la de contratacion a la de hoy
    //Para cambiar la fecha por otra habrá que modificar al encargado desde otra parte

    /**
     *
     * @param t Es el empleado a actualizar en la base de datos. Puede ser un objeto
     * encargado o trabajador
     * @param fFin Es la fecha en la que empieza el contrato como conductor y 
     * termina el anterior contrato. Si el parámetro es null toma hoy como fecha
     * @return Devuelve true si se ha realizado con éxito el ascenso a conductor.
     * Devuelve false si ha habido algun error al realizar el UPDATE
     */
    public abstract boolean ascensoConductor(Trabajador t, LocalDate fFin);
    //trabajador o encargado WHERE DNI=?";
    //Encargados
    //Trabajadores
    //Actualiza la fecha a la de contratacion a la de hoy
    //Para cambiar la fecha por otra habrá que modificar al encargado desde otra parte
        
    /**
     *
     * @param t Es el empleado a actualizar en la base de datos. Puede ser un objeto
     * conductor o trabajador
     * @param fFin Es la fecha en la que empieza el contrato como encargado y 
     * termina el anterior contrato. Si el parámetro es null toma hoy como fecha
     * @return Devuelve true si se ha realizado con éxito el ascenso a encargado.
     * Devuelve false si ha habido algun error al realizar el UPDATE
     */
    public abstract boolean ascensoEncargado(Trabajador t,LocalDate fFin);
    //trabajador o ocnductor WHERE DNI=?";
    //Trabajadores normales
    //Conductores
    //Actualiza la fecha a la de contratacion a la de hoy
    //Para cambiar la fecha por otra habrá que modificar al encargado desde otra parte
        
    /**
     *
     * @param dni El dni del encargado a asignar
     * @param idFinca La ficna a la que asignar el encaqrgado
     * @param fInicio  La fecha en la que inicia el contrato. Si el parámetro es null toma hoy como fecha
     * @return Devuelve true si la asignación se ha realizado con exito y
     * false si ha habido un error
     */
    public abstract boolean asignarEncargado(String dni,String idFinca,LocalDate fInicio);
    //Para cuando se asigna el encargado al crear una nueva finca

    /**
     *
     * @param dni El dni del encargado a asignar
     * @param idFinca La ficna a la que asignar el encaqrgado
     * @return Devuelve true si la asignación se ha realizado con exito y
     * false si ha habido un error
     */
    public abstract boolean asignarFinca(String dni,String idFinca);
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Trabajador con todos los trabajadores,
     *  encargados y conductores.
     */
    public abstract ArrayList<Trabajador> recuperarTodos();

    /**
     *
     * @param dni El dni del trabajador a buscar en la base de datos
     * @return Devuelve un objeto Trabajador que corresponde con el dni pasado por
     * parámetro. Si no coincide ninguno devuelve null
     */
    public abstract Trabajador recuperarTrabajador(String dni);
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Trabajador con todos los trabajadores
     */
    public abstract ArrayList<Trabajador> recuperarTrabajadores();
    
    /**
     *
     * @param dni El dni del conductor a buscar en la base de datos
     * @return Devuelve un objeto Conductor que corresponde con el dni pasado por
     * parámetro. Si no coincide ninguno devuelve null
     */
    public abstract Conductor recuperarConductor(String dni);
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Conductor con todos los conductores
     */
    public abstract ArrayList<Conductor> recuperarConductores();
    //Tractoristas-Conductores

    /**
     *
     * @param dni El dni del encargado a buscar en la base de datos
     * @return Devuelve un objeto Encargado que corresponde con el dni pasado por
     * parámetro. Si no coincide ninguno devuelve null
     */
    public abstract Encargado recuperarEncargado(String dni);
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Encargado con todos los encargados
     */
    public abstract ArrayList<Encargado> recuperarEncargados();
    //Encargados incluidos contrato finalizado

    /**
     *
     * @return Devuelve un ArrayList de objetos Encargado con todos los encargados 
     * activos, es decir, que tienen un contrato que no ha finalizado aún
     */
    public abstract ArrayList<Encargado> recuperarEncargadosVigentes();
    //Encargados con contrato

    /**
     *
     * @param t
     * @return 
     */
    public abstract boolean addTrabajador(Trabajador t);
    
    /**
     *
     * @param conduct
     * @return
     */
    public abstract boolean addConductor(Conductor conduct);
    
    /**
     *
     * @param enc
     * @return
     */
    public abstract boolean addEncargado(Encargado enc);
    
    /**
     *
     * @param dni
     * @return
     */
    public abstract boolean borrarTrabajador(String dni);
    
    /**
     *
     * @param dni
     * @return
     */
    public abstract boolean borrarEncargado(String dni);
    
    /**
     *
     * @param dni
     * @return
     */
    public abstract boolean borrarConductor (String dni);
    
    public abstract void actualizarCampo(String tabla,String dni, String campo, String nuevoValor) throws SQLException;
    
    /**
     *
     * @param dni
     * @param campo
     * @param nuevoValor
     * @return
     */
    public abstract boolean actualizarCampoTrab(String dni, String campo, String nuevoValor);
    
    /**
     *
     * @param id
     * @param campo
     * @param nuevoValor
     * @return
     */
    public abstract boolean actualizarCampoEnc(String id, String campo, String nuevoValor);
    
    /**
     *
     * @param dni
     * @param campo
     * @param nuevoValor
     * @return
     */
    public abstract boolean actualizarCampoCond(String dni, String campo, String nuevoValor);

    /**
     *
     * @return Devuelve un ArrayList de objetos Encargado con los encargados 
     * que no extán asignados a ninguna finca ni cuadrilla y tienen contrato vigente
     */
    public abstract ArrayList<Encargado> recuperarEncargadosLibres();
    //Encargados asignados a una cuadrilla sin fecha de fin o posterior a hoy
    //Restamos los que si estan asignados a nuestra lista con todos los
    //encargados para ir quedandonos solo con los libres
    //Restamos los que si estan asignados a nuestra lista con todos los
    //encargados para ir quedandonos solo con los libres
    //Ya solo estarian los libres y con contrato vigente

    /**
     *
     * @param dni Dni del encargado para modificar
     * @param idFinca Id de la finca a modificar
     * @param fFin Fecha en la que acaba la asignación del encargado a la finca.
     * Si la fecha es null se usa la fecha de hoy.
     * @return Devuelve true si la operación de modificación slae bien y false si
     * ha habido algún error.
     */
    public abstract boolean finAsignacionFinca(String dni, String idFinca,LocalDate fFin);
    //Para cuando se asigna el encargado al crear una nueva finca

    /**
     *
     * @param dni Dni del encargado para modificar
     * @param idFinca Id de la finca a modificar
     * @return Devuelve true si la operación de modificación slae bien y false si
     * ha habido algún error.
     */
    public abstract boolean finAsignacionFinca(String dni, String idFinca);

    /**
     *
     * @return Devuelve un ArrayList de objetos Trabajador con los trabajadores 
     * que no están asignados a ninguna finca ni cuadrilla y tienen contrato vigente
     */
    public abstract ArrayList<Trabajador> recuperarTrabajadoresLibres();
    //Se restan los que estan asignados

    /**
     *
     * @param dni Dni del encargado a modificar
     * @param idCuad Id de la finca a modificar
     * @return Devuelve true si se realiza la modificación correctamente y false
     * si ha habido un error. La fecha que se toma es la de hoy
     */
    public abstract boolean finAsigCuadrilla(String dni, String idCuad);

    /**
     *
     * @param dni Dni del encargado a modificar
     * @param idCuad Id de la finca a modificar
     * @return Devuelve true si se realiza la asignación correctamente y false
     * si ha habido un error. La fecha que se toma es la de hoy
     */
    public abstract boolean asignarCuad(String dni, String idCuad);
}
