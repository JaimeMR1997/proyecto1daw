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
import proyecto1daw.modelo.Venta;

/**
 *
 * @author Jaime
 */
public abstract class VentaDAO {

    /**
     *
     * @param idVenta
     * @param idPlant
     * @return
     */
    public abstract Venta recuperarPorId(String idVenta,String idPlant);

    /**
     *
     * @param fecha
     * @param idPlant
     * @return
     */
    public abstract ArrayList<Venta> recuperarPorFecha(LocalDate fecha,String idPlant);
    
    public abstract ArrayList<Venta> recuperarPorFechas(LocalDate fVentaInicio, LocalDate fVentaFin, String idPlant);
    
    /**
     *
     * @param idPlant
     * @return
     */
    public abstract ArrayList<Venta> recuperarPorPlant(String idPlant);
    
    /**
     *
     * @return
     */
    public abstract ArrayList<Venta> recuperarTodas();
    
    /**
     *
     * @param v
     * @return
     */
    public abstract boolean addVenta(Venta v);
    
    /**
     *
     * @param id
     * @param idPlant
     * @return
     */
    public abstract boolean borrarVenta(String id,String idPlant);
    
    /**
     *
     * @param idPlant
     * @return
     */
    public abstract boolean borrarVentasPlantacion(String idPlant);
    
    /**
     *
     * @param id
     * @param idPlant
     * @param campo
     * @param nuevoValor
     * @return
     */
    public abstract boolean actualizarCampo(String id, String idPlant, String campo, String nuevoValor);
    
    /**
     *
     * @param idVenta
     * @param idPlant
     * @param campo
     * @return
     */
    public abstract String buscar(String idVenta, String idPlant, String campo);

    /**
     *
     * @param idPlant
     * @param fecha
     * @return
     */
    public abstract int contarVentas(String idPlant, LocalDate fecha);

    /**
     *
     * @param idPlant
     * @return
     */
    public abstract double calcularIngresos(String idPlant);
        
    /**
     * @param fVentaInicio Fecha desde la cual se calculan los ingresos,incluida la fecha
     * @param fVentaFin Fecha hasta la que se calculan los ingresos,incluida la fecha
     * @param idPlant El id de la plantacion a calcular sus ingresos
     * @return Un double con la cantidad de ingresos generada en el correspondiente periodo de tiempo
     */
    public abstract double calcularIngresos(LocalDate fVentaInicio, LocalDate fVentaFin, String idPlant);

    public abstract double calcularQuincAnt(String idPlant);

    public abstract double calcularQuincActual(String idPlant);
    
}
