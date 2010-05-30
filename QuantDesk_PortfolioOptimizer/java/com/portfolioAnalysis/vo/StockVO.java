package com.portfolioAnalysis.vo;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StockVO {
	private String symbol;
	private Map<Date, Double> changes = new HashMap<Date, Double>();
	private Set<Date> dates;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Map<Date, Double> getChanges() {
		return changes;
	}

	public void setChanges(Map<Date, Double> changes) {
		this.changes = changes;
	}

	public Set<Date> getDates() {
		if (dates == null) {
			dates = changes.keySet();
		}
		return dates;
	}

	public void setDates(Set<Date> dates) {

		this.dates = dates;
	}

	public void addDate(Date d) {
		dates.add(d);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changes == null) ? 0 : changes.hashCode());
		result = prime * result + ((dates == null) ? 0 : dates.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StockVO other = (StockVO) obj;
		if (changes == null) {
			if (other.changes != null)
				return false;
		} else if (!changes.equals(other.changes))
			return false;
		if (dates == null) {
			if (other.dates != null)
				return false;
		} else if (!dates.equals(other.dates))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
}
