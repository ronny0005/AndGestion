package com.example.tron.androidgestion.bddlocal.fonction;

import android.content.Context;
import android.os.StrictMode;


import com.example.tron.androidgestion.bddlocal.affaire.Affaire;
import com.example.tron.androidgestion.bddlocal.article.ArticleServeur;
import com.example.tron.androidgestion.bddlocal.caisse.Caisse;
import com.example.tron.androidgestion.bddlocal.client.Client;
import com.example.tron.androidgestion.bddlocal.depot.Depot;
import com.example.tron.androidgestion.bddlocal.souche.Souche;
import com.example.tron.androidgestion.bddlocal.vehicule.Vehicule;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by T.Ron$ on 12/03/2016.
 */
public class outils {

    public static boolean connexion(String login,String mdp){
        if(!login.isEmpty() && !mdp.isEmpty()){
            if(login.equals("test") && mdp.equals("test"))
                return true;
        }
        return false;
    }

    public static String getJsonFromServer(String url) throws IOException {
        BufferedReader inputStream = null;
        HttpClient hclient = new DefaultHttpClient();
        HttpPost hpost = new HttpPost(url);
        HttpResponse hres = hclient.execute(hpost);
        HttpEntity entity = hres.getEntity();
        InputStream webs = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(webs,"utf-8"),8);
        return reader.readLine();
        // read the JSON results into a string
    }

    public static ArrayList<Depot> listeDepotServeur(){
        JSONObject json = null;
        ArrayList<Depot> ldep=null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //json = new JSONObject(getJsonFromServer("http://genzy.esy.es/depot.html"));
            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/depot"));
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

    public static ArrayList<Client> listeClientServeur(String param){
        JSONObject json = null;
        ArrayList<Client> ldep=null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
//            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/client.html"));
            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/clients?op="+param));
            JSONArray jArray = json.getJSONArray("data");
            ldep= new ArrayList<Client>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                ldep.add(new Client(json_data.getString("CT_Intitule"),json_data.getString("CT_Num"),json_data.getString("CG_NumPrinc"),json_data.getInt("n_CatTarif"),json_data.getInt("n_CatCompta")));
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
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/getAllArticle"));
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
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/affaire.html"));
//            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/affaire"));
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
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/vehicule.html"));
//            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/vehicule"));
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
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/getPrixClient?AR_Ref="+ref+"&N_CatTarif="+cat_tarif+"&N_CatCompta="+cat_compta));
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
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/vehicule"));
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

    public static ArrayList<String> listeCaisseServeurTexte(){
        ArrayList<String> ldep= new ArrayList<String>();
        ArrayList<Caisse> dep =listeCaisseServeur();
        for(int i=0;i<dep.size();i++)
            ldep.add(dep.get(i).getCa_intitule());
        return ldep;
    }

    public static ArrayList<Souche> listeSoucheServeur(){
        JSONObject json = null;
        ArrayList<Souche> lart=null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
//            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/souche"));
            json = new JSONObject(getJsonFromServer("http://genzy.esy.es/souche.html"));
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
