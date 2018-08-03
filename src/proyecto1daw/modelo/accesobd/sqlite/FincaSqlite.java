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
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Finca;

/**
 *
 * @author alumno
 */
public class FincaSqlite extends FincaDAO{

    @Override
    public boolean actualizarCampo(String id, String campo, String nuevoValor) {
        return super.actualizarCampo(id, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarFinca(String id) {
        super.borrarFinca(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addFinca(Finca f) {
        return super.addFinca(f); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int contarTractores(String idFinca) {
        return super.contarTractores(idFinca); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Encargado> recuperarEncargadosFinca(String idFinca) {
        return super.recuperarEncargadosFinca(idFinca); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Finca recuperarPorId(String idFinca) {
        return super.recuperarPorId(idFinca); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Finca> recuperarTodas() {
        return super.recuperarTodas(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
