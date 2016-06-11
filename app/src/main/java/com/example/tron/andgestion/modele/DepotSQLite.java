package com.example.tron.andgestion.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class DepotSQLite extends SQLiteOpenHelper {

    private static final String TABLE_DEPOT = "table_depot";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "NOM";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_DEPOT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NOM + " TEXT NOT NULL);";

    public DepotSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
        db.execSQL("DROP TABLE " + TABLE_DEPOT + ";");
        onCreate(db);
    }

}
