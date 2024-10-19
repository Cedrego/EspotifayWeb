/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Franco
 */
@Embeddable
public class DTFecha implements Serializable {
    @Column(name="DIA")
    private int dia;
    @Column(name="MES")
    private int mes;
    @Column(name="ANIO")
    private int anio;
    
    
    public DTFecha(){};
    
    public DTFecha(int diaDT, int mesDT, int anioDT){
        this.dia = diaDT;
        this.mes = mesDT;
        this.anio = anioDT;
    };
    
    public int getDia(){
        return dia;
    }
    
    public int getMes(){
        return mes;
    }
    
    public int getAnio(){
        return anio;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    
}
