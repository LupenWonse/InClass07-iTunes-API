package com.gencoglu.inclass07;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ahmet on 03/10/2016.
 */

public class PodcastAdapter extends ArrayAdapter<Podcast> {

    Context context;
    List<Podcast> podcastList;
    int mResource;

    public PodcastAdapter(Context context, int resource, List<Podcast> objects) {
        super(context, resource, objects);
        this.context = context;
        this.podcastList = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if (convertView == null){
            // Inflate the view here
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        Podcast podcast = podcastList.get(position);

        TextView textTitle = (TextView) convertView.findViewById(R.id.textTitle);
        textTitle.setText(podcast.title);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageThumbnail);
        if (podcast.thumbnail != null) {
            Picasso.with(getContext()).load(podcast.thumbnail).into(imageView);
        }

        if (podcast.isHit) {
            convertView.setBackgroundColor(Color.GREEN);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }


}
