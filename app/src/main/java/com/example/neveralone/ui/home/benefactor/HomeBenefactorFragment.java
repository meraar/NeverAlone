package com.example.neveralone.ui.home.benefactor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Activity.Peticiones.Adaptador;
import com.example.neveralone.Activity.Peticiones.CrearPeticionActivity;
import com.example.neveralone.Activity.Peticiones.PeticionDetail;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeBenefactorFragment extends Fragment {
    private FloatingActionButton fab;
    private List<Peticion> elements;
    private String uid;
    private Adaptador listAdapter;
    private DatabaseReference reference;
    private FirebaseUser user;
    private Context context;
    private RecyclerView recyclerView;
    private TextView titol;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_homebenefactor, container, false);

        //boton flotante crear peticion
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CrearPeticionActivity.class);
                i.putExtra("Origen","Crear");
                startActivity(i);
            }
        });
        context = getContext();
        titol = root.findViewById(R.id.textView5);
        recyclerView = root.findViewById(R.id.listRecycleView);
        init();
        return root;
    }

    private void init() {
        elements = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        titol.setText("Mis Peticiones");
        reference.child("User-Peticiones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getKey().equals(uid)){
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
        Intent i = new Intent(context, PeticionDetail.class);
        i.putExtra("Peticion",p);
        i.putExtra("switch", false);
        startActivity(i);
    }

}