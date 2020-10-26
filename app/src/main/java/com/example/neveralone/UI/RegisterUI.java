package com.example.neveralone.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.Activity.FirebaseActivity;
import com.example.neveralone.R;



public class RegisterUI extends AppCompatActivity {

    /*TODO
     1. Contraseña: min 10 caráct. 1 minús., 1 mayús. y 1 caráct. especial (!, @, #, $,%).
     2. Añadir un campo para repetir la contraseña.
     3. Se ha aceptado y leído la ley 45/2015.
     4. Se debe confirmar el registro.
     5. email válido (@gmail, @hotmail, etc..)
     6. voluntario: opcional tutor, añadir un certificado de voluntario y la foto de tu dni. CIUDAD, CÓD. POSTAL
     7. Beneficiario,opcional motivo vulnerable (persona mayor, enfermedades previa, otros). VIVIENDA (piso, calle, codigo postal y dirección).
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onRadioButtonClicked(View view) {

    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterUI.class);
        startActivity(intent);
    }

    public boolean correctPassword(String pass, String pass2){
        //TODO comprovar que la contraseña sea correcta (1 simbolo, 1 mayuscula...)
        if (pass.contentEquals(pass2)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void onClickContinue(View v) {
        EditText email = findViewById(R.id.emailAddressRegister);
        EditText pass = findViewById(R.id.passRegister1);
        EditText pass2 = findViewById(R.id.passRegister2);
        FirebaseActivity fa = new FirebaseActivity();
        if (fa.verifyRegister(email)) {
            //register(email.getText(),pass.getText());
        }
    }


}
