package com.javainuse.controllers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbcConnection {
	Connection connection = null;

	
	public Connection startConnection() {
		System.out.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		System.out.println("MySQL JDBC Driver Registered!");
		
		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://localhost/stockmarket","root", "");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			 e.printStackTrace();
		}
		
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
		return connection;
		
	}
	
	public Connection stopConnection() {
		
		
		return connection;
		
	}
}
