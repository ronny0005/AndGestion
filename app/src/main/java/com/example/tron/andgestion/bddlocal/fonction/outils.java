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
import com.example.tron.andgestion.modele.Caisse;
import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.CompteA;
import com.example.tron.andgestion.modele.DatabaseSQLite;
import com.example.tron.andgestion.modele.Depot;
import com.example.tron.andgestion.modele.Entete;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.modele.Ligne;
import com.example.tron.andgestion.modele.ManquantModele;
import com.example.tron.andgestion.modele.Parametre;
import com.example.tron.andgestion.modele.PrixClient;
import com.example.tron.andgestion.modele.ReglementModele;
import com.example.tron.andgestion.modele.Souche;
import com.example.tron.andgestion.modele.Vehicule;
import com.example.tron.andgestion.modele.cReglement;

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
    public static String lien = "http://192.168.1.14:8083/?";
    public static DatabaseSQLite data;

    public static void demarreBase(Context context){
        data = new DatabaseSQLite(context);
        data.recreate();
    }
    public static Parametre connexion(String login,String mdp) {
        try{
            return getParametre(login,mdp);
        }catch (IOException e ){
//            return data.getParametreWithUser(login,mdp);
        }
        return null;
    }


    public static ArrayList<BmqModele> getBmq(int cono, String datedeb, String datefin) {
        JSONArray json = null;
        ArrayList<BmqModele> ldep = new ArrayList<BmqModele>();
        try {
            json = new JSONArray(getJsonFromServerNouveau("page=getBmq&depot="+cono+"&date_deb=" + datedeb + "&date_fin=" + datefin));
            for (int i = 0; i < json.length(); i++) {
                JSONObject json_data = json.getJSONObject(i);
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
        JSONArray json = null;
        ArrayList<ManquantModele> ldep = new ArrayList<ManquantModele>();
        try {
            json = new JSONArray(getJsonFromServerNouveau("page=getManquantVendeur&CT_Num="+cono+"&date_deb="+datedeb+"&date_fin="+datefin));
            for (int i = 0; i < json.length(); i++) {
                JSONObject json_data = json.getJSONObject(i);
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
        JSONArray json = null;
        ArrayList<Stock> ldep = new ArrayList<Stock>();
        try {
            json = new JSONArray(getJsonFromServerNouveau("page=getStock&DE_No=" + de_no));
            for (int i = 0; i < json.length(); i++) {
                JSONObject json_data = json.getJSONObject(i);
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
        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
        try {
            date = formater.parse(dateString);
        } catch (ParseException ex) {
        }
        return date;
    }

    public static String formatDate(Date date, String pattern) {

        String d = null;
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
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

    public static String getJsonFromServerNouveau(String url) throws IOException {
        //try {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        System.out.println(lien+ url);
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
        JSONArray json = null;
        ArrayList<Depot> ldep=null;
        try {
            String url = "page=depot";
            json = new JSONArray(getJsonFromServerNouveau(url));
            ldep= new ArrayList<Depot>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
                ldep.add(new Depot(json_data.getInt("DE_No"),json_data.getString("DE_Intitule")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static ArrayList<Depot> listeDepotVendeurServeur(int param){
        JSONArray json = null;
        ArrayList<Depot> ldep=null;
        try {
            String url = "page=getDepotVendeur&DE_No="+param;
            json = new JSONArray(getJsonFromServerNouveau(url));
            ldep= new ArrayList<Depot>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
                ldep.add(new Depot(json_data.getInt("DE_No"),json_data.getString("DE_Intitule")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static ArrayList<Depot> listeDepotAvarieServeur(int param){
        JSONArray json = null;
        ArrayList<Depot> ldep=null;
        try {
            String url = "page=getDepotAvarie&DE_No="+param;
            json = new JSONArray(getJsonFromServerNouveau(url));
            ldep= new ArrayList<Depot>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
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
        JSONArray json = null;
        ArrayList<CompteA> ldep=null;
        try {
            String url = "page=getPlanCR";
            json = new JSONArray(getJsonFromServerNouveau(url));
            ldep= new ArrayList<CompteA>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
                CompteA c = new CompteA();
                c.setCbMarq(json_data.getInt("cbMarq"));
                c.setCA_Achat(json_data.getDouble("CA_Achat"));
                c.setCA_Num(json_data.getString("CA_Num"));
                c.setCA_Intitule(json_data.getString("CA_Intitule"));
                c.setN_Analytique(json_data.getInt("N_Analytique"));
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
        JSONArray json = null;
        ArrayList<ArticleServeur> lart=null;
        try {
            json = new JSONArray(getJsonFromServerNouveau("page=getAllArticleDispoByArRef&DE_No=" + DE_No));
            lart= new ArrayList<ArticleServeur>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
                ArticleServeur article =new ArticleServeur(json_data.getString("AR_Ref"),json_data.getString("AR_Design"),json_data.getDouble("AR_PrixAch"),0,0,0);
                article.setAr_prixven((float) json_data.getDouble("AR_PrixVen"));
                lart.add(article);
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

    public static ArrayList<Client> listeClientServeur(String param){
        JSONArray json = null;
        ArrayList<Client> ldep=null;
        try {
//            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/client.html"));
            json = new JSONArray(getJsonFromServerNouveau("page=clients&op=" + param));
            ldep= new ArrayList<Client>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
                ldep.add(new Client(json_data.getString("CT_Intitule"),json_data.getString("CT_Num"), json_data.getString("CG_NumPrinc"), json_data.getInt("N_CatTarif"), json_data.getInt("N_CatCompta")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static ArrayList<Facture> listeFacture(int CO_No,String datedeb,String datefin,String numClient,String ville,ArrayList<Client> listClient) {
        ArrayList<Facture> list = new ArrayList<Facture>();
        JSONArray json = null;
        try {
            json = new JSONArray(getJsonFromServerNouveau("page=getFacture&CO_No=" + CO_No + "&datedeb=" + datedeb + "&datefin=" + datefin + "&CT_Num=" + numClient));
            Facture facture = null;
            for (int i = 0; i < json.length(); i++) {
                JSONObject json_data = json.getJSONObject(i);
                facture = new Facture();
                facture.setNouveau(false);
                ArrayList<Client> lclient;
                if(listClient!=null)
                    lclient= listClient;
                else
                    lclient= listeClientServeur(ville);
                for (int c = 0; c < lclient.size(); c++)
                    if (lclient.get(c).getNum().compareTo(json_data.getString("CT_Num")) == 0)
                        facture.setId_client(lclient.get(c));
                facture.setStatut("");
                facture.setDO_Date(json_data.getString("DO_Date"));
                facture.setTotalTTC((int) Math.round(json_data.getDouble("ttc")));
                if(json_data.getString("avance").equals("null"))
                    facture.setMtt_avance(0);
                else
                    facture.setMtt_avance((int) Math.round(json_data.getDouble("avance")));
                // +10 Arrondi sage
                if (((int) facture.getMtt_avance()+10) >= (int) facture.getTotalTTC() && (int) facture.getMtt_avance() > 0) {
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

    public static ArrayList<Facture> listeFactureClient(int CO_No,String datedeb,String datefin,String numClient,String ville,ArrayList<Client> listClient) {
        ArrayList<Facture> list = new ArrayList<Facture>();
        JSONArray json = null;
        try {
            json = new JSONArray(getJsonFromServerNouveau("page=getFactureCLient&CO_No=" + CO_No + "&datedeb=" + datedeb + "&datefin=" + datefin + "&CT_Num=" + numClient));
            Facture facture = null;
            for (int i = 0; i < json.length(); i++) {
                JSONObject json_data = json.getJSONObject(i);
                facture = new Facture();
                facture.setNouveau(false);
                ArrayList<Client> lclient;
                if(listClient!=null)
                    lclient= listClient;
                else
                    lclient= listeClientServeur(ville);
                for (int c = 0; c < lclient.size(); c++)
                    if (lclient.get(c).getNum().compareTo(json_data.getString("CT_Num")) == 0)
                        facture.setId_client(lclient.get(c));
                facture.setStatut("");
                facture.setDO_Date(json_data.getString("DO_Date"));
                facture.setTotalTTC((int) Math.round(json_data.getDouble("ttc")));
                if(json_data.getString("avance").equals("null"))
                    facture.setMtt_avance(0);
                else
                    facture.setMtt_avance((int) Math.round(json_data.getDouble("avance")));
                // +10 Arrondi sage
                if (((int) facture.getMtt_avance()+10) >= (int) facture.getTotalTTC() && (int) facture.getMtt_avance() > 0) {
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
        JSONArray json = new JSONArray(getJsonFromServerNouveau("page=getLigneFacture&DO_Piece=" +facture.getEntete()));
        for (int j = 0; j < json.length(); j++) {
            JSONObject json_datafact = json.getJSONObject(j);
            ArticleServeur art = new ArticleServeur(json_datafact.getString("AR_Ref"), json_datafact.getString("DL_Design")
                    , json_datafact.getDouble("DL_PrixUnitaire"), json_datafact.getDouble("DL_Taxe1"), json_datafact.getDouble("DL_Taxe2"),
                    json_datafact.getDouble("DL_Taxe3"));
            art.setQte_vendue(json_datafact.getInt("DL_Qte"));
            art.setAr_prixven((float) json_datafact.getDouble("DL_PrixUnitaire"));
            facture.getListe_article().add(art);
            System.out.println(art);
        }
    }

    public static ArrayList<StockEqVendeur> eqStkVendeur(String depot,String date_deb,String date_fin) {
        JSONArray json = null;
        ArrayList<StockEqVendeur> ldep = null;
        try {
            String url = "getAllArticleDispoByArRef";
            json = new JSONArray(getJsonFromServerNouveau("page=getEqStock&depot="+depot+"&date_deb="+date_deb+"&date_fin="+date_fin));
            ldep= new ArrayList<StockEqVendeur>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
                ldep.add(new StockEqVendeur(json_data.getString("AR_Design"), json_data.getInt("STOCKS"), json_data.getInt("ENTREES"), json_data.getInt("RETOURS"), json_data.getInt("AVARIS"), json_data.getInt("STOCK_FINAL"), json_data.getInt("QTE_VENDUES"), json_data.getInt("STOCK_RESTANTS")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ldep;
    }

    public static int ajoutLigneServeur (String AR_Ref,String mvtStock,String DL_Qte,String CT_Num,String DO_Piece,String DE_No,String CA_Num,String vehicule,String cr) throws IOException {
        JSONArray json = null;
        int qte=0;
        try {
            String res = "page=addDocligneMagasin&AR_Ref="+AR_Ref+"&MvtStock="+mvtStock+"&DL_Qte=" + DL_Qte + "&CT_Num=" + CT_Num + "&DO_Piece=" + DO_Piece+"&DE_No=" + DE_No+"&CA_Num=" + CA_Num+"&vehicule=" + vehicule+"&cr=" + cr;
            json = new JSONArray(getJsonFromServerNouveau(res));
            qte = ((JSONObject)json.get(0)).getInt("cbMarq");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return qte;
    }

    public static void updateArtStock (String DE_No,String AR_Ref,String DL_Qte,String Montant) throws IOException {
        JSONArray json = null;
        try {
            String res = "page=updateArtStock&DE_No="+DE_No+"&AR_Ref="+AR_Ref+"&DL_Qte=" + DL_Qte + "&Montant=" + Montant;
            json = new JSONArray(getJsonFromServerNouveau(res));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int ajoutLigneRetourServeur (String no_fac,String ref_fac,int no_ligne,int dl_qte,int dl_remise,String vehicule,String cr) throws IOException {
        JSONObject json = null;
        int qte=0;
        try {
            String res = "addDocligneMagasin?DO_Piece=" + no_fac + "&AR_Ref=" + ref_fac + "&DL_Ligne=" + no_ligne+"&DL_Qte=" + dl_qte+"&DL_Remise=" + dl_remise+"&vehicule=" + vehicule+"&cr=" + cr;
            json = new JSONObject(getJsonFromServer(res));
            JSONObject jArray = json.getJSONObject("data");
            qte = jArray.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return qte;
    }

    public static ArrayList<Vehicule> listeVehiculeServeur(){
        JSONArray json = null;
        ArrayList<Vehicule> lart=null;
        try {
            json = new JSONArray(getJsonFromServerNouveau("page=vehicule"));
            lart= new ArrayList<Vehicule>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
                lart.add(new Vehicule(json_data.getString("CA_Num"),json_data.getString("CA_Intitule"),json_data.getInt("N_Analytique")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static ArrayList<Caisse> listeCaisseServeur(){
        JSONArray json = null;
        ArrayList<Caisse> lart=null;
        try {
            String url="page=caisse";
            json = new JSONArray(getJsonFromServerNouveau(url));
            lart= new ArrayList<Caisse>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
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
        JSONArray json = null;
        int qte=0;
        try {
            String url = "page=isStock&AR_Ref="+ref_art+"&DE_No="+depot;
            json = new JSONArray(getJsonFromServerNouveau(url));
            return (int) ((JSONObject)json.get(0)).getInt("AS_QteSto");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return data.getStockWithARRef(ref_art).getAS_QteSto();
        }
        return qte;
    }


    public static Parametre getParametre(String nomUser,String password) throws IOException{
        JSONArray json = null;
        int qte=0;
        try {
            String url="page=connect&NomUser="+nomUser+"&Password="+password;
            json = new JSONArray(getJsonFromServerNouveau(url));
            JSONObject ob =null;
            if(json.length()>0) {
                ob = (JSONObject) json.get(0);
                ArrayList<Caisse> lcaisse = listeCaisseServeur();
                Caisse c = null;
                if (ob.getInt("id_parametre") != 0) {
                    for (int i = 0; i < lcaisse.size(); i++)
                        if (lcaisse.get(i).getCa_no() == ob.getInt("CA_No"))
                            c = lcaisse.get(i);
                    return new Parametre(ob.getInt("DE_No"), ob.getString("CT_Num"), ob.getInt("CO_No"), ob.getInt("DO_Souche"),
                            ob.getString("Affaire"), ob.getString("NumDoc"), ob.getString("Vehicule"),
                            nomUser, password, c, ob.getString("Date_Facture"), ob.getInt("R_Facture"), ob.getInt("ID_Facture"));
                } else {
                    Toast.makeText(app, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
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

    public static String ajoutEnteteServeur(String date,String ref_fac,String do_tiers,String de_no,float lat,float lon) throws IOException {
        JSONArray json = null;
        int qte=0;
        try {
            String url="page=addDocenteteMagasin&DO_Date="+date+"&DO_Ref="+ref_fac.replace(" ","%20")+"&DO_Tiers="+do_tiers+"&DE_No="+de_no+"&latitude=" + lat+"&longitude=" + lon;
            json = new JSONArray(getJsonFromServerNouveau(url));
            if(json.length()>0)
                return ((JSONObject)json.get(0)).getString("DO_Piece");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPrixclient(String ref_art,int cat_tarif,int cat_compta,ArticleServeur art){
        JSONArray json = null;
        int qte=0;
        try {
            String url="page=getPrixClient&AR_Ref="+ref_art+"&N_CatTarif="+cat_tarif+"&N_CatCompta="+cat_compta;
            json = new JSONArray(getJsonFromServerNouveau(url));
            art.setAr_prixven((float) ((JSONObject)json.get(0)).getDouble("AR_PrixVen"));
            art.setAr_prixach((float) ((JSONObject)json.get(0)).getDouble("AR_PrixAch"));
            art.setTaxe1(((JSONObject)json.get(0)).getDouble("taxe1"));
            art.setTaxe2(((JSONObject)json.get(0)).getDouble("taxe2"));
            art.setTaxe3(((JSONObject)json.get(0)).getDouble("taxe3"));
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static PrixClient getPrixclientMain(String ref_art, int cat_tarif, int cat_compta){
        JSONArray json = null;
        int qte=0;
        try {
            String url="page=getPrixClient&AR_Ref="+ref_art+"&N_CatTarif="+cat_tarif+"&N_CatCompta="+cat_compta;
            json = new JSONArray(getJsonFromServerNouveau(url));
            PrixClient prix = new PrixClient(ref_art,((JSONObject)json.get(0)).getDouble("AR_PrixVen"), ((JSONObject)json.get(0)).getDouble("AR_PrixAch")
                    ,((JSONObject)json.get(0)).getDouble("taxe1"),((JSONObject)json.get(0)).getDouble("taxe2"),((JSONObject)json.get(0)).getDouble("taxe3"));
            return prix;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static ArrayList<PrixClient> getPrixclientMain(int de_no,int cat_tarif, int cat_compta,String ct_num){
        JSONArray json = null;
        ArrayList<PrixClient> lart=null;
        try {
            json = new JSONArray(getJsonFromServerNouveau("page=getArticlePrixClient&DE_No="+de_no+"&N_CatTarif="+cat_tarif+"&N_CatCompta="+cat_compta));
            lart= new ArrayList<PrixClient>();
            for(int i=0; i<json.length(); i++){
                JSONObject json_data = json.getJSONObject(i);
                PrixClient prix = new PrixClient(json_data.getString("AR_Ref"),json_data.getDouble("AR_PrixVen"), json_data.getDouble("AR_PrixAch")
                        ,json_data.getDouble("taxe1"),json_data.getDouble("taxe2"),json_data.getDouble("taxe3"));
                prix.setCT_Num(ct_num);
                lart.add(prix);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static void passeVariable(Intent intent, Activity act, ArrayList<Facture> lfact, Parametre parametre, outils ou, ArrayList<Facture> lrecouvrement, ArrayList<Client> lclient, ArrayList<ArticleServeur> lart, String device){
        intent.putExtra("liste_facture", lfact);
        intent.putExtra("parametre", parametre);
        intent.putExtra("outils", ou);
        intent.putExtra("liste_recouvrement", lrecouvrement);
        intent.putExtra("liste_client", lclient);
        intent.putExtra("liste_article", lart);
        intent.putExtra("device_address", device);
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
            String url="page=updateDrRegle&RG_No="+cr;
            getJsonFromServerNouveau(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ajoutEnteteServeur(int co_no,String ct_num,String ref_fac,String reg,float lat,float lon,String date) throws IOException {
        JSONArray json = null;
        int qte=0;
        try {
            String url="page=addDocenteteFacture&CO_No="+co_no+"&CT_Num="+ct_num+"&ref="+ref_fac.replace(" ","%20")+"&N_Reglement="+reg+"&Latitude=" + lat+"&Longitude=" + lon+"&date=" + date;
            json = new JSONArray(getJsonFromServerNouveau(url));
            return ((JSONObject)json.get(0)).getString("DO_Piece");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int ajoutLigneServeur (String no_fac,String ref_fac,int no_ligne,int dl_qte,int dl_remise,String vehicule,String cr) throws IOException {
        JSONArray json = null;
        int qte=0;
        try {
            String res = "page=addDocligneFacture&DO_Piece=" + no_fac + "&AR_Ref=" + ref_fac + "&DL_Ligne=" + no_ligne+"&DL_Qte=" + dl_qte+"&remise=" + dl_remise+"&vehicule=" + vehicule+"&cr=" + cr;
            json = new JSONArray(getJsonFromServerNouveau(res));
            qte = ((JSONObject)json.get(0)).getInt("cbMarq");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return qte;
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

    public static cReglement reglerEntete(String do_piece,String ref,String avance){
        JSONArray json = null;
        cReglement cr = null;
        try {
            String url="page=addCReglementFacture&DO_Piece="+do_piece+"&ref="+ref+"&montant="+avance;
            json = new JSONArray(getJsonFromServerNouveau(url));
            cr = new cReglement(((JSONObject)json.get(0)).getInt("DR_No"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cr;
    }
    public static ArrayList<Souche> listeSoucheServeur(){
        JSONObject json = null;
        ArrayList<Souche> lart=null;
        try {
            json = new JSONObject(getJsonFromServer("souche"));
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
}
