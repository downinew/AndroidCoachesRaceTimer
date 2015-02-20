package edu.rosehulman.coachesracetimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AthleteDataAdapter {
	private static final String DATABASE_NAME = "AthleteDb";
	private static final int DATABASE_VERSION = 1;
	private SQLiteOpenHelper mOpenHelper;
	private SQLiteDatabase mDatabase;
	public static final String KEY_ID = "_id";
	public static final String KEY_FIRST_NAME = "firstname";
	public static final String KEY_LAST_NAME = "lastname";
	public static final String KEY_PR = "pr";
	public static final String KEY_MAIN_EVENT = "mainevent";
	private static final String TABLE_NAME = "AthleteTable";

	
	public AthleteDataAdapter(Context context){
		mOpenHelper = new AthleteDbHelper(context);
	}
	
	public void open(){
		mDatabase = mOpenHelper.getWritableDatabase();
	}
	
	public void close(){
		mDatabase.close();
	}
	
	private ContentValues getContentValuesFromAthlete(Athlete athlete){
		ContentValues row = new ContentValues();
		row.put(KEY_FIRST_NAME,athlete.getFirstName());
		row.put(KEY_LAST_NAME, athlete.getLastName());
		row.put(KEY_MAIN_EVENT, athlete.getMainEvent());
		row.put(KEY_PR,athlete.getPR());
		return row;
	}
	
	public long addAthlete(Athlete athlete){
		ContentValues row = getContentValuesFromAthlete(athlete);
		long rowId = mDatabase.insert(TABLE_NAME,null,row);
		athlete.setId(rowId);
		return rowId;
	}
	
	public Cursor getAthletesCursor(){
		String[] projection = new String[] { KEY_ID,KEY_FIRST_NAME,KEY_LAST_NAME,KEY_MAIN_EVENT,KEY_PR};
		return mDatabase.query(TABLE_NAME, projection, null, null, null, null, null);
	}
	
	public Athlete getAthlete(long id){
		String[] projection = new String[]{KEY_ID,KEY_FIRST_NAME,KEY_LAST_NAME,KEY_MAIN_EVENT,KEY_PR};
		String selection = KEY_ID+" = "+id;
		Cursor c = mDatabase.query(TABLE_NAME, projection,selection,null,null,null,null,KEY_PR+"ASC");
		if(c!=null&&c.moveToFirst()){
			return getAthleteFromCursor(c);
		}
		return null;
	}
	
	private Athlete getAthleteFromCursor(Cursor c) {
		Athlete a = new Athlete();
		a.setId(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
		a.setFirstName(c.getString(c.getColumnIndexOrThrow(KEY_FIRST_NAME)));
		a.setLastName(c.getString(c.getColumnIndexOrThrow(KEY_LAST_NAME)));
		a.setMainEvent(c.getString(c.getColumnIndexOrThrow(KEY_MAIN_EVENT)));
		a.setPR(c.getString(c.getColumnIndexOrThrow(KEY_PR)));
		a.setId(c.getLong(c.getColumnIndexOrThrow(KEY_ID)));
		return a;
	}
	
	public void updateAthlete(Athlete athlete){
		ContentValues row = getContentValuesFromAthlete(athlete);
		String selection = KEY_ID+" = "+athlete.getId();
		mDatabase.update(TABLE_NAME, row, selection, null);
	}
	
	public boolean removeAthlete(long id){
		return mDatabase.delete(TABLE_NAME,KEY_ID +" = "+id,null)>0;
	}
	
	public boolean removeAthlete(Athlete a){
		return removeAthlete(a.getId());
	}

	private static class AthleteDbHelper extends SQLiteOpenHelper {
		private static String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;
		private static String CREATE_STATEMENT;
		static {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE "+TABLE_NAME + " (");
			sb.append(KEY_ID + " integer primary key autoincrement, ");
			sb.append(KEY_FIRST_NAME + " text, ");
			sb.append(KEY_LAST_NAME + " text, ");
			sb.append(KEY_MAIN_EVENT + " text, ");
			sb.append(KEY_PR + " text");
			sb.append(")");
			CREATE_STATEMENT = sb.toString();
		}

		public AthleteDbHelper(Context context){
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_STATEMENT);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(MainActivity.CRT,"Updating from version "+oldVersion + " to " + newVersion + ", which will destroy old table(s).");
			db.execSQL(DROP_STATEMENT);
			onCreate(db);
		}
	}

}
