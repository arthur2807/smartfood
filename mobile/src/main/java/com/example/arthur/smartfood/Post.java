package com.example.arthur.smartfood;

import java.io.Serializable;

public class Post implements Serializable {
	public long id;
	public String title;
	public String link;
	public String thumbnail;
	public String content;
	public boolean favorito;
	
	// Construtor usado no banco
	public Post(long id, String title, String link, String thumbnail, String content) {
		this(title, link, thumbnail, content);
		this.id = id;
	}
	// Construtor usado no parser
	public Post(String title, String link, String thumbnail, String content) {
		this.title = title;
		this.link = link;
		this.thumbnail = thumbnail;
		this.content = content;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
