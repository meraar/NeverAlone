package com.example.neveralone;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.neveralone.Usuario.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Semaphore;

public class ValueEventListenerAdapter implements ValueEventListener {
    private static Semaphore semaphore = new Semaphore(0);
    private static Usuario us;
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()) {

            us = snapshot.getValue(Usuario.class);
            semaphore.release();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    public static Usuario getUser() {
        return us;
    }

    public static Semaphore getSemaphore() {
        return semaphore;
    }

}
