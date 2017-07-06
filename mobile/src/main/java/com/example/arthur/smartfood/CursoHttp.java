package com.example.arthur.smartfood;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class CursoHttp {

public static List<Curso> retrievePosts() throws Exception{
		
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

	private static List<Curso> parseProdutoJson(InputStream inputStream)
			throws JSONException, Exception {

		List<Curso> cursos = new ArrayList<Curso>();
		
		JSONObject json = new JSONObject(
				bytesToString(inputStream));

		JSONArray jsonEquipes = json.getJSONArray("cursos");
		for (int i = 0; i < jsonEquipes.length(); i++) {
			JSONObject jsonEquipe = jsonEquipes.getJSONObject(i);
			Curso curso = new Curso(
					Long.parseLong(jsonEquipe.getString("dccruso")),
					jsonEquipe.getString("nome"),
					jsonEquipe.getString("imglink"));
			cursos.add(curso);
		}	
		return cursos;
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

	public static List<Turma> turmasJson(Curso curso) throws Exception{

		URL url = new URL("http://appagenda.comeze.com/php/GetTurma.php?cdcursos="+ curso.cd_curso);
		HttpURLConnection conexao = (HttpURLConnection)
				url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setDoInput(true);
		conexao.connect();

		if (conexao.getResponseCode() == 200){ // se net estever OK
			return parseTurmaJson(conexao.getInputStream(),curso);
		}
		return null;
	}

	private static List<Turma> parseTurmaJson(InputStream inputStream, Curso curso)
			throws JSONException, Exception {

		List<Turma> turmas = new ArrayList<>();

		JSONObject json = new JSONObject(
				bytesToString(inputStream));

		JSONArray jsonEquipes = json.getJSONArray("turma");
		for (int i = 0; i < jsonEquipes.length(); i++) {
			JSONObject jsonEquipe = jsonEquipes.getJSONObject(i);
			Turma turma = new Turma(
					Long.parseLong(jsonEquipe.getString("cd_codigo")),
					jsonEquipe.getString("dc_nome"),
					validarCurso(Long.parseLong(jsonEquipe.getString("cd_cursos")), curso),
					jsonEquipe.getString("dc_descricao"));
			turmas.add(turma);
		}
		return turmas;
	}

	private static Curso validarCurso(long cursoJson, Curso cursoin) {
		if (cursoJson == cursoin.cd_curso){
			return cursoin;
		}else {
			return null;
		}
	}

}
