package com.example.tron.andgestion.modele;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by T.Ron on 29/05/2016.
 */
public class CompteA implements Serializable {
    private long id;
    private String CA_Num,CA_Intitule,CA_Classement,CA_Raccourci,cbCreateur,Centralisation;
    private int N_Analytique,CA_Type,CA_Report,N_Analyse,CA_Saut,CA_Sommeil,CA_Domaine,cbProt,cbMarq,cbReplication,cbFlag;
    private Date CA_DateCreate,cbModification;
    private double CA_Achat,CA_Vente;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCA_Num() {
        return CA_Num;
    }

    public void setCA_Num(String CA_Num) {
        this.CA_Num = CA_Num;
    }

    public String getCA_Intitule() {
        return CA_Intitule;
    }

    public void setCA_Intitule(String CA_Intitule) {
        this.CA_Intitule = CA_Intitule;
    }

    public String getCA_Classement() {
        return CA_Classement;
    }

    public void setCA_Classement(String CA_Classement) {
        this.CA_Classement = CA_Classement;
    }

    public String getCA_Raccourci() {
        return CA_Raccourci;
    }

    public void setCA_Raccourci(String CA_Raccourci) {
        this.CA_Raccourci = CA_Raccourci;
    }

    public String getCbCreateur() {
        return cbCreateur;
    }

    public void setCbCreateur(String cbCreateur) {
        this.cbCreateur = cbCreateur;
    }

    public String getCentralisation() {
        return Centralisation;
    }

    public void setCentralisation(String centralisation) {
        Centralisation = centralisation;
    }

    public int getN_Analytique() {
        return N_Analytique;
    }

    public void setN_Analytique(int n_Analytique) {
        N_Analytique = n_Analytique;
    }

    public int getCA_Type() {
        return CA_Type;
    }

    public void setCA_Type(int CA_Type) {
        this.CA_Type = CA_Type;
    }

    public int getCA_Report() {
        return CA_Report;
    }

    public void setCA_Report(int CA_Report) {
        this.CA_Report = CA_Report;
    }

    public int getN_Analyse() {
        return N_Analyse;
    }

    public void setN_Analyse(int n_Analyse) {
        N_Analyse = n_Analyse;
    }

    public int getCA_Saut() {
        return CA_Saut;
    }

    public void setCA_Saut(int CA_Saut) {
        this.CA_Saut = CA_Saut;
    }

    public int getCA_Sommeil() {
        return CA_Sommeil;
    }

    public void setCA_Sommeil(int CA_Sommeil) {
        this.CA_Sommeil = CA_Sommeil;
    }

    public int getCA_Domaine() {
        return CA_Domaine;
    }

    public void setCA_Domaine(int CA_Domaine) {
        this.CA_Domaine = CA_Domaine;
    }

    public int getCbProt() {
        return cbProt;
    }

    public void setCbProt(int cbProt) {
        this.cbProt = cbProt;
    }

    public int getCbMarq() {
        return cbMarq;
    }

    public void setCbMarq(int cbMarq) {
        this.cbMarq = cbMarq;
    }

    public int getCbReplication() {
        return cbReplication;
    }

    public void setCbReplication(int cbReplication) {
        this.cbReplication = cbReplication;
    }

    public int getCbFlag() {
        return cbFlag;
    }

    public void setCbFlag(int cbFlag) {
        this.cbFlag = cbFlag;
    }

    public Date getCA_DateCreate() {
        return CA_DateCreate;
    }

    public void setCA_DateCreate(Date CA_DateCreate) {
        this.CA_DateCreate = CA_DateCreate;
    }

    public Date getCbModification() {
        return cbModification;
    }

    public void setCbModification(Date cbModification) {
        this.cbModification = cbModification;
    }

    public double getCA_Achat() {
        return CA_Achat;
    }

    public void setCA_Achat(double CA_Achat) {
        this.CA_Achat = CA_Achat;
    }

    public double getCA_Vente() {
        return CA_Vente;
    }

    public void setCA_Vente(double CA_Vente) {
        this.CA_Vente = CA_Vente;
    }
}

