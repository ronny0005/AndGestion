package com.example.tron.andgestion.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class DatabaseSQLite extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    private Context dbContext;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "etatCamlait.db";
    public final static String DATABASE_PATH = "/data/data/com.example.tron.andgestion/databases/";

    // Table Names
    private static final String TABLE_DEPOT = "table_depot";
    private static final String TABLE_CAISSE = "table_caisse";
    private static final String TABLE_PARAMETRE = "table_parametre";
    private static final String TABLE_CLIENT = "table_client";
    private static final String TABLE_ARTICLE = "table_article";
    private static final String TABLE_LIGNE = "table_ligne";
    private static final String TABLE_ENTETE = "table_entete";
    private static final String TABLE_ARTSTOCK = "table_arstock";

    //gestion facture
    //entete
    private static final String entete_id="id";
    private static final String entete_ref="ref";
    private static final String entete_entete="entete";
    private static final String entete_DO_Date="DO_Date";
    private static final String entete_id_client="cient";
    private static final String entete_nouveau="nouveau";
    private static final String entete_statut="statut";
    private static final String entete_type_paiement="type_paiement";
    private static final String entete_mtt_avance="mtt_avance";
    private static final String entete_latitude="latitude";
    private static final String entete_longitude="longitude";
    private static final String entete_totalTTC="totalTTC";    
	private static final String entete_commit="entete_commit";

    //ligne
    private static final String ligne_id="id";
    private static final String ligne_entete="entete";
    private static final String ligne_DO_Date="DO_Date";
    private static final String ligne_AR_Ref="AR_Ref";
    private static final String ligne_AR_Design="AR_Design";
    private static final String ligne_DL_Qte="DL_Qte";
    private static final String ligne_DL_PrixUnitaire="DL_PrixUnitaire";
    private static final String ligne_DL_Taxe1="DL_Taxe1";
    private static final String ligne_DL_Taxe2="DL_Taxe2";
    private static final String ligne_DL_Taxe3="DL_Taxe3";
    private static final String ligne_DL_MontantTTC="DL_MontantTTC";
    private static final String ligne_DL_Ligne="DL_Ligne";
    private static final String ligne_DL_PrixVente="DL_PrixVente";




    //colonne caisse
    private static final String caisse_id="id";
    private static final String caisse_jo_num="jo_num";
    private static final String caisse_ca_intitule="ca_intitule";
    private static final String caisse_ca_no="ca_no";
    private static final String caisse_nocaissier="nocaissier";

    //colonne depot
    private static final String depot_id="id";
    private static final String depot_nom="nom";

    //colonne artstock
    private static final String stock_id="id",stock_AS_MontSto="AS_MontSto",
            stock_DE_No="DE_No",
            stock_AR_Ref="AR_Ref",
            stock_AS_QteSto="AS_QteSto";

    //colonne article
    private static final String article_id="id";
    private static final String article_ar_ref="ar_ref";
    private static final String article_ar_design="ar_design";
    private static final String article_prix_vente="prix_vente";
    private static final String article_ar_uniteven="ar_uniteven";
    private static final String article_ar_prixach="ar_prixach";
    private static final String article_fa_codefamille="fa_codefamille";
    private static final String article_ar_prixven="ar_prixven";
    private static final String article_qte_vendue="qte_vendue";
    private static final String article_taxe1="taxe1";
    private static final String article_taxe2="taxe2";
    private static final String article_taxe3="taxe3";
    private static final String article_vehicule="vehicule";
    private static final String article_cr="cr";


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
    private static final String client_id="id";
    private static final String client_intitule="intitule";
    private static final String client_num="num";
    private static final String client_numprinc="numprinc";
    private static final String client_cattarif="cattarif";
    private static final String client_catcompta="catcompta";

    private static final String CREATE_ENTETE = "CREATE TABLE " + TABLE_ENTETE + " ("
            + entete_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + entete_ref + " TEXT NOT NULL, "
            + entete_entete+ " TEXT NOT NULL,"
            + entete_DO_Date + " TEXT NOT NULL,"
            + entete_id_client + " TEXT NOT NULL,"
            + entete_nouveau + " TEXT NOT NULL,"
            + entete_statut + " TEXT NOT NULL,"
            + entete_type_paiement + " TEXT NOT NULL,"
            + entete_mtt_avance + " TEXT NOT NULL,"
            + entete_latitude + " TEXT NOT NULL,"
            + entete_longitude + " TEXT NOT NULL,"
            + entete_commit + " TEXT NOT NULL,"
            + entete_totalTTC + " TEXT NOT NULL);";

    private static final String CREATE_LIGNE = "CREATE TABLE " + TABLE_LIGNE + " ("
            + ligne_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ligne_entete+ " TEXT NOT NULL, "
            + ligne_DO_Date+ " TEXT NOT NULL,"
            + ligne_AR_Ref + " TEXT NOT NULL,"
            + ligne_AR_Design + " TEXT NOT NULL, "
            + ligne_DL_Qte + " TEXT NOT NULL,"
            + ligne_DL_PrixUnitaire + " TEXT NOT NULL,"
            + ligne_DL_Taxe1+ " TEXT NOT NULL, "
            + ligne_DL_Taxe2+ " TEXT NOT NULL,"
            + ligne_DL_Taxe3+ " TEXT NOT NULL,"
            + ligne_DL_MontantTTC+ " TEXT NOT NULL,"
            + ligne_DL_PrixVente+ " TEXT NOT NULL,"
            + ligne_DL_Ligne + " TEXT NOT NULL);";

    private static final String CREATE_CAISSE = "CREATE TABLE " + TABLE_CAISSE + " ("
            + caisse_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + caisse_jo_num + " TEXT NOT NULL, "
            + caisse_ca_intitule + " TEXT NOT NULL,"
            + caisse_ca_no + " TEXT NOT NULL,"
            + caisse_nocaissier + " TEXT NOT NULL);";

    private static final String CREATE_ARTSTOCK = "CREATE TABLE " + TABLE_ARTSTOCK+ " ("
            + stock_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + stock_DE_No+ " TEXT NOT NULL,"
            + stock_AR_Ref+ " TEXT NOT NULL,"
            + stock_AS_MontSto+ " TEXT NOT NULL,"
            + stock_AS_QteSto+ " TEXT NOT NULL);";

    private static final String CREATE_DEPOT = "CREATE TABLE " + TABLE_DEPOT+ " ("
            + depot_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + depot_nom+ " TEXT NOT NULL);";

    private static final String CREATE_ARTICLE = "CREATE TABLE " + TABLE_ARTICLE + " ("
            + article_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + article_ar_design+ " TEXT NOT NULL, "
            + article_ar_prixach+ " TEXT NOT NULL,"
            + article_ar_prixven+ " TEXT NOT NULL,"
            + article_ar_ref+ " TEXT NOT NULL, "
            + article_ar_uniteven+ " TEXT NOT NULL,"
            + article_cr+ " TEXT NOT NULL,"
            + article_fa_codefamille+ " TEXT NOT NULL, "
            + article_prix_vente+ " TEXT NOT NULL,"
            + article_qte_vendue+ " TEXT NOT NULL,"
            + article_taxe1+ " TEXT NOT NULL, "
            + article_taxe2+ " TEXT NOT NULL,"
            + article_taxe3+ " TEXT NOT NULL,"
            + article_vehicule+ " TEXT NOT NULL);";

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
        this.dbContext = context;
        // checking database and open it if exists
        if (checkDataBase()) {
            openDataBase();
        } else
        {
            try {
                this.getReadableDatabase();
                copyDataBase();
                this.close();
                openDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
            Toast.makeText(context, "Initial database is created", Toast.LENGTH_LONG).show();
        }
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = dbContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        //= SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        boolean exist = false;
        try {
            String dbPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.v("db log", "database does't exist");
        }

        if (checkDB != null) {
            exist = true;
            checkDB.close();
        }
        return exist;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_CAISSE);
        db.execSQL(CREATE_PARAMETRE);
        db.execSQL(CREATE_CLIENT);
        db.execSQL(CREATE_ARTICLE);
        db.execSQL(CREATE_DEPOT);
        db.execSQL(CREATE_ENTETE);
        db.execSQL(CREATE_LIGNE);
        db.execSQL(CREATE_ARTSTOCK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPOT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAISSE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETRE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTETE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIGNE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTSTOCK + ";");
        onCreate(db);
    }

    public void recreate(){
        SQLiteDatabase bdd = this.getWritableDatabase();
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPOT + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_CAISSE + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAMETRE + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTETE + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_LIGNE + ";");
        bdd.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTSTOCK + ";");
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

    public long insertDepot(Depot depot){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(depot_id, depot.getId());
        values.put(depot_nom, depot.getNom());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_DEPOT, null, values);
    }

    public Depot getDepotWithId(int id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        Cursor c = bdd.query(TABLE_DEPOT, new String[] {depot_id,depot_nom}, depot_id+ " = " + id_c, null, null, null, null);
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
        Depot depot = new Depot(c.getInt(c.getColumnIndex(depot_id)),c.getString(c.getColumnIndex(depot_nom)));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();
        //On retourne le livre
        return depot;
    }

    public int updateDepot(int id, Depot depot){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(depot_nom, depot.getNom());
        return bdd.update(TABLE_DEPOT, values, depot_id + " = " +id, null);
    }

    public int removeDepotWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        return bdd.delete(TABLE_DEPOT, depot_id + " = " +id, null);
    }

    public long insertArticle(ArticleServeur article){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(article_ar_design, article.getAr_design());
        values.put(article_qte_vendue, article.getQte_vendue());
        values.put(article_taxe1, article.getTaxe1());
        values.put(article_prix_vente, article.getPrix_vente());
        values.put(article_fa_codefamille, article.getFa_codefamille());
        values.put(article_cr,"");// article.getCr());
        values.put(article_vehicule, "");//article.getVehicule());
        values.put(article_ar_uniteven, article.getAr_uniteven());
        values.put(article_taxe2, article.getTaxe2());
        values.put(article_taxe3, article.getTaxe3());
        values.put(article_ar_prixven, article.getAr_prixven());
        values.put(article_ar_prixach, article.getAr_prixach());
        values.put(article_ar_ref, article.getAr_ref());
        //values.put(article_id, article.getId());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_ARTICLE, null, values);
    }

    public ArticleServeur getArticleWithId(String id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        Cursor c = bdd.query(TABLE_ARTICLE, new String[] {article_id,article_ar_ref,article_ar_design,article_prix_vente,article_ar_uniteven,article_ar_prixach,article_fa_codefamille,article_ar_prixven,article_qte_vendue,article_taxe1,article_taxe2,article_taxe3/*,article_vehicule,article_cr*/}, article_ar_ref+ " = '" + id_c+"'", null, null, null, null);
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
        ArticleServeur article= new ArticleServeur(c.getString(c.getColumnIndex(article_ar_ref)), c.getString(c.getColumnIndex(article_ar_design)),
                c.getDouble(c.getColumnIndex(article_ar_prixach)),c.getDouble(c.getColumnIndex(article_taxe1)),c.getDouble(c.getColumnIndex(article_taxe2)),c.getDouble(c.getColumnIndex(article_taxe3)));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return article;
    }

    public int updateArticle(String id, ArticleServeur article){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(article_ar_design, article.getAr_design());
        values.put(article_ar_prixach, article.getAr_prixach());
        values.put(article_ar_prixven, article.getAr_prixven());
        values.put(article_ar_ref, article.getAr_ref());
        values.put(article_ar_uniteven, article.getAr_uniteven());
        values.put(article_cr, article.getCr());
        values.put(article_fa_codefamille, article.getFa_codefamille());
        values.put(article_prix_vente, article.getPrix_vente());
        values.put(article_qte_vendue, article.getQte_vendue());
        values.put(article_taxe1, article.getTaxe1());
        values.put(article_taxe2, article.getTaxe2());
        values.put(article_taxe3, article.getTaxe3());
        return bdd.update(TABLE_ARTICLE, values, article_ar_ref+ " = " +id, null);
    }

    public int removeArticleWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        return bdd.delete(TABLE_ARTICLE, article_ar_ref + " = " +id, null);
    }

    public long insertClient(Client client){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
       // values.put(client_id,client.getId());
        values.put(client_catcompta,client.getCatcompta());
       values.put(client_cattarif, client.getCattarif());
        values.put(client_intitule, client.getIntitule());
        values.put(client_num, client.getNum());
        values.put(client_numprinc, client.getNumprinc());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_CLIENT, null, values);
    }

    public Client getClientWithId(String id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        Cursor c = bdd.query(TABLE_CLIENT, new String[] {client_id,client_cattarif,client_intitule,client_catcompta,client_numprinc,client_num}, client_num+ " = '" + id_c+"'", null, null, null, null);
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
        System.out.println(parametre);
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

    public int removeParametreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        return bdd.delete(TABLE_PARAMETRE, id + " = " +id, null);
    }

    public long insertLigne(Ligne ligne){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(ligne_AR_Design, ligne.getAR_Design());
        values.put(ligne_AR_Ref, ligne.getAR_Ref());
        values.put(ligne_DL_Ligne, ligne.getDL_Ligne());
        values.put(ligne_DL_MontantTTC, ligne.getDL_MontantTTC());
        values.put(ligne_DL_PrixUnitaire, ligne.getDL_PrixUnitaire());
        values.put(ligne_DL_Qte, ligne.getDL_Qte());
        values.put(ligne_DL_Taxe1, ligne.getDL_Taxe1());
        values.put(ligne_DL_Taxe2, ligne.getDL_Taxe2());
        values.put(ligne_DL_Taxe3, ligne.getDL_Taxe3());
        values.put(ligne_DO_Date, ligne.getDO_Date());
        values.put(ligne_entete , ligne.getEntete());
        values.put(ligne_DL_PrixVente , ligne.getDL_PrixVente());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_LIGNE, null, values);
    }

    public ArrayList<Ligne> getLigneWithId(String id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        ArrayList<Ligne>  list = new ArrayList<Ligne>();
        SQLiteDatabase bdd = this.getReadableDatabase();
        Cursor c = bdd.query(TABLE_LIGNE, new String[] {ligne_id,ligne_entete,ligne_DO_Date,ligne_AR_Ref,ligne_AR_Design,ligne_DL_Qte,ligne_DL_PrixUnitaire,ligne_DL_Taxe1,ligne_DL_Taxe2,ligne_DL_Taxe3,ligne_DL_MontantTTC,ligne_DL_Ligne,ligne_DL_PrixVente}, ligne_entete+ " = '" + id_c+"'", null, null, null, null);
        while(c.moveToNext()){
            list.add(cursorToLigne(c));
        }
        c.close();
        return list;
    }


    //Cette méthode permet de convertir un cursor en un livre
    private Ligne cursorToLigne(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        //On créé un livre
        System.out.println(c.getString(c.getColumnIndex(ligne_id)));
        Ligne ligne = new Ligne(c.getString(c.getColumnIndex(ligne_id)), c.getString(c.getColumnIndex(ligne_entete)),
                c.getString(c.getColumnIndex(ligne_DO_Date)), c.getString(c.getColumnIndex(ligne_AR_Ref)),c.getString(c.getColumnIndex(ligne_AR_Design)), c.getString(c.getColumnIndex(ligne_DL_Qte)),
                c.getString(c.getColumnIndex(ligne_DL_PrixUnitaire)), c.getString(c.getColumnIndex(ligne_DL_Taxe1)),c.getString(c.getColumnIndex(ligne_DL_Taxe2)), c.getString(c.getColumnIndex(ligne_DL_Taxe3)),
                c.getString(c.getColumnIndex(ligne_DL_MontantTTC)), c.getString(c.getColumnIndex(ligne_DL_Ligne)), c.getString(c.getColumnIndex(ligne_DL_PrixVente)));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        //On retourne le livre
        return ligne;
    }
	
	public int updateLigne(String ent, Ligne ligne){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        ContentValues values = new ContentValues();
		values.put(ligne_AR_Design, ligne.getAR_Design());
        values.put(ligne_AR_Ref, ligne.getAR_Ref());
        values.put(ligne_DL_Ligne, ligne.getDL_Ligne());
        values.put(ligne_DL_MontantTTC, ligne.getDL_MontantTTC());
        values.put(ligne_DL_PrixUnitaire, ligne.getDL_PrixUnitaire());
        values.put(ligne_DL_Qte, ligne.getDL_Qte());
        values.put(ligne_DL_Taxe1, ligne.getDL_Taxe1());
        values.put(ligne_DL_Taxe2, ligne.getDL_Taxe2());
        values.put(ligne_DL_Taxe3, ligne.getDL_Taxe3());
        values.put(ligne_DO_Date, ligne.getDO_Date());
        values.put(ligne_entete , ligne.getEntete());
        values.put(ligne_DL_PrixVente , ligne.getDL_PrixVente());
        return bdd.update(TABLE_LIGNE, values, ligne_id + " = " +ent, null);
    }

    public long insertEntete(Entete entete){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(entete_entete, entete.getEntete());
        values.put(entete_DO_Date,entete.getDO_Date());
        values.put(entete_id_client, entete.getId_client());
        values.put(entete_latitude, entete.getLatitude());
        values.put(entete_longitude, entete.getLongitude());
        values.put(entete_mtt_avance, entete.getMtt_avance());
        values.put(entete_nouveau, entete.getNouveau());
        values.put(entete_ref, entete.getRef());
        values.put(entete_statut, entete.getStatut());
        values.put(entete_totalTTC, entete.getTotalTTC());
        values.put(entete_type_paiement, entete.getType_paiement());
        values.put(entete_commit, entete.getCommit());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_ENTETE, null, values);
    }

    public ArrayList<Entete> getEnteteWithDate(String id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        ArrayList<Entete>  list = new ArrayList<Entete>();
        Cursor c = bdd.query(TABLE_ENTETE, new String[] {entete_id,entete_ref,entete_entete,entete_DO_Date,entete_id_client,entete_nouveau,entete_statut,entete_type_paiement,entete_mtt_avance,entete_latitude,entete_longitude,entete_commit,entete_totalTTC}, entete_DO_Date+ " = '" + id_c+"'", null, null, null, null);
        while(c.moveToNext()){
            list.add(cursorToEntete(c));
        }
        c.close();
        return list;
    }

    public ArrayList<Entete> getEnteteWithCommit(){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        ArrayList<Entete>  list = new ArrayList<Entete>();
        Cursor c = bdd.query(TABLE_ENTETE, new String[] {entete_id,entete_ref,entete_entete,entete_DO_Date,entete_id_client,entete_nouveau,entete_statut,entete_type_paiement,entete_mtt_avance,entete_latitude,entete_longitude,entete_commit,entete_totalTTC}, entete_commit+ " = 'true'", null, null, null, null);
        while(c.moveToNext()){
            list.add(cursorToEntete(c));
        }
        c.close();
        return list;
    }

    public ArrayList<Entete> getEnteteWithEntete(String id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        System.out.println(id_c+" cool");

        SQLiteDatabase bdd = this.getReadableDatabase();
        ArrayList<Entete>  list = new ArrayList<Entete>();
        Cursor c = bdd.query(TABLE_ENTETE, new String[] {entete_id,entete_ref,entete_entete,entete_DO_Date,entete_id_client,entete_nouveau,entete_statut,entete_type_paiement,entete_mtt_avance,entete_latitude,entete_longitude,entete_commit,entete_totalTTC}, entete_entete+ " = '" + id_c+"'", null, null, null, null);
        while(c.moveToNext()){
            list.add(cursorToEntete(c));
        }
        c.close();
        return list;
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Entete cursorToEntete(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        //Sinon on se place sur le premier élément
        //On créé un livre
        Entete entete= new Entete(c.getString(c.getColumnIndex(entete_ref)), c.getString(c.getColumnIndex(entete_entete)), c.getString(c.getColumnIndex(entete_DO_Date)),
                c.getString(c.getColumnIndex(entete_id_client)), c.getString(c.getColumnIndex(entete_nouveau)),c.getString(c.getColumnIndex(entete_statut)), c.getString(c.getColumnIndex(entete_type_paiement)),
                c.getString(c.getColumnIndex(entete_mtt_avance)), c.getString(c.getColumnIndex(entete_latitude)),c.getString(c.getColumnIndex(entete_longitude)),
                c.getString(c.getColumnIndex(entete_totalTTC)));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor


        //On retourne le livre
        return entete;
    }

	public int updateEntete(String ent, Entete entete){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        ContentValues values = new ContentValues();
		values.put(entete_entete, entete.getEntete());
        values.put(entete_DO_Date,entete.getDO_Date());
        values.put(entete_id_client, entete.getId_client());
        values.put(entete_latitude, entete.getLatitude());
        values.put(entete_longitude, entete.getLongitude());
        values.put(entete_mtt_avance, entete.getMtt_avance());
        values.put(entete_nouveau, entete.getNouveau());
        values.put(entete_ref, entete.getRef());
        values.put(entete_statut, entete.getStatut());
        values.put(entete_totalTTC, entete.getTotalTTC());
        values.put(entete_type_paiement, entete.getType_paiement());
		values.put(entete_commit, entete.getCommit());
        return bdd.update(TABLE_ENTETE, values, entete_entete + " = '" +ent+"'", null);
    }
	
    public long insertStock(QteStock stock){
        SQLiteDatabase bdd = this.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(stock_AR_Ref, stock.getAR_Ref());
        values.put(stock_AS_MontSto, stock.getAS_MontSto());
        values.put(stock_AS_QteSto, stock.getAS_QteSto());
        values.put(stock_DE_No, stock.getDE_No());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_ARTSTOCK, null, values);
    }

    public int updateStock(String id, QteStock stock){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        SQLiteDatabase bdd = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(stock_AS_QteSto,stock.getAS_QteSto());
        return bdd.update(TABLE_ARTSTOCK, values, stock_AR_Ref + " = '" +id+"'", null);
    }

    public QteStock getStockWithARRef(String id_c){
        //Récupère dans un Cursor les valeur correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        SQLiteDatabase bdd = this.getReadableDatabase();
        Cursor c = bdd.query(TABLE_ARTSTOCK, new String[] {stock_AS_MontSto,stock_DE_No,stock_AR_Ref,stock_AS_QteSto}, stock_AR_Ref+ " = '" + id_c+"'", null, null, null, null);
        return cursorToStock(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private QteStock cursorToStock(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        QteStock stock= new QteStock(c.getString(c.getColumnIndex(stock_AS_MontSto)), c.getString(c.getColumnIndex(stock_DE_No)), c.getString(c.getColumnIndex(stock_AR_Ref)),
                c.getString(c.getColumnIndex(stock_AS_QteSto)));
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return stock;
    }

}
