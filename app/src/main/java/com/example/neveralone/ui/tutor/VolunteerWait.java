package com.example.neveralone.ui.tutor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.neveralone.R;

public class VolunteerWait extends Fragment {
    private View root;
    private ImageView imageView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tutor_volunteer_wait, container, false);
        imageView = root.findViewById(R.id.gifLoading);
        Glide.with(this).load(R.drawable.gif_loading).into(imageView);
        return root;
    }

}
