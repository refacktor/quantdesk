package com.portfolioAnalysis.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.portfolioAnalysis.vo.StockVO;

public class StockDAO extends BaseDAO {
	private static StockDAO instance;

	private StockDAO() {
		super();
	}

	public static StockDAO stockDAOFactory() {
		if (instance == null) {
			instance = new StockDAO();
		}
		return instance;
	}

	public StockVO getStockbySymbol(String symbol) {
		Connection conn = getConnection();
		ResultSet results = null;
		Statement s = null;
		StockVO stock = new StockVO();
		stock.setSymbol(symbol);
		try {
			s = conn.createStatement();
			String query = "select * from history where symbol = '"
					+ symbol + "'";
			results = s.executeQuery(query);
			while (results.next()) {
				stock.getChanges().put(results.getDate("date"),
						results.getDouble("change"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException sqlEx) {

					results = null;
				}
			}
			if (s != null) {
				try {
					s.close();
				} catch (SQLException sqlEx) {

					s = null;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					conn = null;
				}
			}
		}
		return stock;
	}
}
