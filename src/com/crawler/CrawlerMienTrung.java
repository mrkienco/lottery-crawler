package com.crawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Debugger;
import com.main.Main;

public class CrawlerMienTrung extends Thread {
	private String url = "http://ketqua.net/";
	private String input_date;
	private boolean mienNam = false;

	public CrawlerMienTrung(String date) {
		url = url + "xo-so-mien-trung.php?" + date;
		input_date = date;
	}

	public CrawlerMienTrung(int MIEN_NAM, String date) {
		this.mienNam = true;
		url = url + "xo-so-mien-nam.php?" + date;
		input_date = date;
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
				System.err.println(url + "Exception #1");
			}
		}
		Document doc = Jsoup.parse(html);
		try {
			Elements all = doc.select("div.col-sm-5");
			ArrayList<Tinh> tinh_array = new ArrayList<Tinh>();
			/* ============================== */
			/* =========== Ket qua ========== */
			/* ============================== */
			String date = all.select("table.table").first().select("thead").first().select("td.dosam").text();
			date = date.substring(date.length() - 10, date.length());
			if (date.endsWith("ngày")) {
				if (mienNam)
					new CrawlerMienNam(Main.getNextDayToString(input_date)).start();
				else
					new CrawlerMienTrung(Main.getNextDayToString(input_date)).start();
				return;
			}
			try {
				Elements cacTinh = all.select("table.table").first().select("thead").first().select("tr.info").first()
						.select("td");
				int soTinhQuay = cacTinh.size() - 1;
				for (int i = 1; i < cacTinh.size(); i++) {
					Tinh tinh = new Tinh();
					tinh.setTen(cacTinh.get(i).text());
					tinh_array.add(tinh);
				}

				Elements cacGiai = all.select("table.table").first().select("tbody").first().select("td");
				// if Khong co du lieu de crawl
				if (cacGiai.size() < 10) {
					if (mienNam)
						new CrawlerMienNam(Main.getNextDayToString(input_date)).start();
					else
						new CrawlerMienTrung(Main.getNextDayToString(input_date)).start();
					return;
				}
				// endif
				int rank = -1;
				String value = "";
				int index = 0;
				for (Element c : cacGiai) {
					switch (c.text()) {
					case "Giải tám":
						rank = 8;
						break;
					case "Giải bảy":
						rank = 7;
						break;
					case "Giải sáu":
						rank = 6;
						break;
					case "Giải năm":
						rank = 5;
						break;
					case "Giải tư":
						rank = 4;
						break;
					case "Giải ba":
						rank = 3;
						break;
					case "Giải nhì":
						rank = 2;
						break;
					case "Giải nhất":
						rank = 1;
						break;
					case "Đặc biệt":
						rank = 0;
						break;
					default:
						value = c.text();
						if (Main.isNumeric(value))
							tinh_array.get(index % soTinhQuay).getRewards().add(new Reward(value, rank));
						index++;
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(url + "Exception #2.");
			}

			/* ============================== */
			/* ============= Lo to ========== */
			/* ============================== */
			try {
				int index = 0;
				Elements lttt_ = all.select("div.tab-pane");
				for (Element lttt : lttt_) {
					Elements loto_ = lttt.select("table.table").first().select("tbody").first().select("td");
					for (Element loto : loto_) {
						if (Main.isNumeric(loto.text()))
							tinh_array.get(index).getLoto().add(loto.text());
					}
					index++;
				}

			} catch (Exception e) {
				if (mienNam)
					new CrawlerMienNam(Main.getNextDayToString(input_date)).start();
				else
					new CrawlerMienTrung(Main.getNextDayToString(input_date)).start();
				return;
			}

			// Print stream
			for (int i = 0; i < tinh_array.size(); i++) {
				tinh_array.get(i).writeToDatabase(date + " 18:30:00");
			}

			// CRAWL NGAY HOM SAU
			if (mienNam)
				new CrawlerMienNam(Main.getNextDayToString(input_date)).start();
			else
				new CrawlerMienTrung(Main.getNextDayToString(input_date)).start();

		} catch (Exception e) {
			System.err.println(url + "Exception #3");
			if (mienNam)
				new CrawlerMienNam(Main.getNextDayToString(input_date)).start();
			else
				new CrawlerMienTrung(Main.getNextDayToString(input_date)).start();
		}
	}
}
