package com.example.tron.androidgestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tron.androidgestion.bddlocal.article.Article;
import com.example.tron.androidgestion.bddlocal.article.ArticleBDD;
import com.example.tron.androidgestion.bddlocal.article.ArticleServeur;
import com.example.tron.androidgestion.bddlocal.client.Client;
import com.example.tron.androidgestion.bddlocal.facture.Facture;
import com.example.tron.androidgestion.bddlocal.fonction.outils;

import java.util.ArrayList;

/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class FactureActivity extends AppCompatActivity {
    ArticleBDD donneeBDD;
    ListView lv;
    Button ajouter;
    Button valider;
    Button ouvrir;
    Button supprimer;
    AutoCompleteTextView client;
    AutoCompleteTextView designation;
    ArrayList<Article> list;
    TextView qte;
    ArticleBDD articleBDD;
    ArrayList<Integer> position_article = new ArrayList<Integer>();
    ArrayList<ArticleServeur> liste_article;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    CheckBox comptant;
    CheckBox credit;
    ArrayList<Facture> liste_facture;
    ArrayList<String> lart;
    TextView caisse;
    Button annuler;
    Integer id_facture;
    ArrayList<String> lclient=new ArrayList<String>();
    public void ajoutListe(){
        lstr = new ArrayList<String>();
        for(int i=0;i<position_article.size();i++){
            double px_achat = (liste_article.get(position_article.get(i)).getAr_prixach()*liste_article.get(position_article.get(i)).getQte_vendue());
            lstr.add(liste_article.get(position_article.get(i)).getAr_design() + " Qte " + ((int) liste_article.get(position_article.get(i)).getQte_vendue()) + " Total " + px_achat);
        }
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lstr );
        lv.setAdapter(arrayAdapter);
        if (position_article.size()>0) { // disable editing password
            client.setFocusable(false);
            client.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            client.setClickable(false); // user navigates with wheel and selects widget
        } else { // enable editing of password
            client.setFocusable(true);
            client.setFocusableInTouchMode(true);
            client.setClickable(true);
        }
    }

    public void clear(){
        qte.setText("");
        designation.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facture);
        annuler = (Button) findViewById(R.id.facture_annuler);
        caisse = (TextView) findViewById(R.id.facture_caisse);
        caisse.setFocusable(false);
        caisse.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        caisse.setClickable(false);

        id_facture= Integer.parseInt(getIntent().getStringExtra("id_facture"));
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");

        position_article = liste_facture.get(id_facture).getPosition_article();
        liste_article = liste_facture.get(id_facture).getListe_article();
        lv = (ListView) findViewById(R.id.facture_liste);
        ajouter = (Button) findViewById(R.id.facture_ajouter);
        final ArrayList<Client> lst_client = (ArrayList<Client>) getIntent().getSerializableExtra("liste_client");
        for(int i=0;i<lst_client.size();i++)
            lclient.add(lst_client.get(i).getIntitule());
        client = (AutoCompleteTextView)findViewById(R.id.facture_client);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lclient);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        client.setAdapter(adapter);

        lart = new ArrayList<String>();
        for(int i=0;i<liste_article.size();i++)
            lart.add(liste_article.get(i).getAr_design());
        designation = (AutoCompleteTextView) findViewById(R.id.facture_designation);
        ArrayAdapter<String> adapterComplete = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lart);
        designation.setAdapter(adapterComplete);

        qte = (TextView) findViewById(R.id.facture_qte);
        ajouter = (Button) findViewById(R.id.facture_ajouter);
        valider = (Button) findViewById(R.id.facture_valider);
        credit = (CheckBox) findViewById(R.id.facture_credit);
        comptant = (CheckBox) findViewById(R.id.facture_comptant);

        lv.setLongClickable(true);
        articleBDD = new ArticleBDD(this);
        articleBDD.open();
        ajoutListe();
        comptant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (comptant.isChecked()) {
                    credit.setChecked(false);
                } else {
                    credit.setChecked(true);
                }
            }
        });


        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position_article = new ArrayList<Integer>();
                ajoutListe();
                clear();
            }
        });

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (credit.isChecked()) {
                    comptant.setChecked(false);
                } else {
                    comptant.setChecked(true);
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleServeur art = liste_article.get(position_article.get(position));
                designation.setText(art.getAr_design());
                qte.setText(String.valueOf(art.getQte_vendue()));
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener
                () {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int
                    pos, long id) {
                final int i = pos;
                new AlertDialog.Builder(FactureActivity.this)
                        .setTitle("Suppression")
                        .setMessage("Voulez vous supprimer " + liste_article.get(position_article.get(pos)).getAr_design() + " ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                position_article.remove(i);
                                ajoutListe();
                                clear();
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

        ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!qte.getText().toString().isEmpty() && !designation.getText().toString().isEmpty() && !client.getText().toString().isEmpty()) {
                    int id_article = 0;
                    for (int i = 0; i < liste_article.size(); i++)
                        if (liste_article.get(i).getAr_design().equals(designation.getText().toString()))
                            id_article = i;
                    liste_article.get(id_article).setQte_vendue(Integer.parseInt(qte.getText().toString()));
                    position_article.add(id_article);
                    int id_client = 0;
                    if (position_article.size() == 1) {
                        for (int i = 0; i < lst_client.size(); i++)
                            if (lst_client.get(i).getIntitule().equals(client.getText().toString()))
                                id_client = i;
                        liste_facture.get(id_facture).setId_client(lst_client.get(id_client));
                    }
                    ajoutListe();
                    clear();
                }
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FactureActivity.this, ValideActivity.class);
                liste_facture.get(id_facture).setPosition_article(position_article);
                intent.putExtra("liste_facture", liste_facture);
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                intent.putExtra("id_facture",String.valueOf(id_facture));
                startActivity(intent);
            }
        });

    }
}
