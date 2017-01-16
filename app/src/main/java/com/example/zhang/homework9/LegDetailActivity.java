package com.example.zhang.homework9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LegDetailActivity extends AppCompatActivity {
    Set<Legislator> legSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        legSet = new HashSet<Legislator>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leg_detail);

        final Legislator myLegislator = (Legislator) getIntent().getSerializableExtra("myLegislator");

        ImageView legImageView = (ImageView)findViewById(R.id.leg_detail_photo);
        String photoUrl = "https://theunitedstates.io/images/congress/original/" + myLegislator.bioguide_id + ".jpg";;
        Picasso.with(this).load(photoUrl).placeholder(R.drawable.place_holder).error(R.drawable.place_holder_error).into(legImageView);

        ImageView legPartyView = (ImageView)findViewById(R.id.leg_detail_party_icon);
        if(myLegislator.party == "D"){
            legPartyView.setImageResource(R.drawable.d);
        }
        else{
            legPartyView.setImageResource(R.drawable.r);
        }

        TextView legPartyTextView = (TextView) findViewById(R.id.leg_detail_party_name);
        if(myLegislator.party == "D"){
            legPartyTextView.setText("Democrat");
        }
        else{
            legPartyTextView.setText("Republican");
        }

        TextView legNameTextView = (TextView) findViewById(R.id.leg_detail_name);
        if(myLegislator.title.equals("")){
            legNameTextView.setText(myLegislator.lastname + ", " + myLegislator.firstname);
        }
        else{
            legNameTextView.setText(myLegislator.title + ". " + myLegislator.lastname + ", " + myLegislator.firstname);
        }


        TextView legEmailTextView = (TextView) findViewById(R.id.leg_detail_email);
        legEmailTextView.setText(myLegislator.email);

        TextView legChamberTextView = (TextView) findViewById(R.id.leg_detail_chamber);
        legChamberTextView.setText(myLegislator.chamber);


        TextView legContactTextView = (TextView) findViewById(R.id.leg_detail_contact);
        legContactTextView.setText(myLegislator.contact);

        TextView legSTermTextView = (TextView) findViewById(R.id.leg_detail_sterm);
        String stermOldFormat = myLegislator.startTerm;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date startTerm = new Date();
        try{
            Date newDate = format.parse(stermOldFormat);
            startTerm = newDate;
            format = new SimpleDateFormat("MMM dd,yyyy");
            String date = format.format(newDate);
            legSTermTextView.setText(date);
        }catch(ParseException e){
            legSTermTextView.setText(stermOldFormat);
        }

        TextView legETermTextView = (TextView) findViewById(R.id.leg_detail_eterm);
        String etermOldFormat = myLegislator.endTerm;
        Log.d("zyl", etermOldFormat);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-mm-dd");
        Date endTerm = new Date();
        try{
            Date newDate = format2.parse(etermOldFormat);
            endTerm = newDate;
            format2 = new SimpleDateFormat("MMM dd,yyyy");
            String date2 = format2.format(newDate);
            legETermTextView.setText(date2);
        }catch(ParseException e){
            legETermTextView.setText(etermOldFormat);
        }

        ProgressBar termProgressBar = (ProgressBar) findViewById(R.id.leg_detail_term);
        int percentage = 0;
        Date curDate = new Date();
        long startMiSecond = startTerm.getTime();
        long endMiSecond = endTerm.getTime();
        long curMiSecond = curDate.getTime();
        if(endMiSecond == startMiSecond){
            percentage = 0;
        }
        else{
            percentage = (int)((curMiSecond - startMiSecond) * 100 / (endMiSecond - startMiSecond));
        }
        termProgressBar.setProgress(percentage);


        TextView officeTextView = (TextView) findViewById(R.id.leg_detail_office);
        officeTextView.setText(myLegislator.office);

        TextView stateTextView = (TextView) findViewById(R.id.leg_detail_state);
        stateTextView.setText(myLegislator.state);

        TextView faxTextView = (TextView) findViewById(R.id.leg_detail_fax);
        faxTextView.setText(myLegislator.fax);

        TextView birthdayTextView = (TextView) findViewById(R.id.leg_detail_birthday);
        birthdayTextView.setText(myLegislator.birthday);


        ImageView saveIcon = (ImageView) findViewById(R.id.leg_detail_button_fav);
        ImageView facebookIcon = (ImageView) findViewById(R.id.leg_detail_button_facebook);
        final ImageView twitterIcon = (ImageView) findViewById(R.id.leg_detail_button_twitter);
        ImageView websiteIcon = (ImageView) findViewById(R.id.leg_detail_button_website);


        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs = getSharedPreferences("legShared" ,MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                if(!mPrefs.contains("LEGISLATOR")){
                    prefsEditor.putString("LEGISLATOR", "LEGISLATOR");
                    prefsEditor.commit();
                }
                String json = mPrefs.getString(myLegislator.bioguide_id, "null");

                if(json.equals("null")){//No legislator here, store this legislator and yellow the botton
                    Gson gson = new Gson();
                    String jsonToStore = gson.toJson(myLegislator);
                    prefsEditor.putString(myLegislator.bioguide_id, jsonToStore);
                    prefsEditor.commit();
                }
                else{//This legislator is here, cancel this legislator and white the button
                    prefsEditor.remove(myLegislator.bioguide_id);
                    prefsEditor.commit();
                }
            }
        });

        facebookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri fbUri;
                if(myLegislator.facebook.equals("")){
                    Toast.makeText(getApplication(), "No Facebook", Toast.LENGTH_SHORT);
                }
                else{
                    fbUri = Uri.parse(myLegislator.facebook);
                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, fbUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }


            }
        });

        twitterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri twiUri;
                if(myLegislator.twitter.equals("")){
                    Toast.makeText(getApplication(), "No Twitter", Toast.LENGTH_SHORT);
                }
                else{
                    twiUri = Uri.parse(myLegislator.twitter);
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, twiUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }


            }
        });

        websiteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webUri;
                if(myLegislator.website.equals("")){
                    Toast.makeText(getApplication(), "No Website", Toast.LENGTH_SHORT);
                }
                else{
                    webUri = Uri.parse(myLegislator.website);
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, webUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }

            }
        });




    }
}
