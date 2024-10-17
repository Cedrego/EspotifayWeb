/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;
import Logica.DTFecha;
import java.util.ArrayList;
import java.util.List;

public class DataCliente{

    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private DTFecha fecha;
    private List<String> DataPart; //coleccion
    private List<String> DataCliSeguido;
    private List<String> DataCliSeguidor;
    private List<String> DataArtSeguido;
    private List<String> DataAlmFav; //coleccion
    private List<String> DataTemaFav; //coleccion
    private List<String> DataPorDefFav; //coleccion
    private List<String> DataPartFav; //coleccion

    public DataCliente() {
    }

    public DataCliente(String nick, String nom, String ape, String mail,String pass, DTFecha fech,List<String> DataP, List<String> DataCliSeguido,List<String> DataCliSeguidor, List<String> DataArtSeguido,List<String> DataAlbFav, List<String> DataTemaFav,List<String> DataPorDefFav, List<String> DataPartFav) {
        this.nickname = nick;
        this.nombre = nom;
        this.apellido = ape;
        this.correo = mail;
        this.password = pass;
        this.fecha = fech;
        this.DataPart = DataP;
        this.DataCliSeguido = DataCliSeguido;
        this.DataCliSeguidor = DataCliSeguidor;
        this.DataArtSeguido = DataArtSeguido;
        this.DataAlmFav = DataAlbFav;
        this.DataTemaFav = DataTemaFav;
        this.DataPorDefFav = DataPorDefFav;
        this.DataPartFav = DataPartFav;
    }
    public DataCliente(String nick, String nom, String ape, String mail,String pass, DTFecha fech) {
        this.nickname = nick;
        this.nombre = nom;
        this.apellido = ape;
        this.correo = mail;
        this.password = pass;
        this.fecha = fech;
        this.DataPart = new ArrayList();
        this.DataArtSeguido = new ArrayList();
        this.DataCliSeguidor = new ArrayList();
        this.DataArtSeguido = new ArrayList();
        this.DataAlmFav = new ArrayList();
        this.DataTemaFav = new ArrayList();
        this.DataPorDefFav = new ArrayList();
        this.DataPartFav = new ArrayList();
    }
    //setters

    public void setDataPart(List<String> DataPart) {
        this.DataPart = DataPart;
    }

    public void setDataCliSeguido(List<String> DataCliSeguido) {
        this.DataCliSeguido = DataCliSeguido;
    }

    public void setDataCliSeguidor(List<String> DataCliSeguidor) {
        this.DataCliSeguidor = DataCliSeguidor;
    }

    public void setDataArtSeguido(List<String> DataArtSeguido) {
        this.DataArtSeguido = DataArtSeguido;
    }

    public void setDataAlmFav(List<String> DataAlmFav) {
        this.DataAlmFav = DataAlmFav;
    }

    public void setDataPorDefFav(List<String> DataPorDefFav) {
        this.DataPorDefFav = DataPorDefFav;
    }

    public void setDataPartFav(List<String> DataPartFav) {
        this.DataPartFav = DataPartFav;
    }

    public void setDataTemaFav(List<String> DataTemaFav) {
        this.DataTemaFav = DataTemaFav;
    }
    
    public String getNickname(){
        return nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public DTFecha getFecha() {
        return fecha;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getDataPart() {
        return DataPart;
    }

    public List<String> getDataCliSeguido() {
        return DataCliSeguido;
    }

    public List<String> getDataCliSeguidor() {
        return DataCliSeguidor;
    }

    public List<String> getDataArtSeguido() {
        return DataArtSeguido;
    }

    public List<String> getDataAlmFav() {
        return DataAlmFav;
    }

    public List<String> getDataTemaFav() {
        return DataTemaFav;
    }

    public List<String> getDataPorDefFav() {
        return DataPorDefFav;
    }

    //getters
    public List<String> getDataPartFav() {    
        return DataPartFav;
    }
}