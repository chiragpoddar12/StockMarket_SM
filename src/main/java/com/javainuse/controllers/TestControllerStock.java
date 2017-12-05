package com.javainuse.controllers;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		System.out.println("I am here");
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
				} catch (Exception e) {
					System.out.println("No Such Company Found");
				}
				try {

					postToBank(response.get(i));

				} catch (Exception e) {
					System.out.println("Error in connection with bank");
				}
				try {
					List<ServiceInstance> instances = discoveryClient.getInstances(response.get(i).getcompanyName().toString());
					ServiceInstance serviceInstance = instances.get(0);
					String baseUrl = serviceInstance.getUri().toString();
					System.out.println(baseUrl);
					ResponseEntity<?> response1 = new RestTemplate().postForEntity(baseUrl, response, String.class);
					System.out.println(response1.getStatusCodeValue());
					
				} catch (Exception e) {
					System.out.println("Error in connection with company");
				}
			}

		}
		return new ResponseEntity<ResponseUpgrade>(HttpStatus.OK);
	}

	

	/*private void postToCompany(ResponseUpgrade response) {
		// First need to get instance of the company and den send the post accordingly
		switch (response.getcompanyName().toString()) {
		case "companyA":
			
		case "companyB":
			try {
				
			} catch (Exception e) {
				System.out.println("Either the company is not active or is Unregistered");
				break;
			}
		case "companyC":
			try {
				List<ServiceInstance> instances = discoveryClient.getInstances(response.getcompanyName().toString());
				ServiceInstance serviceInstance = instances.get(0);
				String baseUrl = serviceInstance.getUri().toString();
				ResponseEntity<?> response1 = new RestTemplate().postForEntity(baseUrl, response, String.class);
				System.out.println(response1.getStatusCodeValue());
				break;
			} catch (Exception e) {
				System.out.println("Either the company is not active or is Unregistered");
				break;
			}
		case "companyD":
			try {
				List<ServiceInstance> instances = discoveryClient.getInstances(response.getcompanyName().toString());
				ServiceInstance serviceInstance = instances.get(0);
				String baseUrl = serviceInstance.getUri().toString();
				ResponseEntity<?> response1 = new RestTemplate().postForEntity(baseUrl, response, String.class);
				System.out.println(response1.getStatusCodeValue());
				break;
			} catch (Exception e) {
				System.out.println("Either the company is not active or is Unregistered");
				break;
			}
		case "companyE":
			try {
				List<ServiceInstance> instances = discoveryClient.getInstances(response.getcompanyName().toString());
				ServiceInstance serviceInstance = instances.get(0);
				String baseUrl = serviceInstance.getUri().toString();
				ResponseEntity<?> response1 = new RestTemplate().postForEntity(baseUrl, response, String.class);
				System.out.println(response1.getStatusCodeValue());
				break;
			} catch (Exception e) {
				System.out.println("Either the company is not active or is Unregistered");
				break;
			}
		case "companyF":
			try {
				List<ServiceInstance> instances = discoveryClient.getInstances(response.getcompanyName().toString());
				ServiceInstance serviceInstance = instances.get(0);
				String baseUrl = serviceInstance.getUri().toString();
				ResponseEntity<?> response1 = new RestTemplate().postForEntity(baseUrl, response, String.class);
				System.out.println(response1.getStatusCodeValue());
				break;
			} catch (Exception e) {
				System.out.println("Either the company is not active or is Unregistered");
				break;
			}
		default:
			System.out.println("No such Company Found");

		}
		/*
		 * try { String url = "http://localhost:8095/" +
		 * response.getCompany_Name().toString() + "/"; ResponseEntity<?> response1 =
		 * new RestTemplate().postForEntity(url, response, String.class);
		 * System.out.println(response1.getStatusCodeValue()); } catch (Exception e) {
		 * System.out.println("Either the company is not active or is Unregistered"); }
		 *

	}*/

	private void postToBank(ResponseUpgrade response) {
		List<ServiceInstance> instances = discoveryClient.getInstances("bank");
		ServiceInstance serviceInstance = instances.get(0);
		String baseUrl = serviceInstance.getUri().toString();
		ResponseEntity<?> response1 = new RestTemplate().postForEntity(baseUrl, response, String.class);
		System.out.println(response1.getStatusCodeValue());

	}

}
