package com.example.neveralone.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neveralone.Activity.LoginActivity;
import com.example.neveralone.Activity.SharedPreferencesSingleton;
import com.example.neveralone.R;
import com.example.neveralone.ui.home.benefactor.HomeBenefactorFragment;
import com.example.neveralone.ui.home.volunteer.HomeVolunteerFragment;


public class BlankFragment extends Fragment {

    private SharedPreferencesSingleton sharedPreferencesSingleton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();


        /*
         * When this container fragment is created, we fill it with our first
         * "real" fragment
         */
        if (sharedPreferencesSingleton.read("voluntario", false)) {
            transaction.replace(R.id.root_frame, new HomeVolunteerFragment());
        } else {
            transaction.replace(R.id.root_frame, new HomeBenefactorFragment());
        }

        transaction.commit();
        return root;
    }
}