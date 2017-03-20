package com.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.database.bean.LotoBean;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class LotoDao extends SimpleJdbcDaoSupport {
	public LotoDao(JdbcConnectionPool pool) {
		super(pool);
	}

	public class LottoMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			LotoBean loto = new LotoBean();
			loto.setCat_id(rs.getInt("cat_id"));
			loto.setGen_date(rs.getDate("gen_date"));
			loto.setId(rs.getInt("id"));
			loto.setValue(rs.getString("value"));
			return loto;
		}
	}

	@SuppressWarnings("unchecked")
	public List<LotoBean> getLoto(int cat_id, String gen_date, String value) {
		String sql = "SELECT * FROM loto WHERE cat_id = " + cat_id + " AND gen_date = '" + gen_date + "' AND value = '"
				+ value + "'";
		return getSimpleJdbcTemplate().query(sql, new LottoMapper());
	}

	public void insertLoto(int cat_id, String gen_date, String value) {
		List<LotoBean> list = this.getLoto(cat_id, gen_date, value);
		if (list == null || list.isEmpty()) {
			String sql = "INSERT INTO loto(cat_id, gen_date, value) VALUES (" + cat_id + ", '" + gen_date + "','"
					+ value + "')";
			getSimpleJdbcTemplate().update(sql);
		}
	}
}
