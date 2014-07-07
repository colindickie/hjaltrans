package com.hjaltrans.test3;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class Class_BaseAdapter extends BaseAdapter {
	Context mycontext;
    ArrayList<Class_RowItem> myrowItems;
    
    public Class_BaseAdapter(Context context, ArrayList<Class_RowItem> rowItems) {
        mycontext = context;
        myrowItems = rowItems;
    }
    
    /*private view holder class*/
    private class ViewHolder {
        //TextView txtPort;
        //TextView txtTime;
    	View_timetable txtPort;
    	View_timetable txtTime;
    }
    
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myrowItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		 return myrowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return myrowItems.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.d(Constants.LOG,"gV started: " + mycontext.toString());
		ViewHolder holder = null;
		//Log.d(Constants.LOG,"gV one");

		LayoutInflater mInflater = (LayoutInflater) mycontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //Log.d(Constants.LOG,"gV two: "+mInflater.toString());
        if (convertView == null) {
        	//Log.d(Constants.LOG,"gV three");
            convertView = mInflater.inflate(R.layout.fragment_destlist, null);
            
            //Log.d(Constants.LOG,"gV four "+convertView.toString());
            holder = new ViewHolder();

            //holder.txtPort = (TextView) convertView.findViewById(R.id.tv1);            
            //holder.txtTime = (TextView) convertView.findViewById(R.id.tv2);
            holder.txtPort = (View_timetable) convertView.findViewById(R.id.tv1); 
            holder.txtTime = (View_timetable) convertView.findViewById(R.id.tv2);

            //Log.d(Constants.LOG,"gV six");
            convertView.setTag(holder);
        }
        else {
        	//Log.d(Constants.LOG,"gV seven");
            holder = (ViewHolder) convertView.getTag();
        }
         
        //Log.d(Constants.LOG,"gV eight");
        Class_RowItem rowItem = (Class_RowItem) getItem(position);
        
        //Log.d(Constants.LOG,"gV nine");
        //int t = rowItem.getTime();
        //Log.d(Constants.LOG,"gV nine.2");
        //String p = rowItem.getPort();
        //Log.d(Constants.LOG,"gV nine.4");
        //holder.txtTime.setText(rowItem.getTime());
        //holder.txtPort.setText(rowItem.getPort());
        holder.txtPort.setText(rowItem.getPort());        
        holder.txtTime.setText(rowItem.getTime()+" minutes away");
        //Log.d(Constants.LOG,"gV nine.6");

        
        //Log.d(Constants.LOG,"gV ten");
         
        return convertView;
	}

}
