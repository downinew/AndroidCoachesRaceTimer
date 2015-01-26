package edu.rosehulman.coachesracetimer;


public class StopWatch extends Thread {
	
	//Possible Use for later
	//private boolean isRunning = false;
	private long startTime;
	
	public StopWatch(){
		
	}
	
	public long getTime(){
		Long currentTime = System.nanoTime();
		return (currentTime - startTime)/1000000;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		startTime = System.nanoTime();
		
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
		
	}
	
	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		super.interrupt();		
	}

	
	
	
	
}
