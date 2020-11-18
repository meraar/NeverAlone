package com.example.neveralone.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AutotestActivity extends AppCompatActivity {

    private ArrayList al = new ArrayList();
    private TextView txtinicio, title2, q2, title3, q3, title4, q4, title5, q5, title6, q6, title7, q7;
    private Button btnRT, btntest1Si, btntest1No, btntest2Si, btntest2No, btntest3Si, btntest3No, btntest4Si, btntest4No, btntest5Si, btntest5No, btntest6Si, btntest6No, btntest7Si, btntest7No;

    private void visibility (View view, TextView title, TextView text, Button yes, Button no) {
        if(title.getVisibility() == View.GONE) title.setVisibility(View.VISIBLE);
        if(text.getVisibility() == View.GONE) text.setVisibility(View.VISIBLE);
        if(yes.getVisibility() == View.GONE) yes.setVisibility(View.VISIBLE);
        if(no.getVisibility() == View.GONE) no.setVisibility(View.VISIBLE);
    }

    private void gone (TextView title, TextView text, Button yes, Button no) {
        if(title.getVisibility() == View.VISIBLE) title.setVisibility(View.GONE);
        if(text.getVisibility() == View.VISIBLE) text.setVisibility(View.GONE);
        if(yes.getVisibility() == View.VISIBLE) yes.setVisibility(View.GONE);
        if(no.getVisibility() == View.VISIBLE) no.setVisibility(View.GONE);
    }

    private void xGone(int a) {
            if (a < 2) {
                gone(title2, q2, btntest2Si, btntest2No);
                if (al.contains("test2Si")) al.remove("test2Si");
                else if (al.contains("test2No")) al.remove("test2No");
            }
            if (a < 3) {
                gone(title3, q3, btntest3Si, btntest3No);
                if (al.contains("test3Si")) al.remove("test3Si");
                else if (al.contains("test3No")) al.remove("test3No");
            }
            if (a < 4) {
                gone(title4, q4, btntest4Si, btntest4No);
                if (al.contains("test4Si")) al.remove("test4Si");
                else if (al.contains("test4No")) al.remove("test4No");
            }
            if (a < 5) {
                gone(title5, q5, btntest5Si, btntest5No);
                if (al.contains("test5Si")) al.remove("test5Si");
                else if (al.contains("test5No")) al.remove("test5No");
            }
            if (a < 6) {
                gone(title6, q6, btntest6Si, btntest6No);
                if (al.contains("test6Si")) al.remove("test6Si");
                else if (al.contains("test6No")) al.remove("test6No");
            }
            if (a < 7) {
                gone(title7, q7, btntest7Si, btntest7No);
                if (al.contains("test7Si")) al.remove("test7Si");
                else if (al.contains("test7No")) al.remove("test7No");
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autotest);
        txtinicio = findViewById(R.id.txtinicio);
        btnRT = findViewById(R.id.btnRT);
        txtinicio.setText("Este auto-test le permitirá orientarse con respecto a los síntomas de la COVID-19, pero debe saber que nunca sustituirá una consulta médica. Si presenta algún síntoma que indique gravedad deberá consultar con el servicio de emergencias sanitarias en el 061.");

        btntest1Si = findViewById(R.id.btntest1Si);
        btntest1No = findViewById(R.id.btntest1No);

        btntest2Si = findViewById(R.id.btntest2Si);
        btntest2No = findViewById(R.id.btntest2No);
        title2 = findViewById(R.id.title2);
        q2 = findViewById(R.id.q2);

        btntest3Si = findViewById(R.id.btntest3Si);
        btntest3No = findViewById(R.id.btntest3No);
        title3 = findViewById(R.id.title3);
        q3 = findViewById(R.id.q3);

        btntest4Si = findViewById(R.id.btntest4Si);
        btntest4No = findViewById(R.id.btntest4No);
        title4 = findViewById(R.id.title4);
        q4 = findViewById(R.id.q4);

        btntest5Si = findViewById(R.id.btntest5Si);
        btntest5No = findViewById(R.id.btntest5No);
        title5 = findViewById(R.id.title5);
        q5 = findViewById(R.id.q5);

        btntest6Si = findViewById(R.id.btntest6Si);
        btntest6No = findViewById(R.id.btntest6No);
        title6 = findViewById(R.id.title6);
        q6 = findViewById(R.id.q6);

        btntest7Si = findViewById(R.id.btntest7Si);
        btntest7No = findViewById(R.id.btntest7No);
        title7 = findViewById(R.id.title7);
        q7 = findViewById(R.id.q7);

        xGone(1);

        btnRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(1);
            }
        });

        btntest1Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                xGone(1);
                if (!al.contains("test1Si")) al.add("test1Si");
                if (al.contains("test1No")) al.remove("test1No");
                visibility(view, title2, q2, btntest2Si, btntest2No);
            }
        });

        btntest1No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                xGone(1);
                if (!al.contains("test1No")) al.add("test1No");
                if (al.contains("test1Si")) al.remove("test1Si");
                visibility(view, title2, q2, btntest2Si, btntest2No);
            }
        });

        btntest2Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(2);
                if (!al.contains("test2Si")) al.add("test2Si");
                if (al.contains("test2No")) al.remove("test2No");


                title3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                title3.setTextColor(Color.parseColor("#818181"));

                q3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                q3.setTextColor(Color.parseColor("#818181"));
                title3.setText("3. DURACIÓN DE LOS SÍNTOMAS");
                q3.setText("¿Hace más de 14 días que comenzaron los síntomas?");
                visibility(view, title3, q3, btntest3Si, btntest3No);
            }
        });

        btntest2No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(2);
                if (!al.contains("test2No")) al.add("test2No");
                if (al.contains("test2Si")) al.remove("test2Si");


                if (al.contains("test1Si") && al.contains("test2No")) {
                    title3.setBackgroundColor(Color.parseColor("#FF6442"));
                    title3.setTextColor(Color.parseColor("#FFFFFF"));

                    q3.setBackgroundColor(Color.parseColor("#FF6442"));
                    q3.setTextColor(Color.parseColor("#FFFFFF"));

                    title3.setText("ATENCIÓN");
                    q3.setText("Usted tuvo un contacto estrecho con un caso de COVID-19, por lo tanto debe permanecer en su casa durante los 14 días que dura una cuarentena evitando los contactos con otras personas.\n" +
                            "\n" +
                            "Llame al número 900 400 116 o pida una consulta telefónica con su centro de salud para recibir las indicaciones específicas en su situación si no contactaron con usted ya.");
                    visibility(view, title3, q3, btntest3Si, btntest3No);
                    if (btntest3No.getVisibility() == View.VISIBLE)
                        btntest3No.setVisibility(View.GONE);
                    if (btntest3Si.getVisibility() == View.VISIBLE)
                        btntest3Si.setVisibility(View.GONE);
                }
                else if (al.contains("test1No") && al.contains("test2No")) {
                    title3.setBackgroundColor(Color.parseColor("#7FE153"));
                    title3.setTextColor(Color.parseColor("#FFFFFF"));

                    q3.setBackgroundColor(Color.parseColor("#7FE153"));
                    q3.setTextColor(Color.parseColor("#FFFFFF"));

                    title3.setText("RESULTADO");
                    q3.setText("En este momento su situación no requiere asistencia sanitaria. Recuerde seguir manteniendo las recomendaciones generales de distanciamiento social, higiene y protección recomendadas.");

                    visibility(view, title3, q3, btntest3Si, btntest3No);
                    if (btntest3No.getVisibility() == View.VISIBLE)
                        btntest3No.setVisibility(View.GONE);
                    if (btntest3Si.getVisibility() == View.VISIBLE)
                        btntest3Si.setVisibility(View.GONE);
                }
            }
        });

        btntest3Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(3);
                if (!al.contains("test3Si")) al.add("test3Si");
                if (al.contains("test3No")) al.remove("test3No");

                title4.setBackgroundColor(Color.parseColor("#FF6442"));
                title4.setTextColor(Color.parseColor("#FFFFFF"));

                q4.setBackgroundColor(Color.parseColor("#FF6442"));
                q4.setTextColor(Color.parseColor("#FFFFFF"));

                title4.setText("MÁS DE 14 DÍAS CON SÍNTOMAS");
                q4.setText("La COVID-19 se presenta como una enfermedade aguda, por lo tanto los síntomas que presenta en este momento podrían deberse a otra causa diferente del nuevo coronavirus. \n" +
                        "\n" +
                                "Solicite cita en su centro de salud para que valoren sus síntomas. Por favor, recuerde que cualquier persona con síntomas compatibles con la Covid debe quedarse en su domicilio y limitar los contactos con otras personas.");

                visibility(view, title4, q4, btntest4Si, btntest4No);
                if(btntest4No.getVisibility() == View.VISIBLE) btntest4No.setVisibility(View.GONE);
                if(btntest4Si.getVisibility() == View.VISIBLE) btntest4Si.setVisibility(View.GONE);
            }
        });

        btntest3No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(3);
                if (!al.contains("test3No")) al.add("test3No");
                if (al.contains("test3Si")) al.remove("test3Si");


                title4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                title4.setTextColor(Color.parseColor("#818181"));

                q4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                q4.setTextColor(Color.parseColor("#818181"));

                title4.setText("4. DIFICULTAD RESPIRATORIA");
                q4.setText("¿Tiene dificultad repentina para respirar o nota falta de aire?");
                visibility(view, title4, q4, btntest4Si, btntest4No);
            }
        });

        btntest4Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(4);
                if (!al.contains("test4Si")) al.add("test4Si");
                if (al.contains("test4No")) al.remove("test4No");


                title5.setBackgroundColor(Color.parseColor("#FF6442"));
                title5.setTextColor(Color.parseColor("#FFFFFF"));

                q5.setBackgroundColor(Color.parseColor("#FF6442"));
                q5.setTextColor(Color.parseColor("#FFFFFF"));

                title5.setText("ATENCIÓN");
                q5.setText("Sus síntomas parecen indicar gravedad. Llame al 061 indicando los síntomas que presenta.");
                visibility(view, title5, q5, btntest5Si, btntest5No);
                if(btntest5No.getVisibility() == View.VISIBLE) btntest5No.setVisibility(View.GONE);
                if(btntest5Si.getVisibility() == View.VISIBLE) btntest5Si.setVisibility(View.GONE);
            }
        });

        btntest4No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(4);
                if (!al.contains("test4No")) al.add("test4No");
                if (al.contains("test4Si")) al.remove("test4Si");

                title5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                title5.setTextColor(Color.parseColor("#818181"));

                q5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                q5.setTextColor(Color.parseColor("#818181"));

                title5.setText("5. FIEBRE > 37,7ºC");
                q5.setText("¿Presenta fiebre de más de 37,7ºC?");
                visibility(view, title5, q5, btntest5Si, btntest5No);
            }
        });

        btntest5Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(5);
                if (!al.contains("test5Si")) al.add("test5Si");
                if (al.contains("test5No")) al.remove("test5No");

                title6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                title6.setTextColor(Color.parseColor("#818181"));

                q6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                q5.setTextColor(Color.parseColor("#818181"));

                title6.setText("6. FIEBRE > 39ºC");
                q6.setText("¿Su temperatura es mayor de 39ºC?");
                visibility(view, title6, q6, btntest6Si, btntest6No);
            }
        });

        btntest5No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(5);
                if (!al.contains("test5No")) al.add("test5No");
                if (al.contains("test5Si")) al.remove("test5Si");

                title6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                title6.setTextColor(Color.parseColor("#818181"));

                q6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                q6.setTextColor(Color.parseColor("#818181"));

                title6.setText("6. SENSACIÓN FEBRIL");
                q6.setText("¿Pese a no presentar fiebre, nota sensación febril o escalofríos?");
                visibility(view, title6, q6, btntest6Si, btntest6No);
            }
        });

        btntest6Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(6);
                if (!al.contains("test6Si")) al.add("test6Si");
                if (al.contains("test6No")) al.remove("test6No");

                if (al.contains("test5Si")) {
                    title7.setBackgroundColor(Color.parseColor("#FF6442"));
                    title7.setTextColor(Color.parseColor("#FFFFFF"));

                    q7.setBackgroundColor(Color.parseColor("#FF6442"));
                    q7.setTextColor(Color.parseColor("#FFFFFF"));

                    title7.setText("ATENCIÓN");
                    q7.setText("Sus síntomas parecen indicar gravedad. Llame al 061 indicando los síntomas que presenta.");
                    visibility(view, title7, q7, btntest7Si, btntest7No);
                    if(btntest7No.getVisibility() == View.VISIBLE) btntest7No.setVisibility(View.GONE);
                    if(btntest7Si.getVisibility() == View.VISIBLE) btntest7Si.setVisibility(View.GONE);
                }
                else if (al.contains("test5No")) {
                    title7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    title7.setTextColor(Color.parseColor("#818181"));

                    q7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    q7.setTextColor(Color.parseColor("#818181"));

                    title7.setText("7. OTROS SÍNTOMAS");
                    q7.setText("Por favor especifique qué otros síntomas está usted experimentando:");
                    visibility(view, title7, q7, btntest7Si, btntest7No);
                    if(btntest7No.getVisibility() == View.VISIBLE) btntest7No.setVisibility(View.GONE);
                    if(btntest7Si.getVisibility() == View.VISIBLE) btntest7Si.setVisibility(View.GONE);
                }
            }
        });

        btntest6No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(6);
                if (!al.contains("test6No")) al.add("test6No");
                if (al.contains("test6Si")) al.remove("test6Si");

                title7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                title7.setTextColor(Color.parseColor("#818181"));

                q7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                q7.setTextColor(Color.parseColor("#818181"));

                title7.setText("7. OTROS SÍNTOMAS");
                q7.setText("Por favor especifique qué otros síntomas está usted experimentando:");
                visibility(view, title7, q7, btntest7Si, btntest7No);
                if(btntest7No.getVisibility() == View.VISIBLE) btntest7No.setVisibility(View.GONE);
                if(btntest7Si.getVisibility() == View.VISIBLE) btntest7Si.setVisibility(View.GONE);
            }
        });

    }
}
