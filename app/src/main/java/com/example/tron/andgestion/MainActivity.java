package com.example.tron.andgestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.tron.andgestion.bddlocal.parametre.Parametre;
import com.example.tron.andgestion.modele.Vehicule;

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
        facture = (Button) findViewById(R.id.menu_facturation);
        login =(TextView) findViewById(R.id.connexion_login);
        mdp =(TextView) findViewById(R.id.connexion_mdp);
        login.requestFocus();
        connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            ArrayList<Depot> list = ou.listeDepotServeur();
            // ! à enlever
            Parametre parametre=null;
                if(!login.getText().toString().isEmpty() && !mdp.getText().toString().isEmpty()) {
                        parametre = ou.connexion(login.getText().toString(), mdp.getText().toString());
                }else {
                    Toast.makeText(MainActivity.this, "Veuillez saisir le login et mot de passe",Toast.LENGTH_SHORT).show();
                }
            if(parametre != null){
                System.out.println(parametre);
                if(parametre.getDo_souche()==6) {
                    liste_client = ou.listeClientServeur(parametre.getCt_num());
                }else {
                    liste_client = ou.listeClientServeur(ou.getVille(parametre.getDo_souche()));
                }
                liste_article = ou.listeArticleDispo(String.valueOf(parametre.getDe_no()));
                liste_vehicule = ou.listeVehiculeServeur();
                liste_cr = ou.listePlanCR();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                DateFormat format = new SimpleDateFormat("yyyy-dd-mm", Locale.FRENCH);
                //ou.passeVariable(intent, MainActivity.this,ou.listeFacture(parametre.getCo_no(),
                //        new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"0",ou.getVille(parametre.getDo_souche()) ),parametre,ou,liste_recouvrement,liste_client,liste_article);
                ou.passeVariableCarburant(intent, MainActivity.this,ou.listeFacture(parametre.getCo_no(),
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"0",
                        ou.getVille(parametre.getDo_souche())),parametre,ou,liste_recouvrement,liste_client,liste_article,liste_vehicule,liste_cr);

            }
            }
        });
    }

}
