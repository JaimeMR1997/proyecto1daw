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
import proyecto1daw.modelo.Explotacion;

/**
 *
 * @author Jaime
 */
public class ExplotacionSqlite extends ExplotacionDAO{

    @Override
    public Explotacion recuperarPorId(String idExp) {
        return super.recuperarPorId(idExp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarPorFinca(String idFinca) {
        return super.contarPorFinca(idFinca); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Explotacion> recuperarPorFinca(String idFinca) {
        return super.recuperarPorFinca(idFinca); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarCampo(String id, String campo, String nuevoValor) {
        return super.actualizarCampo(id, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarExplotacion(String id) {
        super.borrarExplotacion(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addExplotacion(Explotacion exp) {
        return super.addExplotacion(exp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Explotacion> recuperarTodas() {
        return super.recuperarTodas(); //To change body of generated methods, choose Tools | Templates.
    }

    
}
