package com.example.neveralone.ui.home.volunteer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.Activity.Peticiones.Adaptador;
import com.example.neveralone.Activity.Peticiones.PeticionDetail;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.Peticion.TimePicker;
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


public class ViewReqFragment extends Fragment {
    private List<Peticion> elements;
    private String uid;
    private DatabaseReference reference;
    private FirebaseUser user;
    private Context context;
    private Spinner spinner;
    //private TextView titol;
    private RecyclerView recyclerView;
    private String TipoPeticion;
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

        Spinner spinner = (Spinner) root.findViewById(R.id.Spinner);
        String[] valores = {"Compras", "Asesoramiento", "Acompañamiento", "Otros"};
        spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, valores));

        TipoPeticion = null;
        if(spinner != null && spinner.getSelectedItem() !=null ) {
            TipoPeticion = (String)spinner.getSelectedItem();
        }

        //titol = root.findViewById(R.id.textView5);
        recyclerView = root.findViewById(R.id.listRecycleView);
        init();
        return root;
    }


    private void init() {
        elements = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        //Si es Voluntario desplegamos peticiones existentes
        if (LoginActivity.getUserType()) {

           //titol.setText("Peticiones");
            reference.child("Peticiones").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    elements = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.child("estado").getValue().toString().equals("PENDIENTE")) {
                            if(TipoPeticion != null)  {//o not null
                                if(ds.child("categoria").getValue().toString().equals(TipoPeticion)) {
                                    Peticion p = ds.getValue(Peticion.class);
                                    elements.add(p);
                                }
                            }
                            else {
                                Peticion p = ds.getValue(Peticion.class);
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

       //TODO borrar esta parte que es del beneficiario
        else {
            //titol.setText("Mis Peticiones");
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
        //TODO HASTA AQUI

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Reload fragment al cambiar Item
                Fragment currentFragment = getFragmentManager().findFragmentByTag("ViewReqFragment");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void moveToDescription(Peticion p) {
        Intent i = new Intent(context, PeticionDetail.class);
        i.putExtra("Peticion",p);
        startActivity(i);
    }


}