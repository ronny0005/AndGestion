package com.example.tron.andgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.modele.Parametre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TableauMapClientActivity extends AppCompatActivity {
    Button afficher;
    TextView dt_deb;
    TextView dt_fin;
    ListView lst_equation ;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    AutoCompleteTextView client;
    List<Map<String, String>> data;
    outils ou;
    ArrayList<Facture> liste_facture;
    ArrayList<String> lclient = new ArrayList<String>();
    ArrayList<Client> lst_client;

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
            //ou.listeFacture(param.getCo_no(),new SimpleDateFormat("yyyy-MM-dd").format(deb),new SimpleDateFormat("yyyy-MM-dd").format(fin),"0",ou.getVille(param.getDo_souche(),param.getCt_num()));
            String nom_client="";
            for(int i=0;i<lst_client.size();i++)
                if(lst_client.get(i).getIntitule().equals(client.getText().toString()))
                    nom_client=lst_client.get(i).getNum();

            liste_facture=ou.listeFactureClient(param.getCo_no(),new SimpleDateFormat("yyyy-MM-dd").format(deb),new SimpleDateFormat("yyyy-MM-dd").format(fin),nom_client,ou.getVille(param.getDo_souche(),param.getCt_num()),lst_client);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void ajoutListe(Date deb,Date fin,String client){
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

    private void affiche(){
        if(!dt_deb.getText().toString().isEmpty() && !dt_fin.getText().toString().isEmpty() && !client.getText().toString().isEmpty() ) {
            try {

                DateFormat format = new SimpleDateFormat("ddMMyy", Locale.FRENCH);
                Date deb = format.parse(dt_deb.getText().toString());
                Date fin = format.parse(dt_fin.getText().toString());
                ajoutListe(deb,fin,client.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    private void passeVariable(Intent intent,int pos){
        intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
        intent.putExtra("outils", ou);
        intent.putExtra("liste_recouvrement", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_recouvrement"));
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        intent.putExtra("position",pos);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etat_map_client_tableau);
        this.setTitle("Tableau map");
        ou = (outils) getIntent().getSerializableExtra("outils");
        param = (Parametre) getIntent().getSerializableExtra("parametre");
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        lst_client = (ArrayList<Client>) getIntent().getSerializableExtra("liste_client");

        ou.app=TableauMapClientActivity.this;
        afficher = (Button) findViewById(R.id.map_tableau_afficher);
        dt_deb = (TextView) findViewById(R.id.map_tableau_dt_deb);
        dt_fin = (TextView) findViewById(R.id.map_tableau_dt_fin);

        lst_equation = (ListView) findViewById(R.id.map_tableau_lv);
        dt_deb.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        dt_fin.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        for (int i = 0; i < lst_client.size(); i++)
            lclient.add(lst_client.get(i).getIntitule());
        client = (AutoCompleteTextView) findViewById(R.id.map_tableau_client);
        client.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lclient);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        client.setAdapter(adapter);

        affiche();
        afficher.setOnClickListener(new View.OnClickListener(){
               public void onClick(View v) {
               affiche();
           }
        });
        lst_equation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TableauMapClientActivity.this, MapsActivity.class);
                passeVariable(intent,position);
            }
        });

    }
    }
