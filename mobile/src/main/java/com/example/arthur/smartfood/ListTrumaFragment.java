package com.example.arthur.smartfood;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import static android.content.Intent.getIntent;

public class ListTrumaFragment extends ListFragment {

	List<Turma> turmas;
	Curso curso;
	ReadPostsAsyncTask task;
	ProgressBar progress;
	TextView txtMensagem;


	public static ListTrumaFragment novaInstancia(Curso curso){
		Bundle args = new Bundle();
		args.putSerializable("curso", curso);
		ListTrumaFragment f = new ListTrumaFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		if (turmas != null){
			txtMensagem.setVisibility(View.GONE);
			progress.setVisibility(View.GONE);
			refreshList();
			
		} else {
			if (task != null && task.getStatus() == Status.RUNNING){
				mostrarProgress();
				
			} else {
				iniciarDownload();
			}
		}
	}

	private void iniciarDownload() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		curso = (Curso) getArguments().getSerializable("curso");
		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {

			task = new ReadPostsAsyncTask();
			task.execute();

		} else {
			progress.setVisibility(View.GONE);
			txtMensagem.setVisibility(View.VISIBLE);
			txtMensagem.setText("Sem conexao com a Internet");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_lista_posts,
				container, false);

		progress = (ProgressBar) layout.findViewById(R.id.progressBar1);
		txtMensagem = (TextView) layout.findViewById(R.id.textView1);

		return layout;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		if (getActivity() instanceof ClicouNaTurma){
			((ClicouNaTurma)getActivity()).turmaFoiClicado(turmas.get(position));
		}
	}

	private void refreshList() {
		// ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(
		// getActivity(),
		// android.R.layout.simple_list_item_1,
		// posts);
		TurmaAdapter adapter = new TurmaAdapter(getActivity(), turmas);
		setListAdapter(adapter);
	}
	
	private void mostrarProgress() {
		progress.setVisibility(View.VISIBLE);
		txtMensagem.setVisibility(View.VISIBLE);
		txtMensagem.setText("Carregando...");
	}


	class ReadPostsAsyncTask extends AsyncTask<Void, Void, List<Turma>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgress();
		}

		@Override
		protected List<Turma> doInBackground(Void... params) {
			try {
				return CursoHttp.turmasJson(curso);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Turma> result) {
			super.onPostExecute(result);
			if (result != null) {
				turmas = result;
				refreshList();
				txtMensagem.setVisibility(View.GONE);
			} else {
				txtMensagem.setText("Deu erro!");
			}
			progress.setVisibility(View.GONE);
		}
	}
}
