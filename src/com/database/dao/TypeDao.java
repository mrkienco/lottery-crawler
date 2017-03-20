package com.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.bean.TypeBean;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class TypeDao extends SimpleJdbcDaoSupport {

	public TypeDao(JdbcConnectionPool pool) {
		super(pool);
	}

	public class TypeMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			TypeBean type = new TypeBean();
			type.setId(rs.getInt("id"));
			type.setType_id(rs.getInt("type_id"));
			type.setType_name(rs.getString("type_name"));
			return type;
		}
	}
}
