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

public class LstStockActivity extends AppCompatActivity {
    Button stock;
    Button vendeur;
    Button maps;
    Button manquant;
    Button bmq;
    Button tmap;
    outils ou;

    private void passeVariable(Intent intent){
        intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
        intent.putExtra("outils", ou);
        intent.putExtra("liste_recouvrement", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_recouvrement"));
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_stock);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app=LstStockActivity.this;
        stock = (Button) findViewById(R.id.lststk_stock);
        vendeur = (Button) findViewById(R.id.lststk_vendeur);
        maps = (Button) findViewById(R.id.lststk_map);
        manquant = (Button) findViewById(R.id.lststk_manquant);
        bmq = (Button) findViewById(R.id.lststk_bmq);
        tmap = (Button) findViewById(R.id.lststk_tabeau);

        bmq.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstStockActivity.this, BmqActivity.class);
                passeVariable(intent);
            }
        });

        tmap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstStockActivity.this, TableauMapActivity.class);
                passeVariable(intent);
            }
        });

        stock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstStockActivity.this, StockActivity.class);
                passeVariable(intent);
            }
        });

        vendeur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstStockActivity.this, EquationActivity.class);
                passeVariable(intent);
            }
        });

        manquant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstStockActivity.this, ManquantActivity.class);
                passeVariable(intent);
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstStockActivity.this, MapsActivity.class);
                intent.putExtra("position",-1);
                passeVariable(intent);
            }
        });

    }
}
