package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PeticionesAceptadas extends AppCompatActivity {

    private List<Peticion> elements;
    private String uid;
    private DatabaseReference reference;
    private FirebaseUser user;
    private Context context;
    private String tusuari;
    private TextView titol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vermispeticiones);

        context = this;

        init();
    }

    private void init() {
        elements = new ArrayList<>();
        titol = findViewById(R.id.textView5);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        //Si es Voluntario desplegamos peticiones existentes

        titol.setText("Peticiones Aceptadas");
        reference.child("PeticionesAceptadas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.getValue().toString().equals(user.getUid())){
                        for(DataSnapshot users : ds.getChildren()){
                            Peticion p = users.getValue(Peticion.class);
                            elements.add(p);
                        }
                    }
                }
                Adaptador listAdapter = new Adaptador(elements, context, new Adaptador.OnItemClickListener() {
                        @Override
                        public void onItemClick(Peticion p) { moveToDescription(p);
                        }
                    });
                RecyclerView recyclerView = findViewById(R.id.listRecycleView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void moveToDescription(Peticion p) {
        Intent i = new Intent(context,PeticionAceptadaDetail.class);
        i.putExtra("Peticion",p);
        i.putExtra("Tipus", tusuari);
        startActivity(i);
    }
}