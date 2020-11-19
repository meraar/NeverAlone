package com.example.neveralone;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private String codigoPostal;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private GoogleMap googleM;
    private Geocoder x;


    DatabaseReference reference;
    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    public void obtenerAdresaUsuario() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i("myInfoTag2", "entra");
        Log.i("myInfoTag2", userID);

        reference = FirebaseDatabase.getInstance().getReference("Usuarios/" + userID + "/codigopostal");
        Log.i("myInfoTag2", String.valueOf(reference));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("myInfoTag2", String.valueOf(codigoPostal));

                String value = dataSnapshot.getValue(String.class);
                codigoPostal = value;
                Log.i("myInfoTag2.2", String.valueOf(codigoPostal));
                mMap = googleM;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                List<Address> foundGeocode = null;
                try {
                    foundGeocode = x.getFromLocationName(codigoPostal+" España", 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LatLng sydney = new LatLng( foundGeocode.get(0).getLatitude(), foundGeocode.get(0).getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        Log.i("myInfoTag1", String.valueOf(codigoPostal));
        googleM = googleMap;
        x= new Geocoder(this);
        obtenerAdresaUsuario();

         Log.i("myInfoTag5", String.valueOf(codigoPostal));
        //FALTA COMPROBARLO Y COMENTAR ALGUNA COSA
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Geocoder geocoder = new Geocoder(this);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    //String codigo_raro = snapshot.child("null").getValue().toString();
                    Iterable<DataSnapshot> Usuarios_con_peticiones = snapshot.child("User-Peticiones").getChildren();
                    for (DataSnapshot Usuario_con_peticiones : Usuarios_con_peticiones) {
                        List<Address> foundGeocodePeticion = null;
                        String codigo_usuario_con_pet = Usuario_con_peticiones.getValue(String.class);
                        String CP = null;
                        Iterable<DataSnapshot> Usuarios = snapshot.child("Usuarios").getChildren();
                        for (DataSnapshot User : Usuarios) {
                            String código_usuario = User.getValue().toString();
                            if(código_usuario ==codigo_usuario_con_pet){
                                CP = User.child("codigoposta").getValue().toString();
                            }
                        }
                        //get_CP_by_Usr(codigo_usuario, CP);
                        try {
                            //Quizas llamando aqui a una funcion pasando como parametro Usuario_con_peticiones consiga el codigo postal
                            foundGeocodePeticion = geocoder.getFromLocationName(CP + "España", 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LatLng ubi_peticion = new LatLng( foundGeocodePeticion.get(0).getLatitude(), foundGeocodePeticion.get(0).getLongitude());
                        Marker MarkerEjemplo1 = mMap.addMarker(new MarkerOptions()
                                .position(ubi_peticion)
                                .title("Ejemplo1")
                                .snippet("Distancia: 10M KM")); //de momento, habria que mirar como calcular la distancia
                        //MarkerEjemplo1.showInfoWindow();
                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
/*
    private void get_CP_by_Usr(final String codigo_usuario, String codigoUsuario) {
        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        final String[] a = {null};
        final String b =null;
        mDatabase2.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Iterable<DataSnapshot> Usuarios = snapshot.getChildren();
                    for (DataSnapshot usr : Usuarios) {
                        String cod_usr = usr.getValue().toString();
                        if (cod_usr==codigo_usuario){
                             a[1] = usr.child("codigopostal").getValue().toString();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        codigoUsuario = a[0];
    }
 */
}