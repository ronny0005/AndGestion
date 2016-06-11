package com.example.tron.andgestion.modele;

import com.example.tron.andgestion.modele.Caisse;

import java.io.Serializable;

/**
 * Created by T.Ron on 20/03/2016.
 */
public class Parametre implements Serializable {
    private int de_no;
    private String ct_num;
    private int co_no;
    private int do_souche;
    private String affaire;
    private String numdoc;
    private String vehicule;
    private String user;
    private String mdp;
    private Caisse ca_no;
    private String Date_facture;
    private int R_Facture;
    private int ID_Facture;

    public Parametre(int de_no, String ct_num, int co_no, int do_souche, String affaire, String numdoc, String vehicule, String user, String mdp, Caisse ca_no ,String Date_facture,int R_Facture,int ID_Facture) {
        this.de_no = de_no;
        this.ct_num = ct_num;
        this.co_no = co_no;
        this.do_souche = do_souche;
        this.affaire = affaire;
        this.numdoc = numdoc;
        this.vehicule = vehicule;
        this.user = user;
        this.mdp = mdp;
        this.ca_no = ca_no;
        this.Date_facture=Date_facture;
        this.R_Facture=R_Facture;
        this.ID_Facture=ID_Facture;
    }

    public String getDate_facture() {
        return Date_facture;
    }

    public void setDate_facture(String date_facture) {
        Date_facture = date_facture;
    }

    public int getR_Facture() {
        return R_Facture;
    }

    public void setR_Facture(int r_Facture) {
        R_Facture = r_Facture;
    }

    public int getID_Facture() {
        return ID_Facture;
    }

    public void setID_Facture(int ID_Facture) {
        this.ID_Facture = ID_Facture;
    }

    public Caisse getCa_no() {
        return ca_no;
    }

    public void setCa_no(Caisse ca_no) {
        this.ca_no = ca_no;
    }

    public int getDe_no() {
        return de_no;
    }

    public void setDe_no(int de_no) {
        this.de_no = de_no;
    }

    public String getCt_num() {
        return ct_num;
    }

    public void setCt_num(String ct_num) {
        this.ct_num = ct_num;
    }

    public int getCo_no() {
        return co_no;
    }

    public void setCo_no(int co_no) {
        this.co_no = co_no;
    }

    public int getDo_souche() {
        return do_souche;
    }

    public void setDo_souche(int do_souche) {
        this.do_souche = do_souche;
    }

    public String getAffaire() {
        return affaire;
    }

    public void setAffaire(String affaire) {
        this.affaire = affaire;
    }

    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public String toString() {
        return "Parametre{" +
                "de_no=" + de_no +
                ", ct_num='" + ct_num + '\'' +
                ", co_no=" + co_no +
                ", do_souche=" + do_souche +
                ", affaire='" + affaire + '\'' +
                ", numdoc='" + numdoc + '\'' +
                ", vehicule='" + vehicule + '\'' +
                ", user='" + user + '\'' +
                ", mdp='" + mdp + '\'' +
                ", ca_no=" + ca_no +
                ", Date_facture='" + Date_facture + '\'' +
                ", R_Facture=" + R_Facture +
                ", ID_Facture=" + ID_Facture +
                '}';
    }
}
