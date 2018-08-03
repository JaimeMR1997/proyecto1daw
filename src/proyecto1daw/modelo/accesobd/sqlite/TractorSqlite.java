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
import proyecto1daw.modelo.Tractor;

/**
 *
 * @author Jaime
 */
public class TractorSqlite extends TractorDAO{

    @Override
    public void actualizarCampo(String id, String campo, String nuevoValor) {
        super.actualizarCampo(id, campo, nuevoValor); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarTractor(String matricula) {
        super.borrarTractor(matricula); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTractor(Tractor t) {
        super.addTractor(t); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Tractor> recuperarTodos() {
        return super.recuperarTodos(); //To change body of generated methods, choose Tools | Templates.
    }

    
}
