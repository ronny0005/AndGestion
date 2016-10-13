package com.example.tron.andgestion;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.zj.btsdk.BluetoothService;
import com.zj.usbsdk.PrintPic;
import com.zj.usbsdk.UsbController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    private static final int REQUEST_ENABLE_BT = 2;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;
    private static final int REQUEST_CONNECT_DEVICE = 1;
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
    Button imprime_v;
    int id_facture;
    String htmlDocument;
    outils ou = new outils();
    Parametre parametre;
    ArrayList<ArticleServeur> liste_article;
    Facture facture;
    private DecimalFormat decim = new DecimalFormat("#.##");
    private DecimalFormat ttcformat = new DecimalFormat("#");
    private WebView myWebView;
    private String ref;
    String valblue="";

    public void active_avance(boolean active){
        if(active) {
            mtt_avance.setEnabled(true);
            mtt_avance.setText("");
        }else {
            mtt_avance.setEnabled(false);
            mtt_avance.setText(" 0");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        /*if( mService.isBTopen() == false)
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        try {

            imprime_v = (Button) findViewById(R.id.imprime_v);
            imprime_v.setOnClickListener(new ClickEvent());

        } catch (Exception ex) {
            Log.e("³ö´íÐÅÏ¢",ex.getMessage());
        }*/
    }

    private final  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(getApplicationContext(), "Connect successful",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("STATE_CONNECTED","STATE_CONNECTED");
                            valide_impression();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Log.d("STATE_CONNECTING","STATE_CONNECTING");
                            break;
                        case BluetoothService.STATE_LISTEN:
                            Log.d("STATE_LISTEN","STATE_LISTEN");
                            break;
                        case BluetoothService.STATE_NONE:
                            Log.d("STATE_NONE","STATE_NONE");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:
                    Toast.makeText(getApplicationContext(), "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:
                    Toast.makeText(getApplicationContext(), "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    public void calcule(boolean press){
        DecimalFormat decim = new DecimalFormat("#.##");
        Double d;
        if (mtt_avance.getText().toString().equals(""))
            d = Double.MIN_VALUE;
        else d = Double.parseDouble(mtt_avance.getText().toString());

        if (Double.compare(total_ttc, d) >= 0 || !facture.getNouveau()) {
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
                    }catch(IOException e){
                    }

                    for (int i = 0; i < facture.getListe_article().size(); i++) {
                        ArticleServeur article = facture.getListe_article().get(i);
                        for(int j=0;j<liste_article.size();j++)
                            if(liste_article.get(j).getAr_ref().equals(article.getAr_ref()))
                                liste_article.get(j).getQteStock().setAS_QteSto(ou.articleDisponibleServeur(article.getAr_ref(), parametre.getDe_no()));
                        Ligne ligne = new Ligne(b_entete.getEntete(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), article.getAr_ref(), article.getAr_design(),String.valueOf(article.getQte_vendue()),String.valueOf(article.getAr_prixven()),String.valueOf(article.getTaxe1())
                                , String.valueOf(article.getTaxe2()),String.valueOf(article.getTaxe3()),"", i+"0000",String.valueOf(article.getAr_prixven()));
                        try{
                            ou.ajoutLigneServeur(entete, String.valueOf(facture.getListe_article().get(i).getAr_ref()), 10000 * (i+1), article.getQte_vendue(), 0,facture.getVehicule(),facture.getCr());
                            ligne.setEntete(entete);

                        }catch(IOException e){
                        }
                    }
                    facture.setDO_Date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    liste_facture.add(facture);
                    facture.setNouveau(false);
                    facture.setTotalTTC(Integer.parseInt(t_ttc.getText().toString()));
                    montant = "0";
                    if(comptant.isChecked()) {
                        montant = ttcformat.format(total_ttc);
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
                intent.putExtra("liste_article", liste_article);
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
        htmlDocument =   "CAMLAIT S.A. \n" +
                "tel : 237 33400093 \n" +
                "bp : BP 1838 DOUALA \n" +
                "vendeur : "+parametre.getUser()+"\n";
        htmlDocument+="Fact N = "+facture.getEntete()+"\n";
        htmlDocument+=facture.getDO_Date()+"\n";
        htmlDocument+="Client = ("+facture.getId_client().getIntitule()+")\n\n";
        htmlDocument+="";
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
            htmlDocument +=  article.getAr_design() + "\n" +
                    article.getAr_prixven() + " x " + article.getQte_vendue() + " = "+prix+"\n";
        }

        total_ttc = total_ht + total_tva + total_precompte + total_marge;

        htmlDocument +="\n Total HT : "+ttcformat.format(total_ht)+"\n";
        htmlDocument +="TVA : "+ttcformat.format(total_tva)+"\n";
        htmlDocument +="Precompte : "+ttcformat.format(total_precompte)+"\n";
        htmlDocument +="Acompte : "+ttcformat.format(facture.getMtt_avance())+"\n";
        htmlDocument +="Total TTC : "+ttcformat.format(total_ttc)+"\n";
        htmlDocument +="Montant payer : "+ttcformat.format(facture.getMtt_avance())+"\n";
        htmlDocument +="Reste a payer : "+ ttcformat.format((total_ttc-facture.getMtt_avance())) +"\n";
        htmlDocument +="-----------------\n";
        htmlDocument +="Nous vous remercions\n de votre fidelite\n";

        }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            if (v == imprime_v) {
                if(valblue.equals("") ) {
                    Intent serverIntent = new Intent(ValideActivity.this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                    imprime_v.setEnabled(false);
                    valider.setEnabled(false);
                }else {
                    valide_impression();
                }
            }
        }
    }

    public void valide_impression(){
        calcule(true);
        mService.sendMessage(htmlDocument + "\n", "UTF-8");
        mService.stop();
        Intent intent = new Intent(ValideActivity.this, LstFactureActivity.class);
        intent.putExtra("liste_facture", liste_facture);
        intent.putExtra("parametre", (Parametre) getIntent().getSerializableExtra("parametre"));
        intent.putExtra("device_address", valblue);
        intent.putExtra("liste_recouvrement", (ArrayList<Facture>) getIntent().getSerializableExtra("liste_recouvrement"));
        intent.putExtra("liste_article", (ArrayList<ArticleServeur>) getIntent().getSerializableExtra("liste_article"));
        intent.putExtra("liste_client", (ArrayList<Client>) getIntent().getSerializableExtra("liste_client"));
        intent.putExtra("outils", ou);
        startActivity(intent);
    }
    public void bloqueBox(){
        if(!facture.getNouveau()) {
            credit.setEnabled(false);
            comptant.setEnabled(false);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth open successful", Toast.LENGTH_LONG).show();
                } else {
                   // finish();
                }
                break;
            case  REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    con_dev = mService.getDevByMac(address);
                    mService.connect(con_dev);
                    valblue=address;
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valide_facture);
        mService = new BluetoothService(this, mHandler);
        if( mService.isAvailable() == false ){
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            //finish();
        }
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

        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               calcule(false);
            }
        });
    }
}