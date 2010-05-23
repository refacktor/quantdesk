package com.zigabyte.metastock.sample;

import com.zigabyte.metastock.data.*;
import com.zigabyte.metastock.parser.metastock.MetastockParser;

import org.jfree.chart.*;
import org.jfree.data.*;
import org.jfree.data.xy.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.io.*;
import java.text.*;
import java.util.*;

/*******************************************************************************
 * Runs {@link MetastockParser} to parse Metastock files under a root directory,
 * then lists the stocks
 ******************************************************************************/
public class ListSymbols {

	public static void main(String[] parameters) throws IOException {

		if (parameters.length == 0) {
			parameters = new String[] {
					"R:\\", "AMEX", "NASDAQ", "NYSE"
			};
		} 
		
		File metastockDir = new File(parameters[0]);
		
		File fOut = new File(metastockDir, "symbols.txt");
		System.out.println("Writing output to " + fOut.getCanonicalPath());
		FileOutputStream fos = new FileOutputStream(fOut);
		PrintStream out = new PrintStream(fos);
		int count = 0;

		for(int p = 1; p < parameters.length; ++p) {
			String exchange = parameters[p];
			File exchangeDir = new File(metastockDir, exchange);
			int xcount = 0;
			
			// Create a parser which saves memory (assumes prices to nearest cent).
			StockMarketHistoryFactory factory = new MetastockParser(true);

			StockMarketHistory stockMarketHistory = factory.loadHistory(exchangeDir);

			for(Iterator<StockHistory> i = stockMarketHistory.iterator(); i.hasNext();) {
				StockHistory sh = i.next();
				out.println(sh.getSymbol() + "," + sh.getName() + "," + exchange);
				count++;
				xcount++;
			}
			System.out.println(exchange + ": " + xcount + " symbols");
		}
		
		out.close();
		fos.close();
		
		System.out.println("Wrote " + count + " symbols.");
	}
}
