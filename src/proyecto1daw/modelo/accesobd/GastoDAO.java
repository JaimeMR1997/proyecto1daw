/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo.accesobd;

import java.util.ArrayList;
import proyecto1daw.modelo.Gasto;

/**
 *
 * @author Jaime
 */
public abstract class GastoDAO {

    public abstract void borrarGasto(String idGasto);
    //To change body of generated methods, choose Tools | Templates.

    public abstract ArrayList<Gasto> recuperarPorFinca(String id);
    
}
