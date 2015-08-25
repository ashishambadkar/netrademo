package com.example.netra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.netra.MainActivity;

public class Tracking{
	
	ArrayList<LatLng> markerPoints;
	LatLng latLng;
	GpsTracker gps = null;
//	private ProgressDialog dialog;
	Context context = null;
	boolean sucess = false;
	static Polyline polylineFinal = null;
	
	
	public Tracking(Context context1) {
		// TODO Auto-generated constructor stub
	
	/*	if(MainActivity.googleMap != null){
	        // Enable MyLocation Button in the Map
			MainActivity.googleMap.setMyLocationEnabled(true);

 	     // Already two locations
	        if(markerPoints.size() > 1){
	            markerPoints.clear();
	        }
	        
		}   */   
		
		context = context1;		
	}
	
	public  GpsTracker GetGpsTracker(Context appcontext)
	{
	   if(gps == null)
	   {	   
		    
		    gps = new GpsTracker(appcontext);
		//    context = appcontext;	
	   }	   
	   return gps;
	}
	
	public void AddPoints(LatLng src, LatLng desc){
		//LatLng point1 = new LatLng(marker1.getmLatitude(), marker1.getmLatitude());
		//LatLng point2 = new LatLng(marker2.getmLatitude(), marker2.getmLatitude());
		
		String url = getDirectionsUrl(src, desc);
		DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
		
		//return url;
	}
		
	 private String getDirectionsUrl(LatLng origin,LatLng dest){
		 
	        // Origin of route
	        String str_origin = "origin="+origin.latitude+","+origin.longitude;
	 
	        // Destination of route
	        String str_dest = "destination="+dest.latitude+","+dest.longitude;
	 
	        // Sensor enabled
	        String sensor = "sensor=false";
	 
	        // Building the parameters to the web service
	        String parameters = str_origin+"&"+str_dest+"&"+sensor;
	 
	        // Output format
	        String output = "json";
	 
	        // Building the url to the web service
	        String url = "http://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
	        			 
	        return url;
	    }
	
	
	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException{
	    String data = "";
	    InputStream iStream = null;
	    HttpURLConnection urlConnection = null;
	    try{
	       
	    	 URL url = new URL(strUrl);
	      //  URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=Bangalore,India&destination=Belgaum,India&sensor=false");
	        // Creating an http connection to communicate with url
	        urlConnection = (HttpURLConnection) url.openConnection();

	        // Connecting to url
	        urlConnection.connect();

	        // Reading data from url
	        iStream = urlConnection.getInputStream();

	        BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

	        StringBuffer sb = new StringBuffer();

	       
	        
	        String line = "";
	        while( ( line = br.readLine()) != null){
	            sb.append(line);
	        }

	        data = sb.toString();
	        br.close();

	    }catch(Exception e){
	        Log.d("Exception while downloading url", e.toString());
	    }finally{
	        iStream.close();
	        urlConnection.disconnect();
	    }
	    Log.i("data","data  in download URLLLL" +data );

	    return data;
	}

	
	// Fetches data from url passed
	public class DownloadTask extends AsyncTask<String, CharSequence, String>{
  
	//	public static final int timerEnd = 2000;
	 //    private Handler timeHandler = new Handler();
	  //   ProgressDialog dProgress = new ProgressDialog(context);
    
	     @Override
		protected void onProgressUpdate(CharSequence... values) {
			// TODO Auto-generated method stub
		//	super.onProgressUpdate(values);
			  // dProgress.setMessage((String)values[0]);
		        //Reset the timer (remove and re-add)
		     //   timeHandler.removeCallbacks(handleTimeout);
		     //   timeHandler.postDelayed(handleTimeout, timerEnd);
		}

		@Override
        protected void onPreExecute() {
      
			  Toast.makeText(context, "Downloading Routes.... "  , Toast.LENGTH_LONG).show(); 
	        /*  dProgress.setMessage("Getting Path.....");
	          dProgress.setCancelable(false);
	          dProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                     //Dismissing dProgress
	                     dialog.dismiss();
	                     //Removing any Runnables
	                     timeHandler.removeCallbacks(handleTimeout);
	                     //cancelling the AsyncTask
	                     cancel(true);

	                     //Displaying a confirmation dialog
	                     AlertDialog.Builder builder = new AlertDialog.Builder(context);
	                     builder.setMessage("Download cancelled.");
	                     builder.setCancelable(false);
	                     builder.setPositiveButton("OK", null);
	                     builder.show();
	                } //End onClick()
	          }); //End new OnClickListener()
	          dProgress.show(); */

        }  
        		
