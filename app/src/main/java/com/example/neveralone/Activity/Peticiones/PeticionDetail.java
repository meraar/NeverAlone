package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.Activity.CrearChatActivity;
import com.example.neveralone.Activity.ListaContactosActivity;
import com.example.neveralone.ChatPeticion;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PeticionDetail extends AppCompatActivity implements View.OnClickListener {

    TextView categoria, fecha, hora, descripcion, estado, autor;
    private Button crear;

    private EditText txtpostalcode, txtdni, txtDireccion, txtPisoPuerta;
    private Button btnFinalizarRegistro, btnAtras;
    private String correo, nombre, apellido, password;
    private Boolean voluntario;



    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference database2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiondetail);
        crear = findViewById(R.id.crearButton);
        crear.setOnClickListener(this);

        init();
    }

    private void init() {
        Intent i = getIntent();
        Peticion p = (Peticion) i.getSerializableExtra("Peticion");
        //Peticion n = new Peticion("Pepe","","Compras","12/11/20","18:30","Caca");
        categoria   = findViewById(R.id.CategoriaPeticion);
        fecha       = findViewById(R.id.fechaAct);
        descripcion = findViewById(R.id.contenidoDescripción);
        estado      = findViewById(R.id.estado);
        autor       = findViewById(R.id.CreadorPeticion);
        hora        = findViewById(R.id.horaAct);

        categoria.setText(p.getCategoria());
        fecha.setText(p.getFecha());
        hora.setText(p.getHora());
        descripcion.setText(p.getDescripcion());
        estado.setText(p.getEstado().toString());
        autor.setText(p.getUser());
    }

    public void onClick(View v) {
        Bundle b = new Bundle();
        Intent i = new Intent(this,CrearChatActivity.class);
        Intent gi = getIntent();
        final Peticion p = (Peticion) gi.getSerializableExtra("Peticion");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Log.i("mensajepeti11111", p.toString());

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idUsuario1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final String idUsuario2 = "0PUqBwfOVMWEbO0lKskG78KrTe83";
                final String idPeticion = "MMpED1-eu8vb7GODf9M";
                Log.i("mensajepeti", idPeticion);

                DatabaseReference reference = database.getReference("ChatPeticion/" + idUsuario1 + "/" + idPeticion + "/idUsuario2");
                ChatPeticion cp = new ChatPeticion(idUsuario2);
                reference.setValue(idUsuario2);
                Toast.makeText(PeticionDetail.this, "Se creado el chat", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PeticionDetail.this, ListaContactosActivity.class));

            }
        });



    }


}

