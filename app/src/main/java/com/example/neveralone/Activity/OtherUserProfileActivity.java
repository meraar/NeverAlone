package com.example.neveralone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OtherUserProfileActivity extends AppCompatActivity {

    private TextView nombretxt, apellidotxt, dnitxt, codigo_postaltxt, direcciontxt, emailtxt;
    private TextView puntuacion_mediatxt, piso_puertatxt, motivotxt;

    private String user_uid;
    private ImageView profile_image;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Usuario usuario;
    private String id;

    private StorageReference storageReference;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        Intent i = getIntent();
        user_uid = (String) i.getSerializableExtra("uid");

        nombretxt = findViewById(R.id.Nombre);
        apellidotxt = findViewById(R.id.Apellido);
        dnitxt = findViewById(R.id.dni);
        emailtxt = findViewById(R.id.email);
        codigo_postaltxt = findViewById(R.id.codigo_postal);
        puntuacion_mediatxt = findViewById(R.id.puntuacion_media);
        piso_puertatxt = findViewById(R.id.piso_puerta);
        direcciontxt = findViewById(R.id.direccion);
        motivotxt = findViewById(R.id.motivo);

        initialize_data();
        initialize_photo();
    }

    private void initialize_data(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuarios").child(user_uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            usuario = snapshot.getValue(Usuario.class);

                            String nombre = usuario.getNombre();
                            nombretxt.setText(nombre);

                            String apellidos =  usuario.getApellidos();
                            apellidotxt.setText(apellidos);

                            String dni = usuario.getDni();
                            dnitxt.setText(dni);

                            //dnitxt.setEnabled(false);
                            String mail =  usuario.getEmail();
                            emailtxt.setText(mail);

                            //emailtxt.setEnabled(false);
                            String postal =  usuario.getCodigopostal();
                            codigo_postaltxt.setText(postal);

                            if (!usuario.getVoluntario()){
                                //first_direccion.setVisibility(View.VISIBLE);
                                //first_piso_puerta.setVisibility(View.VISIBLE);
                                //first_motivo.setVisibility(View.VISIBLE);

                                String motivo = usuario.getMotivo();
                                motivotxt.setText(motivo);
                                motivotxt.setVisibility(View.VISIBLE);
                                //motivotxt.setEnabled(false); // El motivo no se podra actualizar.
                                motivotxt.setVisibility(View.VISIBLE);

                                String direccion =usuario.getDireccion();
                                direcciontxt.setText(direccion);
                                direcciontxt.setVisibility(View.VISIBLE);

                                String puerta =  usuario.getPiso_puerta();
                                piso_puertatxt.setText(puerta);
                                piso_puertatxt.setVisibility(View.VISIBLE);

                            }
                            else{
                                //first_puntuacion.setVisibility(View.VISIBLE);
                                String puntuacion =  String.valueOf(usuario.getPuntuacioMedia());
                                puntuacion_mediatxt.setText(puntuacion);
                               // puntuacion_mediatxt.setEnabled(false);
                                puntuacion_mediatxt.setVisibility(View.VISIBLE);

                            }
                        } else {
                            Toast.makeText(OtherUserProfileActivity.this, "No hay información del usuario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                    }
                });
    }

    private void initialize_photo() {
        profile_image = findViewById(R.id.profile_image);
        String foto_name = user_uid + ".jpg";
        storageReference = FirebaseStorage.getInstance().getReference().child("profilesImages").child(foto_name);
        if (storageReference != null) {
            storageReference.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    profile_image.setImageBitmap(bitmap);
                }
            });
        }
    }
}