/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Artista")
public class Artista extends Usuario {
    
    //VER BIEN COMO FUNCIONA CON CLASES EXTENDIDAS
    @Column (name="SITIO_WEB")
    private String SitioWeb;
    @Column (name="BIOGRAFIA", length = 3000)
    private String Biografia;
    //aca va una join table
    @OneToMany
    private List<Album> albumes;
    
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "artSigueA")
    private List<Cliente> seguidoPorA;
    
    public Artista() {
    }

    public Artista(String nick, String nom, String ape, String pass, String mail, DTFecha fech, String bio, String webSite) {
        super(nick, nom, ape, mail, pass, fech);
        this.SitioWeb = webSite;
        this.Biografia = bio;
        this.albumes = new ArrayList<>(); //inicializa lista de albumes
        this.seguidoPorA = new ArrayList<>(); //inicializa lista de albumes
    }
    
    //Get variables
    public String getSitioWeb() {
        //ArtistaJpaController artjpa = new ArtistaJpaController();
        //return artjpa.findArtista(this.getNickname()).SitioWeb;
        return this.SitioWeb;
    }
    
    public String getBiografia() {
        //ArtistaJpaController artjpa = new ArtistaJpaController();
        //return artjpa.findArtista(this.getNickname()).Biografia;
        return this.Biografia;
    }
    
    public List<Album> getAlbumes(){
        //ArtistaJpaController artjpa = new ArtistaJpaController();
        //return artjpa.findArtista(this.getNickname()).albumes;
        return this.albumes;
    }
    public List<Cliente> getSeguidoPorA(){
        //ArtistaJpaController artjpa = new ArtistaJpaController();
        //return artjpa.findArtista(this.getNickname()).seguidoPorA;
        return this.seguidoPorA;
    }
    
    //Set variables
    public void setSitioWeb(String webSite) {
        this.SitioWeb = webSite;
    }
    
    public void setBiografia(String bio){
        this.Biografia=bio;
    }
    
    public void setAlbumes(List<Album> alb){
        this.albumes = alb;
    }
    
    public void setSeguidoPorA(List<Cliente> seg){
        this.seguidoPorA = seg;
    }
    
}
