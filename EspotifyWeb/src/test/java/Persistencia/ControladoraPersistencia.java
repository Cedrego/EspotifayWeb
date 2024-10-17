/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Logica.Album;
import Logica.Artista;
import Logica.Cliente;
import Logica.Genero;
import Logica.Particular;
import Logica.Tema;
import Logica.porDefecto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia{
    GeneroJpaController genJpa = new GeneroJpaController();
    TemaJpaController temJpa = new TemaJpaController();
    ClienteJpaController cliJpa = new ClienteJpaController();
    AlbumJpaController albJpa = new AlbumJpaController();
    ParticularJpaController partJpa = new ParticularJpaController();
    porDefectoJpaController pordefJpa = new porDefectoJpaController();
    ArtistaJpaController artJpa = new ArtistaJpaController();
    
    //Operaciones CREATE
    public void crearGenero(Genero gen){
        try {
            genJpa.create(gen);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearTema(Tema tem){
        try {
            temJpa.create(tem);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearCliente(Cliente cli){
        try {
            cliJpa.create(cli);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearArtista(Artista art){
        try {
            artJpa.create(art);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearAlbum(Album alb){
        try {
            albJpa.create(alb);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearParticular(Particular part){
        try {
            partJpa.create(part);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void crearporDefecto(porDefecto pordef){
        try {
            pordefJpa.create(pordef);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Operaciones READ
    public List<Cliente> traerClientes(){
        return cliJpa.findClienteEntities();
    }
    public List<Artista> traerArtistas(){
        return artJpa.findArtistaEntities();
    }
    public List<Tema> traerTemas(){
        return temJpa.findTemaEntities();
    }
    public List<Album> traerAlbumes(){
        return albJpa.findAlbumEntities();
    }
    public List<Genero> traerGeneros(){
        return genJpa.findGeneroEntities();
    }
    public List<porDefecto> traerporDefectos(){
        return pordefJpa.findporDefectoEntities();
    }
    public List<Particular> traerParticulares(){
        return partJpa.findParticularEntities();
    }
    
    //Operaciones FIND
    public Genero findGenero(String gen){
        return genJpa.findGenero(gen);
    }
    public Cliente findCliente(String cli){
        return cliJpa.findCliente(cli);
    }
}
