package com.example.neveralone.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.neveralone.Activity.FirstHomeActivity;
import com.example.neveralone.Activity.UserProfileActivity;
import com.example.neveralone.R;
import com.example.neveralone.Usuario.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private EditText nametxt, surnametxt, dnitxt, emailtxt, postalCodetxt, avgPunctuationtxt, addresstxt, reasontxt;
    private EditText puntuacion_mediatxt, piso_puertatxt, motivotxt;
    private TextInputLayout first_puntuacion, first_piso_puerta, first_direccion, first_motivo;
    private ImageView profile_image;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Usuario usuario;
    private String id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        nametxt = root.findViewById(R.id.Nombre);
        surnametxt = root.findViewById(R.id.Apellido);
        dnitxt = root.findViewById(R.id.dni);
        emailtxt = root.findViewById(R.id.email);
        postalCodetxt = root.findViewById(R.id.codigo_postal);
        avgPunctuationtxt = root.findViewById(R.id.puntuacion_media);
        piso_puertatxt = root.findViewById(R.id.piso_puerta);
        addresstxt = root.findViewById(R.id.direccion);
        reasontxt = root.findViewById(R.id.motivo);
        first_piso_puerta = root.findViewById(R.id.firstpiso_puerta);
        first_direccion = root.findViewById(R.id.firstdireccion);
        first_puntuacion = root.findViewById(R.id.firstpuntuacion_media);
        first_motivo = root.findViewById(R.id.firstmotivo);
        /*galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        initialize();
        return root;
    }

    private void initialize(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        id = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuarios").child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            usuario = snapshot.getValue(Usuario.class);
                            nametxt.setText(usuario.getNombre());
                            surnametxt.setText(usuario.getApellidos());
                            dnitxt.setText(usuario.getDni());
                            dnitxt.setEnabled(false);
                            emailtxt.setText(usuario.getEmail());
                            emailtxt.setEnabled(false);
                            postalCodetxt.setText(usuario.getCodigopostal());

                            if (!usuario.getVoluntario()){
                                first_direccion.setVisibility(View.VISIBLE);
                                first_piso_puerta.setVisibility(View.VISIBLE);
                                first_motivo.setVisibility(View.VISIBLE);

                                reasontxt.setText(usuario.getMotivo());
                                reasontxt.setVisibility(View.VISIBLE);
                                reasontxt.setEnabled(false); // El motivo no se podra actualizar.
                                reasontxt.setVisibility(View.VISIBLE);


                                addresstxt.setText(usuario.getDireccion());
                                addresstxt.setVisibility(View.VISIBLE);


                                piso_puertatxt.setText(usuario.getPiso_puerta());
                                piso_puertatxt.setVisibility(View.VISIBLE);

                            }
                            else{
                                //first_puntuacion.setVisibility(View.VISIBLE);
                                //puntuacion_mediatxt.setText(String.valueOf(usuario.getPuntuacioMedia()));
                                //puntuacion_mediatxt.setEnabled(false);
                                //puntuacion_mediatxt.setVisibility(View.VISIBLE);

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

    public void UpdateProfile(View view) {
        /*if(isAnyUpdate()){
            Toast.makeText(FirstHomeActivity.this, "Se ha actualizado el perfil correctamente.", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(GalleryFragment.this, "No hay ninguna modificación en los datos, por tanto no se puede actualizar el perfil", Toast.LENGTH_SHORT).show();*/
    }

    private boolean isAnyUpdate() {
        boolean name, surname, piso, direction, postal_code;
        name = false;
        surname = false;
        piso = false;
        direction = false;
        postal_code = false;

        String nombre = nametxt.getText().toString();
        if(!usuario.getNombre().equals(nombre)){
            reference.child("Usuarios").child(id).child("nombre").setValue(nombre);
            usuario.setNombre(nombre); // Utilizo la variable global para tener constancia del cambio, y por si el usuario vuelve a intentar cambiarlo.
            Toast.makeText(getActivity(), "Nombre = "+ usuario.getNombre() +" .", Toast.LENGTH_SHORT).show();
            name = true;
        }

        String apellido = surnametxt.getText().toString();
        if(!usuario.getApellidos().equals(apellido)){
            reference.child("Usuarios").child(id).child("apellidos").setValue(apellido);
            usuario.setApellidos(apellido);
            surname = true;
        }

        String codigo_postal = postalCodetxt.getText().toString();
        if (codigo_postal.length() != 5) {
            postalCodetxt.setError("El código postal debe ser de 5 dígitos");
        } else if(!usuario.getCodigopostal().equals(codigo_postal)){
            reference.child("Usuarios").child(id).child("codigopostal").setValue(codigo_postal);
            usuario.setCodigopostal(codigo_postal);
            postal_code = true;
        }

        String direccion = addresstxt.getText().toString();
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
}