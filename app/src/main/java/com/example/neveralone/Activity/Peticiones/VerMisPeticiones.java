package com.example.neveralone.Activity.Peticiones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VerMisPeticiones extends AppCompatActivity {

    private List<Peticion> elements;
    private String uid;
    DatabaseReference reference;
    FirebaseUser user;
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
       // uid = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User-Peticiones").child("1PyehrVEgSZfzxZfPf7cYY2wWK52").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Peticion p = ds.getValue(Peticion.class);
                    elements.add(p);
                }
                Adaptador listAdapter = new Adaptador(elements,context);
                RecyclerView recyclerView = findViewById(R.id.listRecycleView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //elements.add(new Peticion("user", "xd", "mercadona", "e", "e", "String descripcion"));



        //pedir peticiones i cargarlas

    }
}
