package com.example.arthur.smartfood;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostsParser {

	public static List<Post> retrievePosts() throws Exception{
		
		URL url = new URL("https://dl.dropboxusercontent.com/u/6802536/" +
				"posts_blogger.json");
		HttpURLConnection conexao = (HttpURLConnection)url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setReadTimeout(10000);
		conexao.setConnectTimeout(15000);
		conexao.setDoInput(true);
		
		conexao.connect();
		if (conexao.getResponseCode() == 200){ // HTTP_OK
			return parsePostsJson(conexao.getInputStream());
		}
		return null;
	}

	private static List<Post> parsePostsJson(InputStream inputStream) 
			throws JSONException, Exception {
		
		List<Post> posts = new ArrayList<Post>();
		
		JSONObject json = new JSONObject(streamToString(inputStream));
		JSONArray jsonPosts = json.getJSONArray("items");
		for (int i = 0; i < jsonPosts.length(); i++) {
			JSONObject jsonPost = jsonPosts.getJSONObject(i);
			String title     = jsonPost.getString("title");
			String url       = jsonPost.getString("url");
			String content   = jsonPost.getString("content"); 
			String thumbnail = null;
			if (jsonPost.has("images")){
				thumbnail = jsonPost
						.getJSONArray("images")
						.getJSONObject(0)
						.getString("url");
			}
			Post post = new Post(title, url, thumbnail, content);
			posts.add(post);
		}
		return posts;
	}
	
	private static String streamToString(InputStream is) throws IOException{
		byte[] bufferzinho = new byte[1024];
		ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
		int bytesLidos;
		while ((bytesLidos = is.read(bufferzinho)) != -1){
			bufferzao.write(bufferzinho, 0, bytesLidos);
		}
		return new String(bufferzao.toByteArray(), "ISO8859-1");
	}
	
}
