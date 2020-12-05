package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.Activity.Home;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PeticionDetail extends AppCompatActivity {

    private TextView categoria, fecha, hora, descripcion, estado, autor;
    private Button borrar,editar,aceptar;
    private Context context;
    private DatabaseReference reference;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiondetail);
        context = this;
        init();
    }

    private void init() {
        //Intent i = getIntent();
        //Peticion p = (Peticion) i.getSerializableExtra("Peticion");
        final Peticion p = new Peticion("-MNOlUcIUGjHJPHn6WCX", "Eric", "4IS1tZ6IrGbEqE2h6jXR05EeXCj1","Compras","30/11/2020","17:29","");
        categoria        = findViewById(R.id.CategoriaPeticion);
        fecha            = findViewById(R.id.fechaAct);
        descripcion      = findViewById(R.id.contenidoDescripción);
        estado           = findViewById(R.id.estado);
        autor            = findViewById(R.id.CreadorPeticion);
        hora             = findViewById(R.id.horaAct);
        borrar           = findViewById(R.id.borrar);
        editar           = findViewById(R.id.editar);
        aceptar          = findViewById(R.id.ofrecer);

        editar.setText("Editar");
        borrar.setText("Borrar");

        aceptar.setVisibility(View.GONE);

        categoria.setText(p.getCategoria());
        fecha.setText(p.getFecha());
        hora.setText(p.getHora());
        descripcion.setText(p.getDescripcion());
        estado.setText(p.getEstado().toString());
        autor.setText(p.getUser());

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditarPeticion.class);
                i.putExtra("Peticion", p.getPeticionID());
                startActivity(i);
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("‚Está seguro que quiere borrar la petición?")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                reference = FirebaseDatabase.getInstance().getReference();
                                reference.child("Peticiones").child(p.getPeticionID()).removeValue();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }


}

