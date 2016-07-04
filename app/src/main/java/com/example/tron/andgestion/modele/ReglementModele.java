package com.example.tron.andgestion.modele;

/**
 * Created by T.Ron on 04/07/2016.
 */
public class ReglementModele {

    private String RG_No,RG_Date,CT_Intitule,RG_Libelle;
    private double montant;

    public ReglementModele(String RG_No, String RG_Date, String CT_Intitule, String RG_Libelle, double montant) {
        this.RG_No = RG_No;
        this.RG_Date = RG_Date;
        this.CT_Intitule = CT_Intitule;
        this.RG_Libelle = RG_Libelle;
        this.montant = montant;
    }

    public ReglementModele() {
    }

    public String getRG_No() {
        return RG_No;
    }

    public void setRG_No(String RG_No) {
        this.RG_No = RG_No;
    }

    public String getRG_Date() {
        return RG_Date;
    }

    public void setRG_Date(String RG_Date) {
        this.RG_Date = RG_Date;
    }

    public String getCT_Intitule() {
        return CT_Intitule;
    }

    public void setCT_Intitule(String CT_Intitule) {
        this.CT_Intitule = CT_Intitule;
    }

    public String getRG_Libelle() {
        return RG_Libelle;
    }

    public void setRG_Libelle(String RG_Libelle) {
        this.RG_Libelle = RG_Libelle;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "DocReglementModele{" + "RG_No=" + RG_No + ", RG_Date=" + RG_Date + ", CT_Intitule=" + CT_Intitule + ", RG_Libelle=" + RG_Libelle + ", montant=" + montant + '}';
    }

}
