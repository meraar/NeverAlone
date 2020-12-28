package com.example.neveralone.ui.home.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neveralone.Activity.MenuActivity;
import com.example.neveralone.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class MapsFragment extends Fragment implements
        OnMapReadyCallback//,
        //GoogleMap.OnInfoWindowClickListener
{

    private GoogleMap mMap;
    private String codigoPostal;
    private DatabaseReference mDatabase;
    private GoogleMap googleM;
    private Geocoder x;

    DatabaseReference reference;

    //TODO utilizar la función getCodigoPostal de la clase usuario
    public void obtenerAdresaUsuario() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Usuarios/" + userID + "/codigopostal");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                codigoPostal = value;
                mMap = googleM;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                List<Address> foundGeocode = null;
                try {
                    foundGeocode = x.getFromLocationName(codigoPostal+" España", 1);
                } catch (IOException e) {
                    e.printStackTrace();}

                LatLng sydney = new LatLng( foundGeocode.get(0).getLatitude(), foundGeocode.get(0).getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            Log.i("mensaje","entra2");

            googleM = googleMap;
            x= new Geocoder(getContext());
            obtenerAdresaUsuario();
            //FALTA COMPROBARLO Y COMENTAR ALGUNA COSA
            mDatabase = FirebaseDatabase.getInstance().getReference();
            final Geocoder geocoder = new Geocoder(getContext());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        Iterable<DataSnapshot> Usuarios_con_peticiones = snapshot.child("User-Peticiones").getChildren();
                        for (DataSnapshot Usuario_con_peticiones : Usuarios_con_peticiones) {
                            //AÑADIDO DE AQUI...
                            Vector<String> tipos_de_peticiones_del_usr = new Vector<String>();
                            Iterable<DataSnapshot> peticiones = Usuario_con_peticiones.getChildren();
                            boolean Asesoriamiento = false, Compra = false, Acompañamiento = false, Otro = false;
                            for(DataSnapshot peticion : peticiones){
                                //CUIDADO CON COMO SE GUARDAN EN FIREBASE, A VECES CON "" A VECES NO...
                                String tipo_de_peticion = peticion.child("categoria").getValue().toString();

                                if(tipo_de_peticion.equals("Compras")  && !Compra){
                                    Compra = true;
                                    tipos_de_peticiones_del_usr.add("Compras");
                                }
                                else if(tipo_de_peticion.equals("Asesoramiento") && !Asesoriamiento){
                                    Asesoriamiento = true;
                                    tipos_de_peticiones_del_usr.add("Asesoramiento");
                                }
                                else if(tipo_de_peticion.equals("Acompañamiento") && !Acompañamiento) {
                                    Acompañamiento = true;
                                    tipos_de_peticiones_del_usr.add("Acompañamiento");
                                }
                                else if(tipo_de_peticion.equals("Otro") && !Otro) {
                                    Otro = true;
                                    tipos_de_peticiones_del_usr.add("Otro");
                                }
                            }
                            //...A AQUI

                            String codigo_usuario_con_pet = Usuario_con_peticiones.getKey();
                            String CP = null;
                            Iterable<DataSnapshot> Usuarios = snapshot.child("Usuarios").getChildren();
                            for (DataSnapshot User : Usuarios) {
                                String código_usuario = User.getKey();

                                Marker MarkerEjemplo1 = null;
                                if (código_usuario.equals(codigo_usuario_con_pet)) {
                                    CP = User.child("codigopostal").getValue().toString();
                                    List<Address> foundGeocode = null;
                                    try {
                                        foundGeocode = geocoder.getFromLocationName(CP + " España", 1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    LatLng ubi_peticion = new LatLng(foundGeocode.get(0).getLatitude(), foundGeocode.get(0).getLongitude());

                                    Log.i("mensaje2",codigo_usuario_con_pet);
                                    MarkerEjemplo1 = mMap.addMarker(new MarkerOptions()
                                            .position(ubi_peticion)
                                            .title(codigo_usuario_con_pet)
                                            .title(User.child("nombre").getValue().toString() + " " + User.child("apellidos").getValue().toString())
                                            .snippet(String.valueOf(tipos_de_peticiones_del_usr)));

                                    MarkerEjemplo1.setTag(codigo_usuario_con_pet);

                                }
                                //  MarkerEjemplo1.setSnippet(codigo_usuario_con_pet);

                            }
                        }
                    }
                }

                @Override
                public void onCancelled( DatabaseError error) {
                }
            });
            //startActivity(new Intent(getActivity(), MenuActivity.class));
           googleM.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
               @Override
               public void onInfoWindowClick(Marker marker) {
                   //Intent i = new Intent(this, VerMisPeticiones.class); //TODO esta clase ya no se utiliza
                   //i.putExtra("UserId",marker.getTag().toString());
                   //startActivity(i);
               }
           });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {


    }

}