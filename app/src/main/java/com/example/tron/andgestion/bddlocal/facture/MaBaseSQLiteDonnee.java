package com.example.tron.andgestion.bddlocal.facture;

/**
 * Created by T.Ron$ on 11/03/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MaBaseSQLiteDonnee extends SQLiteOpenHelper {

    private static final String TABLE_DEPOT = "table_donnee";
    private static final String COL_ID = "ID";
    private static final String COL_DATE = "DATE";
    private static final String COL_CLIENT = "CLIENT";
    private static final String COL_DESIGNATION = "DESIGNATION";
    private static final String COL_PU = "PU";
    private static final String COL_QTE = "QTE";
    private static final String COL_QTEC = "QTEC";
    private static final String COL_TOTAL = "TOTAL";
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_DEPOT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DATE + " DATE NOT NULL, "
            + COL_CLIENT + " TEXT NOT NULL,"+ COL_DESIGNATION + " TEXT NOT NULL," +
            ""+COL_PU+" FLOAT NOT NULL,"+COL_QTE+" FLOAT NOT NULL,"+COL_QTEC+" FLOAT NOT NULL, "+COL_TOTAL+" FLOAT NOT NULL );";

    public MaBaseSQLiteDonnee(Context context, String name, CursorFactory factory, int version) {
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
