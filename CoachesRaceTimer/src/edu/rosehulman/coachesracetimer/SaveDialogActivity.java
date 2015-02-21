package edu.rosehulman.coachesracetimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SaveDialogActivity extends Activity {
	
	public static final String FILE_NAME = "FILE_NAME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_dialog);
		Button saveButton = (Button) findViewById(R.id.saveButton);
		Button cancelButton = (Button) findViewById(R.id.cancelSaveButton);
		final EditText fileName = (EditText) findViewById(R.id.fileNameEditText);
		saveButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				returnIntent.putExtra(FILE_NAME, fileName.getText().toString());
				setResult(RESULT_OK,returnIntent);
				finish();
			}
			
		});
		cancelButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED,returnIntent);
				finish();
			}
			
		});
	}

}
