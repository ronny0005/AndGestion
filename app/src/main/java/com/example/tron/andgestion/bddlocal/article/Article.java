package com.example.tron.andgestion.bddlocal.article;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by T.Ron$ on 09/03/2016.
 */
public class Article {

    private int id;
    private String date;
    private String designation;
    private float pu;
    private float qte;
    private float qteColissee;
    private float total;

    public Article(){}

    public Article(String designation, float pu, float qte, float qteColissee, float total) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        this.date = dateFormat.format(date);
        this.designation = designation;
        this.pu = pu;
        this.qte = qte;
        this.qteColissee = qteColissee;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public float getPu() {
        return pu;
    }

    public void setPu(float pu) {
        this.pu = pu;
    }

    public float getQte() {
        return qte;
    }

    public void setQte(float qte) {
        this.qte = qte;
    }

    public float getQteColissee() {
        return qteColissee;
    }

    public void setQteColissee(float qteColissee) {
        this.qteColissee = qteColissee;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String toString(){
        return "Depot : Désignation"+this.getDesignation()+" pu:"+this.getPu()+" qte:"+getQte();
    }
}