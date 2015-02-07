package edu.rosehulman.coachesracetimer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity implements OnClickListener {
	StopWatch stopWatch;
	Button startButton;
	Button stopButton;
	TextView timeView;
	TimerHandler timerHandler;
	ListView athleteList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		timeView = (TextView) findViewById(R.id.textView1);
		startButton = (Button) findViewById(R.id.StartButton);
		stopButton = (Button) findViewById(R.id.StopButton);
		startButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		stopWatch = new StopWatch();
		timerHandler = new TimerHandler(stopWatch, timeView);
		athleteList = (ListView) findViewById(R.id.athleteList);
		ArrayList<String> athleteArray = new ArrayList<String>();
		athleteArray.add("Test Athlete 1");
		athleteArray.add("Test Athlete 2");
		athleteArray.add("Test Athlete 3");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.athlete_view, R.id.athleteNameText, athleteArray);
		athleteList.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (startButton == v) {
			this.timerHandler.sendEmptyMessage(TimerHandler.TIMER_START);
		} else if (stopButton == v) {
			timerHandler.sendEmptyMessage(TimerHandler.TIMER_STOP);
		}
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
			return true;
		case R.id.dual_timer:
			Intent intent = new Intent(this, DualTimerActivity.class);
			startActivity(intent);
			return true;
		case R.id.convert_events:
			Intent intent2 = new Intent(this, ConvertActivity.class);
			startActivity(intent2);
			return true;
		case R.id.upload_results:
			// TODO: add this later
			return true;
		}
		return false;
	}
}
