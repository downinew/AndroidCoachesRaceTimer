package edu.rosehulman.coachesracetimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Athlete implements Comparable<Athlete>{

	private String mFirstName;
	private String mLastName;
	private String mPR;
	private String mMainEvent;
	private long mId;
	public static final Map<String,Double> eventWeightMap;
	
	static {
		eventWeightMap = new HashMap<String,Double>();
		eventWeightMap.put("55m",1.);
		eventWeightMap.put("60m",60./55);
		eventWeightMap.put("55m Hurdles",60./55);
		eventWeightMap.put("60m Hurdles",65./55);
		eventWeightMap.put("100m",100./55);
		eventWeightMap.put("100m Hurdles",110./55);
		eventWeightMap.put("110m Hurdles",120./55);
		eventWeightMap.put("200m",200./55);
		eventWeightMap.put("400m",400./55);
		eventWeightMap.put("600m",600./55);
		eventWeightMap.put("800m",800./55);
		eventWeightMap.put("1000m",1000./55);
		eventWeightMap.put("1500m", 1500./55);
		eventWeightMap.put("1600m",1600./55);
		eventWeightMap.put("Mile",1609./55);
		eventWeightMap.put("2000m Steeplechase",2200./55);
		eventWeightMap.put("3000m",3000./55);
		eventWeightMap.put("3000m Steeplechase",3200./55);
		eventWeightMap.put("3200m",3200./55);
		eventWeightMap.put("5000m",5000./55);
		eventWeightMap.put("8000m",8000./55);
		eventWeightMap.put("10,000m",10000./55);
		eventWeightMap.put("15,000m",15000./55);
		eventWeightMap.put("Half-marathon",21078./55);
		eventWeightMap.put("Marathon",42156./55);
	}

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
	
	public int compareTo(Athlete other) {
		double compVal = getPRSeconds()/getEventWeight(getMainEvent())-other.getPRSeconds()/getEventWeight(other.getMainEvent());
		if(compVal<0){
			return -1;
		}
		if(compVal>0){
			return 1;
		}
		return 0;
	}

	public String toString() {
		return getFirstName() + " " + getLastName() + " " + getMainEvent() + " " + getPR();
	}
	
	private double getEventWeight(String event){
		Double weight = eventWeightMap.get(event);
		if(weight!=null){
			return weight;
		}
		throw new NullPointerException();
	}

	

}
