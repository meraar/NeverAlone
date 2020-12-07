package com.example.neveralone.Papelera;


import com.example.neveralone.Peticion.Estado;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChatPeticion implements Serializable {


    private String idUsuario2;
    public ChatPeticion(){
        this.idUsuario2 = null;
    }

    public ChatPeticion(String idUsuario2){
        this.idUsuario2    = idUsuario2;
    }

    public String getidUsuario2() {
        return idUsuario2;
    }

    public void setidUsuario2(String idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idUsuario2", idUsuario2);
        return result;
    }

}

