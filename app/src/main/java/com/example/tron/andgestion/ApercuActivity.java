package com.example.tron.andgestion;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.content.Context;
import android.widget.Button;

import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.facture.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ApercuActivity extends AppCompatActivity {


    private WebView myWebView;
    Parametre parametre;
    outils ou;
    ArrayList<Facture> liste_facture;
    Facture facture;

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apercu);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = ApercuActivity.this;
        parametre=(Parametre) getIntent().getSerializableExtra("parametre");
        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        facture = (Facture) getIntent().getSerializableExtra("facture");
        //if (savedInstanceState == null) {
        //    getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        //}

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

}