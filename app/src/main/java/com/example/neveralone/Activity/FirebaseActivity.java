package com.example.neveralone.Activity;

import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



public class FirebaseActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    public FirebaseActivity() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }
    public boolean verifyRegister(EditText email) {
        //TODO Usuario existente, usuario con email incorrecto ("hotmail)
        return true;
    }
    public void register(final String email, final String password) {
        /**mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText((Context)RegisterActivity.this,
                                    "Se registro correctamente",
                                    Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            Usuario usuario = new Usuario();
                            usuario.setEmail(email);
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                            reference.setValue(usuario);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(FirebaseActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                }); pesneveralone2020@gmail.com**/
    }







}
