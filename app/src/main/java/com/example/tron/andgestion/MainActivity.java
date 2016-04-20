package com.example.tron.andgestion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.depot.Depot;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.ParseException;
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
    ArrayList<Client> liste_client;
    ArrayList<ArticleServeur> liste_article;
    outils ou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Connexion");

        ou = (outils) getIntent().getSerializableExtra("outils");

        ou.app=MainActivity.this;
        connexion = (Button) findViewById(R.id.connexion_button);
        facture = (Button) findViewById(R.id.menu_facturation);
        login =(TextView) findViewById(R.id.connexion_login);
        mdp =(TextView) findViewById(R.id.connexion_mdp);

        connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            ArrayList<Depot> list = ou.listeDepotServeur();
            // ! à enlever
            Parametre parametre=null;
                if(!login.getText().toString().isEmpty() && !mdp.getText().toString().isEmpty()) {
                    try {
                        parametre = ou.connexion(login.getText().toString(), mdp.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Veuillez saisir le login et mot de passe",Toast.LENGTH_SHORT).show();
                }
            if(parametre != null){
                liste_client = ou.listeClientServeur("YDE");
                liste_article = ou.listeArticleDispo(String.valueOf(parametre.getDe_no()));
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                System.out.println("nombre article"+liste_article.size());
                DateFormat format = new SimpleDateFormat("yyyy-dd-mm", Locale.FRENCH);
                intent.putExtra("liste_facture",ou.listeFacture(parametre.getCo_no(),
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(new Date()) ));
                intent.putExtra("parametre", parametre);
                intent.putExtra("outils", ou);
                intent.putExtra("liste_client", liste_client);
                intent.putExtra("liste_article", liste_article);
                startActivity(intent);
            }
            }
        });
    }

}
