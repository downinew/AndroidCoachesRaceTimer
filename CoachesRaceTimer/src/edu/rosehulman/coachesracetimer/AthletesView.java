package edu.rosehulman.coachesracetimer;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class AthletesView extends ListActivity {

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
		String[] fromColumns = new String[] {AthleteDataAdapter.KEY_FIRST_NAME,AthleteDataAdapter.KEY_LAST_NAME,AthleteDataAdapter.KEY_MAIN_EVENT,AthleteDataAdapter.KEY_PR};
		int[] toTextViews = new int[]{R.id.firstName,R.id.lastName,R.id.year,R.id.mainEvent};//change to allow for year here. Significant.
		mCursorAdapter = new SimpleCursorAdapter(this,R.layout.athlete_list_item,cursor,fromColumns,toTextViews,0);
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
			Intent intent3 = new Intent(this,ConvertActivity.class);
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
		ListAdapter adapter = getListAdapter();
		final Athlete currentAthlete;
		if(adapter!=null){
		currentAthlete = (Athlete) adapter.getItem(position);
		}else{
			Log.d(MainActivity.CRT,"adapter is null");
			currentAthlete = new Athlete();
		}
		
		DialogFragment df = new AthleteDialogFragment(currentAthlete);
		df.show(getFragmentManager(), "");
	}
	
	private final class AthleteDialogFragment extends DialogFragment {
		private final Athlete currentAthlete;

		private AthleteDialogFragment(Athlete currentAthlete) {
			this.currentAthlete = currentAthlete;
		}

		@Override
		public View onCreateView(LayoutInflater inflater,
				ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.athlete_dialog_add, container);//TODO: Create dialog XML for this
			getDialog().setTitle(getString(R.string.add_dialog_title));
			final Button confirmButton = (Button) view.findViewById(R.id.add_dialog_ok);
			final Button cancelButton = (Button) view.findViewById(R.id.add_dialog_cancel);
			final EditText firstName = (EditText) view.findViewById(R.id.first_name_edit_text);
			final EditText lastName = (EditText) view.findViewById(R.id.last_name_edit_text);
			final Spinner event = (Spinner) view.findViewById(R.id.event_spinner);
			
			firstName.setText(currentAthlete.getFirstName());
			lastName.setText(currentAthlete.getLastName());
			event.setPrompt(currentAthlete.getMainEvent());
			
//				List<String> eventList = new ArrayList<String>();
//				SpinnerAdapter adapter = new SimpleAdapter(null, null, position, null, null);
			
			confirmButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					currentAthlete.setFirstName(firstName.getText().toString());
					currentAthlete.setLastName(lastName.getText().toString());
					currentAthlete.setMainEvent(event.getSelectedItem().toString());
//					Toast.makeText(AthletesView.this,"Updated "+firstNameText+" "+lastNameText+"'s information",Toast.LENGTH_SHORT).show();
					if(mSelectedId == NO_ID_SELECTED){
						addAthlete(currentAthlete);
					}else{
						editAthlete(currentAthlete);
					}
//					currentAthlete.setName(firstNameText+" "+lastNameText);
//					currentAthlete.setMainEvent(event.getSelectedItem().toString());
//					currentAthlete.setId(mSelectedId);
					
					dismiss();
				}

				private void editAthlete(Athlete currentAthlete) {
					currentAthlete.setId(mSelectedId);
					mAthleteDataAdapter.updateAthlete(currentAthlete);
					mCursorAdapter.changeCursor(mAthleteDataAdapter.getAthletesCursor());
				}

				private void addAthlete(Athlete currentAthlete) {
					mAthleteDataAdapter.addAthlete(currentAthlete);
					Cursor cursor = mAthleteDataAdapter.getAthletesCursor();
					mCursorAdapter.changeCursor(cursor);
				}
				
			});
			cancelButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					dismiss();
					
				}
				
			});
			
			return view;
		}
	}
	
	
	
}
