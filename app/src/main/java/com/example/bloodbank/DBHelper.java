package com.example.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static android.database.sqlite.SQLiteDatabase.openDatabase;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="BloodBank";
    public static final String TABLE1="register";
    public static final String TABLE2="table2";
    public static final String TABLE3="table3";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table register(username text,city text,number bigint,password varchar(20),bloodgroup varchar(5))");
        db.execSQL("create table request(message text,city text,number bigint,bg text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS register");
        db.execSQL("DROP TABLE IF EXISTS request");
        onCreate(db);
    }
    public boolean insertDataReg(String uname,String city,String number,String password,String bloodgroup)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",uname);
        contentValues.put("city",city);
        contentValues.put("number",number);
        contentValues.put("password",password);
        contentValues.put("bloodgroup",bloodgroup);
        long result=db.insert("register",null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean insertDataReq(String msg,String city,String number,String bloodgroup)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("message",msg);
        contentValues.put("city",city);
        contentValues.put("number",number);
        contentValues.put("bg",bloodgroup);
        long result=db.insert("request",null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor readAllData()
    {

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;

        if(db!=null)
        {
            cursor=db.rawQuery("SELECT * FROM request",null);
        }
        return cursor;
    }
    public boolean checkUser(String n,String p)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM register WHERE number=? AND password=?", new String[]{n,p});
        if (mCursor != null) {
            if(mCursor.moveToFirst())
            {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Request> getDonars(String city, String bGroup){
        ArrayList<Request> donars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM register WHERE city=? AND bloodgroup=?", new String[]{city,bGroup});

        if(cursor.moveToFirst())
        {
            do {
                donars.add(new Request(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(4)));
            }
            while(cursor.moveToNext());

        }
        return  donars;
    }

    public ArrayList<String> getLocations(){
        Set<String> locations = new LinkedHashSet<>();
        SQLiteDatabase db = this.getReadableDatabase();
        locations.add("All Locations");
        Cursor cursor = db.rawQuery("SELECT city FROM request", new String[]{});

        if(cursor.moveToFirst())
        {
            do {
                locations.add(cursor.getString(0));
            }
            while(cursor.moveToNext());

        }
        return new ArrayList<>(locations);
    }

    public ArrayList<Request> getCityRequest(String city) {
        ArrayList<Request> requests = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM request WHERE city=?", new String[]{city});

        if(cursor.moveToFirst())
        {
            do {
                requests.add(new Request(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }
            while(cursor.moveToNext());

        }
        return  requests;

    }

    public ArrayList<Request> getRequests(){
        ArrayList<Request> requests = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM request", new String[]{});

        if(cursor.moveToFirst())
        {
            do {
                requests.add(new Request(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }
            while(cursor.moveToNext());

        }
        return  requests;
    }
}
