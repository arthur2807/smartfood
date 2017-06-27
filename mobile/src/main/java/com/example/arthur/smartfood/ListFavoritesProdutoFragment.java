package com.example.arthur.smartfood;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class ListFavoritesProdutoFragment extends ListFragment {

	List<Produto> produtos;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refreshList();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		if (getActivity() instanceof ClicouNoProduto){
			((ClicouNoProduto)getActivity()).produtoFoiClicado(produtos.get(position));
		}
	}

	protected void refreshList() {
		ProdutoDB db = new ProdutoDB(getActivity());
		produtos = db.todosOsPosts();
		
		ProdutoAdapter adapter = new ProdutoAdapter(getActivity(), produtos);
		setListAdapter(adapter);
	}
}
