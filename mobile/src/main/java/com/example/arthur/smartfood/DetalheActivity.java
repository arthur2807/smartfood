package com.example.arthur.smartfood;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class DetalheActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Turma turma = (Turma) getIntent().getSerializableExtra("truma");
		
		DetalheFragment d = DetalheFragment.novaInstancia(turma);
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(android.R.id.content, d)
			.commit();			
	}
}
//content