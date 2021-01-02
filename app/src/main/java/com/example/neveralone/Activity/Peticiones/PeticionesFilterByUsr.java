package com.example.neveralone.Activity.Peticiones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.Activity.RegisterBenefactorActivity;
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

public class PeticionesFilterByUsr extends AppCompatActivity {
    private List<Peticion> elements;
    private DatabaseReference reference;
    private Context context;
    private TextView titol;
    private String IdUsrBenefactor;
    private Button BtnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiones_filter_by_usr);
        BtnAtras = findViewById(R.id.BtnAtras);
        context = this;

        init();
    }

    private void init() {
        elements = new ArrayList<>();
        titol = findViewById(R.id.textView5);

        reference = FirebaseDatabase.getInstance().getReference();

        IdUsrBenefactor = getIntent().getExtras().getString("UserId");

        titol.setText("Sus Peticiones");
        reference.child("User-Peticiones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getKey().equals(IdUsrBenefactor)){
                        for(DataSnapshot ds2:ds.getChildren()){
                            Peticion p = ds2.getValue(Peticion.class);
                            elements.add(p);
                        }
                    }
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
        BtnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PeticionesFilterByUsr.this, LoginActivity.class));
                finish();
            }
        });    }

    private void moveToDescription(Peticion p) {
        Intent i = new Intent(context,PeticionDetail.class);
        i.putExtra("Peticion",p);
        i.putExtra("Tipus", "Voluntario");
        startActivity(i);
    }
}