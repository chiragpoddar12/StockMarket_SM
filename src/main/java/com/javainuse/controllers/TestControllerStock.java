package com.javainuse.controllers;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import com.javainuse.model.Company;

@Controller
@RestController
public class TestControllerStock {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping(value = "/stockUpdates", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<String>> StockUpdates() throws JSONException {

		List<String> allStocks = new ArrayList<String>();
		JSONObject json = new JSONObject();
		jdbcConnection jdbc = new jdbcConnection();
		Connection connection = jdbc.startConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from stockprice");
			ResultSet rs = (ResultSet) stmt.executeQuery();
			while (rs.next()) {
				json.put("stockName", rs.getString(2));
				json.put("stockPrice", rs.getString(3));
				System.out.println(json);
				allStocks.add(json.toString());
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<List<String>>(allStocks, responseHeaders, HttpStatus.OK);
		// return json.toString();
	}

	@RequestMapping(value = "/stockMarket", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseUpgrade> update(@RequestBody ArrayList<ResponseUpgrade> response) {
		if (response != null) {
			for (int i = 0; i < response.size(); i++) {
				System.out.println("Inhere");
				jdbcConnection jdbc = new jdbcConnection();
				Connection conn = jdbc.startConnection();

				PreparedStatement stmt;
				try {
					stmt = conn.prepareStatement("UPDATE stockprice SET StockPrice=" + response.get(i).getstockPrice()
							+ " WHERE StockName='" + response.get(i).getcompanyName() + "'");

					int j = stmt.executeUpdate();
					System.out.println(j + " records updated");
					conn.close();
				} catch (Exception e) {
					System.out.println("No Such Company Found");
				}
				try {

					postToBank(response.get(i));

				} catch (Exception e) {
					System.out.println("Error in connection with bank");
					e.printStackTrace();
				}
				try {
					List<ServiceInstance> instances = discoveryClient.getInstances(response.get(i).getcompanyName().toString());
					ServiceInstance serviceInstance = instances.get(0);
					String baseUrl = serviceInstance.getUri().toString();
					baseUrl += "/"+response.get(i).getcompanyName().toString();
					ResponseEntity<?> response1 = new RestTemplate().postForEntity(baseUrl, response.get(i), String.class);
					System.out.println(response1.getStatusCodeValue());
					
				} catch (Exception e) {
					System.out.println("Error in connection with company");
					e.printStackTrace();
				}
			}

		}
		return new ResponseEntity<ResponseUpgrade>(HttpStatus.OK);
	}
	private void postToBank(ResponseUpgrade response) {
		System.out.println("First");
		System.out.println(discoveryClient);
		List<ServiceInstance> instances = discoveryClient.getInstances("Bank");
		System.out.println("HERE " +instances);
		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString();
		System.out.println(baseUrl);
		baseUrl += "/Bank";
		ResponseEntity<?> response1 = new RestTemplate().postForEntity(baseUrl, response, String.class);
		System.out.println(response1.getStatusCodeValue());

	}

}
