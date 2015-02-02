package edu.rosehulman.coachesracetimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
		event1 = (ListView) findViewById(R.id.listView1);
		event2 = (ListView) findViewById(R.id.listView2);
		String[] events = getResources().getStringArray(R.array.events);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, events);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, events);
		event1.setAdapter(adapter1);
		event2.setAdapter(adapter2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		int id = item.getItemId();
		switch (id) {
		case R.id.race_timer:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		case R.id.dual_timer:
			Intent intent2 = new Intent(this, DualTimerActivity.class);
			startActivity(intent2);
			return true;
		case R.id.convert_events:
			return true;
		case R.id.upload_results:
			// TODO: add this later
			return true;
		}
		return false;
	}
}
