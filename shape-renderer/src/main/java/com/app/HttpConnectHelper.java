package com.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpConnectHelper {
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

	public static <T, E> ResponseEntity<T> getResponse(HttpMethod method, @Nullable String path, @Nullable String id,
													   @Nullable MultiValueMap<String, String> paramsMap,
													   @Nullable E entityBody, Class<T> responseClass) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UriComponentsBuilder builder = UriComponentsBuilder.
				fromHttpUrl(HttpConnectHelper.URL + (path == null ? "" : path) + (id == null ? "" : id)).
				queryParams(paramsMap);

		ResponseEntity<T> response;
		if (entityBody == null) {
			HttpEntity<?> entity = new HttpEntity<>(headers);
			response = restTemplate.exchange(builder.toUriString(), method, entity, responseClass);
		} else {
			HttpEntity<E> entity = new HttpEntity<>(entityBody, headers);
			response = restTemplate.exchange(builder.toUriString(), method, entity, responseClass);
		}
		log.info("response.getStatusCode() {}", response.getStatusCode());
		return response;
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
