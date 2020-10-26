package com.example.neveralone.Activity;

import android.widget.EditText;

import com.example.neveralone.Usuario.Beneficiario;
import com.example.neveralone.Usuario.Voluntario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    public FirebaseActivity() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }
    public boolean verifyRegister(EditText email) {
        //TODO Usuario existente, usuario con email incorrecto ("hotmail)
        return true;
    }
    public void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password);

    }

    public void registerBeneficiario(Beneficiario b) { //aqui paso los datos
        register(b.getEmail(), b.getPassword());
        //TODO añadir los valores necesarios
    }

    public void registerVoluntario(Voluntario v) { //aqui paso los datos
    }






}
