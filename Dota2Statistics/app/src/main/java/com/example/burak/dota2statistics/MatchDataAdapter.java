package com.example.burak.dota2statistics;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burak on 08.08.2015.
 */
public class MatchDataAdapter extends ArrayAdapter<MatchData> {
    LayoutInflater li;
    Context context;
    int resource;
    ArrayList<MatchData> list;

    public MatchDataAdapter(Context context, int resource, ArrayList<MatchData> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        list = objects;
        li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // There might be some mistakes in that code snippet
    public View getView(int position, View convertView, ViewGroup parent) {
;

        if(convertView == null)
            convertView = li.inflate(R.layout.list_view, null);

        ImageView heroPortrait = (ImageView) convertView.findViewById(R.id.heroPortrait);
        TextView date = (TextView) convertView.findViewById(R.id.matchDate);
        TextView heroName = (TextView) convertView.findViewById(R.id.heroName);

        MatchData data = list.get(position);

        Picasso.with(context).load(data.getHeroImageUrl()).into(heroPortrait);
        date.setText(data.getDate());
        heroName.setText(data.getHeroName());


        return convertView;
    }


}
