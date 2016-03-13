package com.example.tron.andgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tron.andgestion.bddlocal.depot.DepotBDD;
import com.example.tron.andgestion.bddlocal.facture.Donnee;
import com.example.tron.andgestion.bddlocal.facture.DonneeBDD;
import com.example.tron.andgestion.bddlocal.fonction.outils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class DonneeActivity extends AppCompatActivity {
    Spinner spinner;
    TextView designation;
    TextView qte;
    TextView pu ;
    TextView qtec;
    TextView total;
    Button ajouter;
    DonneeBDD donneeBDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donnee);
        ArrayList<String> list = outils.listeDepotServeurTexte();
        spinner = (Spinner)findViewById(R.id.donnee_client);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(DonneeActivity.this,
                android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        designation = (TextView) findViewById(R.id.donnee_designation);
        qte = (TextView) findViewById(R.id.donnee_qte);
        pu = (TextView) findViewById(R.id.donnee_pu);
        qtec = (TextView) findViewById(R.id.donnee_qte_colisee);
        total = (TextView) findViewById(R.id.donnee_total);
        ajouter = (Button) findViewById(R.id.donnee_ajouter);
        donneeBDD = new DonneeBDD(this);
        donneeBDD.open();

        qte.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    total.setText(String.valueOf(Float.parseFloat(pu.getText().toString())* Float.parseFloat(qte.getText().toString())));
                    // code to execute when EditText loses focus
                }
            }
        });
        ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!pu.getText().toString().isEmpty() && !qte.getText().toString().isEmpty() && !qtec.getText().toString().isEmpty() && !pu.getText().toString().isEmpty()) {

                    Donnee depot = new Donnee(String.valueOf(spinner.getSelectedItem()), designation.getText().toString(),
                            Float.parseFloat(pu.getText().toString()), Float.parseFloat(qte.getText().toString()),
                            Float.parseFloat(qtec.getText().toString()), Float.parseFloat(total.getText().toString()));
                    donneeBDD.insert(depot);
                    startActivity(new Intent(DonneeActivity.this, FactureActivity.class));
                }
                ArrayList<Donnee> ldep = donneeBDD.Tout();
                for (int i = 0; i < ldep.size(); i++)
                    System.out.println(ldep.get(i));
            }
        });

    }
}
