package com.portfolioAnalysis.dao;

import java.sql.DriverManager;
import java.util.ResourceBundle;

public abstract class BaseDAO {
	private static String USER;
	private static String PASSWORD;
	private static String CONNECTIONURL;

	protected BaseDAO() {

		ResourceBundle properties = ResourceBundle
				.getBundle("com.portfolioAnalysis.resource.app-config");
		USER = properties.getString("user");
		PASSWORD = properties.getString("password");
		CONNECTIONURL = properties.getString("url");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected java.sql.Connection getConnection() {
		java.sql.Connection conn = null;
		try {
			StringBuilder url = new StringBuilder(CONNECTIONURL);
			url.append("?user=");
			url.append(USER);
			url.append("&password=");
			url.append(PASSWORD);
			conn = DriverManager.getConnection(url.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
