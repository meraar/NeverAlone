package com.example.neveralone.ui.tutor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;


public class BlankFragmentTutor extends Fragment {
    private DatabaseReference databaseReference_Logeado, databaseReference_Compañero,referenceT;
    private DatabaseReference reference,reference2;
    private String userID, DirSolicitudLogeado,DirSolicitudCompañero;
    private FragmentTransaction transaction;
    private boolean tutor;
    private Date d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_blank, container, false);
        transaction = getFragmentManager().beginTransaction();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        tutor=false;
         //d= new Date();
        //volunatario
        if (LoginActivity.getUserType()){
            tutor=true;
            DirSolicitudLogeado = "SolicitudVoluntario/";
            DirSolicitudCompañero =  "SolicitudBeneficirio/";
        }
        //beneficiario
        else{
            tutor=false;
            DirSolicitudLogeado = "SolicitudBeneficirio/";
            DirSolicitudCompañero =  "SolicitudVoluntario/";
        }
        trato_tutoria_voluntario();
        return root;
    }

    private void trato_tutoria_voluntario() {
        referenceT = FirebaseDatabase.getInstance().getReference().child("Tutoria/"+ userID);
        referenceT.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //existe un match
                if(snapshot.exists()) {
                    if (tutor) transaction.replace(R.id.root_frame, new TutoriaVoluntario()); //Sustiuir con la clase de tutor voluntario
                    else transaction.replace(R.id.root_frame, new TutoriaBenefactor()); //Sustiuir con la clase de tutor voluntario
                    transaction.commit();
                }
                //si existe alguna instancia en voluntario, podemos hacer el match
                else IntentamosHacerMatch();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    private void IntentamosHacerMatch() {
        reference = FirebaseDatabase.getInstance().getReference().child(DirSolicitudLogeado + userID);
        reference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //miramos si exita instancia en solicitarBenefi
                if (snapshot.exists()) HamosMatch();

                else{
                    if(tutor) transaction.replace(R.id.root_frame, new VolunteerMatch()); //Sustiuir con la clase de tutor voluntario
                    else transaction.replace(R.id.root_frame, new BenefactorMatch()); //Sustiuir con la clase de tutor voluntario
                    transaction.commit();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void HamosMatch() {
        final Date currentDate = Calendar.getInstance().getTime();
        final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        final SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        reference2 = FirebaseDatabase.getInstance().getReference().child(DirSolicitudCompañero);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //si existe alguna instancia en voluntario, podemos hacer el match
                if (snapshot.exists()) {
                    Iterator i = snapshot.getChildren().iterator();

                    String idComp = (String) ((DataSnapshot) i.next()).getValue();
                    databaseReference_Compañero = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);
                    databaseReference_Compañero.setValue(new tutoria(idComp, dayFormat.format(currentDate), monthFormat.format(currentDate), yearFormat.format(currentDate)));
                    databaseReference_Compañero = FirebaseDatabase.getInstance().getReference(DirSolicitudLogeado + userID);
                    databaseReference_Compañero.removeValue();

                    databaseReference_Logeado = FirebaseDatabase.getInstance().getReference("Tutoria/" + idComp);
                    databaseReference_Logeado.setValue(new tutoria(userID, dayFormat.format(currentDate), monthFormat.format(currentDate), yearFormat.format(currentDate)));
                    databaseReference_Logeado = FirebaseDatabase.getInstance().getReference(DirSolicitudCompañero + idComp);
                    databaseReference_Logeado.removeValue();
                }
                if(tutor) transaction.replace(R.id.root_frame, new VolunteerWait()); //Sustiuir con la clase de tutor voluntario
                else transaction.replace(R.id.root_frame, new BenefactorWait()); //Sustiuir con la clase de tutor voluntario
                transaction.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}