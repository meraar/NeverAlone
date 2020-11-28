package com.example.neveralone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.neveralone.ChatPeticion;
import com.example.neveralone.Holder.ContactoViewHolder;
import com.example.neveralone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class   ListaContactosActivity extends AppCompatActivity {
    private static final String NODO_CHAT_PETICIONES = "ChatPeticion";
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView rvUsuario;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        rvUsuario=findViewById(R.id.rvUsuario);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        rvUsuario.setLayoutManager(linearLayoutManager );
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = FirebaseDatabase.getInstance().getReference().child(NODO_CHAT_PETICIONES).child(userID);
        Log.i("mensaje",query.toString());

        FirebaseRecyclerOptions<ChatPeticion> options = new FirebaseRecyclerOptions.Builder<ChatPeticion>().setQuery(query, ChatPeticion.class).build();

        Log.i("mensaje2",options.toString());
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
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
    }
}