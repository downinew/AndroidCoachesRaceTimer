package edu.rosehulman.coachesracetimer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
=======
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
>>>>>>> 19bd8289272200bb67a00c72869b70b90bd04852

public class MainActivity extends Activity  implements OnClickListener{
	
	int TIMER_START = 0;
	int TIMER_UPDATE = 1;
	int TIMER_STOP = 2;
	StopWatch timer;
	Button startButton;
	Button stopButton;    
	TextView timeView;
    Handler timerHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == TIMER_START){
				timer.start();
				timerHandler.sendEmptyMessageDelayed(TIMER_UPDATE, 100);
			}else if(msg.what == TIMER_UPDATE){
				timeView.setText("" + timer.returnTime());
				timerHandler.sendEmptyMessageDelayed(TIMER_UPDATE, 100);
			}else if(msg.what == TIMER_STOP){
				timerHandler.removeMessages(TIMER_UPDATE);
				timer.interrupt();;
				timeView.setText("" + timer.returnTime());
			}
		}
    	
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        timeView = (TextView) findViewById(R.id.textView1);
        startButton = (Button) findViewById(R.id.StartButton);
        stopButton = (Button) findViewById(R.id.StopButton);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        
        timer = new StopWatch(this);
        
    }
    
    
    public void createTimerThread(){
    }
    
<<<<<<< HEAD
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(startButton == v){
			this.timerHandler.sendEmptyMessage(TIMER_START);
		}else if(stopButton == v){
			timerHandler.sendEmptyMessage(TIMER_STOP);
		}
		
		
=======
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		int id=item.getItemId();
		switch(id){
		case R.id.race_timer:
			return true;
		case R.id.dual_timer:
			Intent intent = new Intent(this,DualTimerActivity.class);
			startActivity(intent);
			return true;
		case R.id.convert_events:
			Intent intent2 = new Intent(this,ConvertActivity.class);
			startActivity(intent2);
			return true;
		case R.id.upload_results:
			//TODO: add this later
			return true;
		}
		return false;
>>>>>>> 19bd8289272200bb67a00c72869b70b90bd04852
	}
}
