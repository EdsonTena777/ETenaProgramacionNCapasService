
package com.digis.com.ETenaProgramacionNCapasMaven.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Colonia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcolonia")
    private int idColonia;
    @Column(name = "nombre")
    private String Nombre;
    @Column(name = "codigopostal")
    private String CodigoPostal;
    @ManyToOne
    @JoinColumn(name = "idmunicipio")
    public Municipio Municipio;

    public Colonia(){
    
    }
    
    public Colonia(int idColonia, String Nombre, String CodigoPostal, Municipio Municipio) {
        this.idColonia = idColonia;
        this.Nombre = Nombre;
        this.CodigoPostal = CodigoPostal;
        this.Municipio = Municipio;
    }

    public int getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(int idColonia) {
        this.idColonia = idColonia;
    }

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getCodigoPostal(){
        return CodigoPostal;
    }
    
    public void setCodigoPostal(String CodigoPostal){
        this.CodigoPostal = CodigoPostal;
    }   
}