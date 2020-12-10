package com.example.neveralone.Usuario;

public class Usuario {
    private String email;
    public String nombre;
    private String apellidos;
    private String dni;
    private String codigopostal;
    private Float puntuacioMedia;
    private Boolean voluntario;
    private String direccion;
    private String piso_puerta;
    private String motivo;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPiso_puerta() {
        return piso_puerta;
    }

    public void setPiso_puerta(String piso_puerta) {
        this.piso_puerta = piso_puerta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Boolean getVoluntario() { return voluntario; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public float getPuntuacioMedia() {
        return puntuacioMedia;
    }

    public void setPuntuacioMedia(Float puntuacioMedia) {
        this.puntuacioMedia = puntuacioMedia;
    }

    public void setVoluntario(Boolean voluntario) {
        this.voluntario = voluntario;
    }

}

