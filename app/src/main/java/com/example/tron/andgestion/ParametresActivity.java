package com.example.tron.androidgestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.tron.androidgestion.bddlocal.fonction.outils;

import java.util.ArrayList;

/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class ParametresActivity extends AppCompatActivity {

    Spinner affaire;
    Spinner vehicule;
    Spinner depot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametre);

        ArrayList<String> list = outils.listeAffaireTexte();
        affaire = (Spinner)findViewById(R.id.param_affaire);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        affaire.setAdapter(adapter);

        ArrayList<String> listv = outils.listeVehiculeTexte();
        vehicule = (Spinner)findViewById(R.id.param_vehicule);
        ArrayAdapter<String> adapterv = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicule.setAdapter(adapterv);


        listv = outils.listeDepotServeurTexte();
        depot = (Spinner)findViewById(R.id.param_depot);
        adapterv = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depot.setAdapter(adapterv);

        listv = outils.listeSoucheServeurTexte();
        depot = (Spinner)findViewById(R.id.param_souche);
        adapterv = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depot.setAdapter(adapterv);

        listv = outils.listeCaisseServeurTexte();
        depot = (Spinner)findViewById(R.id.param_caisse);
        adapterv = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depot.setAdapter(adapterv);

        listv = outils.listeSoucheServeurTexte();
        depot = (Spinner)findViewById(R.id.param_souche);
        adapterv = new ArrayAdapter<String>(ParametresActivity.this,
                android.R.layout.simple_spinner_item,listv);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depot.setAdapter(adapterv);

    }
}
