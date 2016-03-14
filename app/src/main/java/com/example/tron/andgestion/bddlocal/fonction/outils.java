package com.example.tron.andgestion.bddlocal.fonction;

import android.content.Context;
import android.os.StrictMode;

import com.example.tron.andgestion.bddlocal.affaire.Affaire;
import com.example.tron.andgestion.bddlocal.article.Article;
import com.example.tron.andgestion.bddlocal.depot.*;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.client.ClientBDD;
import com.example.tron.andgestion.bddlocal.facture.Donnee;
import com.example.tron.andgestion.bddlocal.facture.DonneeBDD;
import com.example.tron.andgestion.bddlocal.vehicule.Vehicule;

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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by T.Ron$ on 12/03/2016.
 */
public class outils {

    public static void InitClient(Context context){
        ClientBDD clientBDD= new ClientBDD(context);
        clientBDD.open();
        Client client= new Client("client 1");
        clientBDD.insert(client);
        client= new Client("client 2");
        clientBDD.insert(client);
        client= new Client("client 3");
        clientBDD.insert(client);
        client= new Client("client 4");
        clientBDD.insert(client);
    }

    public static void InitDonnee(Context context){
        ClientBDD clientBDD= new ClientBDD(context);
        clientBDD.open();
        clientBDD.Supprimer();
        ArrayList<Client> listc = clientBDD.Tout();
        for(int i=0;i<listc.size();i++)
            clientBDD.insert(listc.get(i));
    }

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

    public static ArrayList<Article> listeArticleServeur(){
        JSONObject json = null;
        ArrayList<Article> lart=null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/getAllArticle"));
            JSONArray jArray = json.getJSONArray("data");
            lart= new ArrayList<Article>();
            for(int i=0; i<jArray.length(); i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lart.add(new Article(json_data.getInt("AR_Ref"),json_data.getString("AR_Design")));
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
        ArrayList<Article> art =listeArticleServeur();
        for(int i=0;i<art.size();i++)
            lart.add(art.get(i).getNom());
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
            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/affaire"));
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
            json = new JSONObject(getJsonFromServer("http://192.168.1.14:8082/api/vehicule"));
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
        for(int i=0;i<dep.size();i++){
            ldep.add(dep.get(i).getCa_intitule());
            System.out.println(dep.get(i).getCa_intitule());
        }
        return ldep;
    }
}
