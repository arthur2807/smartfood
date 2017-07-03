package com.example.arthur.smartfood;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoDB {

private DBHelper helper;

	public ProdutoDB(Context contexto) {
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


    public long inserir(Produto produto) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valoresPorPost(produto);

        long id = db.insert("curso", null, values);
        produto.id = id;
        produto.favorito = true;
        db.close();
        return id;
    }

	private ContentValues valoresPorPost(Produto produto) {
		ContentValues values = new ContentValues();
		values.put("title", produto.nome);
		values.put("content", produto.descricao);
		values.put("thumbnail", produto.imagem);
		return values;
	}

	public int excluir(Produto produto) {
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
