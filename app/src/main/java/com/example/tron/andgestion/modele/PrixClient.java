package com.example.tron.andgestion.modele;

import java.io.Serializable;

/**
 * Created by T.Ron on 25/09/2016.
 */
public class PrixClient implements Cloneable,Serializable{
    private String AR_Ref,CT_Num;
    private double prixVen,prixAch,taxe1,taxe2,taxe3;

    public PrixClient() {
    }

    public PrixClient(String AR_Ref, String CT_Num, double prixVen, double prixAch, double taxe1, double taxe2, double taxe3) {
        this.AR_Ref = AR_Ref;
        this.CT_Num = CT_Num;
        this.prixVen = prixVen;
        this.prixAch = prixAch;
        this.taxe1 = taxe1;
        this.taxe2 = taxe2;
        this.taxe3 = taxe3;
    }

    public PrixClient(String AR_Ref, double prixVen, double prixAch, double taxe1, double taxe2, double taxe3) {
        this.AR_Ref = AR_Ref;
        this.prixVen = prixVen;
        this.prixAch = prixAch;
        this.taxe1 = taxe1;
        this.taxe2 = taxe2;
        this.taxe3 = taxe3;
    }

    public double getTaxe1() {
        return taxe1;
    }

    public void setTaxe1(double taxe1) {
        this.taxe1 = taxe1;
    }

    public double getTaxe2() {
        return taxe2;
    }

    public void setTaxe2(double taxe2) {
        this.taxe2 = taxe2;
    }

    public double getTaxe3() {
        return taxe3;
    }

    public void setTaxe3(double taxe3) {
        this.taxe3 = taxe3;
    }

    public String getAR_Ref() {
        return AR_Ref;
    }

    public void setAR_Ref(String AR_Ref) {
        this.AR_Ref = AR_Ref;
    }

    public String getCT_Num() {
        return CT_Num;
    }

    public void setCT_Num(String CT_Num) {
        this.CT_Num = CT_Num;
    }

    public double getPrixVen() {
        return prixVen;
    }

    public void setPrixVen(double prixVen) {
        this.prixVen = prixVen;
    }

    public double getPrixAch() {
        return prixAch;
    }

    public void setPrixAch(double prixAch) {
        this.prixAch = prixAch;
    }

    @Override
    public String toString() {
        return "PrixClient{" +
                "AR_Ref='" + AR_Ref + '\'' +
                ", CT_Num='" + CT_Num + '\'' +
                ", prixVen=" + prixVen +
                ", prixAch=" + prixAch +
                ", taxe1=" + taxe1 +
                ", taxe2=" + taxe2 +
                ", taxe3=" + taxe3 +
                '}';
    }
}
