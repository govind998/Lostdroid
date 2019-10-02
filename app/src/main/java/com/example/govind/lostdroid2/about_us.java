package com.example.govind.lostdroid2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class about_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView textView1=(TextView) findViewById(R.id.textView3);
        TextView textView2=(TextView) findViewById(R.id.textView4);
        TextView textView3=(TextView) findViewById(R.id.textView5);
        TextView textView4=(TextView) findViewById(R.id.textView6);
        textView1.setText("About-Us");
        textView2.setText("Lostdroid is an anti-theft application for android based devices which provide us a technique through which the thief/person who steals/found any android based mobile phone installed with this application, gets captured and the user can make him/her stop misusing any confidential information. Lostdroid includes the latest technology like email, WhatsApp for sending mms (multimedia messaging service) and it also uses the sms(short message service) where you can send pictures from rear and front camera , live location and the subscriber's information , which will helps us to find the current location of the device as well as to recognize the place by looking at the camera pictures . This application will also stop the person/thief from switching off the device, so that the application can continuously work on the background when the app is triggered by a key , it will start to send the live location and pictures from camera to the alternate number registered in the lostdroid application.");
        textView3.setText("Instructions");
        textView4.setText("1) click on the Register button.\n" +
                "2)Enter your credentials and register yourself to the lostdroid application.\n" +
                "3)Now if your phone is lost then take other device and enter the key and send sms to your lost device.\n" +
                "4)For saftey always keep your mobile phone's mobile data and location on.");


    }
}
