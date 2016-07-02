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
import com.example.tron.andgestion.modele.Entete;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.modele.Ligne;
import com.example.tron.andgestion.modele.Parametre;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    outils ou;
    DatabaseSQLite data;

    public void commit(Facture facture/*,Parametre parametre*/){
        String entete="";
        // ArrayList<Entete> lentete=
        ArrayList<Entete> lentete = data.getEnteteWithDate(facture.getDO_Date());
        Entete b_entete = lentete.get(0);
        String old_entete=b_entete.getEntete();
        if(!facture.getCommit()) {
            try {
                entete = ou.ajoutEnteteServeur(19/*parametre.getCo_no()*/, facture.getId_client().getNum(), facture.getRef(), "1", (float) facture.getLatitude(), (float) facture.getLongitude());
                b_entete.setCommit("oui");
                b_entete.setEntete(entete);
                data.updateEntete(b_entete.getEntete(), b_entete);
            } catch (IOException e) {
            }
            ArrayList<Ligne> lligne = data.getLigneWithId(old_entete);
            for (int i = 0; i < facture.getListe_article().size(); i++) {
                ArticleServeur article = facture.getListe_article().get(i);
                try {
                    ou.ajoutLigneServeur(entete, String.valueOf(facture.getListe_article().get(i).getAr_ref()), 10000 * (i + 1), article.getQte_vendue(), 0, facture.getVehicule(), facture.getCr());
                    Ligne l = lligne.get(i);
                    l.setEntete(entete);
                    data.updateLigne(String.valueOf(l.getId()), l);
                } catch (IOException e) {
                }
            }
            ou.reglerEntete(facture.getEntete(), facture.getRef(), String.valueOf(facture.getMtt_avance()));
        }
    }
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
         Facture facture = new Facture();
        facture.setid(0);facture.setRef("Fact170");facture.setStatut("credit");facture.setEntete("11WO00298");facture.setMtt_avance(0.0);facture.setLatitude(0.0);
        facture.setLongitude(0.0);facture.setTotalTTC(420); facture.setDO_Date("2016-06-23"); facture.setCommit(false);
facture.setEntete("AND0001");
        ArticleServeur article = new ArticleServeur("10312","JEM VELOUTE 125G  FRAISE",Float.MIN_VALUE ,Float.MIN_VALUE,(float)171.78, 0,(float)171.78);
        ArrayList<ArticleServeur> larticle = new ArrayList<ArticleServeur>();
        article.setTaxe1(19.25);
        article.setTaxe2(3.0);
        article.setTaxe3(0.0);
        article.setQte_vendue(2);
        larticle.add(article); facture.setListe_article(larticle);
        Client client = new Client("POISSONNERIE FAMILIALE","41DPOISSONNERIEFA","4111100", 1, 8);
        facture.setId_client(client);
        Entete b_entete = new Entete(facture.getRef(), facture.getEntete(),new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                facture.getId_client().getNum(),"false",facture.getStatut(),facture.getType_paiement(),
                "171.78", String.valueOf(facture.getLatitude()),String.valueOf(facture.getLongitude()),"171.78");
        b_entete.setCommit("non");
        b_entete.setType_paiement("espece");
        data.insertEntete(b_entete);
        Ligne ligne = new Ligne(b_entete.getEntete(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), article.getAr_ref(), article.getAr_design(),String.valueOf(article.getQte_vendue()),String.valueOf(article.getAr_prixven()),String.valueOf(article.getTaxe1())
                , String.valueOf(article.getTaxe2()),String.valueOf(article.getTaxe3()),"", 1+"0000",String.valueOf(article.getAr_prixven()));
        data.insertLigne(ligne);
        System.out.println(b_entete);
        System.out.println(ligne);
        System.out.println(facture);
        ou = new outils();
        ArrayList<Entete> lentete = data.getEnteteWithDate(facture.getDO_Date());
        String entete="";
        // ArrayList<Entete> lentete=
        b_entete = lentete.get(0);
        String old_entete=b_entete.getEntete();
        if(!facture.getCommit()) {
            try {
                entete = ou.ajoutEnteteServeur(19/*parametre.getCo_no()*/, facture.getId_client().getNum(), facture.getRef(), "1", (float) facture.getLatitude(), (float) facture.getLongitude());
                b_entete.setCommit("oui");
                b_entete.setEntete(entete);
                data.updateEntete(b_entete.getEntete(), b_entete);
            } catch (IOException e) {
            }
            ArrayList<Ligne> lligne = data.getLigneWithId(old_entete);
            for (int i = 0; i < facture.getListe_article().size(); i++) {
                article = facture.getListe_article().get(i);
                try {
                    ou.ajoutLigneServeur(entete, String.valueOf(facture.getListe_article().get(i).getAr_ref()), 10000 * (i + 1), article.getQte_vendue(), 0, facture.getVehicule(), facture.getCr());
                    Ligne l = lligne.get(i);
                    l.setEntete(entete);
                    data.updateLigne(String.valueOf(l.getId()), l);
                } catch (IOException e) {
                }
            }
            ou.reglerEntete(facture.getEntete(), facture.getRef(), String.valueOf(facture.getMtt_avance()));
        }
        //commit(facture);

        // ArrayList<Facture> liste = outils.listeFacture(19,new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
        //        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"0","19");
       // System.out.println(liste.size());


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
