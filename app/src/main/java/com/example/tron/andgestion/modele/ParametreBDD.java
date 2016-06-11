package com.example.tron.andgestion.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class ParametreBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "parametre.db";

    private static final String TABLE_PARAMETRE = "table_parametre";

    private static final String id="id";
    private static final int num_id = 0;
    private static final String de_no="de_no";
    private static final int num_de_no = 1;
    private static final String ct_num="ct_num";
    private static final int num_ct_num = 2;
    private static final String co_no="co_no";
    private static final int num_co_no = 3;
    private static final String do_souche="do_souche";
    private static final int num_do_souche = 4;
    private static final String affaire="affaire";
    private static final int num_affaire = 5;
    private static final String numdoc="numdoc";
    private static final int num_numdoc = 6;
    private static final String vehicule="vehicule";
    private static final int num_vehicule = 7;
    private static final String user="user";
    private static final int num_user= 8;
    private static final String mdp="mdp";
    private static final int num_mdp= 9;
    private static final String ca_no="ca_no";
    private static final int num_ca_no= 10;
    private static final String Date_facture="Date_facture";
    private static final int num_Date_facture= 11;
    private static final String R_Facture="R_Facture";
    private static final int num_R_Facture= 12;
    private static final String ID_Facture="ID_Facture";
    private static final int num_ID_Facture= 13;

    private SQLiteDatabase bdd;

    private ParametreSQLite maBaseSQLite;

    public ParametreBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new ParametreSQLite(context, NOM_BDD, null, VERSION_BDD);
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

    public long insertParametre(Parametre parametre){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(vehicule, parametre.getVehicule());
        values.put(affaire, parametre.getAffaire());
        values.put(co_no, parametre.getCo_no());
        values.put(ct_num, parametre.getCt_num());
        values.put(Date_facture, parametre.getDate_facture());
        values.put(de_no, parametre.getDe_no());
        values.put(do_souche, parametre.getDo_souche());
        values.put(ID_Facture, parametre.getID_Facture());
        values.put(mdp, parametre.getMdp());
        values.put(user, parametre.getUser());
        values.put(ca_no, parametre.getCa_no().getCa_no());
        values.put(numdoc, parametre.getNumdoc());
        values.put(R_Facture, parametre.getR_Facture());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_PARAMETRE, null, values);
    }

    public int updateParametre(int id, Parametre parametre){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(vehicule, parametre.getVehicule());
        values.put(affaire, parametre.getAffaire());
        values.put(co_no, parametre.getCo_no());
        values.put(ct_num, parametre.getCt_num());
        values.put(Date_facture, parametre.getDate_facture());
        values.put(de_no, parametre.getDe_no());
        values.put(do_souche, parametre.getDo_souche());
        values.put(ID_Facture, parametre.getID_Facture());
        values.put(mdp, parametre.getMdp());
        values.put(user, parametre.getUser());
        values.put(ca_no, parametre.getCa_no().getCa_no());
        values.put(numdoc, parametre.getNumdoc());
        values.put(R_Facture, parametre.getR_Facture());
        return bdd.update(TABLE_PARAMETRE, values, id + " = " +id, null);
    }

    public int removeArticleWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_PARAMETRE, id + " = " +id, null);
    }

    public Parametre getParametreWithUser(String user_p,String mdp_p){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)

        Cursor c = bdd.query(TABLE_PARAMETRE, new String[] {id,de_no,ct_num,do_souche,affaire,numdoc,vehicule,user,mdp,ca_no,Date_facture,R_Facture,ID_Facture}, user + " LIKE \"" + user_p+"\" AND "+mdp+ " LIKE \""+mdp_p+"\"", null, null, null, null);
        return cursorToParametre(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Parametre cursorToParametre(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Parametre parametre = new Parametre(c.getInt(num_de_no), c.getString(num_ct_num),c.getInt(num_co_no),c.getInt(num_do_souche),c.getString(num_affaire),
                c.getString(num_numdoc), c.getString(num_vehicule),c.getString(num_user), c.getString(num_mdp), null,c.getString(num_Date_facture),
                c.getInt(num_R_Facture),c.getInt(num_ID_Facture));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return parametre;
    }
}
