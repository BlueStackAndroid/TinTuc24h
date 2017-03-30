package com.example.pc.hocrsstintuc24h;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.pc.hocrsstintuc24h.R.id.imageView;

/**
 * Created by pc on 3/27/2017.
 */
public class DocBaoAdapter extends ArrayAdapter<DocBao> {
    public DocBaoAdapter(Context context, int resource, List<DocBao> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.item, null);
        }
        DocBao p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txtTitle = (TextView) view.findViewById(R.id.textView);
            txtTitle.setText(p.getTitle());

            ImageView img = (ImageView) view.findViewById(imageView);
            Picasso.with(getContext()).load(p.getImage()).into(img);
        }
        return view;
    }

}
