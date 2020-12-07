package com.example.neveralone.Activity.ListaChat;


public class ElementosDeLista {
    private String nombre;
    private String idPeticion;

    public ElementosDeLista(String nombre, String idPeticion) {
        this.nombre = nombre;
        this.idPeticion = idPeticion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(String idPeticion) {
        this.idPeticion = idPeticion;
    }
}

