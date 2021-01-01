package com.example.neveralone.Activity.ListaChat;


import java.io.Serializable;

public class ElementosDeLista implements Serializable {
    private String id;
    private String nombre;
    private String idPeticion;

    public ElementosDeLista(String id, String nombre, String idPeticion) {
        this.id = id;
        this.nombre = nombre;
        this.idPeticion = idPeticion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

