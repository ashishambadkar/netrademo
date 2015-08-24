package com.example.netra;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.netra.Tracking.DownloadTask;
//import com.example.netra.MainActivity.MarkerInfoWindowAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	   public static GoogleMap googleMap;
	   StreetViewPanorama m_StreetView;
	   private static ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
	   private static HashMap<Marker, MyMarker> mMarkersHashMap;
	   Bundle instance;
	   public static LayoutInflater inflaterInsideActivity;
	   private LatLngBounds.Builder markerBuilder;	
	   public static View view;
	   final int RQS_GooglePlayServices = 1;
	   private static Context globalContext = null;
	   
	   	   
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		if ( view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	    	 view = inflater.inflate(R.layout.fragment_home, container, false);
	    } catch (InflateException e) {
	       System.out.println("Exception inflater");
	    }
	          
	    globalContext = this.getActivity();
        instance = savedInstanceState;
        inflaterInsideActivity = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<Marker, MyMarker>();

        // mMyMarkersArray.add(new MyMarker("T1", "icon1", Double.parseDouble("77.338193"), Double.parseDouble("77.338193"),"tree planted 5 years back"));
        mMyMarkersArray.add(new MyMarker("T2", "Green", Double.parseDouble("16.221686"), Double.parseDouble("77.338708"),"tree planted 12 years back"));
        mMyMarkersArray.add(new MyMarker("T3", "Green", Double.parseDouble("16.221682"), Double.parseDouble("77.338708"),"tree planted 10 years back"));
        mMyMarkersArray.add(new MyMarker("T4", "Green", Double.parseDouble("16.218719"), Double.parseDouble("77.340252"),"tree planted 15 years back"));
        mMyMarkersArray.add(new MyMarker("T5", "Green", Double.parseDouble("16.218058"), Double.parseDouble("77.338124"),"tree planted 11 years back"));
        mMyMarkersArray.add(new MyMarker("T6", "Green", Double.parseDouble("16.218006"), Double.parseDouble("77.338275"),"tree planted 7 years back"));
        mMyMarkersArray.add(new MyMarker("T7", "Green", Double.parseDouble("16.218037"), Double.parseDouble("77.338296"),"tree planted 5 years back"));
        mMyMarkersArray.add(new MyMarker("T8", "Green", Double.parseDouble("16.217975"), Double.parseDouble("77.338350"),"tree planted 5 years back"));
        mMyMarkersArray.add(new MyMarker("T9", "Green", Double.parseDouble(" 16.217903"), Double.parseDouble("77.338285"),"tree planted 25 years back"));
        mMyMarkersArray.add(new MyMarker("T10", "Green", Double.parseDouble("16.217893"), Double.parseDouble("77.338328"),"tree planted 25 years back"));
        mMyMarkersArray.add(new MyMarker("T11","Green", Double.parseDouble("16.217842"), Double.parseDouble("77.338393"),"tree planted 25 years back"));		
        mMyMarkersArray.add(new MyMarker("T12", "Green", Double.parseDouble("16.217811"), Double.parseDouble("77.338457"),"tree planted 25 years back"));

        setUpMap();
        plotMarkers(mMyMarkersArray);     
        markerBuilderRegion(mMyMarkersArray);
        return view;
    }
	
	
	  private void markerBuilderRegion(ArrayList<MyMarker> markers)
	  {
		   markerBuilder = new LatLngBounds.Builder();
		  // LatLng position;
		   
		   for (MyMarker myMarker : markers) {
			   markerBuilder.include(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
			  
		   }
		
		   LatLngBounds bounds = markerBuilder.build();
		   int width = getResources().getDisplayMetrics().widthPixels;
		   int height = getResources().getDisplayMetrics().heightPixels;
		   CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,width -1000 , height -1000, 3);
		 
		  googleMap.animateCamera(cu);
		  // googleMap.moveCamera(cu);
		 //  googleMap.setMyLocationEnabled(true);
	   }	   
 		   
	  private void BuildOnResume(ArrayList<MyMarker> markers)
	  {
		  // markerBuilder = new LatLngBounds.Builder();
		  // LatLng position;
		   
		   for (MyMarker myMarker : markers) {
			//   mMarkersHashMap.put(currentMarker, myMarker); 
			   
			   //MyMarker customMarkar = mMarkersHashMap.get(myMarker);
			   Marker marker = getKeyFromValue(mMarkersHashMap, myMarker);
			  // MarkerOptions mOptions;
			   MarkerOptions mOptions = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
			  
			   if((myMarker.getmIcon()).equals("Green")){	   
				   mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));				   
			   }else{
				   mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				   myMarker.setmIcon("Red");
			   }
			 
			   Marker mMarker = googleMap.addMarker(mOptions);
			   mMarkersHashMap.remove(marker);
               mMarkersHashMap.put(mMarker, myMarker);  
               Log.i("HomeFragment", " mMarkersHashMap.size" + mMarkersHashMap.size());
               Log.i("HomeFragment", " Label :" + myMarker.getmLabel());
               Log.i("HomeFragment", " Icon :" + myMarker.getmIcon());
               Log.i("HomeFragment", " Remark :" + myMarker.getRemark());
               Log.i("HomeFragment", " Lat :" + myMarker.getmLatitude() + "Longitute "  + myMarker.getmLongitude());
               
               googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
 		          
		   }
	   }
	  
	  public static synchronized Marker getKeyFromValue(HashMap<Marker, MyMarker> hm, Object value) {
		    for (Object o : hm.keySet()) {
		      if (hm.get(o).equals(value)) {
		        return (Marker)o;
		      }
		    }
		    return null;
	  }
	  
	   @Override
	   public void onDestroyView() {
   	   			   
	   		Fragment fragment = (getFragmentManager()
	                .findFragmentById(R.id.map));
	   		
	   		if(fragment!= null){
		        if (fragment.isResumed()) {
		            getFragmentManager().beginTransaction().remove(fragment)
		                    .commit();
		        }
	   		}
	        super.onDestroyView();
	  
	   }
	   
   	   private void	SetCameraPosition(com.google.android.gms.maps.model.Marker marker)
	   {
		   	CameraPosition INIT =
		   			new CameraPosition.Builder()
		   			.target(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude))
		   			.zoom( 18F )
		   			.bearing( 300F) // orientation
		   			.tilt( 45F) // viewing angle
		   			.build();
		   			 // use GooggleMap mMap to move camera into position
		   	googleMap.animateCamera( CameraUpdateFactory.newCameraPosition(INIT) );
	  }		
	   
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		
		if(mMyMarkersArray != null)
		{	
			
	//		setUpMap();
	//		BuildOnResume(mMyMarkersArray);
	 //     markerBuilderRegion(mMyMarkersArray);
	        
		}
		  int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(globalContext);
 
		  if (resultCode == ConnectionResult.SUCCESS){
		   Toast.makeText(globalContext, 
		     "isGooglePlayServicesAvailable SUCCESS", 
		     Toast.LENGTH_LONG).show(); 
		  }else{
		   //GooglePlayServicesUtil.getErrorDialog(resultCode, MainActiv, RQS_GooglePlayServices); 
		  }
	}

	   private void plotMarkers(ArrayList<MyMarker> markers)
	   {
	       if(markers.size() > 0)
	       {
	           for (MyMarker myMarker : markers)
	           {
	               // Create user marker with custom icon and other options
	               MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
	               markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
	               Marker currentMarker = googleMap.addMarker(markerOption);
	               mMarkersHashMap.put(currentMarker, myMarker);                          
	               googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
	            }
	       }
	   }
	   
	   private void setUpMap()
	   {
	       // Do a null check to confirm that we have not already instantiated the map.
	       if (googleMap == null)
	       {
	           // Try to obtain the map from the SupportMapFragment.
	    	   googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    	  
	           if (googleMap != null)
	           {
	        	   EnableMapProperties();
	          	   googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
	               {
	                   @Override
	                   public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
	                   {
	                      
	                      //  createStreetView();
	                      /*  CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude),18);
	                	   googleMap.animateCamera(cu);
	                	   marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)); */
	                	   SetCameraPosition(marker);
	      	
	                  	   //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
	                	  // ShowAnimation(marker);
	                	   //marker.showInfoWindow();
	                	   
	                	//   Tracking track = new Tracking(globalContext);
	                   //	   String str=""; 
	                   	//   str = track.GetLocationInfo(globalContext);
	                	//   if(str != null)
	                	   {
	                		  // marker.setTitle(title);
	                		  Log.d("Netra" , "SIZEEEEEEE 1 :::::" + mMarkersHashMap.size());
	                		  MyMarker myMarker = mMarkersHashMap.get(marker);
	                	//	  Log.d("Netra" , "str :::::::" + str);
	                	//	  myMarker.setRemark(str);
	                	//	  Log.d("Netra" , "myMarker :::::::" + myMarker.getRemark());
	                		  mMarkersHashMap.put(marker, myMarker);
	                		  Log.d("Netra" , "SIZEEEEEEE  2:::::" + mMarkersHashMap.size());
	                		  
	                		//  LatLng latLng = GetMylocation(track);
	                		//  GetMapDirectionSrcDesc(latLng, new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
	                	   }
	                	  
	                	   ShowAnimation(marker);
	                	   marker.showInfoWindow();
	                	   return true;
	                   }
	               });
	        	       	   	          	   	        		        	   	        	  
	           }
	           else
	               Toast.makeText(globalContext , "Unable to create Maps", Toast.LENGTH_SHORT).show();
	       }
	   }
  	   
	   public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
	   {
	       public MarkerInfoWindowAdapter()
	       {
	       }

	       @Override
	       public View getInfoWindow(Marker marker)
	       {
	           return null;
	       }

	       @Override
	       public View getInfoContents(Marker marker)
	       {
	           View v  = inflaterInsideActivity.inflate(R.layout.infowindow_layout, null);

	           v.setBackgroundColor(0xFF0CAA); 
	           v.invalidate();
	           MyMarker myMarker = mMarkersHashMap.get(marker);
	           
	           Log.i("HomeFragment", "myMarker" + myMarker );
	           ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);
	           TextView markerLabel = (TextView) v.findViewById(R.id.marker_label);
	                  
	           TextView markerLabel1 = (TextView)v.findViewById(R.id.marker1_label);
	                      
	          //markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));
	           markerIcon.setImageResource(android.R.drawable.btn_dropdown); 	
	                  
	           markerLabel.setText(myMarker.getmLabel());
	           
	          Log.d("TAG", "getRemark  :" +myMarker.getRemark()); 
	          markerLabel1.setText(myMarker.getRemark());
	     
	          return v;
	       }
	   }

	   /**
	    * Initialises the street view member variable with the appropriate
	    * fragment from the FragmentManager
	    */
	     	
	   private static void GetMapDirectionSrcDesc(LatLng src, LatLng desc)
	   {
		   Tracking track = new Tracking(globalContext);
	   	   track.AddPoints(src, desc);
	   }
	   
	   private void EnableMapProperties()
	   {
		   googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		   googleMap.setBuildingsEnabled(true);
		   googleMap.setTrafficEnabled(true);
		   googleMap.setIndoorEnabled(true); 
		   googleMap.getUiSettings().setZoomControlsEnabled(true);
		   googleMap.getUiSettings().setCompassEnabled(true);
		   googleMap.getUiSettings().setMyLocationButtonEnabled(true);
	       
		   googleMap.getUiSettings().setRotateGesturesEnabled(true);
		   googleMap.getUiSettings().setScrollGesturesEnabled(true);
		   googleMap.getUiSettings().setTiltGesturesEnabled(true);
		   googleMap.getUiSettings().setZoomGesturesEnabled(true);
	       //myMap.getUiSettings().setAllGesturesEnabled(true);
		   googleMap.setMyLocationEnabled(true);
	   }
	   
	   private boolean ShowAnimation(final Marker marker)
	   {
		 //Make the marker bounce
	       final Handler handler = new Handler();
	       
	       final long startTime = SystemClock.uptimeMillis();
	       final long duration = 2000;
	       
	       Projection proj = googleMap.getProjection();
	       final LatLng markerLatLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
	       Point startPoint = proj.toScreenLocation(markerLatLng);
	      
	       final LatLng startLatLng = proj.fromScreenLocation(startPoint);
	       final BounceInterpolator interpolator = new BounceInterpolator();

	       handler.post(new Runnable() {
	           @Override
	           public void run() {
	               long elapsed = SystemClock.uptimeMillis() - startTime;
	               float t = interpolator.getInterpolation((float) elapsed / duration);
	               double lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude;
	               double lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude;
	               marker.setPosition(new LatLng(lat, lng));
	              
	               if (t < 1.0) {
	                   // Post again 16ms later.
	                   handler.postDelayed(this, 16);
	               }
	           }
	       });
	       
	       //return false; //have not consumed the event
	       return true; //have consumed the event
	   }
	   
	   
	   private static LatLng GetMylocation(Tracking track)
	   {
		   LatLng latlng; 
		   
		//  Tracking track =  new Tracking();
		  latlng = track.GetMyCurrentLocation(globalContext);
		  Log.i("HomeFragment"," GetMylocation :" + latlng.latitude + latlng.longitude);
		  Log.i("HomeFragment", "MyLocation " + track.GetLocationInfo(globalContext));
				 
		  return latlng;
	   }
	   
	/*   
	   public static synchronized void GetdataSensordata(Sensordata sensorData) { 
	   
		   SetSmsData downloadTask = new SetSmsData();
		   downloadTask.execute(sensorData);
	   }
	   
	   
	   private class SetSmsData extends AsyncTask<Sensordata, void , boolean> {
		    protected String doInBackground(Sensordata... sensorData) {
		       try {

		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		     
		 //     return response;
		    }
		    
		    @Override
		    protected void onPostExecute(boolean  result) {
		     // execution of result of Long time consuming operation
		    // finalResult.setText(result);
		    }
	   }*/ 
	   
	   @SuppressLint("UseValueOf")
	public static synchronized void GetdataSensordata(Sensordata sensorData) {
		
		   if(sensorData.alloted)
		   {
			  int size = mMyMarkersArray.size();
			  
			  int pos = size -1;
			  while(pos >= 0){
				  MyMarker myMarker = mMyMarkersArray.get(pos);
				  Log.i("Netra", "\n myMarker :"+ myMarker );
				  Marker marker = getKeyFromValue(mMarkersHashMap, myMarker);
				  Log.i("Netra", " \n marker :"+ marker );
				  if(myMarker.getmLabel().equals(sensorData.tid)) {
					  if(sensorData.msgtype.equals("RED")){
						  Log.i("Netra", " \n eQUALS  :"+ sensorData.tid + " TO" + myMarker.getmLabel());
						//  myMarker.setmIcon("RED");
						 // myMarker.
						  myMarker.UpdateMarkerData(sensorData.tid,"RED",sensorData.lat,sensorData.lon,sensorData.msg + "\n" +sensorData.time);
					    //  MarkerOptions mOptions = new MarkerOptions().position(new LatLng(sensorData.lat, sensorData.lon));
					 	//  mOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					 	//  mMyMarkersArray.add(new MyMarker(sensorData.tid, "Red", sensorData.lat, sensorData.lon, sensorData.msg + ", Time: " + sensorData.time));
						  // mMyMarkersArray.remove(pos);
					 	 
					 	  //Check changes before saving data.
					 	  Log.i("Netra", "myMarker label  + pos:lat lon" + myMarker.getmLabel() + "," + myMarker.getmLatitude() + "," + myMarker.getmLongitude()); 
					 	  
					 	  
					 //	  myMarker.setmIcon(new String("Red"));
					//	  myMarker.setmLabel(new String(sensorData.tid));
					//	  myMarker.setmLatitude(new Double(sensorData.lat));
					//	  myMarker.setmLatitude(new Double(sensorData.lon));
					//	  myMarker.setmLatitude(sensorData.lat);
					//	  myMarker.setRemark(new String(sensorData.msg));
						  Log.i("Netra", "After set: myMarker label  + pos:lat lon" + myMarker.getmLabel() + "," + myMarker.getmLatitude() + "," + myMarker.getmLongitude()); 
						 	  
						  
						  
						//  marker.setVisible(false);
						  Log.i("HomeFragment ",  " :Sensor Pos" + sensorData.lat + ","+ sensorData.lon);
						  Log.i("HomeFragment ",  " :myMarker Pos" + myMarker.getmLatitude() + myMarker.getmLongitude());
						  marker.setPosition(new LatLng(myMarker.getmLatitude(),myMarker.getmLongitude()));
						  marker.setTitle(myMarker.getRemark());
						  marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					//f	  Marker mMarker = googleMap.addMarker(mOptions);
						  
						 // Marker marker = getKeyFromValue(mMarkersHashMap, myMarker);
						  marker.setVisible(true);
						  Log.i("HomeFragment ",  " :marker" + marker.getPosition());
					//	  marker.remove();
					//	  mMarker.setVisible(true);
						  //mMarker.
					//	  mMarkersHashMap.remove(marker);
						  mMarkersHashMap.put(marker, myMarker); 
						  Log.i("HomeFragment ",  "hashmap :size" + mMarkersHashMap.size());
						  
						  Log.i("HomeFragment ",  "pos :" + pos);
						  mMyMarkersArray.remove(pos);
						  mMyMarkersArray.add(myMarker);   
						  Log.i("HomeFragment ",  "mMyMarkersArray :marker" + marker);
						  getpath(marker);
						  break;
					  }	  		
				  } 
				
				  pos = pos -1;
			  } 
		   } 
		} 
	   
	   
	 private static void getpath(Marker marker)
	 {
		 Tracking track = new Tracking(globalContext);
         LatLng latLng = GetMylocation(track);
         GetMapDirectionSrcDesc(latLng, new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
    	
	 } 
}
