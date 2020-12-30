package com.example.neveralone.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neveralone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView confirmados, muertos, recuperados;
    TextView confirmados_espana, muertos_espana, recuperados_espana;
    TextView confirmados_mundial, muertos_mundial, recuperados_mundial;
    EditText datepicker;
    Button button_cerca;
    String fecha, id_comunitat;
    LinearLayout contenido;
    ArrayList<String> comunitats = new ArrayList<String>();
    ArrayList<String> comunitats_id = new ArrayList<String>();
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infocovid);
        contenido = (LinearLayout) findViewById(R.id.contenido);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        datepicker = (EditText) findViewById(R.id.datepicker);
        button_cerca = (Button) findViewById(R.id.button_cerca);
        confirmados  = (TextView) findViewById(R.id.confirmados);
        muertos = (TextView) findViewById(R.id.muertos);
        recuperados = (TextView) findViewById(R.id.recuperados);

        confirmados_espana  = (TextView) findViewById(R.id.confirmados_espana);
        muertos_espana = (TextView) findViewById(R.id.muertos_espana);
        recuperados_espana = (TextView) findViewById(R.id.recuperados_espana);

        confirmados_mundial  = (TextView) findViewById(R.id.confirmados_mundial);
        muertos_mundial = (TextView) findViewById(R.id.muertos_mundial);
        recuperados_mundial = (TextView) findViewById(R.id.recuperados_mundial);

        getData();
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_ID);
            }
        });
        button_cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("busqueda", fecha + " " + id_comunitat);
                contenido.setVisibility(View.VISIBLE);
                getDataFinal();
            }
        });
    }

    private void colocar_fecha() {
        datepicker.setText(String.format("%02d",mMonthIni + 1) + "-" + String.format("%02d",mDayIni) + "-" + mYearIni+" ");
        fecha = String.format(String.valueOf(mYearIni) + "-" + String.format("%02d",mMonthIni+1) + "-" + String.format("%02d",mDayIni));
        Log.d("datesper",fecha);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();
                }

            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    public void getDataFinal() {
        String sqlfinal = "https://api.covid19tracking.narrativa.com/api/" + fecha + "/country/spain/region/" + id_comunitat;

        URL urlfinal = null;
        HttpURLConnection connfinal;

        try {
            urlfinal = new URL(sqlfinal);
            connfinal = (HttpURLConnection) urlfinal.openConnection();

            connfinal.setRequestMethod("GET");

            connfinal.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connfinal.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            json = response.toString();

            JSONObject jsontotal = new JSONObject(json).getJSONObject("total");
            JSONObject jsonspain = new JSONObject(json).getJSONObject("dates")
                    .getJSONObject(fecha).getJSONObject("countries").getJSONObject("Spain");
            JSONObject jsonregion = jsonspain.getJSONArray("regions").getJSONObject(0);

            String string_confirmados, string_muertos, string_recuperados;
            String string_confirmados_espana, string_muertos_espana, string_recuperados_espana;
            String string_confirmados_mundial, string_muertos_mundial, string_recuperados_mundial;

            string_confirmados = jsonregion.getString("today_confirmed") + " de los cuales " +
                    jsonregion.getString("today_new_confirmed") + " son nuevos confirmados.";
            confirmados.setText(string_confirmados);
            string_muertos = jsonregion.getString("today_deaths") + " de las cuales " +
                    jsonregion.getString("today_new_deaths") + " son nuevas muertes.";
            muertos.setText(string_muertos);
            string_recuperados = jsonregion.getString("today_recovered") + " de las cuales " +
                    jsonregion.getString("today_new_recovered") + " son nuevos recuperados.";
            recuperados.setText(string_recuperados);

            string_confirmados_espana = jsonspain.getString("today_confirmed") + " de los cuales " +
                    jsonspain.getString("today_new_confirmed") + " son nuevos confirmados.";
            confirmados_espana.setText(string_confirmados_espana);
            string_muertos_espana = jsonspain.getString("today_deaths") + " de las cuales " +
                    jsonspain.getString("today_new_deaths") + " son nuevas muertes.";
            muertos_espana.setText(string_muertos_espana);
            string_recuperados_espana = jsonspain.getString("today_recovered") + " de las cuales " +
                    jsonspain.getString("today_new_recovered") + " son nuevos recuperados.";
            recuperados_espana.setText(string_recuperados_espana);

            string_confirmados_mundial = jsontotal.getString("today_confirmed") + " de los cuales " +
                    jsontotal.getString("today_new_confirmed") + " son nuevos confirmados.";
            confirmados_mundial.setText(string_confirmados_mundial);
            string_muertos_mundial = jsontotal.getString("today_deaths") + " de las cuales " +
                    jsontotal.getString("today_new_deaths") + " son nuevas muertes.";
            muertos_mundial.setText(string_muertos_mundial);
            string_recuperados_mundial = jsontotal.getString("today_recovered") + " de las cuales " +
                    jsontotal.getString("today_new_recovered") + " son nuevos recuperados.";
            recuperados_mundial.setText(string_recuperados_mundial);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(){
        String sql = "https://api.covid19tracking.narrativa.com/api/countries/spain/regions";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();

            JSONObject jsonObj = new JSONObject(json);
            Log.d("nom",jsonObj.names().getString(0));
            JSONArray json0 = jsonObj.getJSONArray("countries").getJSONObject(0).getJSONArray("spain");

            for (int i=0; i<json0.length();i++){
                JSONObject jsonnoms = json0.getJSONObject(i);
                comunitats.add(jsonnoms.getString("name"));
                comunitats_id.add(jsonnoms.getString("id"));
                Log.d("noms", jsonnoms.getString("name"));
            }
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    comunitats);
            spinner.setAdapter(spinnerArrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int position, long id) {
                    id_comunitat = comunitats_id.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

            //JSONObject jsonObject = jsonObj.getJSONObject("dates").getJSONObject("2020-03-17").getJSONObject("countries").getJSONObject("Spain");
            //Log.d("noms","Noms" + jsonObject.names());
            //mensaje += "PAIS: "+jsonObject.optString("name_es")+"\n";
            //mensaje += "Dia: "+jsonObject.optString("date")+"\n";
            //mensaje += "Confirmats totals: "+jsonObject.optString("today_confirmed")+"\n";
            //sal.setText(mensaje);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}