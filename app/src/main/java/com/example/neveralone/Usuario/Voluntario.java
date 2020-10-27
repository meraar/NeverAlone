package com.example.neveralone.Usuario;

public class Voluntario extends Usuario {
    private Double puntuacion_media;

    public Voluntario(String correo, String nombre, String apellidos, String dni, String postalcode) {
        super(correo, nombre, apellidos, dni, postalcode);
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
