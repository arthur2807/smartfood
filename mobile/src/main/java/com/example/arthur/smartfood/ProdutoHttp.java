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



public class ProdutoHttp {
public static List<Produto> retrievePosts() throws Exception{
		
		//URL url = new URL("https://dl.dropbox.com/s/cp048f8mv3ncak9/lendo.json");
		//https://www.dropbox.com/s/e6isyozd0zj8rcw/produt.json
	URL url = new URL("https://dl.dropbox.com/s/e6isyozd0zj8rcw/produt.json");
		HttpURLConnection conexao = (HttpURLConnection)
				url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setDoInput(true);
		conexao.connect();
		
		if (conexao.getResponseCode() == 200){ // se net estever OK
			return parseProdutoJson(conexao.getInputStream());
		}
		return null;
	}

	private static List<Produto> parseProdutoJson(InputStream inputStream) 
			throws JSONException, Exception {
		
		List<Produto> produtos = new ArrayList<Produto>();
		
		JSONObject json = new JSONObject(
				bytesToString(inputStream));

		JSONArray jsonEquipes = json.getJSONArray("PESSOA");
		for (int i = 0; i < jsonEquipes.length(); i++) {
			JSONObject jsonEquipe = jsonEquipes.getJSONObject(i);
			Produto produto = new Produto(
					jsonEquipe.getString("nome"),
					jsonEquipe.getString("imagem"),jsonEquipe.getString("descricaoo"));
			produtos.add(produto);
		}	
		return produtos;
	}
	
	
	private static String bytesToString(InputStream is) throws IOException{
		byte[] bufferzinho = new byte[1024];
		ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
		int bytesLidos;
		while ((bytesLidos = is.read(bufferzinho)) != -1){
			bufferzao.write(bufferzinho, 0, bytesLidos);
		}
		return new String(bufferzao.toByteArray());
	}

}
