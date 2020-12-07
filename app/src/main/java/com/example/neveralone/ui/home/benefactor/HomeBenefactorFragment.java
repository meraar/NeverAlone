package com.example.neveralone.ui.home.benefactor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private Adaptador listAdapter;
    private DatabaseReference reference;
    private FirebaseUser user;
    private Context context;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_homebenefactor, container, false);

        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CrearPeticionActivity.class));
            }
        });
        context = getContext();
        recyclerView = root.findViewById(R.id.listRecycleView);
        init();
        return root;
    }

    private void init() {
        elements = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();

        //Intent i = getIntent();
        //SUFANG: DESCOMENTAR ESTO Y COMENTAR LINEA 65 PARA QUE TE FUNCIONE
        // final String userID = i.getStringExtra("UserId");
        final String userID = "4IS1tZ6IrGbEqE2h6jXR05EeXCj1";


        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User-Peticiones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements.clear();
                if(snapshot.exists()) {
                    snapshot = snapshot.child(userID);
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
        Intent i = new Intent(context, PeticionDetail.class);
        i.putExtra("Peticion",p);
        startActivity(i);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == getActivity().RESULT_OK) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listAdapter.notifyDataSetChanged();
                    }
                });
                //your code

            }
            if (resultCode == getActivity().RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

}