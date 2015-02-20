package edu.rosehulman.coachesracetimer;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

public class AthleteTimeCursorAdapter extends SimpleCursorAdapter {
	
	MainActivity context;
//	boolean enableLap;
//	boolean enableStop;

	public AthleteTimeCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.context = (MainActivity)context;
//		enableLap=false;
//		enableStop=false;
	}
	
//	public void setLapEnabled(boolean enabled){
////		enableLap=enabled;
//	}
	
//	public void setStopEnabled(boolean enabled){
////		enableStop=enabled;
//	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final View view =super.getView(position, convertView, parent);
		((Button)view.findViewById(R.id.SecondaryStopButton)).setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.d(MainActivity.CRT,"Stop");
				AthleteTimeCursorAdapter.this.context.stop(view);
			}
			
		});
		((Button)view.findViewById(R.id.lapButton)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(MainActivity.CRT,"Lap");
				AthleteTimeCursorAdapter.this.context.lap(view);
			}
		});
//		((Button)view.findViewById(R.id.SecondaryStopButton)).setEnabled(enableStop);
//		((Button)view.findViewById(R.id.lapButton)).setEnabled(enableLap);
		return view;
	}

}
