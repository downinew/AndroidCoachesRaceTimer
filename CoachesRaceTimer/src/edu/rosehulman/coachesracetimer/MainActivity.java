package edu.rosehulman.coachesracetimer;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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
    
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(startButton == v){
			this.timerHandler.sendEmptyMessage(TIMER_START);
		}else if(stopButton == v){
			timerHandler.sendEmptyMessage(TIMER_STOP);
		}
		
		
	}
}
