package com.example.tron.andgestion.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class CaisseBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "caisse.db";

    private static final String TABLE_CAISSE = "table_caisse";

    private static final String id="id";
    private static final int num_id = 0;
    private static final String jo_num="jo_num";
    private static final int num_jonum = 1;
    private static final String ca_intitule="ca_intitule";
    private static final int num_caintitule = 2;
    private static final String ca_no="ca_no";
    private static final int num_cano = 3;
    private static final String nocaissier="nocaissier";
    private static final int num_nocaissier = 4;

    private SQLiteDatabase bdd;

    private CaisseSQLite maBaseSQLite;

    public CaisseBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new CaisseSQLite(context, NOM_BDD, null, VERSION_BDD);
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

    public long insertCaisse(Caisse caisse){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(jo_num, caisse.getJo_num());
        values.put(ca_intitule, caisse.getCa_intitule());
        values.put(ca_no, caisse.getCa_no());
        values.put(nocaissier, caisse.getNocaissier());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_CAISSE, null, values);
    }

    public int updateCaisse(int id, Caisse caisse){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(jo_num, caisse.getJo_num());
        values.put(ca_intitule, caisse.getCa_intitule());
        values.put(ca_no, caisse.getCa_no());
        values.put(nocaissier, caisse.getNocaissier());
        return bdd.update(TABLE_CAISSE, values, id + " = " +id, null);
    }

    public int removeCaisseWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_CAISSE, id + " = " +id, null);
    }

    public Caisse getCaisseWithId(int id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)

        Cursor c = bdd.query(TABLE_CAISSE, new String[] {id,jo_num,ca_intitule,ca_no,nocaissier}, ca_no + " = " + id_c, null, null, null, null);
        return cursorToCaisse(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Caisse cursorToCaisse(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Caisse caisse = new Caisse(c.getString(num_jonum), c.getString(num_caintitule),c.getInt(num_cano),c.getInt(num_nocaissier));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return caisse;
    }
}
