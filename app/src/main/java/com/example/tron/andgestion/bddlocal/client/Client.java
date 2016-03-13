package com.example.tron.andgestion.bddlocal.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by T.Ron$ on 09/03/2016.
 */
public class Client {

    private int id;
    private String nom;

    public Client(String nom) {
        this.nom = nom;
    }

    public Client(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public String toString(){
        return "Depot : Nom"+this.getNom();
    }
}