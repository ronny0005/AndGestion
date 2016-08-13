package com.example.tron.andgestion.bddlocal.fonction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.widget.Toast;


import com.example.tron.andgestion.Stock.BmqModele;
import com.example.tron.andgestion.Stock.Stock;
import com.example.tron.andgestion.Stock.StockEqVendeur;
import com.example.tron.andgestion.modele.Affaire;
import com.example.tron.andgestion.modele.ArticleServeur;
import com.example.tron.andgestion.modele.CompteA;
import com.example.tron.andgestion.modele.DatabaseSQLite;
import com.example.tron.andgestion.modele.Entete;
import com.example.tron.andgestion.modele.Ligne;
import com.example.tron.andgestion.modele.ReglementModele;
import com.example.tron.andgestion.modele.cReglement;
import com.example.tron.andgestion.modele.Caisse;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.Depot;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.modele.ManquantModele;
import com.example.tron.andgestion.modele.Parametre;
import com.example.tron.andgestion.modele.Souche;
import com.example.tron.andgestion.modele.Vehicule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.text.Html.escapeHtml;

/**
 * Created by T.Ron$ on 12/03/2016.
 */
public class outils implements Serializable{

    public static Activity app=null;
    public static String lien="http://192.168.1.14:8083/api/";
    public static DatabaseSQLite data;

    public static void demarreBase(Context context){
        data = new DatabaseSQLite(context);
        data.recreate();
    }
    public static Parametre connexion(String login,String mdp) {
        try{
            return getParametre(login,mdp);
        }catch (IOException e ){
            return data.getParametreWithUser(login,mdp);
        }
    }

   public static void commit(Parametre parametre){
		String entete="";
       // ArrayList<Entete> lentete=
        data.getEnteteWithDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ArrayList<Entete> lentete=
                data.getEnteteWithDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
         Entete b_entete = lentete.get(0);
        String old_entete=b_entete.getEntete();
        if(b_entete.getCommit().equals("false")) {
            try {
                entete = ajoutEnteteServeur(parametre.getCo_no(), data.getClientWithId(b_entete.getId_client()).getNum(), b_entete.getRef(), "1", Float.parseFloat(b_entete.getLatitude()),Float.parseFloat(b_entete.getLongitude()),b_entete.getDO_Date());
                b_entete.setCommit("oui");
                b_entete.setEntete(entete);
                data.updateEntete(b_entete.getEntete(), b_entete);
            } catch (IOException e) {
            }
            ArrayList<Ligne> lligne = data.getLigneWithId(old_entete);
            for (int i = 0; i < lligne.size(); i++) {
                Ligne article = lligne.get(i);
                try {
                    ajoutLigneServeur(entete, article.getAR_Ref(), 10000 * (i + 1), Integer.parseInt(article.getDL_Qte()), 0, "","");
                    Ligne l = lligne.get(i);
                    l.setEntete(entete);
                    data.updateLigne(String.valueOf(l.getId()), l);
                } catch (IOException e) {
                }
            }
            reglerEntete(b_entete.getEntete(), b_entete.getRef(), String.valueOf(b_entete
                    .getMtt_avance()));
        }
	}
	
