package com.app.restapp.service;

import com.app.restapp.model.shape.Shape;

import java.util.List;

public interface ShapeService {

	Shape getById(String id);

	List<Shape> getAll();

	Shape insert(Shape shape);

	Shape update(String id, Shape updatedShape);

	void delete(Shape shape);

	boolean deleteById(String id);

	void deleteAll();

	Shape rotateShape(String id, double angle);

	Shape moveShape(String id, double x, double y);

	Shape increase(String id, double scale);

	Shape reduce(String id, double scale);
}
