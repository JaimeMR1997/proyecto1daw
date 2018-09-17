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
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyecto1daw.modelo.Fechas;
import proyecto1daw.modelo.Plantacion;

/**
 *
 * @author Jaime
 */
public abstract class PlantacionDAO {

    /**
     *
     * @param fInicio Fecha minima en al que se planto. Busca plantaciones plantadas
     * a partir de esta fecha, incluyendo esta.
     * @param fFin Fecha maxima en al que se planto. Busca plantaciones plantadas
     * antes de esta fecha, incluyendo esta.
     * @param idExp Id de la explotacin en la que hay que buscar las plantaciones
     * @return Devuelve un ArrayList de objetos de tipo Plantacion con
     * objetos Plantacion comprendidos entre las fechas proporcionadas
     */
    public abstract ArrayList<Plantacion> recuperarPorFecha(LocalDate fInicio,LocalDate fFin,String idExp);
    
    /**
     * @param fFin Fecha maxima en al que se planto. Busca plantaciones plantadas
     * antes de esta fecha, incluyendo esta.
     * @param idExp Id de la explotacin en la que hay que buscar las plantaciones
     * @return Devuelve un ArrayList de objetos de tipo Plantacion con
     * objetos Plantacion comprendidos entre las fechas proporcionadas
     */
    
    public abstract ArrayList<Plantacion> recuperarPorFechaFin(LocalDate fFin,String idExp);
    
    /**
     *
     * @param idPlant Id de pla plantacion por la que buscar
     * @return Un objeto Plantacion con los datos del id proporcionado o un objeto
     * Plantacion null si no conincide con nada en la BD.
     */
    public abstract Plantacion recuperarPorId(String idPlant);
    
    /**
     *
     * @param idExp Id de la explotacin de la que se van a bsucar las plantaciones
     * @return Un ArrayList de objetos Plantaciñon con todas las plantaciones de
     * la explotacin
     */
    public abstract ArrayList<Plantacion> recuperarPorExp(String idExp);
    
    /**
     *
     * @return Devuelve un ArrayList de objetos Plantacion con todas las plantaciones
     */
    public abstract ArrayList<Plantacion> recuperarTodas();
    
    /**
     *
     * @param p Plantacion para añadir a la BD
     * @return true si se añade correctamente y false si ha habido algun error
     */
    public abstract boolean addPlantacion(Plantacion p);

    /**
     *
     * @param id Id de la plantacin a borrar
     */
    public abstract void borrarPlantacion(String id);
    
    /**
     *
     * @param id Id de la plantacion a actualizar
     * @param campo Campo de  la tabla que se va a actualizar
     * @param nuevoValor Valor nuevo que va a tomar el campo
     * @return True si se ejecuta correctamente y false si ha habido un error
     */
    public abstract boolean actualizarCampo(String id, String campo, String nuevoValor);

    /**
     *
     * @param idExplotacion Id de la explotacion que se van a contar sus plantaciones
     * @return El numero de plantaciones que tiene la explotacion pasada por parametro
     */
    public abstract int contarPlant(String idExplotacion);

    /**
     *
     * @param idExplotacion Id de la explotacion que se va a buscar si tiene 
     * plantaciones activas
     * @return Devuelve true si hay plantaciones activas y false si no las hay
     */
    public abstract boolean hayPlantSinFinalizar(String idExplotacion);
    //Hay plant sin finalizar
    //No hay plant sin finalizar

    /**
     * 
     * @param p Plantacion de la que se va a recuperar las estadisticas de
     * distribucin del color en sus ventas
     * @return De vuelve un hashmap con los colores como clave y los kg por color
     * como valor
     */
    public abstract HashMap estadisticasColorTotal(Plantacion p);
    
    /**
     * 
     * @param p Plantacion de la que recuperar el precio medio por meses
     * @param anio Año del que se van a buscar las ventas
     * @return Devuelve un hasmap con los meses como clave y como valor el
     * precio medio cada mes
     */
    public abstract LinkedHashMap estadisticasPrecioMes(Plantacion p,int anio);
    /**
     * 
     * @param p Plantacion de la que se van a buscar los kg 
     * @param anio año en el que se van a buscar las ventas
     * @return Un hashmap con los meses como clave y como valor la produccion
     * de la plantacin or cada mes
     */
    public abstract LinkedHashMap estadisticasKgMes(Plantacion p,int anio);

    /**
     * 
     * @param p Objeto Plantacion de la que recuperar las estadísticas
     * @param anios El numero de años hacia atrás en los que se buscarán datos
     * @return Devuelve un objeto LinkedHashMap con los años como clave
     * y como valor el numero de kg de la plantacion de todo el año
     */
    public abstract LinkedHashMap estadisticasKgAnio(Plantacion p, int anios);

    /**
     * @param p La plantacion de la que se recuperarán las ventas
     * @param anio El año en el qeu se buscaran als ventas
     * @return Devuelve un LinkedHashMap con el mes como clave y como resultado otro
     * LinkedHashMap con el color como cave y el precio medio como valor.
     * Enero -> {(Color ->0.324),(Verde ->0.6)}
     */
    public abstract LinkedHashMap estadisticasPrecioColorMes(Plantacion p, int anio);

    public abstract LinkedHashMap estadisticasKgColorMes(Plantacion p, int anio);

    public abstract LinkedHashMap estadisticasKgColorQuincena(Plantacion p, int anio);
    /*
    this.modLista.addElement(Fechas.toString(LocalDate.of(anio, i, 1))+" - "+Fechas.toString(LocalDate.of(anio, i, 15)));
    this.modLista.addElement(Fechas.toString(LocalDate.of(anio, i, 16))+" - "
    +Fechas.toString(LocalDate.of(anio, i, 1).plusMonths(1).minusDays(1)));
     */
    //Es primera quincena
    //Es segunda quincena
    //Es primera quincena
    //Es segunda quincena
    //Cuando i es par es la segunda quincena del mes
    //Aumentamos j para  que cambie de mes

    public abstract LinkedHashMap estadisticasKgQuincena(Plantacion p, int anio);
    //Es primera quincena
    //Es segunda quincena
    //Es primera quincena
    //Es segunda quincena
    //Cuando i es par es la segunda quincena del mes
    //Aumentamos j para  que cambie de mes

    public abstract LinkedHashMap estadisticasPrecioColorQuincena(Plantacion p, int anio);
    //Es primera quincena
    //Es segunda quincena
    //Es primera quincena
    //Es segunda quincena
    //Cuando i es par es la segunda quincena del mes
    //Aumentamos j para  que cambie de mes
    }
