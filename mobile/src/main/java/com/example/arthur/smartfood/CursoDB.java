package com.example.arthur.smartfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CursoDB {

private DBHelper helper;

	public CursoDB(Context contexto) {
		helper = new DBHelper(contexto);
	}

	/*public long inserir(Produto produto) {
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = valoresPorPost(produto);

		long id = db.insert("cardapio", null, values);
		produto.id = id;
		produto.favorito = true;
		db.close();
		return id;
	}*/


    public long inserir(Curso curso) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valoresPorPost(curso);

        long id = db.insert	("curso", null, values);
        curso.cd_curso = id;
        curso.favorito = true;
        db.close();
        return id;
    }

	private ContentValues valoresPorPost(Curso curso) {
		ContentValues values = new ContentValues();
		values.put("cd_curso", curso.cd_curso);
		values.put("descricao", curso.dc_curso);
		values.put("imglink", curso.imglink);
		return values;
	}

	public int excluir(Curso produto) {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db.delete("cardapio", "_id = " + produto.id, null);
		produto.favorito = false;
		db.close();

		return rows;
	}

	public boolean favorito(Produto produto) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select _id,title, content,thumbnail from cardapio where title = ?",
						new String[] { produto.nome });

		boolean resultado = false;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			produto.id = cursor.getLong(cursor.getColumnIndex("_id"));
			resultado = true;
		}
		cursor.close();
		db.close();
		return resultado;
	}
	
	

	public List<Produto> todosOsPosts() {
		List<Produto> produtos = new ArrayList<Produto>();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery(
				"select _id, title,content,thumbnail from cardapio", null);

		while (cursor.moveToNext()) {
			Produto post = preencherPost(cursor);

			produtos.add(post);
		}
		cursor.close();
		db.close();
		return produtos;
	}

	private Produto preencherPost(Cursor cursor) {
		long id = cursor.getLong(0);
		String title = cursor.getString(1);

		String content = cursor.getString(2);
		String thumbnail = cursor.getString(3);

		Produto post = new Produto(id, title, thumbnail, content);
		post.favorito = true;

		return post;
	}
}
