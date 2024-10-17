/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.util.Objects;

/**
 *
 * @author cedre
 */
public class PartId {
    
    protected String nombre;
    private String cliente;

    public PartId(String nombre, String cliente) {
        this.nombre = nombre;
        this.cliente = cliente;
    }

    
    public String getNombre() {
        return nombre;
    }

    public String getCliente() {
        return cliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartId that = (PartId) o;
        return Objects.equals(nombre, that.nombre) &&
               Objects.equals(cliente, that.cliente);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nombre, cliente);
    }
}
