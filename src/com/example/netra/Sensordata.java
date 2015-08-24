package com.example.netra;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.internal.GetDataItemResponse;

import android.support.v7.appcompat.R.string;

public class Sensordata {
	
	String msgtype;
    String tid;
    String msg;
    double lat;
    double lon;
    LatLng pos;
    String time;
    boolean alloted;
	    
    public Sensordata() {
    	// TODO Auto-generated constructor stub
	  	String msgtype = "";
	  	String tid = null;
	    String msg = null;
	    double lat = 0.0;
	    double lon = 0.0;
	    LatLng pos = null;
	    alloted = false;
	}
	    
    public static Sensordata GetDataItem() 
    {
    	
    	
		return null;
    	
    }
}
