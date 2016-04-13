package com.example.tron.andgestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.Stock.StockEqVendeur;
import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.depot.Depot;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EquationActivity extends AppCompatActivity {
    Button afficher;
    TextView dt_deb;
    TextView dt_fin;
    ListView lst_equation ;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    List<Map<String, String>> data;
    outils ou;
    Parametre param;
    Map<String, String> datum;
    ArrayList<StockEqVendeur> lstk;

    private Map<String, ?> createRow(String value1, String value2) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("value1", value1);
        row.put("value2", value2);
        return row;
    }

    public void ajoutListe(Date deb,Date fin){
        lstk = new ArrayList<StockEqVendeur> ();
        data = new ArrayList<Map<String, String>>();
        lstk=ou.eqStkVendeur(String.valueOf(param.getDe_no()), new SimpleDateFormat("yyyy-MM-dd").format(deb), new SimpleDateFormat("yyyy-MM-dd").format(fin));
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for(int i=0;i<lstk.size();i++) {
            data.add(createRow(lstk.get(i).getDesign(), " Stock : " + lstk.get(i).getStock()+"  Entrées : " + lstk.get(i).getEntrees()
            +"\n Retours : " + lstk.get(i).getRetours()+"  Avaries : " + lstk.get(i).getAvaris()
            +"\n Stock final : " + lstk.get(i).getStock_final() +" Quantités vendues : " + lstk.get(i).getQte_vendues()
            +"\n Stock restants : " + lstk.get(i).getStk_restants()));
        }
        String[] from = {"value1", "value2"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, data,android.R.layout.simple_list_item_2, from, to);
        lst_equation.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equation_stock);
        this.setTitle("Equation stock");
        ou = (outils) getIntent().getSerializableExtra("outils");
        param = (Parametre) getIntent().getSerializableExtra("parametre");
        ou.app=EquationActivity.this;
        afficher = (Button) findViewById(R.id.equation_afficher);
        dt_deb = (TextView) findViewById(R.id.equation_dt_deb);
        dt_fin = (TextView) findViewById(R.id.equation_dt_fin);
        lst_equation = (ListView) findViewById(R.id.equation_lv);
        dt_deb.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        dt_fin.setText(new SimpleDateFormat("ddMMyy").format(new Date()));
        afficher.setOnClickListener(new View.OnClickListener(){
               public void onClick(View v) {
               if(!dt_deb.getText().toString().isEmpty() && !dt_fin.getText().toString().isEmpty()) {
                   try {

                       DateFormat format = new SimpleDateFormat("ddMMyy", Locale.FRENCH);
                       Date deb = format.parse(dt_deb.getText().toString());
                       Date fin = format.parse(dt_fin.getText().toString());
                       ajoutListe(deb,fin);
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
               }
           }
        });
        }
    }
