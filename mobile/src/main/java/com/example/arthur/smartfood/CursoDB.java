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


    public long inserir(Turma turma) {
        SQLiteDatabase db = helper.getWritableDatabase();
		Curso curso = turma.cd_curso;
        ContentValues valuesturma = valoresTurma(turma);
		ContentValues valuesCurso = valoresCurso(curso);

        db.insert ("turma", null, valuesturma);
		db.insert("curso",null, valuesCurso);
		turma.favorito = true;
		db.close();
        return turma.cd_turma;
    }

	private ContentValues valoresTurma(Turma turma) {
		ContentValues values = new ContentValues();
		values.put("cd_turma", turma.cd_turma);
		values.put("dc_turma", turma.dc_turma);
		values.put("cd_curso", turma.cd_curso.cd_curso);
		values.put("dc_horario_turma", turma.dc_horario_turma);
		return values;
	}

	private ContentValues valoresCurso(Curso curso){
		ContentValues values = new ContentValues();
		values.put("cd_curso", curso.cd_curso);
		values.put("dc_curso", curso.dc_curso);
		values.put("imglink",  curso.imglink);
		return values;
	}

	public int excluir(Turma turma) {
		SQLiteDatabase db = helper.getWritableDatabase();
		Curso curso = turma.cd_curso;
		int rows = db.delete("curso", "cd_curso = " + curso.cd_curso, null);
		rows += db.delete("turma", "cd_turma = " + turma.cd_turma,null);
		turma.favorito = false;
		db.close();

		return rows;
	}

	public boolean favorito(Turma turma) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db
				.rawQuery(
						"select cd_turma, dc_turma, cd_curso, dc_horario_turma from turma where cd_turma = ?",
						new String[]{String.valueOf(turma.cd_turma)});

		boolean resultado = false;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			turma.cd_turma = cursor.getLong(cursor.getColumnIndex("cd_turma"));
			resultado = true;
		}
		cursor.close();
		db.close();
		return resultado;
	}
	
	

	public List<Turma> todosTurmas() {
		List<Turma> turmas = new ArrayList<Turma>();

		SQLiteDatabase db = helper.getReadableDatabase();

		/*Cursor cursorC = db.rawQuery(
				"select c.cd_curso, c.dc_curso,c.imglink from curso c", null);*/
		Cursor cursorT = db.rawQuery(
				"select c.cd_curso, c.dc_curso,c.imglink ,t.cd_turma ,t.dc_turma ,t.cd_curso ,t.dc_horario_turma " +
						"from turma t, curso c", null);

		while (cursorT.moveToNext()) {
			//Curso curso = preencherCurso(cursorC);
			Turma turma = preencherTurma(cursorT);
			turmas.add(turma);
		}
		//cursorC.close();
		cursorT.close();
		db.close();
		return turmas;
	}

	private Turma preencherTurma(Cursor cursorT) {
		Curso curso = new Curso(cursorT.getLong(0),
								cursorT.getString(1),
								cursorT.getString(2));
		Turma turma = new Turma(cursorT.getLong(3),
								cursorT.getString(4),
								curso,
								cursorT.getString(6));
		return turma;
	}

	/*private Curso preencherCurso(Cursor cursor) {
		long cd_curso = cursor.getLong(0);
		String dc_curso = cursor.getString(1);
		String imglink = cursor.getString(2);
		Curso curso = new Curso(cd_curso, dc_curso, imglink);
		curso.favorito = true;
		return curso;
	}*/
}
