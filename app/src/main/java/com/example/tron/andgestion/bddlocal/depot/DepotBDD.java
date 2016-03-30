package com.example.tron.andgestion.bddlocal.depot;

/**
 * Created by T.Ron$ on 09/03/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tron.andgestion.bddlocal.client.Client;

import java.util.ArrayList;

public class DepotBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "depot.db";

    private static final String TABLE_DEPOT = "table_depot";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NOM = "NOM";
    private static final int NUM_COL_NOM = 1;

    private SQLiteDatabase bdd;

    private MaBaseSQLiteDepot maBaseSQLite;

    public DepotBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new MaBaseSQLiteDepot(context, NOM_BDD, null, VERSION_BDD);
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

    public long insert(Client depot){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NOM, depot.getIntitule());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_DEPOT, null, values);
    }

    public int update(int id, Client client){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NOM, client.getIntitule());
        return bdd.update(TABLE_DEPOT, values, COL_ID + " = " + id, null);
    }

    public int removeWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_DEPOT, COL_ID + " = " + id, null);
    }

    /*public Depot getDepotWithTitre(String titre){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_DEPOT, new String[] {COL_ID, COL_DESIGNATION, COL_QTE}, COL_DESIGNATION + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToDepot(c);
    }*/

    //Cette méthode permet de convertir un cursor en un livre
    private Client cursorToObj(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        //c.moveToFirst();
        //On créé un livre
        Client client = new Client("","");
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        client.setId(c.getInt(NUM_COL_ID));
        client.setIntitule(c.getString(NUM_COL_NOM));
        //On ferme le cursor
        //c.close();

        //On retourne le livre
        return client;
    }

    public Cursor fetchAll(){
        Cursor cursor = bdd.rawQuery("SELECT * FROM "+TABLE_DEPOT, null);
        //bdd.close();
        return cursor;
    }

    public ArrayList<Client> Tout()
    {
        Cursor c = fetchAll();
        ArrayList<Client> ldep = new ArrayList<Client>();
        for(c.moveToFirst();c.isLast();c.moveToNext())
            ldep.add(cursorToObj(c));
        c.moveToNext();
        return ldep;
    }
}
