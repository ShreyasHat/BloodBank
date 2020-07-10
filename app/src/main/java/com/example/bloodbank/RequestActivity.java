package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RequestActivity extends AppCompatActivity {
    EditText msgE,cityE,numberE,bloodgroupE;
    Button req;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        db=new DBHelper(this);
        msgE=findViewById(R.id.message);
        cityE=findViewById(R.id.City);
        numberE=findViewById(R.id.Mobile_number);
        bloodgroupE=findViewById(R.id.Blood_Group);
        req=findViewById(R.id.sreq);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message,city,bloodgroup,password,number;
                message=msgE.getText().toString();
                city=cityE.getText().toString();
                number=numberE.getText().toString();
                bloodgroup=bloodgroupE.getText().toString();

                if(msgE.getText().toString().equals("") && cityE.getText().toString().equals("") && bloodgroupE.getText().toString().equals("") 
                        && numberE.getText().toString().equals(""))
                {
                    msgE.setError("Enter Message");
                    cityE.setError("Enter city");
                    bloodgroupE.setError("Enter the Blood Group");
                    if(numberE.length()<10)
                    {
                        numberE.setError("Enter valid number number");
                    }
                    else
                    {
                        numberE.setError("Enter number number");
                    }
                }
                else if(cityE.getText().toString().equals(""))
                {
                    cityE.setError("Enter city");
                }
                else if(bloodgroupE.getText().toString().equals(""))
                {
                    bloodgroupE.setError("Enter the Blood Group");
                }
                else if(numberE.getText().toString().equals(""))
                {
                    if(numberE.length()<10)
                    {
                        numberE.setError("Enter valid number number");
                    }
                    else
                    {
                        numberE.setError("Enter number number");
                    }
                }
                else {


                    db.insertDataReq(message, city, number, bloodgroup);
                    Toast.makeText(RequestActivity.this,"Added",Toast.LENGTH_SHORT);
                    startActivity(new Intent(RequestActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
    }
}