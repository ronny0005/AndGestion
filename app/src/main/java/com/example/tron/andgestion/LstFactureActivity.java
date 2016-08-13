package com.example.tron.andgestion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.CompteA;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Parametre;
import com.example.tron.andgestion.modele.Vehicule;

import org.json.JSONException;

import java.io.IOException;
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

public class LstFactureActivity extends AppCompatActivity {
    Button nouveau;
    Button menu;
    Button valide;
    ArrayList<Facture> liste_facture;
    ListView lst_fact;
    ArrayList<String> lstr;
    ArrayList<ArticleServeur> liste_article;
    ArrayAdapter<String> arrayAdapter;
    List<Map<String, String>> data;
    outils ou;
    TextView datedeb;
    TextView datefin;
    Map<String, String> datum;
    int compteur = 0;
    Date datecmpt = new Date();
    Parametre parametre;
    private static final String PREFS_NAME = "compteur";

    private void itemCommun(Intent intent,Facture facture,int idfacture){
        intent.putExtra("liste_facture", liste_facture);
        intent.putExtra("outils", ou);
        intent.putExtra("facture", facture);
        intent.putExtra("id_facture", String.valueOf(idfacture));
        intent.putExtra("parametre", parametre);
        intent.putExtra("liste_recouvrement", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_recouvrement"));
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        intent.putExtra("liste_vehicule", (ArrayList<Vehicule>) getIntent().getSerializableExtra("liste_vehicule"));
        intent.putExtra("liste_cr", (ArrayList<CompteA>) getIntent().getSerializableExtra("liste_cr"));
        startActivity(intent);
    }

    public void valideFacture(){
        try {
            DateFormat format = new SimpleDateFormat("ddMMyy", Locale.FRENCH);
            Date deb = format.parse(datedeb.getText().toString());
            Date fin = format.parse(datefin.getText().toString());
            liste_facture=ou.listeFacture(parametre.getCo_no(),
                    new SimpleDateFormat("yyyy-MM-dd").format(deb),
                    new SimpleDateFormat("yyyy-MM-dd").format(fin),"0",ou.getVille(parametre.getDo_souche(),parametre.getCt_num()));
            ajoutListe();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void initVariable() {
        // Get from the SharedPreferences
        try {
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            DateFormat format = new SimpleDateFormat("yyy-MM-dd", Locale.FRENCH);
            String form = settings.getString("dateCmpt", null);
            if (form == null)
                form = format.format(new Date());
            datecmpt = format.parse(form);
            if (datecmpt.getDay() == (new Date()).getDay()) {
                compteur = settings.getInt("compteur", 0);
            } else {
                compteur = 0;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(compteur);
    }

    private Map<String, ?> createRow(String value1, String value2) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("value1", value1);
        row.put("value2", value2);
        return row;
    }

    public void ajoutListe() {
        data = new ArrayList<Map<String, String>>();
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        System.out.println(liste_facture.size()+" liste");
        for (int i = 0; i < liste_facture.size(); i++) {
            Facture fac = liste_facture.get(i);
            System.out.println(fac);
            String val="";
            DecimalFormat ttcformat = new DecimalFormat("#");
            if(fac.getStatut().equals("avance"))
                val=" ("+ttcformat.format(fac.getMtt_avance())+")";

            String nomclient = fac.getId_client().getIntitule();
            data.add(createRow(fac.getRef() + " - " + fac.getEntete()+" - " + fac.getStatut()+val ,
                    "Client : " + nomclient+ "\nTotal TTC : "+ fac.getTotalTTC()
                            +"\n"+fac.getDO_Date() ));
        }
        String[] from = {"value1", "value2"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, from, to);
        lst_fact.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_facture);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = LstFactureActivity.this;
        parametre=(Parametre) getIntent().getSerializableExtra("parametre");
        nouveau = (Button) findViewById(R.id.lstfac_nouveau);
        menu = (Button) findViewById(R.id.lstfac_menu);
        valide = (Button) findViewById(R.id.lstfac_valider);
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        datedeb = (TextView) findViewById(R.id.lstfac_deb);
        datefin = (TextView) findViewById(R.id.lstfac_fin);
        System.out.println("taille liste facture : "+liste_facture.size());
        lst_fact = (ListView) findViewById(R.id.liste_facture);

        datedeb.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        datefin.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        liste_facture=ou.listeFacture(parametre.getCo_no(),new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"0",ou.getVille(parametre.getDo_souche(),parametre.getCt_num()));
        ajoutListe();
        initVariable();
        System.out.println(liste_facture.size()+" taille");
    //    if(ou.data.getEnteteWithDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).size()>0)
    //   for(int i=0;i<liste_facture.size();i++)
    //       ou.commit(parametre);

        lst_fact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Facture fact = liste_facture.get(position);
                    outils.LigneFacture(fact);
                    Intent intent = new Intent(LstFactureActivity.this, FactureActivity.class);
                    itemCommun(intent,fact,position);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            ArrayList<ArticleServeur> lart = (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article");
            Facture fact = new Facture("Fact" + compteur);
            compteur++;
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("compteur", compteur);
            editor.commit();
            editor.putString("datecmpt", new SimpleDateFormat("dd/mm/yyyy").format(datecmpt));
            editor.commit();
            Parametre param =(Parametre) getIntent().getSerializableExtra("parametre");

                Intent intent = new Intent(LstFactureActivity.this, FactureActivity.class);
                itemCommun(intent,fact,liste_facture.size() - 1);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstFactureActivity.this, MenuActivity.class);
                itemCommun(intent,null,0);
            }
        });

        valide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                valideFacture();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        outils Ou = new outils();
        Ou.app = LstFactureActivity.this;
        Intent intent = new Intent(LstFactureActivity.this, LstFactureActivity.class);
        itemCommun(intent,null,0);
        super.onBackPressed();  // optional depending on your needs
    }
}
