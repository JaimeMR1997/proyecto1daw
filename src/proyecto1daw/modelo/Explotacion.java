/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1daw.modelo;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 *
 * @author Jaime
 */
public class Explotacion {
    private String id;
    private int superficie;
    private String tipo;
    private LocalDate fCreacion;
    private LocalDate fFin;
    private String idFinca;
    private ArrayList<Plantacion> listaPlant;

    public Explotacion(String id, int superficie, String tipo, LocalDate fCreacion, LocalDate fFin, String idFinca) {
        this.id = id;
        this.superficie = superficie;
        this.tipo = tipo;
        this.fCreacion = fCreacion;
        this.fFin = fFin;
        this.idFinca = idFinca;
        
        PlantacionDAO modeloPlant = new PlantacionDAO();
        this.listaPlant = modeloPlant.recuperarPorExp(this.id);
    }
    
    public boolean hayPlantacion(){
        boolean res = false;
        for (Plantacion p : this.listaPlant) {
            if(p.estaActiva()){
                res=true;
            }
        }
        return res;
    }
    
    public String calcularEstado(){
        //Este método se llama asi para futura ampliacion
        //Se planea avisar de cuando no es rentable mantener la plantacion
        //Para ello se necesitarian saber gastos
        
        //De momento solo devuelve Plantado/Sin plantar
        String res = "Sin plantar";
        if(hayPlantacion()){
            res="Plantado";
        }
        return res;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getfCreacion() {
        return fCreacion;
    }

    public void setfCreacion(LocalDate fCreacion) {
        this.fCreacion = fCreacion;
    }

    public LocalDate getfFin() {
        return fFin;
    }

    public void setfFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

    public String getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(String idFinca) {
        this.idFinca = idFinca;
    }

    public ArrayList<Plantacion> getListaPlant() {
        return listaPlant;
    }

    public void setListaPlant(ArrayList<Plantacion> listaPlant) {
        this.listaPlant = listaPlant;
    }

    
}
