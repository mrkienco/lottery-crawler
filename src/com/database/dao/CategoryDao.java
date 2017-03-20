package com.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.database.bean.CategoryBean;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class CategoryDao extends SimpleJdbcDaoSupport {
	public CategoryDao(JdbcConnectionPool pool) {
		super(pool);
	}

	public class CategoryMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			CategoryBean category = new CategoryBean();
			category.setCat_id(rs.getInt("cat_id"));
			category.setCat_name(rs.getString("cat_name"));
			category.setId(rs.getInt("id"));
			category.setType_id(rs.getInt("type_id"));
			return category;
		}
	}

	public int getCat_id(String ten_tinh) {
		String sql = "SELECT * FROM category WHERE cat_name = '" + ten_tinh + "'";
		// System.out.println(sql);
		@SuppressWarnings("unchecked")
		List<CategoryBean> row = getSimpleJdbcTemplate().query(sql, new CategoryMapper());
		CategoryBean cb = (CategoryBean) row.get(0);
		return cb.getCat_id();
	}
}
