package com.example.tron.andgestion.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class ArticleBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "article.db";

    private static final String TABLE_ARTICLES = "table_articles";

    private static final String id="id";
    private static final int num_id = 0;
    private static final String ar_ref="ar_ref";
    private static final int num_arref = 1;
    private static final String ar_design="ar_design";
    private static final int num_ardesign = 2;
    private static final String prix_vente="prix_vente";
    private static final int num_pxvente = 3;
    private static final String ar_uniteven="ar_uniteven";
    private static final int num_unitven = 4;
    private static final String ar_prixach="ar_prixach";
    private static final int num_pxach = 5;
    private static final String fa_codefamille="fa_codefamille";
    private static final int num_facodefamille = 6;
    private static final String ar_prixven="ar_prixven";
    private static final int num_arpxven = 7;
    private static final String qte_vendue="qte_vendue";
    private static final int num_qteven= 8;
    private static final String taxe1="taxe1";
    private static final int num_taxe1= 9;
    private static final String taxe2="taxe2";
    private static final int num_taxe2= 10;
    private static final String taxe3="taxe3";
    private static final int num_taxe3= 11;
    private static final String vehicule="vehicule";
    private static final int num_vehicule= 12;
    private static final String cr="cr";
    private static final int num_cr= 13;

    private SQLiteDatabase bdd;

    private ArticleSQLite maBaseSQLite;

    public ArticleBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new ArticleSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertArticle(ArticleServeur article){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(ar_design, article.getAr_design());
        values.put(ar_prixach, article.getAr_prixach());
        values.put(ar_prixven, article.getAr_prixven());
        values.put(ar_ref, article.getAr_ref());
        values.put(ar_uniteven, article.getAr_uniteven());
        values.put(cr, article.getCr());
        values.put(fa_codefamille, article.getFa_codefamille());
        values.put(prix_vente, article.getPrix_vente());
        values.put(qte_vendue, article.getQte_vendue());
        values.put(id, article.getId());
        values.put(taxe1, article.getTaxe1());
        values.put(taxe2, article.getTaxe2());
        values.put(taxe3, article.getTaxe3());
        values.put(vehicule, article.getVehicule());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_ARTICLES, null, values);
    }

    public int updateArticle(int id, ArticleServeur article){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(ar_design, article.getAr_design());
        values.put(ar_prixach, article.getAr_prixach());
        values.put(ar_prixven, article.getAr_prixven());
        values.put(ar_ref, article.getAr_ref());
        values.put(ar_uniteven, article.getAr_uniteven());
        values.put(cr, article.getCr());
        values.put(fa_codefamille, article.getFa_codefamille());
        values.put(prix_vente, article.getPrix_vente());
        values.put(qte_vendue, article.getQte_vendue());
        values.put(taxe1, article.getTaxe1());
        values.put(taxe2, article.getTaxe2());
        values.put(taxe3, article.getTaxe3());
        values.put(vehicule, article.getVehicule());
        return bdd.update(TABLE_ARTICLES, values, id + " = " +id, null);
    }

    public int removeArticleWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_ARTICLES, id + " = " +id, null);
    }

    public ArticleServeur getArticleWithRef(String ref){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_ARTICLES, new String[] {id, ar_design, ar_prixach,ar_prixven,taxe1,taxe2,taxe3}, ar_ref + " LIKE \"" + ref +"\"", null, null, null, null);
        return cursorToArticle(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private ArticleServeur cursorToArticle(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        ArticleServeur article = new ArticleServeur(c.getString(num_arref),c.getString(num_ardesign) ,c.getDouble(num_pxach) ,c.getDouble(num_taxe1),c.getDouble(num_taxe2),c.getDouble(num_taxe3));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return article;
    }
}
