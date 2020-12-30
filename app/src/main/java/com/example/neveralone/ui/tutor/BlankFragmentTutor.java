package com.example.neveralone.ui.tutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.neveralone.Activity.Chat.MessageActivity;
import com.example.neveralone.Activity.Chat.MessageEnviar;
import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.R;
import com.example.neveralone.ui.home.benefactor.HomeBenefactorFragment;
import com.example.neveralone.ui.home.volunteer.HomeVolunteerFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BlankFragmentTutor extends Fragment {
    private Button btnSolicitar;
    private DatabaseReference databaseReference_currentUser, databaseReference_friendUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank_tutor, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        /*
         * When this container fragment is created, we fill it with our first
         * "real" fragment
         *//*
        if (LoginActivity.getUserType()) {
            transaction.replace(R.id.root_frame, new HomeVolunteerFragment()); //Sustiuir con la clase de tutor voluntario
        } else {
            transaction.replace(R.id.root_frame, new HomeBenefactorFragment()); //Sustiuir con la clase de tutor beneficiario
        }*/

        btnSolicitar = root.findViewById(R.id.btnSolicitar);
        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference_currentUser = FirebaseDatabase.getInstance().getReference("SolicitudBeneficirio/" + userID);
                databaseReference_currentUser.push().setValue(userID);
            }
        });
        transaction.commit();
        return root;
    }
}