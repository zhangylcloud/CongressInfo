package com.example.zhang.homework9;

/**
 * Created by zhang on 11/29/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by zhang on 11/28/2016.
 */

public class LegislatorAdapter extends ArrayAdapter<Legislator> {

    public Activity activity;
    //public ViewHolder h;

    Context context;
    public LegislatorAdapter(Context context, List<Legislator> legislators){
        super(context, 0, legislators);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.leg_custom_row, parent, false);
            //h = new ViewHolder();
            //h.imageView = (ImageView) listItemView.findViewById(R.id.leg_photo);
            //convertView.setTag(h);
        }
        //else{
        //    h=(ViewHolder)convertView.getTag();
        //}



        Legislator currentLegislator = getItem(position);

        ImageView legImageView = (ImageView) listItemView.findViewById(R.id.leg_photo);
        String photoUrl = "https://theunitedstates.io/images/congress/original/" + currentLegislator.bioguide_id + ".jpg";
        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(photoUrl).resize(100, 100).placeholder(R.drawable.place_holder).error(R.drawable.place_holder_error).into(legImageView);


        TextView nameView = (TextView) listItemView.findViewById(R.id.leg_name);

        String lastname = currentLegislator.lastname;
        String firstname = currentLegislator.firstname;
        //String title = currentLegislator.title;
        String fullname = "";
        fullname = lastname + ", " + firstname;
        nameView.setText(fullname);

        TextView contentView = (TextView) listItemView.findViewById(R.id.party_place);
        String party = currentLegislator.party;
        String stateName = currentLegislator.stateName;
        String district = currentLegislator.district;
        String contentString = "(" + party + ")" + stateName + " - District " + district;
        contentView.setText(contentString);
        //bioguideView.setText(bioguide_id);

        return listItemView;
    }
}

