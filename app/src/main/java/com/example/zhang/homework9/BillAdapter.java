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
import static android.R.attr.positiveButtonText;

/**
 * Created by zhang on 11/30/2016.
 */

public class BillAdapter extends ArrayAdapter<Bill>{
    public Activity activity;

    Context context;
    public BillAdapter(Context context, List<Bill> bills){
        super(context, 0, bills);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.bill_custom_row, parent, false);
        }
        Bill currentBill = getItem(position);

        TextView idView = (TextView) listItemView.findViewById(R.id.bill_id);
        String billId = currentBill.billId;
        idView.setText(billId);

        TextView introducedView = (TextView) listItemView.findViewById(R.id.bill_introduced);
        String introducedOn = currentBill.introduced;
        introducedView.setText(introducedOn);

        TextView titleView = (TextView) listItemView.findViewById(R.id.bill_short_title);
        String shortTitle = currentBill.billStortTitle;
        titleView.setText(shortTitle);

        return listItemView;





    }


}
