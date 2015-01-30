package edu.rosehulman.coachesracetimer;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class TimerHandler extends Handler {

	static final int TIMER_START = 0;
	static final int TIMER_UPDATE = 1;
	static final int TIMER_STOP = 2;
	static final int REFRESH_DELAY_MILLIS = 25;
	StopWatch stopWatch;
	TextView timeView;
	
	public TimerHandler(StopWatch stopWatch,TextView timeView) {
		this.stopWatch = stopWatch;
		this.timeView = timeView;
	}

	@Override
	public void handleMessage(Message msg){
		super.handleMessage(msg);
		if(msg.what == TIMER_START){
			stopWatch.run();
			this.sendEmptyMessageDelayed(TIMER_UPDATE, REFRESH_DELAY_MILLIS);
		}else if(msg.what == TIMER_UPDATE){
			timeView.setText("" + stopWatch.getTime());
			this.sendEmptyMessageDelayed(TIMER_UPDATE, REFRESH_DELAY_MILLIS);
		}else if(msg.what == TIMER_STOP){
			this.removeMessages(TIMER_UPDATE);
			stopWatch.interrupt();
			timeView.setText("" + stopWatch.getTime());
		}
	}
	
}
