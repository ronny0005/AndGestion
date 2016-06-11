package com.example.tron.andgestion.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class DepotBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "depot.db";

    private static final String TABLE_DEPOT = "table_depot";

    private static final String id="id";
    private static final int num_id = 0;
    private static final String nom="nom";
    private static final int num_nom = 1;

    private SQLiteDatabase bdd;

    private DepotSQLite maBaseSQLite;

    public DepotBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new DepotSQLite(context, NOM_BDD, null, VERSION_BDD);
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

    public long insertDepot(Depot depot){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(nom, depot.getNom());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_DEPOT, null, values);
    }

    public int updateDepot(int id, Depot depot){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(nom, depot.getNom());
        return bdd.update(TABLE_DEPOT, values, id + " = " +id, null);
    }

    public int removeDepotWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_DEPOT, id + " = " +id, null);
    }

    public Depot getDepotWithId(int id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)

        Cursor c = bdd.query(TABLE_DEPOT, new String[] {id,nom}, id+ " = " + id_c, null, null, null, null);
        return cursorToDepot(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Depot cursorToDepot(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Depot depot= new Depot(c.getString(num_nom));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return depot;
    }
}
