package com.example.arthur.smartfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;


public class TurmaActivity extends ActionBarActivity
	implements TabListener, ClicouNaTurma {

	ListProdutoFragment fragment1;
	ListFavoritesProdutoFragment fragment2;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_truma);

		fragment1 = new ListProdutoFragment();
		fragment2 = new ListFavoritesProdutoFragment();

		pager = (ViewPager)findViewById(R.id.viewPagerTurma);
		FragmentManager fm = getSupportFragmentManager();
		pager.setAdapter(new MeuAdapter(fm));
		pager.setOnPageChangeListener(new SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
			}
		});
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {

	}

	private boolean isTablet(){
		return findViewById(R.id.detail) != null;
	}

	@Override
	public void turmaFoiClicado(Turma turma) {
		if (isTablet()){
			DetalheFragment d = DetalheFragment.novaInstancia(turma);

			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.detail, d)
				.commit();
		} else {
			Intent it = new Intent(this, DetalheActivity.class);
			it.putExtra("turma", turma);
			startActivity(it);
		}		
	}

	@Override
	public void produtoAdicionadoAoFavorito(Produto produto) {
		fragment2.refreshList();
		
	}

	@Override
	public void turmaFoiClicado(Turma turma) {

	}

	class MeuAdapter extends FragmentPagerAdapter {

		public MeuAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0){
				return fragment1;
			}
			return fragment2;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}
}
