package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    Button log_buttonE;
    EditText monoE,pwdE;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        monoE=findViewById(R.id.mobile_number);
        pwdE=findViewById(R.id.pwd_l);
        log_buttonE=findViewById(R.id.log_in);
        dbHelper=new DBHelper(this);
        log_buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mono=monoE.getText().toString();
                String pwd=pwdE.getText().toString();
                if(dbHelper.checkUser(mono,pwd))
                {
                    Intent logintent=new Intent(loginActivity.this,MainActivity.class);
                    startActivity(logintent);
                    Toast.makeText(loginActivity.this,"Login Successfully",Toast.LENGTH_SHORT);
                    finish();

                }
                else
                {
                    Toast.makeText(loginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}