package com.example.neveralone.Activity.Peticiones;

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

        //Peticion p = (Peticion) getIntent().getSerializableExtra("Peticion");
        Peticion n = new Peticion("Pepe","","Compras","12/11/20","18:30","Caca");
        categoria   = findViewById(R.id.CategoriaPeticion);
        fecha       = findViewById(R.id.fecha_peticion);
        descripcion = findViewById(R.id.contenidoDescripción);
        estado      = findViewById(R.id.estado);
        autor       = findViewById(R.id.CreadorPeticion);
        hora        = findViewById(R.id.hora_peticion);

        categoria.setText(n.getCategoria());
        fecha.setText("Para el dia: " + n.getFecha());
        hora.setText("A las: " + n.getHora());
        descripcion.setText(n.getDescripcion());
        estado.setText(n.getEstado().toString());
        autor.setText(n.getUser());
    }


}

