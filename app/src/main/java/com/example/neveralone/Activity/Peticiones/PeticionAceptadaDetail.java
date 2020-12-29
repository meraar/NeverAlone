package com.example.neveralone.Activity.Peticiones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PeticionAceptadaDetail extends AppCompatActivity {

    private TextView categoria, fecha, hora, descripcion, estado, autor,voluntarios;
    private Button borrar,editar,aceptar, chat;
    private Context context;
    private DatabaseReference reference;
    private Peticion p; //peticion que ens arriba desde lista de peticions al intent
    private List<Usuario> elements;
    private AdaptadorUsers listAdapter;
    private RecyclerView recyclerView;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference database2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticiondetail);
        context      = this;
        reference    = FirebaseDatabase.getInstance().getReference();
        Intent i     = getIntent();
        p            = (Peticion) i.getSerializableExtra("Peticion");
    }
}
