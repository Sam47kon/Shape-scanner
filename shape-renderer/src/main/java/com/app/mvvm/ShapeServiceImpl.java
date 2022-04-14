package com.app.mvvm;

import com.app.HttpConnectHelper;
import com.app.model.Point;
import com.app.model.Shape;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zul.ListModelList;

import java.io.IOException;
import java.util.List;

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
		ResponseEntity<Shape> response = HttpConnectHelper.getResponse(HttpMethod.POST, null, null, null, points, Shape.class);
		return response.getBody();
	}

	@Override
	public Shape rotate(String id, Double rotateAngle) {
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("angle", rotateAngle.toString());
		return HttpConnectHelper.getResponse(HttpMethod.PUT, "rotate/", id, paramsMap, null, Shape.class).getBody();
	}

	@Override
	public Shape move(String id, Double moveX, Double moveY) {
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("x", moveX.toString());
		paramsMap.add("y", moveY.toString());
		return HttpConnectHelper.getResponse(HttpMethod.PUT, "move/", id, paramsMap, null, Shape.class).getBody();
	}

	@Override
	public Shape scale(String id, boolean increase, Double scale) {
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("scale", scale.toString());
		return HttpConnectHelper.getResponse(HttpMethod.PUT, increase ? "increase/" : "reduce/", id, paramsMap, null, Shape.class).getBody();
	}

	@Override
	public void delete(Shape shape) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(HttpConnectHelper.URL + shape.getId());
	}

	private List<Shape> findAll() throws IOException {
		return new Gson().<ListModelList<Shape>>fromJson(
				HttpConnectHelper.requestGet(),
				new TypeToken<ListModelList<Shape>>() {
				}.getType());
	}
}
