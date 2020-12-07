package com.example.neveralone.Activity.ListaChat;

import android.os.Bundle;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListaDeContactos extends AppCompatActivity {
    List<ElementosDeLista> elementos;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.listacontactos);
       init();
   }

    public void init() {
        elementos = new ArrayList<>();
        elementos.add(new ElementosDeLista("Eric","idpeticioxxx"));
        elementos.add(new ElementosDeLista("Pau","idpeticio2xxx"));
        elementos.add(new ElementosDeLista("Maria","idpeticio3xxx"));

        ListaAdaptador listaAdaptador =new ListaAdaptador(elementos,this);
        RecyclerView recyclerView= findViewById(R.id.ListaRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        recyclerView.setAdapter(listaAdaptador);

    }
}
