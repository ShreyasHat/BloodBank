package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectInputValidation;

public class RegisterActivity extends AppCompatActivity {
    DBHelper db;
    EditText nameE,cityE,bloodGroupE,passwordE,mobileE;
    Button submitE;
    TextView logviewE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=new DBHelper(this);

        nameE=findViewById(R.id.name);
        cityE=findViewById(R.id.city);
        bloodGroupE=findViewById(R.id.blood_group);
        passwordE=findViewById(R.id.password);
        mobileE=findViewById(R.id.number);
        submitE=findViewById(R.id.submit_button);
        logviewE=findViewById(R.id.log_view);
        submitE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,city,bloodgroup,password,mobile;
                name=nameE.getText().toString();
                city=cityE.getText().toString();
                bloodgroup=bloodGroupE.getText().toString();
                password=passwordE.getText().toString();
                mobile=mobileE.getText().toString();
                if(nameE.getText().toString().equals("") && cityE.getText().toString().equals("") && bloodGroupE.getText().toString().equals("") && passwordE.getText().toString().equals("")
                && mobileE.getText().toString().equals(""))
                {
                    nameE.setError("Enter username");
                    cityE.setError("Enter city");
                    bloodGroupE.setError("Enter the Blood Group");
                    if(passwordE.length()<6)
                    {
                        passwordE.setError("Enter password atleast 6 character");
                    }
                    else {
                        passwordE.setError("Enter the password");
                    }
                    if(mobileE.length()<10)
                    {
                        mobileE.setError("Enter valid mobile number");
                    }
                    else
                    {
                        mobileE.setError("Enter mobile number");
                    }
                }
                else if(cityE.getText().toString().equals(""))
                {
                    cityE.setError("Enter city");
                }
                else if(bloodGroupE.getText().toString().equals(""))
                {
                    bloodGroupE.setError("Enter the Blood Group");
                }
                else if(passwordE.getText().toString().equals("") )
                {
                    if(passwordE.length()<6)
                    {
                        passwordE.setError("Enter password atleast 6 character");
                    }
                    else {
                        passwordE.setError("Enter the password");
                    }
                }
                else if(mobileE.getText().toString().equals(""))
                {
                    if(mobileE.length()<10)
                    {
                        mobileE.setError("Enter valid mobile number");
                    }
                    else
                    {
                        mobileE.setError("Enter mobile number");
                    }
                }
                else {


                    db.insertDataReg(name, city, mobile, password, bloodgroup);
                    startActivity(new Intent(RegisterActivity.this,loginActivity.class));
                    finish();
                }
                ///howMessage(name+"\n"+city+"\n"+bloodgroup+"\n"+password+"\n"+mobile);

            }
        });
        logviewE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,loginActivity.class));
                finish();
            }
        });
    }

}