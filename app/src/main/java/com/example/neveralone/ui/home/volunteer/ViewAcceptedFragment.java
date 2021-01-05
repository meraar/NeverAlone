package com.example.neveralone.ui.home.volunteer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Activity.Peticiones.Adaptador;
import com.example.neveralone.Activity.Peticiones.PeticionAceptadaDetail;
import com.example.neveralone.Activity.Peticiones.PeticionDetail;
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

public class ViewAcceptedFragment extends Fragment {
    private List<Peticion> elements;
    private String uid;
    private DatabaseReference reference;
    private FirebaseUser user;
    private Context context;
    private Switch pSwitch;
    private TextView titolSwitch;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_vol_view_requests, container, false);
        context = getContext();
        titolSwitch = root.findViewById(R.id.titleSwitch);
        recyclerView = root.findViewById(R.id.listRecycleView);
        pSwitch      = root.findViewById(R.id.switchPeticiones);

        pSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    init("pendientes");
                    titolSwitch.setText("Ver peticiones aceptadas");
                } else {
                    // The toggle is disabled
                    init("aceptados");
                    titolSwitch.setText("Ver peticiones pendientes");
                }
            }
        });

        init("aceptados");
        return root;
    }

    private void init(String tipo) {
        elements = new ArrayList<>();


        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        //Si es Voluntario desplegamos peticiones existentes

        if (tipo.equals("aceptados")){
            reference.child("PeticionesAceptadas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    elements = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String nodo = ds.getKey();

                        if (nodo.equals(user.getUid())) {
                            for (DataSnapshot users : ds.getChildren()) {
                                Peticion p = users.getValue(Peticion.class);
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
        }else{
            reference.child("Pendientes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    elements = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String nodo = ds.getKey();

                        if (nodo.equals(user.getUid())) {
                            for (DataSnapshot users : ds.getChildren()) {
                                Peticion p = users.getValue(Peticion.class);
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
    }

    private void moveToDescription(Peticion p) {
        Intent i = new Intent(context, PeticionDetail.class);
        i.putExtra("Peticion",p);
        i.putExtra("switch", pSwitch.isChecked());
        startActivity(i);
    }

}
