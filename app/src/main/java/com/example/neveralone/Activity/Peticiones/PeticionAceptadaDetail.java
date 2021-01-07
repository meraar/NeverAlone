package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Peticion.Estado;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PeticionAceptadaDetail extends AppCompatActivity {

    private TextView categoria, fecha, hora, descripcion, estado, autor,voluntarios;
    private Button cancelar;
    private Context context;
    private DatabaseReference reference;
    private Peticion p; //peticion que ens arriba desde lista de peticions al intent
    private List<Usuario> elements;
    private AdaptadorUsers listAdapter;
    private RecyclerView recyclerView;
    private FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticionaceptadadetail);
        context      = this;
        reference    = FirebaseDatabase.getInstance().getReference();
        Intent i     = getIntent();
        p            = (Peticion) i.getSerializableExtra("Peticion");
        init();
    }

    private void init() {
        user             = FirebaseAuth.getInstance().getCurrentUser();
        elements         = new ArrayList<>();
        categoria        = findViewById(R.id.CategoriaPeticion);
        fecha            = findViewById(R.id.fechaAct);
        descripcion      = findViewById(R.id.contenidoDescripción);
        estado           = findViewById(R.id.estado);
        autor            = findViewById(R.id.CreadorPeticion);
        hora             = findViewById(R.id.horaAct);
        cancelar         = findViewById(R.id.dejar);

        recyclerView     = findViewById(R.id.recycleViewUsers);
        voluntarios      = findViewById(R.id.tituloVoluntarios);

        categoria.setText(p.getCategoria());
        fecha.setText(p.getFecha());
        hora.setText(p.getHora());
        descripcion.setText(p.getDescripcion());
        estado.setText(p.getEstado().toString());
        autor.setText(p.getUser());

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("¿Está seguro que quiere cancelar la peticion?")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Actualizar estado de la peticion
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Peticiones");

                                reference.child(p.getPeticionID()).child("estado").setValue(Estado.PENDIENTE);

                                reference = FirebaseDatabase.getInstance().getReference().child("User-Peticiones");

                                reference.child(p.getUid()).child(p.getPeticionID()).child("estado").setValue(Estado.PENDIENTE);

                                //Borrar interacciones
                                reference = FirebaseDatabase.getInstance().getReference().child("Interacciones");
                                String as = p.getPeticionID();
                                reference.child(p.getPeticionID()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChildren()) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                //TODO Borrar tots menys l'usuari al qui has acceptat
                                                //TODO Necessito que l'usuari guari el seu UID de firebase
                                                //TODO O sino, simplement borro tots els voluntaris i ya
                                                ds.getRef().removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                // Borrar instancias de peticiones aceptadas

                                reference = FirebaseDatabase.getInstance().getReference().child("PeticionesAceptadas");
                                String a = reference.getKey();
                                reference.child(user.getUid()).removeValue();
                                reference = FirebaseDatabase.getInstance().getReference().child("PeticionesAceptadas");
                                String b = reference.getKey();
                                reference.child(p.getUid()).removeValue();
                                finish();

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
        initVoluntarios();
    }

    private void initVoluntarios() {
        reference.child("Interacciones").child(p.getPeticionID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Usuario p = ds.getValue(Usuario.class);
                    elements.add(p);
                }

                listAdapter = new AdaptadorUsers(elements, context, new AdaptadorUsers.OnItemClickListenerBorrar() {
                    @Override
                    public void onItemClickborrar(int adapterPosition) {

                    }
                }, new AdaptadorUsers.OnItemClickListener() {
                    @Override
                    public void onItemClick(final int pos) {

                    }
                }, p);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(listAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}
