package com.example.tron.andgestion;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Parametre;

import java.util.ArrayList;


/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class MenuActivity extends AppCompatActivity {
    Button facture;
   // Button parametre;
    Button etat;
    Button recouvrement;
    Button avari;
    outils ou;
    GridLayout grid;


    private void passeVariable(Intent intent){
        ou.passeVariable(intent, MenuActivity.this,(ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"),
              (Parametre) getIntent().getSerializableExtra("parametre"),ou,(ArrayList<Facture>) getIntent().getSerializableExtra("liste_recouvrement"),
            (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"),(ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"),"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app=MenuActivity.this;
        facture = (Button) findViewById(R.id.menu_facture);
       // parametre = (Button) findViewById(R.id.menu_parametres);
        etat = (Button) findViewById(R.id.menu_etats);
        recouvrement = (Button) findViewById(R.id.menu_recouvrement);

        etat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LstStockActivity.class);
                passeVariable(intent);
            }
        });


        facture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LstFactureActivity.class);
                passeVariable(intent);
            }
        });
        recouvrement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(MenuActivity.this, RecouvrementActivity.class);
                //passeVariable(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        //outils Ou = new outils();
        //Ou.app = MenuActivity.this;
        Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
        startActivity(intent);
        //itemCommun(intent,null,0);
        super.onBackPressed();  // optional depending on your needs
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuItem deconnexion =  menu.add(Menu.NONE,1, Menu.NONE, "Déconnexion");
       // MenuItem password =  menu.add(Menu.NONE,2, Menu.NONE, "Changer Mot de Passe");
        deconnexion.setIcon(R.drawable.deconnexion);
       /// password.setIcon(R.drawable.password);
        deconnexion.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        //password.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                System.out.println("deconnxion");
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            //case 2:
              //  System.out.println("change password");
               // Intent intent2 = new Intent(MenuActivity.this, ChangePassword.class);
               // startActivity(intent2);
               // return true;

            default:
                return false;
        }
    }

}
