package com.example.tron.andgestion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.Stock.BmqModele;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BmqActivity extends AppCompatActivity {


    Button afficher;
    TextView dt_deb;
    TextView dt_fin;
    ListView lst_manquant;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    List<Map<String, String>> data;
    Map<String, String> datum;
    ArrayList<BmqModele> lstk;
    outils ou;
    Parametre param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmq);
        ou = (outils) getIntent().getSerializableExtra("outils");
        param = (Parametre) getIntent().getSerializableExtra("parametre");
        ou.app=BmqActivity.this;
        afficher = (Button) findViewById(R.id.btnBmqValider);
        dt_deb = (TextView) findViewById(R.id.dateDebBmq);
        dt_fin = (TextView) findViewById(R.id.dateFinBmq);
        lst_manquant = (ListView) findViewById(R.id.listBmq);
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

        lstk = new ArrayList<BmqModele>();
        double total_tva = 0,total_precompte=0,total_remise=0,total_comptant=0,total_ht=0,manquant = 0;
        lstk = ou.getBmq(19, new SimpleDateFormat("yyyy-MM-dd").format(deb), new SimpleDateFormat("yyyy-MM-dd").format(fin));
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (int i = 0; i < lstk.size(); i++) {

            total_tva = total_tva + lstk.get(i).getTVA();
            total_comptant = total_comptant + lstk.get(i).getCOMPTANT_TTC();
            total_ht=total_ht+lstk.get(i).getVALEUR();
            total_precompte=total_precompte+lstk.get(i).getPRECOMPTE();
            total_remise=total_remise+lstk.get(i).getREMISE();
            manquant = lstk.get(i).getMANQUANT();

            data.add(createRow(lstk.get(i).getDL_Design(), " "
                            + "\n Recu : " + lstk.get(i).getRECU()
                            + "\n Retour : " + lstk.get(i).getRETOUR()+"  Avari : "+lstk.get(i).getAVARI()
                            + "\n Total recu : " + (lstk.get(i).getRECU()-(lstk.get(i).getRETOUR()+lstk.get(i).getAVARI()))
                            + "\n Total vendu : " + lstk.get(i).getVENDU()+"  Valeur : "+lstk.get(i).getVALEUR()
            ));
        }
        DecimalFormat decim = new DecimalFormat("#.##");
        data.add(createRow("TOTAL HT: " + decim.format(total_ht), ""));
        data.add(createRow("TOTAL TVA: " + decim.format(total_tva), ""));
        data.add(createRow("TOTAL PRECOMPTE: " + decim.format(total_precompte), ""));
        data.add(createRow("TOTAL TTC: " + decim.format(total_ht+total_tva+total_precompte), ""));
        data.add(createRow("TOTAL COMPTANT: " + decim.format(total_comptant), ""));
        data.add(createRow("TOTAL CREDIT: " + decim.format(((total_ht+total_precompte+total_tva)-total_comptant)), ""));
        data.add(createRow("MANQUANT : " +decim.format(manquant), ""));
        data.add(createRow("NET A ENCAISSE : " +decim.format(total_comptant), ""));
        data.add(createRow("NET VERSER : " +decim.format((total_comptant-manquant)), ""));

        String[] from = {"value1", "value2"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, from, to);
        lst_manquant.setAdapter(adapter);
    }

}