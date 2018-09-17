/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.util.prefs.Preferences;

/**
 *
 * @author Jaime
 */
public class Configuracion {
    private Preferences pref;

    public Configuracion() {
        this.pref = Preferences.userRoot().node(this.getClass().getName());
    }
    
    public void setTipoServer(String tipo){
        pref.put("tiposerver", tipo);
    }
    
    /**
     * 
     * @return El tipo de BD en minuscula. Puede ser: "oracle", "mysql", "mariadb", "sqlite"
     */
    public String getTipoServer(){
        return pref.get("tiposerver", null);
    }
    
    public void setIp(String ip){
        pref.put("ip", ip);
    }
    
    public String getIP(){
        return pref.get("ip", null);
    }
    
    public void setPuerto(String puerto){
        pref.put("puerto", puerto);
    }
    
    public String getPuerto(){
        return pref.get("puerto", null);
    }
    
    public void setBd(String bd){
        pref.put("bd", bd);
    }
    
    public String getBd(){
        return pref.get("bd", null);
    }
}
