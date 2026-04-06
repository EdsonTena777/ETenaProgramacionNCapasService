package com.digis.com.ETenaProgramacionNCapasMaven.JPA;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DireccionAddDTO {
    
    @JsonProperty("Calle")
    private String calle;

    @JsonProperty("NumeroExterior")
    private String numeroExterior;

    @JsonProperty("NumeroInterior")
    private String numeroInterior;

    @JsonProperty("Colonia")
    private Colonia colonia;

    // Getters y Setters
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }
}
