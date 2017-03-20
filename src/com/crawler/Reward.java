package com.crawler;

public class Reward {
	private String value;
	private int rank;

	public Reward(String value, int rank) {
		this.rank = rank;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
