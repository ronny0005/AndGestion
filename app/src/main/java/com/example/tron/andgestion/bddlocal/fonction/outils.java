package com.example.tron.andgestion.bddlocal.fonction;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;


import com.example.tron.andgestion.Stock.Stock;
import com.example.tron.andgestion.Stock.StockEqVendeur;
import com.example.tron.andgestion.bddlocal.affaire.Affaire;
import com.example.tron.andgestion.bddlocal.article.Article;
import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.caisse.Caisse;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.depot.Depot;
import com.example.tron.andgestion.bddlocal.parametre.Parametre;
import com.example.tron.andgestion.bddlocal.souche.Souche;
import com.example.tron.andgestion.bddlocal.vehicule.Vehicule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by T.Ron$ on 12/03/2016.
 */
public class outils implements Serializable{

    public static Activity app=null;
    public static String lien="http://192.168.1.14:8082/api/";
    public static Parametre connexion(String login,String mdp) throws IOException{
        return getParametre(login,mdp);
    }

    public static String getJsonFromServer(String url) throws IOException {
        try {
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
        }catch(IOException e){
            Toast.makeText(app,"Problème de connexion! Veuillez réessayer plus tard !", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    public static String getJsonFromServerGenzy(String url) throws IOException {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.out.println(url);
            InputStream is = (InputStream) new URL(url).getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String result, line = reader.readLine();
            result = line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            return result;
        }catch(IOException e){
            Toast.makeText(app,"Problème de connexion! Veuillez réessayer plus tard !", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    public static ArrayList<Depot> listeDepotServeur(){
        JSONObject json = null;
        ArrayList<Depot> ldep=null;
        try {
            String url = "depot";
            //String url = "http://genzy.esy.es/depot.html";
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

    public static ArrayList<StockEqVendeur> eqStkVendeur(String depot,String date_deb,String date_fin) {
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        ArrayList<StockEqVendeur> ldep = null;
        try {
            socket = new Socket("192.168.1.19", 2009);
            System.out.println("Demande de connexion");
            out = new PrintWriter(socket.getOutputStream());
            out.println("equation_stock_vendeur/"+depot+"/"+date_deb+"/"+date_fin);
            System.out.println("equation_stock_vendeur/"+depot+"/"+date_deb+"/"+date_fin);
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message_distant = in.readLine();
            System.out.println(message_distant);
            JSONObject json = null;
            ldep = new ArrayList<StockEqVendeur>();
            json = new JSONObject(message_distant);
            JSONArray jArray = json.getJSONArray("data");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new StockEqVendeur(json_data.getString("AR_Design"), json_data.getInt("STOCKS"), json_data.getInt("ENTREES"), json_data.getInt("RETOURS"), json_data.getInt("AVARIS"), json_data.getInt("STOCK_FINAL"), json_data.getInt("QTE_VENDUES"), json_data.getInt("STOCK_RESTANTS")));
            }
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
                lart.add(new ArticleServeur(json_data.getInt("AR_Ref"),json_data.getString("AR_Design"),json_data.getDouble("AR_PrixAch"),json_data.getDouble("taxe1"),json_data.getDouble("taxe2"),json_data.getDouble("taxe3")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
    }

    public static int ajoutLigneServeur(String no_fac,String ref_fac,int no_ligne,int dl_qte,int dl_remise){
        JSONObject json = null;
        int qte=0;
        try {
            String res = "addDocligne?DO_Piece=" + no_fac + "&AR_Ref=" + ref_fac + "&DL_Ligne=" + no_ligne+"&DL_Qte=" + dl_qte+"&DL_Remise=" + dl_remise;
            json = new JSONObject(getJsonFromServer(res));
            JSONArray jArray = json.getJSONArray("data");
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                qte = json_data.getInt("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return qte;
    }


    public static ArrayList<Stock> getStock(int de_no) {
        JSONObject json = null;
        ArrayList<Stock> ldep = new ArrayList<Stock>();
        ldep.add(new Stock("10111","ARTICLE",22,2000,20));
        try {
            //json = new JSONObject(getJsonFromServer("http://genzy.esy.es/depot.html"));
            String url="stock?DE_No=" + de_no;
            json = new JSONObject(getJsonFromServer(url));
            JSONArray jArray = json.getJSONArray("data");
            //ldep = new ArrayList<Stock>();
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

    public static void reglerEntete(int idEntete,String ref){
        JSONObject json = null;
        ArrayList<ArticleServeur> lart=null;
        try {
            json = new JSONObject(getJsonFromServer("regleDocentete?idEntete="+idEntete+"&ref="+ref));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<ArticleServeur>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new ArticleServeur(json_data.getInt("AR_Ref"),json_data.getString("AR_Design"),json_data.getDouble("AR_PrixAch"),json_data.getDouble("taxe1"),json_data.getDouble("taxe2"),json_data.getDouble("taxe3")));
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
                lart.add(new ArticleServeur(json_data.getInt("AR_Ref"),json_data.getString("AR_Design"),json_data.getDouble("AR_PrixAch"),json_data.getDouble("taxe1"),json_data.getDouble("taxe2"),json_data.getDouble("taxe3")));
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

    public static double articleDisponibleServeur(int ref_art,int depot){
        JSONObject json = null;
        double qte=0;
        try {
            String url = "isStock?AR_Ref="+ref_art+"&DE_No="+depot;
            json = new JSONObject(getJsonFromServer(url));
            return json.getJSONObject("data").getDouble("AS_QteSto");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return qte;
    }


    public static Parametre getParametre(String password,String nomUser) throws IOException{
        JSONObject json = null;
        int qte=0;
        try {
            //String url="getParametreByLogin?NomUser="+nomUser+"&Password="+password;
            String url="http://genzy.esy.es/parametre.html";
            json = new JSONObject(getJsonFromServerGenzy(url));
            JSONObject ob =json.getJSONObject("data");
            ArrayList<Caisse> lcaisse = listeCaisseServeur();
            Caisse c =null;
            for(int i=0;i<lcaisse.size();i++)
                if(lcaisse.get(i).getCa_no()==ob.getInt("CA_No"))
                    c=lcaisse.get(i);
            return new Parametre( ob.getInt("DE_No"),ob.getString("CT_Num") , ob.getInt("CO_No"),ob.getInt("DO_Souche"),
                    ob.getString("affaire"),ob.getString("numDoc") ,ob.getString("vehicule"),
                    nomUser,password,c);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String ajoutEnteteServeur(int co_no,String ct_num,String ref_fac,String reg){
        JSONObject json = null;
        int qte=0;
        try {
            String url="addDocentete?CO_No="+co_no+"&CT_Num="+ct_num+"&ref="+ref_fac+"&N_Reglement="+reg;
            json = new JSONObject(getJsonFromServer(url));
            return json.getJSONObject("data").getString("DO_Piece");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPrixclient(int ref_art,int cat_tarif,int cat_compta,ArticleServeur art){
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

    public static ArrayList<Souche> reglerEntete(String do_piece,String ref){
        JSONObject json = null;
        ArrayList<Souche> lart=null;
        try {
            String url="regleDocentete?DO_Piece="+do_piece+"&ref="+ref;
            json = new JSONObject(getJsonFromServer(url));

//            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/souche.html"));
            JSONArray jArray = json.getJSONArray("data");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lart;
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
