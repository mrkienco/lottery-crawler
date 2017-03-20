package com.crawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Debugger;
import com.main.Main;

public class CrawlerVietlott extends Thread {
	private String url = "http://ketqua.net/xo-so-mega6x45.php?";
	private String input_date;
	private ArrayList<String> jackpot_list = new ArrayList<String>();

	public CrawlerVietlott(String date) {
		url += date;
		this.input_date = date;
	}

	public void run() {
		if (input_date.endsWith("2018"))
			return;
		Debugger.get().log(url);
		String html = "";
		while (html.equals("")) {
			try {
				html = new DefaultHttpConnection().get(url, "");
			} catch (Exception e) {
			}
		}
		Document doc = Jsoup.parse(html);
		if (html.endsWith("not available!")) {
			new CrawlerVietlott(Main.getNextDayToString(input_date)).start();
			return;
		}
		try {
			// NẾU NHƯ KHÔNG CÓ KẾT QUẢ CỦA NGÀY TRUY VẤN
			try {
				if (doc.select("p.text-danger").first().text().startsWith("Không có kết quả")) {
					new CrawlerVietlott(Main.getNextDayToString(input_date)).start();
					return;
				}
			} catch (Exception e) {
			}
			// END NẾU

			// DATE
			String date_ = doc.select("table.table").first().select("thead").first().text();
			date_ = date_.substring(date_.indexOf(',') + 2, date_.indexOf(',') + 12);

			// GET VALUE
			Elements jackpot_ = doc.select("table.table").first().select("tbody").first().select("td");
			for (Element jackpot : jackpot_) {
				jackpot_list.add(jackpot.text());
			}

			// ========== TO DATABASE ===========//
			String date = date_.substring(6, 10) + "-" + date_.substring(3, 5) + "-" + date_.substring(0, 2) + ""
					+ date_.substring(10, date_.length()) + " 18:30:00";
			for (int i = 0; i < jackpot_list.size(); i++) {
				DBEngine.get().lotteryDao.insertLottery(40, date, 1, jackpot_list.get(i));
			}

			// CRAWL NGAY HOM SAU
			new CrawlerVietlott(Main.getNextDayToString(input_date)).start();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Jsoup exception" + url);
			new CrawlerVietlott(Main.getNextDayToString(input_date)).start();
		}
	}
}
