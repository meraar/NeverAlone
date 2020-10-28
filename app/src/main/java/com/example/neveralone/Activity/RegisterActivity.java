package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.Usuario.Beneficiario;
import com.example.neveralone.Usuario.Voluntario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.example.neveralone.Usuario.Usuario;
import com.example.neveralone.R;

import java.io.Serializable;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity{

    /*TODO
     1. Contraseña: min 10 caráct. 1 minús., 1 mayús. y 1 caráct. especial (!, @, #, $,%).
     2. Añadir un campo para repetir la contraseña.
     3. Se ha aceptado y leído la ley 45/2015.
     4. Se debe confirmar el registro.
     5. email válido (@gmail, @hotmail, etc..)
     6. voluntario: opcional tutor, añadir un certificado de voluntario y la foto de tu dni. CIUDAD, CÓD. POSTAL
     7. Beneficiario,opcional motivo vulnerable (persona mayor, enfermedades previa, otros). VIVIENDA (piso, calle, codigo postal y dirección).
    */
    private EditText txtNombre, txtApellido, txtCorreo, txtContrasena, txtContrasenaRepetida, txtpostalcode, txtdni;
    private RadioButton voluntario, beneficiario;
    private Button btnRegistrar, btnFinalizarRegistro, btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtNombre = findViewById(R.id.registerName);
        txtApellido = findViewById(R.id.registerSurname);
        txtCorreo = findViewById(R.id.emailAddressRegister);
        txtContrasena = findViewById(R.id.passRegister1);
        txtContrasenaRepetida = findViewById(R.id.passRegister2);
        voluntario = findViewById(R.id.radioButtonVolunteer);
        beneficiario = findViewById(R.id.radioButtonBeneficiary);
        btnRegistrar = findViewById(R.id.buttonRegisterContinue);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            private Voluntario volunteer;
            private Beneficiario benefactor;
            private String password;
            @Override
            public void onClick(View v) {
                final String correo = txtCorreo.getText().toString();
                final String nombre = txtNombre.getText().toString();
                final String apellido = txtApellido.getText().toString();
                password = txtContrasena.getText().toString();
                final Boolean vol = voluntario.isChecked();
                final Boolean ben = beneficiario.isChecked();
                if (isValidEmail(correo) && validarContrasena() && validarNombre(nombre, apellido) && validarchecked(vol,ben)) {
                    Intent intent = null;
                    if (vol) {
                        volunteer = new Voluntario(correo, nombre, apellido, null, null);
                        intent = new Intent(RegisterActivity.this,RegisterVolunteerActivity.class);
                        intent.putExtra("voluntario",(Serializable) this.volunteer);
                    } else if (ben) {
                        benefactor = new Beneficiario(correo, nombre, apellido, null, null);
                        intent = new Intent(RegisterActivity.this,RegisterBenefactorActivity.class);
                        intent.putExtra("beneficiario",(Serializable) this.benefactor);
                    }
                    //ENCRIPTAR CUANDO PUEDAS OKEY?
                    intent.putExtra("password",(Serializable) this.password);
                    startActivityForResult(intent, 0);
                    finish();
                }
            }
        });


    }

    private boolean comprobarCampos(String codigopostal, String dni) {
        if(codigopostal.length() != 5) {
            Toast.makeText(RegisterActivity.this, "El codigo postal ha de tener 5 digitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dni.length() != 9) {
            Toast.makeText(RegisterActivity.this, "El dni ha de tener 9 digitos.", Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         String numeros = dniEntero.substring(0,8);
         //Log.d("MyApp",numeros);
         String letra = dni.getText().toString().substring(8);
         //Log.d("MyApp",letra);
         Boolean dniB = numeros.matches("[0-9]+") && Pattern.matches("[a-zA-Z]+",letra);
         Log.d("MyApp",dniB.toString());
         if (!dniB){
         dni.setError("Introduzca un DNI válido, por ejemplo: 23345432K");
         return false;
         } else {
         dni.setError(null);
         }
         //Log.d("MyApp",dni.toString());
         **/
        return true;
    }

    private boolean validarchecked(boolean voluntario, boolean beneficiario){
        if(!voluntario && !beneficiario) {
            Toast.makeText(RegisterActivity.this, "Selecciona Voluntario o Beneficiario.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }

    private boolean isValidEmail(String email) {
        boolean valid = !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!valid) Toast.makeText(this, "Introduce un correo valido.", Toast.LENGTH_SHORT).show();
        return valid;
    }

    public boolean validarContrasena() {
        String contrasena, contrasenaRepetida;
        contrasena = txtContrasena.getText().toString();
        contrasenaRepetida = txtContrasenaRepetida.getText().toString();
        if (contrasena.equals(contrasenaRepetida)) {
            if (contrasena.length() >= 6 && contrasena.length() < 16) {
                return true;
            } else {
                Toast.makeText(this, "La contraseña ha de contener entre 6 y 15 carácteres.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validarNombre(String nombre, String Apellido) {
        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Apellido.isEmpty()) {
            Toast.makeText(this, "El apellido no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
}
