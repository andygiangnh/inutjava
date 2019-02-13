package com.mkyong.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// mk inut12345
public class NetClientPost {

	// http://localhost:8080/RESTfulExample/json/product/post
	public static void main(String[] args) {
		int hoi1 = 2, hoi2 = 0, delay = 0; // default toggle for 2 sec
		if(args.length >= 1) {
			hoi1 = Integer.parseInt(args[0]);
		}
		if(args.length == 3) {			
			hoi2 = Integer.parseInt(args[1]);
			delay = Integer.parseInt(args[2]);
		}
		
		// Hoi chuong 1st
		if(hoi1 > 0) {
			sendInutCmd(); // turn on
			delaySecond(hoi1);
			sendInutCmd(); // turn off
		}

		if(delay > 0) {
			delaySecond(delay);
		}
		
		if(hoi2 > 0) {
			sendInutCmd(); // turn on
			delaySecond(hoi2);
			sendInutCmd(); // turn off
		}
	}
	
	private static void delaySecond(int sec) {
		try {
			Thread.sleep(1000 * sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Xuong 1 Phu Thi: https://connect.mysmarthome.vn/api/1.0/request/
	// GmKUmVREXgMdLhVd1o8LgqJazaH2/
	// H1dTC_Jv7/3ecba306d4200b70e5c2bdb52473255c307024238592/
	// req_device_toggle
	// Xuong 2 Phu Tho: https://connect.mysmarthome.vn/api/1.0/request/
	// GmKUmVREXgMdLhVd1o8LgqJazaH2/
	// ByaN2SAHX/1ec33704879e21f39292675f3c5d3ba9953049213925/req_device_toggle
	
	private static void sendInutCmd() {
		String[][] urlArr = {
				{"https://connect.mysmarthome.vn/api/1.0/request/",
					"GmKUmVREXgMdLhVd1o8LgqJazaH2/ByaN2SAHX/",
					"1ec33704879e21f39292675f3c5d3ba9953049213925/"}
				,
				{"https://connect.mysmarthome.vn/api/1.0/request/",
					"GmKUmVREXgMdLhVd1o8LgqJazaH2/H1dTC_Jv7/",
					"3ecba306d4200b70e5c2bdb52473255c307024238592/"}
				};
		
		try {
			int x1 = 0;
			int x2 = 1;
			
			String apiStr = urlArr[1][0]
					+ urlArr[1][1]
					+ urlArr[1][2]
					+ "req_device_toggle";

			URL url = new URL(apiStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"id\": 0,\"command\": \"TOGGLE\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}