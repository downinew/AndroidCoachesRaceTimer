package edu.rosehulman.coachesracetimer;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class AthleteCursorAdapter extends SimpleCursorAdapter {

	private Context context;
	private int layout;
	private Cursor c;
	private String[] from;
	private int[] to;
	private int flags;
	
	public AthleteCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.context=context;
		this.layout=layout;
		this.c=c;
		this.from=from;
		this.to=to;
		this.flags=flags;
	}
	
	@Override
	public Object getItem(int position){
		c.moveToPosition(position);
		return c.toString();
	}

}
