package com.example.tron.andgestion.modele;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by T.Ron$ on 09/03/2016.
 */
public class Client implements Serializable{

    private int id;
    private String intitule;
    private String num;
    private String numprinc;
    private int cattarif;
    private int catcompta;
    private String lib_cattarif;
    private String lib_catcompta;
    private ArrayList<PrixClient> prixArticle;

    public Client(String intitule, String num) {
        this.intitule = intitule;
        this.num = num;
    }

    public Client(String intitule, String num, String numprinc, int cattarif, int catcompta,String lib_catcompta,String lib_cattarif) {
        this.intitule = intitule;
        this.num = num;
        this.numprinc = numprinc;
        this.cattarif = cattarif;
        this.catcompta = catcompta;
        this.lib_catcompta=lib_catcompta;
        this.lib_cattarif=lib_cattarif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNumprinc() {
        return numprinc;
    }

    public void setNumprinc(String numprinc) {
        this.numprinc = numprinc;
    }

    public int getCattarif() {
        return cattarif;
    }

    public void setCattarif(int cattarif) {
        this.cattarif = cattarif;
    }

    public int getCatcompta() {
        return catcompta;
    }

    public void setCatcompta(int catcompta) {
        this.catcompta = catcompta;
    }

    public ArrayList<PrixClient> getPrixArticle() {
        return prixArticle;
    }

    public void setPrixArticle(ArrayList<PrixClient> prixArticle) {
        this.prixArticle = prixArticle;
    }

    public String getLib_cattarif() {
        return lib_cattarif;
    }

    public void setLib_cattarif(String lib_cattarif) {
        this.lib_cattarif = lib_cattarif;
    }

    public String getLib_catcompta() {
        return lib_catcompta;
    }

    public void setLib_catcompta(String lib_catcompta) {
        this.lib_catcompta = lib_catcompta;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                ", num='" + num + '\'' +
                ", numprinc='" + numprinc + '\'' +
                ", cattarif=" + cattarif +
                ", catcompta=" + catcompta +
                ", lib_cattarif='" + lib_cattarif + '\'' +
                ", lib_catcompta='" + lib_catcompta + '\'' +
                ", prixArticle=" + prixArticle +
                '}';
    }
}