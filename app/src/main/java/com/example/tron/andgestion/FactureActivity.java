package com.example.tron.andgestion;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    ArrayList<ArticleServeur> liste_article;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> lart;
    TextView caisse;
    TextView date;
    Button annuler;
    Boolean modif = false;
    int position_modif = 0;
    outils ou;
    TextView total;
    double latitude;
    double longitude;
    protected boolean gps_enabled, network_enabled;
    private Location currentBestLocation = null;
    protected LocationManager locationManager;

    Integer id_facture;
    ArrayList<String> lclient = new ArrayList<String>();
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    Parametre parametre;
    Facture facture;

    public void verouille(){
        if(!facture.getNouveau()) {
            client.setEnabled(false);
            designation.setEnabled(false);
            qte.setEnabled(false);
            caisse.setEnabled(false);
            date.setEnabled(false);
        }
    }
    public void ajoutListe() {
        data = new ArrayList<Map<String, String>>();
        for (int i = 0; i < facture.getListe_article().size(); i++) {
            DecimalFormat decim = new DecimalFormat("#.##");
            double px_achat = (facture.getListe_article().get(i).getAr_prixven() * facture.getListe_article().get(i).getQte_vendue());
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("First Line", facture.getListe_article().get(i).getAr_design());
            datum.put("Second Line", "Qte : " + ((int) facture.getListe_article().get(i).getQte_vendue())
                    + " Prix de vente : " + decim.format(px_achat));
            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[]{"First Line", "Second Line"},
                new int[]{android.R.id.text1, android.R.id.text2});

        lv.setAdapter(adapter);
        if (facture.getListe_article().size() > 0)  // disable editing password
            client.setEnabled(false);
            else  // enable editing of password
            client.setEnabled(true);
    }

    public void initialise() {
        if (!facture.getNouveau())
            client.setText(facture.getId_client().getIntitule());
    }

    public void clear() {
        qte.setText("");
        designation.setText("");
    }

    public String calculPrix() {
        double total_tva = 0, total_precompte = 0, total_marge = 0, total_ht = 0, total_ttc;
        for (int i = 0; i < facture.getListe_article().size(); i++) {
            ArticleServeur article = facture.getListe_article().get(i);
            double prix = Math.round(article.getAr_prixven() * article.getQte_vendue());
            total_tva += Math.round(prix * article.getTaxe1() / 100);
            total_precompte += Math.round(prix * article.getTaxe2() / 100);
            total_marge += Math.round(article.getQte_vendue() * article.getTaxe3());
            total_ht += prix;
        }

        DecimalFormat decim = new DecimalFormat("#");
        total_ttc = total_ht + total_tva + total_precompte + total_marge;
        return "Total TTC : " + decim.format(total_ttc);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facture);

        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = FactureActivity.this;
        parametre = (Parametre) getIntent().getSerializableExtra("parametre");
        id_facture = Integer.parseInt(getIntent().getStringExtra("id_facture"));
        final ArrayList<Client> lst_client = (ArrayList<Client>) getIntent().getSerializableExtra("liste_client");
        facture = (Facture) getIntent().getSerializableExtra("facture");
        annuler = (Button) findViewById(R.id.facture_annuler);
        caisse = (TextView) findViewById(R.id.facture_caisse);
        total = (TextView) findViewById(R.id.facture_ttc);

        caisse.setText(parametre.getCa_no().getCa_intitule());
        date = (TextView) findViewById(R.id.facture_date);
        date.setEnabled(false);
        caisse.setEnabled(false);
        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));


        liste_article =  (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article");

        if (facture.getNouveau())
            this.setTitle("Facture");
        else
            this.setTitle(facture.getRef()+" - "+facture.getEntete());

        lv = (ListView) findViewById(R.id.facture_liste);
        ajouter = (Button) findViewById(R.id.facture_ajouter);
        for (int i = 0; i < lst_client.size(); i++)
            lclient.add(lst_client.get(i).getIntitule());
        client = (AutoCompleteTextView) findViewById(R.id.facture_client);
        client.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lclient);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        client.setAdapter(adapter);

        lart = new ArrayList<String>();
        for (int i = 0; i < liste_article.size(); i++)
            lart.add(liste_article.get(i).getAr_design());
        System.out.println(lart.size()+" liste article");
        designation = (AutoCompleteTextView) findViewById(R.id.facture_designation);
        ArrayAdapter<String> adapterComplete = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lart);
        designation.setAdapter(adapterComplete);
        designation.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        qte = (TextView) findViewById(R.id.facture_qte);
        ajouter = (Button) findViewById(R.id.facture_ajouter);
        valider = (Button) findViewById(R.id.facture_valider);

        lv.setLongClickable(true);
        articleBDD = new ArticleBDD(this);
        articleBDD.open();
        ajoutListe();
        initialise();
        verouille();
        total.setText(calculPrix());

        if(!facture.getNouveau()) {
            valider.setText("Continuer");
            date.requestFocus();
        }else
            client.requestFocus();

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facture.getNouveau()) {
                    ajoutListe();
                    clear();
                    total.setText(calculPrix());
                    facture.setListe_ligne(new ArrayList<Integer>());
                    facture.setListe_article(new ArrayList<ArticleServeur>());
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (facture.getNouveau()) {
                    ArticleServeur art = facture.getListe_article().get(position);
                    designation.setText(art.getAr_design());
                    qte.setText(String.valueOf(art.getQte_vendue()));
                    modif = true;
                    ajouter.setText("Modifier");
                    position_modif = position;
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener
                () {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                if (facture.getNouveau()) {
                    final int i = pos;
                    new AlertDialog.Builder(FactureActivity.this)
                            .setTitle("Suppression")
                            .setMessage("Voulez vous supprimer " + facture.getListe_article().get(i).getAr_design() + " ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    facture.getListe_article().remove(i);
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
                }
                return false;
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (facture.getNouveau()) {

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

                            int qteart = ou.articleDisponibleServeur(liste_article.get(id_article).getAr_ref(), parametre.getDe_no());

                            if (!qte.getText().toString().equals("0") && qteart > 0) {
                                if (qteart >= Integer.parseInt(qte.getText().toString())) {
                                    if (facture.getEntete().equals("")) {
                                        facture.setId_client(lst_client.get(id_client));
                                        if (ActivityCompat.checkSelfPermission(FactureActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FactureActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        } else {
                                          Location locationGPS = getLastBestLocation();
                                            facture.setLatitude(locationGPS.getLatitude());
                                          facture.setLongitude(locationGPS.getLongitude());
                                        }
                                    }
                                    art.setQte_vendue(Integer.parseInt(qte.getText().toString()));
                                    ou.getPrixclient(liste_article.get(id_article).getAr_ref(), facture.getId_client().getCattarif(), facture.getId_client().getCatcompta(), art);
                                    if (!modif) {
                                        facture.getListe_article().add(art);
                                    } else {
                                        modif = false;
                                        ajouter.setText("Ajouter");
                                    }
                                    ajoutListe();
                                    total.setText(calculPrix());
                                    clear();
                                    designation.requestFocus();
                                } else {

                                    DecimalFormat ttcformat = new DecimalFormat("#");
                                    Toast.makeText(FactureActivity.this, "Saisie impossible. Il reste en stock " + ttcformat.format(qteart) + " éléments.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(FactureActivity.this, "Le stock de cet article est épuisé.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(FactureActivity.this, "Veuillez saisir un article et un client.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    if (facture.getListe_article().size() > 0) {
                        Intent intent = new Intent(FactureActivity.this, ValideActivity.class);
                        intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
                        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                        intent.putExtra("id_facture", String.valueOf(id_facture));
                        intent.putExtra("outils", ou);
                        intent.putExtra("facture",facture);
                        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                        startActivity(intent);
                    }
            }
        });

    }

    public Location getLastBestLocation() {
        GPSTracker gps = new GPSTracker(this,FactureActivity.this);
        return gps.getLocation(FactureActivity.this);
    }
}
