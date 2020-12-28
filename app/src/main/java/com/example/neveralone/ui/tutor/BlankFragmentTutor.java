package com.example.neveralone.ui.tutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.R;
import com.example.neveralone.ui.home.benefactor.HomeBenefactorFragment;
import com.example.neveralone.ui.home.volunteer.HomeVolunteerFragment;


public class BlankFragmentTutor extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank_tutor, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        /*
         * When this container fragment is created, we fill it with our first
         * "real" fragment
         */
        if (LoginActivity.getUserType()) {
            transaction.replace(R.id.root_frame, new HomeVolunteerFragment()); //Sustiuir con la clase de tutor voluntario
        } else {
            transaction.replace(R.id.root_frame, new HomeBenefactorFragment()); //Sustiuir con la clase de tutor beneficiario
        }

        transaction.commit();
        return root;
    }
}