
package com.digis.com.ETenaProgramacionNCapasMaven.JPA;


public class ErroresArchivo {
    
    public int fila;
    public String dato;
    public String descripcion;

    public int getFila(){
        return fila;
    }
    
    public void setFila(int fila){
        this.fila = fila;
    }
    
    public String getDato(){
        return dato;
    }
    
    public void setDato(String dato){
        this.dato = dato;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    
}
