package com.example.neveralone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;

public class RegisterVolunteerActivity extends AppCompatActivity {

    private EditText codigoPostal, dni;
    private Button register, back;
    private TextWatcher campos = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String codigopostal = codigoPostal.getText().toString().trim();
            String dniText = dni.getText().toString().trim();

            register.setEnabled(!codigopostal.isEmpty() && !dniText.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registervolunteer);

        configEditText();
        configButtons();
    }

    private void configEditText() {
        codigoPostal = findViewById(R.id.user_postalcode);
        dni = findViewById(R.id.idDNI);


        codigoPostal.addTextChangedListener(campos);
        dni.addTextChangedListener(campos);
    }

    private void configButtons() {
        register = findViewById(R.id.idRegistroRegistrar);
        back = findViewById(R.id.idVolverAtras);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterVolunteerActivity.this, RegisterActivity.class);
                startActivity(intent);

            }


        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comprobarCampos()){
                    /*Intent intent = new Intent(RegisterVolunteer.this, PaginaPrincipalVoluntario.class);
                    startActivity(intent);*/
                }else{

                }
            }

        });
    }

    private boolean comprobarCampos() {
        String cpEntero = codigoPostal.getText().toString();
        String dniEntero = dni.getText().toString();

        boolean cP = !(cpEntero.length() != 5); //true
        if(!cP){
            codigoPostal.setError("El código postal debe ser de 5 dígitos");
            return false;
        } else {
            codigoPostal.setError(null);
        }

        if (dniEntero.length() != 9) {
            dni.setError("El DNI debe ser de 9 dígitos");
            return false;
        } else {
            dni.setError(null);
        }

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

        return true;
    }

}
