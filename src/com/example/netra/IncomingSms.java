package com.example.netra;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.NotificationCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.appcompat.R.string;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class IncomingSms {
	
	    static Context appContext = null;
	    private static IncomingSms inst = null;
	    ArrayList<String> smsMessagesList = new ArrayList<String>();
	    static int count;
	    string smsdata = null;
	    String msgtype = "";
	    String tid = null;
	    String msg = null;
	    Double lat=0.0;
	    Double lon =0.0;
	    String time=null;
	    //LatLng pos = null;
	    Sensordata sensorData = null;
	    
	    public static synchronized IncomingSms GetSmsInstance(Context context) {
	    	if(inst == null)	{
	    		inst = new IncomingSms(); 
	    		appContext = context;
	    	}
	    	return inst;
	    }
	    
	  public void UpdateMapInfo(String smsMessageStr)
	  {
		  if(smsMessageStr.contains("tid"))
		  {	  
			  boolean check =	GetDatafromSms(smsMessageStr);
			  if(msgtype.equals("RED")){
				  SetNotification();
				  SetData();
				  GetSensordata();
			  }
		  }	  
	  }   
	  
	  private void SetNotification()
	  {
		  NotificationCompat.Builder mBuilder =
				    new NotificationCompat.Builder(appContext)
		  			.setSmallIcon(R.drawable.netra)
		 		    .setContentTitle("Netra Alert....")
				    .setContentText("Tree Under Treat :" + tid + " " + "time -"+  time );
		    Intent resultIntent = new Intent(appContext, MainActivity.class);
		    resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
		    TaskStackBuilder stackBuilder = TaskStackBuilder.create(appContext);
		    stackBuilder.addParentStack(MainActivity.class);
		    stackBuilder.addNextIntent(resultIntent);
		    PendingIntent resultPendingIntent =
		            stackBuilder.getPendingIntent(0,  PendingIntent.FLAG_CANCEL_CURRENT);
		    mBuilder.setDefaults(NotificationCompat.FLAG_AUTO_CANCEL);
		    long[] pattern = {500,500,500,500,500,500,500,500,500};
		    mBuilder.setVibrate(pattern);
		    mBuilder.setStyle(new NotificationCompat.BigPictureStyle());
		    mBuilder.setAutoCancel(true);
		    
		    mBuilder.setContentIntent(resultPendingIntent);
		    
		    Uri alarmSound = Uri.parse("android.resource://"
		            + appContext.getPackageName() + "/" + R.drawable.doorbell); 
		    mBuilder.setSound(alarmSound); 
		    NotificationManager mNotificationManager =
		            (NotificationManager) appContext.getSystemService(appContext.NOTIFICATION_SERVICE);
		    
		    mNotificationManager.notify(0, mBuilder.build());
		  
	  }
	  
	  /*SMS patterns for plot, red alert*/ 
	  
		   /*
			   * msgtype:PLOT
			   * tid:T2,
			   * msg:plotting tree on map,
			   * Lat=72.23123,
			   * lan=72.345345
			   */
	  
	  /*
	   * msgtype:RED,
	   * tid:T2,
	   * msg:Tree under threat,
	   * Lat:72.23123,
	   * Lon:72.345345
	   */
	/*
	  private boolean ParseSmsMessage(String smsMessage)
	  {
		 msgtype = getTextBetweenTwoWords("msgtype:", ",", smsMessage); 
		 Log.i("getTextBetweenTwoWords", "msgtype " +msgtype);
		 tid = getTextBetweenTwoWords("tid:", ",", smsMessage); 
		 Log.i("getTextBetweenTwoWords", "tid " +tid);
		 msg = getTextBetweenTwoWords("msg:", ",", smsMessage); 
		 Log.i("getTextBetweenTwoWords", "msg: " +msg);
		 String lat = getTextBetweenTwoWords("Lat:", ",", smsMessage);
		 Log.i("getTextBetweenTwoWords", "lat: " +lat);
		 String lon = getTextBetweenTwoWords("Lon:", "'", smsMessage); 
		 Log.i("getTextBetweenTwoWords", "lon: " +lon);
	 	 pos = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
     	 Toast.makeText(appContext, 
     			        "tid :" + tid          + 
     			        "msgtype :" + msgtype  +
     			        "msg :" + msg          +
     			        "lat :" + pos.latitude +
     			        "lon :" + pos.longitude,Toast.LENGTH_LONG ).show();
	 	 return true;  
	  }
	    
	  private  String getTextBetweenTwoWords(String text)
	  {
		  Log.i("getTextBetweenTwoWords", "test " +text);
		  
		  if(text.substring(("msgtype:").indexOf(c))
		  {
			  
		  }
	      String data =  text.substring(text.indexOf(firstWord) + firstWord.length(), text.indexOf(secondword));
	      Log.i("getTextBetweenTwoWords", "data " +data);
	      return data;
	  }*/
	  
	  
	  private boolean GetDatafromSms(String smsMessageStr)
	  {
		  StringTokenizer tokenizer = new StringTokenizer(smsMessageStr, ",",false);
		  while (tokenizer.hasMoreTokens()) 
		  {
		      String token = tokenizer.nextToken();
		  //    System.out.println("==Token== : "+ token);
		  //    Log.i("netra", "Token :" +token);
		     
		      SetdataFromSms(token);
		       
		  }
		  
	     Toast.makeText(appContext, 
			        "tid :" + tid          + 
			        "msgtype :" + msgtype  +
			        "msg :" + msg          +
			        "lat :" + lat          +
			        "lon :" + lon           
			        ,Toast.LENGTH_LONG ).show(); 
	      
	      return true;
	  }
	  
	  private void SetdataFromSms(String token)
	  {
		  String firstWord;
		  Log.i("netra", "token-->" +token);
		  if(token.contains("msgtype:"))
		  {	  
			  firstWord = "msgtype:";
	    	  msgtype =  token.substring(token.indexOf(firstWord) + firstWord.length(), token.length());   	  
	    	  Log.i("netra", "msgtype --->" + msgtype);
		  }else if (token.contains("tid:")) {
			  firstWord = "tid:";
			  tid =  token.substring(token.indexOf(firstWord) + firstWord.length(), token.length());
			  Log.i("netra", "tid --->" + tid);
		  }else if (token.contains("msg:")) {
			  firstWord = "msg:";
			  msg =  token.substring(token.indexOf(firstWord) + firstWord.length(), token.length());
			  Log.i("netra", "msg --->" + msg);
		  }else if (token.contains("Lat:")) {
			 firstWord = "Lat:";
			 String latitude =  token.substring(token.indexOf(firstWord) + firstWord.length(), token.length());
			 Log.i("netra", "latitude --->" + latitude);
			 lat = Double.parseDouble(latitude);
		  }else if (token.contains("Lon:")) {
			 firstWord = "Lon:";
			 //String timetoken = "SMS Time:";
			 String longitude =  token.substring(token.indexOf(firstWord) + firstWord.length(), token.length());
			 lon = Double.parseDouble(longitude);
			 Log.i("netra", "longitude --->" + longitude);
				  
		  }else if (token.contains("SMS Time:")) {
			  firstWord = "SMS Time:";
			  time =  token.substring(token.indexOf(firstWord) + firstWord.length(), token.length());
			  System.out.println("netra msg --->" + time);
		  }else{
			 Toast.makeText(appContext, "No proper Token...wrong SMS "  + token, Toast.LENGTH_LONG).show();
		  }
		  		  
	  }
	  
	  public void SetData()
	  {
		  sensorData = new Sensordata();
		  sensorData.lat = lat;
		  sensorData.lon = lon;
		  sensorData.msg = new String(msg); 
		  sensorData.msgtype = new String(msgtype);
		  sensorData.tid = new String(tid);
		  sensorData.time = new String(time);
		  sensorData.alloted = true;
	  }
	  
	  public void GetSensordata()
	  {
		  if(sensorData.alloted)
		  {	  
		//	  return sensorData;
		  	  HomeFragment.GetdataSensordata(sensorData);
		  }
	  }
}
