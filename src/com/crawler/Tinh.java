package com.crawler;

import java.util.ArrayList;

public class Tinh {
	private String ten = "";
	private ArrayList<Reward> rewards = new ArrayList<Reward>();
	private ArrayList<String> loto = new ArrayList<String>();

	protected Tinh() {
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public ArrayList<Reward> getRewards() {
		return rewards;
	}

	public void setRewards(ArrayList<Reward> rewards) {
		this.rewards = rewards;
	}

	public ArrayList<String> getLoto() {
		return loto;
	}

	public void setLoto(ArrayList<String> loto) {
		this.loto = loto;
	}

	public void writeToDatabase(String date_) {
		if (this.ten.equals("Hồ Chí Minh"))
			this.ten = "Tp. Hồ Chí Minh";
		int cat_id = DBEngine.get().categoryDao.getCat_id(this.ten);
		String date = date_.substring(6, 10) + "-" + date_.substring(3, 5) + "-" + date_.substring(0, 2) + ""
				+ date_.substring(10, date_.length());
		// các giải
		for (int i = 0; i < rewards.size(); i++) {
			try {
				DBEngine.get().lotteryDao.insertLottery(cat_id, date, rewards.get(i).getRank(),
						rewards.get(i).getValue());
			} catch (Exception e) {
			}
		}
		// lô tô
		for (int i = 0; i < loto.size(); i++) {
			try {
				DBEngine.get().lotoDao.insertLoto(cat_id, date, loto.get(i));
			} catch (Exception e) {
			}
		}
	}

}