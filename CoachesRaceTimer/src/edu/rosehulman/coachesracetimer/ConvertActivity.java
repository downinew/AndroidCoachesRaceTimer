package edu.rosehulman.coachesracetimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ConvertActivity extends Activity {
	
	ListView event1;
	ListView event2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_convert);
		event1=(ListView) findViewById(R.id.listView1);
		event2=(ListView) findViewById(R.id.listView2);
		String[] events = getResources().getStringArray(R.array.events);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,events);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,events);
		event1.setAdapter(adapter1);
		event2.setAdapter(adapter2);
	}
}
