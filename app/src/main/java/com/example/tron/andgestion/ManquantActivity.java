package com.example.tron.andgestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.manquant.ManquantModele;
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

public class ManquantActivity extends AppCompatActivity {

    Button afficher;
    TextView dt_deb;
    TextView dt_fin;
    ListView lst_manquant;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    List<Map<String, String>> data;
    Map<String, String> datum;
    ArrayList<ManquantModele> lstk;
    private outils ou;
    private Parametre parametre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etatmanquant);
        this.setTitle("Etat manquant");
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = ManquantActivity.this;
        parametre = (Parametre) getIntent().getSerializableExtra("parametre");

        afficher = (Button) findViewById(R.id.btnManValider);
        dt_deb = (TextView) findViewById(R.id.dateDebMan);
        dt_fin = (TextView) findViewById(R.id.dateFinMan);
        lst_manquant = (ListView) findViewById(R.id.listMan);
        dt_deb.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        dt_fin.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        afficher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!dt_deb.getText().toString().isEmpty() && !dt_fin.getText().toString().isEmpty()) {
                    try {

                        DateFormat format = new SimpleDateFormat("ddMMyy", Locale.FRENCH);
                        Date deb = format.parse(dt_deb.getText().toString());
                        Date fin = format.parse(dt_fin.getText().toString());
                        ajoutListe(deb, fin);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private Map<String, ?> createRow(String value1, String value2) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("value1", value1);
        row.put("value2", value2);
        return row;
    }


    public void ajoutListe(Date deb, Date fin) {
        lstk = new ArrayList<ManquantModele>();
        double total=0;
        //data = new ArrayList<Map<String, String>>();
        lstk = ou.getManquant(19,new SimpleDateFormat("yyyy-MM-dd").format(deb), new SimpleDateFormat("yyyy-MM-dd").format(fin));
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (int i = 0; i < lstk.size(); i++) {
            total = total + lstk.get(i).getMontant();
            data.add(createRow(lstk.get(i).getCT_Intitule(), " "
                    +"\n Date : " + lstk.get(i).getDate()
                    + "\n Montant : " + lstk.get(i).getMontant()));
        }
        data.add(createRow("TOTAL : "+total, ""));
        String[] from = {"value1", "value2"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, from, to);
        lst_manquant.setAdapter(adapter);
    }


}
