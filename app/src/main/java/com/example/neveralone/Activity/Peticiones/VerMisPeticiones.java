package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

public class VerMisPeticiones extends AppCompatActivity {

    private boolean voluntario = false;
    private List<Peticion> elements;
    private String uid;
    private DatabaseReference reference;
    private FirebaseUser user;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vermispeticiones);
        context = this;
        init();
    }

    private void init() {
        elements = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        //Ver si es voluntario o beneficiario
        reference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if (ds.getValue().toString().equals(uid)) {
                        String Vol_or_Ben = ds.child("voluntario").getValue().toString();
                        if (Vol_or_Ben.equals("true")) voluntario = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Si es voluntario desplegamos peticiones hechas por el
        if (voluntario) {
            reference.child("User-Peticiones").child("4gGXfo84A6aKnNF6NgO1jqMTLpI3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Peticion p = ds.getValue(Peticion.class);
                        elements.add(p);
                    }
                    Adaptador listAdapter = new Adaptador(elements, context, new Adaptador.OnItemClickListener() {
                        @Override
                        public void onItemClick(Peticion p) {
                            AcceptOrNot(p);
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
        //Si es beneficiario desplegamos todas las peticiones existentes
        else {
            reference.child("Peticiones").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Peticion p = ds.getValue(Peticion.class);
                        elements.add(p);
                    }
                    Adaptador listAdapter = new Adaptador(elements, context, new Adaptador.OnItemClickListener() {
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
    }

    private void moveToDescription(Peticion p) {
        Intent i = new Intent(context,PeticionDetail.class);
        i.putExtra("Peticion",p);
        startActivity(i);
    }

    private void AcceptOrNot(Peticion p) {
        Intent i = new Intent(context,Accept_Refuse_Peticion.class);
        i.putExtra("Peticion",p);
        startActivity(i);
    }

}
