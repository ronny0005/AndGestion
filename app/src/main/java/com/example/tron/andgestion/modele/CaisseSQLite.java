package com.example.tron.andgestion.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class CaisseSQLite extends SQLiteOpenHelper {

    private static final String TABLE_CAISSE = "table_caisse";
    private static final String id="id";
    private static final String jo_num="jo_num";
    private static final String ca_intitule="ca_intitule";
    private static final String ca_no="ca_no";
    private static final String nocaissier="nocaissier";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_CAISSE + " ("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + jo_num + " TEXT NOT NULL, "
            + ca_intitule + " TEXT NOT NULL,"
            + ca_no + " DOUBLE,"
            + nocaissier + " DOUBLE);";

    public CaisseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
        db.execSQL("DROP TABLE " + TABLE_CAISSE + ";");
        onCreate(db);
    }

}
