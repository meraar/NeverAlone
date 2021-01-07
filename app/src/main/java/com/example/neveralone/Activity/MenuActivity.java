package com.example.neveralone.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.neveralone.R;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.NavController;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);  //aqui falla
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.blankFragment, R.id.nav_myprofile, R.id.nav_autotest, R.id.nav_contacts,  R.id.nav_infocovid, R.id.blankFragmentTutor, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        /*
        if (LoginActivity.getUserType()) {
            navController.navigate(R.id.action_blankFragment_to_nav_homev);
        } else {
            navController.navigate(R.id.action_blankFragment_to_nav_homeb);
        }
        */

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        MenuItem logoutIt = navigationView.getMenu().findItem(R.id.nav_logout);
        logoutIt.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                finish();
                return true;
            }
        });

        TextView name = findViewById(R.id.navHeaderName);
        TextView email = findViewById(R.id.navHeaderEmail);
       /* try {
            ValueEventListenerAdapter.getSemaphore().acquire();
            name.setText(ValueEventListenerAdapter.getUser().getNombre());
            email.setText(ValueEventListenerAdapter.getUser().getEmail());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    /* TODO Si no añadimos settings eliminar esto
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first_home, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}