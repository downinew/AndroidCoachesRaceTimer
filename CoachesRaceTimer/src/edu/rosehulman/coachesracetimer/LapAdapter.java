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
	private ArrayList<String> mTimes;
	private boolean didStop;

	public LapAdapter(Context context) {
		mContext = context;
		mNumRows = 0;
		mTimes = new ArrayList<String>();
		didStop = false;
	}

	public void notifyStopped() {
		didStop = true;
	}

	public void notifyStarted() {
		didStop = false;
	}

	@Override
	public int getCount() {
		return mNumRows;
	}

	public void addView(String time) {
		mTimes.add(time);
		mNumRows++;
	}

	public void reset() {
		mTimes = new ArrayList<String>();
		mNumRows = 0;
	}

	@Override
	public Object getItem(int position) {
		// Do nothing
		return null;
	}

	@Override
	public long getItemId(int position) {
		// Do nothing
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if (convertView == null) {
			view = new TextView(this.mContext);
		} else {
			view = (TextView) convertView;
		}
		if (position == mTimes.size() - 1 && didStop) {
			view.setText("Total: " + getTime(position));
		} else {
			view.setText("Lap " + (position + 1) + ": " + getTime(position));
		}
		return view;
	}

	private String getTime(int index) {
		return mTimes.get(index);
	}

}
