package com.example.arthur.smartfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;

import static android.content.Intent.getIntent;


public class TurmaActivity extends ActionBarActivity
	implements TabListener, ClicouNaTurma {

	ListTrumaFragment fragment1 ;
	ListFavoritesProdutoFragment fragment2;
	ViewPager pager;
	Curso curso;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_truma);
		curso = (Curso) getIntent().getSerializableExtra("curso");

		fragment1 = new ListTrumaFragment().novaInstancia(curso);
		fragment2 = new ListFavoritesProdutoFragment();

		final ActionBar actionBar = getSupportActionBar();

		pager = (ViewPager)findViewById(R.id.viewPagerTurma);
		FragmentManager fm = getSupportFragmentManager();
		pager.setAdapter(new TurmaActivity.MeuAdapter(fm));
		pager.setOnPageChangeListener(new SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				actionBar.setSelectedNavigationItem(position);
			}
		});

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab aba1 = actionBar.newTab();
		aba1.setText("Turma");
		aba1.setTabListener(this);

		Tab aba2 = actionBar.newTab();
		aba2.setText("Minhas Turma");
		aba2.setTabListener(this);

		actionBar.addTab(aba1);
		actionBar.addTab(aba2);
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

	public static TurmaActivity novaInstancia(Curso curso){
		Bundle args = new Bundle();
		args.putSerializable("curso", curso);
		TurmaActivity f = new TurmaActivity();
		//f.setArguments(args);
		return f;
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
