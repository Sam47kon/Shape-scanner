package com.app.mvvm;

import com.app.HttpConnect;
import com.app.model.Point;
import com.app.model.Shape;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.zkoss.zul.ListModelList;

import java.io.IOException;
import java.util.List;

import static com.app.HttpConnect.requestGet;

@Slf4j
public class ShapeServiceImpl implements ShapeService {

	public ShapeServiceImpl() {
	}

	@Override
	public List<Shape> search(String keyword) throws IOException {
		List<Shape> result = new ListModelList<>();
		if (keyword == null || "".equals(keyword)) {
			result = findAll();
		} else {
			for (Shape shape : findAll()) { // TODO findByShapeType (поправить контроллер бэка)
				if (shape.getShapeType().getTitle().toLowerCase().equals(keyword.toLowerCase().trim())) {
					result.add(shape);
				}
			}
		}
		return result;
	}

	@Override
	public Shape create(List<Point> points) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<Point>> entity = new HttpEntity<>(points, headers);

		ResponseEntity<Shape> response = restTemplate.exchange(HttpConnect.URL, HttpMethod.POST, entity, Shape.class);
		log.info("response.getStatusCode() {}", response.getStatusCode());
		return response.getBody();
	}

	@Override
	public Shape rotate(String id, Double rotateAngle) {
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("angle", rotateAngle.toString());
		return getShapeFromRest("rotate/", id, paramsMap);
	}

	@Override
	public Shape move(String id, Double moveX, Double moveY) {
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("x", moveX.toString());
		paramsMap.add("y", moveY.toString());
		return getShapeFromRest("move/", id, paramsMap);
	}

	@Override
	public Shape scale(String id, boolean increase, Double scale) {
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("scale", scale.toString());
		return getShapeFromRest(increase ? "increase/" : "reduce/", id, paramsMap);
	}

	private Shape getShapeFromRest(String path, String id, MultiValueMap<String, String> paramsMap) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UriComponentsBuilder builder = UriComponentsBuilder.
				fromHttpUrl(HttpConnect.URL + path + id).
				queryParams(paramsMap);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<Shape> response = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity, Shape.class);
		log.info("response.getStatusCode() {}", response.getStatusCode());
		return response.getBody();
	}

	@Override
	public void delete(Shape shape) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(HttpConnect.URL + shape.getId());
	}

	private List<Shape> findAll() throws IOException {
//		ListModelList<Shape> result = new ListModelList<>();
//		result.addAll(getResponse());
//		return result;
		return new Gson().<ListModelList<Shape>>fromJson(
				requestGet(),
				new TypeToken<ListModelList<Shape>>() {
				}.getType());
	}
}
