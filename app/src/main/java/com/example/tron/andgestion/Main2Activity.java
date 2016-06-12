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

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Parametre parametre = outils.connexion("wokam","wokam");
        DatabaseSQLite data = new DatabaseSQLite(getApplicationContext());
        data.recreate();
        ArrayList<Caisse> lcaisse =outils.listeCaisseServeur();
        data.insertParametre(parametre);
        for(int i=0;i<lcaisse.size();i++) {
            data.insertCaisse(lcaisse.get(i));
            System.out.println(data.getCaisseWithId(lcaisse.get(i).getCa_no()));
        }


        ArrayList<Depot> ldepot =outils.listeDepotServeur();
        for(int i=0;i<ldepot.size();i++) {
            data.insertDepot(ldepot.get(i));
            System.out.println(data.getDepotWithId(ldepot.get(i).getId()));
        }

        ArrayList<ArticleServeur> larticle =outils.listeArticleServeur();
        for(int i=0;i<larticle.size();i++) {
            data.insertArticle(larticle.get(i));
            System.out.println(data.getArticleWithId(larticle.get(i).getAr_ref()));
        }

        ArrayList<Client> lclient =outils.listeClientServeur("YDE");
        for(int i=0;i<lclient.size();i++) {
            data.insertClient(lclient.get(i));
            System.out.println(data.getClientWithId(lclient.get(i).getNum()));
        }


        ArrayList<Facture> facture =outils.listeFacture(19,"","","","");
        for(int i=0;i<lclient.size();i++) {
            data.insertClient(lclient.get(i));
            System.out.println(data.getClientWithId(lclient.get(i).getNum()));
        }

       System.out.println(data.getParametreWithUser("wokam","wokam"));

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
