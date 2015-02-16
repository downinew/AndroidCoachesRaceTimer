package edu.rosehulman.coachesracetimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ClipData.Item;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ConvertActivity extends Activity {

	ListView event1;
	ListView event2;
	Object item = null;
	Object item2 = null;
	Button convertButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_convert);
		event1 = (ListView) findViewById(R.id.listView1);
		event2 = (ListView) findViewById(R.id.listView2);
		convertButton = (Button) findViewById(R.id.convert_button);
		String[] events = getResources().getStringArray(R.array.events);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, events);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, events);
		event1.setAdapter(adapter1);
		event2.setAdapter(adapter2);

		event1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				item = parent.getItemAtPosition(position);
			}
		});

		event2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				item2 = parent.getItemAtPosition(position);
			}
		});

		convertButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				printFunction();
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
			Intent intent2 = new Intent(this, DualTimerActivity.class);
			startActivity(intent2);
			return true;
		case R.id.convert_events:
			return true;
		case R.id.manage_athletes:
			Intent intent3 = new Intent(this,AthletesView.class);
			startActivity(intent3);
			return true;
		}
		return false;
	}

	public void printFunction() {

		if (item != null && item2 != null) {
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("Event Conversion");
			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							// Nothing
						}
					});
			builder.setMessage("Implementation nearing completion all that is left is the conversion");
			builder.create();
			builder.show();
		}
	}

}
