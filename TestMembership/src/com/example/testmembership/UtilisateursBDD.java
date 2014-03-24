package com.example.testmembership;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UtilisateursBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "whattowatch.db";
 
	private static final String TABLE_UTILISATEURS = "table_utilisateurs";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;

	private static final String COL_PSEUDO = "Pseudo";
	private static final int NUM_COL_PSEUDO = 1;

	//private static final String COL_PASSWORD = "Motdepasse";
	//private static final int NUM_COL_PASSWORD = 2;

	private static final String COL_AGE = "Age";
	private static final int NUM_COL_AGE = 2;

	private static final String COL_EMAIL = "Email";
	private static final int NUM_COL_EMAIL = 3;

	
 
	private SQLiteDatabase bdd;
 
	private MaBaseSQLite maBaseSQLite;
 
	public UtilisateursBDD(Context context){
		//On crée la BDD et sa table
		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 
	public long insertUtilisateur(Utilisateur utilisateur){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_AGE, utilisateur.getAge());
		values.put(COL_PSEUDO, utilisateur.getPseudo());
		//values.put(COL_PASSWORD, utilisateur.getPassword());
		values.put(COL_EMAIL, utilisateur.getEmail());

		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_UTILISATEURS, null, values);
	}
 
	public int updateLivre(int id, Utilisateur utilisateur){
		//La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
		ContentValues values = new ContentValues();
		values.put(COL_AGE, utilisateur.getAge());
		values.put(COL_PSEUDO, utilisateur.getPseudo());
		//values.put(COL_PASSWORD, utilisateur.getPassword());
		values.put(COL_EMAIL, utilisateur.getEmail());

		return bdd.update(TABLE_UTILISATEURS, values, COL_ID + " = " +id, null);
	}
 
	public int removeUtilisateurWithID(int id){
		//Suppression d'un livre de la BDD grâce à l'ID
		return bdd.delete(TABLE_UTILISATEURS, COL_ID + " = " +id, null);
	}
 
	public Utilisateur getUtilisateurWithPseudo(String pseudo){
		//Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
		//Cursor c = bdd.query(TABLE_UTILISATEURS, new String[] {COL_ID, COL_PSEUDO, COL_PASSWORD, COL_AGE, COL_EMAIL}, COL_PSEUDO + " LIKE \"" + pseudo +"\"", null, null, null, null);
		Cursor c = bdd.query(TABLE_UTILISATEURS, new String[] {COL_ID, COL_PSEUDO, COL_AGE, COL_EMAIL}, COL_PSEUDO + " LIKE \"" + pseudo +"\"", null, null, null, null);

		return cursorToUtilisateur(c);
	}
 
	//Cette méthode permet de convertir un cursor en un livre
	private Utilisateur cursorToUtilisateur(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		//On créé un livre
		//Utilisateur utilisateur = new Utilisateur(c.getInt(NUM_COL_ID), c.getInt(NUM_COL_AGE), c.getString(NUM_COL_PSEUDO), c.getString(NUM_COL_EMAIL), c.getString(NUM_COL_PASSWORD) );
		Utilisateur utilisateur = new Utilisateur(c.getInt(NUM_COL_ID), c.getInt(NUM_COL_AGE), c.getString(NUM_COL_PSEUDO), c.getString(NUM_COL_EMAIL) );

		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return utilisateur;
	}
}