package com.example.neveralone.Usuario;

public class Beneficiario extends Usuario {
    private String direccion;

    public Beneficiario(String correo, String nombre, String apellidos, String dni, String postalcode) {
        super(correo, nombre, apellidos, dni, postalcode);
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
