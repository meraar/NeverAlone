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
import android.widget.Switch;
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
    private Spinner spinnerfilter;
    //private TextView titol;
    private RecyclerView recyclerView;
    private String TipoPeticion;
    private Switch pSwitch;
    private TextView titolSwitch;


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

        spinnerfilter = root.findViewById(R.id.spinnerfilter);
        String[] valores = {"Todo","Compras", "Asesoramiento", "Acompañamiento", "Otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerfilter.setAdapter(adapter);


        titolSwitch = root.findViewById(R.id.titleSwitch);
        recyclerView = root.findViewById(R.id.listRecycleView);
        pSwitch      = root.findViewById(R.id.switchPeticiones);
        pSwitch.setVisibility(View.GONE);
        titolSwitch.setVisibility(View.GONE);
        init();
        return root;
    }


    private void init() {
        TipoPeticion = null;
        if(spinnerfilter != null && spinnerfilter.getSelectedItem() !=null ) {
            TipoPeticion = (String)spinnerfilter.getSelectedItem();
        }
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
                            if(TipoPeticion != "Todo")  {//o not null
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

//TODO MIO
        spinnerfilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Reload fragment al cambiar Item
                init();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }//TODO

    private void moveToDescription(Peticion p) {
        Intent i = new Intent(context, PeticionDetail.class);
        i.putExtra("Peticion",p);
        i.putExtra("switch", false);
        startActivity(i);
    }


}