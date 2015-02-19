package edu.rosehulman.coachesracetimer;

import java.util.ArrayList;
import java.util.List;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class AthletesView extends ListActivity {
	public static final String NEW = "NEW";
	public static final String EDIT = "EDIT";
	public static final String EDIT_OR_NEW = "EDIT_OR_NEW";
	public static final String EDIT_ATHLETE_ID = "EDIT_ATHLETE_ID";
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

	public static final long NO_ID_SELECTED = -1;
	private AthleteDataAdapter mAthleteDataAdapter;
	private SimpleCursorAdapter mCursorAdapter;
	private long mSelectedId = NO_ID_SELECTED;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_athletes_view);

		mAthleteDataAdapter = new AthleteDataAdapter(this);
		mAthleteDataAdapter.open();
		Cursor cursor = mAthleteDataAdapter.getAthletesCursor();
		String[] fromColumns = new String[] {
				AthleteDataAdapter.KEY_FIRST_NAME,
				AthleteDataAdapter.KEY_LAST_NAME,
				AthleteDataAdapter.KEY_MAIN_EVENT, AthleteDataAdapter.KEY_PR };
		int[] toTextViews = new int[] { R.id.firstName, R.id.lastName,
				R.id.mainEvent, R.id.pr };
		mCursorAdapter = new SimpleCursorAdapter(this,
				R.layout.athlete_list_item, cursor, fromColumns, toTextViews, 0);
		setListAdapter(mCursorAdapter);
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
			Intent intent3 = new Intent(this, ConvertActivity.class);
			startActivity(intent3);
			return true;
		case R.id.manage_athletes:
			return true;
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mSelectedId = id;

		Intent newAthleteIntent = new Intent(this, NewAthleteActivity.class);
		newAthleteIntent.putExtra(EDIT_OR_NEW, EDIT);
		newAthleteIntent.putExtra("FIRST_NAME", ((TextView) l.findViewById(R.id.firstName)).getText()
				.toString());
		newAthleteIntent.putExtra("LAST_NAME", ((TextView) l.findViewById(R.id.lastName)).getText()
				.toString());
		newAthleteIntent.putExtra("MAIN_EVENT", ((TextView) l.findViewById(R.id.mainEvent)).getText()
				.toString());
//		EditText hr = (EditText) l.findViewById(R.id.hourPicker);
//		EditText min = (EditText) l.findViewById(R.id.minutePicker);
//		EditText sec = (EditText) l.findViewById(R.id.secondPicker);
//		EditText ms = (EditText) l.findViewById(R.id.millisecondPicker);
		newAthleteIntent.putExtra("PR",((TextView) l.findViewById(R.id.pr)).getText().toString());
		startActivityForResult(newAthleteIntent,
				MainActivity.REQUEST_CODE_NEW_ATHLETE);

		// DialogFragment df = new AthleteDialogFragment();
		// df.show(getFragmentManager(), "");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==MainActivity.REQUEST_CODE_NEW_ATHLETE&&resultCode==RESULT_OK){
			Athlete a = new Athlete();
			a.setFirstName(data.getStringExtra(MainActivity.KEY_FIRST_NAME_STRING));
			a.setLastName(data.getStringExtra(MainActivity.KEY_LAST_NAME_STRING));
			a.setMainEvent(data.getStringExtra(MainActivity.KEY_MAIN_EVENT_STRING));
			a.setPR(data.getStringExtra(MainActivity.KEY_PR_STRING));
			editAthlete(a);
		}
	}
	
	private void editAthlete(Athlete currentAthlete) {
		currentAthlete.setId(mSelectedId);
		mAthleteDataAdapter.updateAthlete(currentAthlete);
		mCursorAdapter.changeCursor(mAthleteDataAdapter
				.getAthletesCursor());
	}

	public final class AthleteDialogFragment extends DialogFragment {
		private final Athlete currentAthlete;

		private AthleteDialogFragment() {
			this.currentAthlete = new Athlete();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater
					.inflate(R.layout.athlete_dialog_add, container);
			getDialog().setTitle(getString(R.string.add_dialog_title));
			final Button confirmButton = (Button) view
					.findViewById(R.id.add_dialog_ok);
			final Button cancelButton = (Button) view
					.findViewById(R.id.add_dialog_cancel);
			final EditText firstName = (EditText) view
					.findViewById(R.id.first_name_edit_text);
			final EditText lastName = (EditText) view
					.findViewById(R.id.last_name_edit_text);
			final Spinner event = (Spinner) view
					.findViewById(R.id.event_spinner);
			// event.setAdapter(new ArrayAdapter<String>(this,
			// android.R.layout.simple_spinner_item, eventList));

			firstName.setText(currentAthlete.getFirstName());
			lastName.setText(currentAthlete.getLastName());
			SpinnerAdapter adapter = event.getAdapter();
			String mainEventString = currentAthlete.getMainEvent();
			for (int i = 0; i < adapter.getCount(); i++) {
				if (adapter.getItem(i).equals(mainEventString)) {
					event.setSelection(i);
					break;
				}
			}

			// List<String> eventList = new ArrayList<String>();
			// SpinnerAdapter adapter = new SimpleAdapter(null, null, position,
			// null, null);

			confirmButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					currentAthlete.setFirstName(firstName.getText().toString());
					currentAthlete.setLastName(lastName.getText().toString());
					currentAthlete.setMainEvent(event.getSelectedItem()
							.toString());
					// Toast.makeText(AthletesView.this,"Updated "+firstNameText+" "+lastNameText+"'s information",Toast.LENGTH_SHORT).show();
					if (mSelectedId == NO_ID_SELECTED) {
						addAthlete(currentAthlete);
					} else {
						editAthlete(currentAthlete);
					}
					// currentAthlete.setName(firstNameText+" "+lastNameText);
					// currentAthlete.setMainEvent(event.getSelectedItem().toString());
					// currentAthlete.setId(mSelectedId);

					dismiss();
				}

				private void editAthlete(Athlete currentAthlete) {
					currentAthlete.setId(mSelectedId);
					mAthleteDataAdapter.updateAthlete(currentAthlete);
					mCursorAdapter.changeCursor(mAthleteDataAdapter
							.getAthletesCursor());
				}

				private void addAthlete(Athlete currentAthlete) {
					mAthleteDataAdapter.addAthlete(currentAthlete);
					Cursor cursor = mAthleteDataAdapter.getAthletesCursor();
					mCursorAdapter.changeCursor(cursor);
				}

			});
			cancelButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();

				}

			});

			return view;
		}
	}

}