		// Downloading data in non-ui thread
	    @Override
	    protected String doInBackground(String... url) {

	        // For storing data from web service
	        String data = "";
	        try{
	            // Fetching the data from web service
	            data = downloadUrl(url[0]);
	            Log.i("Tracking", "data  :: " + data);
	        }catch(Exception e){
	          // 	 Toast.makeText(context, "Exception while Downloading Routes.... "  , Toast.LENGTH_SHORT).show();
	        	 Log.d("Background Task",e.toString());
	            //timeHandler.removeCallbacks(handleTimeout); 
	            return null;
	        }
	        
	        if(data.length() >= 1)
	        {	
	        	sucess = true;
	        }
	      
	     //   timeHandler.removeCallbacks(handleTimeout);
	       // timeHandler.postDelayed(handleTimeout, timerEnd);
  
	        return data;
	    }

	 
		// Executes in UI thread, after the execution of
	    // doInBackground()
	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);

	       if(result != null){
	    	
	       }
	       else{
	    	   Toast.makeText(context, "Download Fail Unable to download data.... "  , Toast.LENGTH_SHORT).show();
	       } 
	    	   ParserTask parserTask = new ParserTask();

	        // Invokes the thread for parsing the JSON data
	        parserTask.execute(result);
	    }
	    

	  /*  private Runnable handleTimeout = new Runnable() {
	        public void run() {
	                //Dismiss dProgress and bring up the timeout dialog
	                dProgress.dismiss();
	                AlertDialog.Builder builder = new AlertDialog.Builder(context);
	                
	                if(sucess)
	                	builder.setMessage("Download completed.");
	                else
	                	builder.setMessage("Download error.");
	                builder.setCancelable(false);
	                builder.setPositiveButton("OK", null);
	                builder.show();
	        }
	    }; */
	}
	
	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
	    // Parsing the data in non-ui thread
	    @Override
	    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

	        JSONObject jObject;
	        List<List<HashMap<String, String>>> routes = null;

	        try{
	        	Log.i("JSON", "jsonData :::::" + jsonData);
	            jObject = new JSONObject(jsonData[0]);
	            DirectionsJSONParser parser = new DirectionsJSONParser();

	            // Starts parsing data
	            routes = parser.parse(jObject);
	            Log.i("Tracking", "Routes size" + routes.size());
	            
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        
	           return routes;

	    }

	    // Executes in UI thread, after the parsing process
	   @Override
	    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
	        ArrayList<LatLng> points = null;
	        PolylineOptions lineOptions = null;
	        points = new ArrayList<LatLng>();
	   
	        // Traversing through all the routes
	        if(result != null){
	        	for(int i=0;i<result.size();i++){
	        		lineOptions = new PolylineOptions();
	
		            // Fetching i-th route
		            List<HashMap<String, String>> path = result.get(i);
	
		            // Fetching all the points in i-th route
		            for(int j=0;j<path.size();j++){
		                HashMap<String,String> point = path.get(j);
	
		                double lat = Double.parseDouble(point.get("lat"));
		                double lng = Double.parseDouble(point.get("lng" ));
		                Log.i("TAG","lat ::" + lat + "lng ::" + lng);
		                
		                LatLng position = new LatLng(lat, lng);
		                points.add(position);
		              //  points.add(latLng);
		            }
		            // Adding all the points in the route to LineOptions
		            lineOptions.addAll(points);
		            lineOptions.width(10);
		            lineOptions.visible(true);
		            lineOptions.color(Color.RED);	
		             
		            // drawDashedPolyLine(HomeFragment.googleMap, points, Color.RED);
	        	}
	        	
	        	// Drawing polyline in the Google Map for the i-th route
	        	if((lineOptions != null) && (!points.isEmpty()))
	        	{
	        		if(polylineFinal != null)
	        			polylineFinal.remove();
	        		
	        		polylineFinal = HomeFragment.googleMap.addPolyline(lineOptions);
	        		Toast.makeText(context, "Download Route Sucessful.... "  , Toast.LENGTH_SHORT).show();
	        	}else{
	        		if(polylineFinal != null)
	        			polylineFinal.remove();
	        		Toast.makeText(context, "No Routes Downloaded...Issue with GPS Provider...... "  , Toast.LENGTH_SHORT).show();
	        	}
	         
	        }else{
	 	        	
	        }
 
	        
	    } 
	}

   public String GetLocationInfo(Context context)
   {
		 
	 String data = new String();  
	 // check if GPS enabled
     GpsTracker gpsTracker = new GpsTracker(context);
    // gpsTracker.getLocation();
     
     if (gpsTracker.getIsGPSTrackingEnabled())
     {
         String stringLatitude = String.valueOf(gpsTracker.latitude);
         String stringLongitude = String.valueOf(gpsTracker.longitude);
         String country = gpsTracker.getCountryName(context);
         String city = gpsTracker.getLocality(context);
         String postalCode = gpsTracker.getPostalCode(context);
         String addressLine = gpsTracker.getAddressLine(context);
         data += addressLine + " " + 
        		 	   city + " " + postalCode + " " 
        		 	   + country + "latitude :" + stringLatitude
        		 	   + " " + "longittude : " +  stringLongitude;
         Log.d("data is ::::::", data);
         
         
         return data;
     }
     else
     {
         // can't get location
         // GPS or Network is not enabled
         // Ask user to enable GPS/network in settings
    	 Log.d("SHOW alert ::::::", data);
    	 gpsTracker.showSettingsAlert(context);
     }
 
     return null;
	   
   }
   
   public LatLng GetMyCurrentLocation(Context context)
   {
	   LatLng latLng= null;
	
		   if (GetGpsTracker(context).isGPSTrackingEnabled)
		   {
			   double latitude = GetGpsTracker(context).getLatitude();
			   double longitude = GetGpsTracker(context).getLongitude();
			   latLng = new LatLng(latitude, longitude);
			 //  return latLng;
		   }
	   
		   return latLng;
   }
   
   
   private void drawDashedPolyLine(GoogleMap mMap, ArrayList<LatLng> listOfPoints, int color) {
	    /* Boolean to control drawing alternate lines */
	    boolean added = false;
	    for (int i = 0; i < listOfPoints.size() - 1 ; i++) {
	        /* Get distance between current and next point */
	        double distance = getConvertedDistance(listOfPoints.get(i),listOfPoints.get(i + 1));

	        /* If distance is less than 0.002 kms */
	        if (distance < 0.002) {
	            if (!added) {
	                mMap.addPolyline(new PolylineOptions()
	                        .add(listOfPoints.get(i))
	                        .add(listOfPoints.get(i + 1))
	                        .color(color));
	                added = true;
	            } else {/* Skip this piece */
	                added = false;
	            }
	        } else {
	            /* Get how many divisions to make of this line */
	            int countOfDivisions = (int) ((distance/0.002));

	            /* Get difference to add per lat/lng */
	            double latdiff = (listOfPoints.get(i+1).latitude - listOfPoints
	                    .get(i).latitude) / countOfDivisions;
	            double lngdiff = (listOfPoints.get(i + 1).longitude - listOfPoints
	                    .get(i).longitude) / countOfDivisions;

	            /* Last known indicates start point of polyline. Initialized to ith point */
	            LatLng lastKnowLatLng = new LatLng(listOfPoints.get(i).latitude, listOfPoints.get(i).longitude);
	            for (int j = 0; j < countOfDivisions; j++) {

	                /* Next point is point + diff */
	                LatLng nextLatLng = new LatLng(lastKnowLatLng.latitude + latdiff, lastKnowLatLng.longitude + lngdiff);
	                if (!added) {
	                    mMap.addPolyline(new PolylineOptions()
	                    .add(lastKnowLatLng)
	                    .add(nextLatLng)
	                    .color(color));
	                    added = true;
	                } else {
	                    added = false;
	                }
	                lastKnowLatLng = nextLatLng;
	            }
	        }
	    }
	}

	private double getConvertedDistance(LatLng latlng1, LatLng latlng2) {
	    double distance = Distance.distance(latlng1.latitude,
	            latlng1.longitude,
	            latlng2.latitude,
	            latlng2.longitude);
	    BigDecimal bd = new BigDecimal(distance);
	    BigDecimal res = bd.setScale(3, RoundingMode.DOWN);
	    return res.doubleValue();
	}
   
}