    public static ArrayList<BmqModele> getBmq(int cono, String datedeb, String datefin) {
        JSONObject json = null;
        ArrayList<BmqModele> ldep = new ArrayList<BmqModele>();
        try {
            json = new JSONObject(getJsonFromServer("getBmqVendeur?depot="+cono+"&datedeb=" + datedeb + "&datefin=" + datefin));
            JSONArray jArray = json.getJSONArray("data");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new BmqModele(json_data.getDouble("PR"),json_data.getDouble("RECU"),json_data.getDouble("RETOUR"),json_data.getDouble("AVARI"),json_data.getDouble("VENDU"),json_data.getDouble("VALEUR"),json_data.getDouble("TVA"),json_data.getDouble("PRECOMPTE"),json_data.getDouble("REMISE"),json_data.getDouble("VENTE_TTC"),json_data.getDouble("MANQUANT"),json_data.getString("AR_Ref"),json_data.getString("AR_Design"),1,json_data.getInt("MARGE"),json_data.getDouble("avance")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static ArrayList<ManquantModele> getManquant(int cono,String datedeb,String datefin) {
        JSONObject json = null;
        ArrayList<ManquantModele> ldep = new ArrayList<ManquantModele>();
        try {
            json = new JSONObject(getJsonFromServer("manquantVendeur?collaborateur_deb="+cono+"&debut="+datedeb+"&fin="+datefin));
            JSONArray jArray = json.getJSONArray("data");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new ManquantModele(formatDate(convertirDate(json_data.getString("RG_Date"), "yyyy-MM-dd"), "dd/MM/yyyy"), json_data.getString("CT_NumPayeur"), json_data.getString("RG_Libelle"), json_data.getDouble("RG_Montant")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ldep);
        return ldep;
    }

    public static ArrayList<Stock> getStock(int de_no) {
        JSONObject json = null;
        ArrayList<Stock> ldep = new ArrayList<Stock>();
        ldep.add(new Stock("10111","ARTICLE",22,2000,20));
        try {
            json = new JSONObject(getJsonFromServer("stock?DE_No=" + de_no));
            JSONArray jArray = json.getJSONArray("data");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new Stock(json_data.getString("AR_Ref"), json_data.getString("AR_Design"), json_data.getInt("DE_No"), json_data.getDouble("AS_MontSto"),json_data.getDouble("AS_QteSto")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static Date convertirDate(String dateString, String dateFormat) {
        Date date = null;
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(dateFormat);
        try {
            date = formater.parse(dateString);
        } catch (ParseException ex) {
        }
        return date;
    }

    public static String formatDate(Date date, String pattern) {

        String d = null;
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(pattern);
        d = formater.format(date);
        return d;
    }

    public static String getJsonFromServer(String url) throws IOException {
        //try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.out.println(lien + url);
            InputStream is = (InputStream) new URL(lien+url).getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String result, line = reader.readLine();
            result = line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            return result;
       // }catch(IOException e){
         //   Toast.makeText(app,"Problème de connexion! Veuillez réessayer plus tard !", Toast.LENGTH_SHORT).show();
        //}
        //return "oups";
    }

    public static ArrayList<Depot> listeDepotServeur(){
        JSONObject json = null;
        ArrayList<Depot> ldep=null;
        try {
            String url = "depot";
            json = new JSONObject(getJsonFromServer(url));
            JSONArray jArray = json.getJSONArray("data");
            ldep= new ArrayList<Depot>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new Depot(json_data.getInt("DE_No"),json_data.getString("DE_Intitule")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }


    public static ArrayList<CompteA> listePlanCR(){
        JSONObject json = null;
        ArrayList<CompteA> ldep=null;
        try {
            String url = "getPlanCR";
            json = new JSONObject(getJsonFromServer(url));
            JSONArray jArray = json.getJSONArray("data");
            ldep= new ArrayList<CompteA>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                CompteA c = new CompteA();
                c.setCbMarq(json_data.getInt("cbMarq"));
                c.setCA_Achat(json_data.getDouble("CA_Achat"));
                c.setCA_Num(json_data.getString("CA_Num"));
                c.setCA_Intitule(json_data.getString("CA_Intitule"));
                c.setN_Analytique(json_data.getInt("n_Analytique"));
                c.setCA_Type(json_data.getInt("CA_Type"));
                c.setCA_Report(json_data.getInt("CA_Report"));
                c.setN_Analyse(json_data.getInt("n_Analyse"));
                c.setCA_Saut(json_data.getInt("CA_Saut"));
                ldep.add(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public boolean verifDate(String date){

        return false;
    }
    public static ArrayList<ArticleServeur> listeArticleDispo(String DE_No){
        JSONObject json = null;
        ArrayList<ArticleServeur> lart=null;
        try {
            json = new JSONObject(getJsonFromServer("getAllArticleDispoByArRef?DE_No=" + DE_No));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<ArticleServeur>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new ArticleServeur(json_data.getString("AR_Ref"),json_data.getString("AR_Design"),json_data.getDouble("AR_PrixAch"),json_data.getDouble("taxe1"),json_data.getDouble("taxe2"),json_data.getDouble("taxe3")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }


    public static String getVille(int num,String nom){
        if(num==1)
            return "DLA";
        if(num==2)
            return "YDE";
        if(num==3)
            return "BAF";
        if(num==6)
            return "NKO";
        if(num==5)
            return "KUM";
        if(num==8)
            return "BMD";
        if(num==7)
            return "BER";
        if(num==10)
            return "NGA";
        if(num==4)
            return "CO";
        if(num==11)
            return nom;
        return "";
    }

    public static ArrayList<Facture> listeFacture(int CO_No,String datedeb,String datefin,String numClient,String ville) {
        ArrayList<Facture> list = new ArrayList<Facture>();
        JSONObject json = null;
        try {
            json = new JSONObject(getJsonFromServer("getFactureCO?CO_No=" + CO_No + "&datedeb=" + datedeb + "&datefin=" + datefin + "&CT_Num=" + numClient));
            JSONArray jArray = json.getJSONArray("data");
            Facture facture = null;
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                facture = new Facture();
                facture.setNouveau(false);
                ArrayList<Client> lclient = listeClientServeur(ville);

                for (int c = 0; c < lclient.size(); c++)
                    if (lclient.get(c).getNum().compareTo(json_data.getString("CT_Num")) == 0)
                        facture.setId_client(lclient.get(c));
                facture.setStatut("");
                facture.setDO_Date(json_data.getString("DO_Date"));
                facture.setTotalTTC((int) Math.round(json_data.getDouble("ttc")));
                facture.setMtt_avance((int) Math.round(json_data.getDouble("avance")));
                if ((int) facture.getMtt_avance() >= (int) facture.getTotalTTC() && (int) facture.getMtt_avance() > 0) {
                    facture.setStatut("comptant");
                } else if ((int) facture.getMtt_avance() > 0)
                    facture.setStatut("avance");
                else
                    facture.setStatut("credit");
                facture.setRef(json_data.getString("DO_Ref"));
                facture.setEntete(json_data.getString("DO_Piece"));
                facture.setLatitude(Double.parseDouble(json_data.getString("latitude")));
                facture.setLongitude(Double.parseDouble(json_data.getString("longitude")));
                list.add(facture);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            
        }
        return list;
    }

    public static void LigneFacture(Facture facture) throws IOException, JSONException {
        JSONObject json = new JSONObject(getJsonFromServer("getLigneFacture?DO_Piece=" +facture.getEntete()));
        JSONArray jArrayFacture = json.getJSONArray("data");
        for (int j = 0; j < jArrayFacture.length(); j++) {
            JSONObject json_datafact = jArrayFacture.getJSONObject(j);
            ArticleServeur art = new ArticleServeur(json_datafact.getString("AR_Ref"), json_datafact.getString("DL_Design")
                    , json_datafact.getDouble("DL_PrixUnitaire"), json_datafact.getDouble("DL_Taxe1"), json_datafact.getDouble("DL_Taxe2"),
                    json_datafact.getDouble("DL_Taxe3"));
            art.setQte_vendue(json_datafact.getInt("DL_Qte"));
            art.setAr_prixven((float) json_datafact.getDouble("DL_PrixUnitaire"));
            facture.getListe_article().add(art);
        }
    }
    
    public static ArrayList<StockEqVendeur> eqStkVendeur(String depot,String date_deb,String date_fin) {
        JSONObject json = null;
        System.out.println(escapeHtml(depot));
        ArrayList<StockEqVendeur> ldep = null;
        try {
            String url = "getAllArticleDispoByArRef";
            json = new JSONObject(getJsonFromServer("getEqStock?depot="+depot+"&date_deb="+date_deb+"&date_fin="+date_fin));
            JSONArray jArray = json.getJSONArray("data");
            ldep= new ArrayList<StockEqVendeur>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new StockEqVendeur(json_data.getString("AR_Design"), json_data.getInt("STOCKS"), json_data.getInt("ENTREES"), json_data.getInt("RETOURS"), json_data.getInt("AVARIS"), json_data.getInt("STOCK_FINAL"), json_data.getInt("QTE_VENDUES"), json_data.getInt("STOCK_RESTANTS")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static ArrayList<ReglementModele> listeReglement(String date_deb, String date_fin,int co_no) {
        JSONObject json = null;
        ArrayList<ReglementModele> ldep = null;
        try {
            String url = "getAllArticleDispoByArRef";
            json = new JSONObject(getJsonFromServer("getReglement?CO_No="+co_no+"&datedeb="+date_deb+"&datefin="+date_fin));
            JSONArray jArray = json.getJSONArray("data");
            ldep= new ArrayList<ReglementModele>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new ReglementModele(json_data.getString("RG_No"), json_data.getString("RG_Date"), json_data.getString("CT_Intitule"), json_data.getString("RG_Libelle"), json_data.getDouble("montant")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static ArrayList<Client> listeClientServeur(String param){
        JSONObject json = null;
        ArrayList<Client> ldep=null;
        try {
//            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/client.html"));
            json = new JSONObject(getJsonFromServer("clients?op=" + param));
            JSONArray jArray = json.getJSONArray("data");
            ldep= new ArrayList<Client>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new Client(json_data.getString("CT_Intitule"),json_data.getString("CT_Num"), json_data.getString("CG_NumPrinc"), json_data.getInt("n_CatTarif"), json_data.getInt("n_CatCompta")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static ArrayList<ArticleServeur> listeArticleServeur(){
        JSONObject json = null;
        ArrayList<ArticleServeur> lart=null;
        try {
            json = new JSONObject(getJsonFromServer("getAllArticle"));
            //json = new JSONObject(getJsonFromServer("http://genzy.esy.es/article.html"));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<ArticleServeur>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new ArticleServeur(json_data.getString("AR_Ref"),json_data.getString("AR_Design"),json_data.getDouble("AR_PrixAch"),
                        json_data.getDouble("taxe1"),json_data.getDouble("taxe2"),json_data.getDouble("taxe3")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static int ajoutLigneServeur (String no_fac,String ref_fac,int no_ligne,int dl_qte,int dl_remise,String vehicule,String cr) throws IOException {
        JSONObject json = null;
        int qte=0;
        try {
            String res = "addDocligne?DO_Piece=" + no_fac + "&AR_Ref=" + ref_fac + "&DL_Ligne=" + no_ligne+"&DL_Qte=" + dl_qte+"&DL_Remise=" + dl_remise+"&vehicule=" + vehicule+"&cr=" + cr;
            json = new JSONObject(getJsonFromServer(res));
            JSONObject jArray = json.getJSONObject("data");
            qte = jArray.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return qte;
    }

    public static void ajoutVehicule(String intitule,String vehicule,String cr){
        JSONObject json = null;
        try {
            String res = "addCompteA?intitule="+intitule+"&vehicule="+vehicule+"&cr="+cr;
            json = new JSONObject(getJsonFromServer(res));
            JSONObject jArray = json.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SupprLigneServeur(String entete){
        JSONObject json = null;
        try {
            String res = "delLigne?DO_Piece=" +entete;
            json = new JSONObject(getJsonFromServer(res));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SupprEnteteServeur(String entete){
        JSONObject json = null;
        try {
            String res = "delEntete?DO_Piece=" +entete;
            json = new JSONObject(getJsonFromServer(res));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static cReglement UpdateEntete(String entete, String ref, int montant){
        JSONObject json = null;
        cReglement cr =null;
        try {
            String url="regleDocentete?DO_Piece="+entete+"&ref="+ref+"&avance="+montant;
            json = new JSONObject(getJsonFromServer(url));
            JSONObject jArray = json.getJSONObject("data");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cr;
    }

    public static void ModifLigneServeur(int idLigne,int qte){
        JSONObject json = null;
        try {
            String res = "modifLigne?CbMarq=" +idLigne+"&qte="+qte;
            json = new JSONObject(getJsonFromServer(res));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void reglerEntete(int idEntete,String ref){
        JSONObject json = null;
        ArrayList<ArticleServeur> lart=null;
        try {
            json = new JSONObject(getJsonFromServer("regleDocentete?idEntete="+idEntete+"&ref="+ref));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<ArticleServeur>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new ArticleServeur(json_data.getString("AR_Ref"),json_data.getString("AR_Design"),json_data.getDouble("AR_PrixAch"),json_data.getDouble("taxe1"),json_data.getDouble("taxe2"),json_data.getDouble("taxe3")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return lart;
    }

    public static ArrayList<String> listeArticleServeurTexte(){
        ArrayList<String> lart= new ArrayList<String>();
        ArrayList<ArticleServeur> art =listeArticleServeur();
        for(int i=0;i<art.size();i++)
            lart.add(art.get(i).getAr_design());
        return lart;
    }

    public static ArrayList<String> listeDepotServeurTexte(){
        ArrayList<String> ldep= new ArrayList<String>();
        ArrayList<Depot> dep =listeDepotServeur();
        for(int i=0;i<dep.size();i++)
            ldep.add(dep.get(i).getNom());
        return ldep;
    }

    public static ArrayList<Affaire> listeAffaireServeur(){
        JSONObject json = null;
        ArrayList<Affaire> lart=null;
        try {
//            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/affaire.html"));
            json = new JSONObject(getJsonFromServer("affaire"));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<Affaire>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new Affaire(json_data.getInt("CA_Num"),json_data.getString("CA_Intitule"),json_data.getInt("n_Analytique")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static ArrayList<String> listeAffaireTexte(){
        ArrayList<String> ldep= new ArrayList<String>();
        ArrayList<Affaire> dep =listeAffaireServeur();
        for(int i=0;i<dep.size();i++)
            ldep.add(dep.get(i).getCa_intitule());
        return ldep;
    }

    public static ArrayList<Vehicule> listeVehiculeServeur(){
        JSONObject json = null;
        ArrayList<Vehicule> lart=null;
        try {
//            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/vehicule.html"));
            json = new JSONObject(getJsonFromServer("vehicule"));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<Vehicule>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new Vehicule(json_data.getString("CA_Num"),json_data.getString("CA_Intitule"),json_data.getInt("n_Analytique")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static ArrayList<String> listeVehiculeTexte(){
        ArrayList<String> ldep= new ArrayList<String>();
        ArrayList<Vehicule> dep =listeVehiculeServeur();
        for(int i=0;i<dep.size();i++)
            ldep.add(dep.get(i).getCa_intitule());
        return ldep;
    }
    public static ArrayList<ArticleServeur> listePrixProduitClient(String ref,String cat_compta,String cat_tarif){
        JSONObject json = null;
        ArrayList<ArticleServeur> lart=null;
        try {
            json = new JSONObject(getJsonFromServer("getPrixClient?AR_Ref="+ref+"&N_CatTarif="+cat_tarif+"&N_CatCompta="+cat_compta));
            //json = new JSONObject(getJsonFromServer("http://genzy.esy.es/article.html"));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<ArticleServeur>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new ArticleServeur(json_data.getString("AR_Ref"),json_data.getString("AR_Design"),json_data.getDouble("AR_PrixAch"),json_data.getDouble("taxe1"),json_data.getDouble("taxe2"),json_data.getDouble("taxe3")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static ArrayList<Caisse> listeCaisseServeur(){
        JSONObject json = null;
        ArrayList<Caisse> lart=null;
        try {
            String url="caisse";
  //          String url="http://genzy.esy.es/caisse.html";
            json = new JSONObject(getJsonFromServer(url));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<Caisse>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new Caisse(json_data.getString("JO_Num"),json_data.getString("CA_Intitule"),json_data.getInt("CA_No"),json_data.getInt("CO_NoCaissier")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static int articleDisponibleServeur(String ref_art,int depot){
        JSONObject json = null;
        int qte=0;
        try {
            String url = "isStock?AR_Ref="+ref_art+"&DE_No="+depot;
            json = new JSONObject(getJsonFromServer(url));
            return json.getJSONObject("data").getInt("AS_QteSto");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(ref_art+" reecchhhh");
            return Integer.parseInt(data.getStockWithARRef(ref_art).getAS_QteSto());
        }
        return qte;
    }


    public static Parametre getParametre(String nomUser,String password) throws IOException{
        JSONObject json = null;
        int qte=0;
        try {
            String url="connect?NomUser="+nomUser+"&Password="+password;
            json = new JSONObject(getJsonFromServer(url));
            JSONObject ob =json.getJSONObject("data");
            ArrayList<Caisse> lcaisse = listeCaisseServeur();
            Caisse c =null;
            if(ob.getInt("id_parametre")!=0) {
                for (int i = 0; i < lcaisse.size(); i++)
                    if (lcaisse.get(i).getCa_no() == ob.getInt("CA_No"))
                        c = lcaisse.get(i);
                return new Parametre(ob.getInt("DE_No"), ob.getString("CT_Num"), ob.getInt("CO_No"), ob.getInt("DO_Souche"),
                        ob.getString("affaire"), ob.getString("numDoc"), ob.getString("vehicule"),
                        nomUser, password, c,ob.getString("date_facture"),ob.getInt("r_Facture"),ob.getInt("ID_Facture"));
            }else {
                Toast.makeText(app, "Login ou mot de passe incorrect",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Parametre changePassword(String login,String oldPwd ,String newPwd) throws IOException{
        JSONObject json = null;
        int qte=0;
        try {
            String url="changePwd?login="+login+"&oldPwd="+oldPwd+"&newPwd="+newPwd;
            json = new JSONObject(getJsonFromServer(url));
            JSONObject ob =json.getJSONObject("data");
            ArrayList<Caisse> lcaisse = listeCaisseServeur();
            Caisse c =null;
            if(ob.getInt("id_parametre")!=0) {
                for (int i = 0; i < lcaisse.size(); i++)
                    if (lcaisse.get(i).getCa_no() == ob.getInt("CA_No"))
                        c = lcaisse.get(i);
                return new Parametre(ob.getInt("DE_No"), ob.getString("CT_Num"), ob.getInt("CO_No"), ob.getInt("DO_Souche"),
                        ob.getString("affaire"), ob.getString("numDoc"), ob.getString("vehicule"),
                        login, newPwd, c,ob.getString("date_facture"),ob.getInt("r_Facture"),ob.getInt("ID_Facture"));
            }else {
                Toast.makeText(app, "Login ou mot de passe incorrect",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setParametre(String password,String nomUser,String date_Facture,String ID_Facture) {
        JSONObject json = null;
        try {
            String url="setParametre?NomUser="+nomUser+"&Password="+password+"&Date_facture="+date_Facture+"&ID_facture="+ID_Facture;
            System.out.println(url);
            json = new JSONObject(getJsonFromServer(url));
            JSONObject ob =json.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ajoutEnteteServeur(int co_no,String ct_num,String ref_fac,String reg,float lat,float lon,String date) throws IOException {
        JSONObject json = null;
        int qte=0;
        try {
            String url="addDocentete?CO_No="+co_no+"&CT_Num="+ct_num+"&ref="+ref_fac+"&N_Reglement="+reg+"&Latitude=" + lat+"&Longitude=" + lon+"&date=" + date;
            json = new JSONObject(getJsonFromServer(url));
            return json.getJSONObject("data").getString("DO_Piece");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPrixclient(String ref_art,int cat_tarif,int cat_compta,ArticleServeur art){
        JSONObject json = null;
        int qte=0;
        try {
            String url="getPrixClient?AR_Ref="+ref_art+"&N_CatTarif="+cat_tarif+"&N_CatCompta="+cat_compta;
            json = new JSONObject(getJsonFromServer(url));
            art.setAr_prixven((float) json.getJSONObject("data").getDouble("AR_PrixVen"));
            art.setAr_prixach((float) json.getJSONObject("data").getDouble("AR_PrixAch"));
            art.setTaxe1(json.getJSONObject("data").getDouble("taxe1"));
            art.setTaxe2(json.getJSONObject("data").getDouble("taxe2"));
            art.setTaxe3(json.getJSONObject("data").getDouble("taxe3"));
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static ArrayList<String> listeCaisseServeurTexte(){
        ArrayList<String> ldep= new ArrayList<String>();
        ArrayList<Caisse> dep =listeCaisseServeur();
        for(int i=0;i<dep.size();i++)
            ldep.add(dep.get(i).getCa_intitule());
        return ldep;
    }

    public static cReglement reglerEntete(String do_piece,String ref,String avance){
        JSONObject json = null;
        cReglement cr = null;
        try {
            String url="regleDocentete?DO_Piece="+do_piece+"&ref="+ref+"&avance="+avance;
            json = new JSONObject(getJsonFromServer(url));
            JSONObject jArray = json.getJSONObject("data");
            cr = new cReglement(jArray.getInt("RG_No"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cr;
    }

    public static void passeVariable(Intent intent,Activity act,ArrayList<Facture> lfact,Parametre parametre,outils ou,ArrayList<Facture> lrecouvrement,ArrayList<Client> lclient,ArrayList<ArticleServeur> lart){
        intent.putExtra("liste_facture", lfact);
        intent.putExtra("parametre", parametre);
        intent.putExtra("outils", ou);
        intent.putExtra("liste_recouvrement", lrecouvrement);
        intent.putExtra("liste_client", lclient);
        intent.putExtra("liste_article", lart);
        act.startActivity(intent);
    }

    public static void passeVariableCarburant(Intent intent,Activity act,ArrayList<Facture> lfact,Parametre parametre,outils ou,ArrayList<Facture> lrecouvrement,ArrayList<Client> lclient,ArrayList<ArticleServeur> lart,ArrayList<Vehicule> lvehicule,ArrayList<CompteA> lcr){
        intent.putExtra("liste_facture", lfact);
        intent.putExtra("parametre", parametre);
        intent.putExtra("outils", ou);
        intent.putExtra("liste_recouvrement", lrecouvrement);
        intent.putExtra("liste_client", lclient);
        intent.putExtra("liste_article", lart);
        intent.putExtra("liste_vehicule", lvehicule);
        intent.putExtra("liste_cr", lcr);
        act.startActivity(intent);
    }

    public static cReglement addReglement(String client,String ref,String avance,String co_no,String CA_No,String date){
        JSONObject json = null;
        cReglement cr = null;
        try {
            String url="addReglement?client="+client+"&libelle="+ref+"&avance="+avance+"&CO_No="+co_no+"&CA_No="+CA_No+"&date="+date;
            json = new JSONObject(getJsonFromServer(url));
            JSONObject jArray = json.getJSONObject("data");
            cr = new cReglement(jArray.getInt("RG_No"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cr;
    }

    public static void addEcheance(String cr,String avance,String do_piece,String dr){
        JSONObject json = null;
        try {
            String url="addEcheance?cr_no="+cr+"&montant="+avance+"&do_piece="+do_piece+"&dr="+dr;
            getJsonFromServer(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateRgImpute(String cr){
        JSONObject json = null;
        try {
            String url="updateRgImpute?RG_No="+cr;
            getJsonFromServer(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Souche> listeSoucheServeur(){
        JSONObject json = null;
        ArrayList<Souche> lart=null;
        try {
            json = new JSONObject(getJsonFromServer("souche"));
            //json = new JSONObject(getJsonFromServer("http://genzy.esy.es/souche.html"));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<Souche>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new Souche(json_data.getString("JO_Num"),json_data.getString("JO_NumSituation"),json_data.getString("s_Intitule"),json_data.getInt("s_Valide"),json_data.getInt("cbIndice"),json_data.getInt("cbMarq")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static ArrayList<String> listeSoucheServeurTexte(){
        ArrayList<String> ldep= new ArrayList<String>();
        ArrayList<Souche> dep =listeSoucheServeur();
        for(int i=0;i<dep.size();i++)
            ldep.add(dep.get(i).getS_intitule());
        return ldep;
    }


}
