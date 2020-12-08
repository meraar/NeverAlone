package com.example.neveralone.Activity.Chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neveralone.Activity.CrearChatActivity;
import com.example.neveralone.Activity.ListaChat.relacionChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

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
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private String nombre_2,nombre2,nombre1;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private ImageButton btnEnviarFoto;
    private String idUsuario1, idUsuario2, idPeticion;

    private AdaptadorMessage adaptador, adaptador_2;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference, databaseReference_2,databaseReference_3,databaseReference_4;
    //private FirebaseStorage storage;
    //private StorageReference storageReference;

    private static final int PHOTO_SEND = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        System.out.println("Acabo de entrar en el MessageActivity");

        fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        nombre = (TextView) findViewById(R.id.nombre);


        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFoto = (ImageButton) findViewById(R.id.btnEnviarFoto);

        database = FirebaseDatabase.getInstance();
        //storage = FirebaseStorage.getInstance();

        adaptador = new AdaptadorMessage(this);
        adaptador_2 = new AdaptadorMessage(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adaptador);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            idUsuario1 = (String) b.getString("idUsuario1");
            idUsuario2 = (String) b.getString("idUsuario2");
            idPeticion = (String) b.getString("idPeticion");
            nombre2 = (String) b.getString("nombre2");
            nombre1 = (String) b.getString("nombre1");
            Log.i("nombreChat",nombre2);
        }
        System.out.println("Hola: ");

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios/" + idUsuario2 + "/nombre");
        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nombre_captura = dataSnapshot.getValue(String.class);
                        nombre.setText(nombre_captura);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        databaseReference_2 = FirebaseDatabase.getInstance().getReference("Usuarios/" + idUsuario1 + "/nombre");
        databaseReference_2.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        nombre_2 = dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        databaseReference = database.getReference("chat/"+idUsuario1);
        databaseReference_2 = database.getReference("chat/"+idUsuario2);
        databaseReference_3 = database.getReference("ChatPeticion/"+idUsuario1);
        databaseReference_4 = database.getReference("ChatPeticion/"+idUsuario2);

        btnEnviar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (txtMensaje.getText().toString().equals("")) {
                    Toast.makeText(MessageActivity.this, "No hay nada escrito", Toast.LENGTH_SHORT).show();
                }
                else {
                   databaseReference.push().setValue(new Message(txtMensaje.getText().toString(), nombre2, "", "1", "00:00",idPeticion));
                   databaseReference_2.push().setValue(new Message(txtMensaje.getText().toString(), nombre1, "", "1", "00:00",idPeticion));
                   databaseReference_3.push().setValue(new relacionChat(idUsuario2,nombre2,idPeticion));
                   databaseReference_4.push().setValue(new relacionChat(idUsuario1,nombre1,idPeticion));
                    //databaseReference.push().setValue(new Message(txtMensaje.getText().toString(), nombre.getText().toString(), "", "1", "00:00"));
                   txtMensaje.setText(null);
                }
            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Para poder acceder a la galeria del movil
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                // Solo imagenes jpeg
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                // Al pasar una imagen esta sea evaluada por un código, del intento que acabamos de crear. Si es correcto se devuelve un 1
                startActivityForResult(Intent.createChooser(i, "Selecciona una foto"), PHOTO_SEND);
            }
        });

        adaptador.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message m = snapshot.getValue(Message.class);
                adaptador.addMessage(m);
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

        databaseReference_2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message m = snapshot.getValue(Message.class);
                adaptador_2.addMessage(m);
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
        rvMensajes.scrollToPosition(adaptador.getItemCount()-1);
    }

    // Al elegir un intent (imagen) que nos devuelva un resultado resultCode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Miramos que la request del intent imagen se ha seleccionado correctamente y que el resultado es OK
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Al seleccionar correctamente una imagen, nos devuelve una uri
            // en esta variable
            Uri u = data.getData();
        //    storageReference = storage.getReference("imagenes_chat"); //imagenes_chat

        }
    }
}

