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
    Parametre parametre;
    ArrayList<ArticleServeur> liste_article;
    Facture facture;
    private DecimalFormat decim = new DecimalFormat("#.##");
    private DecimalFormat ttcformat = new DecimalFormat("#");


    public void active_avance(boolean active){
        if(active) {
            mtt_avance.setEnabled(true);
            mtt_avance.setText("");
        }else {
            mtt_avance.setEnabled(false);
            mtt_avance.setText(" 0");
        }
    }


    public void bloqueBox(){
        if(!liste_facture.get(id_facture).getNouveau()) {
            credit.setEnabled(false);
            comptant.setEnabled(false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valide_facture);
        parametre = (Parametre) getIntent().getSerializableExtra("parametre");
        liste_article = (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article");
        this.setTitle("Validation");
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        facture = (Facture) getIntent().getSerializableExtra("facture");
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
        valider = (Button) findViewById(R.id.valide_ajout);
        lstr = new ArrayList<String>();
        lstr.add("Espèce");
        lstr.add("Carte");
        lstr.add("Chèque");
        active_avance(false);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lstr);
        mode_paiement.setAdapter(arrayAdapter);
        System.out.println(liste_facture.get(id_facture).getStatut()+" statut");
        if(!liste_facture.get(id_facture).getNouveau()) {
            if (!liste_facture.get(id_facture).getStatut().isEmpty())
                if (liste_facture.get(id_facture).getStatut().equals("credit"))
                    credit.setChecked(true);
                else
                    comptant.setChecked(true);
        }

        liste_facture.get(id_facture).setType_paiement("espece");

        bloqueBox();
        comptant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (comptant.isChecked()) {
                    credit.setChecked(false);
                    active_avance(false);
                    facture.setStatut("comptant");
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
                    facture.setStatut("credit");
                }
            }
        });

        final ArrayList<Integer> position = facture.getPosition_article();
        System.out.println("id facture "+liste_facture.size());
        for (int i = 0; i < position.size(); i++) {
            ArticleServeur article = facture.getListe_article().get(position.get(i));
            double prix = article.getAr_prixven() * article.getQte_vendue();
            total_tva += prix * article.getTaxe1() / 100;
            total_precompte += prix * article.getTaxe2() / 100;
            total_marge += article.getQte_vendue() * article.getTaxe3();
            total_ht += prix;
            System.out.println(article.getAr_design() + " total :" + prix);
        }


        total_ttc = total_ht + total_tva + total_precompte + total_marge;
        t_marge.setText(decim.format(total_marge));
        t_tva.setText(decim.format(total_tva));
        t_ttc.setText(ttcformat.format(total_ttc));
        t_ht.setText(decim.format(total_ht));
        t_precompte.setText(decim.format(total_precompte));

        if(!facture.getNouveau()) {
            valider.setText("Continuer");
            mtt_avance.setText(String.valueOf(ttcformat.format(facture.getMtt_avance())));
        }
        mode_paiement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (position == 0)
                    facture.setType_paiement("espece");
                if (position == 1)
                    facture.setType_paiement("carte");
                if (position == 2)
                    facture.setType_paiement("cheque");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DecimalFormat decim = new DecimalFormat("#.##");
                Double d;
                if (mtt_avance.getText().toString().equals(""))
                    d = Double.MIN_VALUE;
                else d = Double.parseDouble(mtt_avance.getText().toString());

                if (Double.compare(total_ttc, d) >= 0) {
                    if (comptant.isChecked() || credit.isChecked()) {
                        if (credit.isChecked() && !mtt_avance.getText().toString().isEmpty()
                                && Double.compare(total_ttc, d) >= 0) {
                            facture.setStatut("avance");
                            facture.setMtt_avance(d);
                        }

                        if (facture.getNouveau()) {
                            String entete = ou.ajoutEnteteServeur(parametre.getCo_no(), facture.getId_client().getNum(), facture.getRef(), "1");
                            facture.setEntete(entete);
                            for (int i = 0; i < position.size(); i++) {
                                ArticleServeur article = facture.getListe_article().get(position.get(i));
                                ou.ajoutLigneServeur(entete, String.valueOf(liste_article.get(facture.getPosition_article().get(i)).getAr_ref()), 10000 * i, article.getQte_vendue(), 0);
                            }
                            liste_facture.set(id_facture, facture);
                            facture.setNouveau(false);
                            facture.setTotalTTC(Integer.parseInt(t_ttc.getText().toString()));
                            String montant = "0";
                            if(comptant.isChecked())
                                montant=ttcformat.format(total_ttc);
                            else
                                if(!mtt_avance.getText().toString().equals(""))
                                    montant=mtt_avance.getText().toString();
                            ou.reglerEntete(facture.getEntete(), facture.getRef(),montant);
                        }
                        Intent intent = new Intent(ValideActivity.this, LstFactureActivity.class);
                        intent.putExtra("liste_facture", liste_facture);
                        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                        intent.putExtra("outils", ou);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ValideActivity.this, "Choississez un mode de règlement.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ValideActivity.this, "le montant de l'avance ne peut pas être supérieur au montant TTC.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}