package com.example.neveralone.Activity.Peticiones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Activity.Home;
import com.example.neveralone.Activity.LoginActivity;
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

public class VerMisPeticiones extends AppCompatActivity implements View.OnClickListener {

    private List<Peticion> elements;
    private String uid;
    private Button crear;
    private Adaptador listAdapter;
    DatabaseReference reference;
    FirebaseUser user;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vermispeticiones);
        context = this;
        crear = findViewById(R.id.crearButton);
        crear.setOnClickListener(this);
        init();
    }




    private void init() {
        elements = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = "4IS1tZ6IrGbEqE2h6jXR05EeXCj1";

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User-Peticiones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements.clear();
                if(snapshot.exists()) {
                    snapshot = snapshot.child("4IS1tZ6IrGbEqE2h6jXR05EeXCj1");
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Peticion p = ds.getValue(Peticion.class);
                        elements.add(p);
                    }
                    listAdapter = new Adaptador(elements, context, new Adaptador.OnItemClickListener() {
                        @Override
                        public void onItemClick(Peticion p) {
                            moveToDescription(p);
                        }
                    });

                    RecyclerView recyclerView = findViewById(R.id.listRecycleView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(listAdapter);

                } else return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void moveToDescription(Peticion p) {
        Intent i = new Intent(context,PeticionDetail.class);
        i.putExtra("Peticion",p);
        startActivity(i);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context,CrearPeticionActivity.class);

        startActivityForResult(i,1);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listAdapter.notifyDataSetChanged();
                    }
                });
                //your code

            }
            if (resultCode == RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
}
