package com.example.neveralone.Activity.Chat;

public class Message {

    private String mensaje;
    private String userID;
    private String fotoPerfil;
    private String type_mensaje;
    private String idPeticion;

    public Message() {
    }

    public Message(String mensaje, String userID, String fotoPerfil, String type_mensaje, String idPeticion) {
        this.mensaje = mensaje;
        this.userID = userID;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
        this.idPeticion = idPeticion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }

    public String getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(String idPeticion) {
        this.idPeticion = idPeticion;
    }
}
