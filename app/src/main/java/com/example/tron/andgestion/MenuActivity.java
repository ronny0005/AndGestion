package com.example.tron.andgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.util.ArrayList;


/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class MenuActivity extends AppCompatActivity {
    Button facture;
    Button parametre;
    Button etat;
    Button recouvrement;
    outils ou;


    private void passeVariable(Intent intent){
        intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
        intent.putExtra("outils", ou);
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app=MenuActivity.this;
        facture = (Button) findViewById(R.id.menu_facturation);
        parametre = (Button) findViewById(R.id.menu_parametre);
        etat = (Button) findViewById(R.id.menu_etats);
        recouvrement = (Button) findViewById(R.id.menu_recouvrement);


        etat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LstStockActivity.class);
                passeVariable(intent);
            }
        });


        facture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LstFactureActivity.class);
                passeVariable(intent);
            }
        });
        recouvrement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, RecouvrementActivity.class);
                passeVariable(intent);
            }
        });
        parametre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ParametresActivity.class);
                passeVariable(intent);
            }
        });
    }


}
