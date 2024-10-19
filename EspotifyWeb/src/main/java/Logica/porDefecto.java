/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author cedre
 */
@Entity
public class porDefecto extends Playlist {
    @OneToOne
    private Genero genero;

    public porDefecto() {}
     public porDefecto(String NuevoNombre, Genero Gen){
        this.genero = Gen;
        nombre = NuevoNombre;
        this.temas = new ArrayList();
    }   

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    public void addTema(Tema tem){
        this.temas.add(tem);
    }
    public void removerTema(String NomTema){
       for(Tema tem : this.temas){
           if(tem.getNombre().equalsIgnoreCase(NomTema)){
               this.temas.remove(tem);
           }
       }
    }
}