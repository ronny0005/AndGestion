package com.example.tron.andgestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.Stock.StockEqVendeur;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TableauMapActivity extends AppCompatActivity {
    Button afficher;
    TextView dt_deb;
    TextView dt_fin;
    ListView lst_equation ;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    List<Map<String, String>> data;
    outils ou;
    ArrayList<Facture> liste_facture;
    Parametre param;
    Map<String, String> datum;

    private Map<String, ?> createRow(String value1, String value2) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("value1", value1);
        row.put("value2", value2);
        return row;
    }

    public void valideFacture(){
        try {
            DateFormat format = new SimpleDateFormat("ddMMyy", Locale.FRENCH);
            Date deb = format.parse(dt_deb.getText().toString());
            Date fin = format.parse(dt_fin.getText().toString());
            liste_facture=ou.listeFacture(param.getCo_no(),new SimpleDateFormat("yyyy-MM-dd").format(deb),new SimpleDateFormat("yyyy-MM-dd").format(fin));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void ajoutListe(Date deb,Date fin){
        data = new ArrayList<Map<String, String>>();
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        valideFacture();
        for(int i=0;i<liste_facture.size();i++) {
            data.add(createRow(liste_facture.get(i).getRef(), " N° Facture : " + liste_facture.get(i).getEntete()+"\n Client : " + liste_facture.get(i).getId_client().getIntitule()
            +"\n Position : " + liste_facture.get(i).getLongitude()+"," + liste_facture.get(i).getLatitude()));
        }
        String[] from = {"value1", "value2"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, data,android.R.layout.simple_list_item_2, from, to);
        lst_equation.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etat_map_tableau);
        this.setTitle("Tableau map");
        ou = (outils) getIntent().getSerializableExtra("outils");
        param = (Parametre) getIntent().getSerializableExtra("parametre");
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        ou.app=TableauMapActivity.this;
        afficher = (Button) findViewById(R.id.map_tableau_afficher);
        dt_deb = (TextView) findViewById(R.id.map_tableau_dt_deb);
        dt_fin = (TextView) findViewById(R.id.map_tableau_dt_fin);
        lst_equation = (ListView) findViewById(R.id.map_tableau_lv);
        dt_deb.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        dt_fin.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        afficher.setOnClickListener(new View.OnClickListener(){
               public void onClick(View v) {
               if(!dt_deb.getText().toString().isEmpty() && !dt_fin.getText().toString().isEmpty()) {
                   try {

                       DateFormat format = new SimpleDateFormat("ddMMyy", Locale.FRENCH);
                       Date deb = format.parse(dt_deb.getText().toString());
                       Date fin = format.parse(dt_fin.getText().toString());
                       ajoutListe(deb,fin);
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
               }
           }
        });
        }
    }
