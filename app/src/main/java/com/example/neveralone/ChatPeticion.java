package com.example.neveralone;


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

/*
    private String uid;
    private String idPeticion;
    private String idUsuario1;
    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    private String fotoPerfil;
*/


   /* public String getidPeticion() {
        return idPeticion;
    }
    public void setidPeticion(String idPeticion) {
        this.idPeticion = idPeticion;
    }



    public String getidUsuario1() {
        return idUsuario1;
    }
    public void setidUsuario1(String idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }


*/
}

