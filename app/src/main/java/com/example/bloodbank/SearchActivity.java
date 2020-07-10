package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    EditText city,blood;
    Button find;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        city=findViewById(R.id.et_city);
        blood=findViewById(R.id.et_blood_group);
        find=findViewById(R.id.submit_search_button);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityE=city.getText().toString();
                String bloodE=blood.getText().toString();
                if(cityE.equals("") && bloodE.equals(""))
                {
                    city.setError("Enter City");
                    blood.setError("Enter Blood Group");
                }
                else if(cityE.equals(""))
                {
                    city.setError("Enter City");
                }
                else if(bloodE.equals(""))
                {
                    blood.setError("Enter Blood Group");
                }
                else
                {
                    Intent intent=new Intent(SearchActivity.this,SearchResultActivity.class);
                    intent.putExtra("City",city.getText().toString());
                    intent.putExtra("BG",blood.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}