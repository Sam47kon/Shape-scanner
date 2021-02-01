package com.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShapeServiceImpl implements ShapeService {

	private static int id = 1;
	private List<Shape> shapes;

	public ShapeServiceImpl() {
		shapes = new ArrayList<>();
		shapes.add(new Shape(String.valueOf(id++),
				Arrays.asList(new Point(-2, 1), new Point(1, 1),
						new Point(1, -2), new Point(-2, -2)),
				null, "Квадрат"));
	}

	@Override
	public List<Shape> findAll() {
		return shapes;
	}

	@Override
	public List<Shape> search(String keyword) {
		List<Shape> result = new ArrayList<>();
		if (keyword == null || "".equals(keyword)) {
			result = shapes;
		} else {
			for (Shape shape : shapes) {
				if (shape.getShapeType().toLowerCase().equals(keyword.toLowerCase().trim())) {
					result.add(shape);
				}
			}
		}
		return result;
	}
}
