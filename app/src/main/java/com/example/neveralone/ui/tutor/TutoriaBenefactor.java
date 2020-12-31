package com.example.neveralone.ui.tutor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

import java.util.Date;

public class TutoriaBenefactor extends Fragment {
    private View root;
    private Button button3;
    private Context context;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_blank_tutoria_benefactor, container, false);
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        context = this.getContext();

        button3 = root.findViewById(R.id.DejarSerTutot);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                final DatabaseReference databaseReference_Logeado = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);

                databaseReference_Logeado.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Date d=new Date();
                            if(snapshot.child("day").getValue().equals(d.getDate()) && snapshot.child("month").getValue().equals(d.getMonth())) {
                                Toast.makeText(context, "No puedes dejar de tener tutor hoy, intenta mañana.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String idComp= (String) snapshot.child("compañeroID").getValue();
                                DatabaseReference databaseReference_Logeado = FirebaseDatabase.getInstance().getReference("Tutoria/" + userID);
                                databaseReference_Logeado.removeValue();
                                DatabaseReference databaseReference_Comp = FirebaseDatabase.getInstance().getReference("Tutoria/" + idComp);
                                databaseReference_Comp.removeValue();
                                transaction.replace(R.id.root_frame, new BenefactorMatch()); //Sustiuir con la clase de tutor voluntario
                                transaction.commit();
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
            }
        });
        transaction.commit();
        return root;
    }
}
