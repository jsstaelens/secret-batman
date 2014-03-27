package com.example.testmembership;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSQLite extends SQLiteOpenHelper {
	 
	private static final String TABLE_UTILISATEURS = "table_utilisateurs";
	private static final String COL_ID = "ID";
	private static final String COL_PSEUDO = "Pseudo";
	//private static final String COL_PASSWORD = "Motdepasse";
	private static final String COL_AGE = "Age";
	private static final String COL_EMAIL = "Email";

	
 
	private static final String CREATE_BDD = "CREATE TABLE " + TABLE_UTILISATEURS + 
	" (" + COL_ID +" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "
		+ COL_PSEUDO	+ " TEXT NOT NULL , "
		//+ COL_PASSWORD + " TEXT NOT NULL , "
		+ COL_AGE + " INTEGER NOT NULL , "
		+ COL_EMAIL + " TEXT NOT NULL );";
 
	public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		//on crée la table à partir de la requête écrite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
		//comme ça lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + TABLE_UTILISATEURS + ";");
		onCreate(db);
	}
 
}

