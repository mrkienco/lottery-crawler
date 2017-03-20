package com.database.bean;

import java.sql.Date;

public class LotoBean {
private int id, cat_id;
private Date gen_date;
	private String value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public Date getGen_date() {
		return gen_date;
	}

	public void setGen_date(Date gen_date) {
		this.gen_date = gen_date;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
