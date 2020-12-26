
package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RegisterVolunteerActivity extends AppCompatActivity {

    private EditText txtpostalcode, txtdni;
    private Button btnFinalizarRegistro, btnAtras;
    private String correo, nombre, apellido, password;
    private Boolean voluntario;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sacamos los valores de la tuberia
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            correo = (String)b.getString("correo");
            nombre = (String)b.getString("nombre");
            apellido = (String)b.getString("apellido");
            password = (String)b.getString("password");
            voluntario = (Boolean)b.getBoolean("voluntario");
        }
        System.out.println("VOlunrrio =  "+ voluntario );
        if (voluntario){
            setContentView(R.layout.activity_registervolunteer); // FUnciona
            btnAtras = findViewById(R.id.idVolverAtras);
            btnFinalizarRegistro = findViewById(R.id.idRegistroRegistrar);
            txtpostalcode = findViewById(R.id.user_postalcode);
            txtdni = findViewById(R.id.idDNI);

            mAuth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();

            btnFinalizarRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String postalcode = txtpostalcode.getText().toString();
                    final String dni = txtdni.getText().toString();
                    final String motivo, direccion, piso;
                    final Float puntuacion = 0.0f;
                    motivo = "null"; // Guardamos las siguientes variables como null ya que el voluntario no tiene estos atributos.
                    direccion = "null";
                    piso = "null";
                    //final Voluntario voluntario = new Voluntario(correo, nombre, apellido, dni, postalcode);
                    if(comprobarCampos(postalcode, dni)){
                        mAuth.createUserWithEmailAndPassword(correo, password)
                                .addOnCompleteListener(RegisterVolunteerActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Hemos comprobado que si una sola linea de codigo falla, toda la tarea dentro del if no se ejecuta.
                                        if (task.isSuccessful()) {
                                            //System.out.println("Debug5: Se ha registrado");
                                            Toast.makeText(RegisterVolunteerActivity.this, "Se registro correctamente", Toast.LENGTH_SHORT).show();
                                            FirebaseUser currentUser = mAuth.getCurrentUser();
                                            DatabaseReference reference = database.getReference("Usuarios/" + currentUser.getUid());
                                            Usuario usuario = new Usuario();
                                            usuario.setUid(currentUser.getUid());
                                            usuario.setEmail(correo);
                                            usuario.setNombre(nombre);
                                            usuario.setApellidos(apellido);
                                            usuario.setDni(dni);
                                            usuario.setCodigopostal(postalcode);
                                            usuario.setMotivo(motivo);
                                            usuario.setDireccion(direccion);
                                            usuario.setPiso_puerta(piso);
                                            usuario.setPuntuacioMedia(puntuacion);
                                            usuario.setVoluntario(true);
                                            reference.setValue(usuario);

                                            //SetDisplayName of the User
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(nombre).build();
                                            currentUser.updateProfile(profileUpdates);

                                            // verficar el mail
                                            currentUser.sendEmailVerification();
                                            startActivity(new Intent(RegisterVolunteerActivity.this,LoginActivity.class));
                                            finish();
                                        } else {
                                            System.out.println("Debug5: No se ha registrado");
                                            Toast.makeText(RegisterVolunteerActivity.this, "Error al registrarse.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });


        }
        else {
            setContentView(R.layout.activity_registerbeneficiario); // Funciona
            btnAtras = findViewById(R.id.AtrasBut);
        }

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterVolunteerActivity.this,RegisterActivity.class));
                finish();
            }
        });



    }
    /**
     private void configEditText() {
     txtpostalcode = findViewById(R.id.user_postalcode);
     txtdni = findViewById(R.id.idDNI);
     txtpostalcode.addTextChangedListener(campos);
     txtdni.addTextChangedListener(campos);
     }
     **/
    private boolean comprobarCampos(@NotNull String cpEntero, String dniEntero) {

        boolean cP = !(cpEntero.length() != 5); //true
        if(!cP){
            txtpostalcode.setError("El código postal debe ser de 5 dígitos");
            return false;
        } else {
            txtpostalcode.setError(null);
        }

        if (dniEntero.length() != 9) {
            txtdni.setError("El DNI debe ser de 9 dígitos");
            return false;
        } else {
            txtdni.setError(null);
        }

        String numeros = dniEntero.substring(0,8);
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

        return true;
    }

}