package com.example.neveralone.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.ChatPeticion;
import com.example.neveralone.Holder.ContactoViewHolder;
import com.example.neveralone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class   ListaContactosActivity extends AppCompatActivity {
    private static final String NODO_CHAT_PETICIONES = "ChatPeticion";
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView rvUsuario;
    private ArrayList<String> peticionesList;
    private ArrayList<String> userList;
    private DatabaseReference reference, ref;

    /*
    MessageActivity --> ListaContactosActivity
    Message --> Usuario
    HolderMessage --> ContactoViewHolder
    AdaptadorMessage --> Lo tienes que crear
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        rvUsuario = findViewById(R.id.rvUsuario);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvUsuario.setLayoutManager(linearLayoutManager);
        final String  userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        peticionesList = new ArrayList<String>();

        reference = FirebaseDatabase.getInstance().getReference().child(NODO_CHAT_PETICIONES).child(userID);
        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Get map of peticiones in datasnapshot
                        for (DataSnapshot dsp : snapshot.getChildren()) {
                            peticionesList.add(dsp.getKey());
                        }

                        userList = new ArrayList<String>();

                        for (String peticion : peticionesList) {

                            ref = FirebaseDatabase.getInstance().getReference().child(NODO_CHAT_PETICIONES).child(userID).child(peticion);

                            ref.addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            //Get map of peticiones in datasnapshot

                                            for (DataSnapshot dsp : snapshot.getChildren()) {
                                                userList.add(dsp.getKey());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    }
                            );
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


        /*for (String peticion : peticionesList) {
            System.out.println("Hola3");

            ref = FirebaseDatabase.getInstance().getReference().child(NODO_CHAT_PETICIONES).child(userID).child(peticion);
            System.out.println("Hola4");

            ref.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Get map of peticiones in datasnapshot
                            for (DataSnapshot dsp : snapshot.getChildren()) {
                                System.out.println("Usuario:");
                                System.out.println(dsp.getKey()); // IDs de los usuarios
                                userList.add(dsp.getKey());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );
        }*/

            /*FirebaseRecyclerOptions<ChatPeticion> options = new FirebaseRecyclerOptions.Builder<ChatPeticion>().setQuery(query, ChatPeticion.class).build();
            adapter = new FirebaseRecyclerAdapter<ChatPeticion, ContactoViewHolder>(options) {
                @Override
                public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_contacto, parent, false);
                    return new ContactoViewHolder(view);
                }

                @Override
                protected void onBindViewHolder(final ContactoViewHolder holder, int position, final ChatPeticion  model) {
                    //Glide.with(ListaContactosActivity.this).load(model.getFotoPerfil()).into(holder.getFotoPerfil());
                    //Nombre contacto: se tendra que hacer hacemos un get de nombre en firebase
                    reference = FirebaseDatabase.getInstance().getReference("Usuarios/" + model.getidUsuario2() + "/nombre");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String nombre = dataSnapshot.getValue(String.class);
                            holder.getTxtNombreUsurio().setText(nombre);
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }
            };
        }
        Query query = FirebaseDatabase.getInstance().getReference().child(NODO_CHAT_PETICIONES).child(userID);

        FirebaseRecyclerOptions<ChatPeticion> options = new FirebaseRecyclerOptions.Builder<ChatPeticion>().setQuery(query, ChatPeticion.class).build();

        //Log.i("mensaje2",options.toString());
        adapter = new FirebaseRecyclerAdapter<ChatPeticion, ContactoViewHolder>(options) {
            @Override
            public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_contacto, parent, false);
                return new ContactoViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final ContactoViewHolder holder, int position, final ChatPeticion  model) {
                //Glide.with(ListaContactosActivity.this).load(model.getFotoPerfil()).into(holder.getFotoPerfil());
                //Nombre contacto: se tendra que hacer hacemos un get de nombre en firebase
                reference = FirebaseDatabase.getInstance().getReference("Usuarios/" + model.getidUsuario2() + "/nombre");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nombre = dataSnapshot.getValue(String.class);
                        holder.getTxtNombreUsurio().setText(nombre);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
        };
        rvUsuario.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/

    }
}