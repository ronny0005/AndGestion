package com.example.tron.andgestion.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class ArticleSQLite extends SQLiteOpenHelper {

    private static final String TABLE_ARTICLES = "table_articles";
    private static final String id="id";
    private static final String ar_ref="ar_ref";
    private static final String ar_design="ar_design";
    private static final String prix_vente="prix_vente";
    private static final String ar_uniteven="ar_uniteven";
    private static final String ar_prixach="ar_prixach";
    private static final String fa_codefamille="fa_codefamille";
    private static final String ar_prixven="ar_prixven";
    private static final String qte_vendue="qte_vendue";
    private static final String taxe1="taxe1";
    private static final String taxe2="taxe2";
    private static final String taxe3="taxe3";
    private static final String vehicule="vehicule";
    private static final String cr="cr";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_ARTICLES + " ("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ar_ref + " TEXT NOT NULL, "
            + ar_design + " TEXT NOT NULL,"
            + prix_vente + " DOUBLE,"
            + ar_uniteven + " DOUBLE,"
            + ar_prixach + " DOUBLE,"
            + fa_codefamille + " TEXT NOT NULL,"
            + ar_prixven + " DOUBLE,"
            + qte_vendue + " DOUBLE,"
            + taxe1+ " DOUBLE,"
            + taxe2+ " DOUBLE,"
            + taxe3+ " DOUBLE,"
            + vehicule+ " TEXT NOT NULL,"
            + cr + " TEXT NOT NULL);";

    public ArticleSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_ARTICLES + ";");
        onCreate(db);
    }

}
