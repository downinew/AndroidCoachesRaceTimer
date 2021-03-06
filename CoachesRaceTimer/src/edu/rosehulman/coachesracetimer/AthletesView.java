package edu.rosehulman.coachesracetimer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class AthletesView extends Activity implements OnClickListener {
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

	public static final long NO_ID_SELECTED = 0;
	private AthleteDataAdapter mAthleteDataAdapter;
	private SimpleCursorAdapter mCursorAdapter;
	private long mSelectedId = NO_ID_SELECTED;
	private Button addAthletesButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_athletes_view);

		addAthletesButton = (Button) findViewById(R.id.add_athlete_to_team_button);
		addAthletesButton.setOnClickListener(this);
		mAthleteDataAdapter = new AthleteDataAdapter(this);
		mAthleteDataAdapter.open();
		Cursor cursor = mAthleteDataAdapter.getAthletesCursor();
		String[] fromColumns = new String[] {
				AthleteDataAdapter.KEY_FIRST_NAME,
				AthleteDataAdapter.KEY_LAST_NAME,
				AthleteDataAdapter.KEY_MAIN_EVENT, AthleteDataAdapter.KEY_PR };
		int[] toTextViews = new int[] { R.id.firstName, R.id.lastName,
				R.id.mainEvent, R.id.pr };
		mCursorAdapter = new AthleteManagerCursorAdapter(this,
				R.layout.athlete_list_item, cursor, fromColumns, toTextViews, 0);
		ListView listView = (ListView) findViewById(android.R.id.list);
		mCursorAdapter.getCursor().moveToFirst();
		listView.setAdapter(mCursorAdapter);
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final TextView fName = (TextView) view
						.findViewById(R.id.firstName);
				final TextView lName = (TextView) view
						.findViewById(R.id.lastName);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						AthletesView.this);
				builder.setTitle("Delete " + fName.getText().toString() + " "
						+ lName.getText().toString() + " from roster?");
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								Cursor c = mCursorAdapter.getCursor();
								c.moveToFirst();
								int iD = -1;
								if (c.getCount() > 0) {
									do {
										if (c.getString(
												c.getColumnIndex(AthleteDataAdapter.KEY_FIRST_NAME))
												.equals(fName.getText()
														.toString())
												&& c.getString(
														c.getColumnIndex(AthleteDataAdapter.KEY_LAST_NAME))
														.equals(lName.getText()
																.toString())) {
											iD = c.getInt(c
													.getColumnIndex(AthleteDataAdapter.KEY_ID));
											break;
										}
									} while (c.moveToNext());
								}
								removeAthlete(iD);
								String[] fromColumns = new String[] {
										AthleteDataAdapter.KEY_FIRST_NAME,
										AthleteDataAdapter.KEY_LAST_NAME,
										AthleteDataAdapter.KEY_MAIN_EVENT,
										AthleteDataAdapter.KEY_PR };
								int[] toTextViews = new int[] { R.id.firstName,
										R.id.lastName, R.id.mainEvent, R.id.pr };
								mCursorAdapter = new AthleteManagerCursorAdapter(
										AthletesView.this,
										R.layout.athlete_list_item,
										mAthleteDataAdapter.getAthletesCursor(),
										fromColumns, toTextViews, 0);
								Log.d(MainActivity.CRT, "Called remove");
							}

						});
				builder.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();
				return true;
			}

		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectedId = id;
				Intent newAthleteIntent = new Intent(AthletesView.this,
						NewAthleteActivity.class);
				newAthleteIntent.putExtra(EDIT_OR_NEW, EDIT);
				newAthleteIntent.putExtra("FIRST_NAME", ((TextView) view
						.findViewById(R.id.firstName)).getText().toString());
				newAthleteIntent.putExtra("LAST_NAME", ((TextView) view
						.findViewById(R.id.lastName)).getText().toString());
				newAthleteIntent.putExtra("MAIN_EVENT", ((TextView) view
						.findViewById(R.id.mainEvent)).getText().toString());
				newAthleteIntent.putExtra("PR", ((TextView) view
						.findViewById(R.id.pr)).getText().toString());
				startActivityForResult(newAthleteIntent,
						MainActivity.REQUEST_CODE_NEW_ATHLETE);
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
			Intent intent3 = new Intent(this, ConvertActivity.class);
			startActivity(intent3);
			return true;
		case R.id.manage_athletes:
			return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MainActivity.REQUEST_CODE_NEW_ATHLETE
				&& resultCode == RESULT_OK) {
			Athlete a = new Athlete();
			a.setFirstName(data
					.getStringExtra(MainActivity.KEY_FIRST_NAME_STRING));
			a.setLastName(data
					.getStringExtra(MainActivity.KEY_LAST_NAME_STRING));
			a.setMainEvent(data
					.getStringExtra(MainActivity.KEY_MAIN_EVENT_STRING));
			a.setPR(data.getStringExtra(MainActivity.KEY_PR_STRING));
			if (mSelectedId == NO_ID_SELECTED) {
				addAthlete(a);
			} else {
				editAthlete(a);
			}
			String[] fromColumns = new String[] {
					AthleteDataAdapter.KEY_FIRST_NAME,
					AthleteDataAdapter.KEY_LAST_NAME,
					AthleteDataAdapter.KEY_MAIN_EVENT,
					AthleteDataAdapter.KEY_PR };
			int[] toTextViews = new int[] { R.id.firstName, R.id.lastName,
					R.id.mainEvent, R.id.pr };
			mCursorAdapter = new AthleteManagerCursorAdapter(AthletesView.this,
					R.layout.athlete_list_item,
					mAthleteDataAdapter.getAthletesCursor(), fromColumns,
					toTextViews, 0);
		}
	}

	private void addAthlete(Athlete currentAthlete) {
		mAthleteDataAdapter.addAthlete(currentAthlete);
		mCursorAdapter.changeCursor(mAthleteDataAdapter.getAthletesCursor());
	}

	private void editAthlete(Athlete currentAthlete) {
		currentAthlete.setId(mSelectedId);
		mAthleteDataAdapter.updateAthlete(currentAthlete);
		mCursorAdapter.changeCursor(mAthleteDataAdapter.getAthletesCursor());
	}

	public void removeAthlete(long id) {
		if (mAthleteDataAdapter.removeAthlete(id)) {
			Log.d(MainActivity.CRT, "Removed athlete");
		} else {
			Log.d(MainActivity.CRT, "Failed to remove athlete");
		}
		mCursorAdapter.changeCursor(mAthleteDataAdapter.getAthletesCursor());
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
			confirmButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					currentAthlete.setFirstName(firstName.getText().toString());
					currentAthlete.setLastName(lastName.getText().toString());
					currentAthlete.setMainEvent(event.getSelectedItem()
							.toString());
					if (mSelectedId == NO_ID_SELECTED) {
						addAthlete(currentAthlete);
					} else {
						editAthlete(currentAthlete);
					}

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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.add_athlete_to_team_button) {
			mSelectedId = NO_ID_SELECTED;
			Intent newAthleteIntent = new Intent(this, NewAthleteActivity.class);
			newAthleteIntent.putExtra(AthletesView.EDIT_OR_NEW,
					AthletesView.NEW);
			startActivityForResult(newAthleteIntent,
					MainActivity.REQUEST_CODE_NEW_ATHLETE);
		}
		Log.d("CRT", "Click");
	}

}
