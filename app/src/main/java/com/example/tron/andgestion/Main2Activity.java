package com.example.tron.andgestion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Caisse;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.DatabaseSQLite;
import com.example.tron.andgestion.modele.Depot;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.modele.Parametre;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  Parametre parametre = outils.connexion("wokam","wokam");
        DatabaseSQLite data = new DatabaseSQLite(getApplicationContext());
        data.recreate();
        ArrayList<Caisse> lcaisse =outils.listeCaisseServeur();
     //   data.insertParametre(parametre);
        ArrayList<Facture> liste = outils.listeFacture(19,new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"0","19");
        System.out.println(liste.size());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
