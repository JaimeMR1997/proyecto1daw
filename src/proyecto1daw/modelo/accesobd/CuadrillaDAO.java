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
public abstract class CuadrillaDAO {

    /**
     *
     * @return Un ArrayList de objetos Cuadrilla con todas las cuadrillas
     */
    public abstract ArrayList<Cuadrilla> recuperarTodas();
    
    /**
     *
     * @param idCuad id por el cual buscar uan cuadrilla
     * @return Un objeto cuadrilla con el id especificado o null 
     */
    public abstract Cuadrilla recuperarPorId(String idCuad);
    
    /**
     *
     * @param cuad cuadrilla a añadir a la BD
     * @return true si se ha añadiddo correctamente la cuadrilla o false si ha habido un error
     */
    public abstract boolean addCuadrilla(Cuadrilla cuad);
    
    /**
     *
     * @param id Id de la cuadrilla a eliminar así como las relaciones de los 
     * empleados y encargados con ella
     * @return true si se elimina correctamente ,false si ha habido error
     */
    public abstract boolean borrarCuadrilla(String id);
    
    /**
     *
     * @param id Id de la cuadrilla por el cual se identifica
     * @param campo Campo o columna de la tabla a actualizar
     * @param nuevoValor nuevo valor
     * @return true si se actualiza correctamente false si hay error
     */
    public abstract boolean actualizarCampoCuadrilla(String id, String campo, String nuevoValor);

    /**
     *
     * @param cuad cuadrilla de la que buscar los trabajos
     * @return arraylist de trabajos realizados por la cuadrilla especificada
     */
    public abstract ArrayList<Trabajo> recuperarTrabajos(Cuadrilla cuad);
    
    /**
     *
     * @param cuad cuadrilla de al que buscar el ultimo trabajo
     * @return un objeto trabajo, el ultimo realizado por la cuadrilla pasada por parametro
     */
    public abstract Trabajo recuperarUltTrabajo(Cuadrilla cuad);
    
    /**
     *
     * @return arraylist de todos los trabajos de todas las cuadrillas
     */
    public abstract ArrayList<Trabajo> recuperarTrabajos();
    
    /**
     *
     * @param cuad cuadrilla de la que recuperar su encargado
     * @return el dni del encargado del a cuadrilla pasada por parametro
     */
    public abstract String getDniEncargado(Cuadrilla cuad);

    /**
     *
     * @param t trabajo a añadir a la BD
     * @return true si se alade correctamente y faalse si hay error
     */
    public abstract boolean addTrabajo(Trabajo t);

    /**
     *
     * @param emple empleado a asignar a la cuadrilla
     * @param cuad cuadrilal al a que asignar el encargado
     * @return true si se ejecuta correctaente false si hay un error al inserar
     */
    public abstract boolean asignarEncargado(Trabajador emple, Cuadrilla cuad);
    
    /**
     *
     * @param t empleado a asignar a la cuadrilla
     * @param cuad cuadrilal al a que asignar el encargado
     * @param fecha fecha de inicio en la que el empleado empieza a ser el encargado de la cuadrilla
     * @return true si se ejecuta correctaente false si hay un error al inserar
     */
    public abstract boolean asignarEncargado(Trabajador t, Cuadrilla cuad,LocalDate fecha);
    
    /**
     *
     * @param campo campo o columna a actualiar
     * @param nuevoValor nueo valor
     * @param t trabajo a actualizar
     * @return true si se ejecuta correctamente false si hay error
     */
    public abstract boolean actualizarCampoTrabajo(String campo,String nuevoValor,Trabajo t);
    
    /**
     *
     * @return el numero de cuadrillas que existen
     */
    public abstract int contarCuadrillas();
    
    /**
     *
     * @param id id de la cuadrilla que buscar
     * @return la cuadrilla que coincide con el id o null
     */
    public abstract Cuadrilla buscarCuadrillaPorId(String id);

    public abstract Encargado recuperarEncargado(String dni);

    /**
     *
     * @param idCuad cuadrilla de la que recuperar los trabajadores
     * @return arraylist de los trabajadores de la cuadrilla
     */
    public abstract ArrayList<Trabajador> recuperarTrabajadores(String idCuad);
    //Se le pasa la fecha como parametro y no se usa la funcion SYSDATE de Oracle
    //porque al devolver hora no aparecen en la lista de trabajadores actuales
    //los que han sido cambiados de cuadrilla hoy
    //Recupera DNI y busca por DNI
    //y lo añade a la lista
        
    /**
     *
     * @param t trabajo a borrar
     * @return true si se ejecuta correctaemnte false si hay error
     */
    public abstract boolean borrarTrabajo(Trabajo t);
}
