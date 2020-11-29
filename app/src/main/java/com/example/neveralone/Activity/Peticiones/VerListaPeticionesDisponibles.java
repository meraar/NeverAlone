package com.example.neveralone.Activity.Peticiones;

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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class VerListaPeticionesDisponibles extends AppCompatActivity {

    private List<Peticion> elements;
    private String uid;
    DatabaseReference Database;
    FirebaseUser user;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lista_peticiones_disponibles);
        context = this;
        init();
    }

    private void init() {
        elements = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        // uid = user.getUid();

        Database = FirebaseDatabase.getInstance().getReference();
        Database.child("Peticiones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Peticion p = ds.getValue(Peticion.class);
                    elements.add(p);
                }
                Adaptador listAdapter = new Adaptador(elements,context, new Adaptador.OnItemClickListener(){
                    @Override
                    public void onItemClick(Peticion p) {
                        moveToDescription(p);
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
        Intent i = new Intent(context,Accept_Refuse_Peticion.class);
        i.putExtra("Peticion",p);
        startActivity(i);
    }
}