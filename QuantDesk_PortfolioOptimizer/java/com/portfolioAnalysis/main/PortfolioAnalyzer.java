package com.portfolioAnalysis.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.portfolioAnalysis.bp.PortfolioBP;
import com.portfolioAnalysis.dao.StockDAO;
import com.portfolioAnalysis.vo.PortfolioVO;
import com.portfolioAnalysis.vo.StockVO;

public class PortfolioAnalyzer {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Missing Parameters!");
		} else {
			String portfolioSize = args[0];
			PortfolioBP portfolioBP = PortfolioBP.portfolioBPFactory();
			List<StockVO> stocks = new ArrayList<StockVO>();
			StockDAO dao = StockDAO.stockDAOFactory();
			for (int i = 1; i < args.length; i++) {
				stocks.add(dao.getStockbySymbol(args[i]));
			}
			StockVO[] s = new StockVO[stocks.size()];
			Set<PortfolioVO> portfolios = portfolioBP.generatePortfolios(stocks
					.toArray(s), portfolioSize);
			List<PortfolioVO> updatedPortfolios = new ArrayList<PortfolioVO>(
					portfolios.size());
			for (PortfolioVO portfolio : portfolios) {
				portfolioBP.findMaxDrawdown(portfolio);
				updatedPortfolios.add(portfolio);
			}
			Collections.sort(updatedPortfolios);
			PortfolioVO winningPortfolio = null;
			for (PortfolioVO port : updatedPortfolios) {
				if (port.getMaxDrawdown() < 0.0) {
					winningPortfolio = port;
					break;
				}
			}
			System.out
					.println("The portfolio with the smallest maximum drawdown contains stocks: ");
			StringBuilder sb = new StringBuilder();
			for (StockVO stock : winningPortfolio.getStocks()) {
				sb.append(stock.getSymbol());
				sb.append(" ");
			}
			System.out.println(sb.toString());
			System.out.println("The maximum drawdown is "
					+ winningPortfolio.getMaxDrawdown());
		}
	}
}
