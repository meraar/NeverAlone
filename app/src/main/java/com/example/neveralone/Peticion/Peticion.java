package com.example.neveralone.Peticion;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Peticion implements Serializable {

    private String user;
    private String uid;
    private String descripcion;
    private String categoria;
    private String fecha;
    private String hora;
    private Estado estado;

    public Peticion(){
        this.user           = null;
        this.descripcion    = null;
        this.categoria      = null;
        this.fecha          = null;
        this.hora           = null;
        this.estado         = null;
    }

    public Peticion(String user, String uid, String categoria, String fecha, String hora, String descripcion){
        this.user           = user;
        this.uid            = uid;
        this.descripcion    = descripcion;
        this.categoria      = categoria;
        this.fecha          = fecha;
        this.hora           = hora;
        this.estado         = Estado.PENDIENTE;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("user", user);
        result.put("categoria", categoria);
        result.put("fecha", fecha);
        result.put("hora", hora);
        result.put("descripcion", descripcion);
        result.put("estado", estado.toString());
        return result;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
