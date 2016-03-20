package com.example.tron.androidgestion;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.tron.androidgestion.bddlocal.client.Client;
import com.example.tron.androidgestion.bddlocal.depot.Depot;
import com.example.tron.androidgestion.bddlocal.facture.Facture;
import com.example.tron.androidgestion.bddlocal.fonction.outils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button connexion;
    Button facture;
    TextView login;
    TextView mdp;
    ArrayList<Facture> liste_facture = new ArrayList<Facture>();
    ArrayList<Client> liste_client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connexion = (Button) findViewById(R.id.connexion_button);
        facture = (Button) findViewById(R.id.menu_facturation);
        login =(TextView) findViewById(R.id.connexion_login);
        mdp =(TextView) findViewById(R.id.connexion_mdp);

        connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<Depot> list = outils.listeDepotServeur();
                System.out.println("main figure"+login.getText().toString()+" test "+mdp.getText().toString());
                // ! à enlever
                if(!outils.connexion(login.getText().toString(), mdp.getText().toString())){
                    liste_client = outils.listeClientServeur("YDE");
                    System.out.println("size "+liste_client.size());
                    System.out.println("login passé");
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("liste_facture", liste_facture);
                    intent.putExtra("liste_client", liste_client);
                    startActivity(intent);
                }
            }
        });

    }
}
