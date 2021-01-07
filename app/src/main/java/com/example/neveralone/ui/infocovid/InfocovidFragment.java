package com.example.neveralone.ui.infocovid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class InfocovidFragment extends DialogFragment{
    private TextView confirmados, muertos, recuperados;
    private TextView confirmados_espana, muertos_espana, recuperados_espana;
    private TextView confirmados_mundial, muertos_mundial, recuperados_mundial;
    private Button datepicker;
    private boolean internet;
    private ImageButton button_cerca;
    private String fecha, id_comunitat;
    private LinearLayout contenido, imagencarga, imagensininternet, fechamal;
    private ArrayList<String> comunitats = new ArrayList<String>();
    private ArrayList<String> comunitats_id = new ArrayList<String>();
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_infocovid, container, false);
        super.onCreate(savedInstanceState);
        internet = true;
        contenido = (LinearLayout) root.findViewById(R.id.contenido);
        imagencarga = (LinearLayout) root.findViewById(R.id.imagencarga);
        fechamal = (LinearLayout) root.findViewById(R.id.fechamal);
        imagensininternet = (LinearLayout) root.findViewById(R.id.imagensininternet);
        datepicker = (Button) root.findViewById(R.id.datepicker);
        button_cerca = (ImageButton) root.findViewById(R.id.button_cerca);
        confirmados  = (TextView) root.findViewById(R.id.confirmados);
        muertos = (TextView) root.findViewById(R.id.muertos);
        recuperados = (TextView) root.findViewById(R.id.recuperados);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        int arradapter = android.R.layout.simple_spinner_dropdown_item;

        confirmados_espana  = (TextView) root.findViewById(R.id.confirmados_espana);
        muertos_espana = (TextView) root.findViewById(R.id.muertos_espana);
        recuperados_espana = (TextView) root.findViewById(R.id.recuperados_espana);

        confirmados_mundial  = (TextView) root.findViewById(R.id.confirmados_mundial);
        muertos_mundial = (TextView) root.findViewById(R.id.muertos_mundial);
        recuperados_mundial = (TextView) root.findViewById(R.id.recuperados_mundial);

        getData(root,arradapter);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mDateSetListener, sYearIni, sMonthIni, sDayIni);
                datePickerDialog.show();
            }
        });
        button_cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("busqueda", fecha + " " + id_comunitat);
                getDataFinal();
                int mes = mMonthIni + 1;
                Log.d("fecha exemple",  mYearIni + " " + mes + " " + mDayIni);
                Log.d("fecha hoy",  sYearIni + " " + sMonthIni+1 + " " + sDayIni);

                if(mYearIni < Integer.parseInt("2020")
                        ||
                        (mYearIni == Integer.parseInt("2020")
                                && mes == Integer.parseInt("01")
                                && mDayIni < Integer.parseInt("23"))
                        ||
                        (sYearIni < mYearIni)
                        ||
                        ((sYearIni == mYearIni) && (sMonthIni < mMonthIni))
                        ||
                        ((sYearIni == mYearIni) && (sMonthIni == mMonthIni) && (sDayIni < mDayIni))){
                    Log.d("fechamal", "fechamal");
                    imagencarga.setVisibility(View.VISIBLE);
                    fechamal.setVisibility(View.VISIBLE);
                    contenido.setVisibility(View.INVISIBLE);
                    imagensininternet.setVisibility(View.INVISIBLE);
                }
                else {
                    fechamal.setVisibility(View.INVISIBLE);
                    if (internet) {
                        contenido.setVisibility(View.VISIBLE);
                        imagencarga.setVisibility(View.INVISIBLE);
                        imagensininternet.setVisibility(View.INVISIBLE);
                    } else {
                        contenido.setVisibility(View.INVISIBLE);
                        imagensininternet.setVisibility(View.VISIBLE);
                        imagencarga.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        // Inflate the layout for this fragment
        return root;
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

            string_confirmados = "<b>" + jsonregion.getString("today_confirmed") + "</b>" + " de los cuales " +
                    "<b>" + jsonregion.getString("today_new_confirmed") + "</b>" + " son nuevos confirmados.";
            confirmados.setText(Html.fromHtml(string_confirmados));
            string_muertos = "<b>" + jsonregion.getString("today_deaths") + "</b>" + " de las cuales " +
                    "<b>" + jsonregion.getString("today_new_deaths") + "</b>" + " son nuevas muertes.";
            muertos.setText(Html.fromHtml(string_muertos));
            string_recuperados = "<b>" + jsonregion.getString("today_recovered") +"</b>"+ " de las cuales " +
                    "<b>" + jsonregion.getString("today_new_recovered") + "</b>" + " son nuevos recuperados.";
            recuperados.setText(Html.fromHtml(string_recuperados));

            string_confirmados_espana = jsonspain.getString("today_confirmed");
            confirmados_espana.setText(string_confirmados_espana);
            string_muertos_espana = jsonspain.getString("today_deaths");
            muertos_espana.setText(string_muertos_espana);
            string_recuperados_espana = jsonspain.getString("today_recovered");
            recuperados_espana.setText(string_recuperados_espana);

            string_confirmados_mundial = jsontotal.getString("today_confirmed");
            confirmados_mundial.setText(string_confirmados_mundial);
            string_muertos_mundial = jsontotal.getString("today_deaths");
            muertos_mundial.setText(string_muertos_mundial);
            string_recuperados_mundial = jsontotal.getString("today_recovered");
            recuperados_mundial.setText(string_recuperados_mundial);
            internet = true;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            internet = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(View root, int arradapter){
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
            Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(),
                    arradapter,
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
            internet = true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            internet = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}