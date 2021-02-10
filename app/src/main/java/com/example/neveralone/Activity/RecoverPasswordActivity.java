package com.example.neveralone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neveralone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecoverPasswordActivity extends AppCompatActivity {
    private EditText mail;
    private Button recover_button;
    private String correo = "";
    private FirebaseAuth auth;
    private ProgressDialog Progressdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        mail = findViewById(R.id.email);
        recover_button = findViewById(R.id.recuperar);
        auth = FirebaseAuth.getInstance();
        Progressdialog = new ProgressDialog(this);
    }

    public void resetPassword(android.view.View view){
        correo = mail.getText().toString();
        if(!correo.isEmpty()){

            Progressdialog.setMessage("Espere un momento ...");
            Progressdialog.setCanceledOnTouchOutside(false);

            auth.setLanguageCode("es"); // Para enviar el mensaje en español
            auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RecoverPasswordActivity.this, "Se ha enviado un mail a tu correo para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RecoverPasswordActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        mail.setError("Porfavor, introduce un mail registrado en NeverAlone.");
                        Toast.makeText(RecoverPasswordActivity.this, "No se pudo enviar el mail de restablecer la contraseña.", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(RecoverPasswordActivity.this, "Porfavor, introduce un mail registrado en NeverAlone", Toast.LENGTH_SHORT).show();

                    }
                    Progressdialog.dismiss();
                }
            });
        }
        else mail.setError("Debe introducir un correo registrado en NeverAlone.");

    }

}