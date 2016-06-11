package com.example.tron.andgestion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.ListView;

import com.example.tron.andgestion.Stock.ListAdapter;
import com.example.tron.andgestion.Stock.Stock;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Parametre;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity {

    private TableLayout tables;
    outils ou = new outils();
    Parametre param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = StockActivity.this;
        param = (Parametre) getIntent().getSerializableExtra("parametre");
        ListView yourListView = (ListView) findViewById(R.id.listView);

        System.out.println("before");
        ArrayList<Stock> stocks = outils.getStock(param.getDe_no());
/*
        ArrayList<Stock> stocks1 = new ArrayList<>();
        stocks1.add(new Stock("10111","ARTICLE",22,2000,20));
        stocks1.add(new Stock("10111", "Nouriss vanille", 22, 2000, 20));
        stocks1.add(new Stock("10111", "Nouriss fraise", 22, 3000, 30));
        stocks1.add(new Stock("10111","Nouriss ananas",22,4000,40));
        stocks1.add(new Stock("10111","Nouriss vanille",22,2000,20));
        stocks1.add(new Stock("10111","Nouriss fraise",22,3000,30));
        stocks1.add(new Stock("10111","Nouriss ananas",22,4000,40));
        stocks1.add(new Stock("10111","Nouriss vanille",22,2000,20));
        stocks1.add(new Stock("10111","Nouriss fraise",22,3000,30));
        stocks1.add(new Stock("10111","Nouriss ananas",22,4000,40));
        stocks1.add(new Stock("10111","Nouriss vanille",22,2000,20));
        stocks1.add(new Stock("10111","Nouriss fraise",22,3000,30));
        stocks1.add(new Stock("10111","Nouriss ananas",22,4000,40));
        stocks1.add(new Stock("10111","Nouriss vanille",22,2000,20));
        stocks1.add(new Stock("10111","Nouriss fraise",22,3000,30));
        stocks1.add(new Stock("10111","Nouriss ananas",22,4000,40));
*/
        ListAdapter customAdapter = new ListAdapter(this, R.layout.itemlistrow, stocks);

        yourListView.setAdapter(customAdapter);

    }
}
