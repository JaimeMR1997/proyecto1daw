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
import proyecto1daw.modelo.Cuadrilla;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Trabajador;
import proyecto1daw.modelo.Trabajo;

/**
 *
 * @author Jaime
 */
public class CuadrillaSqlite extends CuadrillaDAO{

    @Override
    public boolean borrarTrabajo(Trabajo t) {
        return super.borrarTrabajo(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Trabajador> recuperarTrabajadores(String idCuad) {
        return super.recuperarTrabajadores(idCuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Encargado recuperarEncargado(String dni) {
        return super.recuperarEncargado(dni); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cuadrilla buscarCuadrillaPorId(String id) {
        return super.buscarCuadrillaPorId(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarCuadrillas() {
        return super.contarCuadrillas(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampoTrabajo(String campo, String nuevoValor, Trabajo t) {
        return super.actualizarCampoTrabajo(campo, nuevoValor, t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean asignarEncargado(Trabajador t, Cuadrilla cuad, LocalDate fecha) {
        return super.asignarEncargado(t, cuad, fecha); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean asignarEncargado(Trabajador emple, Cuadrilla cuad) {
        return super.asignarEncargado(emple, cuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addTrabajo(Trabajo t) {
        return super.addTrabajo(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDniEncargado(Cuadrilla cuad) {
        return super.getDniEncargado(cuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Trabajo> recuperarTrabajos() {
        return super.recuperarTrabajos(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Trabajo recuperarUltTrabajo(Cuadrilla cuad) {
        return super.recuperarUltTrabajo(cuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Trabajo> recuperarTrabajos(Cuadrilla cuad) {
        return super.recuperarTrabajos(cuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampoCuadrilla(String id, String campo, String nuevoValor) {
        return super.actualizarCampoCuadrilla(id, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrarCuadrilla(String id) {
        return super.borrarCuadrilla(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addCuadrilla(Cuadrilla cuad) {
        return super.addCuadrilla(cuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cuadrilla recuperarPorId(String idCuad) {
        return super.recuperarPorId(idCuad); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cuadrilla> recuperarTodas() {
        return super.recuperarTodas(); //To change body of generated methods, choose Tools | Templates.
    }

    
}
