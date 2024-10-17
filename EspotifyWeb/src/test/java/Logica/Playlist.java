package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author cedre
 */


@MappedSuperclass
public abstract class Playlist{
    @Id
    protected String nombre;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Tema> temas;
    //imagen;
    
   public Playlist() {
        // Inicializar la lista aquí si se desea que todas las instancias tengan una lista de temas vacía
        this.temas = new ArrayList<>();
    }
    
    public Playlist(String NuevoNombre) {
        this.nombre = NuevoNombre;
        this.temas = new ArrayList<>();
    }
    
    
    public String getNombre(){
        return nombre;
    }
    
    public List<Tema> getTemas(){
        return temas;
    }
    
    public void setNombre(String NuevoNombre){
        this.nombre = NuevoNombre;
    }
    
    public void setTemas(List<Tema> nuevosTemas){
        this.temas = nuevosTemas;
    }
    
    
    
    
}