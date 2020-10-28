package com.example.neveralone.Usuario;

import java.io.Serializable;

public class Voluntario extends Usuario implements Serializable {
    private Double puntuacion_media;

    public Voluntario(String correo, String nombre, String apellidos, String dni, String postalcode) {
        super(correo, nombre, apellidos, dni, postalcode);
        puntuacion_media = null;
    }

    public void setPostalCodeyDNI(String postalcode, String dni){
        super.setCodigopostal(postalcode);
        super.setDni(dni);
    }

    public Voluntario() {

    }

    public Double getPuntuacion_media() {
        return puntuacion_media;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion_media = puntuacion;
    }

    private void Calcular_puntuacion_media(){

    }

    public Usuario getUsuario() {
        return super.getUsuario();
    }
}
