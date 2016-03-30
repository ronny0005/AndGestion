package com.example.tron.andgestion.bddlocal.facture;

/**
 * Created by T.Ron$ on 11/03/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSQLiteFacture extends SQLiteOpenHelper {

    private static final String TABLE = "table_facture";
    private static final String COL_ID = "ID";
    private static final String COL_REF = "REF";
    private static final String COL_ID_CLIENT = "ID_CLIENT";
    private static final String COL_ID_DEPOT = "ID_DEPOT";
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_REF + " STRING NOT NULL, "
            + COL_ID_CLIENT + " INTEGER NOT NULL,"+ COL_ID_DEPOT + " INTEGER NOT NULL );";

    public MaBaseSQLiteFacture(Context context, String name, CursorFactory factory, int version) {
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
        db.execSQL("DROP TABLE " + TABLE + ";");
        onCreate(db);
    }

}
