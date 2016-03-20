package com.example.tron.androidgestion.bddlocal.depot;

/**
 * Created by T.Ron$ on 11/03/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSQLiteDepot extends SQLiteOpenHelper {

    private static final String TABLE_CLIENT = "table_depot";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "NOM";
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_CLIENT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOM + " STRING NOT NULL)";

    public MaBaseSQLiteDepot(Context context, String name, CursorFactory factory, int version) {
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
        db.execSQL("DROP TABLE " + TABLE_CLIENT + ";");
        onCreate(db);
    }

}
