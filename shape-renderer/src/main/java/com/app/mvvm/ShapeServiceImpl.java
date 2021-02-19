package com.app.mvvm;

import com.app.HttpConnect;
import com.app.model.Shape;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zul.ListModelList;

import java.io.IOException;
import java.util.List;

import static com.app.HttpConnect.requestGet;

public class ShapeServiceImpl implements ShapeService {

	public ShapeServiceImpl() {
	}

	@Override
	public List<Shape> search(String keyword) throws IOException {
		List<Shape> result = new ListModelList<>();
		if (keyword == null || "".equals(keyword)) {
			result = findAll();
		} else {
			for (Shape shape : findAll()) { // TODO findByShapeType
				if (shape.getShapeType().getTitle().toLowerCase().equals(keyword.toLowerCase().trim())) {
					result.add(shape);
				}
			}
		}
		return result;
	}

	@Override
	public void save(Shape shape) {
		// TODO
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
