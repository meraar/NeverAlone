package com.example.neveralone.ui.home.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.neveralone.Activity.Peticiones.CrearPeticionActivity;
import com.example.neveralone.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class HomeVolunteerFragment extends Fragment {
    private FloatingActionButton fab;
    private TabLayout homeTabLayout;
    private TabItem maps_tab, list_tab;
    private ViewPager2 pager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_homevolunteer, container, false);
        pager = root.findViewById(R.id.viewpager);
        homeTabLayout = root.findViewById(R.id.tabLayout);
        maps_tab = root.findViewById(R.id.maps_tab);
        list_tab = root.findViewById(R.id.list_tab);


        return root;
    }

}