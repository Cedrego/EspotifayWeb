/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Franco
 */
@Entity
public class Genero implements Serializable {
    @Id
    @Column (name="NOMBRE")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "SUBGENERO_DE") // Columna que guarda el ID del padre
    private Genero padre;
    
    public Genero() {
    }
    
    public Genero(String nombre, Genero padre) {
        this.nombre = nombre;
        this.padre = padre;
    }
    
    public Genero(String nom){
        this.nombre=nom;
        this.padre = null;
    }
    
    //getters
    public String getNombre(){
        return nombre;
    }
    public Genero getPadre(){
        return padre; 
    }
    
    //setters
    public void setNombre(String nom){
        this.nombre = nom;
    }
    public void setPadre(Genero gen){
        this.padre=gen;
    }           

    boolean equalsIgnoreCase(Genero genero) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
