package com.example.tron.andgestion.bddlocal.article;

/**
 * Created by T.Ron$ on 14/03/2016.
 */
public class Article {
    private int id;
    private String nom;

    public Article(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
