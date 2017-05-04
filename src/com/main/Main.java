package com.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import com.crawler.CrawlerMienBac;
import com.crawler.CrawlerMienNam;
import com.crawler.CrawlerMienTrung;
import com.crawler.CrawlerVietlott;
import com.crawler.DBEngine;

public class Main {

	public static String host, port, user, password, dbname;
	private static String dateMienBac = "dc", dateMienTrung, dateMienNam, dateVietlott;

	public static void main(String[] args) {
		/*---------------------------------------*/
		/* ---------Connect to database--------- */
		/*---------------------------------------*/
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("DBConfig.properties");
			try {
				prop.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			host = prop.getProperty("host");
			port = prop.getProperty("port");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			dbname = prop.getProperty("dbname");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DBEngine.get().start(10, 10);

		Properties dateConfigProp = new Properties();
		InputStream dateInput = null;
		try {
			dateInput = new FileInputStream("DateConfig.properties");
			try {
				dateConfigProp.load(dateInput);
			} catch (Exception e) {
			}
			dateMienBac = dateConfigProp.getProperty("MienBac");
			dateMienTrung = dateConfigProp.getProperty("MienTrung");
			dateMienNam = dateConfigProp.getProperty("MienNam");
			dateVietlott = dateConfigProp.getProperty("Vietlott");
		} catch (Exception e) {

		}

		/*---------------------------------------*/
		/* --------Start Crawler threads-------- */
		/*---------------------------------------*/
		Debugger.get().setDebug(true);
		new CrawlerMienBac(dateMienBac).start();
		new CrawlerMienTrung(dateMienTrung).start();
		new CrawlerMienNam(dateMienNam).start();
		new CrawlerVietlott(dateVietlott).start();
	}

	public synchronized static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public synchronized static String getNextDayToString(String cur_date) {
		try {
			cur_date = cur_date.replaceAll("ngay=", "");
			cur_date.trim();
			Date dateCurDate = TimeUtils.ddMMYYYY.parse(cur_date);
			Date nexDate = new Date(dateCurDate.getTime() + TimeUtils.MILLIS_ONE_DAY);
			String next_date = TimeUtils.ddMMYYYY.format(nexDate);
			// Debugger.get().log("Cur Date " + cur_date + " Next Date " +
			// next_date);
			return "ngay=" + next_date;
		} catch (Exception ex) {
			ex.printStackTrace();
			// Debugger.get().log("Exception GetNextDayToString With date : " +
			// cur_date);
			return null;
		}
	}
}
