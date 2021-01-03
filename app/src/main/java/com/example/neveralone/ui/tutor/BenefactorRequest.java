package com.example.neveralone.ui.tutor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;


public class BenefactorRequest extends Fragment {
    private Button btnSolicitar;
    private DatabaseReference databaseReference_currentUser, databaseReference_Benef,databaseReference_Volun,reference2;
    private View root;
    private ViewGroup cont;
    private LayoutInflater i;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tutor_benefactor_request, container, false);
        btnSolicitar = root.findViewById(R.id.btnSolicitar);
        final Date currentDate = Calendar.getInstance().getTime();
        final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        final SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        //final Date d = new Date();
        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference_currentUser = FirebaseDatabase.getInstance().getReference("SolicitudBeneficirio/" + userID);
                databaseReference_currentUser.setValue(userID);
                reference2 = FirebaseDatabase.getInstance().getReference().child("SolicitudVoluntario/");
                reference2.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //si existe alguna instancia en voluntario, podemos hacer el match
                                if (snapshot.exists()) {
                                    Iterator i = snapshot.getChildren().iterator();
                                    String idVolun = (String) ((DataSnapshot) i.next()).getValue();
                                    databaseReference_Benef = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);
                                    databaseReference_Benef.setValue(new tutoria(idVolun, dayFormat.format(currentDate), monthFormat.format(currentDate), yearFormat.format(currentDate)));
                                    databaseReference_Benef = FirebaseDatabase.getInstance().getReference("SolicitudBeneficirio/" + userID);
                                    databaseReference_Benef.removeValue();
                                    databaseReference_Volun = FirebaseDatabase.getInstance().getReference("Tutoria/" + idVolun);
                                    databaseReference_Volun.setValue(new tutoria(userID,dayFormat.format(currentDate), monthFormat.format(currentDate), yearFormat.format(currentDate)));
                                    databaseReference_Volun = FirebaseDatabase.getInstance().getReference("SolicitudVoluntario/" + idVolun);
                                    databaseReference_Volun.removeValue();
                                }
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.root_frame_tutor_benefactor_request, new BlankFragmentTutor()); //Sustiuir con la clase de tutor voluntario
                                transaction.commit();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
            }
        });

       // transaction.commit();
        return root;
    }



}