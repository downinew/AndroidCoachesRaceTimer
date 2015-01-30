package edu.rosehulman.coachesracetimer;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LapAdapter extends BaseAdapter {

	private Context mContext;
	private int mNumRows;
	private ArrayList<Long> mTimes;
	
	public LapAdapter(Context context){
		mContext=context;
		mNumRows=0;
		mTimes = new ArrayList<Long>();
	}
	
	@Override
	public int getCount() {
		return mNumRows;
	}
	
	public void addView(long time){
		mTimes.add(time);
		mNumRows++;
	}
	
	public void reset(){
		mTimes = new ArrayList<Long>();
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
		view.setText("Lap "+position+": "+getTime(position));
		return view;
	}
	
	private long getTime(int index){
		return mTimes.get(index);
	}

}
