package com.hjaltrans.test3;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Activity_Main extends Activity implements Fragment_Origin.onIslandChangedListener, Fragment_Origin.onNodeChangedListener, Fragment_DestList.onFerrySelectedListener{
	
	
	public interface Constants {
		  String LOG = "com.hjaltrans.test3";
	};
	
	public static ArrayList<Class_RowItem> rowItems;
	
	public void onIslandChanged(int island){
		Log.d(Constants.LOG,"onIslandChangedListener Callback made");
		
		String DB = getFilesDir().getParent() + "/databases/hjaltrans.db";
		SQLiteDatabase mSampleDb =  SQLiteDatabase.openOrCreateDatabase(DB, null);          
		Cursor mCursor = mSampleDb.rawQuery("SELECT _id ,Location FROM Locations WHERE islandID=" + island + " AND LocationTypeID=1", null);  
        String[] dataColumns = { "_id" , "Location" };
        int[] viewIDs = {R.id.tv1, R.id.tv2}; 
        Log.d(Constants.LOG,"FD cursor rows:" + mCursor.getCount());
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.fragment_destlist, mCursor, dataColumns, viewIDs, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        
        FragmentManager fm  = getFragmentManager();
        Fragment_DestList fram = (Fragment_DestList) fm.findFragmentById(R.id.fragment_destlist);
        fram.setListAdapter(mAdapter);
	};
	
	//public void onFerrySelected(String ferry, int timetoferry){
	public void onFerrySelected(int clickedposition){
		String ferry = rowItems.get(clickedposition).getPort();
		int ferryid = rowItems.get(clickedposition).getPortID();
		int timetoferry = rowItems.get(clickedposition).getTime();
		
		Toast.makeText(this, "CLICKED ON :" + ferry, Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent(this, Activity_TimeTable.class);
        intent.putExtra("ferry", ferry);
        intent.putExtra("ferryid", ferryid);
        intent.putExtra("timetoferry", timetoferry);
    	//Log.d(Constants.LOG, "Intent created");
        startActivity(intent);
	}
	
	public String dbName = "hjaltrans.db";

	private Context myContext;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	Log.d(Constants.LOG,"onCreate Activity_Main");
	        //Launch activity_main layout
	    	super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        myContext = this;
	        String path = myContext.getFilesDir().getParent() + "databases";
	        Log.d(Constants.LOG,"onCreate path:" + path);
	        

	        try {
				copyDataBase(path,dbName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        
	        
	 }
	
	 private void copyDataBase(String path, String dbname) throws IOException {
	        // Open your local db as the input stream
	    	Log.d(Constants.LOG,"copyDB one");
	        InputStream myInput = myContext.getAssets().open(dbname);
	        Log.d(Constants.LOG,"copyDB two");
	        // Path to the just created empty db
	        //String outFileName = "/data/data/com.zettrans.database/databases/" + dbname;
	        String outFileName = myContext.getFilesDir().getParent() + "/databases/" + dbname;
	        
	        Log.d(Constants.LOG,"copyDB three");
	        // Open the empty db as the output stream
	        //File f = new File("/data/data/com.zettrans.database/databases", dbname);
	        //File f = new File(myContext.getFilesDir().getParent() + "/databases", dbname);
	        //f.delete();
	        //f.mkdirs();
	        
	        Log.d(Constants.LOG,"copyDB four");
	        try {
	        	OutputStream myOutput = new FileOutputStream(outFileName);
	        	        Log.d(Constants.LOG,"copyDB five");
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = myInput.read(buffer)) > 0) {
	            myOutput.write(buffer, 0, length);
	        }
	        // Close the streams
	        myOutput.flush();
	        myOutput.close();
	        myInput.close();
	        	
	        	
	        }catch(Exception e){
	        	Log.d(Constants.LOG,"copyDB "+ e.getMessage());
	        }
	        
	        // transfer bytes from the inputfile to the outputfile
	        

	    }

	@Override
	public void onNodeChanged(Class_Position cp) {
		// TODO Auto-generated method stub
		int islandid = cp.getIsland();
		//int d1 = cp.getD1();
		//int d2 = cp.getD2();
		//int d3 = cp.getD3();
		//int d4 = cp.getD4();
		//int d5 = cp.getD5();
		
		
		//ArrayList<String> ports = new ArrayList<String>();
		//ArrayList<Integer> times = new ArrayList<Integer>();
		//Log.d(Constants.LOG,"oNC one");
		
		
		rowItems = new ArrayList<Class_RowItem>();
		
		switch (islandid){
			case 1:
				//Unst
	        	rowItems.add(new Class_RowItem("Belmont",1, cp.getT(1)));
				break;
			case 2:
				//Yell
				rowItems.add(new Class_RowItem("Gutcher",2, cp.getT(2)));
				rowItems.add(new Class_RowItem("Ulsta",4, cp.getT(4)));
				break;
			case 3:
				//Fetlar
				rowItems.add(new Class_RowItem("Hammars Ness",3,cp.getT(3)));
				break;
			case 4:
				//Mainland
				rowItems.add(new Class_RowItem("Toft",5, cp.getT(5)));
				rowItems.add(new Class_RowItem("Vidlin",6, cp.getT(6)));
				rowItems.add(new Class_RowItem("Laxo", 7,cp.getT(7)));
				rowItems.add(new Class_RowItem("Lerwick",10, cp.getT(10)));
				rowItems.add(new Class_RowItem("West Burrafirth",12, cp.getT(12)));
				rowItems.add(new Class_RowItem("Walls",15, cp.getT(15)));
				rowItems.add(new Class_RowItem("Grutness",16, cp.getT(16)));
				break;
			case 5:
				//Skerries
				//rowItems.add(new RowItem("Skerries", d8));
				break;
			case 6:
				//Whalsay
				//rowItems.add(new RowItem("Symbister", d8));
				break;
			case 7:
				//Papa Stour
				//rowItems.add(new RowItem("Papa Stour", d13));
				break;
			case 8:
				//Bressay
				//rowItems.add(new RowItem("Bressay", d11));
				break;
			case 9:
				//Foula
				//rowItems.add(new RowItem("Foula", d14));
				break;
			case 10:
				//Fair Isle
				//rowItems.add(new RowItem("Fair Isle", d17));
				break;
			default:
				break;
		}
		
		
       
        
		Class_BaseAdapter adapter = new Class_BaseAdapter(this, rowItems);
		//Log.d(Constants.LOG,"oNC six");
		
		//Log.d(Constants.LOG,"oNC seven"+ adapter.getCount());
		
		FragmentManager fm  = getFragmentManager();
        Fragment_DestList fram = (Fragment_DestList) fm.findFragmentById(R.id.fragment_destlist);
        
		fram.setListAdapter(adapter);
    

	}
}
