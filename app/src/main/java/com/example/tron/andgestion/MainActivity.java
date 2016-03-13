package com.example.tron.andgestion;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tron.andgestion.bddlocal.depot.Depot;
import com.example.tron.andgestion.bddlocal.fonction.outils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button connexion;
    Button facture;
    TextView login;
    TextView mdp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connexion = (Button) findViewById(R.id.connexion_button);
        facture = (Button) findViewById(R.id.menu_facturation);
        login =(TextView) findViewById(R.id.connexion_login);
        mdp =(TextView) findViewById(R.id.connexion_mdp);

        connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<Depot> list = outils.listeDepotServeur();
                System.out.println("main figure"+login.getText().toString()+" test "+mdp.getText().toString());
                // ! à enlever
                if(!outils.connexion(login.getText().toString(),mdp.getText().toString())){
                    System.out.println("login passé");
                    startActivity(new Intent(MainActivity.this, MenuActivity.class));
                }
            }
        });

    }
}
