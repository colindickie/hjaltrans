package com.hjaltrans.test3;


import android.app.Activity;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;


public class Activity_TimeTable extends Activity implements Fragment_Origin.onIslandChangedListener, Fragment_Origin.onNodeChangedListener{
	
	public interface Constants {
		  String LOG = "com.hjaltrans.test3";
	};
	//private int timetoferry;
	//private String ferry;
	private int ferryid;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created.
        setContentView(R.layout.activity_timetable);
        
        //String ferry = getIntent().getExtras().getString("ferry");
        ferryid = getIntent().getExtras().getInt("ferryid");
        int timetoferry = getIntent().getExtras().getInt("timetoferry");
        
               
        
        reQuery(timetoferry);
        
        
    }
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        
		
        
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
	@Override
	public void onIslandChanged(int island) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNodeChanged(Class_Position cp) {
		// TODO Auto-generated method stub

	
		int timetoferry;
		//if (ferry=="Ulsta"){timetoferry = d4;}
		//Log.d(Constants.LOG,"oNC timetoferry:"+timetoferry);

		timetoferry = cp.getT(ferryid);
		
		reQuery(timetoferry);
	}
	
	private void reQuery(int timetoferry){
		FragmentManager fm = this.getFragmentManager();
        //TextView header = (TextView)this.findViewById(R.id.textView1);
        //header.setText(ferry);
        
        Time now = new Time();
        now.setToNow();
        int minutes = now.hour*60 + now.minute;
        int totaltime = minutes + timetoferry;
        
        Log.d(Constants.LOG,"minutes: " + minutes);
        Log.d(Constants.LOG,"timetoferry: " + timetoferry);
        Log.d(Constants.LOG,"timetotal: " + totaltime);
		String DB = getFilesDir().getParent() + "/databases/hjaltrans.db";
		SQLiteDatabase mSampleDb =  SQLiteDatabase.openOrCreateDatabase(DB, null);          
		Cursor mCursor = mSampleDb.rawQuery("SELECT Timetable._id as _id ,Depart, Locations.Location as Destination, CASE(Booking) WHEN 0 THEN '' WHEN 1 THEN 'Bookings Only' END as Booking, cast(minutesweek-"+totaltime+" as TEXT)||' mins' as togo FROM Timetable INNER JOIN Locations ON Timetable.Destination = Locations._id WHERE origin="+ferryid+" AND minutesweek >" +minutes+" limit 10", null);  
        
		
		String[] dataColumns = { "Depart" , "Destination", "togo", "Booking" };
        int[] viewIDs = {R.id.tt1, R.id.tt2, R.id.tt3, R.id.tt4}; 
        Log.d(Constants.LOG,"FD cursor rows:" + mCursor.getCount());
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.fragment_timetable, mCursor, dataColumns, viewIDs, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        
        //FragmentManager fm  = getFragmentManager();
        Fragment_TimeTable fram = (Fragment_TimeTable) fm.findFragmentById(R.id.fragment2);
        fram.setListAdapter(mAdapter);
 
	}
}
