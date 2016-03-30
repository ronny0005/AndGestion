package com.example.tron.andgestion;

import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LstFactureActivity extends AppCompatActivity {
    Button nouveau;
    ArrayList<Facture> liste_facture;
    ListView lst_fact ;
    ArrayList<String> lstr;
    ArrayList<ArticleServeur> liste_article;
    ArrayAdapter<String> arrayAdapter;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    outils ou;

    public void ajoutListe(){
        lstr  = new ArrayList<String>();
        Map<String, String> datum = new HashMap<String, String>(2);
        for(int i=0;i<liste_facture.size();i++) {
            datum.put("First Line", liste_facture.get(i).getRef());
            datum.put("Second Line","Client : "+ liste_facture.get(i).getId_client().getIntitule() );
            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data,android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },new int[] {android.R.id.text1, android.R.id.text2 });
        lst_fact.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_facture);
        this.setTitle("Liste de facture");
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app=LstFactureActivity.this;
        nouveau = (Button) findViewById(R.id.lstfac_nouveau);
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        lst_fact = (ListView) findViewById(R.id.liste_facture);

        ajoutListe();

        lst_fact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Facture fact = liste_facture.get(position);
                if (fact.getStatut().equals("credit")) {
                    Intent intent = new Intent(LstFactureActivity.this, FactureActivity.class);
                    intent.putExtra("liste_facture", liste_facture);
                    intent.putExtra("outils", ou);
                    intent.putExtra("id_facture", String.valueOf(position));
                    intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                    intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                    startActivity(intent);
                }
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

            nouveau.setOnClickListener(new View.OnClickListener(){
               public void onClick(View v) {
                   outils Ou = new outils();
                   Ou.app=LstFactureActivity.this;
                   ArrayList<ArticleServeur> lart=(ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article");
                   Facture fact  = new Facture("Fact" + liste_facture.size(),lart);
                   fact.setListe_article(Ou.listeArticleServeur());
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
        }
    }
