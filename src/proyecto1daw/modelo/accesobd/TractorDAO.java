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
import proyecto1daw.modelo.Tractor;

/**
 *
 * @author Jaime
 */
public abstract class TractorDAO {

    /**
     *
     * @return
     */
    public abstract ArrayList<Tractor> recuperarTodos();
    
    /**
     *
     * @param t
     */
    public abstract void addTractor(Tractor t);
    
    /**
     *
     * @param matricula
     */
    public abstract void borrarTractor(String matricula);
    
    /**
     *
     * @param id
     * @param campo
     * @param nuevoValor
     */
    public abstract void actualizarCampo(String id, String campo, String nuevoValor);
}
