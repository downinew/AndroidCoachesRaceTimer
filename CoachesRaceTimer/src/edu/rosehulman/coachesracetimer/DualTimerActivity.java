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
import android.widget.TextView;

public class DualTimerActivity extends Activity {

	static boolean didStart1;
	static boolean didStart2;
	static StopWatch watch1;
	static StopWatch watch2;
	static TimerHandler handler1;
	static TimerHandler handler2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dual_timer);
		ListView listView1 = (ListView) findViewById(R.id.listView1);
		ListView listView2 = (ListView) findViewById(R.id.listView2);
		final TextView textView1 = (TextView) findViewById(R.id.event1);
		final TextView textView2 = (TextView) findViewById(R.id.event2);
		Button lapButton1 = (Button) findViewById(R.id.start1);
		Button lapButton2 = (Button) findViewById(R.id.start2);
		Button stopButton1 = (Button) findViewById(R.id.stop1);
		Button stopButton2 = (Button) findViewById(R.id.stop2);
		final LapAdapter lapAdapter1 = new LapAdapter(this);
		final LapAdapter lapAdapter2 = new LapAdapter(this);
		watch1 = new StopWatch();
		watch2 = new StopWatch();
		handler1 = new TimerHandler(watch1, textView1);
		handler2 = new TimerHandler(watch2, textView2);
		didStart1 = false;
		didStart2 = false;
		listView1.setAdapter(lapAdapter1);
		listView2.setAdapter(lapAdapter2);
		lapButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (didStart1) {
					lapAdapter1.addView(watch1.getLap());
					lapAdapter1.notifyDataSetChanged();
				} else {
					handler1.sendEmptyMessage(TimerHandler.TIMER_START);
					watch1.start();
					lapAdapter1.notifyStarted();
					didStart1 = true;
				}
			}
		});
		lapButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (didStart2) {
					lapAdapter2.addView(watch2.getLap());
					lapAdapter2.notifyDataSetChanged();
				} else {
					handler2.sendEmptyMessage(TimerHandler.TIMER_START);
					watch2.start();
					lapAdapter2.notifyStarted();
					didStart2 = true;
				}
			}
		});
		stopButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (didStart1) {
					handler1.sendEmptyMessage(TimerHandler.TIMER_STOP);
					didStart1 = false;
					lapAdapter1.addView(watch1.getLap());
					lapAdapter1.addView(watch1.getTime());
					lapAdapter1.notifyStopped();
					lapAdapter1.notifyDataSetChanged();
				} else {
					watch1 = new StopWatch();
					lapAdapter1.reset();
					lapAdapter1.notifyDataSetChanged();
					textView1.setText(getString(R.string.default_time));
				}
			}
		});
		stopButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (didStart2) {
					handler2.sendEmptyMessage(TimerHandler.TIMER_STOP);
					didStart2 = false;
					lapAdapter2.addView(watch1.getLap());
					lapAdapter2.addView(watch2.getTime());
					lapAdapter2.notifyStopped();
					lapAdapter2.notifyDataSetChanged();
				} else {
					watch2 = new StopWatch();
					lapAdapter2.reset();
					lapAdapter2.notifyDataSetChanged();
					textView2.setText(getString(R.string.default_time));
				}
			}
		});
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
			return true;
		case R.id.convert_events:
			Intent intent2 = new Intent(this, ConvertActivity.class);
			startActivity(intent2);
			return true;
		case R.id.manage_athletes:
			Intent intent3 = new Intent(this,AthletesView.class);
			startActivity(intent3);
			return true;
		}
		return false;
	}
}
