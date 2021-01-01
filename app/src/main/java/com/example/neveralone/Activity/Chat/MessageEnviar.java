package com.example.neveralone.Activity.Chat;

import java.util.Map;

public class MessageEnviar extends Message {
    private Map hora;

    public MessageEnviar(String userID) {
    }

    public MessageEnviar(Map hora) {
        this.hora = hora;
    }

    public MessageEnviar(String mensaje, String nombre, String fotoPerfil, String idPeticion, Map hora) {
        super(mensaje, nombre, fotoPerfil, idPeticion);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
