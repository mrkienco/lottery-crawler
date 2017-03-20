package com.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultHttpConnection {

	public String get(String serviceUrl, String params) {
		String response = "";
		HttpURLConnection connection = null;
		URL serverAddress = null;
		try {			
			if (params != null && params.length() > 0) {
				serviceUrl += "?" + params;
			}
			serviceUrl = serviceUrl.replaceAll(" ", "%20");
			serverAddress = new URL(serviceUrl);
			connection = null;

			// Set up the initial connection
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
//			connection.setRequestProperty("Accept-Encoding", "gzip");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setReadTimeout(60000);
			connection.setUseCaches(false);
			connection.connect();
			BufferedReader in;
//			if (connection.getContentEncoding().equalsIgnoreCase("gzip")) {
//				in = new BufferedReader(new InputStreamReader(
//	                    new GZIPInputStream(connection.getInputStream())));
//			} else {
				in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
//			}
			String inputLine;
            StringBuffer res = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                res.append(inputLine);
            }
            in.close();
            response = res.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	public String post(String serviceUrl, String params) {
		HttpURLConnection connection = null;
        URL serverAddress = null;
        DataInputStream input = null;
        //DataOutputStream output = null;
        String response = "";
        try {
            serviceUrl = serviceUrl.replaceAll(" ", "%20");
            serverAddress = new URL(serviceUrl);
            //set up out communications stuff
            connection = null;

            //Set up the initial connection
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setReadTimeout(60000);
            connection.setUseCaches(false);
            connection.connect();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),
                    "UTF-8"));
            //output = new DataOutputStream(connection.getOutputStream());
            writer.write(params);
            writer.flush();
            writer.close();
            
            input = new DataInputStream(connection.getInputStream());
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(input, "UTF-8"));
            String inputLine;
            StringBuffer res = new StringBuffer();

            while ((inputLine = rd.readLine()) != null) {
                res.append(inputLine);
            }
            input.close();
            response = res.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
	}

}
