package com.example.tron.andgestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.lang.reflect.Array;
import java.text.DateFormat;
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
    ArrayList<Facture> liste_facture;
    ListView lst_fact;
    ArrayList<String> lstr;
    ArrayList<ArticleServeur> liste_article;
    ArrayAdapter<String> arrayAdapter;
    List<Map<String, String>> data;
    outils ou;
    Map<String, String> datum;
    int compteur = 0;
    Date datecmpt = new Date();
    private static final String PREFS_NAME = "compteur";

    public void initVariable() {
        // Get from the SharedPreferences
        try {
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            DateFormat format = new SimpleDateFormat("yyy-MM-dd", Locale.FRENCH);
            String form = settings.getString("dateCmpt", null);
            if (form == null)
                form = format.format(new Date());
            datecmpt = format.parse(form);
            System.out.println(datecmpt.getDay() + " lopp " + (new Date()).getDay() + " lal a");
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
        for (int i = 0; i < liste_facture.size(); i++) {
            data.add(createRow(liste_facture.get(i).getRef() + " - " + liste_facture.get(i).getEntete()+" - " + liste_facture.get(i).getStatut() ,
                    "Client : " + liste_facture.get(i).getId_client().getIntitule()+ "\nTotal TTC : " + liste_facture.get(i).getTotalTTC() ));
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
        this.setTitle("Liste de facture");
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = LstFactureActivity.this;
        nouveau = (Button) findViewById(R.id.lstfac_nouveau);
        menu = (Button) findViewById(R.id.lstfac_menu);
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        lst_fact = (ListView) findViewById(R.id.liste_facture);
        ajoutListe();
        initVariable();

        lst_fact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Facture fact = liste_facture.get(position);
                Intent intent = new Intent(LstFactureActivity.this, FactureActivity.class);
                intent.putExtra("liste_facture", liste_facture);
                intent.putExtra("outils", ou);
                intent.putExtra("id_facture", String.valueOf(position));
                intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                startActivity(intent);
            }
        });

        lst_fact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                final int i = pos;
                Facture fact = liste_facture.get(i);
                if (fact.getStatut().equals("credit")) {
                    new AlertDialog.Builder(LstFactureActivity.this)
                            .setTitle("Suppression")
                            .setMessage("Voulez vous supprimer " + liste_facture.get(pos).getRef() + " ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    liste_facture.remove(i);
                                    ajoutListe();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return false;
            }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                outils Ou = new outils();
                Ou.app = LstFactureActivity.this;
                ArrayList<ArticleServeur> lart = (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article");
                Facture fact = new Facture("Fact" + compteur, lart);
                compteur++;
                SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("compteur", compteur);
                editor.commit();
                editor.putString("datecmpt", new SimpleDateFormat("dd/mm/yyyy").format(datecmpt));
                editor.commit();
                Parametre param =(Parametre) getIntent().getSerializableExtra("parametre");
                //fact.setListe_article(lart);
                liste_facture.add(fact);
                Intent intent = new Intent(LstFactureActivity.this, FactureActivity.class);
                intent.putExtra("liste_facture", liste_facture);
                intent.putExtra("outils", ou);
                intent.putExtra("id_facture", String.valueOf(liste_facture.size() - 1));
                intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                outils Ou = new outils();
                Ou.app = LstFactureActivity.this;
                Intent intent = new Intent(LstFactureActivity.this, MenuActivity.class);
                intent.putExtra("liste_facture", liste_facture);
                intent.putExtra("outils", ou);
                intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        outils Ou = new outils();
        Ou.app = LstFactureActivity.this;
        Intent intent = new Intent(LstFactureActivity.this, LstFactureActivity.class);
        intent.putExtra("liste_facture", liste_facture);
        intent.putExtra("outils", ou);
        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        startActivity(intent);
        super.onBackPressed();  // optional depending on your needs
    }
}
