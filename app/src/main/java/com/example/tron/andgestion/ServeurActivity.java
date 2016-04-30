package com.example.tron.andgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class ServeurActivity extends AppCompatActivity {
    TextView adresse;
    Button valide;
    outils ou = new outils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_serveur);
        this.setTitle("Connexion serveur");
        ou.app=ServeurActivity.this;
        adresse = (TextView) findViewById(R.id.serveur_adresse);
        valide = (Button) findViewById(R.id.serveur_valide);

        valide.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ou.lien="http://"+adresse.getText().toString()+":8083/api/";
                System.out.println(ou.lien);
                Intent intent = new Intent(ServeurActivity.this, MainActivity.class);
                intent.putExtra("outils", ou);
                startActivity(intent);
            }
        });
    }
}