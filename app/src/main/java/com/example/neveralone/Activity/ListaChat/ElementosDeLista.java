package com.example.neveralone.Activity.ListaChat;


import java.io.Serializable;

public class ElementosDeLista implements Serializable {
    private String idFriendUser;
    private String friendName;
    private String idPeticion;

    public ElementosDeLista(String id, String friendName, String idPeticion) {
        this.idFriendUser = id;
        this.friendName = friendName;
        this.idPeticion = idPeticion;
    }

    public String getId() {
        return idFriendUser;
    }

    public void setId(String id) {
        this.idFriendUser = id;
    }

    public String getNombre() {
        return friendName;
    }

    public void setNombre(String nombre) {
        this.friendName = nombre;
    }

    public String getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(String idPeticion) {
        this.idPeticion = idPeticion;
    }
}

