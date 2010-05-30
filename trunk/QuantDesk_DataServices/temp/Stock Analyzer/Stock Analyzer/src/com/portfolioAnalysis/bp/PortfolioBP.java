package com.portfolioAnalysis.bp;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.portfolioAnalysis.vo.PortfolioVO;
import com.portfolioAnalysis.vo.StockVO;

public class PortfolioBP {
	private static PortfolioBP instance;

	private PortfolioBP() {
	}

	public static PortfolioBP portfolioBPFactory() {
		if (instance == null) {
			instance = new PortfolioBP();
		}
		return instance;
	}

	// utilizes textbook algorithm for generating powersets
	public Set<PortfolioVO> generatePortfolios(StockVO[] stocks, String size) {
		int targetSize = Integer.parseInt(size);
		Set<PortfolioVO> powerSet = new LinkedHashSet<PortfolioVO>();
		int numberOfStocks = stocks.length;
		long numberOfCombinations = (long) Math.pow(2, numberOfStocks);
		for (int i = 0; i < numberOfCombinations; i++) {
			String binaryRepresentation = intToBinaryString(i, numberOfStocks);
			LinkedHashSet<StockVO> portfolioSet = new LinkedHashSet<StockVO>();
			for (int j = 0; j < binaryRepresentation.length(); j++) {
				if (binaryRepresentation.charAt(j) == '1') {
					portfolioSet.add(stocks[j]);
				}
			}
			if (portfolioSet.size() == targetSize) {
				powerSet.add(new PortfolioVO(portfolioSet));
			}
		}
		return powerSet;
	}

	private String intToBinaryString(int toConvert, int size) {
		String temp = Integer.toBinaryString(toConvert);
		int givenDigits = temp.length();
		StringBuilder sb = new StringBuilder();
		for (int i = givenDigits; i < size; i++) {
			sb.append("0");
		}
		sb.append(temp);
		return sb.toString();
	}

	public void findMaxDrawdown(PortfolioVO portfolio) {
		StockVO stock = null;
		for (StockVO iter : portfolio.getStocks()) {
			stock = iter;
			break;
		}
		if (stock != null) {
			Set<Date> dates = stock.getDates();
			for (Date currentDay : dates) {
				double drawdown = 0.0;
				if (isDataAvailable(portfolio, currentDay)) {
					for (StockVO currentStock : portfolio.getStocks()) {
						drawdown += currentStock.getChanges().get(currentDay);
					}
					if (portfolio.getMaxDrawdown() > drawdown && drawdown < 0) {
						portfolio.setMaxDrawdown(drawdown);
					}
				}
			}
		}
	}

	private boolean isDataAvailable(PortfolioVO portfolio, Date date) {
		for (StockVO stock : portfolio.getStocks()) {
			if (!stock.getChanges().containsKey(date)) {
				return false;
			}
		}
		return true;
	}
}
