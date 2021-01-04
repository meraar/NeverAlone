package com.example.neveralone.Activity.Chat;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neveralone.Activity.ListaChat.RelacionChat;
import com.example.neveralone.Activity.ListaChat.RelacionChatTutor;
import com.example.neveralone.Activity.OtherUserProfileActivity;
import com.example.neveralone.Peticion.Peticion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private String nameFriendUser;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private String idCurrentUser, idFriendUser, idPeticion;
    private FirebaseUser user;

    private AdaptadorMessage adaptador_currentUser, adaptador_friendUser;

    private DatabaseReference databaseReference_currentUser, databaseReference_friendUser;

    private StorageReference storageReference;
    private Bitmap bitmap = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        fotoPerfil = findViewById(R.id.fotoPerfil);
        nombre = findViewById(R.id.nombre);

        rvMensajes = findViewById(R.id.rvMensajes);
        txtMensaje = findViewById(R.id.txtMensaje);
        btnEnviar = findViewById(R.id.btnEnviar);

        adaptador_currentUser = new AdaptadorMessage(this);
        adaptador_friendUser = new AdaptadorMessage(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adaptador_currentUser);

        user = FirebaseAuth.getInstance().getCurrentUser();


        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            idCurrentUser =  b.getString("idCurrentUser"); //current
            idFriendUser = b.getString("idFriendUser");
            idPeticion = b.getString("idPeticion");
            nameFriendUser = b.getString("nameFriendUser");
        }

        nombre.setText(nameFriendUser);

        //initialize_photo();

        nombre.setText(nameFriendUser);

        if (idPeticion.equals("Tutor")) {
            databaseReference_currentUser = FirebaseDatabase.getInstance().getReference("ChatTutor/" + idCurrentUser + "/" + idFriendUser);
            databaseReference_friendUser = FirebaseDatabase.getInstance().getReference("ChatTutor/" + idFriendUser + "/" + idCurrentUser);
            final DatabaseReference dbContactoTutorCurrentUser = FirebaseDatabase.getInstance().getReference("ContactoTutoria/"+ idCurrentUser);
            dbContactoTutorCurrentUser.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                DatabaseReference dbContactoTutorFriendUser = FirebaseDatabase.getInstance().getReference("ContactoTutoria/"+ idFriendUser);
                                dbContactoTutorCurrentUser.push().setValue(new RelacionChatTutor(idFriendUser, nameFriendUser));
                                dbContactoTutorFriendUser.push().setValue(new RelacionChatTutor(idCurrentUser, user.getDisplayName()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );
        }

        else {
            databaseReference_currentUser = FirebaseDatabase.getInstance().getReference("Chat/" + idCurrentUser + "/" + idFriendUser);
            databaseReference_friendUser = FirebaseDatabase.getInstance().getReference("Chat/" + idFriendUser + "/" + idCurrentUser);
        }

        nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this, OtherUserProfileActivity.class);
                intent.putExtra("uid", idFriendUser);
                startActivity(intent);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (txtMensaje.getText().toString().equals("")) {
                    Toast.makeText(MessageActivity.this, "No hay nada escrito", Toast.LENGTH_SHORT).show();
                }
                else {
                   databaseReference_currentUser.push().setValue(new MessageEnviar(txtMensaje.getText().toString(), idCurrentUser, "", idPeticion, ServerValue.TIMESTAMP));
                   databaseReference_friendUser.push().setValue(new MessageEnviar(txtMensaje.getText().toString(), idCurrentUser, "", idPeticion, ServerValue.TIMESTAMP));
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

    private void initialize_photo() {
        fotoPerfil = findViewById(R.id.profile_image);
        String foto_name = idFriendUser + ".jpg";
        storageReference = FirebaseStorage.getInstance().getReference().child("profilesImages").child(foto_name);
        storageReference.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                fotoPerfil.setImageBitmap(bitmap);
            }
        });
    }
}

