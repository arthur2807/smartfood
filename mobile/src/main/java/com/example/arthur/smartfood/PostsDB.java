package com.example.arthur.smartfood;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PostsDB {

	private DBHelper helper;
	
	public PostsDB(Context contexto){
		helper = new DBHelper(contexto);
	}
	
	public long inserir(Post post){
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = valoresPorPost(post);
		
		long id = db.insert("posts", null, values);
		post.id = id;
		post.favorito = true;
		
		db.close();
		
		return id;
	}

	private ContentValues valoresPorPost(Post post) {
		ContentValues values = new ContentValues();
		values.put("title", post.title);
		values.put("link", post.link);
		values.put("content", post.content);
		values.put("thumbnail", post.thumbnail);
		return values;
	}
	
	public int excluir(Post post){
		SQLiteDatabase db = helper.getWritableDatabase();
		
		int rows = db.delete("posts", "_id = "+ post.id, null);
		post.favorito = false;
		db.close();
		
		return rows;
	}
	
	public List<Post> todosOsPosts(){
		List<Post> posts = new ArrayList<Post>();
		
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery(
				"select * from posts", null);
		
		while (cursor.moveToNext()){
			Post post = preencherPost(cursor);
			posts.add(post);
		}
		cursor.close();
		db.close();
		return posts;
	}

	private Post preencherPost(Cursor cursor) {
		long id          = cursor.getLong(0);
		String title     = cursor.getString(1);
		String link      = cursor.getString(2);
		String content   = cursor.getString(3);
		String thumbnail = cursor.getString(4);
		
		Post post = new Post(
				id,
				title,
				link,
				thumbnail,
				content
			);
		post.favorito = true;
		
		return post;
	}
}



