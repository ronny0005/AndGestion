package com.example.tron.androidgestion.bddlocal.article;

/**
 * Created by T.Ron$ on 09/03/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ArticleBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "article.db";

    private static final String TABLE = "table_article";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_DATE = "DATE";
    private static final int NUM_COL_DATE = 1;
    private static final String COL_DESIGNATION = "DESIGNATION";
    private static final int NUM_COL_DESIGNATION = 2;
    private static final String COL_PU = "PU";
    private static final int NUM_COL_PU = 3;
    private static final String COL_QTE = "QTE";
    private static final int NUM_COL_QTE = 4;
    private static final String COL_QTEC = "QTEC";
    private static final int NUM_COL_QTEC = 5;
    private static final String COL_TOTAL = "TOTAL";
    private static final int NUM_COL_TOTAL = 6;

    private SQLiteDatabase bdd;

    private MaBaseSQLiteArticle maBaseSQLite;

    public ArticleBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseSQLiteArticle(context, NOM_BDD, null, VERSION_BDD);
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

    public void recreerBase(){
        maBaseSQLite.onUpgrade(bdd,0,1);
    }
    public long insert(Article item){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_DESIGNATION, item.getDesignation());
        values.put(COL_QTE, item.getQte());
        values.put(COL_QTEC, item.getQteColissee());
        values.put(COL_PU, item.getPu());
        values.put(COL_TOTAL, item.getTotal());
        values.put(COL_DATE, item.getDate());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE, null, values);
    }

    public int update(int id, Article item){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_DATE, item.getDate());
        values.put(COL_DESIGNATION, item.getDesignation());
        values.put(COL_QTE, item.getQte());
        values.put(COL_QTEC, item.getQteColissee());
        values.put(COL_PU, item.getPu());
        values.put(COL_TOTAL, item.getTotal());
        return bdd.update(TABLE, values, COL_ID + " = " + id, null);
    }

    public int removeWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE, COL_ID + " = " + id, null);
    }

    public Article getWithTitre(String titre){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE, new String[] {COL_ID, COL_DESIGNATION, COL_QTE}, COL_DESIGNATION + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToObj(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Article cursorToObj(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        //On créé un livre
        Article item = new Article();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        item.setId(c.getInt(NUM_COL_ID));
        item.setDate(c.getString(NUM_COL_DATE));
        item.setDesignation(c.getString(NUM_COL_DESIGNATION));
        item.setPu(c.getFloat(NUM_COL_PU));
        item.setQte(c.getFloat(NUM_COL_QTE));
        item.setQteColissee(c.getFloat(NUM_COL_QTEC));
        item.setTotal(c.getFloat(NUM_COL_TOTAL));
        //On retourne le livre
        return item;
    }

    public Cursor fetchAll(){
        Cursor cursor = bdd.rawQuery("SELECT * FROM "+TABLE, null);
        return cursor;
    }

    public ArrayList<Article> Tout()
    {
        Cursor c = fetchAll();
        ArrayList<Article> ldep = new ArrayList<Article>();
        c.moveToFirst();
        System.out.println(c.getCount());
        for(int i=0;i<c.getCount();c.moveToNext())
            ldep.add(cursorToObj(c));
        c.close();
        return ldep;
    }
}
