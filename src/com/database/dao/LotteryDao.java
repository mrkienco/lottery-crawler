package com.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.database.bean.LotteryBean;
import com.main.Debugger;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class LotteryDao extends SimpleJdbcDaoSupport {

	public LotteryDao(JdbcConnectionPool pool) {
		super(pool);
	}

	public class LotteryMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			LotteryBean lottery = new LotteryBean();
			lottery.setCat_id(rs.getInt("cat_id"));
			lottery.setGen_date(rs.getDate("gen_date"));
			lottery.setId(rs.getInt("id"));
			lottery.setRank(rs.getInt("rank"));
			lottery.setValue(rs.getString("value"));
			return lottery;
		}
	}

	@SuppressWarnings("unchecked")
	public List<LotteryBean> getLottery(int cat_id, int rank, String value, String gen_date) {
		String sql = "SELECT * FROM lottery WHERE cat_id = " + cat_id + " AND rank = " + rank + " AND value = '" + value
				+ "' AND gen_date = '" + gen_date + "'";
		return getSimpleJdbcTemplate().query(sql, new LotteryMapper());
	}

	public void insertLottery(int cat_id, String gen_date, int rank, String value) {
		List<LotteryBean> list = this.getLottery(cat_id, rank, value, gen_date);
		if (list == null || list.isEmpty()) {
			String sql = "INSERT INTO lottery(cat_id,gen_date,rank,value) VALUES ('" + cat_id + "','" + gen_date + "',"
					+ rank + ",'" + value + "')";
			getSimpleJdbcTemplate().update(sql);
		}
	}
}
