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
import android.widget.SpinnerAdapter;

public class NewAthleteActivity extends Activity implements OnClickListener {

	Button addButton;
	EditText firstNameText;
	EditText lastNameText;
	Spinner eventSpinner;
	EditText hour;
	EditText min;
	EditText sec;
	EditText ms;
	private static final List<String> eventList;
	
	static {
		eventList = new ArrayList<String>();
		eventList.add("55m");
		eventList.add("60m");
		eventList.add("55m Hurdles");
		eventList.add("60m Hurdles");
		eventList.add("100m");
		eventList.add("100m Hurdles");
		eventList.add("110m Hurdles");
		eventList.add("200m");
		eventList.add("400m");
		eventList.add("600m");
		eventList.add("800m");
		eventList.add("1000m");
		eventList.add("1500m");
		eventList.add("1600m");
		eventList.add("Mile");
		eventList.add("2000m Steeplechase");
		eventList.add("3000m");
		eventList.add("3000m Steeplechase");
		eventList.add("3200m");
		eventList.add("5000m");
		eventList.add("8000m");
		eventList.add("10,000m");
		eventList.add("15,000m");
		eventList.add("Half-marathon");
		eventList.add("Marathon");
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
		hour = (EditText) findViewById(R.id.hourPicker);
		min = (EditText) findViewById(R.id.minutePicker);
		sec = (EditText) findViewById(R.id.secondPicker);
		ms = (EditText) findViewById(R.id.millisecondPicker);
		if(getIntent().getStringExtra(AthletesView.EDIT_OR_NEW).equals(AthletesView.EDIT) ? true : false){//If it's an edit
			firstNameText.setText(getIntent().getStringExtra("FIRST_NAME"));
			lastNameText.setText(getIntent().getStringExtra("LAST_NAME"));
			String mainEventString = getIntent().getStringExtra("MAIN_EVENT");
			SpinnerAdapter adapter = eventSpinner.getAdapter();
			for(int i=0;i<adapter.getCount();i++){
				if(adapter.getItem(i).equals(mainEventString)){
					eventSpinner.setSelection(i);
					break;
				}
			}
			String[] prStrings = getIntent().getStringExtra("PR").split(":");
			hour.setText(prStrings[0]);
			min.setText(prStrings[1]);
			sec.setText(prStrings[2].split("\\.")[0]);
			ms.setText(prStrings[2].split("\\.")[1]);
		}//Might need else here? not sure
		addButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.addButton){
			Intent returnIntent = new Intent();
			returnIntent.putExtra(MainActivity.KEY_FIRST_NAME_STRING, firstNameText.getText().toString());
			returnIntent.putExtra(MainActivity.KEY_LAST_NAME_STRING, lastNameText.getText().toString());
			returnIntent.putExtra(MainActivity.KEY_MAIN_EVENT_STRING,eventSpinner.getItemAtPosition(eventSpinner.getSelectedItemPosition()).toString());
			returnIntent.putExtra(MainActivity.KEY_PR_STRING, ""+hour.getText()+":"+min.getText()+":"+sec.getText()+"."+ms.getText());
			setResult(RESULT_OK, returnIntent);
			this.finish();
		}
	}
}
