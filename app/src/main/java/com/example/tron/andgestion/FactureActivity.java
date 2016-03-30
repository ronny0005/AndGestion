package com.example.tron.andgestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tron.andgestion.bddlocal.article.Article;
import com.example.tron.andgestion.bddlocal.article.ArticleBDD;
import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by T.Ron$ on 13/03/2016.
 */
public class FactureActivity extends AppCompatActivity {
    ArticleBDD donneeBDD;
    ListView lv;
    Button ajouter;
    Button valider;
    Button ouvrir;
    Button supprimer;
    AutoCompleteTextView client;
    AutoCompleteTextView designation;
    ArrayList<Article> list;
    TextView qte;
    ArticleBDD articleBDD;
    ArrayList<Integer> position_article = new ArrayList<Integer>();
    ArrayList<ArticleServeur> liste_article;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Facture> liste_facture;
    ArrayList<String> lart;
    TextView caisse;
    TextView date;
    Button annuler;
    Boolean modif = false;
    int position_modif = 0;
    outils ou;
    TextView total;

    Integer id_facture;
    ArrayList<String> lclient=new ArrayList<String>();
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    Parametre parametre;
    public void ajoutListe(){
        data=new ArrayList<Map<String, String>>();
        for(int i=0;i<position_article.size();i++){
            double px_achat = (liste_article.get(position_article.get(i)).getAr_prixach()*liste_article.get(position_article.get(i)).getQte_vendue());
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("First Line", liste_article.get(position_article.get(i)).getAr_design());
            datum.put("Second Line","Qte : " + ((int) liste_article.get(position_article.get(i)).getQte_vendue())
            +" Prix achat : "+ px_achat);
            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        lv.setAdapter(adapter);
        if (position_article.size()>0) { // disable editing password
            client.setFocusable(false);
            client.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            client.setClickable(false); // user navigates with wheel and selects widget
        } else { // enable editing of password
            client.setFocusable(true);
            client.setFocusableInTouchMode(true);
            client.setClickable(true);
        }
    }

    public void initialise(){
        if(liste_facture.get(id_facture).getPosition_article().size()>0){
            client.setText(liste_facture.get(id_facture).getId_client().getIntitule());
        }
    }
    public void clear(){
        qte.setText("");
        designation.setText("");
    }
    public String calculPrix(){
        double total_tva=0,total_precompte=0,total_marge=0,total_ht=0,total_ttc;
        for (int i = 0; i < position_article.size(); i++) {
            ArticleServeur article = liste_facture.get(id_facture).getListe_article().get(position_article.get(i));
            double prix = article.getAr_prixven() * article.getQte_vendue();
            total_tva += prix * article.getTaxe1() / 100;
            total_precompte += prix * article.getTaxe2() / 100;
            total_marge += prix * article.getTaxe3() / 100;
            total_ht += prix;
        }
        DecimalFormat decim = new DecimalFormat("#.##");
        total_ttc = total_ht + total_tva + total_precompte + total_marge;
        return "Total TTC : "+decim.format(total_ttc);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facture);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app=FactureActivity.this;
        parametre = (Parametre) getIntent().getSerializableExtra("parametre");
        id_facture= Integer.parseInt(getIntent().getStringExtra("id_facture"));
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        final ArrayList<Client> lst_client = (ArrayList<Client>) getIntent().getSerializableExtra("liste_client");

        annuler = (Button) findViewById(R.id.facture_annuler);
        caisse = (TextView) findViewById(R.id.facture_caisse);
        total = (TextView) findViewById(R.id.facture_ttc);

        caisse.setFocusable(false);
        caisse.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        caisse.setClickable(false);
        caisse.setText(parametre.getCa_no().getCa_intitule());
        caisse.setBackgroundColor(R.color.gray);
        date = (TextView) findViewById(R.id.facture_date);
        date.setBackgroundColor(R.color.gray);
        date.setFocusable(false);
        date.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        date.setClickable(false);
        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));


        System.out.println(liste_facture.size());
        position_article = liste_facture.get(id_facture).getPosition_article();
        liste_article = liste_facture.get(id_facture).getListe_article();

        if(liste_facture.get(id_facture).getNouveau())
            this.setTitle("Facture");
        else
            this.setTitle(liste_facture.get(id_facture).getRef());

        lv = (ListView) findViewById(R.id.facture_liste);
        ajouter = (Button) findViewById(R.id.facture_ajouter);
        for(int i=0;i<lst_client.size();i++)
            lclient.add(lst_client.get(i).getIntitule());
        client = (AutoCompleteTextView)findViewById(R.id.facture_client);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lclient);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        client.setAdapter(adapter);

        lart = new ArrayList<String>();
        for(int i=0;i<liste_article.size();i++)
            lart.add(liste_article.get(i).getAr_design());
        designation = (AutoCompleteTextView) findViewById(R.id.facture_designation);
        ArrayAdapter<String> adapterComplete = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lart);
        designation.setAdapter(adapterComplete);

        qte = (TextView) findViewById(R.id.facture_qte);
        ajouter = (Button) findViewById(R.id.facture_ajouter);
        valider = (Button) findViewById(R.id.facture_valider);

        lv.setLongClickable(true);
        articleBDD = new ArticleBDD(this);
        articleBDD.open();
        ajoutListe();
        initialise();

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position_article = new ArrayList<Integer>();
                ajoutListe();
                liste_facture.get(id_facture).setId(0);
                clear();
                total.setText(calculPrix());
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleServeur art = liste_article.get(position_article.get(position));
                designation.setText(art.getAr_design());
                qte.setText(String.valueOf(art.getQte_vendue()));
                modif = true;
                position_modif = position;
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener
                () {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int
                    pos, long id) {
                final int i = pos;
                new AlertDialog.Builder(FactureActivity.this)
                        .setTitle("Suppression")
                        .setMessage("Voulez vous supprimer " + liste_article.get(position_article.get(pos)).getAr_design() + " ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                position_article.remove(i);
                                ajoutListe();
                                clear();
                                total.setText(calculPrix());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!qte.getText().toString().isEmpty() && !designation.getText().toString().isEmpty() && !client.getText().toString().isEmpty()) {
                    ArticleServeur art = null;
                    for (int i = 0; i < liste_article.size(); i++) {
                        if (liste_article.get(i).getAr_design().equals(designation.getText().toString()))
                            art = liste_article.get(i);
                    }
                    int id_client = 0;
                    for (int i = 0; i < lst_client.size(); i++)
                        if (lst_client.get(i).getIntitule().equals(client.getText().toString()))
                            id_client = i;
                    if (art != null && id_client != 0) {
                        int id_article = 0;
                        for (int i = 0; i < liste_article.size(); i++)
                            if (liste_article.get(i).getAr_design().equals(designation.getText().toString()))
                                id_article = i;
                        double qteart = ou.articleDisponibleServeur(liste_article.get(id_article).getAr_ref(), parametre.getDe_no());
                        System.out.println("qte art"+qteart );
                        if (qteart > 0) {
                            String piece = "";
                            if (liste_facture.get(id_facture).getEntete().equals("")) {
                                piece = ou.ajoutEnteteServeur(parametre.getCo_no(), lst_client.get(id_client).getNum(), liste_facture.get(id_facture).getRef(), "1");
                                liste_facture.get(id_facture).setId_client(lst_client.get(id_client));
                                liste_facture.get(id_facture).setEntete(piece);
                            }
                            if (!modif) {
                                ou.ajoutLigneServeur(liste_facture.get(id_facture).getEntete(), String.valueOf(liste_article.get(id_article).getAr_ref()), (position_article.size()+1) * 10000, Integer.parseInt(qte.getText().toString()), 0);
                                position_article.add(id_article);
                                liste_article.get(id_article).setId(liste_article.size());
                            } else {
                                modif = false;
                            }
                            liste_article.get(id_article).setQte_vendue(Integer.parseInt(qte.getText().toString()));
                            ou.getPrixclient(liste_article.get(id_article).getAr_ref(), liste_facture.get(id_facture).getId_client().getCattarif(), liste_facture.get(id_facture).getId_client().getCatcompta(), liste_article.get(id_article));
                            ajoutListe();
                            total.setText(calculPrix());
                            clear();

                        }
                        else {
                            Toast.makeText(FactureActivity.this, "Le stock de cet article est épuisé.",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(FactureActivity.this, "Veuillez saisir un article et un client.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if(position_article.size()>0) {
                Intent intent = new Intent(FactureActivity.this, ValideActivity.class);
                liste_facture.get(id_facture).setPosition_article(position_article);
                intent.putExtra("liste_facture", liste_facture);
                intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                intent.putExtra("id_facture", String.valueOf(id_facture));
                intent.putExtra("outils", ou);
                intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                startActivity(intent);
            }
            }
        });

    }
}
