package com.example.tron.androidgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.tron.androidgestion.bddlocal.article.ArticleServeur;
import com.example.tron.androidgestion.bddlocal.client.Client;
import com.example.tron.androidgestion.bddlocal.facture.Facture;

import java.util.ArrayList;

/**
 * Created by T.Ron on 19/03/2016.
 */
public class ValideActivity extends AppCompatActivity {
    ArrayList<Facture> liste_facture;
    double total_tva = 0;
    double total_precompte = 0;
    double total_marge = 0;
    double total_ttc = 0;
    double total_ht = 0;
    TextView t_marge;
    TextView t_ht;
    TextView t_tva;
    TextView t_ttc;
    TextView t_precompte;
    Spinner mode_paiement;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    CheckBox comptant;
    CheckBox credit;
    Button annuler;
    Button valider;
    int id_facture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valide_facture);
        t_marge = (TextView) findViewById(R.id.valide_marge);
        t_ht = (TextView) findViewById(R.id.valide_ht);
        t_tva = (TextView) findViewById(R.id.valide_tva);
        t_ttc = (TextView) findViewById(R.id.valide_ttc);
        t_precompte = (TextView) findViewById(R.id.vailde_precompte);
        mode_paiement = (Spinner) findViewById(R.id.valide_paiement);
        comptant = (CheckBox) findViewById(R.id.valide_comptant);
        credit = (CheckBox) findViewById(R.id.valide_credit);
        annuler = (Button) findViewById(R.id.valide_annuler);
        valider = (Button) findViewById(R.id.valide_ajout);
        lstr = new ArrayList<String>();
        lstr.add("Espèce");
        lstr.add("Carte");
        lstr.add("Chèque");
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                lstr);
        mode_paiement.setAdapter(arrayAdapter);

        comptant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (comptant.isChecked())
                    credit.setChecked(false);
            }
        });

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (credit.isChecked())
                    comptant.setChecked(false);
            }
        });

        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");

        id_facture= Integer.parseInt(getIntent().getStringExtra("id_facture"));
        ArrayList<Integer> position = liste_facture.get(id_facture).getPosition_article();
        System.out.println("id facture "+liste_facture.size());
        for (int i = 0; i < position.size(); i++) {
            ArticleServeur article = liste_facture.get(id_facture).getListe_article().get(position.get(i));
            double prix = article.getAr_prixach() * article.getQte_vendue();
            total_tva += prix * article.getTaxe1() / 100;
            total_precompte += prix * article.getTaxe2() / 100;
            total_marge += prix * article.getTaxe3() / 100;
            total_ht += prix;
            System.out.println(article.getAr_design() + " total :" + prix);
        }

        total_ttc = total_ht + total_tva + total_precompte + total_marge;
        t_marge.setText(String.valueOf(total_marge));
        t_tva.setText(String.valueOf(total_tva));
        t_ttc.setText(String.valueOf(total_ttc));
        t_marge.setText(String.valueOf(total_marge));
        t_ht.setText(String.valueOf(total_ht));
        t_ht.setText(String.valueOf(total_ht));
        t_precompte.setText(String.valueOf(total_precompte));

        annuler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ValideActivity.this, FactureActivity.class);
                intent.putExtra("liste_facture",liste_facture);
                intent.putExtra("id_facture",String.valueOf(id_facture));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                startActivity(intent);
            }
        });


        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ValideActivity.this, LstFactureActivity.class);
                intent.putExtra("liste_facture",liste_facture);
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                startActivity(intent);
            }
        });
    }
}