package com.example.neveralone.ui.profile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private EditText nombretxt, apellidotxt, dnitxt, codigo_postaltxt, direcciontxt, emailtxt;
    private EditText puntuacion_mediatxt, piso_puertatxt, motivotxt;
    private TextInputLayout first_puntuacion, first_piso_puerta, first_direccion, first_motivo;
    private ImageView profile_image;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Usuario usuario;
    private String id;
    private StorageReference storageReference;
    private Bitmap bitmap = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        nombretxt = root.findViewById(R.id.Nombre);
        apellidotxt = root.findViewById(R.id.Apellido);
        dnitxt = root.findViewById(R.id.dni);
        emailtxt = root.findViewById(R.id.email);
        codigo_postaltxt = root.findViewById(R.id.codigo_postal);
        puntuacion_mediatxt = root.findViewById(R.id.puntuacion_media);
        piso_puertatxt = root.findViewById(R.id.piso_puerta);
        direcciontxt = root.findViewById(R.id.direccion);
        motivotxt = root.findViewById(R.id.motivo);
        first_piso_puerta = root.findViewById(R.id.firstpiso_puerta);
        first_direccion = root.findViewById(R.id.firstdireccion);
        first_puntuacion = root.findViewById(R.id.firstpuntuacion_media);
        first_motivo = root.findViewById(R.id.firstmotivo);
        profile_image = root.findViewById(R.id.profile_image);
        /*galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        Button updateButton = root.findViewById(R.id.ActualizarPerfil);
        updateButton.setOnClickListener(this);

        Button unsubscribeButton = root.findViewById(R.id.BajaUsuario);
        unsubscribeButton.setOnClickListener(this);

        CircleImageView imageButton = root.findViewById(R.id.profile_image);
        imageButton.setOnClickListener(this);
        initialize();
        initialize2();

        return root;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
                Uri imagen_uri = CropImage.getPickImageResultUri(getActivity(),data);

                CropImage.activity(imagen_uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setRequestedSize(640, 480)
                        .setAspectRatio(2, 2).start(getActivity());
            }

            if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == Activity.RESULT_OK) {
                    Uri resultUri = result.getUri();
                    File url = new File(resultUri.getPath());

                    Picasso.with(getActivity()).load(url).into(profile_image);
                    //Comprimir la imagen
                    try{
                        bitmap = new Compressor(getActivity()).setMaxWidth(640).setMaxHeight(480).setQuality(90).compressToBitmap(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    final byte [] bitmap_byte = byteArrayOutputStream.toByteArray();
                    final String foto_name = id + ".jpg";

                    final StorageReference ref = FirebaseStorage.getInstance().getReference().child("profilesImages").child(foto_name);
                    UploadTask uploadTask = ref.putBytes(bitmap_byte);

                    ref.putBytes(bitmap_byte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            getDownloadUrl(ref);
                        }
                    });
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDownloadUrl(StorageReference ref) {
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();

                user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Foto cargada correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "ERRROR: Foto no cargada", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initialize(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String h = user.getEmail().toString();
        id = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuarios").child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            usuario = snapshot.getValue(Usuario.class);
                            nombretxt.setText(usuario.getNombre());
                            apellidotxt.setText(usuario.getApellidos());
                            dnitxt.setText(usuario.getDni());
                            dnitxt.setEnabled(false);
                            emailtxt.setText(usuario.getEmail());
                            emailtxt.setEnabled(false);
                            codigo_postaltxt.setText(usuario.getCodigopostal());

                            if (!usuario.getVoluntario()){
                                first_direccion.setVisibility(View.VISIBLE);
                                first_piso_puerta.setVisibility(View.VISIBLE);
                                first_motivo.setVisibility(View.VISIBLE);

                                motivotxt.setText(usuario.getMotivo());
                                motivotxt.setVisibility(View.VISIBLE);
                                motivotxt.setEnabled(false); // El motivo no se podra actualizar.
                                motivotxt.setVisibility(View.VISIBLE);


                                direcciontxt.setText(usuario.getDireccion());
                                direcciontxt.setVisibility(View.VISIBLE);


                                piso_puertatxt.setText(usuario.getPiso_puerta());
                                piso_puertatxt.setVisibility(View.VISIBLE);

                            }
                            else{
                                first_puntuacion.setVisibility(View.VISIBLE);
                                puntuacion_mediatxt.setText(String.valueOf(usuario.getPuntuacioMedia()));
                                puntuacion_mediatxt.setEnabled(false);
                                puntuacion_mediatxt.setVisibility(View.VISIBLE);

                            }
                        } else {
                            Toast.makeText(getActivity(), "No hay información del usuario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                    }
                });
    }

    private void initialize2() {

        String foto_name = id + ".jpg";
        storageReference = FirebaseStorage.getInstance().getReference().child("profilesImages").child(foto_name);
        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profile_image.setImageBitmap(bitmap);
            }
        });

    }



    private boolean isAnyUpdate() {
        boolean name, surname, piso, direction, postal_code;
        name = false;
        surname = false;
        piso = false;
        direction = false;
        postal_code = false;

        String nombre = nombretxt.getText().toString();
        if(!usuario.getNombre().equals(nombre)){
            reference.child("Usuarios").child(id).child("nombre").setValue(nombre);
            usuario.setNombre(nombre); // Utilizo la variable global para tener constancia del cambio, y por si el usuario vuelve a intentar cambiarlo.
            name = true;
        }

        String apellido = apellidotxt.getText().toString();
        if(!usuario.getApellidos().equals(apellido)){
            reference.child("Usuarios").child(id).child("apellidos").setValue(apellido);
            usuario.setApellidos(apellido);
            surname = true;
        }

        String codigo_postal = codigo_postaltxt.getText().toString();
        if (codigo_postal.length() != 5) {
            codigo_postaltxt.setError("El código postal debe ser de 5 dígitos");
        } else if(!usuario.getCodigopostal().equals(codigo_postal)){
            reference.child("Usuarios").child(id).child("codigopostal").setValue(codigo_postal);
            usuario.setCodigopostal(codigo_postal);
            postal_code = true;
        }

        String direccion = direcciontxt.getText().toString();
        if(!usuario.getDireccion().equals(direccion)){
            reference.child("Usuarios").child(id).child("direccion").setValue(direccion);
            usuario.setDireccion(direccion);
            direction = true;
        }

        String piso_puerta = piso_puertatxt.getText().toString();
        if(!usuario.getPiso_puerta().equals(piso_puerta)){
            reference.child("Usuarios").child(id).child("piso_puerta").setValue(piso_puerta);
            usuario.setPiso_puerta(piso_puerta);
            piso = true;
        }

        return (name || surname || piso || direction || postal_code);

    }

    public void UpdateFoto(){
        CropImage.startPickImageActivity(getActivity());
        Intent intent = CropImage.activity()
                .setAspectRatio(16,9)
                .getIntent(getContext());

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void UpdateProfile() {
        if(isAnyUpdate()){
            Toast.makeText(getActivity(), "Se ha actualizado el perfil correctamente.", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getActivity(), "No hay ninguna modificación en los datos, por tanto no se puede actualizar el perfil", Toast.LENGTH_SHORT).show();
    }

    public void Dar_Baja_Usuario(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Estás seguro que quiere dar de baja de NeverAlone?")
                .setTitle("Mensaje de Confirmación")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //reference = FirebaseDatabase.getInstance().getReference();
                        //reference.child("Peticiones").child(p.getPeticionID()).removeValue();
                        //reference.child("User-Peticiones").child(user.getUid()).child(p.getPeticionID()).removeValue();
                        /** reference.child("Interacciones").child(p.getPeticionID()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                        dialog.dismiss();
                         */
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ActualizarPerfil:
                UpdateProfile();
                break;
            case R.id.profile_image:
                UpdateFoto();
                break;
            case R.id.BajaUsuario:
                Dar_Baja_Usuario();
                break;
        }
    }

}