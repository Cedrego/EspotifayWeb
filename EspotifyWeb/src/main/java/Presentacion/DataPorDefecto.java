/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author cedre
 */

public class DataPorDefecto  {
    private String nombre;
    private String genero;
    private List<DataTema> Datatemas;
    //imagen;
    public DataPorDefecto() {}
    
    public DataPorDefecto(String NuevoNombre, String nomGen, List<DataTema> DataTem){
        this.genero = nomGen;
        this.nombre = NuevoNombre;
        this.Datatemas = DataTem;
    }   
    
   //Setters

    public void setGenero(String nomGenero) {
        this.genero = nomGenero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDatatemas(List<DataTema> Datatemas) {
        this.Datatemas = Datatemas;
    }
    //Getters

    public String getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }

    public List<DataTema> getDataTemas() {
        return Datatemas;
    }
    
}