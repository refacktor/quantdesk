package org.zigabyte.quantdesk;
import org.yccheok.jstock.engine.StockHistoryServer;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;


public class NumericIndicator implements Indicator {
	private NumericType type;
	private int period;
	private Core core;
	
	public NumericIndicator(NumericType type, int period) {
		this.type = type;
		this.period = period;
		this.core = new Core();
	}
	
	public double getResult(StockHistoryServer s) {
		double result = 0;
		MInteger outInd = new MInteger();
		MInteger outNB = new MInteger();
		double[] prices = new double[s.getNumOfCalendar()];
		double[] output = new double[s.getNumOfCalendar()];
		for(int i = 0; i < s.getNumOfCalendar(); i++) {
			prices[i] = s.getStock(s.getCalendar(i)).getLastPrice();
		}
		switch(type) {
			case MAX:
				core.max(0, s.getNumOfCalendar() - 1, prices, period, outInd, outNB, output);
				result = output[0];
				break;
			case MIN:
				core.min(0, s.getNumOfCalendar() - 1, prices, period, outInd, outNB, output);
				result = output[0];
				break;
			case MACD:
				break;
			case MA:
				break;
		}
		return result;
	}
	
	private double getMax() {
		return 0.0;
	}
	
	private double getMin() {
		return 0.0;
	}
	
	private double getMacd() {
		return 0.0;
	}
}
