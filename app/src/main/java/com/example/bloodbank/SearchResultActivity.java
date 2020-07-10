package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity implements RecyclerReqAdapter.onBGClick{

    RecyclerView recyclerView;
    RecyclerReqAdapter recyclerReqAdapter;
    ArrayList<Request> donars;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        String city = intent.getStringExtra("City");
        String bg  = intent.getStringExtra("BG");
        heading = findViewById(R.id.heading);

        DBHelper db = new DBHelper(SearchResultActivity.this);
        donars = db.getDonars(city,bg);
        heading.setText(donars.size() + " Results found");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerReqAdapter = new RecyclerReqAdapter(donars,this);
        recyclerView.setAdapter(recyclerReqAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onShare(int pos) {
        Request request = donars.get(pos);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Username: "+ request.getMessage()+ "\n Phone Number: "+ request.getNumber()+"\n Blood Group"+ request.getGroup()+"\n City: "+request.getCity();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Blood Bank Request");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onCall(int pos) {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + donars.get(pos).getNumber()));
        startActivity(intent);
    }
}