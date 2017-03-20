package com.crawler;

public class CrawlerMienNam extends Thread {
	private String date;

	public CrawlerMienNam(String date) {
		this.date = date;
	}

	public void run() {
		new CrawlerMienTrung(1, date).start();
	}
}
