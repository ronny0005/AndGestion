package com.example.tron.androidgestion;

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
import android.widget.TextView;

import com.example.tron.androidgestion.bddlocal.article.ArticleServeur;
import com.example.tron.androidgestion.bddlocal.client.Client;
import com.example.tron.androidgestion.bddlocal.facture.Facture;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LstFactureActivity extends AppCompatActivity {
    Button nouveau;
    ArrayList<Facture> liste_facture;
    ListView lst_fact ;
    ArrayList<String> lstr;
    ArrayList<ArticleServeur> liste_article;
    ArrayAdapter<String> arrayAdapter;

    public void ajoutListe(){
        lstr  = new ArrayList<String>();
        for(int i=0;i<liste_facture.size();i++)
            lstr.add(liste_facture.get(i).getRef()+ "Client "+liste_facture.get(i).getId_client().getIntitule());
            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lstr);
        lst_fact.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_facture);
        nouveau = (Button) findViewById(R.id.lstfac_nouveau);
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        lst_fact = (ListView) findViewById(R.id.liste_facture);
        ajoutListe();

        lst_fact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LstFactureActivity.this, FactureActivity.class);
                intent.putExtra("liste_facture",liste_facture);
                intent.putExtra("id_facture",String.valueOf(position));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                startActivity(intent);
            }
        });

        lst_fact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener
                () {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int
                    pos, long id) {
                final int i = pos;
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
                return false;
            }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                liste_facture.add(new Facture("Fact" + liste_facture.size()));
                Intent intent = new Intent(LstFactureActivity.this, FactureActivity.class);
                intent.putExtra("liste_facture",liste_facture);
                intent.putExtra("id_facture",String.valueOf(liste_facture.size()-1));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                startActivity(intent);
            }
        });
    }
}
