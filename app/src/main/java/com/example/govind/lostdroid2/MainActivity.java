package com.example.govind.lostdroid2;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {


    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    private  WifiManager wifiManager;
   ImageView imageView;
    LocationManager locationManager;
    private static final int Request_call=1;
    String msg3="empty";
    TelephonyManager   tm;
    String string="please wait....";
    String mobile = "8454842766";
    String msg = "location not found", msg2 = "device status not found";
    TextView tv;
    TextView tv2;
    SmsManager sms = SmsManager.getDefault();
    FloatingActionButton floatingActionButton,floatingActionButton2,floatingActionButton3;
    private static final int My_Permisssion_request_Recieve_Sms=0;
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this,"pressed to long",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.dispatchKeyEvent(event);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){

                                                    @Override
                                                    public void onClick(View v) {
                                                        sendEmail();
                                                    }
                                                });
        floatingActionButton3=(FloatingActionButton) findViewById(R.id.floatingActionButton4);
        floatingActionButton3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"clicked phone button",Toast.LENGTH_LONG).show();
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CALL_PHONE},Request_call);
                }

                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:9819395931")));

            }
        });
        floatingActionButton2=(FloatingActionButton) findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                WifiState();
            }
        });
         wifiManager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        }
        if(ContextCompat.checkSelfPermission(getBaseContext(),"android.permission.RECEIVE_SMS")!=PackageManager.PERMISSION_GRANTED){
           final int REQUEST_CODE_ASK_PERMISSION=124;
            ActivityCompat.requestPermissions(this,new String[]{"android.permission.RECEIVE_SMS"},REQUEST_CODE_ASK_PERMISSION);
        }
        if(ContextCompat.checkSelfPermission(getBaseContext(),"android.permission.READ_SMS")!=PackageManager.PERMISSION_GRANTED){
            final int REQUEST_CODE_ASK_PERMISSION=123;
            ActivityCompat.requestPermissions(this,new String[]{"android.permission.READ_SMS"},REQUEST_CODE_ASK_PERMISSION);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS))
            {
                //do nothing user denied for it
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},My_Permisssion_request_Recieve_Sms);
            }
        }
        tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
//java code for menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
           int id =item.getItemId();

      if(id==R.id.registartion){
        Toast.makeText(this,"enter user details here",Toast.LENGTH_LONG).show();

        Intent ractivity =new Intent(this,Registration.class);
        startActivity(ractivity);
        }
        if(id==R.id.about)
        {
            Intent about =new Intent(this,about_us.class);
            startActivity(about);

        }
       if(id==R.id.exit){

           System.exit(0);
           Toast.makeText(this,"Lostdroid is closed by the user",Toast.LENGTH_LONG).show();
       }
        return super.onOptionsItemSelected(item);
    }
    public void fetchLocation(View view) {

        tv = (TextView) findViewById(R.id.textView);

        tv.setText(string);
        tv.setVisibility(View.VISIBLE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        Toast.makeText(getApplicationContext(), "fetching device location.. please wait!!!!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onLocationChanged(Location location) {

        string="latitude:" + location.getLatitude() + " longitude:" + location.getLongitude();
        tv.setText(string);
        msg = "https://www.google.co.in/maps/@" + location.getLatitude() + "," + location.getLongitude() + ",15z?hl=en&authuser=0";
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onProviderDisabled(String provider) {}
    public void sendSms(View view) {
       SmsGenerator(msg);
       tv.setText(string);
    }
    //this code works on send status button it will fetch the device details and then call sms generator and send msg to the number using intent
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendStatus(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        String IMEINumber=tm.getDeviceId();
        String subscriberID=tm.getDeviceId();
        String SIMSerialNumber=tm.getSimSerialNumber();
        String networkCountryISO=tm.getNetworkCountryIso();
        String SIMCountryISO=tm.getSimCountryIso();
        String softwareVersion=tm.getDeviceSoftwareVersion();
        String voiceMailNumber=tm.getVoiceMailNumber();
        String imei=tm.getSimOperatorName();
        String strphoneType="";

        int phoneType=tm.getPhoneType();

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType="NONE";
                break;
        }
        boolean isRoaming=tm.isNetworkRoaming();

        String info="Phone Details:\n"+" IMEI Number:"+IMEINumber+"\n SubscriberID:"+subscriberID+"" +
                "\n Sim Serial Number:"+SIMSerialNumber+"\n Network Country " +
                "ISO:"+networkCountryISO+"\n SIM Country ISO:"+SIMCountryISO+"\n Software Version:"+softwareVersion
        +"\n Voice Mail Number:"+voiceMailNumber+"\n Phone Network Type:"+strphoneType+"\n In Roaming? :"+isRoaming+"\n imei:"+imei;

        tv2=(TextView)findViewById(R.id.textView2);
        msg3="subs id"+subscriberID+" IMEI number : "+IMEINumber+" sim serial number : "+SIMSerialNumber;
        SmsGenerator(msg3);
        tv2.setText(info);
    }
    public void SmsGenerator(String msg1){

        SmsManager sms = SmsManager.getDefault();
PendingIntent pi;
        pi = PendingIntent.getActivity(getApplicationContext(), 0,new Intent(), 0);

        sms.sendTextMessage(mobile, null, msg1, pi, null);
        Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_LONG).show();
    }
    public void sendEmail() {

        Toast.makeText(this,"in email method from receiver class",Toast.LENGTH_LONG).show();
        String email = "agrawalgovind998@gmail.com";
        String subject = "device location";
        String message = msg+"                          \ndevice status:"+msg3;
        SendMail sm = new SendMail(this, email, subject, message);
        sm.execute();
    }
    public void sendEmail2(){

    }
    public void WifiState(){

        if(wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(false);
            Toast.makeText(this,"wifi disabled",Toast.LENGTH_SHORT).show();
        }else{
            wifiManager.setWifiEnabled(true);
            Toast.makeText(this,"wifi enabled",Toast.LENGTH_SHORT).show();

        }
}
public void CaptureImage(View view){

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
     imageView=(ImageView) findViewById(R.id.imageView2);

    startActivityForResult(intent,0);
}

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(resultCode,resultCode,data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
  }
