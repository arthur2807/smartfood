package com.example.arthur.smartfood;

import com.example.arthur.smartfood.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetalheFragment extends Fragment {

	Produto produto;
	ProdutoDB db;
	TextView txtTitulo;
	WebView webViewConteudo;
	Button btnFavorito;
	
	public static DetalheFragment novaInstancia(Produto produto){
		Bundle args = new Bundle();
		args.putSerializable("produto", produto);
		
		DetalheFragment f = new DetalheFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		db = new ProdutoDB(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_detalhe_post, null);
		
		txtTitulo = (TextView)layout.findViewById(R.id.textView1);
		webViewConteudo = (WebView)layout.findViewById(R.id.webView1);
		WebSettings settings = webViewConteudo.getSettings();
		settings.setDefaultTextEncodingName("UTF-8");
		
		produto = (Produto)getArguments().getSerializable("produto");
		produto.favorito = db.favorito(produto);
		txtTitulo.setText(produto.nome);
		webViewConteudo.loadDataWithBaseURL(null, produto.descricao, "text/html", "UTF-8", null);
		return layout;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.detalhe, menu);
		
		MenuItem item = menu.findItem(R.id.action_favoritos);
		if (produto.favorito){
			item.setIcon(android.R.drawable.ic_menu_delete);
		} else {
			item.setIcon(android.R.drawable.ic_menu_save);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_favoritos){
			
			if (produto.favorito){
				db.excluir(produto);
				Toast.makeText(getActivity(), "Removido dos favoritos", Toast.LENGTH_SHORT).show();
			} else {
				db.inserir(produto);
				Toast.makeText(getActivity(), "Adicionado aos favoritos", Toast.LENGTH_SHORT).show();
			}
			
			if (getActivity() instanceof ProdutoNosFavoritos){
				((ProdutoNosFavoritos)getActivity()).produtoAdicionadoAoFavorito(produto);
			}
			
			((ActionBarActivity)getActivity()).supportInvalidateOptionsMenu();
		}
		return super.onOptionsItemSelected(item);
	}
	
	interface ProdutoNosFavoritos {
		void produtoAdicionadoAoFavorito(Produto produto);
	}
	
}
