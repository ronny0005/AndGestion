package com.example.tron.andgestion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by T.Ron on 19/03/2016.
 */
public class ValideActivity extends AppCompatActivity {
    ArrayList<Facture> liste_facture;
    double total_tva = 0;
    double total_precompte = 0;
    double total_marge = 0;
    double total_ttc = 0;
    double total_ht = 0;
    TextView t_marge;
    TextView t_ht;
    TextView t_tva;
    TextView t_ttc;
    TextView t_precompte;
    TextView mtt_avance;
    Spinner mode_paiement;
    ArrayList<String> lstr;
    ArrayAdapter<String> arrayAdapter;
    CheckBox comptant;
    CheckBox credit;
    Button annuler;
    Button valider;
    Button imprime;
    int id_facture;
    outils ou = new outils();
    Parametre parametre;
    ArrayList<ArticleServeur> liste_article;
    Facture facture;
    private DecimalFormat decim = new DecimalFormat("#.##");
    private DecimalFormat ttcformat = new DecimalFormat("#");
    private WebView myWebView;

    public void active_avance(boolean active){
        if(active) {
            mtt_avance.setEnabled(true);
            mtt_avance.setText("");
        }else {
            mtt_avance.setEnabled(false);
            mtt_avance.setText(" 0");
        }
    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    public void imprime(){
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String url)
            {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                createWebPrintJob(view);
                myWebView = null;
            }
        });
        String htmlDocument =   "<html><body><h1>CAMLAIT S.A.</h1>" +
                "tel : 635423723<br/><br/>";
        htmlDocument+=facture.getEntete()+"<br/><br/>";
        htmlDocument+="<table>";
        double total_tva=0;
        double total_precompte=0;
        double total_marge=0;
        double total_ht=0;
        double total_ttc=0;
        for (int i = 0; i < facture.getListe_article().size(); i++) {
            ArticleServeur article = facture.getListe_article().get(i);
            double prix = Math.round(article.getAr_prixven() * article.getQte_vendue());
            total_tva += Math.round(prix * article.getTaxe1() / 100);
            total_precompte += Math.round(prix * article.getTaxe2() / 100);
            total_marge += Math.round(article.getQte_vendue() * article.getTaxe3());
            total_ht += prix;
        }

        total_ttc = total_ht + total_tva + total_precompte + total_marge;

        for(int i=0;i<facture.getListe_article().size();i++) {
            htmlDocument += "<tr><td>" + facture.getListe_article().get(i).getAr_design() + "</td></tr><tr><td style=\"float:right\">" +
                    facture.getListe_article().get(i).getAr_prixven() + "x" + facture.getListe_article().get(i).getQte_vendue() + "</td></tr>";
        }
        htmlDocument +="<tr><td><br/></td></tr>";
        htmlDocument +="<tr><td>Total : "+total_ttc+"</td></tr>";
        htmlDocument+="</table>";
        htmlDocument+= "</body></html>";
        webView.loadDataWithBaseURL(null, htmlDocument,"text/HTML", "UTF-8", null);
        myWebView = webView;
    }
    public void bloqueBox(){
        if(!facture.getNouveau()) {
            credit.setEnabled(false);
            comptant.setEnabled(false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valide_facture);
        parametre = (Parametre) getIntent().getSerializableExtra("parametre");
        liste_article = (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article");
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        facture = (Facture) getIntent().getSerializableExtra("facture");
        id_facture= Integer.parseInt(getIntent().getStringExtra("id_facture"));
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = ValideActivity.this;

        t_marge = (TextView) findViewById(R.id.valide_marge);
        t_ht = (TextView) findViewById(R.id.valide_ht);
        t_tva = (TextView) findViewById(R.id.valide_tva);
        t_ttc = (TextView) findViewById(R.id.valide_ttc);
        t_precompte = (TextView) findViewById(R.id.vailde_precompte);
        mtt_avance = (TextView) findViewById(R.id.valide_avance);
        mode_paiement = (Spinner) findViewById(R.id.valide_paiement);
        comptant = (CheckBox) findViewById(R.id.valide_comptant);
        credit = (CheckBox) findViewById(R.id.valide_credit);
        valider = (Button) findViewById(R.id.valide_ajout);
        imprime = (Button) findViewById(R.id.valide_imprime);
        lstr = new ArrayList<String>();
        lstr.add("Espèce");
        lstr.add("Carte");
        lstr.add("Chèque");
        active_avance(false);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lstr);
        mode_paiement.setAdapter(arrayAdapter);
        if(!facture.getNouveau()) {
            if (!facture.getStatut().isEmpty())
                if (facture.getStatut().equals("credit"))
                    credit.setChecked(true);
                else
                    comptant.setChecked(true);
        }

        facture.setType_paiement("espece");

        bloqueBox();
        comptant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (comptant.isChecked()) {
                    credit.setChecked(false);
                    active_avance(false);
                    facture.setStatut("comptant");
                }
            }
        });

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (credit.isChecked()) {
                    comptant.setChecked(false);
                    active_avance(true);
                    facture.setStatut("credit");
                }
            }
        });

        for (int i = 0; i < facture.getListe_article().size(); i++) {
            ArticleServeur article = facture.getListe_article().get(i);
            double prix = Math.round(article.getAr_prixven() * article.getQte_vendue());
            total_tva += Math.round(prix * article.getTaxe1() / 100);
            total_precompte += Math.round(prix * article.getTaxe2() / 100);
            total_marge += Math.round(article.getQte_vendue() * article.getTaxe3());
            total_ht += prix;
            System.out.println(article.getAr_design() + " total :" + prix);
        }


        total_ttc = total_ht + total_tva + total_precompte + total_marge;
        t_marge.setText(decim.format(total_marge));
        t_tva.setText(decim.format(total_tva));
        t_ttc.setText(ttcformat.format(total_ttc));
        t_ht.setText(decim.format(total_ht));
        t_precompte.setText(decim.format(total_precompte));

        if(!facture.getNouveau()) {
            valider.setText("Continuer");
            mtt_avance.setText(String.valueOf(ttcformat.format(facture.getMtt_avance())));
        }
        mode_paiement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (position == 0)
                    facture.setType_paiement("espece");
                if (position == 1)
                    facture.setType_paiement("carte");
                if (position == 2)
                    facture.setType_paiement("cheque");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        imprime.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               DecimalFormat decim = new DecimalFormat("#.##");
               Double d;
               if (mtt_avance.getText().toString().equals(""))
                   d = Double.MIN_VALUE;
               else d = Double.parseDouble(mtt_avance.getText().toString());

               if (Double.compare(total_ttc, d) >= 0) {
                   if (comptant.isChecked() || credit.isChecked()) {
                       if (credit.isChecked() && !mtt_avance.getText().toString().isEmpty()
                               && Double.compare(total_ttc, d) >= 0) {
                           facture.setStatut("avance");
                           facture.setMtt_avance(d);
                       }

                       if (facture.getNouveau()) {
                           /*parametre= ou.getParametre(parametre.getUser(),parametre.getMdp());
                           try {
                               Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(parametre.getDate_facture());
                               if(dt.compareTo(new Date())==0){
                                   facture.setRef("Fact"+parametre.getID_Facture());
                                   parametre.setID_Facture(parametre.getID_Facture()+1);
                               }else{
                                   facture.setRef("Fact1");
                                   parametre.setDate_facture(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                   parametre.setID_Facture(2);
                                   System.out.println("vloop");
                               }
                               ou.setParametre(parametre.getMdp(),parametre.getUser(),parametre.getDate_facture(),String.valueOf(parametre.getID_Facture()));
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }*/

                           String entete = ou.ajoutEnteteServeur(parametre.getCo_no(), facture.getId_client().getNum(), facture.getRef(), "1",(float)facture.getLatitude(),(float)facture.getLongitude());
                           facture.setEntete(entete);
                           for (int i = 0; i < facture.getListe_article().size(); i++) {
                               ArticleServeur article = facture.getListe_article().get(i);
                               ou.ajoutLigneServeur(entete, String.valueOf(facture.getListe_article().get(i).getAr_ref()), 10000 * i, article.getQte_vendue(), 0);
                           }
                           facture.setDO_Date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                           liste_facture.add(facture);
                           facture.setNouveau(false);
                           facture.setTotalTTC(Integer.parseInt(t_ttc.getText().toString()));
                           String montant = "0";
                           if(comptant.isChecked())
                               montant=ttcformat.format(total_ttc);
                           else
                           if(!mtt_avance.getText().toString().equals(""))
                               montant=mtt_avance.getText().toString();
                           ou.reglerEntete(facture.getEntete(), facture.getRef(),montant);
                       }
                       Intent intent = new Intent(ValideActivity.this, LstFactureActivity.class);
                       intent.putExtra("liste_facture", liste_facture);
                       intent.putExtra("facture", facture);
                       intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                       intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                       intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                       intent.putExtra("outils", ou);
                       imprime();
                       startActivity(intent);
                   } else {
                       Toast.makeText(ValideActivity.this, "Choississez un mode de règlement.", Toast.LENGTH_SHORT).show();
                   }
               } else {
                   Toast.makeText(ValideActivity.this, "le montant de l'avance ne peut pas être supérieur au montant TTC.",
                           Toast.LENGTH_SHORT).show();
               }
               if (!facture.getNouveau()) {

                   Intent intent = new Intent(ValideActivity.this, LstFactureActivity.class);
                   intent.putExtra("liste_facture", liste_facture);
                   intent.putExtra("facture", facture);
                   intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                   intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                   intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                   intent.putExtra("outils", ou);
                   imprime();
                   startActivity(intent);
               }
        }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DecimalFormat decim = new DecimalFormat("#.##");
                Double d;
                if (mtt_avance.getText().toString().equals(""))
                    d = Double.MIN_VALUE;
                else d = Double.parseDouble(mtt_avance.getText().toString());

                if (Double.compare(total_ttc, d) >= 0) {
                    if (comptant.isChecked() || credit.isChecked()) {
                        if (credit.isChecked() && !mtt_avance.getText().toString().isEmpty()
                                && Double.compare(total_ttc, d) >= 0) {
                            facture.setStatut("avance");
                            facture.setMtt_avance(d);
                        }

                        if (facture.getNouveau()) {
                           /* parametre= ou.getParametre(parametre.getUser(),parametre.getMdp());
                            try {
                                Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(parametre.getDate_facture());
                                if(dt.compareTo(new Date())==0){
                                    facture.setRef("Fact"+parametre.getID_Facture());
                                    parametre.setID_Facture(parametre.getID_Facture()+1);
                                }else{
                                    facture.setRef("Fact1");
                                    parametre.setDate_facture(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                    parametre.setID_Facture(2);
                                    System.out.println("vloop");
                                }
                                ou.setParametre(parametre.getMdp(),parametre.getUser(),parametre.getDate_facture(),String.valueOf(parametre.getID_Facture()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
*/
                            String entete = ou.ajoutEnteteServeur(parametre.getCo_no(), facture.getId_client().getNum(), facture.getRef(), "1",(float)facture.getLatitude(),(float)facture.getLongitude());
                            facture.setEntete(entete);
                            for (int i = 0; i < facture.getListe_article().size(); i++) {
                                ArticleServeur article = facture.getListe_article().get(i);
                                ou.ajoutLigneServeur(entete, String.valueOf(facture.getListe_article().get(i).getAr_ref()), 10000 * i, article.getQte_vendue(), 0);
                            }
                            facture.setDO_Date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            liste_facture.add(facture);
                            facture.setNouveau(false);
                            facture.setTotalTTC(Integer.parseInt(t_ttc.getText().toString()));
                            String montant = "0";
                            if(comptant.isChecked())
                                montant=ttcformat.format(total_ttc);
                            else
                                if(!mtt_avance.getText().toString().equals(""))
                                    montant=mtt_avance.getText().toString();
                            ou.reglerEntete(facture.getEntete(), facture.getRef(),montant);
                        }
                        Intent intent = new Intent(ValideActivity.this, LstFactureActivity.class);
                        intent.putExtra("liste_facture", liste_facture);
                        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
                        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
                        intent.putExtra("outils", ou);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ValideActivity.this, "Choississez un mode de règlement.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ValideActivity.this, "le montant de l'avance ne peut pas être supérieur au montant TTC.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}