/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;


/**
 *
 * @author Franco
 */
public class DataGenero{
    private String nombre;
    private String padre;
    
    public DataGenero() {
    }
    
    public DataGenero(String nombre, String padre) {
        this.nombre = nombre;
        this.padre = padre;
    }
    
    public DataGenero(String nom){
        this.nombre=nom;
        this.padre = null;
    }
    
    //getters
    public String getNombre(){
        return nombre;
    }
    public String getPadre(){
        return padre;
    }
    
    //setters
    public void setNombre(String nom){
        this.nombre = nom;
    }
    public void setPadre(String gen){
        this.padre = gen;
    }
}
