package edu.rosehulman.coachesracetimer;

public class Athlete {

	private String mFirstName;
	private String mLastName;
	private String mPR;
	private String mMainEvent;
	private long mId;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		this.mId = id;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public void setFirstName(String name) {
		mFirstName = name;
	}
	
	public String getLastName(){
		return mLastName;
	}
	
	public void setLastName(String name){
		mLastName = name;
	}
	
	public String getPR(){
		return mPR;
	}
	
	public void setPR(String pr){
		mPR = pr;
	}
	
	public void setPR(double pr){
		int hrs = (int) (pr/3600);
		pr-=3600*hrs;
		int mins = (int) (pr/60);
		pr-=60*mins;
		int secs = (int) pr;
		pr-=secs;
		int millis = (int) (1000*pr);
		mPR = ""+hrs+":"+mins+":"+secs+"."+millis;
	}
	
	public double getPRSeconds(){
		String[] time = getPR().split(":");
		int hrs = Integer.parseInt(time[0]);
		int mins = Integer.parseInt(time[1]);
		int secs = Integer.parseInt(time[2].split(".")[0]);
		int millis = Integer.parseInt(time[2].split(".")[1]);
		return 3600*hrs+60*mins+secs+millis*0.001;
	}
	
	public String getMainEvent() {
		return mMainEvent;
	}

	public void setMainEvent(String mainEvent) {
		mMainEvent=mainEvent;
	}
	
	public double compareTo(Athlete other) {
		return getPRSeconds()-other.getPRSeconds();
	}

	public String toString() {
		return getFirstName() + " " + getLastName() + " " + getMainEvent() + " " + getPR();
	}

	

}
