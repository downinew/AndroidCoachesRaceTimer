package edu.rosehulman.coachesracetimer;

public class StopWatch extends Thread {

	private static final int HOUR_IN_MS = 3600000;
	private static final int MIN_IN_MS = 60000;
	private static final int SEC_IN_MS = 1000;
	private static final int SEC_IN_NS = 1000000;
	// Possible Use for later
	// private boolean isRunning = false;
	private long startTime;
	private long lapTime;

	public StopWatch() {

	}

	public String getTime() {
		Long currentTime = System.nanoTime();

		long elapsed = (currentTime - startTime) / SEC_IN_NS;

		return formatTime(elapsed);
	}

	public String getLap() {
		Long currentTime = System.nanoTime();
		Long currentLapTime = (currentTime - this.lapTime) / SEC_IN_NS;
		this.lapTime = currentTime;
		return formatTime(currentLapTime);
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

	private String formatTime(long ms) {
		StringBuilder sb = new StringBuilder();
		long hrs = 0;
		long mins = 0;
		long secs = 0;
		if (ms >= HOUR_IN_MS) {
			hrs = ms / HOUR_IN_MS;
			ms -= HOUR_IN_MS * hrs;
		}
		if (ms >= MIN_IN_MS) {
			mins = ms / MIN_IN_MS;
			ms -= MIN_IN_MS * mins;
		}
		if (ms >= SEC_IN_MS) {
			secs = ms / SEC_IN_MS;
			ms -= SEC_IN_MS * secs;
		}
		sb.append(hrs);
		sb.append(":");
		if (mins < 10) {
			sb.append('0');
		}
		sb.append(mins);
		sb.append(":");
		if (secs < 10) {
			sb.append('0');
		}
		sb.append(secs);
		sb.append(".");
		if (ms < 100) {
			sb.append('0');
		}
		if (ms < 10) {
			sb.append('0');
		}
		sb.append(ms);
		return sb.toString();
	}

}
