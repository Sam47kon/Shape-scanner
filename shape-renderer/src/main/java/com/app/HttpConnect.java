package com.app;

import com.app.model.Shape;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zul.ListModelList;

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
//		Map<String, String> parameters = new HashMap<>();
//		parameters.put("id", "123");
//		connection.setDoOutput(true);
//		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//		out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
//		out.flush();
//		out.close();
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

	// FIXME Приходит объект Shape с бэка, не конвертится в Shape
//	@SuppressWarnings("unchecked")
	public static ListModelList<Shape> getResponse() throws JsonProcessingException {
		RestTemplate template = new RestTemplate();
		ResponseEntity<ListModelList> response = template.getForEntity(URL, ListModelList.class);

//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode root = mapper.readTree(String.valueOf(response.getBody()));
//		JsonNode name = root.path("name");
//		System.out.println("name = " + name);
		return template.getForObject(URL, ListModelList.class);
	}
}
