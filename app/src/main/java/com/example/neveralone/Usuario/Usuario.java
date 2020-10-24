package com.example.neveralone.Usuario;


import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.Activity.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usuario {
    private String email;
    private String nombre;
    private String apellidos;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario(String correo, String nombre, String apellidos){
        this.setEmail(correo);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
    }





}
