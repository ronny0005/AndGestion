package com.example.tron.andgestion;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.Entete;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Ligne;
import com.example.tron.andgestion.modele.Parametre;
import com.example.tron.andgestion.modele.QteStock;
import com.zj.usbsdk.PrintPic;
import com.zj.usbsdk.UsbController;

import java.io.IOException;
import java.text.DecimalFormat;
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

    private int[][] u_infor;
    UsbController usbCtrl = null;
    UsbDevice dev = null;
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
    private String ref;

    public void active_avance(boolean active){
        if(active) {
            mtt_avance.setEnabled(true);
            mtt_avance.setText("");
        }else {
            mtt_avance.setEnabled(false);
            mtt_avance.setText(" 0");
        }
    }

    private final  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbController.USB_CONNECTED:
                    Toast.makeText(getApplicationContext(), "getpermission",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private void createWebPrintJob(WebView webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

            PrintDocumentAdapter printAdapter =
                    webView.createPrintDocumentAdapter();

            String jobName = getString(R.string.app_name) + " Print Test";

            printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
        }
    }


    public void calcule(boolean press){
        DecimalFormat decim = new DecimalFormat("#.##");
        Double d;
        if (mtt_avance.getText().toString().equals(""))
            d = Double.MIN_VALUE;
        else d = Double.parseDouble(mtt_avance.getText().toString());

        if (Double.compare(total_ttc, d) >= 0) {
            if (comptant.isChecked() || credit.isChecked()) {
                if (credit.isChecked() && !mtt_avance.getText().toString().isEmpty() && Double.compare(total_ttc, d) >= 0) {
                    facture.setStatut("avance");
                    facture.setMtt_avance(d);
                }

                if (facture.getNouveau()) {
                    String montant = "0";
                    if(comptant.isChecked())
                        montant=ttcformat.format(total_ttc);
                    else
                    if(!mtt_avance.getText().toString().equals(""))
                        montant=mtt_avance.getText().toString();
                    String nouv="";
                    if(facture.getNouveau()==true)
                        nouv="true";
                    else
                        nouv="false";
                    String entete="";
                    Entete b_entete = new Entete(facture.getRef(), facture.getEntete(),new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                            facture.getId_client().getNum(),nouv,facture.getStatut(),facture.getType_paiement(),
                            montant, String.valueOf(facture.getLatitude()),String.valueOf(facture.getLongitude()),t_ttc.getText().toString());
                    try {
                        entete = ou.ajoutEnteteServeur(parametre.getCo_no(), facture.getId_client().getNum(), facture.getRef(), "1",(float)facture.getLatitude(),(float)facture.getLongitude(),facture.getDO_Date());
                        facture.setEntete(entete);
                        b_entete.setCommit("oui");
                        b_entete.setEntete(entete);
            //            ou.data.insertEntete(b_entete);
                    }catch(IOException e){
                        b_entete.setEntete(ref);
                        b_entete.setCommit("non");
						facture.setCommit(false);
            //            ou.data.insertEntete(b_entete);
                    }

                    for (int i = 0; i < facture.getListe_article().size(); i++) {
                        ArticleServeur article = facture.getListe_article().get(i);
                        Ligne ligne = new Ligne(b_entete.getEntete(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), article.getAr_ref(), article.getAr_design(),String.valueOf(article.getQte_vendue()),String.valueOf(article.getAr_prixven()),String.valueOf(article.getTaxe1())
                                , String.valueOf(article.getTaxe2()),String.valueOf(article.getTaxe3()),"", i+"0000",String.valueOf(article.getAr_prixven()));
                        try{
                            ou.ajoutLigneServeur(entete, String.valueOf(facture.getListe_article().get(i).getAr_ref()), 10000 * (i+1), article.getQte_vendue(), 0,facture.getVehicule(),facture.getCr());
                            ligne.setEntete(entete);
            //                ou.data.insertLigne(ligne);
                        }catch(IOException e){
            //                ou.data.insertLigne(ligne);
            //                ligne.setEntete(ref);
            //                QteStock stock = ou.data.getStockWithARRef(article.getAr_ref());
            //                stock.setAS_QteSto(String.valueOf(Integer.parseInt(stock.getAS_QteSto())-article.getQte_vendue()));
            //                ou.data.updateStock(article.getAr_ref(),stock);
                        }
                    }
                    facture.setDO_Date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    liste_facture.add(facture);
                    facture.setNouveau(false);
                    facture.setTotalTTC(Integer.parseInt(t_ttc.getText().toString()));
                    montant = "0";
                    if(comptant.isChecked()) {
                        montant = ttcformat.format(total_ttc);
                        //ou.reglerEntete(facture.getEntete(), facture.getRef(), montant);
                    }
                        else
                        if (!mtt_avance.getText().toString().equals(""))
                            montant = mtt_avance.getText().toString();
                        if (facture.getNouveau() == true)
                            nouv = "true";
                        else
                            nouv = "false";
                        if (!mtt_avance.getText().toString().equals(""))
                            ou.reglerEntete(facture.getEntete(), facture.getRef(), montant);
                   }
                Intent intent = new Intent(ValideActivity.this, LstFactureActivity.class);
                intent.putExtra("liste_facture", liste_facture);
                intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
                intent.putExtra("liste_recouvrement", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_recouvrement"));
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
        if(press)
            imprime();
    }
    public void imprime(){
        String htmlDocument =   "<html><body><h3>CAMLAIT S.A.</h3>" +
                "tel : 237 33400093<br/>" +
                "bp : BP 1838 DOUALA <br/>" +
                "vendeur : "+parametre.getUser()+"<br/>";
        htmlDocument+="Fact N = "+facture.getEntete()+"<br/>";
        htmlDocument+=facture.getDO_Date()+"<br/>";
        htmlDocument+="Client = ("+facture.getId_client().getIntitule()+")<br/><br/>";
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
            htmlDocument += "<tr><td>" + article.getAr_design() + "</td></tr><tr><td style=\"float:right\">" +
                    article.getAr_prixven() + " x " + article.getQte_vendue() + " = "+prix+"</td></tr>";
        }

        total_ttc = total_ht + total_tva + total_precompte + total_marge;

        htmlDocument +="<tr><td><br/></td></tr>";

        htmlDocument +="<tr><td>Total HT : "+ttcformat.format(total_ht)+"</td></tr>";
        htmlDocument +="<tr><td>TVA : "+ttcformat.format(total_tva)+"</td></tr>";
        htmlDocument +="<tr><td>Précompte : "+ttcformat.format(total_precompte)+"</td></tr>";
        htmlDocument +="<tr><td>Acompte : "+ttcformat.format(facture.getMtt_avance())+"</td></tr>";
        htmlDocument +="<tr><td>Total TTC : "+ttcformat.format(total_ttc)+"</td></tr>";
        htmlDocument +="<tr><td>Montant payé : "+ttcformat.format(facture.getMtt_avance())+"</td></tr>";
        htmlDocument +="<tr><td>Reste à payer : "+ ttcformat.format((total_ttc-facture.getMtt_avance())) +"</td></tr>";
        htmlDocument +="<tr><td>-----------------</td></tr>";
        htmlDocument +="<tr><td>Nous vous remercions<br/> de votre fidélité</td></tr>";
        htmlDocument+="</table>";
        htmlDocument+= "</body></html>";
        byte isHasPaper;
        usbCtrl = new UsbController(this,mHandler);
        u_infor = new int[6][2];
        u_infor[0][0] = 0x1CBE;
        u_infor[0][1] = 0x0003;
        u_infor[1][0] = 0x1CB0;
        u_infor[1][1] = 0x0003;
        u_infor[2][0] = 0x0483;
        u_infor[2][1] = 0x5740;
        u_infor[3][0] = 0x0493;
        u_infor[3][1] = 0x8760;
        u_infor[4][0] = 0x0416;
        u_infor[4][1] = 0x5011;
        u_infor[5][0] = 0x0416;
        u_infor[5][1] = 0xAABB;
        usbCtrl.close();
        int  i = 0;
        for( i = 0 ; i < 6 ; i++ ){
            dev = usbCtrl.getDev(u_infor[i][0],u_infor[i][1]);
            if(dev != null)
                break;
        }
        if( dev != null ){
            if( !(usbCtrl.isHasPermission(dev))){
                usbCtrl.getPermission(dev);
            }else{
                Toast.makeText(getApplicationContext(), "permission",
                        Toast.LENGTH_SHORT).show();
            }
        }
        isHasPaper = usbCtrl.revByte(dev);
        if( isHasPaper == 0x38 ){
            Toast.makeText(getApplicationContext(), "The printer has no paper",
                    Toast.LENGTH_SHORT).show();
            return;
        }
            if( CheckUsbPermission() == true ){
                usbCtrl.sendMsg(htmlDocument, "GBK", dev);
            }
        }

    private void printImage() {
      /*  int i = 0,s = 0,j = 0,index = 0;
        byte[] temp = new byte[56];
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(384);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/icon.jpg");
        sendData = pg.printDraw();

        for( i = 0 ; i < pg.getLength() ; i++ ){
            s = 0;
            temp[s++] = 0x1D;
            temp[s++] = 0x76;
            temp[s++] = 0x30;
            temp[s++] = 0x00;
            temp[s++] = (byte)(pg.getWidth() / 8);
            temp[s++] = 0x00;
            temp[s++] = 0x01;
            temp[s++] = 0x00;
            for( j = 0 ; j < (pg.getWidth() / 8) ; j++ )
                temp[s++] = sendData[index++];
            usbCtrl.sendByte(temp, dev);
        }*/
    }

    public boolean CheckUsbPermission(){
        if( dev != null ){
            if( usbCtrl.isHasPermission(dev)){
                return true;
            }
        }
        Toast.makeText(getApplicationContext(), "connexion",
                Toast.LENGTH_SHORT).show();
        return false;
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
        ref="AND000"+liste_facture.size();
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
               calcule(true);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               calcule(false);
            }
        });
    }
}