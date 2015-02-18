package edu.rosehulman.coachesracetimer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class NewAthleteActivity extends Activity implements OnClickListener {

	Button addButton;
	EditText firstNameText;
	EditText lastNameText;
	Spinner eventSpinner;
	NumberPicker hour;
	NumberPicker min;
	NumberPicker sec;
	NumberPicker ms;
	private static final List<String> eventList;
	
	static {
		eventList = new ArrayList<String>();
		eventList.add("800m");
		//TODO: add more events
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_athlete);
		addButton = (Button) findViewById(R.id.addButton);
		firstNameText = (EditText) findViewById(R.id.firstNameText);
		lastNameText = (EditText) findViewById(R.id.lastNameText);
		eventSpinner = (Spinner) findViewById(R.id.eventSpinner);
		eventSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventList));
		hour = (NumberPicker) findViewById(R.id.hourPicker);
		min = (NumberPicker) findViewById(R.id.minutePicker);
		sec = (NumberPicker) findViewById(R.id.secondPicker);
		ms = (NumberPicker) findViewById(R.id.millisecondPicker);
		addButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.addButton){
			Intent returnIntent = new Intent();
			returnIntent.putExtra(MainActivity.KEY_FIRST_NAME_STRING, firstNameText.getText().toString());
			returnIntent.putExtra(MainActivity.KEY_LAST_NAME_STRING, lastNameText.getText().toString());
			returnIntent.putExtra(MainActivity.KEY_MAIN_EVENT_STRING,eventSpinner.getItemAtPosition(eventSpinner.getSelectedItemPosition()).toString());
			returnIntent.putExtra(MainActivity.KEY_PR_STRING, ""+hour.getValue()+":"+min.getValue()+":"+sec.getValue()+"."+ms.getValue());
			setResult(RESULT_OK, returnIntent);
			this.finish();
		}
	}
}
