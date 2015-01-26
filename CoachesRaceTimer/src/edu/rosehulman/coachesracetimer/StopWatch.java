package edu.rosehulman.coachesracetimer;

import android.content.Context;
import android.widget.Chronometer;

public class StopWatch extends Thread {
	
	
	public boolean isRunning = true;
	Chronometer timer;
	private Context context;
	
	
	public StopWatch(Context context){
		this.context = context;
	}
	
	public long returnTime(){
		return timer.getBase();
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
		timer = new Chronometer(this.context);
		timer.start();
	}
	
	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		super.interrupt();
		timer.stop();
		
	}

	
	
	
	
}
