package com.example.tron.andgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;
import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.CompteA;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.modele.Vehicule;

import java.util.ArrayList;


/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class InfoVehiculeActivity extends AppCompatActivity {
    Button ok;
    TextView intitule;
    TextView vehicule;
    AutoCompleteTextView cr;
    ArrayList<CompteA> liste_cr;
    ArrayList<String> lcr;
    outils ou;


    private void passeVariable(Intent intent){
        intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
        intent.putExtra("id_facture", getIntent().getSerializableExtra("id_facture"));
        intent.putExtra("outils", ou);
        intent.putExtra("facture",(Facture) getIntent().getSerializableExtra("facture"));
        intent.putExtra("liste_recouvrement", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_recouvrement"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_vehicule);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app=InfoVehiculeActivity.this;
        ok = (Button) findViewById(R.id.info_ok);
        intitule= (TextView) findViewById(R.id.info_intitule);

        liste_cr =  (ArrayList<CompteA>) getIntent().getSerializableExtra("liste_cr");
        lcr = new ArrayList<String>();
        for (int i = 0; i < liste_cr.size(); i++)
            lcr.add(liste_cr.get(i).getCA_Intitule());
        cr = (AutoCompleteTextView) findViewById(R.id.info_cr);
        ArrayAdapter<String> adapterComplete = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, lcr);
        cr.setAdapter(adapterComplete);
        vehicule= (TextView) findViewById(R.id.info_vehicule);


        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!intitule.getText().toString().equals("")&& !cr.getText().toString().equals("") && !vehicule.getText().toString().equals("")) {
                    ou.ajoutVehicule(intitule.getText().toString(),vehicule.getText().toString(),cr.getText().toString());
                    Intent intent = new Intent(InfoVehiculeActivity.this, FactureCarburantActivity.class);
                    passeVariable(intent);
                }
            }
        });
    }


}
