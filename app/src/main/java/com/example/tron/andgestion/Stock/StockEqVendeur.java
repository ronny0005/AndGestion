package com.example.tron.andgestion.Stock;

/**
 * Created by T.Ron on 03/04/2016.
 */
public class StockEqVendeur {
    private String design;
    private int stock;
    private int entrees;
    private int retours;
    private int avaris;
    private int stock_final;
    private int qte_vendues;
    private int stk_restants;


    public StockEqVendeur(String design, int stock, int entrees, int retours, int avaris, int stock_final, int qte_vendues, int stk_restants) {
        this.design = design;
        this.stock = stock;
        this.entrees = entrees;
        this.retours = retours;
        this.avaris = avaris;
        this.stock_final = stock_final;
        this.qte_vendues = qte_vendues;
        this.stk_restants = stk_restants;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getEntrees() {
        return entrees;
    }

    public void setEntrees(int entrees) {
        this.entrees = entrees;
    }

    public int getRetours() {
        return retours;
    }

    public void setRetours(int retours) {
        this.retours = retours;
    }

    public int getAvaris() {
        return avaris;
    }

    public void setAvaris(int avaris) {
        this.avaris = avaris;
    }

    public int getStock_final() {
        return stock_final;
    }

    public void setStock_final(int stock_final) {
        this.stock_final = stock_final;
    }

    public int getQte_vendues() {
        return qte_vendues;
    }

    public void setQte_vendues(int qte_vendues) {
        this.qte_vendues = qte_vendues;
    }

    public int getStk_restants() {
        return stk_restants;
    }

    public void setStk_restants(int stk_restants) {
        this.stk_restants = stk_restants;
    }
}
