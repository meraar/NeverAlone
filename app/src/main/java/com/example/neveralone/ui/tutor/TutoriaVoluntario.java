package com.example.neveralone.ui.tutor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.neveralone.Activity.Chat.MessageActivity;
import com.example.neveralone.Activity.OtherUserProfileActivity;
import com.example.neveralone.R;
import com.example.neveralone.ui.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TutoriaVoluntario extends Fragment {
    private View root;
    private Button btnEnviarMensaje, btnDejarSerTutot,VerPerfilBeneficiario;
    private Context context;
    private static String idFriendUser, nameFriendUser;

    private static String idTutor;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tutoria_volunteer, container, false);
        btnEnviarMensaje = root.findViewById(R.id.btnEnviarMensajeTutorBen);
        btnDejarSerTutot = root.findViewById(R.id.DejarSerTutot);
        VerPerfilBeneficiario=root.findViewById(R.id.VerPerfilBeneficiario);
        System.out.println("Yep");
        context = this.getContext();

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseReference_Logeado = FirebaseDatabase.getInstance().getReference();
                databaseReference_Logeado.child("Tutoria").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            idFriendUser = snapshot.child(userID).child("compañeroID").getValue(String.class);
                            DatabaseReference dbTutor = FirebaseDatabase.getInstance().getReference();
                            dbTutor.child("Usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        nameFriendUser = snapshot.child(idFriendUser).child("nombre").getValue(String.class);
                                        Bundle b = new Bundle();
                                        b.putString("idCurrentUser", userID);
                                        b.putString("idFriendUser", idFriendUser);
                                        b.putString("idPeticion", "Tutor");
                                        b.putString("nameFriendUser", nameFriendUser);

                                        Intent intent = new Intent(context, MessageActivity.class);
                                        intent.putExtras(b);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnDejarSerTutot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                final DatabaseReference databaseReference_Logeado = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);

                databaseReference_Logeado.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                final Date currentDate = Calendar.getInstance().getTime();
                                final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                                final SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                                final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
                                if(snapshot.child("day").getValue().equals(dayFormat.format(currentDate)) && snapshot.child("month").getValue().equals(monthFormat.format(currentDate)) && snapshot.child("year").getValue().equals(yearFormat.format(currentDate))) {
                                    Toast.makeText(context, "No puedes dejar ser tutor, intenta mañana.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    String idComp = (String) snapshot.child("compañeroID").getValue();
                                    DatabaseReference databaseReference_Logeado = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);
                                    databaseReference_Logeado.removeValue();
                                    DatabaseReference databaseReference_Comp = FirebaseDatabase.getInstance().getReference("Tutoria/" + idComp);
                                    databaseReference_Comp.removeValue();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.root_frame_tutoria_volunteer, new BlankFragmentTutor());
                                    transaction.commit();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
            }
        });

        VerPerfilBeneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                final DatabaseReference databaseReference_Logeado = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);

                databaseReference_Logeado.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                idTutor = (String) snapshot.child("compañeroID").getValue();
                                Intent intent = new Intent(getActivity(), OtherUserProfileActivity.class);
                                intent.putExtra("uid", idTutor);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
            }


        });
        return root;
    }
    public static String getIdTutoriaVolunteer(){
        return idTutor;
    }
}
