package com.hjaltrans.test3;



import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Fragment_DestList extends ListFragment  {
	public interface Constants {
		  String LOG = "com.hjaltrans.test3";
	};
	
	public interface onFerrySelectedListener {
		public void onFerrySelected(int clickedposition);
	}
	
	private onFerrySelectedListener onferrySelectedListener;
	
	//Called when the Fragment is attached to its parent Activity
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	    try {
	    	onferrySelectedListener = (onFerrySelectedListener) activity;
	    	} catch (ClassCastException e) {
	    		throw new ClassCastException(activity.toString() + " must implement onFerrySelectedListener");
	    	}
	}
	
	
	
	
	//Called to do the initial creation of the Fragment
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	
	//Called once the Fragment has been created in order for it to create its user interface
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(android.R.layout.list_content, container);

		
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
	}
	
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        onferrySelectedListener.onFerrySelected(pos);
    }
	
	
	//Called at the end of the active lifetime
	@Override
	public void onPause(){
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
}
