package com.example.zhang.homework9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BillDetailActivity extends AppCompatActivity {
    Set<Bill> billSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billSet = new HashSet<Bill>();
        setContentView(R.layout.activity_bill_detail);

        final Bill myBill = (Bill) getIntent().getSerializableExtra("myBill");


        //Save Function
        ImageView billStarView = (ImageView) findViewById(R.id.bill_detail_star);



        TextView billIdTextView = (TextView) findViewById(R.id.bill_detail_id);
        billIdTextView.setText(myBill.billId);

        TextView billTitleTextView = (TextView) findViewById(R.id.bill_detail_title);
        billTitleTextView.setText(myBill.billStortTitle);

        TextView billTypeTextView = (TextView) findViewById(R.id.bill_detail_type);
        billTypeTextView.setText(myBill.billType);


        TextView billSponsorTextView = (TextView) findViewById(R.id.bill_detail_sponsor);
        billSponsorTextView.setText(myBill.sponsor);

        TextView billChamberTextView = (TextView) findViewById(R.id.bill_detail_chamber);
        billChamberTextView.setText(myBill.chamber);

        TextView billStatusTextView = (TextView) findViewById(R.id.bill_detail_status);
        billStatusTextView.setText(myBill.status);

        TextView billIntroducedTextView = (TextView) findViewById(R.id.bill_detail_introduced);
        String introducedDateInOldFormat = myBill.introduced;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try{
            Date newDate = format.parse(introducedDateInOldFormat);
            format = new SimpleDateFormat("MMM dd,yyyy");
            String date = format.format(newDate);
            billIntroducedTextView.setText(date);
        }catch(ParseException e){
            billIntroducedTextView.setText(introducedDateInOldFormat);
        }

        TextView congressUrlTextView = (TextView) findViewById(R.id.bill_detail_congress_url);
        congressUrlTextView.setText(myBill.congressUrl);

        TextView versionStatusTextView = (TextView) findViewById(R.id.bill_detail_status);
        versionStatusTextView.setText(myBill.versionStatus);

        TextView billUrlTextView = (TextView) findViewById(R.id.bill_detail_url);
        billUrlTextView.setText(myBill.url);
    }
}
