package com.example.arthur.smartfood;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class ListFavoritesPostsFragment extends ListFragment {

	List<Post> posts;

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
		
		if (getActivity() instanceof ClicouNoPost){
			((ClicouNoPost)getActivity()).postFoiClicado(posts.get(position));
		}
	}

	protected void refreshList() {
		PostsDB db = new PostsDB(getActivity());
		posts = db.todosOsPosts();
		
		PostAdapter adapter = new PostAdapter(getActivity(), posts);
		setListAdapter(adapter);
	}
}
