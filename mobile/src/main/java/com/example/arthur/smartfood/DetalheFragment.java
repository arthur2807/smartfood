package com.example.arthur.smartfood;

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

	Turma turma;
	CursoDB db;
	TextView txtTitulo;
	WebView webViewConteudo;
	Button btnFavorito;
	
	public static DetalheFragment novaInstancia(Turma turma){
		Bundle args = new Bundle();
		args.putSerializable("truma", turma);
		DetalheFragment f = new DetalheFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		db = new CursoDB(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_detalhe_post, null);
		
		txtTitulo = (TextView)layout.findViewById(R.id.textView1);
		webViewConteudo = (WebView)layout.findViewById(R.id.webView1);
		WebSettings settings = webViewConteudo.getSettings();
		settings.setDefaultTextEncodingName("UTF-8");
		
		turma = (Turma)getArguments().getSerializable("truma");
		//turma.favorito = db.favorito(turma);
		txtTitulo.setText(turma.dc_turma);
		webViewConteudo.loadDataWithBaseURL(null, turma.dc_horario_turma, "text/html", "UTF-8", null);
		return layout;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.detalhe, menu);
		
		MenuItem item = menu.findItem(R.id.action_favoritos);
		if (turma.favorito){
			item.setIcon(android.R.drawable.ic_menu_delete);
		} else {
			item.setIcon(android.R.drawable.ic_menu_save);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_favoritos){
			
			/*if (turma.favorito){
				db.excluir(turma);
				Toast.makeText(getActivity(), "Removido dos favoritos", Toast.LENGTH_SHORT).show();
			} else {
				db.inserir(turma);
				Toast.makeText(getActivity(), "Adicionado aos favoritos", Toast.LENGTH_SHORT).show();
			}*/
			
			if (getActivity() instanceof TrumaNosFavoritos){
				((TrumaNosFavoritos)getActivity()).trumaAdicionadoAoFavorito(turma);
			}
			
			((ActionBarActivity)getActivity()).supportInvalidateOptionsMenu();
		}
		return super.onOptionsItemSelected(item);
	}
	
	interface TrumaNosFavoritos {
		void trumaAdicionadoAoFavorito(Turma turma);
	}
}
