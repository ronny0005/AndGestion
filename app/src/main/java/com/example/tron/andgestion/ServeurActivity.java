package com.example.tron.andgestion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tron.andgestion.bddlocal.fonction.outils;

/**
 * Created by T.Ron on 19/03/2016.
 */
public class ServeurActivity extends AppCompatActivity {
    TextView adresse;
    Button valide;
    outils ou = new outils();
    private static final String PREFS_NAME = "YOUR_TAG";
    private static final String DATA_TAG = "DATA_TAG";
    private String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_serveur);
        ou.app=ServeurActivity.this;
        adresse = (TextView) findViewById(R.id.serveur_adresse);
        valide = (Button) findViewById(R.id.serveur_valide);
        SharedPreferences mSettings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = mSettings.edit();

        data= mSettings.getString(DATA_TAG, null);
        if(data!=null){
            ou.lien=data;
            Intent intent = new Intent(ServeurActivity.this, MainActivity.class);
            intent.putExtra("outils", ou);
            startActivity(intent);
        }
        valide.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                ou.lien="http://"+adresse.getText().toString()+":8083/api/";
                data=ou.lien;
                editor.putString(DATA_TAG, data);
                editor.commit();
                System.out.println(ou.lien);
                Intent intent = new Intent(ServeurActivity.this, MainActivity.class);
                intent.putExtra("outils", ou);
                startActivity(intent);
            }
        });
    }
}