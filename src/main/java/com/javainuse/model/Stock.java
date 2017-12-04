package com.javainuse.model;

import java.util.ArrayList;

public class Stock extends ArrayList<Stock>{
	private String stockName;
	private double stockPrice;
	
	public Stock() {
	}

	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public double getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}
	
}
