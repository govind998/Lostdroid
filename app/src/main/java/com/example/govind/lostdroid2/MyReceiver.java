package com.example.govind.lostdroid2;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver implements LocationListener {
    private WifiManager wifiManager;
    private SharedPreferences preferences;

    private static final int Request_call=1;

    SQLiteDatabase sqLiteDatabase;
    SQLiteOpenHelper sqLiteOpenHelper;

Cursor cursor;
    String msg4="";
    MainActivity obj = new MainActivity();
    TelephonyManager tm2;
    SmsManager sms = SmsManager.getDefault();
    String msg="null";
    String Passkey="";
    String passkey2="";
    String wifissid="";
    String mobile2="null";
    String chargingstatus;
    LocationManager locationManager;
    PendingIntent pi;
    Context context2;
    String fullname="null";
    String batteryp;

    String mobile3="";
    String address="null";
    String phone="null";
    Registration obj2=new Registration();
    Activity activity;
    //this method is call whenever the message arrives on phone
    //this is the trigger method for all the modules in project
    //ri8 now its triggering wifi status and sending device status to the alternate number


   /* public void showNotification(String title, String message) {
        NotificationManager mNotificationManager =
                (NotificationManager) context2.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        Intent callIntent = new Intent(context2,BroadcastReceiver.class);
        callIntent.setAction(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+"9819395931"));//change the number
        PendingIntent callintent2=PendingIntent.getBroadcast(context2,0,callIntent,0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context2, "YOUR_CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("lostdroid") // title for notification
                .setContentText("owner info")// message for notification

                .setContentIntent(callintent2)
                .addAction(R.drawable.location,"call",callintent2)
                .setAutoCancel(true); // clear notification after click


        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
*/
    public String LoadHandler(){
        String result="";
        String result2="";
        String result3="";
        String result4="";
        String result6="";
        String result5="";
        String Query="SELECT * FROM "+DatabaseHelper.table;
        sqLiteDatabase=sqLiteOpenHelper.getWritableDatabase();
        cursor=sqLiteDatabase.rawQuery(Query,null);
        if(cursor.getCount()>0)
        {
            //int result_0=cursor.getInt(0);
          //  String result_1=cursor.getString(1);
            //result +=String.valueOf(result_0)+" "+result_1;
            cursor.moveToLast();
            result=cursor.getString(0);
            result2=cursor.getString(6);
            result3=cursor.getString(2);
            result4=cursor.getString(3);
            result5=cursor.getString(4);
            result6=cursor.getString(5);

        }
        address=result2;
        phone=result3;
       fullname=result;
       mobile2=result4;
       Passkey=result5;
       passkey2=result6;

        return result;
    }
    public void call() {
        Toast.makeText(context2,"call",Toast.LENGTH_SHORT).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel: 9819395931"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context2, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context2,"YOUR_CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("lostdroid")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        notificationBuilder.setStyle(inboxStyle);
        inboxStyle.setBigContentTitle("Device Owner Details");

        inboxStyle.addLine("name : "+fullname);
        inboxStyle.addLine("phone : "+phone);
        inboxStyle.addLine("Address : "+address);
        notificationBuilder.setStyle(inboxStyle);

        NotificationManager notificationManager =
                (NotificationManager) context2.getSystemService(Context.NOTIFICATION_SERVICE);
        int NOTIFICATION_ID = 100;
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }




    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

//risk area
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        // Are we charging / charged?
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        if(isCharging){

            chargingstatus="charging";

        }
        else{
            chargingstatus="not charging";
        }
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        batteryp=Integer.toString(level);
// How are we charging?
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;


        //end of risk
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        tm2 = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        context2=context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        sqLiteOpenHelper=new DatabaseHelper(context);
        sqLiteDatabase=sqLiteOpenHelper.getReadableDatabase();

        LoadHandler();


        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msgBody;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        mobile3 = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                        // Toast.makeText(context,"message:"+msgBody+"\n phone number:"+msg_from,Toast.LENGTH_LONG).show();
                        if (msgBody.equals(Passkey)) {


                            //showNotification("hello,","bye");
                            call();
                            LoadHandler();
                            Toast.makeText(context, "key entered is correct\n", Toast.LENGTH_LONG).show();
                            Toast.makeText(context, "message:" + msgBody + "\n phone number:" + mobile2, Toast.LENGTH_LONG).show();


                            ConnectivityManager cm =
                                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                            boolean isConnected = activeNetwork != null &&
                                    activeNetwork.isConnectedOrConnecting();

                            if (isConnected) {
                                Toast.makeText(context, "internet is Available", Toast.LENGTH_LONG).show();
                                WifiInfo wifiinfo=wifiManager.getConnectionInfo();
                                wifissid=wifiinfo.getSSID();
                                Toast.makeText(context, "wifi enabled"+wifissid, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "internet is not Available", Toast.LENGTH_LONG).show();

                                if (!wifiManager.isWifiEnabled()) {
                                    wifiManager.setWifiEnabled(true);
                                    WifiInfo wifiinfo=wifiManager.getConnectionInfo();
                                    wifissid=wifiinfo.getSSID();
                                    Toast.makeText(context, "wifi enabled"+wifissid, Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (ContextCompat.checkSelfPermission(context,
                                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            }
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10000, this);

                            String IMEINumber = tm2.getDeviceId();
                            String subscriberID = tm2.getDeviceId();
                            String SIMSerialNumber = tm2.getSimSerialNumber();
                            String networkCountryISO = tm2.getNetworkCountryIso();
                            String SIMCountryISO = tm2.getSimCountryIso();
                            String softwareVersion = tm2.getDeviceSoftwareVersion();
                            String voiceMailNumber = tm2.getVoiceMailNumber();
                            String imei = tm2.getSimOperatorName();
                            int phoneType = tm2.getPhoneType();
                            String strphoneType = "";
                            switch (phoneType) {
                                case (TelephonyManager.PHONE_TYPE_CDMA):
                                    strphoneType = "CDMA";
                                    break;
                                case (TelephonyManager.PHONE_TYPE_GSM):
                                    strphoneType = "GSM";
                                    break;
                                case (TelephonyManager.PHONE_TYPE_NONE):
                                    strphoneType = "NONE";
                                    break;
                            }

                            //getting information if phone is in roaming
                            boolean isRoaming = tm2.isNetworkRoaming();

                            msg = "Phone Details:\n" + " IMEI Number:" + IMEINumber + "\n SubscriberID:" + subscriberID + "" +
                                    "\n Sim Serial Number:" + SIMSerialNumber + "\n Network Country " +
                                    "ISO:" + networkCountryISO + "\n SIM Country ISO:" + SIMCountryISO + "\n Software Version:" + softwareVersion
                                    + "\n Voice Mail Number:" + voiceMailNumber + "\n Phone Network Type:" + strphoneType + "\n In Roaming? :" + isRoaming + "\n imei:" + imei;

                            String msg3="subs id"+subscriberID+"\n IMEI number : "+IMEINumber+"\n sim serial number : "+SIMSerialNumber+"\n wifi name :"+wifissid+"\n battery percentage :"+batteryp ;
                            msg4="battery status\n"+"battery percentage :"+batteryp+"\n charging percentage :"+batteryp;
                            SmsManager sms = SmsManager.getDefault();

                            pi = PendingIntent.getActivity(context, 0,new Intent(), 0);

                            sms.sendTextMessage(phone, null, msg3, pi, null);
                            sms.sendTextMessage(mobile2, null, msg3, pi, null);
                            sms.sendTextMessage(mobile3, null, msg3, pi, null);
                            Toast.makeText(context, "message sent", Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        String currentlocation="latitude:" + location.getLatitude() + " longitude:" + location.getLongitude();
        String msg = "https://www.google.co.in/maps/@" + location.getLatitude() + "," + location.getLongitude() + ",15z?hl=en&authuser=0";
        sms.sendTextMessage(phone, null, msg, pi, null);
        sms.sendTextMessage(mobile2, null, msg, pi, null);
        sms.sendTextMessage(mobile3, null, msg, pi, null);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}

