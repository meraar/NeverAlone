package com.example.neveralone.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

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

    private View contenedorBeneficiario;
    private View contenedorVoluntario;

    private EditText txtNombre, txtCorreo, txtContrasena, txtContrasenaRepetida;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        contenedorBeneficiario = findViewById(R.id.contenedor_Beneficiario);
        contenedorVoluntario = findViewById(R.id.contenedor_Voluntario);
    }

    public void onRadioButtonClicked(View view) {
        // Acciones
        boolean marcado = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.idRadioButtonBeneficiario:
                if (marcado) {
                    changeView(true);
                }
                break;

            case R.id.idRadioButtonVoluntario:
                if (marcado) {
                    changeView(false);
                }
                break;
        }
    }

    private void changeView(boolean b) {
        contenedorBeneficiario.setVisibility(b ? View.VISIBLE : View.GONE);
        contenedorVoluntario.setVisibility(b ? View.GONE : View.VISIBLE);
    }



}
