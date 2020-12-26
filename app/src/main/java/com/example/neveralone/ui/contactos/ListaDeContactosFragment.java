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

import java.util.ArrayList;
import java.util.List;

public class ListaDeContactosFragment extends Fragment {

    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private Context context;
    private ListaAdaptador listaAdaptador;


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
        reference = FirebaseDatabase.getInstance().getReference().child("ChatPeticion/" + userID);
        Log.i("mensaje", reference.toString());
        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Get map of peticiones in datasnapshot
                        List<ElementosDeLista> elementos = new ArrayList<>();
                        System.out.println("hola!! antes del for");
                        for (DataSnapshot dsp : snapshot.getChildren()) {
                            System.out.println("hola!! estoy dentro del for");

                            String peticion = dsp.getKey();
                            System.out.println("peticion " + peticion);
                            String nombre = dsp.child("friendName").getValue().toString();
                            System.out.println("nombre " + nombre);
                            elementos.add(new ElementosDeLista(nombre, peticion));
                        }
                        System.out.println("elementos " + elementos.size());

                        listaAdaptador = new ListaAdaptador(elementos, context, new ListaAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(ElementosDeLista item) {
                                Intent i = new Intent(context, MessageActivity.class);
                                //info que se pasa a chat de mensajes
                                //i.putExtra("chat",item);
                                startActivity(i);
                            }});
                        System.out.println("item count " + listaAdaptador.getItemCount());

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

}