/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cliente")
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente")
    @JoinTable(
        name = "CliPart",
        joinColumns = @JoinColumn(name = "nomParticular"),
        inverseJoinColumns = @JoinColumn(name = "nickCli")
    )
    private List<Particular> particular; //coleccion
    
     // Relación autoreferencial para seguir a otros clientes
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "cliente_sigue_cliente",
        joinColumns = @JoinColumn(name = "cliente_id"),
        inverseJoinColumns = @JoinColumn(name = "sigue_a_id")
    )
    private List<Cliente> cliSigueA;
    
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "cliSigueA")
    private List<Cliente> seguidoPor;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ARTISTAS_SEGUIDOS",
        joinColumns = @JoinColumn(name = "CLIENTE"),
        inverseJoinColumns = @JoinColumn(name = "ARTISTA")
    )
    private List<Artista> artSigueA;
    
    @OneToMany
    private List<Album> albumFav; //coleccion
    
    @OneToMany
    private List<Tema> temasFAV; //coleccion
    
    @OneToMany
    private List<porDefecto> playFavPD; //coleccion
    
    @OneToMany
    private List<Particular> playFavPart; //coleccion

    public Cliente() {
    }
    public Cliente(String nick, String nom, String ape, String mail, String pass, DTFecha fech) {
        super(nick, nom, ape, mail, pass, fech);
        this.particular = new ArrayList();
        this.cliSigueA = new ArrayList();
        this.artSigueA = new ArrayList();
        this.seguidoPor = new ArrayList();
        this.albumFav = new ArrayList();
        this.temasFAV = new ArrayList();
        this.playFavPD = new ArrayList();
        this.playFavPart = new ArrayList();
    }

    public List<Particular> getParticular() {
        return particular;
    }

    public List<Cliente> getCliSigueA() {
        return cliSigueA;
    }

    public List<Cliente> getSeguidoPor() {
        return seguidoPor;
    }

    public List<Artista> getArtSigueA() {
        return artSigueA;
    }

    public List<Album> getAlbumFav() {
        return albumFav;
    }

    public List<Tema> getTemasFAV() {
        return temasFAV;
    }

    public List<porDefecto> getPlayFavPD() {
        return playFavPD;
    }

    public List<Particular> getPlayFavPart() {
        return playFavPart;
    }
    
}
