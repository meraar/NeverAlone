package com.example.neveralone.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;

public class RegisterActivity extends AppCompatActivity {

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
