package com.example.zhang.homework9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class ComDetailActivity extends AppCompatActivity {
    Set<Committee> comSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        comSet = new HashSet<Committee>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_detail);

        final Committee myCommittee = (Committee) getIntent().getSerializableExtra("myCommittee");
        ImageView billStarView = (ImageView) findViewById(R.id.com_detail_star);

        TextView comIdTextView = (TextView) findViewById(R.id.com_detail_id);
        comIdTextView.setText(myCommittee.committeeId);

        TextView comNameTextView = (TextView) findViewById(R.id.com_detail_name);
        comNameTextView.setText(myCommittee.committeeName);

        TextView comChamberTextView = (TextView) findViewById(R.id.com_detail_chamber);
        comChamberTextView.setText(myCommittee.chamber);

        TextView comParentTextView = (TextView) findViewById(R.id.com_detail_parent_committee);
        comParentTextView.setText(myCommittee.parentCommitteeId);

        TextView comContactTextView = (TextView) findViewById(R.id.com_detail_contact);
        comContactTextView.setText(myCommittee.contact);

        TextView comOfficeTextView = (TextView) findViewById(R.id.com_detail_office);
        comOfficeTextView.setText(myCommittee.office.equals("") ? "N.A." : myCommittee.office);

    }
}
