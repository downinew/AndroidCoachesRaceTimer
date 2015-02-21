package edu.rosehulman.coachesracetimer;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class AthleteManagerCursorAdapter extends SimpleCursorAdapter {
	
	AthletesView context;

	public AthleteManagerCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.context=(AthletesView)context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int fPosition = position;
		final View view = super.getView(position, convertView, parent);
		view.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				AthleteManagerCursorAdapter.this.context.removeAthlete(fPosition);
				Log.d(MainActivity.CRT,"Called remove");
				return true;
			}
		});
		return view;
		
	}

}
