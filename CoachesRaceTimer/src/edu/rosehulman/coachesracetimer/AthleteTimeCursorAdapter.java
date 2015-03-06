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

	public AthleteTimeCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.context = (MainActivity) context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final View view = super.getView(position, convertView, parent);
		((Button) view.findViewById(R.id.SecondaryStopButton))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.d(MainActivity.CRT, "Stop");
						AthleteTimeCursorAdapter.this.context.stop(view,
								getItemId(position));
					}

				});
		((Button) view.findViewById(R.id.lapButton))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						long id = getItemId(position);
						Log.d(MainActivity.CRT, "id: " + id);
						AthleteTimeCursorAdapter.this.context.lap(view,
								getItemId(position));
					}
				});
		return view;
	}

}
