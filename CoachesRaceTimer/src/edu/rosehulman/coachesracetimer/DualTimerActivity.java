package edu.rosehulman.coachesracetimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class DualTimerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dual_timer);
		ListView listView1 = (ListView) findViewById(R.id.listView1);
		ListView listView2 = (ListView) findViewById(R.id.listView2);
		Button lapButton1 = (Button) findViewById(R.id.start1);
		Button lapButton2 = (Button) findViewById(R.id.start2);
		Button stopButton1 = (Button) findViewById(R.id.stop1);
		Button stopButton2 = (Button) findViewById(R.id.stop2);
		final LapAdapter lapAdapter1 = new LapAdapter(this);
		final LapAdapter lapAdapter2 = new LapAdapter(this);
		listView1.setAdapter(lapAdapter1);
		listView2.setAdapter(lapAdapter2);
		lapButton1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lapAdapter1.addView();
				lapAdapter1.notifyDataSetChanged();
			}
		});
		lapButton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lapAdapter2.addView();
				lapAdapter2.notifyDataSetChanged();
			}
		});
		stopButton1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lapAdapter1.reset();
				lapAdapter1.notifyDataSetChanged();
			}
		});
		stopButton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lapAdapter2.reset();
				lapAdapter2.notifyDataSetChanged();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		int id=item.getItemId();
		switch(id){
		case R.id.race_timer:
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			return true;
		case R.id.dual_timer:
			return true;
		case R.id.convert_events:
			Intent intent2 = new Intent(this,ConvertActivity.class);
			startActivity(intent2);
			return true;
		case R.id.upload_results:
			//TODO: add this later
			return true;
		}
		return false;
	}
}
