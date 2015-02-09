package edu.rosehulman.coachesracetimer;

public class Athlete {

	private String mName;
	private int mRank;
	private long mId;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public int getRank() {
		return mRank;
	}

	public void setRank(int rank) {
		mRank = rank;
	}

	public int compareTo(Athlete other) {
		return other.getRank() - getRank();
	}

	public String toString() {
		return getName() + " " + getRank();
	}

}
