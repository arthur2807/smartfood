package com.example.arthur.smartfood;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PostAdapter extends ArrayAdapter<Post> {

	DisplayImageOptions options;

	public PostAdapter(Context context, List<Post> objects) {
		super(context, 0, 0, objects);

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher) // resource or drawable
				.cacheInMemory(true) // default
				.cacheOnDisc(true) // default
				.build();

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		Post post = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.linha_post, null);

			holder = new ViewHolder();
			holder.txtTexto = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.imgFoto = (ImageView) convertView
					.findViewById(R.id.imageView1);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtTexto.setText(post.title);
		ImageLoader.getInstance().displayImage(post.thumbnail, holder.imgFoto, options);
//		BitmapManager.getInstance().loadBitmap(post.thumbnail, holder.imgFoto);

		return convertView;
	}

	static class ViewHolder {
		ImageView imgFoto;
		TextView txtTexto;
	}
}
