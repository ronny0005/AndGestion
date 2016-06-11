package com.example.tron.andgestion.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tron.andgestion.Main2Activity;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class DatabaseSQLite extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "etatCamlait";
    // Table Names
    private static final String TABLE_DEPOT = "table_depot";
    private static final String TABLE_CAISSE = "table_caisse";
    private static final String TABLE_PARAMETRE = "table_parametre";
    private static final String TABLE_CLIENT = "table_client";

    //colonne caisse
    private static final String caisse_id="id";
    private static final String caisse_jo_num="jo_num";
    private static final String caisse_ca_intitule="ca_intitule";
    private static final String caisse_ca_no="ca_no";
    private static final String caisse_nocaissier="nocaissier";

    //colonne parametre
    private static final String parametre_id="id";
    private static final String parametre_de_no="de_no";
    private static final String parametre_co_no="co_no";
    private static final String parametre_ct_num="ct_num";
    private static final String parametre_do_souche="do_souche";
    private static final String parametre_affaire="affaire";
    private static final String parametre_numdoc="numdoc";
    private static final String parametre_vehicule="vehicule";
    private static final String parametre_user="user";
    private static final String parametre_mdp="mdp";
    private static final String parametre_ca_no="ca_no";
    private static final String parametre_Date_facture="Date_facture";
    private static final String parametre_R_Facture="R_Facture";
    private static final String parametre_ID_Facture="ID_Facture";

    //colonne client
    private static String client_id;
    private static String client_intitule;
    private static String client_num;
    private static String client_numprinc;
    private static String client_cattarif;
    private static String client_catcompta;

    private static final String CREATE_CAISSE = "CREATE TABLE " + TABLE_CAISSE + " ("
            + caisse_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + caisse_jo_num + " TEXT NOT NULL, "
            + caisse_ca_intitule + " TEXT NOT NULL,"
            + caisse_ca_no + " TEXT NOT NULL,"
            + caisse_nocaissier + " TEXT NOT NULL);";

    private static final String CREATE_CLIENT = "CREATE TABLE " + TABLE_CLIENT + " ("
            + client_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +client_intitule+ " TEXT NOT NULL,"
            + client_num + " TEXT NOT NULL,"
            + client_numprinc + " TEXT NOT NULL,"
            + client_cattarif + " TEXT NOT NULL,"
            + client_catcompta+ " TEXT NOT NULL);";

    private static final String CREATE_PARAMETRE = "CREATE TABLE " + TABLE_PARAMETRE + " ("
            + parametre_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + parametre_de_no + " TEXT NOT NULL, "
            + parametre_ct_num + " TEXT NOT NULL,"
            + parametre_do_souche + " TEXT NOT NULL,"
            + parametre_affaire + " TEXT NOT NULL,"
            + parametre_numdoc + " TEXT NOT NULL,"
            + parametre_vehicule + " TEXT NOT NULL,"
            + parametre_user + " TEXT NOT NULL,"
            + parametre_mdp + " TEXT NOT NULL,"
            + parametre_ca_no+ " TEXT NOT NULL,"
            + parametre_co_no+ " TEXT NOT NULL,"
            + parametre_Date_facture+ " TEXT NOT NULL,"
            + parametre_R_Facture+ " TEXT NOT NULL,"
            + parametre_ID_Facture+ " TEXT NOT NULL)";

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_CAISSE);
        db.execSQL(CREATE_PARAMETRE);
        db.execSQL(CREATE_CLIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPOT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAISSE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETRE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT + ";");
        onCreate(db);
    }

    public void recreate(){
        SQLiteDatabase bdd = this.getWritableDatabase();
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPOT + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_CAISSE + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETRE + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT + ";");
        onCreate(bdd);
    }

    public long insertCaisse(Caisse caisse){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(caisse_jo_num, caisse.getJo_num());
        values.put(caisse_ca_intitule, caisse.getCa_intitule());
        values.put(caisse_ca_no, caisse.getCa_no());
        values.put(caisse_nocaissier, caisse.getNocaissier());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_CAISSE, null, values);
    }

    public Caisse getCaisseWithId(int id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        Cursor c = bdd.query(TABLE_CAISSE, new String[] {caisse_id,caisse_jo_num,caisse_ca_intitule,caisse_ca_no,caisse_nocaissier}, caisse_ca_no+ " = " + id_c, null, null, null, null);
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
        Caisse caisse = new Caisse(c.getString(c.getColumnIndex(caisse_jo_num)), c.getString(c.getColumnIndex(caisse_ca_intitule)),
                c.getInt(c.getColumnIndex(caisse_ca_no)),c.getInt(c.getColumnIndex(caisse_nocaissier)));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return caisse;
    }

    public int updateCaisse(int id, Caisse caisse){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(caisse_jo_num, caisse.getJo_num());
        values.put(caisse_ca_intitule, caisse.getCa_intitule());
        values.put(caisse_ca_no, caisse.getCa_no());
        values.put(caisse_nocaissier, caisse.getNocaissier());
        return bdd.update(TABLE_CAISSE, values, id + " = " +id, null);
    }

    public int removeCaisseWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        return bdd.delete(TABLE_CAISSE, id + " = " +id, null);
    }

    public long insertClient(Client client){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(client_id,client.getId());
        values.put(client_catcompta,client.getCatcompta());
        values.put(client_cattarif, client.getCattarif());
        values.put(client_intitule, client.getIntitule());
        values.put(client_num, client.getNum());
        values.put(client_numprinc, client.getNumprinc());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_CAISSE, null, values);
    }

    public Client getClientWithId(int id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        Cursor c = bdd.query(TABLE_CLIENT, new String[] {client_id,client_cattarif,client_intitule,client_catcompta,client_numprinc,client_num}, client_num+ " = " + id_c, null, null, null, null);
        return cursorToClient(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Client cursorToClient(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Client client = new Client (c.getString(c.getColumnIndex(client_intitule)),c.getString(c.getColumnIndex(client_num)), c.getString(c.getColumnIndex(client_numprinc)),
                c.getInt(c.getColumnIndex(client_cattarif)),c.getInt(c.getColumnIndex(client_catcompta)));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return client;
    }

    public int updateClient(int id, Client client){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(client_catcompta,client.getCatcompta());
        values.put(client_cattarif, client.getCattarif());
        values.put(client_intitule, client.getIntitule());
        values.put(client_num, client.getNum());
        values.put(client_numprinc, client.getNumprinc());
        return bdd.update(TABLE_CLIENT, values, client_id + " = " +id, null);
    }

    public int removeClientWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        return bdd.delete(TABLE_CLIENT, client_id+ " = " +id, null);
    }


    public long insertParametre(Parametre parametre){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(parametre_vehicule, parametre.getVehicule());
        values.put(parametre_affaire, parametre.getAffaire());
        values.put(parametre_co_no, parametre.getCo_no());
        values.put(parametre_ct_num, parametre.getCt_num());
        values.put(parametre_Date_facture, parametre.getDate_facture());
        values.put(parametre_de_no, parametre.getDe_no());
        values.put(parametre_do_souche, parametre.getDo_souche());
        values.put(parametre_ID_Facture, parametre.getID_Facture());
        values.put(parametre_mdp, parametre.getMdp());
        values.put(parametre_user, parametre.getUser());
        values.put(parametre_ca_no, parametre.getCa_no().getCa_no());
        values.put(parametre_numdoc, parametre.getNumdoc());
        values.put(parametre_R_Facture, parametre.getR_Facture());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_PARAMETRE, null, values);
    }

    public Parametre getParametreWithUser(String user_p,String mdp_p){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        Cursor c = bdd.query(TABLE_PARAMETRE, new String[] {parametre_id,parametre_de_no,parametre_ct_num,parametre_do_souche,parametre_affaire,parametre_numdoc,parametre_vehicule,parametre_user,
                parametre_mdp,parametre_ca_no,parametre_Date_facture,parametre_R_Facture,parametre_ID_Facture}, parametre_user + " LIKE \"" + user_p+"\" AND "+parametre_mdp+ " LIKE \""+mdp_p+"\"", null, null, null, null);
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
        Parametre parametre = new Parametre(c.getInt(c.getColumnIndex(parametre_de_no)), c.getString(c.getColumnIndex(parametre_ct_num)),19,//c.getInt(c.getColumnIndex(parametre_co_no)),
                c.getInt(c.getColumnIndex(parametre_do_souche)),c.getString(c.getColumnIndex(parametre_affaire)),c.getString(c.getColumnIndex(parametre_numdoc)),
                c.getString(c.getColumnIndex(parametre_vehicule)),c.getString(c.getColumnIndex(parametre_user)), c.getString(c.getColumnIndex(parametre_mdp)),
                null,c.getString(c.getColumnIndex(parametre_Date_facture)),c.getInt(c.getColumnIndex(parametre_R_Facture)),c.getInt(c.getColumnIndex(parametre_ID_Facture)));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return parametre;
    }

    public int updateParametre(int id, Parametre parametre){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(parametre_vehicule, parametre.getVehicule());
        values.put(parametre_affaire, parametre.getAffaire());
        values.put(parametre_co_no, parametre.getCo_no());
        values.put(parametre_ct_num, parametre.getCt_num());
        values.put(parametre_Date_facture, parametre.getDate_facture());
        values.put(parametre_de_no, parametre.getDe_no());
        values.put(parametre_do_souche, parametre.getDo_souche());
        values.put(parametre_ID_Facture, parametre.getID_Facture());
        values.put(parametre_mdp, parametre.getMdp());
        values.put(parametre_user, parametre.getUser());
        values.put(parametre_ca_no, parametre.getCa_no().getCa_no());
        values.put(parametre_numdoc, parametre.getNumdoc());
        values.put(parametre_R_Facture, parametre.getR_Facture());
        return bdd.update(TABLE_PARAMETRE, values, id + " = " +id, null);
    }

    public int removeArticleWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        return bdd.delete(TABLE_PARAMETRE, id + " = " +id, null);
    }

}
