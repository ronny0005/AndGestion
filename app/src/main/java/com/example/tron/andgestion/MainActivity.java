package com.example.tron.andgestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.CompteA;
import com.example.tron.andgestion.modele.Depot;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Parametre;
import com.example.tron.andgestion.modele.PrixClient;
import com.example.tron.andgestion.modele.QteStock;
import com.example.tron.andgestion.modele.Vehicule;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "compteur";
    Button connexion;
    Button facture;
    TextView login;
    TextView mdp;
    ArrayList<Facture> liste_facture = new ArrayList<Facture>();
    ArrayList<Facture> liste_recouvrement = new ArrayList<Facture>();
    ArrayList<Client> liste_client;
    ArrayList<Vehicule> liste_vehicule;
    ArrayList<ArticleServeur> liste_article;
    ArrayList<CompteA> liste_cr;
    outils ou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ou = (outils) getIntent().getSerializableExtra("outils");

        ou.app=MainActivity.this;
        connexion = (Button) findViewById(R.id.connexion_button);
        login =(TextView) findViewById(R.id.connexion_login);
        mdp =(TextView) findViewById(R.id.connexion_mdp);
        login.requestFocus();
        connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            ArrayList<Depot> list = ou.listeDepotServeur();
            Parametre parametre=null;
                if(!login.getText().toString().isEmpty() && !mdp.getText().toString().isEmpty()) {
//                        ou.demarreBase(getApplicationContext());

                    parametre = ou.connexion(login.getText().toString(), mdp.getText().toString());
          //
                    System.out.println(parametre);
                    // ou.data.insertParametre(parametre);
                }else {
                    Toast.makeText(MainActivity.this, "Veuillez saisir le login et mot de passe",Toast.LENGTH_SHORT).show();
                }
            if(parametre != null){
                liste_article = ou.listeArticleDispo(String.valueOf(parametre.getDe_no()));
                liste_client = ou.listeClientServeur(ou.getVille(parametre.getDo_souche(),parametre.getCt_num()));

                for(int i=0;i<liste_article.size();i++) {
         //         ou.data.insertArticle(liste_article.get(i));
                    QteStock qte = new QteStock();
                    qte.setAS_QteSto(ou.articleDisponibleServeur(liste_article.get(i).getAr_ref(),parametre.getDe_no()));
                    qte.setAR_Ref(liste_article.get(i).getAr_ref());
                    qte.setDE_No(parametre.getDe_no());
                    qte.setAS_MontSto("");
                    liste_article.get(i).setQteStock(qte);
         //           ou.data.insertStock(qte);
                }
                liste_vehicule = ou.listeVehiculeServeur();
                liste_cr = ou.listePlanCR();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                DateFormat format = new SimpleDateFormat("yyyy-dd-mm", Locale.FRENCH);
                ou.passeVariable(intent, MainActivity.this,ou.listeFacture(parametre.getCo_no(),
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"0",ou.getVille(parametre.getDo_souche(),parametre.getCt_num()),liste_client ),parametre,ou,liste_recouvrement,liste_client,liste_article,"");

            }
            }
        });
    }



}
