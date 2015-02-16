package edu.rosehulman.coachesracetimer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class AthletesView extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_athletes_view);
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
			Intent intent3 = new Intent(this,ConvertActivity.class);
			startActivity(intent3);
			return true;
		case R.id.manage_athletes:
			return true;
		}
		return false;
	}
	
	
}
