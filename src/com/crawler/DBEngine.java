package com.crawler;

import com.database.dao.CategoryDao;
import com.database.dao.LotoDao;
import com.database.dao.LotteryDao;
import com.database.dao.TypeDao;
import com.main.Main;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.JdbcParameter;

public class DBEngine {

	private static DBEngine INSTANCE;
	/*--------Database Config------------*/
	private String dbAddr;
	private String dbPort;
	private String dbUser;
	private String dbPass;
	private String dbName;
	/*--------DAO------------------------*/
	public CategoryDao categoryDao;
	public LotoDao lotoDao;
	public LotteryDao lotteryDao;
	public TypeDao typeDao;
	/*---------Object Fields-------------*/
	private JdbcConnectionPool pool;
	private JdbcParameter parameter;

	public static DBEngine get() {
		if (INSTANCE == null) {
			INSTANCE = new DBEngine();
		}
		return INSTANCE;
	}

	public void start(int maxConn, int clearPeriod) {
		try {
			this.dbAddr = Main.host;
			this.dbPort = Main.port;
			this.dbUser = Main.user;
			this.dbPass = Main.password;
			this.dbName = Main.dbname;
			// this.maxConn = maxConn;
			// this.clearPeriod = clearPeriod;

			this.parameter = new JdbcParameter();
			this.parameter.setUrl("jdbc:mysql://" + dbAddr + ":" + dbPort + "/" + dbName);
			this.parameter.setUsername(dbUser);
			this.parameter.setPassword(dbPass);
			this.parameter.setMaxConn(maxConn);
			this.parameter.setClearPeriod(clearPeriod);

			this.pool = new JdbcConnectionPool(parameter);

			this.categoryDao = new CategoryDao(pool);
			this.lotoDao = new LotoDao(pool);
			this.lotteryDao = new LotteryDao(pool);
			this.typeDao = new TypeDao(pool);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
