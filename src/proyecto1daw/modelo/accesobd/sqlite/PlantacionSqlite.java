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
public class PlantacionSqlite extends PlantacionDAO{

    @Override
    public LinkedHashMap estadisticasKgColorMes(Plantacion p, int anio) {
        return super.estadisticasKgColorMes(p, anio); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedHashMap estadisticasPrecioColorMes(Plantacion p, int anio) {
        return super.estadisticasPrecioColorMes(p, anio); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedHashMap estadisticasKgAnio(Plantacion p, int anios) {
        return super.estadisticasKgAnio(p, anios); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedHashMap estadisticasKgMes(Plantacion p, int anio) {
        return super.estadisticasKgMes(p, anio); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedHashMap estadisticasPrecioMes(Plantacion p, int anio) {
        return super.estadisticasPrecioMes(p, anio); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap estadisticasColorTotal(Plantacion p) {
        return super.estadisticasColorTotal(p); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hayPlantSinFinalizar(String idExplotacion) {
        return super.hayPlantSinFinalizar(idExplotacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarPlant(String idExplotacion) {
        return super.contarPlant(idExplotacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampo(String id, String campo, String nuevoValor) {
        return super.actualizarCampo(id, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarPlantacion(String id) {
        super.borrarPlantacion(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPlantacion(Plantacion p) {
        return super.addPlantacion(p); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Plantacion> recuperarTodas() {
        return super.recuperarTodas(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Plantacion> recuperarPorExp(String idExp) {
        return super.recuperarPorExp(idExp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Plantacion recuperarPorId(String idPlant) {
        return super.recuperarPorId(idPlant); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Plantacion> recuperarPorFecha(LocalDate fInicio, LocalDate fFin, String idExp) {
        return super.recuperarPorFecha(fInicio, fFin, idExp); //To change body of generated methods, choose Tools | Templates.
    }


}
