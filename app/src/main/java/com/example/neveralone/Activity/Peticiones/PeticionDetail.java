package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Activity.Home;
import com.example.neveralone.Peticion.Estado;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeticionDetail extends AppCompatActivity {

    private TextView categoria, fecha, hora, descripcion, estado, autor;
    private Button borrar,editar,aceptar,enviarmensaje;
    private Context context;
    private DatabaseReference reference;
    private Peticion p; //peticion que ens arriba desde lista de peticions al intent
    private List<Usuario> elements;
    private AdaptadorUsers listAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiondetail);
        context = this;
        reference = FirebaseDatabase.getInstance().getReference();
        init();
    }

    private void init() {
        //Intent i = getIntent();
        //Peticion p = (Peticion) i.getSerializableExtra("Peticion");

        elements = new ArrayList<>();
        p = new Peticion("-MOaOzQr2wYxoKspiYVO", "Meraj", "np2Es3nr6bNZL93gUKYJZAznjZg2","Compras","24/12/2020","12:22","");
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
                                reference.child("User-Peticiones").child("np2Es3nr6bNZL93gUKYJZAznjZg2").child(p.getPeticionID()).removeValue();
                                reference.child("Interacciones").child(p.getPeticionID()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        listAdapter.notifyDataSetChanged();
                                    }
                                });
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

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Debe crear una instancia de Interacción

                String uidVoluntario = "zzQA9zhkm1ñkz2FLMF2fYrgJvzgi1";
                String name = "Maron";
                String apellido = "Riera";
                reference = FirebaseDatabase.getInstance().getReference();
                String key = reference.child("Interacciones").push().getKey();

                Map<String, Object> postValues = new HashMap<>();

                postValues.put("uid", uidVoluntario);
                postValues.put("user", name);
                postValues.put("apellidos", apellido);

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/Interacciones/" + p.getPeticionID() + "/" + uidVoluntario, postValues);

                reference.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        initVoluntarios();

    }

    private void initVoluntarios() {
        reference.child("Interacciones").child(p.getPeticionID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Usuario p = new Usuario("",(String) ds.child("user").getValue(),(String) ds.child("apellidos").getValue(),"","",true,0);
                    elements.add(p);

                }

                listAdapter = new AdaptadorUsers(elements, context, new AdaptadorUsers.OnItemClickListener() {
                    @Override
                    public void onItemClick(Usuario p) {
                        AceptarPeticion();
                    }}, p.getPeticionID());

                RecyclerView recyclerView = findViewById(R.id.recycleViewUsers);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(listAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void AceptarPeticion() {

    }

}