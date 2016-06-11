package com.example.tron.andgestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Parametre;

import java.util.ArrayList;


/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class ParametresActivity extends AppCompatActivity {

    Spinner affaire;
    Spinner vehicule;
    Spinner depot;
    Parametre param;
    outils ou = new outils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametre);
        param = (Parametre) getIntent().getSerializableExtra("parametre");

        ArrayList<String> list = ou.listeAffaireTexte();
        affaire = (Spinner)findViewById(R.id.param_affaire);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        affaire.setAdapter(adapter);

        ArrayList<String> listv = ou.listeVehiculeTexte();
        vehicule = (Spinner)findViewById(R.id.param_vehicule);
        ArrayAdapter<String> adapterv = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicule.setAdapter(adapterv);
        for(int i=0;i<listv.size();i++){
            if(listv.get(i).equals(param.getVehicule()))
                vehicule.setSelection(i);
        }

        listv = ou.listeDepotServeurTexte();
        depot = (Spinner)findViewById(R.id.param_depot);
        adapterv = new ArrayAdapter<String>(ParametresActivity.this,android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depot.setAdapter(adapterv);


        listv = ou.listeSoucheServeurTexte();
        depot = (Spinner)findViewById(R.id.param_souche);
        adapterv = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depot.setAdapter(adapterv);

        listv = ou.listeCaisseServeurTexte();
        depot = (Spinner)findViewById(R.id.param_caisse);
        adapterv = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depot.setAdapter(adapterv);

        listv = ou.listeSoucheServeurTexte();
        depot = (Spinner)findViewById(R.id.param_souche);
        adapterv = new ArrayAdapter<String>(ParametresActivity.this,android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depot.setAdapter(adapterv);

    }
}
