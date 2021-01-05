package com.example.neveralone.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_TIME = 1000;
    private SharedPreferencesSingleton sharedPreferencesSingleton;
    private Intent mainIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharedPreferencesSingleton.init(getApplicationContext());

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if(currentUser!=null) {
                    sharedPreferencesSingleton.write("LoggedIn", true);
                    System.out.println("estas logueado??" + sharedPreferencesSingleton.read("LoggedIn", false));
                    System.out.println("eres voluntario??" + sharedPreferencesSingleton.read("voluntario", false));
                    mainIntent = new Intent(MainActivity.this,MenuActivity.class);
                }else{
                    sharedPreferencesSingleton.write("LoggedIn", false);
                    System.out.println("estas logueado??" + sharedPreferencesSingleton.read("LoggedIn", false));
                    mainIntent = new Intent(MainActivity.this,LoginActivity.class);
                }
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }
}