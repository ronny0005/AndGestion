package com.example.tron.andgestion.bddlocal.facture;

import com.example.tron.andgestion.bddlocal.article.ArticleServeur;
import com.example.tron.andgestion.bddlocal.client.Client;
import com.example.tron.andgestion.bddlocal.fonction.outils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by T.Ron on 18/03/2016.
 */
public class Facture implements Serializable {
    private int id;
    private String ref;
    private Client id_client;
    private int id_depot;
    private ArrayList<ArticleServeur> liste_article;
    private ArrayList<Integer> position_article;
    private String statut;
    private String type_paiement;
    private Boolean nouveau;
    private String entete;
    private double mtt_avance;
    private double latitude;
    private double longitude;
    private int totalTTC;

    public Facture(String ref, Client id_client, int id_depot) {
        this.ref = ref;
        this.id_client = id_client;
        this.id_depot = id_depot;
        this.position_article= new  ArrayList<Integer>();
        this.setNouveau(true);
        this.id=0;
        this.entete="";
        this.statut="";
    }

    public int getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(int totalTTC) {
        this.totalTTC = totalTTC;
    }

    public double getMtt_avance() {
        return mtt_avance;
    }

    public void setMtt_avance(double mtt_avance) {
        this.mtt_avance = mtt_avance;
    }

    public ArrayList<Integer> getPosition_article() {
        return position_article;
    }

    public void setPosition_article(ArrayList<Integer> position_article) {
        this.position_article = position_article;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public Facture(String ref,ArrayList<ArticleServeur> list) {
        this.ref = ref;
        this.liste_article= list;
        this.position_article= new  ArrayList<Integer>();
        this.id_client=null;
        this.id=0;
        this.setNouveau(true);
        this.entete="";
        this.statut="";
    }

    public Facture() {
        this.liste_article= new  ArrayList<ArticleServeur>();
    }

    public String getType_paiement() {
        return type_paiement;
    }

    public String getEntete() {
        return entete;
    }

    public void setEntete(String entete) {
        this.entete = entete;
    }

    public void setType_paiement(String type_paiement) {
        this.type_paiement = type_paiement;
    }

    public Boolean getNouveau() {
        return nouveau;
    }

    public void setNouveau(Boolean nouveau) {
        this.nouveau = nouveau;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Client getId_client() {
        return id_client;
    }

    public void setId_client(Client id_client) {
        this.id_client = id_client;
    }

    public int getId_depot() {
        return id_depot;
    }

    public void setId_depot(int id_depot) {
        this.id_depot = id_depot;
    }

    public ArrayList<ArticleServeur> getListe_article() {
        return liste_article;
    }

    public void setListe_article(ArrayList<ArticleServeur> liste_article) {
        this.liste_article = liste_article;
    }

    public String toString(){
        return "Id : "+this.getId()+" depot :"+this.getId_depot()+" client:"+this.getId_client().getIntitule()+" statut :"+this.getStatut()
                +" nouveau :"+this.getNouveau();
    }
}
