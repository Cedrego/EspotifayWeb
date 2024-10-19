package Logica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

/**
 * Representa a un usuario en el sistema con nombre, apellido y cédula de identidad.
 * @author Camilo
 */
@MappedSuperclass
public abstract class Usuario implements Serializable {
    @Id
    @Column (name="NICK")
    private String nickname;
    @Column (name="NOMBRE")
    private String nombre;
    @Column (name="APELLIDO")
    private String apellido;
    @Column (name="MAIL")
    private String correo;
    @Column (name="CONTRASENIA")
    private String password;
    @JoinColumn (name="FECNAC")
    private DTFecha fecha;
    //private imagen picture;
    
    public Usuario() {
    }

    public Usuario(String nick, String nom, String ape, String pass, String mail, DTFecha fech) {
        this.nickname = nick;
        this.nombre = nom;
        this.apellido = ape;
        this.correo = mail;
        this.password = pass;
        this.fecha = fech;
        //this.imagen = picture;
    }
    
    //Get variables
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNickname() {
        return nickname;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public String getPassword() {
        return password;
    }
    
    public DTFecha getFecha() {
        return fecha;
    }
    /*
    public getImagen() {
        return imagen;
    }
    */
    
    //Set variables
    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public void setApellido(String ape) {
        this.apellido = ape;
    }

    public void setNickname(String nick) {
        this.nickname = nick;
    }
    
    public void setCorreo(String mail) {
        this.correo = mail;
    }
    
    public void setPassword(String pass) {
        this.password = pass;
    }
    
    public void setFecha(DTFecha fech) {
        this.fecha = fech;
    }
    /*
    public void setImagen(String picture) {
        imagen = picture;
    }
    */
}
