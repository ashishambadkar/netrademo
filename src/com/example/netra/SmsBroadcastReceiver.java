package com.example.netra;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

    public class SmsBroadcastReceiver extends BroadcastReceiver {

        public static final String SMS_BUNDLE = "pdus";

        @SuppressLint("SimpleDateFormat")
		public void onReceive(Context context, Intent intent) {
            Bundle intentExtras = intent.getExtras();
            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
                String smsMessageStr = "";
                for (int i = 0; i < sms.length; ++i) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    String smsBody = smsMessage.getMessageBody().toString();
                    String address = smsMessage.getOriginatingAddress();
                    
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(smsMessage.getTimestampMillis());
                     
                    SimpleDateFormat df = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss Z");
                    String date = df.format(Calendar.getInstance().getTime());
                    
                    smsMessageStr += "SMS From:" + address + "\n";
                    smsMessageStr += "SMS Body:" + smsBody + "," + "\n";
                    smsMessageStr += "SMS Time:" + date;
                }
                Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
                Log.i("netra", "smsMessageStr " +smsMessageStr );	
                //this will update the UI with message
                IncomingSms inst = IncomingSms.GetSmsInstance(context);
                inst.UpdateMapInfo(smsMessageStr); 
               // inst.updateList(smsMessageStr);
            }
        }
    }