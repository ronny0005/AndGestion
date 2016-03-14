package com.example.tron.andgestion.bddlocal.affaire;

/**
 * Created by T.Ron$ on 14/03/2016.
 */
public class Affaire {
    private int id;
    private String ca_intitule;
    private int n_analytique;

    public Affaire(int id, String ca_intitule, int n_analytique) {
        this.id = id;
        this.ca_intitule = ca_intitule;
        this.n_analytique = n_analytique;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCa_intitule() {
        return ca_intitule;
    }

    public void setCa_intitule(String ca_intitule) {
        this.ca_intitule = ca_intitule;
    }

    public int getN_analytique() {
        return n_analytique;
    }

    public void setN_analytique(int n_analytique) {
        this.n_analytique = n_analytique;
    }
}
