package com.example.neveralone;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.neveralone.Usuario.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ValueEventListenerAdapter implements ValueEventListener {
    private static Usuario us;
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()) {
            us = snapshot.getValue(Usuario.class);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    public static Usuario getUser() {
        return us;
    }

}
