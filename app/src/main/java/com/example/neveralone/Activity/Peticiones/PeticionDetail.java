package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Activity.Chat.MessageActivity;
import com.example.neveralone.Activity.ListaChat.RelacionChat;
import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.Peticion.Estado;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private TextView categoria, fecha, hora, descripcion, estado, autor,voluntarios;
    private Button borrar,editar,aceptar, chat, cancelar, dejar;
    private Context context;
    private DatabaseReference reference;
    private Peticion p; //peticion que ens arriba desde lista de peticions al intent
    private List<Usuario> elements;
    private AdaptadorUsers listAdapter;
    private RecyclerView recyclerView;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference database2;
    private DatabaseReference database3;
    private Boolean checked;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiondetail);
        context      = this;
        reference    = FirebaseDatabase.getInstance().getReference();
        Intent i     = getIntent();
        p            = (Peticion) i.getSerializableExtra("Peticion");
        checked      = (Boolean) i.getSerializableExtra("switch"); //checked tru = pendientes
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        elements = new ArrayList<>();
        categoria        = findViewById(R.id.CategoriaPeticion);
        fecha            = findViewById(R.id.fechaAct);
        descripcion      = findViewById(R.id.contenidoDescripción);
        estado           = findViewById(R.id.estado);
        autor            = findViewById(R.id.CreadorPeticion);
        hora             = findViewById(R.id.horaAct);
        borrar           = findViewById(R.id.borrar);
        editar           = findViewById(R.id.editar);
        aceptar          = findViewById(R.id.ofrecer);
        recyclerView     = findViewById(R.id.recycleViewUsers);
        voluntarios      = findViewById(R.id.tituloVoluntarios);
        cancelar         = findViewById(R.id.dejar);
        dejar            = findViewById(R.id.dejarP);

        chat = findViewById(R.id.chat);

        categoria.setText(p.getCategoria());
        fecha.setText(p.getFecha());
        hora.setText(p.getHora());
        descripcion.setText(p.getDescripcion());
        estado.setText(p.getEstado().toString());
        autor.setText(p.getUser());

        if(!LoginActivity.getUserType()){ //beneficiario
            editar.setText("Editar");
            borrar.setText("Borrar");
            aceptar.setVisibility(View.GONE);
            chat.setVisibility(View.GONE);
        }else{
            borrar.setVisibility(View.GONE);
            editar.setVisibility(View.GONE);
            voluntarios.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }

        if(p.getEstado().equals(Estado.CURSO)) {
            aceptar.setVisibility(View.GONE);
            borrar.setVisibility(View.GONE);
        }

        if(!(p.getEstado().equals(Estado.CURSO)) && !checked){ //cuando es pendiente y no viene de pendiente del voluntario
            cancelar.setVisibility(View.GONE);
            dejar.setVisibility(View.GONE);
        }else if((p.getEstado().equals(Estado.PENDIENTE)) && checked){ // es para ver el switch de pendientes
            aceptar.setVisibility(View.GONE);
            aceptar.setEnabled(false);
            cancelar.setVisibility(View.GONE);
        }else{ //esta en curso y viene de beneficiario
            dejar.setVisibility(View.GONE);
            editar.setVisibility(View.GONE);
        }

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

                                reference.child(p.getPeticionID()).removeValue();

                                // Borrar instancias de peticiones aceptadas

                                if(LoginActivity.getUserType()) {
                                    reference = FirebaseDatabase.getInstance().getReference().child("PeticionesAceptadas");
                                    reference.child(user.getUid()).removeValue();

                                    reference = FirebaseDatabase.getInstance().getReference().child("PeticionesAceptadas");
                                    reference.child(p.getUid()).removeValue();
                                }else{
                                    reference = FirebaseDatabase.getInstance().getReference().child("PeticionesAceptadas");
                                    reference.child(user.getUid()).removeValue();

                                    reference = FirebaseDatabase.getInstance().getReference().child("PeticionesAceptadas");
                                    reference.child(elements.get(0).getUid()).removeValue(); //el jambo voluntari
                                }

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

        dejar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference();
                reference.child("Pendientes").child(user.getUid()).removeValue();
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCurrentUser = user.getUid();
                String idFriendUser = p.getUid();
                String idPeticion = p.getPeticionID();

                DatabaseReference databaseReference_3 = database.getInstance().getReference("ChatPeticion/"+idCurrentUser);
                DatabaseReference databaseReference_4 = database.getInstance().getReference("ChatPeticion/"+idFriendUser);
                databaseReference_3.push().setValue(new RelacionChat(idFriendUser,p.getUser(),idPeticion));
                databaseReference_4.push().setValue(new RelacionChat(idCurrentUser,user.getDisplayName(),idPeticion));

                Bundle b = new Bundle();
                b.putString("idCurrentUser", idCurrentUser);
                b.putString("idFriendUser", idFriendUser);
                b.putString("idPeticion", idPeticion);
                b.putString("nameFriendUser",p.getUser());

                Intent intent = new Intent(PeticionDetail.this, MessageActivity.class);
                intent.putExtras(b);

                startActivity(intent);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CrearPeticionActivity.class);
                i.putExtra("Origen","Editar");
                i.putExtra("Peticion", p.getPeticionID());
                startActivityForResult(i,1);
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("¿Está seguro que quiere borrar la petición?")
                        .setCancelable(false)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                reference = FirebaseDatabase.getInstance().getReference();
                                reference.child("Peticiones").child(p.getPeticionID()).removeValue();
                                reference.child("User-Peticiones").child(user.getUid()).child(p.getPeticionID()).removeValue();
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

                reference = FirebaseDatabase.getInstance().getReference();

                reference.child("Usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Usuario voluntario = snapshot.child(user.getUid()).getValue(Usuario.class);
                        reference = FirebaseDatabase.getInstance().getReference();
                        //String key = reference.child("Interacciones").push().getKey();

                        Map<String, Object> postValues = new HashMap<>();
                        Map<String, Object> postValue2 = p.toMap();

                        postValues.put("uid", user.getUid());
                        postValues.put("nombre", voluntario.getNombre());
                        postValues.put("apellidos", voluntario.getApellidos());

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/Interacciones/" + p.getPeticionID() + "/" + user.getUid(), postValues);
                        childUpdates.put("/Pendientes/" + user.getUid() + "/" + p.getPeticionID(), postValue2);

                        reference.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                listAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
                    Usuario p = ds.getValue(Usuario.class);

                    elements.add(p);
                }

                listAdapter = new AdaptadorUsers(elements, context, new AdaptadorUsers.OnItemClickListener() {
                    @Override
                    public void onItemClick(final int pos) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("¿Está seguro que quiere aceptar a este voluntario?")
                                .setCancelable(false)
                                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Actualizar estado de la peticion
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Peticiones");

                                        reference.child(p.getPeticionID()).child("estado").setValue(Estado.CURSO);

                                        reference = FirebaseDatabase.getInstance().getReference().child("User-Peticiones");

                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(p.getPeticionID()).child("estado").setValue(Estado.CURSO);

                                        //Borrar interacciones
                                        reference = FirebaseDatabase.getInstance().getReference().child("Interacciones");

                                        reference.child(p.getPeticionID()).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.hasChildren()) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        //TODO Borrar tots menys l'usuari al qui has acceptat
                                                        //TODO Necessito que l'usuari guari el seu UID de firebase
                                                        //TODO O sino, simplement borro tots els voluntaris i ya
                                                        String userToCheck = ds.child("uid").getValue().toString();
                                                        String userAccepted = elements.get(pos).getUid();
                                                        if (!(userToCheck.equals(userAccepted))) {
                                                            ds.getRef().removeValue();
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        // Crear una instancia de peticiones aceptadas

                                        reference = FirebaseDatabase.getInstance().getReference();

                                        String key = reference.child("PeticionesAceptadas").push().getKey();
                                        p.setEstado(Estado.CURSO);
                                        Map<String, Object> postValues = p.toMap();

                                        String uid = elements.get(pos).getUid();
                                        Map<String, Object> childUpdates = new HashMap<>();
                                        childUpdates.put("/PeticionesAceptadas/" + "/" + uid + "/" + key, postValues);
                                        childUpdates.put("/PeticionesAceptadas/" + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + key, postValues);
                                        reference.updateChildren(childUpdates);

                                        //Borrar pendientes

                                        reference = FirebaseDatabase.getInstance().getReference();

                                        reference.child("Pendientes").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.hasChildren()) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {

                                                            ds.child(p.getPeticionID()).getRef().removeValue();

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }}, p);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(listAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == PeticionDetail.RESULT_OK) {
                    p = (Peticion) data.getSerializableExtra("Peticion");
                    init();
                }
                break;
            }
        }
    }
}