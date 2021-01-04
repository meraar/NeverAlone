package com.example.neveralone.ui.contactos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neveralone.Activity.Chat.MessageActivity;
import com.example.neveralone.Activity.ListaChat.ElementosDeLista;
import com.example.neveralone.Activity.ListaChat.ListaAdaptador;
import com.example.neveralone.Activity.Peticiones.AdaptadorUsers;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListaDeContactosFragment extends Fragment {

    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private Context context;
    private ListaAdaptador listaAdaptador;
    private static List<ElementosDeLista> elementos;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_listadecontactos, container, false);
        recyclerView = root.findViewById(R.id.ListaRecycleView);
        context = getContext();
        init();
        return root;
    }

    public void init() {
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("ContactoTutoria/" + userID);
        elementos = new ArrayList<>();
        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                        for (DataSnapshot dsp : snapshot.getChildren()) {
                                String nombre = dsp.child("friendName").getValue().toString();
                                String id = dsp.child("idFriendUser").getValue().toString();
                                elementos.add(new ElementosDeLista(id, nombre, "Tutor"));
                            }
                        }

                        reference = FirebaseDatabase.getInstance().getReference().child("ChatPeticion/" + userID);
                        reference.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        //Get map of peticiones in datasnapshot
                                        if (snapshot.exists()) {
                                            for (DataSnapshot dsp : snapshot.getChildren()) {
                                                String peticion = dsp.child("idPeticion").getValue().toString();
                                                String nombre = dsp.child("friendName").getValue().toString();
                                                String id = dsp.child("idFriendUser").getValue().toString();
                                                elementos.add(new ElementosDeLista(id, nombre, peticion));
                                            }
                                        }

                                        listaAdaptador = new ListaAdaptador(elementos, context, new ListaAdaptador.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(ElementosDeLista item) {
                                                Intent i = new Intent(context, MessageActivity.class);
                                                //info que se pasa a chat de mensajes
                                                i.putExtra("idCurrentUser", userID);
                                                i.putExtra("idFriendUser", item.getId());
                                                i.putExtra("idPeticion", item.getIdPeticion());
                                                i.putExtra("nameFriendUser", item.getNombre());
                                                startActivity(i);
                                            }});

                                        recyclerView.setHasFixedSize(true);
                                        //recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                        recyclerView.setAdapter(listaAdaptador);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                }
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

}