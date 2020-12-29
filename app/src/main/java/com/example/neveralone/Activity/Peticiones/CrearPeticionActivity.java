package com.example.neveralone.Activity.Peticiones;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.neveralone.Peticion.DatePicker;
import com.example.neveralone.Peticion.Peticion;
import com.example.neveralone.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CrearPeticionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Button crear;
    private Spinner categorias;
    private EditText fechaEt, horaEt, descripcionEt;
    private TextView result;
    private String origen;

    private String peticionID;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_crearpeticion);

        Intent i = getIntent();
        origen = (String) i.getSerializableExtra("Origen");
        peticionID = i.getStringExtra("Peticion");
        if(origen.equals("Editar")) carregarPeticio(peticionID);
        initComponents();
    }

    private void carregarPeticio(final String peticionID) {


        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Peticiones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    snapshot = snapshot.child(peticionID);
                    Peticion p = snapshot.getValue(Peticion.class);
                    if (p.getCategoria() != null) {
                        categorias.setSelection(getIndex(p.getCategoria()));
                    }

                    fechaEt.setText(p.getFecha());
                    horaEt.setText(p.getHora());
                    descripcionEt.setText(p.getDescripcion());

                } else return;
            }

            private int getIndex(String categoria) {
                for (int i=0;i<categorias.getCount();i++){
                    if (categorias.getItemAtPosition(i).toString().equalsIgnoreCase(categoria)){
                        return i;
                    }
                }
                return 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initComponents() {

        categorias = findViewById(R.id.SpinnerCategoriaPeticion);
        fechaEt = findViewById(R.id.fecha_peticion);
        horaEt = findViewById(R.id.hora_peticion);
        crear = findViewById(R.id.idCrearPeticion);
        descripcionEt = findViewById(R.id.descripcion);
        result = findViewById(R.id.result);
        String[] valores = {"Compras", "Asesoramiento", "Acompañamiento", "Otros"};
        categorias.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
        fechaEt.setInputType(InputType.TYPE_NULL);
        horaEt.setInputType(InputType.TYPE_NULL);

        fechaEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fechaEt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        horaEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                horaEt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (compruebaFecha()) {

                        result.setText("SUCCES");

                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference();

                        final String categoria = categorias.getSelectedItem().toString();
                        final String fecha = fechaEt.getText().toString();
                        final String hora = horaEt.getText().toString();
                        final String descripcion = descripcionEt.getText().toString();
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                        reference.child("Usuarios").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String uid = user.getUid();
                                    String name = (String) snapshot.child(uid).child("nombre").getValue();
                                    Map<String, Object> childUpdates = new HashMap<>();
                                    final Peticion p;
                                    if(origen.equals("Editar")){
                                        p = new Peticion(peticionID,name, uid,categoria,fecha,hora,descripcion);
                                        //DatabaseReference ref = reference.child("Peticiones").child(peticionID);
                                        Map<String, Object> postValues = p.toMap();
                                        childUpdates.put("/Peticiones/" + peticionID, postValues);
                                        childUpdates.put("/User-Peticiones/" + uid + "/" + peticionID, postValues);
                                    }else{
                                        String key = reference.child("Peticiones").push().getKey();
                                        p = new Peticion(key, name, uid, categoria, fecha, hora, descripcion);
                                        Map<String, Object> postValues = p.toMap();
                                        childUpdates.put("/Peticiones/" + key, postValues);
                                        childUpdates.put("/User-Peticiones/" + uid + "/" + key, postValues);
                                    }


                                    reference.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            result.setText("SUCCES");
                                            try {
                                                Thread.sleep(4);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Intent returnIntent = new Intent();
                                            returnIntent.putExtra("Peticion", p);
                                            setResult(RESULT_OK,returnIntent);
                                            finish();

                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    } else result.setText("Something went wrong");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean compruebaFecha() throws ParseException {
        String fecha, hora;

        fecha = fechaEt.getText().toString();
        hora = horaEt.getText().toString();
        if (fecha.isEmpty()) {
            fechaEt.setError("Escoja una fecha");
            return false;
        }
        if (hora.isEmpty()) {
            fechaEt.setError("Escoja una hora");
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaCompleta = fecha + " " + hora;
        Date strDate = sdf.parse(fechaCompleta);

        Date actual = new Date();

        if (actual.before(strDate)) {
            fechaEt.setError(null);
            horaEt.setError(null);
            return true;
        } else {
            fechaEt.setError("La fecha no puede ser anterior a la actual");
            horaEt.setError("La hora debe ser posterior a la actual");
            return false;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new com.example.neveralone.Peticion.TimePicker();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }


    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalender = Calendar.getInstance();
        mCalender.set(Calendar.YEAR, year);
        mCalender.set(Calendar.MONTH, month);
        mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        fechaEt.setText(sdf.format(mCalender.getTime()));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = String.valueOf(hourOfDay) + ':';

        if (minute < 10) {
            time = time + '0' + String.valueOf(minute);
        } else {
            time = time + String.valueOf(minute);
        }

        horaEt.setText(time);
    }
}
