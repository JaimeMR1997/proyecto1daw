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
import proyecto1daw.modelo.Configuracion;
import proyecto1daw.modelo.Encargado;
import proyecto1daw.modelo.Finca;

/**
 *
 * @author alumno
 */
public abstract class FincaDAO {
    
    /**
     *
     * @return
     */
    public abstract ArrayList<Finca> recuperarTodas();
    
    /**
     *
     * @param idFinca
     * @return
     */
    public abstract Finca recuperarPorId(String idFinca);
    
    /**
     *
     * @param idFinca
     * @return
     */
    public abstract ArrayList<Encargado> recuperarEncargadosFinca(String idFinca);
    
    /**
     *
     * @param idFinca
     * @return
     */
    public abstract int contarTractores(String idFinca);
    
    /**
     *
     * @param f
     * @return
     */
    public abstract boolean addFinca(Finca f);
//          st.setDate(5, Date.valueOf(f.getfFin()));

    /**
     *
     * @param id
     */
    public abstract void borrarFinca(String id);
    
    /**
     *
     * @param id
     * @param campo
     * @param nuevoValor
     * @return
     */
    public abstract boolean actualizarCampo(String id, String campo, String nuevoValor);
    //st.setString(1, campo);
    }
