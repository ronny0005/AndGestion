package com.example.tron.andgestion.bddlocal.article;

import java.io.Serializable;

/**
 * Created by T.Ron on 18/03/2016.
 */
public class ArticleServeur implements Serializable {
    private int id;
    private int ar_ref;
    private String ar_design;
    private float prix_vente;
    private float ar_uniteven;
    private double ar_prixach;
    private int fa_codefamille;
    private float ar_prixven;
    private int qte_vendue;
    private double taxe1;
    private double taxe2;
    private double taxe3;


    public ArticleServeur(int ar_ref, String ar_design,double ar_prixach,double taxe1,double taxe2,double taxe3) {
        this.ar_ref = ar_ref;
        this.ar_design = ar_design;
        this.ar_prixach = ar_prixach;
        this.taxe1 = taxe1;
        this.taxe2 = taxe2;
        this.taxe3 = taxe3;
    }

    public ArticleServeur(int ar_ref, String ar_design, float prix_vente, float ar_uniteven, float ar_prixach, int fa_codefamille, float ar_prixven) {
        this.ar_ref = ar_ref;
        this.ar_design = ar_design;
        this.prix_vente = prix_vente;
        this.ar_uniteven = ar_uniteven;
        this.ar_prixach = ar_prixach;
        this.fa_codefamille = fa_codefamille;
        this.ar_prixven = ar_prixven;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getPrix_vente() {
        return prix_vente;
    }

    public int getQte_vendue() {
        return qte_vendue;
    }

    public void setQte_vendue(int qte_vendue) {
        this.qte_vendue = qte_vendue;
    }

    public void setPrix_vente(float prix_vente) {
        this.prix_vente = prix_vente;
    }

    public float getAr_uniteven() {
        return ar_uniteven;
    }

    public void setAr_uniteven(float ar_uniteven) {
        this.ar_uniteven = ar_uniteven;
    }

    public double getAr_prixach() {
        return ar_prixach;
    }

    public void setAr_prixach(double ar_prixach) {
        this.ar_prixach = ar_prixach;
    }

    public int getFa_codefamille() {
        return fa_codefamille;
    }

    public void setFa_codefamille(int fa_codefamille) {
        this.fa_codefamille = fa_codefamille;
    }

    public float getAr_prixven() {
        return ar_prixven;
    }

    public void setAr_prixven(float ar_prixven) {
        this.ar_prixven = ar_prixven;
    }

    public int getAr_ref() {
        return ar_ref;
    }

    public void setAr_ref(int ar_ref) {
        this.ar_ref = ar_ref;
    }

    public String getAr_design() {
        return ar_design;
    }

    public void setAr_design(String ar_design) {
        this.ar_design = ar_design;
    }
}
