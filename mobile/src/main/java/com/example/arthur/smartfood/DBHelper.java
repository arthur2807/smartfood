package com.example.arthur.smartfood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String NOME_BANCO   = "cardapioDB";
	private static final int    VERSAO_BANCO = 1;
	
	public DBHelper(Context context) {
		super(context, NOME_BANCO, null, VERSAO_BANCO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"CREATE TABLE cardapio (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"title TEXT NOT NULL, link TEXT, content TEXT, thumbnail TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Utilizar se for atualizar uma nova versão do banco ou seja atualizar
	}

}
