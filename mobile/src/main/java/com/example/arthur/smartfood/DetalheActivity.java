package com.example.arthur.smartfood;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class DetalheActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Produto produto = (Produto)getIntent().getSerializableExtra("produto");
		
		DetalheFragment d = DetalheFragment.novaInstancia(produto);
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(android.R.id.content, d)
			.commit();			
	}
}
//content