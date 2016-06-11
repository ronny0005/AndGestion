package com.example.tron.andgestion.modele;

import java.io.Serializable;

/**
 * Created by T.Ron$ on 15/03/2016.
 */
public class Caisse implements Serializable {
    private int id;
    private String jo_num;
    private String ca_intitule;
    private int ca_no;
    private int nocaissier;

    public Caisse(String jo_num, String ca_intitule, int ca_no, int nocaissier) {
        this.jo_num = jo_num;
        this.ca_intitule = ca_intitule;
        this.ca_no = ca_no;
        this.nocaissier = nocaissier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJo_num() {
        return jo_num;
    }

    public void setJo_num(String jo_num) {
        this.jo_num = jo_num;
    }

    public String getCa_intitule() {
        return ca_intitule;
    }

    public void setCa_intitule(String ca_intitule) {
        this.ca_intitule = ca_intitule;
    }

    public int getCa_no() {
        return ca_no;
    }

    public void setCa_no(int ca_no) {
        this.ca_no = ca_no;
    }

    public int getNocaissier() {
        return nocaissier;
    }

    public void setNocaissier(int nocaissier) {
        this.nocaissier = nocaissier;
    }

    @Override
    public String toString() {
        return "Caisse{" +
                "id=" + id +
                ", jo_num='" + jo_num + '\'' +
                ", ca_intitule='" + ca_intitule + '\'' +
                ", ca_no=" + ca_no +
                ", nocaissier=" + nocaissier +
                '}';
    }
}
