package com.example.neveralone.Activity.ListaChat;

public class RelacionChat {
    private String idUs1;
    private String idUs2;
    private String nombre2;
    private String idPeticion;

    public RelacionChat(String idUs2, String nombre2, String idPeticion) {
        this.idUs2 = idUs2;
        this.nombre2 = nombre2;
        this.idPeticion = idPeticion;
    }

    public String getIdUs1() {
        return idUs1;
    }

    public void setIdUs1(String idUs1) {
        this.idUs1 = idUs1;
    }

    public String getIdUs2() {
        return idUs2;
    }

    public void setIdUs2(String idUs2) {
        this.idUs2 = idUs2;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getidPeticion() {
        return idPeticion;
    }

    public void setidPeticion(String idPeticion) {
        this.idPeticion = idPeticion;
    }
}
