package com.crawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Debugger;
import com.main.Main;

public class CrawlerMienBac extends Thread {
	private String url = "http://ketqua.net/xo-so-mien-bac.php?";
	private String input_date;
	private ArrayList<Reward> ketqua = new ArrayList<Reward>();
	private ArrayList<String> loto = new ArrayList<String>();
	private ArrayList<String> dt123 = new ArrayList<String>();
	private ArrayList<String> thantai = new ArrayList<String>();
	private ArrayList<String> dt6x36 = new ArrayList<String>();

	public CrawlerMienBac(String date) {
		input_date = date;
		this.url += date;
	}

	public void run() {
		if (input_date.endsWith("2018"))
			return;
		Debugger.get().log(url);
		Document doc = null;
		DefaultHttpConnection httpConnect = new DefaultHttpConnection();
		String html = "";
		while (html.equals("")) {
			try {
				html = httpConnect.get(url, "");
			} catch (Exception e) {
				System.err.println(this.url + " exception. #1");
			}
		}
		doc = Jsoup.parse(html);
		String date_ = "";
		try {
			Element all = doc.select("div.col-sm-5").first();

			// ============================================//
			/* ----------- Xo so truyen thong ----------- */
			// ============================================//
			Element truyenThong = all.select("div.result_div").first();

			// Date
			date_ = truyenThong.select("thead").first().select("td").first().text();
			date_ = date_.substring(date_.length() - 10, date_.length());
			if (date_.endsWith("ngày")) {
				new CrawlerMienBac(Main.getNextDayToString(this.input_date)).start();
				return;
			}

			// Chi tiet cac giai thuong //
			Elements giaiTruyenThong;
			try {
				giaiTruyenThong = truyenThong.select("tbody").first().select("tr");
			} catch (Exception e) {
				System.err.println(this.url + " exception #2.");
				new CrawlerMienBac(Main.getNextDayToString(this.input_date)).start();
				return;
			}
			int rank = -1;
			for (Element giaiIndex : giaiTruyenThong) {
				String value = "";
				Elements chiTiet = giaiIndex.select("td");
				for (Element td : chiTiet) {
					switch (td.text()) {
					case "Đặc biệt": {
						rank = 0;
						break;
					}
					case "Giải nhất": {
						rank = 1;
						break;
					}
					case "Giải nhì": {
						rank = 2;
						break;
					}
					case "Giải ba": {
						rank = 3;
						break;
					}
					case "Giải tư": {
						rank = 4;
						break;
					}
					case "Giải năm": {
						rank = 5;
						break;
					}
					case "Giải sáu": {
						rank = 6;
						break;
					}
					case "Giải bảy": {
						rank = 7;
						break;
					}
					default: {
						value = td.text();
						break;
					}
					}
					if (Main.isNumeric(value))
						ketqua.add(new Reward(value, rank));
				}
			}

			// Lo to truc tiep //
			try {
				Elements lotoTrucTiep = all.select("table.table").get(1).select("td");
				for (Element lotoIndex : lotoTrucTiep) {
					// ===============================// Loto
					String loto_str = lotoIndex.text();
					if (Main.isNumeric(loto_str))
						loto.add(loto_str);
				}
			} catch (Exception e) {
				System.err.println(this.url + " exception. #3");
			}

			// ============================================//
			/* -------------- Dien toan 123 -------------- */
			// ============================================//
			try {
				Elements dienToan123 = all.select("div");
				for (Element dt : dienToan123) {
					if (dt.attr("id").equals("outer_result_123")) {
						Elements ketqua123_ = dt.select("tbody").first().select("td");
						for (Element ketqua123 : ketqua123_) {
							if (Main.isNumeric(ketqua123.text()))
								dt123.add(ketqua123.text());
						}
					}
				}
			} catch (Exception e) {
				System.err.println("dien_toan_123 exception");
			}

			// ============================================//
			/* -------------- Dien toan 6x36 ------------- */
			// ============================================//
			try {
				Elements dienToan6x36 = all.select("div");
				for (Element dt : dienToan6x36) {
					if (dt.attr("id").equals("outer_result_636")) {
						Elements ketqua636_ = dt.select("tbody").first().select("td");
						for (Element k : ketqua636_) {
							if (Main.isNumeric(k.text()))
								dt6x36.add(k.text());
						}
					}
				}
			} catch (Exception e) {
				System.err.println("6x36 exception");
			}

			// ============================================//
			/* ------------- Xo so than tai -------------- */
			// ============================================//
			try {
				Elements thanTai = all.select("div");
				for (Element dt : thanTai) {
					if (dt.attr("id").equals("outer_result_tt4")) {
						Elements tt_ = dt.select("tbody").first().select("td");
						for (Element tt : tt_)
							if (Main.isNumeric(tt.text()))
								thantai.add(tt.text());
					}
				}
			} catch (Exception e) {
				System.err.println("than_tai exception");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("jsoup exception Mien Bac " + this.input_date);
		}

		// =============================================//
		// ================ TO DATABASE ================//
		// =============================================//

		String date = "";
		try {
			date = date_.substring(6, 10) + "-" + date_.substring(3, 5) + "-" + date_.substring(0, 2) + ""
					+ date_.substring(10, date_.length()) + " 18:30:00";
		} catch (Exception e) {
		}

		// Kết quả trực tiếp
		for (int i = 0; i < ketqua.size(); i++) {
			try {
				DBEngine.get().lotteryDao.insertLottery(1, date, ketqua.get(i).getRank(), ketqua.get(i).getValue());
			} catch (Exception e) {
			}
		}

		// Lô tô
		for (int i = 0; i < loto.size(); i++) {
			try {
				DBEngine.get().lotoDao.insertLoto(1, date, loto.get(i));
			} catch (Exception e) {
			}
		}

		// Điện toán 123
		for (int i = 0; i < dt123.size(); i++) {
			try {
				DBEngine.get().lotteryDao.insertLottery(2, date, 1, dt123.get(i));
			} catch (Exception e) {
			}
		}

		// Xổ số thần tài
		for (int i = 0; i < thantai.size(); i++) {
			DBEngine.get().lotteryDao.insertLottery(4, date, 1, thantai.get(i));
		}

		// Xổ số 6x36
		for (int i = 0; i < dt6x36.size(); i++) {
			DBEngine.get().lotteryDao.insertLottery(3, date, 1, dt6x36.get(i));
		}

		// Crawl ngay hom sau
		new CrawlerMienBac(Main.getNextDayToString(this.input_date)).start();
	}
}
