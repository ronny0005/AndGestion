package com.example.tron.andgestion.bddlocal.manquant;

import java.util.Date;

/**
 * Created by BORICE on 12/04/2016.
 */
public class ManquantModele {

    private String date,CT_Num, CT_Intitule;
    private double montant;

    public ManquantModele() {
    }

    public ManquantModele(String date, String CT_Num, String CT_Intitule, double montant) {
        this.date = date;
        this.CT_Num = CT_Num;
        this.CT_Intitule = CT_Intitule;
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCT_Num() {
        return CT_Num;
    }

    public void setCT_Num(String CT_Num) {
        this.CT_Num = CT_Num;
    }

    public String getCT_Intitule() {
        return CT_Intitule;
    }

    public void setCT_Intitule(String CT_Intitule) {
        this.CT_Intitule = CT_Intitule;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "ManquantModele{" +
                "date='" + date + '\'' +
                ", CT_Num='" + CT_Num + '\'' +
                ", CT_Intitule='" + CT_Intitule + '\'' +
                ", montant=" + montant +
                '}';
    }
}
