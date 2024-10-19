/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(PartId.class)
public class Particular extends Playlist implements Serializable {
    @Column (name="PRIVADO")
    private boolean privado;
    @Id
    @ManyToOne
    private Cliente cliente;
    
    public Particular(){}
    
    public Particular(String NuevoNombre, Cliente cli){
        this.nombre = NuevoNombre;
        this.privado = true;
        this.cliente = cli;
        this.temas = new ArrayList();
    }   
    
    public boolean getPrivado(){
        return privado;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    public void removerTema(String NomTema){
       for(Tema tem : this.temas){
           if(tem.getNombre().equalsIgnoreCase(NomTema)){
               this.temas.remove(tem);
           }
       }
    }
    public void setCliente (Cliente cli){
        this.cliente = cli;
    }
    public void addTema(Tema tem){
        this.temas.add(tem);
    }
}