package com.example.tron.andgestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
                startActivity(new Intent(MenuActivity.this, FactureActivity.class));
            }
        });

        parametre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ParametresActivity.class));
            }
        });
    }


}
