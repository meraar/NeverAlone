package com.example.neveralone.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.neveralone.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AutotestActivity extends AppCompatActivity {

    private ArrayList al = new ArrayList();
    private ArrayList subal = new ArrayList();
    private CardView card2, card3, card4, card5, card6, card7, card13;
    private TextView txtinicio, title2, q2, title3, q3, title4, q4, title5, q5, title6, q6, title7, q7, q71, q8, q9, q10, q11, q12, title13, q13;
    private Button defbtn, btnconf, btnRT, btntest1Si, btntest1No, btntest2Si, btntest2No, btntest3Si, btntest3No, btntest4Si, btntest4No, btntest5Si, btntest5No,
            btntest6Si, btntest6No, btntest7Si, btntest7No, btntest8Si, btntest8No, btntest9Si, btntest9No, btntest10Si, btntest10No, btntest11Si, btntest11No,
            btntest12Si, btntest12No;

    private void visibility (CardView cv, TextView title, TextView text, Button yes, Button no) {
        if(cv.getVisibility() == View.GONE) cv.setVisibility(View.VISIBLE);
        if(title.getVisibility() == View.GONE) title.setVisibility(View.VISIBLE);
        if(text.getVisibility() == View.GONE) text.setVisibility(View.VISIBLE);
        if(yes.getVisibility() == View.GONE) yes.setVisibility(View.VISIBLE);
        if(no.getVisibility() == View.GONE) no.setVisibility(View.VISIBLE);
    }

    private void gone (CardView cv, TextView title, TextView text, Button yes, Button no) {
        if(cv.getVisibility() == View.VISIBLE) cv.setVisibility(View.GONE);
        if(title.getVisibility() == View.VISIBLE) title.setVisibility(View.GONE);
        if(text.getVisibility() == View.VISIBLE) text.setVisibility(View.GONE);
        if(yes.getVisibility() == View.VISIBLE) yes.setVisibility(View.GONE);
        if(no.getVisibility() == View.VISIBLE) no.setVisibility(View.GONE);
    }

    private void reset (CardView cv, TextView title, TextView text, Button yes, Button no, Button def) {
        gone(cv, title, text, yes, no);
        yes.setBackground(def.getBackground());
        no.setBackground(def.getBackground());
    }

    private void xGone(int a) {
            if (a < 2) {
                reset(card2, title2, q2, btntest2Si, btntest2No, defbtn);
                if (al.contains("test2Si")) al.remove("test2Si");
                else if (al.contains("test2No")) al.remove("test2No");
            }
            if (a < 3) {
                reset(card3, title3, q3, btntest3Si, btntest3No, defbtn);
                if (al.contains("test3Si")) al.remove("test3Si");
                else if (al.contains("test3No")) al.remove("test3No");
            }
            if (a < 4) {
                reset(card4, title4, q4, btntest4Si, btntest4No, defbtn);
                if (al.contains("test4Si")) al.remove("test4Si");
                else if (al.contains("test4No")) al.remove("test4No");
            }
            if (a < 5) {
                reset(card5, title5, q5, btntest5Si, btntest5No, defbtn);
                if (al.contains("test5Si")) al.remove("test5Si");
                else if (al.contains("test5No")) al.remove("test5No");
            }
            if (a < 6) {
                reset(card6, title6, q6, btntest6Si, btntest6No, defbtn);
                if (al.contains("test6Si")) al.remove("test6Si");
                else if (al.contains("test6No")) al.remove("test6No");
            }
            if (a < 7) {
                reset(card7, title7, q7, btntest7Si, btntest7No, defbtn);

                if (q71.getVisibility() == View.VISIBLE) q71.setVisibility(View.GONE);
                if (subal.contains("test7Si")) subal.remove("test7Si");
                else if (al.contains("test7No")) al.remove("test7No");

                reset(card7, title7, q8, btntest8Si, btntest8No, defbtn);
                if (subal.contains("test8Si")) subal.remove("test8Si");
                else if (al.contains("test8No")) al.remove("test8No");

                reset(card7, title7, q9, btntest9Si, btntest9No, defbtn);
                if (subal.contains("test9Si")) subal.remove("test9Si");
                else if (al.contains("test9No")) al.remove("test9No");

                reset(card7, title7, q10, btntest10Si, btntest10No, defbtn);
                if (subal.contains("test10Si")) subal.remove("test10Si");
                else if (al.contains("test10No")) al.remove("test10No");

                reset(card7, title7, q11, btntest11Si, btntest11No, defbtn);
                if (subal.contains("test11Si")) subal.remove("test11Si");
                else if (al.contains("test11No")) al.remove("test11No");

                reset(card7, title7, q12, btntest12Si, btntest12No, defbtn);
                if (subal.contains("test12Si")) subal.remove("test12Si");
                else if (al.contains("test12No")) al.remove("test12No");
            }
            if (a < 13) {
                if (card13.getVisibility() == View.VISIBLE) card13.setVisibility(View.GONE);
                if (title13.getVisibility() == View.VISIBLE) title13.setVisibility(View.GONE);
                if (q13.getVisibility() == View.VISIBLE) q13.setVisibility(View.GONE);
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autotest);
        txtinicio = findViewById(R.id.txtinicio);
        btnRT = findViewById(R.id.btnRT);
        txtinicio.setText("Este auto-test le permitirá orientarse con respecto a los síntomas de la COVID-19, pero debe saber que nunca sustituirá una consulta médica. Si presenta algún síntoma que indique gravedad deberá consultar con el servicio de emergencias sanitarias en el 061.");
        defbtn = new Button(this);
        btnconf = findViewById(R.id.btnconf);

        btntest1Si = findViewById(R.id.btntest1Si);
        btntest1No = findViewById(R.id.btntest1No);

        card2 = (CardView) findViewById(R.id.card2);
        btntest2Si = findViewById(R.id.btntest2Si);
        btntest2No = findViewById(R.id.btntest2No);
        title2 = findViewById(R.id.title2);
        q2 = findViewById(R.id.q2);

        card3 = (CardView) findViewById(R.id.card3);
        btntest3Si = findViewById(R.id.btntest3Si);
        btntest3No = findViewById(R.id.btntest3No);
        title3 = findViewById(R.id.title3);
        q3 = findViewById(R.id.q3);

        card4 = (CardView) findViewById(R.id.card4);
        btntest4Si = findViewById(R.id.btntest4Si);
        btntest4No = findViewById(R.id.btntest4No);
        title4 = findViewById(R.id.title4);
        q4 = findViewById(R.id.q4);

        card5 = (CardView) findViewById(R.id.card5);
        btntest5Si = findViewById(R.id.btntest5Si);
        btntest5No = findViewById(R.id.btntest5No);
        title5 = findViewById(R.id.title5);
        q5 = findViewById(R.id.q5);

        card6 = (CardView) findViewById(R.id.card6);
        btntest6Si = findViewById(R.id.btntest6Si);
        btntest6No = findViewById(R.id.btntest6No);
        title6 = findViewById(R.id.title6);
        q6 = findViewById(R.id.q6);

        card7 = (CardView) findViewById(R.id.card7);
        btntest7Si = findViewById(R.id.btntest7Si);
        btntest7No = findViewById(R.id.btntest7No);
        title7 = findViewById(R.id.title7);
        q7 = findViewById(R.id.q7);
        q71 = findViewById(R.id.q71);

        btntest8Si = findViewById(R.id.btntest8Si);
        btntest8No = findViewById(R.id.btntest8No);
        q8 = findViewById(R.id.q8);

        btntest9Si = findViewById(R.id.btntest9Si);
        btntest9No = findViewById(R.id.btntest9No);
        q9 = findViewById(R.id.q9);

        btntest10Si = findViewById(R.id.btntest10Si);
        btntest10No = findViewById(R.id.btntest10No);
        q10 = findViewById(R.id.q10);

        btntest11Si = findViewById(R.id.btntest11Si);
        btntest11No = findViewById(R.id.btntest11No);
        q11 = findViewById(R.id.q11);

        btntest12Si = findViewById(R.id.btntest12Si);
        btntest12No = findViewById(R.id.btntest12No);
        q12 = findViewById(R.id.q12);

        card13 = (CardView) findViewById(R.id.card13);
        title13 = findViewById(R.id.title13);
        q13 = findViewById(R.id.q13);

        xGone(1);

        btnRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(1);
                al.clear();
                subal.clear();
                btntest1Si.setBackground(defbtn.getBackground());
                btntest1No.setBackground(defbtn.getBackground());
            }
        });

        btntest1Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                subal.clear();
                xGone(1);
                if (!al.contains("test1Si")) al.add("test1Si");
                if (al.contains("test1No")) al.remove("test1No");
                btntest1Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest1No.setBackground(defbtn.getBackground());
                visibility(card2, title2, q2, btntest2Si, btntest2No);
            }
        });

        btntest1No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al.clear();
                subal.clear();
                xGone(1);
                if (!al.contains("test1No")) al.add("test1No");
                if (al.contains("test1Si")) al.remove("test1Si");
                btntest1No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest1Si.setBackground(defbtn.getBackground());
                visibility(card2, title2, q2, btntest2Si, btntest2No);
            }
        });

        btntest2Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(2);
                if (!al.contains("test2Si")) al.add("test2Si");
                if (al.contains("test2No")) al.remove("test2No");


                title3.setTextColor(Color.parseColor("#818181"));

                q3.setTextColor(Color.parseColor("#818181"));

                card3.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                title3.setText("3. DURACIÓN DE LOS SÍNTOMAS");
                q3.setText("¿Hace más de 14 días que comenzaron los síntomas?");
                btntest2Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest2No.setBackground(defbtn.getBackground());
                visibility(card3, title3, q3, btntest3Si, btntest3No);
            }
        });

        btntest2No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(2);
                if (!al.contains("test2No")) al.add("test2No");
                if (al.contains("test2Si")) al.remove("test2Si");
                btntest2No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest2Si.setBackground(defbtn.getBackground());

                if (al.contains("test1Si") && al.contains("test2No")) {

                    title3.setTextColor(Color.parseColor("#FFFFFF"));

                    q3.setTextColor(Color.parseColor("#FFFFFF"));

                    card3.setCardBackgroundColor(Color.parseColor("#FF6442"));

                    title3.setText("ATENCIÓN");
                    q3.setText("Usted tuvo un contacto estrecho con un caso de COVID-19, por lo tanto debe permanecer en su casa durante los 14 días que dura una cuarentena evitando los contactos con otras personas.\n" +
                            "\n" +
                            "Llame al número 900 400 116 o pida una consulta telefónica con su centro de salud para recibir las indicaciones específicas en su situación si no contactaron con usted ya.");
                    visibility(card3, title3, q3, btntest3Si, btntest3No);
                    if (btntest3No.getVisibility() == View.VISIBLE)
                        btntest3No.setVisibility(View.GONE);
                    if (btntest3Si.getVisibility() == View.VISIBLE)
                        btntest3Si.setVisibility(View.GONE);
                }
                else if (al.contains("test1No") && al.contains("test2No")) {

                    title3.setTextColor(Color.parseColor("#FFFFFF"));

                    q3.setTextColor(Color.parseColor("#FFFFFF"));

                    card3.setCardBackgroundColor(Color.parseColor("#7FE153"));

                    title3.setText("RESULTADO");
                    q3.setText("En este momento su situación no requiere asistencia sanitaria. Recuerde seguir manteniendo las recomendaciones generales de distanciamiento social, higiene y protección recomendadas.");

                    visibility(card3, title3, q3, btntest3Si, btntest3No);
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
                btntest3Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest3No.setBackground(defbtn.getBackground());
                title4.setTextColor(Color.parseColor("#FFFFFF"));

                q4.setTextColor(Color.parseColor("#FFFFFF"));

                card4.setCardBackgroundColor(Color.parseColor("#EEEE6A"));

                title4.setText("MÁS DE 14 DÍAS CON SÍNTOMAS");
                q4.setText("La COVID-19 se presenta como una enfermedad aguda, por lo tanto los síntomas que presenta en este momento podrían deberse a otra causa diferente del nuevo coronavirus. \n" +
                        "\n" +
                                "Solicite cita en su centro de salud para que valoren sus síntomas. Por favor, recuerde que cualquier persona con síntomas compatibles con la Covid debe quedarse en su domicilio y limitar los contactos con otras personas.");

                visibility(card4, title4, q4, btntest4Si, btntest4No);
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
                btntest3No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest3Si.setBackground(defbtn.getBackground());
                title4.setTextColor(Color.parseColor("#818181"));

                q4.setTextColor(Color.parseColor("#818181"));

                card4.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                title4.setText("4. DIFICULTAD RESPIRATORIA");
                q4.setText("¿Tiene dificultad repentina para respirar o nota falta de aire?");
                visibility(card4, title4, q4, btntest4Si, btntest4No);
            }
        });

        btntest4Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(4);
                if (!al.contains("test4Si")) al.add("test4Si");
                if (al.contains("test4No")) al.remove("test4No");
                btntest4Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest4No.setBackground(defbtn.getBackground());
                title5.setTextColor(Color.parseColor("#FFFFFF"));

                q5.setTextColor(Color.parseColor("#FFFFFF"));

                card5.setCardBackgroundColor(Color.parseColor("#FF6442"));

                title5.setText("ATENCIÓN");
                q5.setText("Sus síntomas parecen indicar gravedad. Llame al 061 indicando los síntomas que presenta.");
                visibility(card5, title5, q5, btntest5Si, btntest5No);
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
                btntest4No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest4Si.setBackground(defbtn.getBackground());
                title5.setTextColor(Color.parseColor("#818181"));

                q5.setTextColor(Color.parseColor("#818181"));

                card5.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                title5.setText("5. FIEBRE > 37,7ºC");
                q5.setText("¿Presenta fiebre de más de 37,7ºC?");
                visibility(card5, title5, q5, btntest5Si, btntest5No);
            }
        });

        btntest5Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(5);
                if (!al.contains("test5Si")) al.add("test5Si");
                if (al.contains("test5No")) al.remove("test5No");
                btntest5Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest5No.setBackground(defbtn.getBackground());
                title6.setTextColor(Color.parseColor("#818181"));

                q6.setTextColor(Color.parseColor("#818181"));

                title6.setText("6. FIEBRE > 39ºC");
                q6.setText("¿Su temperatura es mayor de 39ºC?");
                visibility(card6, title6, q6, btntest6Si, btntest6No);
            }
        });

        btntest5No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(5);
                if (!al.contains("test5No")) al.add("test5No");
                if (al.contains("test5Si")) al.remove("test5Si");
                btntest5No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest5Si.setBackground(defbtn.getBackground());
                title6.setTextColor(Color.parseColor("#818181"));

                q6.setTextColor(Color.parseColor("#818181"));

                title6.setText("6. SENSACIÓN FEBRIL");
                q6.setText("¿Pese a no presentar fiebre, nota sensación febril o escalofríos?");
                visibility(card6, title6, q6, btntest6Si, btntest6No);
            }
        });

        btntest6Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(6);
                if (!al.contains("test6Si")) al.add("test6Si");
                if (al.contains("test6No")) al.remove("test6No");
                btntest6Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest6No.setBackground(defbtn.getBackground());
                if (al.contains("test5Si")) {

                    title7.setTextColor(Color.parseColor("#FFFFFF"));

                    q7.setTextColor(Color.parseColor("#FFFFFF"));

                    card7.setCardBackgroundColor(Color.parseColor("#FF6442"));

                    title7.setText("ATENCIÓN");
                    q7.setText("Sus síntomas parecen indicar gravedad. Llame al 061 indicando los síntomas que presenta.");
                    visibility(card7, title7, q7, btntest7Si, btntest7No);
                    if (btnconf.getVisibility() == View.VISIBLE) btnconf.setVisibility(View.GONE);
                    if(btntest7No.getVisibility() == View.VISIBLE) btntest7No.setVisibility(View.GONE);
                    if(btntest7Si.getVisibility() == View.VISIBLE) btntest7Si.setVisibility(View.GONE);
                }
                else if (al.contains("test5No")) {

                    title7.setTextColor(Color.parseColor("#818181"));

                    q7.setTextColor(Color.parseColor("#818181"));

                    card7.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                    title7.setText("7. OTROS SÍNTOMAS");
                    q7.setText("Por favor especifique qué otros síntomas está usted experimentando:");
                    visibility(card7, title7, q7, btntest7Si, btntest7No);
                    if (btnconf.getVisibility() == View.GONE) btnconf.setVisibility(View.VISIBLE);
                    if (q71.getVisibility() == View.GONE) q71.setVisibility(View.VISIBLE);
                    visibility(card7, title7, q8, btntest8Si, btntest8No);
                    visibility(card7, title7, q9, btntest9Si, btntest9No);
                    visibility(card7, title7, q10, btntest10Si, btntest10No);
                    visibility(card7, title7, q11, btntest11Si, btntest11No);
                    visibility(card7, title7, q12, btntest12Si, btntest12No);
                }
            }
        });

        btntest6No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(6);
                if (!al.contains("test6No")) al.add("test6No");
                if (al.contains("test6Si")) al.remove("test6Si");
                btntest6No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest6Si.setBackground(defbtn.getBackground());
                title7.setTextColor(Color.parseColor("#818181"));

                q7.setTextColor(Color.parseColor("#818181"));

                card7.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                title7.setText("7. OTROS SÍNTOMAS");
                q7.setText("Por favor especifique qué otros síntomas está usted experimentando:");
                visibility(card7, title7, q7, btntest7Si, btntest7No);
                if (q71.getVisibility() == View.GONE) q71.setVisibility(View.VISIBLE);
                if (btnconf.getVisibility() == View.GONE) btnconf.setVisibility(View.VISIBLE);
                visibility(card7, title7, q8, btntest8Si, btntest8No);
                visibility(card7, title7, q9, btntest9Si, btntest9No);
                visibility(card7, title7, q10, btntest10Si, btntest10No);
                visibility(card7, title7, q11, btntest11Si, btntest11No);
                visibility(card7, title7, q12, btntest12Si, btntest12No);
            }
        });
        btntest7Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!subal.contains("test7Si")) subal.add("test7Si");
                if (al.contains("test7No")) al.remove("test7No");
                btntest7Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest7No.setBackground(defbtn.getBackground());
            }
        });
        btntest7No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!al.contains("test7No")) al.add("test7No");
                if (subal.contains("test7Si")) subal.remove("test7Si");
                btntest7No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest7Si.setBackground(defbtn.getBackground());
            }
        });
        btntest8Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!subal.contains("test8Si")) subal.add("test8Si");
                if (al.contains("test8No")) al.remove("test8No");
                btntest8Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest8No.setBackground(defbtn.getBackground());
            }
        });
        btntest8No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!al.contains("test8No")) al.add("test8No");
                if (subal.contains("test8Si")) subal.remove("test8Si");
                btntest8No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest8Si.setBackground(defbtn.getBackground());
            }
        });
        btntest9Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!subal.contains("test9Si")) subal.add("test9Si");
                if (al.contains("test9No")) al.remove("test9No");
                btntest9Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest9No.setBackground(defbtn.getBackground());
            }
        });
        btntest9No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!al.contains("test9No")) al.add("test9No");
                if (subal.contains("test9Si")) subal.remove("test9Si");
                btntest9No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest9Si.setBackground(defbtn.getBackground());
            }
        });
        btntest10Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!subal.contains("test10Si")) subal.add("test10Si");
                if (al.contains("test10No")) al.remove("test10No");
                btntest10Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest10No.setBackground(defbtn.getBackground());
            }
        });
        btntest10No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!al.contains("test10No")) al.add("test10No");
                if (subal.contains("test10Si")) subal.remove("test10Si");
                btntest10No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest10Si.setBackground(defbtn.getBackground());
            }
        });
        btntest11Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!subal.contains("test11Si")) subal.add("test11Si");
                if (al.contains("test11No")) al.remove("test11No");
                btntest11Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest11No.setBackground(defbtn.getBackground());
            }
        });
        btntest11No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!al.contains("test11No")) al.add("test11No");
                if (subal.contains("test11Si")) subal.remove("test11Si");
                btntest11No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest11Si.setBackground(defbtn.getBackground());
            }
        });
        btntest12Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!subal.contains("test12Si")) subal.add("test12Si");
                if (al.contains("test12No")) al.remove("test12No");
                btntest12Si.setBackgroundColor(Color.parseColor("#448afd"));
                btntest12No.setBackground(defbtn.getBackground());
            }
        });
        btntest12No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGone(12);
                if (!al.contains("test12No")) al.add("test12No");
                if (subal.contains("test12Si")) subal.remove("test12Si");
                btntest12No.setBackgroundColor(Color.parseColor("#448afd"));
                btntest12Si.setBackground(defbtn.getBackground());

            }
        });

        btnconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // miramos si el usuario ha pulsado todas las opciones antes de aceptar (suma del array con respuestas si + array respuestas no)
                if (subal.size() + (al.size() - 6) != 6) {
                    title13.setTextColor(Color.parseColor("#FFFFFF"));
                    q13.setTextColor(Color.parseColor("#FFFFFF"));
                    card13.setCardBackgroundColor(Color.parseColor("#FF6442"));
                    title13.setText("FALTAN DATOS");
                    q13.setText("Por favor, contesta todas las preguntas antes de confirmar.");
                    if (q13.getVisibility() == View.GONE) q13.setVisibility(View.VISIBLE);
                    if (card13.getVisibility() == View.GONE) card13.setVisibility(View.VISIBLE);
                    if (title13.getVisibility() == View.GONE) title13.setVisibility(View.VISIBLE);
                }
                else if ((al.contains("test5Si") && subal.size() >= 2) || (al.contains("test5No") && (al.contains("test6Si")) && subal.size() >= 4) || (al.contains("test5No") && (al.contains("test6No")) && subal.size() >= 6)) {
                    // fiebre con varios síntomas
                    // ROJO
                    title13.setTextColor(Color.parseColor("#FFFFFF"));
                    q13.setTextColor(Color.parseColor("#FFFFFF"));
                    card13.setCardBackgroundColor(Color.parseColor("#FF6442"));
                    title13.setText("CUIDADO");
                    q13.setText("Sus síntomas parecen indicar gravedad. Llame al 061 indicando los síntomas que presenta.");
                    if (q13.getVisibility() == View.GONE) q13.setVisibility(View.VISIBLE);
                    if (card13.getVisibility() == View.GONE) card13.setVisibility(View.VISIBLE);
                    if (title13.getVisibility() == View.GONE) title13.setVisibility(View.VISIBLE);
                }
                else if ((al.contains("test5Si") && subal.size() < 2) || (al.contains("test5No") && (al.contains("test6Si")) && subal.size() < 4) || (al.contains("test5No") && (al.contains("test6No")) && subal.size() < 6 && subal.size() >= 2)) {
                    // fiebre con pocos o ningún síntoma
                    // AMARILLO
                    title13.setTextColor(Color.parseColor("#FFFFFF"));
                    q13.setTextColor(Color.parseColor("#FFFFFF"));
                    card13.setCardBackgroundColor(Color.parseColor("#EEEE6A"));
                    title13.setText("ATENCION");
                    q13.setText("Los síntomas que presenta en este momento podrían deberse a otra causa diferente del coronavirus. \n" +
                            "\n" +
                            "Solicite cita en su centro de salud para que valoren sus síntomas. Por favor, recuerde que cualquier persona con síntomas compatibles con la Covid debe quedarse en su domicilio y limitar los contactos con otras personas.");
                    if (q13.getVisibility() == View.GONE) q13.setVisibility(View.VISIBLE);
                    if (card13.getVisibility() == View.GONE) card13.setVisibility(View.VISIBLE);
                    if (title13.getVisibility() == View.GONE) title13.setVisibility(View.VISIBLE);
                }
                else if (al.contains("test5No") && (al.contains("test6No")) && subal.size() < 2) {
                    // sin fiebre y pocos o ningun síntomas
                    // VERDE
                    title13.setTextColor(Color.parseColor("#FFFFFF"));
                    q13.setTextColor(Color.parseColor("#FFFFFF"));
                    card13.setCardBackgroundColor(Color.parseColor("#7FE153"));
                    title13.setText("RESULTADO");
                    q13.setText("En este momento su situación no requiere asistencia sanitaria, pero solicite cita en su centro de salud si empeora o si lo considera necesario. Recuerde seguir manteniendo las recomendaciones generales de distanciamiento social, higiene y protección recomendadas.");
                    if (q13.getVisibility() == View.GONE) q13.setVisibility(View.VISIBLE);
                    if (card13.getVisibility() == View.GONE) card13.setVisibility(View.VISIBLE);
                    if (title13.getVisibility() == View.GONE) title13.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
