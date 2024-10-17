/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cedre
 */

public class DataParticular  {
    
    private  String nombre;
    private List<DataTema> Datatemas;
    private boolean privado;
    private String cliente;

    
    
    public DataParticular(){}
    
    public DataParticular(String NuevoNombre, String nickCliente, boolean priv, List<DataTema> DataTem){
        this.nombre = NuevoNombre;
        this.privado = priv;
        this.cliente = nickCliente;
        this.Datatemas = DataTem;
    }   
     
    //Setters

    public void setPrivado(boolean privado) {
        this.privado = privado;
    }

    public void setDatacliente(String nickCliente) {
        this.cliente = nickCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDatatemas(List<DataTema> Datatemas) {
        this.Datatemas = Datatemas;
    }
    //Getters

    public boolean getPrivado() {
        return privado;
    }

    public String getCliente() {
        return cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public List<DataTema> getDataTemas() {
        return Datatemas;
    }
  
}