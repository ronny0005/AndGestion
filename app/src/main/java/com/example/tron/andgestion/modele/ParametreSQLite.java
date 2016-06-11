package com.example.tron.andgestion.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by T.Ron on 06/06/2016.
 */
public class ParametreSQLite extends SQLiteOpenHelper {

    private static final String TABLE_PARAMETRE = "table_parametre";
    private static final String id="id";
    private static final String de_no="de_no";
    private static final String ct_num="ct_num";
    private static final String do_souche="do_souche";
    private static final String affaire="affaire";
    private static final String numdoc="numdoc";
    private static final String vehicule="vehicule";
    private static final String user="user";
    private static final String mdp="mdp";
    private static final String ca_no="ca_no";
    private static final String Date_facture="Date_facture";
    private static final String R_Facture="R_Facture";
    private static final String ID_Facture="ID_Facture";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_PARAMETRE + " ("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + de_no + " TEXT NOT NULL, "
            + ct_num + " TEXT NOT NULL,"
            + do_souche + " TEXT NOT NULL,"
            + affaire + " TEXT NOT NULL,"
            + numdoc + " TEXT NOT NULL,"
            + vehicule + " TEXT NOT NULL,"
            + user + " TEXT NOT NULL,"
            + mdp + " TEXT NOT NULL,"
            + ca_no+ " TEXT NOT NULL,"
            + Date_facture+ " TEXT NOT NULL,"
            + R_Facture+ " TEXT NOT NULL,"
            + ID_Facture+ " TEXT NOT NULL)";

    public ParametreSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
        db.execSQL("DROP TABLE " + TABLE_PARAMETRE + ";");
       // onCreate(db);
    }

}
