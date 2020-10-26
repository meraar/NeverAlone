package com.example.neveralone.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Usuario u = new Usuario();
        EditText email = findViewById(R.id.emailAddressLogin);
        EditText pass = findViewById(R.id.passLogin);
        String mail = email.getText().toString();
        if (verifyEmail(mail)) { //TODO falta mirar que tenga un nombre antes de @ y que solo haya 1 @
            u.setEmail(mail);
            u.setPassword(hashSha256(pass.getText().toString()));
        } else {
            //TODO mensaje de error, color en rojo, a parte el toast y en el register, la contraseña es too weak
            Context context = getApplicationContext();
            CharSequence text = "Correo electrónico no válido!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }

    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterUI.class);
        startActivity(intent);
    }

    public void registerWithGoogle() {

    }

    private boolean verifyEmail(String email) {
        return (email.contains("@hotmail.com") || email.contains("@gmail.com") || email.contains("@hotmail.es") || email.contains("@yahoo.com"));
    }

    private String hashSha256(String passw) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance( "SHA-256" );
            final byte[] data = passw.getBytes();
            return toHexString(sha256.digest(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passw;
    }


    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
