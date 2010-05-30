package com.portfolioAnalysis.vo;

import java.util.LinkedHashSet;
import java.util.Set;

public class PortfolioVO implements Comparable<PortfolioVO> {
	LinkedHashSet<StockVO> stocks = new LinkedHashSet<StockVO>(); //
	Double maxDrawdown = 0.0;

	public PortfolioVO(Set<StockVO> stocks) {
		this.stocks.addAll(stocks);
	}

	public LinkedHashSet<StockVO> getStocks() {
		return stocks;
	}

	public void setStocks(LinkedHashSet<StockVO> stocks) {
		this.stocks = stocks;
	}

	public Double getMaxDrawdown() {
		return maxDrawdown;
	}

	public void setMaxDrawdown(Double maxDrawdown) {
		this.maxDrawdown = maxDrawdown;
	}

	public int compareTo(PortfolioVO o) {
		return o.getMaxDrawdown().compareTo(maxDrawdown);
	}
}
