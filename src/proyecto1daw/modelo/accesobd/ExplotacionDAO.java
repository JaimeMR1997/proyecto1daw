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
import proyecto1daw.modelo.Explotacion;

/**
 *
 * @author Jaime
 */
public abstract class ExplotacionDAO {

    /**
     *
     * @return
     */
    public abstract ArrayList<Explotacion> recuperarTodas();
    
    /**
     *
     * @param exp
     * @return
     */
    public abstract boolean addExplotacion(Explotacion exp);
    //st.setDate(5, Date.valueOf(exp.getfFin()));

    /**
     *
     * @param id
     */
    public abstract void borrarExplotacion(String id);

    /**
     *
     * @param id
     * @param campo
     * @param nuevoValor
     * @return
     */
    public abstract boolean actualizarCampo(String id, String campo, String nuevoValor);
    
    /**
     *
     * @param idFinca
     * @return
     */
    public abstract ArrayList<Explotacion> recuperarPorFinca(String idFinca);
    
    /**
     *
     * @param idFinca
     * @return
     */
    public abstract int contarPorFinca(String idFinca);

    /**
     *
     * @param idExp
     * @return
     */
    public abstract Explotacion recuperarPorId(String idExp);
    
}
