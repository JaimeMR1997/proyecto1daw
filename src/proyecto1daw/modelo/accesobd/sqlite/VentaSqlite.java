/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo.accesobd.sqlite;

import proyecto1daw.modelo.accesobd.*;
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
public class VentaSqlite extends VentaDAO{

    @Override
    public double calcularQuincActual(String idPlant) {
        return super.calcularQuincActual(idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double calcularQuincAnt(String idPlant) {
        return super.calcularQuincAnt(idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double calcularIngresos(String idPlant) {
        return super.calcularIngresos(idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarVentas(String idPlant, LocalDate fecha) {
        return super.contarVentas(idPlant, fecha); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buscar(String idVenta, String idPlant, String campo) {
        return super.buscar(idVenta, idPlant, campo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampo(String id, String idPlant, String campo, String nuevoValor) {
        return super.actualizarCampo(id, idPlant, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrarVentasPlantacion(String idPlant) {
        return super.borrarVentasPlantacion(idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrarVenta(String id, String idPlant) {
        return super.borrarVenta(id, idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addVenta(Venta v) {
        return super.addVenta(v); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Venta> recuperarTodas() {
        return super.recuperarTodas(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Venta> recuperarPorPlant(String idPlant) {
        return super.recuperarPorPlant(idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Venta> recuperarPorFecha(LocalDate fecha, String idPlant) {
        return super.recuperarPorFecha(fecha, idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Venta recuperarPorId(String idVenta, String idPlant) {
        return super.recuperarPorId(idVenta, idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    
}
