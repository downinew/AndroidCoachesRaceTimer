package edu.rosehulman.coachesracetimer;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AthleteManagerCursorAdapter extends SimpleCursorAdapter {

	AthletesView context;
	Cursor c;

	public AthleteManagerCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.context = (AthletesView) context;
		this.c = c;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int fPosition = position;
		final View view = super.getView(position, convertView, parent);
		view.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				c.moveToFirst();
				TextView fName = (TextView) v.findViewById(R.id.firstName);
				TextView lName = (TextView) v.findViewById(R.id.lastName);
				int id = -1;
				if (c.getCount() > 0) {
					do {
						if (c.getString(
								c.getColumnIndex(AthleteDataAdapter.KEY_FIRST_NAME))
								.equals(fName.getText().toString())
								&& c.getString(
										c.getColumnIndex(AthleteDataAdapter.KEY_LAST_NAME))
										.equals(lName.getText().toString())) {
							id=c.getInt(c.getColumnIndex(AthleteDataAdapter.KEY_ID));
							break;
						}
					} while (c.moveToNext());
				}
				AthleteManagerCursorAdapter.this.context
						.removeAthlete(id);
				Log.d(MainActivity.CRT, "Called remove");
				return true;
			}
		});
		return view;

	}

}
