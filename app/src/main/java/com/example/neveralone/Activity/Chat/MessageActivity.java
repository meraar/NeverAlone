package com.example.neveralone.Activity.Chat;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ServerValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neveralone.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private String nameFriendUser;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private String idCurrentUser, idFriendUser, idPeticion;

    private AdaptadorMessage adaptador_currentUser, adaptador_friendUser;

    private DatabaseReference databaseReference_currentUser, databaseReference_friendUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        nombre = (TextView) findViewById(R.id.nombre);

        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        adaptador_currentUser = new AdaptadorMessage(this);
        adaptador_friendUser = new AdaptadorMessage(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adaptador_currentUser);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            idCurrentUser = (String) b.getString("idCurrentUser"); //current
            idFriendUser = (String) b.getString("idFriendUser");
            idPeticion = (String) b.getString("idPeticion");
            nameFriendUser = (String) b.getString("nameFriendUser");
        }

        nombre.setText(nameFriendUser);

        databaseReference_currentUser = FirebaseDatabase.getInstance().getReference("Chat/" + idCurrentUser + "/" + idFriendUser);
        databaseReference_friendUser = FirebaseDatabase.getInstance().getReference("Chat/" + idFriendUser + "/" + idCurrentUser);

        btnEnviar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (txtMensaje.getText().toString().equals("")) {
                    Toast.makeText(MessageActivity.this, "No hay nada escrito", Toast.LENGTH_SHORT).show();
                }
                else {
                   databaseReference_currentUser.push().setValue(new MessageEnviar(txtMensaje.getText().toString(), idCurrentUser, "", "1", idPeticion, ServerValue.TIMESTAMP));
                   databaseReference_friendUser.push().setValue(new MessageEnviar(txtMensaje.getText().toString(), idCurrentUser, "", "1", idPeticion, ServerValue.TIMESTAMP));
                   txtMensaje.setText(null);
                }
            }
        });

        adaptador_currentUser.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference_currentUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageRecibir m = snapshot.getValue(MessageRecibir.class);
                adaptador_currentUser.addMessage(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference_friendUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageRecibir m = snapshot.getValue(MessageRecibir.class);
                adaptador_friendUser.addMessage(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // Hace que el scroll sea automático al enviar muchos mensajes
    private void setScrollbar() {
        rvMensajes.scrollToPosition(adaptador_currentUser.getItemCount()-1);
    }
}

