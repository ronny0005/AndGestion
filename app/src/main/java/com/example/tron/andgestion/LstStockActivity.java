package com.example.tron.andgestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LstStockActivity extends AppCompatActivity {
    Button stock;
    Button vendeur;
    outils ou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_stock);
        this.setTitle("Liste des etats");
        stock = (Button) findViewById(R.id.lststk_stock);
        vendeur = (Button) findViewById(R.id.lststk_vendeur);

        stock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstStockActivity.this, StockActivity.class);
                intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
                intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                intent.putExtra("outils",(outils) getIntent().getSerializableExtra("outils"));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                startActivity(intent);
            }
        });

        vendeur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LstStockActivity.this, EquationActivity.class);
                intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
                intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                intent.putExtra("outils",(outils) getIntent().getSerializableExtra("outils"));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                startActivity(intent);
            }
        });

    }
}
