package com.example.arthur.smartfood;

import java.util.List;

 import com.example.arthur.smartfood.R;

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

public class ListProdutoFragment extends ListFragment {

	List<Produto> produtos;
	ReadPostsAsyncTask task;
	ProgressBar progress;
	TextView txtMensagem;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		if (produtos != null){
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
		
		if (getActivity() instanceof ClicouNoProduto){
			((ClicouNoProduto)getActivity()).produtoFoiClicado(produtos.get(position));
		}
	}

	private void refreshList() {
		// ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(
		// getActivity(),
		// android.R.layout.simple_list_item_1,
		// posts);
		ProdutoAdapter adapter = new ProdutoAdapter(getActivity(), produtos);
		setListAdapter(adapter);
	}
	
	private void mostrarProgress() {
		progress.setVisibility(View.VISIBLE);
		txtMensagem.setVisibility(View.VISIBLE);
		txtMensagem.setText("Carregando...");
	}
	
	class ReadPostsAsyncTask extends AsyncTask<Void, Void, List<Produto>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgress();
		}

		@Override
		protected List<Produto> doInBackground(Void... params) {
			try {
//				Thread.sleep(4000);
				return ProdutoHttp.retrievePosts();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Produto> result) {
			super.onPostExecute(result);
			if (result != null) {
				produtos = result;
				refreshList();
				txtMensagem.setVisibility(View.GONE);
			} else {
				txtMensagem.setText("Deu erro!");
			}
			progress.setVisibility(View.GONE);
		}
	}
}
