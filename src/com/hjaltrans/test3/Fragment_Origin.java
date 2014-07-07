package com.hjaltrans.test3;



import java.util.List;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Origin extends Fragment{

	public interface Constants {
		  String LOG = "com.hjaltrans.test3";
	};
	
	public interface onIslandChangedListener {
		public void onIslandChanged(int island);
	}
	public interface onNodeChangedListener {
		public void onNodeChanged(Class_Position position);
	}
	
	private LocationManager locationManager;
	private final Criteria criteria = new Criteria();
	private static int minUpdateTime = 0; //milliseconds
	private static int minUpdateDistance = 0; //metres
	private static final String TAG = "DYNAMIC_LOCATION_PROVIDER";
	private int island = 1;
	private String[] islands = {"Unst", "Yell", "Fetlar", "Mainland", "Skerries", "Whalsaa", "PapaStour", "Bressa", "Foula", "Fair Isle"};
	private onIslandChangedListener onislandchangedListener;
	private onNodeChangedListener onnodeChangedListener;
	
	//Called when the Fragment is attached to its parent Activity
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	    try {
	    	onislandchangedListener = (onIslandChangedListener) activity;
	    	} catch (ClassCastException e) {
	    		throw new ClassCastException(activity.toString() + " must implement onIslandChangedListener");
	    	}
	    try {
	    	onnodeChangedListener = (onNodeChangedListener) activity;
	    	} catch (ClassCastException e) {
	    		throw new ClassCastException(activity.toString() + " must implement onNodeChangedListener");
	    	}
	}
	
	//Called to do the initial creation of the Fragment
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
        //Initialise Location services
        String svcName =Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getActivity().getSystemService(svcName);
        
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_LOW);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_LOW);
        criteria.setBearingAccuracy(Criteria.ACCURACY_LOW);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_LOW);
		
	}
	
	//Called once the Fragment has been created in order for it to create its user interface
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_origin, container);
		return view;
	}
	
	//Called when the parent Activity and the Fragments UI have been created
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
	}
	
	//Called at the start of the visible lifetime
	@Override
	public void onStart(){
		super.onStart();
	}
	
	//Called at the start of the active lifetime
	@Override
	public void onResume(){
		super.onResume();
		registerListener();
	}
	
	//Called at the end of the active lifetime
	@Override
	public void onPause(){
		unregisterAllListeners();
		super.onPause();
	}
	
	//Called to save UI state changes at the end of the active lifecycle
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
	}
	
	//Called at the end of the visible lifetime
	@Override
	public void onStop(){
		super.onStop();
	}
	
	//Called when the Fragment's View has been detached
	@Override
	public void onDestroyView(){
		super.onDestroyView();
	}
	
	//Called at the end of the full lifetime
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	//Called when the Fragment has been detached from its parent Activity
	@Override
	public void onDetach(){
		super.onDetach();
	}

	private void registerListener(){
		//Log.d(Constants.LOG,"registerListener Started");
		unregisterAllListeners();
		//String bestProvider = locationManager.getBestProvider(criteria, false);
		String bestProvider = LocationManager.GPS_PROVIDER;
		
		String bestAvailableProvider = locationManager.getBestProvider(criteria, true);
	
		//Log.d(TAG,bestProvider + "/" + bestAvailableProvider);
	
		if (bestProvider == null)
			Log.d(TAG,"No Location Providers exist on device.");
		else if (bestProvider.equals(bestAvailableProvider))
			locationManager.requestLocationUpdates(bestAvailableProvider,minUpdateTime, minUpdateDistance, bestAvailableProviderListener);
		else {
			locationManager.requestLocationUpdates(bestProvider,minUpdateTime,minUpdateDistance, bestProviderListener);
		
			if (bestAvailableProvider != null)
				locationManager.requestLocationUpdates(bestAvailableProvider, minUpdateTime,minUpdateDistance,bestAvailableProviderListener);
				else {
					List<String> allProviders = locationManager.getAllProviders();
					for (String provider : allProviders)
						locationManager.requestLocationUpdates(provider, 0, 0, bestProviderListener);
					Log.d(TAG,"No Location Providers currently available");
				}
		}
	}
	
	private void unregisterAllListeners(){
    	locationManager.removeUpdates(bestProviderListener);
    	locationManager.removeUpdates(bestAvailableProviderListener);
    };
    
    private LocationListener bestProviderListener = new LocationListener() {

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			reactToLocationChanged(location);
		}
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			registerListener();
		}
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
    };
    
    private LocationListener bestAvailableProviderListener = new LocationListener() {
    	public void onLocationChanged(Location location){
    		reactToLocationChanged(location);
    	};
    	public void onProviderDisabled(String provider) {
    	};
    	public void onProviderEnabled(String provider) {
    		registerListener();
    	};
    	public void onStatusChanged(String provider, int Status, Bundle extras){
    	}
    };
    
    public void reactToLocationChanged(Location location){
		
		//location changed
    	TextView originView1 = (TextView)getActivity().findViewById(R.id.textView1);
    	TextView originView2 = (TextView)getActivity().findViewById(R.id.textView2);
    	TextView originView3 = (TextView)getActivity().findViewById(R.id.textView3);
    	TextView originView4 = (TextView)getActivity().findViewById(R.id.textView4);
		
        
        if (location != null){
        	double lat = location.getLatitude();
        	double lng = location.getLongitude();
        	
        	//lat = 60.757945; lng = -0.865954; // Unst
        	//lat = 60.704761; lng = -1.017586; // Yell
        	//lat = 60.594860; lng = -0.890500; // Fetlar
        	//lat = 60.511130; lng = -1.389690; // Northmavine
        	//lat = 60.324864; lng = -1.695738; // Papa Stour
        	//lat = 60.369334; lng = -1.297286; // Delting
        	//lat = 60.336093; lng = -1.014954; // Whalsay
        	//lat = 60.422321; lng = -0.765965; // Skerries
        	//lat = 60.248772; lng = -1.347001; // Central Mainland
        	//lat = 60.263827; lng = -1.114745; // East Nesting
        	//lat = 60.173452; lng = -1.109265; // North Bressay
        	//lat = 60.134593; lng = -2.075649; // Foula
        	//lat = 59.996424; lng = -1.228512; // South Mainland
        	//lat = 60.131705; lng = -1.096005; // South Bressay
        	//lat = 59.532121; lng = -1.627776; // Fair Isle
        	
        	int newlocation = findisland(lat,lng);
        	if (newlocation!=island){
        		//changed island
        		island = newlocation;
        		switch (island){
    				case 11:
    					//speak("Doos now in delting");
    					break;
    				case 13:
    					//speak("doos noo in da central belt oo Shitlind");
    					break;
    				case 14:
    					//speak("Doos now in da Sooth end");
    					break;
        			case 15:
        				//speak("Doos noo in lerook");
        				break;
        			default:
        				//speak("Doos noo in " + islands[island-1]);
        				break;
        		}
        		//speak("Dus noo in " + island);
        		
        		//perform callback to activity
        		onislandchangedListener.onIslandChanged(island);
        		

        		
        		
        		Toast.makeText(getActivity(), "Island Changed", Toast.LENGTH_LONG ).show();
        	}

        	Class_Position position  = findNearestNode(lat,lng,island);

        	onnodeChangedListener.onNodeChanged(position);
        	
        	Log.d(Constants.LOG,"latitude="+lat);
        	originView1.setText(islands[island-1]);
        	originView2.setText("Lat: "+round(lat) );        	
        	originView3.setText("Node:"+position.getLocation() );
        	originView4.setText("Lng: "+round(lng) );
        } else {
        	originView1.setText("Cannot Find Location");
        	originView2.setText("");
        	originView3.setText("");
        	originView4.setText("");
        }
	}
    
    private double round(double num){
    	return (double)Math.round(num*1000000)/1000000;
    }
    
    public Class_Position findNearestNode(double lat, double lng, int island){
		String DB = getActivity().getFilesDir().getParent() + "/databases/hjaltrans.db";
		SQLiteDatabase mSampleDb =  SQLiteDatabase.openOrCreateDatabase(DB, null);
		
		double abslng = (lng < 0) ? -lng : lng;
		String query = "SELECT _id, Location, MIN((lat-"+lat+")*(lat-"+lat+")+(lng+"+abslng+")*(lng+"+abslng+")) as distance, lat, lng, islandID, d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17 FROM Locations WHERE islandID=" + island + " AND locationTypeID=2;";
		//Log.d(Constants.LOG,"query = "+query);
		Cursor mCursor = mSampleDb.rawQuery(query, null);  
		
		//Log.d(Constants.LOG,"Cursor has "+mCursor.getCount()+" records");

		Class_Position cp = new Class_Position(mCursor);
		//cp.setValues(mCursor);
        return cp;
    }
    
    
    public int findisland(double lat, double lng){
		  int island = -1;
	
		  if (lat > 60.311542){
			  //North Shetland
			  if (lat > 60.4811345){
				  //North Isles and Northmavine
				  if (lng > -1.2204281){
					  //North Isles
					  if (lng < -0.9818885){
						  //Yell
						  island = 2;
					  }else{
						  //Unst and Fetlar
						  if (lat > 60.646317){
							  //Unst
							  island = 1;
						  }else{
							  //Fetlar
							  island = 3;
						  }
					  }
				  }else{
					  //Northmavine
					  island = 4;
				  }
			  }else{
				  //Delting
				  if (lng > -1.0343439){
					  //Whalsay and Skerries
					  if (lng > -0.847253){
						  //Skerries
						  island = 5;
					  }else{
						  //Whalsay
						  island = 6;
					  }
				  }else{
					  //Delting and Papa Stour
					  if (lng < -1.6449905){
						  //Papa Stour
						  island = 7;
					  }else{
						  //Delting
						  island =4;
					  }
				  }
			  }
		  }else{
			  //Central and South Shetland
			  if (lat > 60.160016){
				  //Central Shetland
				  if (lng > -1.1460795){
					  //North Bressay and East Nesting
					  if (lat > 60.2204475){
						  //East Nesting
						  island = 4;
					  }else{
						  //North Bressay
						  island = 8;
					  }
				  }else{
					  //Central Mainland
					  if (lng > -1.196301) {
						  //Lerwick North
						  island = 4;
					  }else{
						  //Walls etc
						  island = 4;
					  }
				  }
			  }else{
				  //South Shetland
				  if (lat > 59.7032755){
					  //South Mainland, South Bressay and Foula
					  if (lng > -1.12865){
						  //South Bressay
						  island = 8;
					  }else{
						  //South Mainland and Foula
						  if (lng < -1.76485){
							  //Foula
							  island = 9;
						  }else{
							  //South Mainland
							  island = 4;
						  }
					  }
				  }else{
					  //Fair Isle
					  island = 10;
				  }
			  }
		  }
	
		  return island;
	}
};

