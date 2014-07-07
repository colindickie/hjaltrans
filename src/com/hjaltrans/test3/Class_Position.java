package com.hjaltrans.test3;

import com.hjaltrans.test3.Fragment_Origin.Constants;

import android.database.Cursor;
import android.util.Log;

public class Class_Position {

	private int rowid;
	private String Location;
	private int islandID;
	private double lat;
	private double lng;

	private int[] t = new int[18];
	private int[] d = new int[18];
	
	public Class_Position(Cursor myCursor){
		Log.d(Constants.LOG,"Class Position initiated");
		//boolean returnval = false;
		if (myCursor.moveToFirst()){
			rowid = myCursor.getInt(myCursor.getColumnIndex("_id"));
			//Log.d(Constants.LOG,"ClassPosition rowid");
			Location = myCursor.getString(myCursor.getColumnIndex("Location"));
			//Log.d(Constants.LOG,"ClassPosition Location");
			islandID = myCursor.getInt(myCursor.getColumnIndex("islandID"));
			//Log.d(Constants.LOG,"ClassPosition islandID");
			lat = myCursor.getDouble(myCursor.getColumnIndex("lat"));
			//Log.d(Constants.LOG,"ClassPosition lat");
			lng = myCursor.getDouble(myCursor.getColumnIndex("lng"));
			//Log.d(Constants.LOG,"ClassPosition lng");

			
			
			//create dummy entries for 0 index to make the remaining array index 1
			d[0] = 0;
			t[0] = 0;
			
			for (int i=1;i<18;i++){
				//Log.d(Constants.LOG,"d"+i+" being set to-");
				//Log.d(Constants.LOG,myCursor.getInt(myCursor.getColumnIndex("d"+i))+" being set to-"+" okay?");
				d[i] = myCursor.getInt(myCursor.getColumnIndex("d"+i));
				t[i] = myCursor.getInt(myCursor.getColumnIndex("t"+i))/60;
			}
			
			
			//returnval = true;
		} else {
			Log.d(Constants.LOG,"Cursor empty");
		}
		//return returnval;
	}
	
	public int getRow(){ return rowid;};
	public String getLocation(){ return Location;};
	public int getIsland(){ return islandID;};
	public double getLat(){ return lat;};
	public double getLng(){ return lng;};
	public int getD(int m){ return d[m];};
	public int getT(int m){ return t[m];};

	
}
