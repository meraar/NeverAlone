package com.example.neveralone.ui.tutor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.neveralone.Activity.Chat.MessageActivity;
import com.example.neveralone.Activity.ListaChat.ElementosDeLista;
import com.example.neveralone.Activity.ListaChat.ListaAdaptador;
import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.R;
import com.example.neveralone.ui.home.benefactor.HomeBenefactorFragment;
import com.example.neveralone.ui.home.volunteer.HomeVolunteerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Volunteer extends Fragment {
    private Button btnSolicitar;
    private DatabaseReference databaseReference_currentUser, databaseReference_Volun,reference2,databaseReference_Benef;
    private Context context;
    private View root;
    private DatabaseReference reference;
    private ViewGroup cont;
    private LayoutInflater i;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_blank_tutor_voluntario, container, false);
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        btnSolicitar = root.findViewById(R.id.btnSolicitar);
        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference_currentUser = FirebaseDatabase.getInstance().getReference("SolicitudVoluntario/" + userID);
                databaseReference_currentUser.setValue(userID);
                //transaction.replace(R.layout.fragment_blank_tutor_voluntario, new HomeBenefactorFragment()); //Sustiuir con la clase de tutor beneficiario
                reference2 = FirebaseDatabase.getInstance().getReference().child("SolicitudBeneficirio/");
                reference2.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //si existe alguna instancia en voluntario, podemos hacer el match
                                Log.i("mmmmmm", "entra");

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
                                }
                                else{
                                    Log.i("mmmmmm", "entra else ");

                                    transaction.replace(R.id.root_frame, new VolunteerWait()); //Sustiuir con la clase de tutor voluntario
                                    transaction.commit();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
            }
        });

        //transaction.commit();
        return root;
    }



}