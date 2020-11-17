package com.example.neveralone.Activity.Peticiones;

public class ListElement {

    private String user, color, estado, peticion;

    public ListElement(String user, String color, String estado, String peticion) {
        this.user = user;
        this.color = color;
        this.estado = estado;
        this.peticion = peticion;
    }

    public String getPeticion() {
        return peticion;
    }

    public void setPeticion(String peticion) {
        this.peticion = peticion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
