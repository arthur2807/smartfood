package com.example.arthur.smartfood;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class ListFavoritesProdutoFragment extends ListFragment {

	List<Turma> turmas;

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
		
		if (getActivity() instanceof ClicouNaTurma){
			((ClicouNaTurma)getActivity()).turmaFoiClicado(turmas.get(position));
		}
	}

	protected void refreshList() {
		CursoDB db = new CursoDB(getActivity());
		turmas = db.todosTurmas();

		TurmaAdapter adapter = new TurmaAdapter(getActivity(), turmas);
		setListAdapter(adapter);
	}
}
