package com.example.neveralone.ui.tutor;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class BlankFragmentTutor extends Fragment {
    private Button btnSolicitar;
    private DatabaseReference databaseReference_Benef, databaseReference_Volun,referenceT;
    private Context context;
    private DatabaseReference reference,reference2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View root =inflater.inflate(R.layout.fragment_blank, container, false);
        context = getContext();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        //volunatario
        if (LoginActivity.getUserType()) {
            final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            referenceT = FirebaseDatabase.getInstance().getReference().child("Tutoria/"+ userID);
            referenceT.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i("mensaje1", "entraaaa aarriba");
                            Log.i("mensaje1", String.valueOf(referenceT));

                            if(snapshot.exists()) {
                                Log.i("mensaje1", "entraaaa");

                                transaction.replace(R.id.root_frame, new TutoriaVoluntario()); //Sustiuir con la clase de tutor voluntario
                                transaction.commit();
                            }
                            //si existe alguna instancia en voluntario, podemos hacer el match
                            else {

                                reference = FirebaseDatabase.getInstance().getReference().child("SolicitudVoluntario/" + userID);
                                reference.addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot snapshot) {
                                                //miramos si exita instancia en solicitarBenefi
                                                if (snapshot.exists()) {
                                                    reference2 = FirebaseDatabase.getInstance().getReference().child("SolicitudBeneficirio/");
                                                    reference2.addListenerForSingleValueEvent(
                                                            new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    //si existe alguna instancia en voluntario, podemos hacer el match
                                                                    if (snapshot.exists()) {
                                                                        Iterator i = snapshot.getChildren().iterator();

                                                                        String idbene = (String) ((DataSnapshot) i.next()).getValue();
                                                                        databaseReference_Volun = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);
                                                                        databaseReference_Volun.setValue(idbene);
                                                                        databaseReference_Volun = FirebaseDatabase.getInstance().getReference("SolicitudVoluntario/" + userID);
                                                                        databaseReference_Volun.removeValue();

                                                                        databaseReference_Benef = FirebaseDatabase.getInstance().getReference("Tutoria/" + idbene);
                                                                        databaseReference_Benef.setValue(userID);
                                                                        databaseReference_Benef = FirebaseDatabase.getInstance().getReference("SolicitudBeneficirio/" + idbene);
                                                                        databaseReference_Benef.removeValue();
                                                                    } else {
                                                                        transaction.replace(R.id.root_frame, new VolunteerWait()); //Sustiuir con la clase de tutor voluntario
                                                                        transaction.commit();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                }
                                                            });
                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });
                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


        }

        //beneficiario
        else {
            final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.i("mensaje1", userID);
            reference = FirebaseDatabase.getInstance().getReference().child("SolicitudBeneficirio/" + userID);
            reference.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            //miramos si exita instancia en solicitarBenefi
                            if (snapshot.exists()) {
                                reference2 = FirebaseDatabase.getInstance().getReference().child("SolicitudVoluntario/");
                                reference2.addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                //si existe alguna instancia en voluntario, podemos hacer el match
                                                if (snapshot.exists()) {
                                                    Iterator i = snapshot.getChildren().iterator();

                                                    String idVolun = (String) ((DataSnapshot) i.next()).getValue();
                                                    databaseReference_Benef = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);
                                                    databaseReference_Benef.setValue(idVolun);
                                                    databaseReference_Benef = FirebaseDatabase.getInstance().getReference("SolicitudBeneficirio/" + userID);
                                                    databaseReference_Benef.removeValue();

                                                    databaseReference_Volun = FirebaseDatabase.getInstance().getReference("Tutoria/" + idVolun);
                                                    databaseReference_Volun.setValue(userID);
                                                    databaseReference_Volun = FirebaseDatabase.getInstance().getReference("SolicitudVoluntario/" + idVolun);
                                                    databaseReference_Volun.removeValue();
                                                }
                                                else{
                                                    transaction.replace(R.id.root_frame, new BenefactorWait()); //Sustiuir con la clase de tutor voluntario
                                                    transaction.commit();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {}
                                        });
                            }
                            else {
                                //miramos si es tiene tutor o no el beneficiario
                                databaseReference_Benef = FirebaseDatabase.getInstance().getReference().child("Tutoria/" + userID);
                                reference.addListenerForSingleValueEvent(
                                        new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                if (snapshot.exists()){
                                                    //vamos a los 3 botoness
                                                }
                                                else {
                                                    transaction.replace(R.id.root_frame, new Benefactor()); //Sustiuir con la clase de tutor voluntario
                                                    transaction.commit();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });



                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
        }
        return root;
    }
}