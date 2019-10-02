package com.example.govind.lostdroid2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;

    EditText fname,email,phn1,phn2,pswrd,cnfpswrd,addrss;
     TextView textView;
     Context context;
    Button savebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        context=getApplicationContext();
        sqLiteOpenHelper=new DatabaseHelper(this);
        fname=(EditText) findViewById(R.id.editText);
        email=(EditText) findViewById(R.id.editText2);
        phn1=(EditText) findViewById(R.id.editText3);
        phn2=(EditText) findViewById(R.id.editText4);
        pswrd=(EditText) findViewById(R.id.editText5);
        cnfpswrd=(EditText) findViewById(R.id.editText6);
        addrss=(EditText) findViewById(R.id.editText7);
        savebutton=(Button) findViewById(R.id.button3);



        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sqLiteDatabase=sqLiteOpenHelper.getWritableDatabase();
                String fullname=fname.getText().toString();
                String emailid=email.getText().toString();
                String phone1=phn1.getText().toString();
                String phone2=phn2.getText().toString();
                String password=pswrd.getText().toString();
                String confirmpswrd=cnfpswrd.getText().toString();
                String address=addrss.getText().toString();

                if(!password.equals(confirmpswrd)){

                    Toast.makeText(getApplicationContext(),"password doesn`t match",Toast.LENGTH_LONG).show();
                }
                else {
                    insertdata(fullname, emailid, phone1, phone2, password, confirmpswrd, address);
                    Toast.makeText(getApplicationContext(), "register successfull" + fullname, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void insertdata(String fullname,String emailid,String phone1,String phone2,String password,String confirmpswrd,String address){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.col1,fullname);
        contentValues.put(DatabaseHelper.col2,emailid);
        contentValues.put(DatabaseHelper.col3,phone1);
        contentValues.put(DatabaseHelper.col4,phone2);
        contentValues.put(DatabaseHelper.col5,password);
        contentValues.put(DatabaseHelper.col6,confirmpswrd);
        contentValues.put(DatabaseHelper.col7,address);
        Toast.makeText(context,"insertdata",Toast.LENGTH_LONG).show();
long id=sqLiteDatabase.insert(DatabaseHelper.table,null,contentValues);
    }

}
