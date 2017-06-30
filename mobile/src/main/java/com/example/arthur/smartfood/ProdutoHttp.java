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
	URL url = new URL("http://appagenda.comeze.com/php/GetCursos.php");
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

		JSONArray jsonEquipes = json.getJSONArray("cursos");
		for (int i = 0; i < jsonEquipes.length(); i++) {
			JSONObject jsonEquipe = jsonEquipes.getJSONObject(i);
			Produto produto = new Produto(
					jsonEquipe.getString("nome"),
					jsonEquipe.getString("imglink"));
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

	public static List<Turma> turmasJson(Produto produto) throws Exception{

		URL url = new URL("http://appagenda.comeze.com/php/GetTurmas.php?cd_curso="+produto.id);
		HttpURLConnection conexao = (HttpURLConnection)
				url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setDoInput(true);
		conexao.connect();

		if (conexao.getResponseCode() == 200){ // se net estever OK
			return parseTurmaJson(conexao.getInputStream());
		}
		return null;
	}

	private static List<Turma> parseTurmaJson(InputStream inputStream)
			throws JSONException, Exception {

		List<Turma> turmas = new ArrayList<>();

		JSONObject json = new JSONObject(
				bytesToString(inputStream));

		JSONArray jsonEquipes = json.getJSONArray("turmas");
		for (int i = 0; i < jsonEquipes.length(); i++) {
			JSONObject jsonEquipe = jsonEquipes.getJSONObject(i);
			Turma turma = new Turma(
					jsonEquipe.getString("dc_turma"),
					(new Produto( Long.parseLong(jsonEquipe.getString("cd_curso")),
							jsonEquipe.getString("dc_curso"),
							jsonEquipe.getString("imglink")
							)),
					jsonEquipe.getString("dc_horario_turma"));
			turmas.add(turma);
		}
		return turmas;
	}
}
