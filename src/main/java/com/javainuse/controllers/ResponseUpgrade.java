package com.javainuse.controllers;

public class ResponseUpgrade {
private double stock_price;
private String Company_Name;
private String Buyer;
private String Seller;
private int Number_of_shares;
public double getStock_price() {
	return stock_price;
}
public void setStock_price(double stock_price) {
	this.stock_price = stock_price;
}
public String getCompany_Name() {
	return Company_Name;
}
public void setCompany_Name(String company_Name) {
	Company_Name = company_Name;
}
public String getBuyer() {
	return Buyer;
}
public void setBuyer(String buyer) {
	Buyer = buyer;
}
public String getSeller() {
	return Seller;
}
public void setSeller(String seller) {
	Seller = seller;
}
public int getNumber_of_shares() {
	return Number_of_shares;
}
public void setNumber_of_shares(int number_of_shares) {
	Number_of_shares = number_of_shares;
}
}
