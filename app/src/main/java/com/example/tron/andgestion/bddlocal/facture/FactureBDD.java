package com.example.tron.andgestion.bddlocal.facture;

/**
 * Created by T.Ron$ on 09/03/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FactureBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "facture.db";

    private static final String TABLE = "table_facture";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_REF = "REF";
    private static final int NUM_COL_REF = 1;
    private static final String COL_ID_CLIENT = "ID_CLIENT";
    private static final int NUM_COL_ID_CLIENT = 2;
    private static final String COL_ID_DEPOT = "ID_DEPOT";
    private static final int NUM_COL_ID_DEPOT = 3;

    private SQLiteDatabase bdd;

    private MaBaseSQLiteFacture maBaseSQLite;

    public FactureBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseSQLiteFacture(context, NOM_BDD, null, VERSION_BDD);
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
    public long insert(Facture item){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur
        values.put(COL_REF , item.getRef());
       // values.put(COL_ID_CLIENT, item.getId_client());
        values.put(COL_ID_DEPOT, item.getId_depot());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE, null, values);
    }

    public int update(int id, Facture item){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_REF, item.getRef());
       // values.put(COL_ID_CLIENT, item.getId_client());
        values.put(COL_ID_DEPOT, item.getId_depot());
        return bdd.update(TABLE, values, COL_ID + " = " + id, null);
    }

    public int removeWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE, COL_ID + " = " + id, null);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Facture cursorToObj(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        //Sinon on se place sur le premier élément
        //On créé un livre
        Facture item = new Facture();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        item.setId(c.getInt(NUM_COL_ID));
        //item.setId_client(c.getInt(NUM_COL_ID_CLIENT));
        item.setId_depot(c.getInt(NUM_COL_ID_DEPOT));
        item.setRef(c.getString(NUM_COL_REF));
        //On ferme le cursor
        c.close();
        //On retourne le livre
        return item;
    }

    public Cursor fetchAll(){
        Cursor cursor = bdd.rawQuery("SELECT * FROM "+TABLE, null);
        return cursor;
    }

    public ArrayList<Facture> Tout()
    {
        Cursor c = fetchAll();
        ArrayList<Facture> ldep = new ArrayList<Facture>();
        c.moveToFirst();
        for(int i=0;i<c.getCount();c.moveToNext())
            ldep.add(cursorToObj(c));
        return ldep;
    }
}
