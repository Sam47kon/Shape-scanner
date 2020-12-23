package com.app.restapp.service;

import com.app.restapp.model.shape.Shape;

import java.util.List;

public interface ShapeService {
	Shape getById(String id);

	List<Shape> getAll();

	Shape insert(Shape shape);

	Shape update(Shape updatedShape, String id);

	void delete(Shape shape);

	boolean deleteById(String id);

	void deleteAll();
}
