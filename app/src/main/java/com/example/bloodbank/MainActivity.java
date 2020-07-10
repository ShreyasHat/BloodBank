package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements  RecyclerReqAdapter.onBGClick{
    TextView mr;
    RecyclerView recyclerView;
    RecyclerReqAdapter recyclerReqAdapter;
    ArrayList<Request> requests;
    androidx.appcompat.widget.Toolbar toolbar;
    DBHelper db;
    Spinner location;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mr= findViewById(R.id.m_r);
        toolbar= findViewById(R.id.toolbar);
        location = findViewById(R.id.pick_location);
        db=new DBHelper(MainActivity.this);


        updateSpinner();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.search_button) {
                    //open search
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                }
                return false;
            }
        });

        mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RequestActivity.class));

            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        setList(db.getRequests());



        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String city = adapterView.getItemAtPosition(i).toString();
                Log.d("City",city);
                if(city!="All Locations")
                    setList(db.getCityRequest(city));
                else
                    setList(db.getRequests());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void updateSpinner() {

        ArrayList<String> locations = db.getLocations();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter);
        location.setSelection(adapter.getPosition("All Locations"));
    }

    public void setList(ArrayList<Request> requests){
        recyclerReqAdapter = new RecyclerReqAdapter(requests,this);
        recyclerView.setAdapter(recyclerReqAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onShare(int pos) {

        Request request = requests.get(pos);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Message: "+ request.getMessage()+ "\n Phone Number: "+ request.getNumber()+"\n Blood Group"+ request.getGroup()+"\n City: "+request.getCity();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Blood Bank Request");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onCall(int pos) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + requests.get(pos).getNumber()));
        startActivity(intent);
    }
}
