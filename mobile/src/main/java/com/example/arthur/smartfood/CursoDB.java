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

	public int excluir(Curso curso) {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db.delete("curso", "cd_curso = " + curso.cd_curso, null);
		curso.favorito = false;
		db.close();

		return rows;
	}

	public boolean favorito(Curso curso) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select _id,title, content,thumbnail from cardapio where title = ?",
						new String[] { curso.dc_curso });

		boolean resultado = false;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			curso.cd_curso = cursor.getLong(cursor.getColumnIndex("_id"));
			resultado = true;
		}
		cursor.close();
		db.close();
		return resultado;
	}
	
	

	public List<Curso> todosOsCursos() {
		List<Curso> cursos = new ArrayList<Curso>();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery(
				"select cd_curso, dc_curso,imglink from curso", null);

		while (cursor.moveToNext()) {
			Curso curso = preencherCurso(cursor);

			cursos.add(curso);
		}
		cursor.close();
		db.close();
		return cursos;
	}

	private Curso preencherCurso(Cursor cursor) {
		long cd_curso = cursor.getLong(0);
		String dc_curso = cursor.getString(1);

		String imglink = cursor.getString(2);

		Curso curso = new Curso(cd_curso, dc_curso, imglink);
		curso.favorito = true;

		return curso;
	}
}
