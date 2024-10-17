/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Presentacion.DataAlbum;
import Presentacion.DataArtista;
import Presentacion.DataCliente;
import Presentacion.DataGenero;
import Presentacion.DataParticular;
import Presentacion.DataPorDefecto;
import Presentacion.DataTema;
import java.util.List;

public interface ICtrl {
    //Creadores
    public abstract void crearCliente(DataCliente cli);
    public abstract void crearArtista(DataArtista art);
    public abstract void crearTema(DataTema tem);
    public abstract void crearGenero(DataGenero gen);
    public abstract void crearAlbum(DataAlbum alb);
    public abstract void crearParticular(DataParticular part);
    public abstract void crearporDefecto(DataPorDefecto pordef);
    
    //Getters
    public abstract List<DataCliente> traerClientes();
    public abstract List<DataArtista> traerArtistas();
    public abstract List<DataGenero> traerGeneros();
    public abstract List<DataTema> traerTemas();
    public abstract List<DataAlbum> traerAlbumes();
    public abstract List<DataParticular> traerParticulares();
    public abstract List<DataPorDefecto> traerporDefectos();
    
    public abstract boolean existeCliente(String nick);
    public abstract boolean existeArtista(String nick);
    public boolean existePassC(String nick,String pass);
    public boolean existePassA(String nick,String pass);
}
