package com.example.arthur.smartfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class TurmaAdapter extends ArrayAdapter<Turma> {

		DisplayImageOptions options;

		public TurmaAdapter(Context context, List<Turma> objects) {
			super(context, 0, 0, objects);

			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.ic_launcher) // resource or drawable
					.cacheInMemory(true) // default
					.cacheOnDisc(true) // default
					.build();
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			Turma turma = getItem(position);

			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.linha_turma, null);

				holder = new ViewHolder();
				holder.txtTexto = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.txtTexto2 = (TextView) convertView
						.findViewById(R.id.textView2);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txtTexto.setText(turma.dc_turma);
			holder.txtTexto2.setText(turma.cd_curso.dc_curso);
			return convertView;
		}

		static class ViewHolder {
			TextView txtTexto;
			TextView txtTexto2;
		}
	}


