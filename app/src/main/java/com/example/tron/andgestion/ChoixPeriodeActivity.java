package com.example.tron.andgestion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.modele.Parametre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChoixPeriodeActivity extends AppCompatActivity {
    Button valider;
    EditText datedeb;
    EditText datefin;
    outils ou;
    Parametre parametre;
    ArrayList<Facture> liste_facture;

    private void passeVariable(Intent intent){
        intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
        System.out.println("liste article"+ ((ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture")).size());
        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
        intent.putExtra("outils", (outils) getIntent().getSerializableExtra("outils"));
        intent.putExtra("liste_recouvrement", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_recouvrement"));
        intent.putExtra("position", 0);
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        intent.putExtra("ncontribuable", (String)getIntent().getSerializableExtra("ncontribuable"));
        intent.putExtra("liste_fact", liste_facture);
        intent.putExtra("device_address", getIntent().getSerializableExtra("device_address"));
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_periode);

        valider = (Button) findViewById(R.id.cp_valider);
        datedeb = (EditText) findViewById(R.id.date_debut);
        datefin = (EditText) findViewById(R.id.date_fin);
        datedeb.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        datefin.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        parametre = (Parametre) getIntent().getSerializableExtra("parametre");

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    DateFormat format = new SimpleDateFormat("ddMMyy", Locale.FRENCH);
                    Date deb = format.parse(datedeb.getText().toString());
                    Date fin = format.parse(datefin.getText().toString());
                    liste_facture=ou.listeFacture(parametre.getCo_no(),
                    new SimpleDateFormat("yyyy-MM-dd").format(deb),
                    new SimpleDateFormat("yyyy-MM-dd").format(fin),"0",ou.getVille(parametre.getDo_souche(),parametre.getCt_num()),(ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                    Intent intent = new Intent(ChoixPeriodeActivity.this, MapsActivity.class);
                    passeVariable(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }



}
