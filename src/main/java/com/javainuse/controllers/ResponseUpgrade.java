package com.javainuse.controllers;

public class ResponseUpgrade {
private double stockPrice;
private String companyName;
private String buyerName;
private String sellerName;
private int numberOfShares;
public double getstockPrice() {
	return stockPrice;
}
public void setstockPrice(double stockPrice) {
	this.stockPrice = stockPrice;
}
public String getcompanyName() {
	return companyName;
}
public void setcompanyName(String companyName) {
	this.companyName = companyName;
}
public String getbuyerName() {
	return buyerName;
}
public void setbuyerName(String buyerName) {
	this.buyerName = buyerName;
}
public String getsellerName() {
	return sellerName;
}
public void setsellerName(String sellerName) {
	this.sellerName = sellerName;
}
public int getnumberOfShares() {
	return numberOfShares;
}
public void setnumberOfShares(int numberOfShares) {
	this.numberOfShares = numberOfShares;
}
}
