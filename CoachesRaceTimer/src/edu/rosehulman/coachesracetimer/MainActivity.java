package edu.rosehulman.coachesracetimer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

public class MainActivity extends Activity implements OnClickListener {
	public static final String CRT = "CRT";
	static final int REQUEST_CODE_NEW_ATHLETE = 1;
	static final int REQUEST_CODE_SAVE_FILE = 2;
	public static final String KEY_FIRST_NAME_STRING = "KEY_FIRST_NAME_STRING";
	public static final String KEY_LAST_NAME_STRING = "KEY_LAST_NAME_STRING";
	public static final String KEY_MAIN_EVENT_STRING = "KEY_MAIN_EVENT_STRING";
	public static final String KEY_PR_STRING = "KEY_PR_STRING";
	private AthleteDataAdapter mAthleteDataAdapter;
	private AthleteTimeCursorAdapter mCursorAdapter;

	FileWriter writer;
	StopWatch stopWatch;
	Button startButton;
	Button stopButton;
	Button addAthleteButton;
	Button saveRaceButton;
	TextView timeView;
	TimerHandler timerHandler;
	ListView athleteList;
	Map<String, List<String>> lapMap;

	// ArrayList<String> athleteArray;
	// ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lapMap = new HashMap<String, List<String>>();
		timeView = (TextView) findViewById(R.id.textView1);
		startButton = (Button) findViewById(R.id.StartButton);
		stopButton = (Button) findViewById(R.id.StopButton);
		addAthleteButton = (Button) findViewById(R.id.addAthButton);
		saveRaceButton = (Button) findViewById(R.id.saveButton);
		startButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		addAthleteButton.setOnClickListener(this);
		saveRaceButton.setOnClickListener(this);
		stopWatch = new StopWatch();
		timerHandler = new TimerHandler(stopWatch, timeView);
		athleteList = (ListView) findViewById(R.id.athleteList);
		mAthleteDataAdapter = new AthleteDataAdapter(this);
		mAthleteDataAdapter.open();

		Cursor cursor = mAthleteDataAdapter.getAthletesCursor();

		String[] fromColumns = new String[] {
				AthleteDataAdapter.KEY_FIRST_NAME,
				AthleteDataAdapter.KEY_LAST_NAME };
		int[] toTextViews = new int[] { R.id.athleteFirstNameText,
				R.id.athleteLastNameText };
		mCursorAdapter = new AthleteTimeCursorAdapter(this,
				R.layout.athlete_view, cursor, fromColumns, toTextViews, 0);
		athleteList.setAdapter(mCursorAdapter);
		mCursorAdapter.getCursor().moveToFirst();
		if(mCursorAdapter.getCursor().getCount()>0){
		do {
//			try{
			Cursor currentCursor = mCursorAdapter.getCursor();
			int firstNameCol = mCursorAdapter.getCursor().getColumnIndexOrThrow(AthleteDataAdapter.KEY_FIRST_NAME);
			String firstName="";
			if(firstNameCol!=-1){
			firstName = currentCursor.getString(firstNameCol);
			}else{
				Log.d(CRT,"First name incorrect");
			}
			int lastNameCol = mCursorAdapter.getCursor().getColumnIndexOrThrow(AthleteDataAdapter.KEY_LAST_NAME);
			String lastName = "";
			if(lastNameCol!=-1){
				lastName=currentCursor.getString(lastNameCol);
			}else{
				Log.d(CRT,"Last name incorrect");
			}
			lapMap.put(firstName + " " + lastName, new ArrayList<String>());
			int idCol = currentCursor.getColumnIndexOrThrow(AthleteDataAdapter.KEY_ID);
			Log.d(CRT,""+currentCursor.getString(idCol));
//			}catch (Exception e){
//				break;
//			}
		} while(mCursorAdapter.getCursor().moveToNext());
		}
		Log.d(CRT, lapMap.toString());

