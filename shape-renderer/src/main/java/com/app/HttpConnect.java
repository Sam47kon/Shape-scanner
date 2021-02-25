package com.app;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpConnect {
	public static final String URL = "http://127.0.0.1:8080/shapes/";
	private static HttpURLConnection CONNECTION;


	private static HttpURLConnection getConnection() {
		try {
			CONNECTION = (HttpURLConnection) new URL(URL).openConnection();
			CONNECTION.setRequestProperty("Content-Type", "application/json");
			CONNECTION.setRequestProperty("charset", "utf-8");
			CONNECTION.setConnectTimeout(5000);
			CONNECTION.setReadTimeout(5000);
		} catch (IOException e) {
			log.error("Не удалось соединиться с " + URL);
			e.printStackTrace();
		}
		return CONNECTION;
	}

	public static String requestGet() throws IOException {
		HttpURLConnection connection = getConnection();
		connection.setRequestMethod("GET");

		InputStream in = new BufferedInputStream(connection.getInputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder result = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		in.close();
		reader.close();
		connection.disconnect();
		return result.toString();
	}
}
