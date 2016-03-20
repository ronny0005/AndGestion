package com.example.tron.androidgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tron.androidgestion.bddlocal.client.Client;
import com.example.tron.androidgestion.bddlocal.facture.Facture;

import java.util.ArrayList;

/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class MenuActivity extends AppCompatActivity {
    Button facture;
    Button parametre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        facture = (Button) findViewById(R.id.menu_facturation);
        parametre = (Button) findViewById(R.id.menu_parametre);

        facture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LstFactureActivity.class);
                intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                startActivity(intent);
            }
        });

        parametre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ParametresActivity.class));
            }
        });
    }


}
