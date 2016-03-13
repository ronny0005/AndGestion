package com.example.tron.andgestion.bddlocal.facture;

/**
 * Created by T.Ron$ on 09/03/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DonneeBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "depot.db";

    private static final String TABLE_DEPOT = "table_donnee";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_DATE = "DATE";
    private static final int NUM_COL_DATE = 1;
    private static final String COL_CLIENT = "CLIENT";
    private static final int NUM_COL_CLIENT = 2;
    private static final String COL_DESIGNATION = "DESIGNATION";
    private static final int NUM_COL_DESIGNATION = 3;
    private static final String COL_PU = "PU";
    private static final int NUM_COL_PU = 4;
    private static final String COL_QTE = "QTE";
    private static final int NUM_COL_QTE = 5;
    private static final String COL_QTEC = "QTEC";
    private static final int NUM_COL_QTEC = 6;
    private static final String COL_TOTAL = "TOTAL";
    private static final int NUM_COL_TOTAL = 7;

    private SQLiteDatabase bdd;

    private MaBaseSQLiteDonnee maBaseSQLite;

    public DonneeBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseSQLiteDonnee(context, NOM_BDD, null, VERSION_BDD);
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

    public long insert(Donnee depot){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_CLIENT, depot.getClient());
        values.put(COL_DESIGNATION, depot.getDesignation());
        values.put(COL_QTE, depot.getQte());
        values.put(COL_QTEC, depot.getQteColissee());
        values.put(COL_PU, depot.getPu());
        values.put(COL_TOTAL, depot.getTotal());
        values.put(COL_DATE, depot.getDate());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_DEPOT, null, values);
    }

    public int update(int id, Donnee depot){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_CLIENT, depot.getClient());
        values.put(COL_DATE, depot.getDate());
        values.put(COL_DESIGNATION, depot.getDesignation());
        values.put(COL_QTE, depot.getQte());
        values.put(COL_QTEC, depot.getQteColissee());
        values.put(COL_PU, depot.getPu());
        values.put(COL_TOTAL, depot.getTotal());
        return bdd.update(TABLE_DEPOT, values, COL_ID + " = " + id, null);
    }

    public int removeWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_DEPOT, COL_ID + " = " + id, null);
    }

    public Donnee getWithTitre(String titre){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_DEPOT, new String[] {COL_ID, COL_DESIGNATION, COL_QTE}, COL_DESIGNATION + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToObj(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Donnee cursorToObj(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        //c.moveToFirst();
        //On créé un livre
        Donnee depot = new Donnee();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        depot.setId(c.getInt(NUM_COL_ID));
        depot.setClient(c.getString(NUM_COL_CLIENT));
        depot.setDate(c.getString(NUM_COL_DATE));
        depot.setDesignation(c.getString(NUM_COL_DESIGNATION));
        depot.setPu(c.getFloat(NUM_COL_PU));
        depot.setQte(c.getFloat(NUM_COL_QTE));
        depot.setQteColissee(c.getFloat(NUM_COL_QTEC));
        depot.setTotal(c.getFloat(NUM_COL_TOTAL));
        //On ferme le cursor
        //c.close();
        //On retourne le livre
        return depot;
    }

    public Cursor fetchAll(){
        Cursor cursor = bdd.rawQuery("SELECT * FROM "+TABLE_DEPOT, null);
        //bdd.close();
        return cursor;
    }

    public ArrayList<Donnee> Tout()
    {

        Cursor c = fetchAll();
        ArrayList<Donnee> ldep = new ArrayList<Donnee>();
        c.moveToFirst();
        while(!c.isLast()) {
            ldep.add(cursorToObj(c));
        //c.close();
            c.moveToNext();
        }
        return ldep;
    }
}
