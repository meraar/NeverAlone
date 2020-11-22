package com.example.neveralone.Activity.Peticiones;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;

public class PeticionDetail extends AppCompatActivity {

    TextView categoria, fecha, hora, descripcion, estado, autor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiondetail);

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


}

