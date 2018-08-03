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
public class TrabajadorSqlite extends TrabajadorDAO{

    @Override
    public boolean asignarCuad(String dni, String idCuad) {
        return super.asignarCuad(dni, idCuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean finAsigCuadrilla(String dni, String idCuad) {
        return super.finAsigCuadrilla(dni, idCuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Trabajador> recuperarTrabajadoresLibres() {
        return super.recuperarTrabajadoresLibres(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean finAsignacionFinca(String dni, String idFinca) {
        return super.finAsignacionFinca(dni, idFinca); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean finAsignacionFinca(String dni, String idFinca, LocalDate fFin) {
        return super.finAsignacionFinca(dni, idFinca, fFin); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Encargado> recuperarEncargadosLibres() {
        return super.recuperarEncargadosLibres(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampoCond(String dni, String campo, String nuevoValor) {
        return super.actualizarCampoCond(dni, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampoEnc(String id, String campo, String nuevoValor) {
        return super.actualizarCampoEnc(id, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampoTrab(String dni, String campo, String nuevoValor) {
        return super.actualizarCampoTrab(dni, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrarConductor(String dni) {
        return super.borrarConductor(dni); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrarEncargado(String dni) {
        return super.borrarEncargado(dni); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrarTrabajador(String dni) {
        return super.borrarTrabajador(dni); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEncargado(Encargado enc) {
        return super.addEncargado(enc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addConductor(Conductor conduct) {
        return super.addConductor(conduct); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addTrabajador(Trabajador t) {
        return super.addTrabajador(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Encargado> recuperarEncargadosVigentes() {
        return super.recuperarEncargadosVigentes(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Encargado> recuperarEncargados() {
        return super.recuperarEncargados(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Encargado recuperarEncargado(String dni) {
        return super.recuperarEncargado(dni); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Conductor> recuperarConductores() {
        return super.recuperarConductores(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Conductor recuperarConductor(String dni) {
        return super.recuperarConductor(dni); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Trabajador> recuperarTrabajadores() {
        return super.recuperarTrabajadores(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Trabajador recuperarTrabajador(String dni) {
        return super.recuperarTrabajador(dni); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Trabajador> recuperarTodos() {
        return super.recuperarTodos(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean asignarFinca(String dni, String idFinca) {
        return super.asignarFinca(dni, idFinca); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean asignarEncargado(String dni, String idFinca, LocalDate fInicio) {
        return super.asignarEncargado(dni, idFinca, fInicio); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ascensoEncargado(Trabajador t, LocalDate fFin) {
        return super.ascensoEncargado(t, fFin); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ascensoConductor(Trabajador t, LocalDate fFin) {
        return super.ascensoConductor(t, fFin); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ascensoTrabajador(Trabajador t, LocalDate fFin) {
        return super.ascensoTrabajador(t, fFin); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
