package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.Activity.Chat.MessageActivity;
import com.example.neveralone.Papelera.ChatPeticion;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PeticionDetail extends AppCompatActivity {

    private TextView categoria, fecha, hora, descripcion, estado, autor;
    private Button borrar,editar,aceptar,enviarmensaje;
    private Context context;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference database2;


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
        enviarmensaje          = findViewById(R.id.enviarMensaje);

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

        enviarmensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cambiar por los datos de la peticion
                final String idUsuario1 = "yyZIf6aLmFYPklgpAtJwkSfNOqV2";
                String idUsuario2 = "";
                String nombre2= p.getUser();
                Log.i("nombre2",nombre2);
                final String idPeticion = "MMMmVvp4TTw3UPIJV07";
                if (idUsuario1.equals("4IS1tZ6IrGbEqE2h6jXR05EeXCj1")) {
                    idUsuario2 = "yyZIf6aLmFYPklgpAtJwkSfNOqV2";
                }
                else if (idUsuario1.equals("yyZIf6aLmFYPklgpAtJwkSfNOqV2")) {
                    idUsuario2 = "4IS1tZ6IrGbEqE2h6jXR05EeXCj1";
                }

                ChatPeticion cp = new ChatPeticion(idUsuario2);

                Toast.makeText(PeticionDetail.this, "Se creado el chat", Toast.LENGTH_SHORT).show();
                Bundle b = new Bundle();
                b.putString("idUsuario1", idUsuario1);
                b.putString("idUsuario2", idUsuario2);
                b.putString("idPeticion", idPeticion);
                b.putString("nombre2",p.getUser());
                b.putString("nombre1","nombre1");//cambiar nombre 1 por nombre del corrent user

                Intent intent = new Intent(PeticionDetail.this, MessageActivity.class);
                intent.putExtras(b);

                startActivity(intent);
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

