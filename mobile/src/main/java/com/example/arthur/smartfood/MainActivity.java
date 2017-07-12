package com.example.arthur.smartfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;




public class MainActivity extends ActionBarActivity
	implements ClicouNoProduto {

    ViewPager pager;
	ListProdutoFragment fragment;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new ListProdutoFragment();
        pager = (ViewPager)findViewById(R.id.viewPager);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
	}

	/*private boolean isTablet(){
		return findViewById(R.id.detail) != null;
	}*/

	@Override
	public void produtoFoiClicado(Curso curso) {
		Intent intent = new Intent(MainActivity.this, TurmaActivity.class);
		intent.putExtra("curso", curso);
			startActivity(intent);
	}

	@Override
	public void onPointerCaptureChanged(boolean hasCapture) {

	}
}
