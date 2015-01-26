package edu.rosehulman.coachesracetimer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LapAdapter extends BaseAdapter {

	private Context mContext;
	private int mNumRows;
	
	public LapAdapter(Context context){
		mContext=context;
		mNumRows=0;
	}
	
	@Override
	public int getCount() {
		return mNumRows;
	}
	
	public void addView(){
		mNumRows++;
	}
	
	public void reset(){
		mNumRows=0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if(convertView==null){
			view = new TextView(this.mContext);
		}else{
			view=(TextView) convertView;
		}
		view.setText("Lap "+position+": "+getTime());
		return view;
	}
	
	private String getTime(){
		return "";
	}

}
