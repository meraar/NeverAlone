package com.example.neveralone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;

public class OtherUserProfileActivity extends AppCompatActivity {

    private String user_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        Intent i     = getIntent();
        user_uid     = (String) i.getSerializableExtra("uid");
    }
}