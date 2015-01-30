package edu.rosehulman.coachesracetimer;

public class StopWatch extends Thread {

	// Possible Use for later
	// private boolean isRunning = false;
	private long startTime;
	private long lapTime;

	public StopWatch() {

	}

	public long getTime() {
		Long currentTime = System.nanoTime();
		return (currentTime - startTime) / 1000000;
	}

	public long getLap() {
		Long currentTime = System.nanoTime();
		Long currentLapTime = (currentTime - this.lapTime) / 1000000;
		this.lapTime = currentTime;
		return currentLapTime;
	}

	public void restart() {
		// TODO: keep track of total time, e.g. when user presses start, then
		// stop, then start again. Currently crashes in this scenario.
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		startTime = System.nanoTime();
		lapTime = startTime;

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
