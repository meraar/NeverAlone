package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterBenefactorActivity extends AppCompatActivity {

    private EditText txtpostalcode, txtdni, txtDireccion, txtPisoPuerta;
    private Button btnFinalizarRegistro, btnAtras;
    private String correo, nombre, apellido, password;
    private Boolean voluntario;



    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference database2;

    private TextWatcher campos = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String codigopostal = txtpostalcode.getText().toString().trim();
            String dniText = txtdni.getText().toString().trim();

            //register.setEnabled(!codigopostal.isEmpty() && !dniText.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            correo = (String)b.getString("correo");
            nombre = (String)b.getString("nombre");
            apellido = (String)b.getString("apellido");
            password = (String)b.getString("password");
            voluntario = (Boolean)b.getBoolean("voluntario");
        }

        if(!voluntario) {
            setContentView(R.layout.activity_registerbeneficiario);
            btnAtras = findViewById(R.id.AtrasBut);
            btnFinalizarRegistro = findViewById(R.id.RegistrarBut);
            txtpostalcode = findViewById(R.id.CodigoPostalTxt);
            txtdni = findViewById(R.id.DNITxt);
            txtDireccion = findViewById(R.id.DireccionTxt);
            txtPisoPuerta = findViewById(R.id.PisoPuertaTxt);

            Spinner spinner = (Spinner) findViewById(R.id.Spinner);
            String[] valores = {"Soy una persona mayor", "Problemas inmunológicos", "Otros"};
            spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));

            mAuth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            database2 = FirebaseDatabase.getInstance().getReference("Usuarios/prueba");

            //configEditText();

            btnFinalizarRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String postalcode = txtpostalcode.getText().toString();
                    final String dni = txtdni.getText().toString();
                    final String direccion = txtDireccion.getText().toString();
                    final String PisoPuerta = txtPisoPuerta.getText().toString();
                    final Double puntuacion = 0.0;
                    Spinner spinner = (Spinner)findViewById(R.id.Spinner);
                    final String motivo = spinner.getSelectedItem().toString();

                    if (comprobarCampos(postalcode, dni, direccion, PisoPuerta)) {
                        mAuth.createUserWithEmailAndPassword(correo, password)
                                .addOnCompleteListener(RegisterBenefactorActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterBenefactorActivity.this, "Se registro correctamente", Toast.LENGTH_SHORT).show();
                                            FirebaseUser currentUser = mAuth.getCurrentUser();
                                            DatabaseReference reference = database.getReference("Usuarios/" + currentUser.getUid());
                                            Usuario usuario = new Usuario();
                                            usuario.setEmail(correo);
                                            usuario.setNombre(nombre);
                                            usuario.setApellidos(apellido);
                                            usuario.setDni(dni);
                                            usuario.setCodigopostal(postalcode);
                                            usuario.setMotivo(motivo);
                                            usuario.setDireccion(direccion);
                                            usuario.setPiso_puerta(PisoPuerta);
                                            usuario.setPuntuacioMedia(puntuacion);
                                            usuario.setVoluntario(false);
                                            reference.setValue(usuario);
                                            //verficar el mail
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            user.sendEmailVerification();
                                            startActivity(new Intent(RegisterBenefactorActivity.this, LoginActivity.class));
                                            finish();


                                        } else {
                                            System.out.println("Debug5: No se ha registrado");
                                            Toast.makeText(RegisterBenefactorActivity.this, "Error al registrarse.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
        }

        else {
            setContentView(R.layout.activity_registervolunteer); // Funciona
            btnAtras = findViewById(R.id.AtrasBut);
        }

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterBenefactorActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
    private boolean comprobarCampos(String  CPostal, String dni, String Dire, String PisoPuerta) {

        boolean cP = !(CPostal.length() != 5); //true
        if(!cP){
            txtpostalcode.setError("El código postal debe ser de 5 dígitos");
            return false;
        } else {
            txtpostalcode.setError(null);
        }

        if (dni.length() != 9) {
            txtdni.setError("El DNI debe ser de 9 dígitos");
            return false;
        } else {
            txtdni.setError(null);
        }

        String numeros = dni.substring(0,8);
        //Log.d("MyApp",numeros);
        String letra = txtdni.getText().toString().substring(8);
        //Log.d("MyApp",letra);
        Boolean dniB = numeros.matches("[0-9]+") && Pattern.matches("[a-zA-Z]+",letra);
        Log.d("MyApp",dniB.toString());
        if (!dniB){
            txtdni.setError("Introduzca un DNI válido, por ejemplo: 23345432K");
            return false;
        } else {
            txtdni.setError(null);
        }
        //Log.d("MyApp",dni.toString());
/*
        boolean pispuert = false;
        int PisoPuertalength = PisoPuerta.length();
        if (PisoPuertalength == 3 || PisoPuertalength == 4) {
            boolean PisoPuertaValidos;
            if (PisoPuertalength == 3) {
                String Piso = PisoPuerta.substring(0,1);
                String coma = PisoPuerta.substring(1);
                String Puerta = PisoPuerta.substring(2);
                PisoPuertaValidos = Piso.matches("[0-9]+") && Puerta.matches("[0-9]+") && coma == ",";
            }
            else {
                String Piso = PisoPuerta.substring(0,2);
                String coma = PisoPuerta.substring(2);
                String Puerta = PisoPuerta.substring(3);
                PisoPuertaValidos = Piso.matches("[0-9]+") && Puerta.matches("[0-9]+") && coma == ",";
            }
            if (PisoPuertaValidos) pispuert = true;
        }
        if(!pispuert){
            txtPisoPuerta.setError("Introduzca el piso y la puerta separados por una coma");
            return false;
        } else {
            txtPisoPuerta.setError(null);
        }
 */

        return true;
    }
}