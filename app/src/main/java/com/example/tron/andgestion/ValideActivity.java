package com.example.tron.andgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.text.DecimalFormat;
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
    TextView mtt_avance;
    Spinner mode_paiement;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    CheckBox comptant;
    CheckBox credit;
    Button annuler;
    Button valider;
    int id_facture;
    outils ou = new outils();

    public void active_avance(boolean active){
        mtt_avance.setText("");
        if(active){
            mtt_avance.setFocusable(true);
            mtt_avance.setFocusableInTouchMode(true);
            mtt_avance.setClickable(true);
        } else { // enable editing of password
            mtt_avance.setFocusable(false);
            mtt_avance.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            mtt_avance.setClickable(false); // user navigates with wheel and selects widget
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valide_facture);
        this.setTitle("Validation");
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        id_facture= Integer.parseInt(getIntent().getStringExtra("id_facture"));
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = ValideActivity.this;

        t_marge = (TextView) findViewById(R.id.valide_marge);
        t_ht = (TextView) findViewById(R.id.valide_ht);
        t_tva = (TextView) findViewById(R.id.valide_tva);
        t_ttc = (TextView) findViewById(R.id.valide_ttc);
        t_precompte = (TextView) findViewById(R.id.vailde_precompte);
        mtt_avance = (TextView) findViewById(R.id.valide_avance);
        mode_paiement = (Spinner) findViewById(R.id.valide_paiement);
        comptant = (CheckBox) findViewById(R.id.valide_comptant);
        credit = (CheckBox) findViewById(R.id.valide_credit);
        annuler = (Button) findViewById(R.id.valide_annuler);
        valider = (Button) findViewById(R.id.valide_ajout);
        lstr = new ArrayList<String>();
        lstr.add("Espèce");
        lstr.add("Carte");
        lstr.add("Chèque");
        active_avance(false);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                lstr);
        mode_paiement.setAdapter(arrayAdapter);
        if(!liste_facture.get(id_facture).getNouveau()) {
            if (!liste_facture.get(id_facture).getStatut().isEmpty())
                if (liste_facture.get(id_facture).getStatut().equals("credit"))
                    credit.setChecked(true);
                else
                    credit.setChecked(false);
        }

        liste_facture.get(id_facture).setType_paiement("espece");


            comptant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (comptant.isChecked()) {
                    credit.setChecked(false);
                    active_avance(false);
                    liste_facture.get(id_facture).setStatut("comptant");
                }
            }
        });

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (credit.isChecked()) {
                    comptant.setChecked(false);
                    active_avance(true);
                    liste_facture.get(id_facture).setStatut("credit");
                }
            }
        });

        ArrayList<Integer> position = liste_facture.get(id_facture).getPosition_article();
        System.out.println("id facture "+liste_facture.size());
        for (int i = 0; i < position.size(); i++) {
            ArticleServeur article = liste_facture.get(id_facture).getListe_article().get(position.get(i));
            double prix = article.getAr_prixven() * article.getQte_vendue();
            total_tva += prix * article.getTaxe1() / 100;
            total_precompte += prix * article.getTaxe2() / 100;
            total_marge += prix * article.getTaxe3() / 100;
            total_ht += prix;
            System.out.println(article.getAr_design() + " total :" + prix);
        }

        DecimalFormat decim = new DecimalFormat("#.##");

        total_ttc = total_ht + total_tva + total_precompte + total_marge;
        t_marge.setText(decim.format(total_marge));
        t_tva.setText(decim.format(total_tva));
        t_ttc.setText(decim.format(total_ttc));
        t_ht.setText(decim.format(total_ht));
        t_precompte.setText(decim.format(total_precompte));

        annuler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ValideActivity.this, FactureActivity.class);
                intent.putExtra("liste_facture", liste_facture);
                intent.putExtra("id_facture", String.valueOf(id_facture));
                intent.putExtra("outils", ou);
                intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                startActivity(intent);
            }
        });

        mode_paiement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(position==0)
                    liste_facture.get(id_facture).setType_paiement("espece");
                if(position==1)
                    liste_facture.get(id_facture).setType_paiement("carte");
                if(position==2)
                    liste_facture.get(id_facture).setType_paiement("cheque");
                System.out.println(liste_facture.get(id_facture).getType_paiement());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        valider.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               if(comptant.isChecked() || credit.isChecked()) {
                   if(credit.isChecked() && !mtt_avance.getText().toString().isEmpty()) {
                       liste_facture.get(id_facture).setStatut("avance");
                       liste_facture.get(id_facture).setMtt_avance(Double.parseDouble(mtt_avance.getText().toString()));
                   }
                   if(comptant.isChecked()){
                       ou.reglerEntete(liste_facture.get(id_facture).getEntete(),liste_facture.get(id_facture).getRef());
                   }
                   liste_facture.get(id_facture).setNouveau(false);
                   Intent intent = new Intent(ValideActivity.this, LstFactureActivity.class);
                   intent.putExtra("liste_facture", liste_facture);
                   intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                   intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                   intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                   intent.putExtra("outils", ou);
                   startActivity(intent);
               }   else {
                   Toast.makeText(ValideActivity.this, "Choississez un mode de règlement.",
                           Toast.LENGTH_SHORT).show();
               }
           }
       });
    }
}