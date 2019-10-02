package com.example.govind.lostdroid2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String Database_name="user.db";
    public static final String table="userinfo";
    public static final String col1="fullname";
    public static final String col2="emailid";
    public static final String col3="phone1";
    public static final String col4="phone2";
    public static final String col5="password";
    public static final String col6="confirmpswrd";
    public static final String col7="Address";

    public DatabaseHelper(Context context) {

        super(context,Database_name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+table+"(fullname TEXT,emailid TEXT,phone1 TEXT,phone2 TEXT,password TEXT,confirmpswrd TEXT,address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+table);//drop older table
        onCreate(db);
    }
}
