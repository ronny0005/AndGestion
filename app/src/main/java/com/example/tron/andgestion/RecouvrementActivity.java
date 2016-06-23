package com.example.tron.andgestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.cReglement;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Parametre;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecouvrementActivity extends AppCompatActivity {
    Button nouveau;
    ArrayList<String> lclient = new ArrayList<String>();
    AutoCompleteTextView client;
    Button menu;
    Button valide;
    Button mttvalide;
    Button rechercher;
    ArrayList<Facture> liste_recouvrement;
    ListView lst_fact;
    ArrayList<String> lstr;
    ArrayList<ArticleServeur> liste_article;
    ArrayAdapter<String> arrayAdapter;
    List<Map<String, String>> data;
    outils ou;
    TextView mtt;
    Map<String, String> datum;
    int compteur = 0;
    Date datecmpt = new Date();
    Parametre parametre;
    int pos;
    private static final String PREFS_NAME = "compteur";
    private cReglement cr=null;
    ArrayList<Client> lst_client;

    private void itemCommun(Intent intent,Facture facture,int idfacture){
        intent.putExtra("liste_recouvrement", liste_recouvrement);
        intent.putExtra("liste_facture", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture"));
        intent.putExtra("outils", ou);
        intent.putExtra("facture", facture);
        intent.putExtra("id_facture", String.valueOf(idfacture));
        intent.putExtra("parametre", parametre);
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        startActivity(intent);
    }

    private void AfficheFacture(){
        for(int i=0;i<lst_client.size();i++) {
            if (lst_client.get(i).getIntitule().equals(client.getText().toString())) {
                lst_fact.setEnabled(true);
                valideFacture(lst_client.get(i).getNum());
                lst_fact.setEnabled(false);
                mtt.setEnabled(true);
                valide.setEnabled(true);

            }
        }
    }

    public void valideFacture(String ct_num){
        liste_recouvrement=ou.listeFacture(parametre.getCo_no(),"0","0",ct_num,ou.getVille(parametre.getDo_souche(),parametre.getCt_num()));
        ajoutListe();
    }

    public void initVariable() {
        // Get from the SharedPreferences
        try {
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            DateFormat format = new SimpleDateFormat("yyy-MM-dd", Locale.FRENCH);
            String form = settings.getString("dateCmpt", null);
            if (form == null)
                form = format.format(new Date());
            datecmpt = format.parse(form);
            if (datecmpt.getDay() == (new Date()).getDay()) {
                compteur = settings.getInt("compteur", 0);
            } else {
                compteur = 0;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Map<String, ?> createRow(String value1, String value2) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("value1", value1);
        row.put("value2", value2);
        return row;
    }

    public void ajoutListe() {
        data = new ArrayList<Map<String, String>>();
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        for (int i = 0; i < liste_recouvrement.size(); i++) {
            Facture fac =liste_recouvrement.get(i);
            String val="";
            DecimalFormat ttcformat = new DecimalFormat("#");
            int reste = (int) (fac.getTotalTTC()-fac.getMtt_avance());
            if(liste_recouvrement.get(i).getStatut().equals("avance"))
                val=" ("+ttcformat.format(fac.getMtt_avance())+")";
            data.add(createRow(fac.getRef() + " - " + fac.getEntete()+" - " + fac.getStatut()+val ,
                    "Client : " + fac.getId_client().getIntitule()+ "\nTotal TTC : "
                            + fac.getTotalTTC()
                            +"\nReste à payer : "+reste
                            +"\n"+fac.getDO_Date() ));
        }
        String[] from = {"value1", "value2"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, from, to);
        lst_fact.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recouvrement);
        ou = (outils) getIntent().getSerializableExtra("outils");

        ou.app = RecouvrementActivity.this;
        pos=-1;
        parametre=(Parametre) getIntent().getSerializableExtra("parametre");
        menu = (Button) findViewById(R.id.recouvrement_menu);
        rechercher = (Button) findViewById(R.id.recouvrement_rechercher);
        valide = (Button) findViewById(R.id.recouvrement_valide);
        lst_client = (ArrayList<Client>) getIntent().getSerializableExtra("liste_client");
        liste_recouvrement = new ArrayList<Facture>();
        mtt = (TextView) findViewById(R.id.recouvrement_mtt);
        mtt.setEnabled(false);
        valide.setEnabled(false);

        for (int i = 0; i < lst_client.size(); i++)
            lclient.add(lst_client.get(i).getIntitule());
        client = (AutoCompleteTextView) findViewById(R.id.recouvrement_client);
        client.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lclient);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        client.setAdapter(adapter);

        lst_fact = (ListView) findViewById(R.id.liste_facture);
        ajoutListe();
        initVariable();


        rechercher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AfficheFacture();
            }
        });

        valide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String clt = "";
                for(int i=0;i<lst_client.size();i++) {
                    if (lst_client.get(i).getIntitule().equals(client.getText().toString())) {
                        clt = lst_client.get(i).getNum();
                    }
                }
                System.out.println(clt);
                System.out.println(String.valueOf(parametre.getCa_no().getCa_no()));
                System.out.println(String.valueOf(parametre.getCo_no()));
                System.out.println(Integer.parseInt(mtt.getText().toString()));
               cr =  outils.addReglement(clt, "RGT" , String.valueOf( Integer.parseInt(mtt.getText().toString())),String.valueOf(parametre.getCo_no()),String.valueOf(parametre.getCa_no().getCa_no()));
                System.out.println(cr);
                lst_fact.setEnabled(true);
                mtt.setEnabled(false);
            }
        });
        lst_fact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Facture fact = liste_recouvrement.get(position);
                pos = position;
                new AlertDialog.Builder(RecouvrementActivity.this)
                        .setTitle("Réglement")
                        .setMessage("Voulez vous régler la facture " + fact.getEntete() + " ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int mtt_regl = Integer.parseInt(mtt.getText().toString());
                                int total = mtt_regl - (fact.getTotalTTC()- (int)fact.getMtt_avance());
                                String dr="0";
                                if(total>=0) {
                                    dr = "1";
                                    mtt_regl=fact.getTotalTTC()- (int)fact.getMtt_avance();
                                }
                                mtt.setEnabled(true);
                                mtt.setText(""+total);
                                mtt.setEnabled(false);
                                outils.addEcheance(String.valueOf(cr.getCbMarq()), String.valueOf(mtt_regl), fact.getEntete(), dr);
                                if((int)fact.getMtt_avance()==0){
                                //    cr=ou.reglerEntete(fact.getEntete(), fact.getRef(), String.valueOf(mtt_regl));
                                }else {
                                    if (cr == null) {
                                        //cr = outils.addReglement(fact.getEn), "RGT" /*+ fact.getId_client().getIntitule()*/, String.valueOf(mtt_regl),String.valueOf(parametre.getCo_no()));
                                    }
                                }
                                for(int i=0;i<lst_client.size();i++) {

                                    if (lst_client.get(i).getIntitule().equals(client.getText().toString())) {
                                        valideFacture(lst_client.get(i).getNum());
                                    }
                                }
                                System.out.println("total "+total);
                                if(total<=0){
                                    total = 0;
                                    mtt.setEnabled(true);
                                    mtt.setText(""+total);
                                    lst_fact.setEnabled(true);
                                    outils.updateRgImpute(String.valueOf(cr.getCbMarq()));
                                    cr=null;
                                }
                                AfficheFacture();

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
        });
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RecouvrementActivity.this, MenuActivity.class);
                itemCommun(intent,null,0);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        outils Ou = new outils();
        Ou.app = RecouvrementActivity.this;
        Intent intent = new Intent(RecouvrementActivity.this, RecouvrementActivity.class);
        itemCommun(intent,null,0);
        super.onBackPressed();  // optional depending on your needs
    }
}
