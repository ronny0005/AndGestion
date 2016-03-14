package com.example.tron.andgestion;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.tron.andgestion.bddlocal.facture.Donnee;
import com.example.tron.andgestion.bddlocal.facture.DonneeBDD;

import java.util.ArrayList;

/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class FactureActivity extends AppCompatActivity {
    DonneeBDD donneeBDD;
    ListView lv;
    Button nouveau;
    Button ouvrir;
    Button supprimer;
    ArrayList<Donnee> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facture);
        donneeBDD = new DonneeBDD(this);
        donneeBDD.open();
        list = donneeBDD.Tout();
        ArrayList<String> lstr = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            lstr.add(list.get(i).getDesignation());
        }
        lv = (ListView) findViewById(R.id.facture_liste);
         ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                lstr );
        lv.setAdapter(arrayAdapter);
        nouveau = (Button) findViewById(R.id.facture_nouveau);
        ouvrir = (Button) findViewById(R.id.facture_ouvrir);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                Intent intent = new Intent(FactureActivity.this, DonneeActivity.class);
                b.putInt("key", position); //Your id
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(FactureActivity.this, DonneeActivity.class));
            }
        });

        ouvrir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                startActivity(new Intent(FactureActivity.this, DonneeActivity.class));

                System.out.println(lv.getSelectedItemPosition());
            }
        });

    }
}
