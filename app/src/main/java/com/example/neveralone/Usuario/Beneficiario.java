package com.example.neveralone.Usuario;

public class Beneficiario extends Usuario {
    private String direccion;

    public Beneficiario(String correo, String nombre, String apellidos) {
        super(correo, nombre, apellidos);
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
