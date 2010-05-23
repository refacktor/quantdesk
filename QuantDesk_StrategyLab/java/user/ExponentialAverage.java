/*
 *  javastock - Java MetaStock parser and Stock Portfolio Simulator
 *  Copyright (C) 2005 Zigabyte Corporation. ALL RIGHTS RESERVED.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package user;

import com.zigabyte.metastock.data.StockDataPoint;
import com.zigabyte.stock.indicator.*;

/** Compute the average price over the last dayCount days. **/
public class ExponentialAverage extends AbstractMovingIndicator {
  //// CONSTRUCTORS
  /** @see AbstractMovingIndicator#AbstractMovingIndicator(int) **/
  public ExponentialAverage(int dayCount) {
    super(dayCount);
  }
  /** @see AbstractMovingIndicator#AbstractMovingIndicator(int,boolean) **/
  public ExponentialAverage(int dayCount, boolean countCalendarDays) {
    super(dayCount, countCalendarDays);
  }
  /** @see AbstractMovingIndicator#AbstractMovingIndicator(int,boolean,StockDataPoint.FieldID) **/
  public ExponentialAverage(int dayCount, boolean countCalendarDays,
		       StockDataPoint.FieldID fieldID) {
    super(dayCount, countCalendarDays, fieldID);
  }

  //// AbstractMovingIndicator
  /** Average is initially zero **/
  public double initialCombinationValue() {
    return 0.0;
  } 
  /** Combine by summing item values. **/
  public double combine(double combination, double itemValue) {
      if (combination == 0)
	  return itemValue; // prime with first item's value
      else
	  return (1-1.0/dayCount)*combination + (1.0/dayCount)*itemValue;
  }
  /** Finally divide combination by itemCount **/
  public double finalCombinationValue(double combination, int itemCount) {
    return combination;
  }
  //// OBJECT toString
  public String toString() {
    return super.toString("Avg");
  }
}
