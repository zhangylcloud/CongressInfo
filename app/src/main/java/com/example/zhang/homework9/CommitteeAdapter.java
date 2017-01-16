package com.example.zhang.homework9;

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
 * Created by zhang on 11/30/2016.
 */

public class CommitteeAdapter extends ArrayAdapter<Committee> {
    public Activity activity;
    //public ViewHolder h;

    Context context;
    public CommitteeAdapter(Context context, List<Committee> committees){
        super(context, 0, committees);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.com_custom_row, parent,false);

        }

        Committee currentCommittee = getItem(position);
        TextView idView = (TextView) listItemView.findViewById(R.id.com_id);
        idView.setText(currentCommittee.committeeId);

        TextView nameView = (TextView) listItemView.findViewById(R.id.com_name);
        nameView.setText(currentCommittee.committeeName);

        TextView chamberView = (TextView) listItemView.findViewById(R.id.com_chamber);
        chamberView.setText(currentCommittee.chamber);
        return listItemView;
    }


}
