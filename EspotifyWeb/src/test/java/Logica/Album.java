/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Album implements Serializable{
    @Id
    @Column (name="NOMBRE")
    private String nombre;
    @Column (name="ARTISTA")
    private String artista;
    @Column (name="CREACION")
    private int creacion;
    @JoinColumn(name = "TEMAS")
    private List<Tema> temas;
    @OneToMany
    private List<Genero> generos;
    
    public Album() {
    }

    public Album(String nombre, String artista, int creacion) {
        this.nombre = nombre;
        this.artista = artista;
        this.creacion = creacion;
        this.temas = new ArrayList();
        this.generos = new ArrayList();
    }

   
    //Get variables
    public String getNombre() {
        return nombre;
    }
    
    public String getArtista() {
        return artista;
    }
 
    public int getCreacion() {
        return creacion;
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    
    
    //Set variables
    public void setNombre(String nom) {
        this.nombre = nom;
    }
    
    public void setArtista(String art) {
        this.artista = art;
    }
    
    public void setFecha(int fech) {
        this.creacion = fech;
    }

    public void setTemas(List<Tema> temas) {
        this.temas = temas;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    //Agregar a listas
    
    public void addGenero(Genero gen){
        this.generos.add(gen);
    }
    
    public void addTemas(Tema tem){
        this.temas.add(tem);
    }
}
