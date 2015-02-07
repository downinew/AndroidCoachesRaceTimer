package edu.rosehulman.coachesracetimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewAthleteActivity extends Activity implements OnClickListener {

	Button addButton;
	EditText nameText;
	EditText rankText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_athlete);
		addButton = (Button) findViewById(R.id.addButton);
		nameText = (EditText) findViewById(R.id.nameText);
		rankText = (EditText) findViewById(R.id.rankText);
		addButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.addButton){
			Intent returnIntent = new Intent();
			returnIntent.putExtra(MainActivity.KEY_NAME_STRING, nameText.getText().toString());
			returnIntent.putExtra(MainActivity.KEY_RANK_STRING, Integer.parseInt(rankText.getText().toString()));
			setResult(RESULT_OK, returnIntent);
			this.finish();
		}
	}
}
