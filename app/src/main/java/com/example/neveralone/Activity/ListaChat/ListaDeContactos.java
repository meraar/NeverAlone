package com.example.neveralone.Activity.ListaChat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Activity.Chat.MessageActivity;
import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaDeContactos extends AppCompatActivity {

    private DatabaseReference reference, ref;
    private ArrayList<String> peticionesList;
    private ArrayList<String> userList;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.listacontactos);
       init();
   }

    public void init() {
       final Context context= this;
        peticionesList = new ArrayList<String>();
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //reference = FirebaseDatabase.getInstance().getReference().child("ChatPeticion/4IS1tZ6IrGbEqE2h6jXR05EeXCj1");
        reference = FirebaseDatabase.getInstance().getReference().child("ChatPeticion/" + userID);
        Log.i("mensaje",reference.toString());
        reference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Get map of peticiones in datasnapshot
                        List<ElementosDeLista> elementos = new ArrayList<>();;
                        for (DataSnapshot dsp : snapshot.getChildren()) {
                            String peticion = dsp.getKey();
                            String nombre=dsp.child("nombre2").getValue().toString();
                            String id=dsp.child("nombre2").getValue().toString();
                            elementos.add(new ElementosDeLista(id, nombre, peticion));
                        }
                        ListaAdaptador listaAdaptador =new ListaAdaptador(elementos, context, new ListaAdaptador.OnItemClickListener() {
                            @Override
                            public void onItemClick(ElementosDeLista item) {
                                Intent i = new Intent(context, MessageActivity.class);
                                //info que se pasa a chat de mensajes
                                //i.putExtra("chat",item);
                                startActivity(i);

                            }
                        });
                        RecyclerView recyclerView= findViewById(R.id.ListaRecycleView);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
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