		// athleteArray = new ArrayList<String>();
		// for(int i=1;i<15;i++){
		// athleteArray.add("Test Athlete "+i);
		// }
		// adapter = new ArrayAdapter<String>(this,
		// R.layout.athlete_view, R.id.athleteNameText, athleteArray);
		// athleteList.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (startButton == v) {
			Log.d(CRT, "Start");
			this.timerHandler.sendEmptyMessage(TimerHandler.TIMER_START);
			// mCursorAdapter.setLapEnabled(true);
			// mCursorAdapter.setStopEnabled(true);
		} else if (stopButton == v) {
			Log.d(CRT, "Stop");
			// mCursorAdapter.setStopEnabled(false);
			// mCursorAdapter.setLapEnabled(false);
			timerHandler.sendEmptyMessage(TimerHandler.TIMER_STOP);
		} else if (addAthleteButton == v) {
			Log.d(CRT, "addAthleteButton");
			if (!stopWatch.isAlive()) {// Can't add athletes once the stopwatch
										// has started
				Intent newAthleteIntent = new Intent(this,
						NewAthleteActivity.class);
				newAthleteIntent.putExtra(AthletesView.EDIT_OR_NEW,
						AthletesView.NEW);
				startActivityForResult(newAthleteIntent,
						REQUEST_CODE_NEW_ATHLETE);
			}
		} else if (saveRaceButton == v) {
			Intent intent = new Intent(this, SaveDialogActivity.class);
			startActivityForResult(intent, REQUEST_CODE_SAVE_FILE);
		}
		Log.d(CRT, "TEST");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_NEW_ATHLETE:
			if (resultCode == Activity.RESULT_OK) {
				Log.d(CRT, "Result ok!");
				Athlete a = new Athlete();

				// a.setRank(data.getIntExtra(KEY_RANK_STRING,0));
				// a.setName(data.getStringExtra(KEY_NAME_STRING));
				a.setFirstName(data.getStringExtra(KEY_FIRST_NAME_STRING));
				a.setLastName(data.getStringExtra(KEY_LAST_NAME_STRING));
				a.setMainEvent(data.getStringExtra(KEY_MAIN_EVENT_STRING));
				a.setPR(data.getStringExtra(KEY_PR_STRING));
				mAthleteDataAdapter.addAthlete(a);
				Cursor cursor = mAthleteDataAdapter.getAthletesCursor();
				mCursorAdapter.changeCursor(cursor);
				lapMap = new HashMap<String, List<String>>();
				// for(int i=0;i<mCursorAdapter.getCursor().getCount();i++){
				// lapMap.put(mCursorAdapter.getCursor().getString(0)+" "+mCursorAdapter.getCursor().getString(1),
				// new ArrayList<String>());
				// }TODO: FIX THIS!!!!!!
				// int size = athleteArray.size();
				// int rank = data.getIntExtra(KEY_RANK_STRING,
				// size);
				// String name = data.getStringExtra(KEY_NAME_STRING);
				// athleteArray.add((rank-1 <= size ? rank-1 : size), name);
				// adapter.notifyDataSetChanged();
			} else {
				Log.d(CRT, "Result not okay. User hit back w/o a button");
			}
			break;
		case REQUEST_CODE_SAVE_FILE:
			if (resultCode == Activity.RESULT_OK) {

				Toast.makeText(MainActivity.this, "Saving...",
						Toast.LENGTH_SHORT).show();
				File root = Environment.getExternalStorageDirectory();
				String fileName = data
						.getStringExtra(SaveDialogActivity.FILE_NAME);
				if (!fileName.endsWith(".csv")) {
					fileName += ".csv";
				}
				File athleteDataFile = new File(root, fileName);
				// Create the CSV within here

				try {
					writer = new FileWriter(athleteDataFile);
					writeCsvData(lapMap);
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Toast.makeText(MainActivity.this,
						"Saved to device as " + fileName, Toast.LENGTH_SHORT)
						.show();
			}
			break;
		default:
			Log.d(CRT, "Unknown result code");
			break;
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
		case R.id.manage_athletes:
			Intent intent3 = new Intent(this, AthletesView.class);
			startActivity(intent3);
			return true;
		}
		return false;
	}

	// private Athlete getAthlete(long id){
	// return mAthleteDataAdapter.getAthlete(id);
	// }
	//
	// private void editAthlete(Athlete a){
	// mAthleteDataAdapter.updateAthlete(a);
	// Cursor cursor = mAthleteDataAdapter.getAthletesCursor();
	// mCursorAdapter.changeCursor(cursor);
	// }
	//
	// private void removeAthlete(long id){
	// mAthleteDataAdapter.removeAthlete(id);
	// Cursor cursor = mAthleteDataAdapter.getAthletesCursor();
	// mCursorAdapter.changeCursor(cursor);
	// }

	public void stop(View v) {
		lap(v);
		Button button = (Button) v.findViewById(R.id.lapButton);
		// if(button!=null){
		// button.setEnabled(false);
		// }
	}

	public void lap(View v) {
		Log.d(CRT, "Lap called");
		TextView first = (TextView) v.findViewById(R.id.athleteFirstNameText);
		if (first != null) {
			String firstName = first.getText().toString();
			String lastName = ((TextView) v
					.findViewById(R.id.athleteLastNameText)).getText()
					.toString();
			lapMap.get(firstName + " " + lastName).add(stopWatch.getTime());
			Log.d(CRT, lapMap.toString());
		}
	}

	// Method to Create CSV File
	private void writeCsvData(Map<String, List<String>> data)
			throws IOException {
		StringBuilder builder = new StringBuilder();
		Iterator<Map.Entry<String,List<String>>> iter = data.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String,List<String>> pair = (Map.Entry<String,List<String>>)iter.next();
			builder.append(pair.getKey());
			for(String lap : pair.getValue()){
				builder.append(", ");
				builder.append(lap);
			}
			builder.append("\n");
			iter.remove();
			
		}
		writer.write(builder.toString());
	}

}
