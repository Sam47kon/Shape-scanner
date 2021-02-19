package com.app.mvvm;

import com.app.model.Shape;

import java.io.IOException;
import java.util.List;

public interface ShapeService {

	List<Shape> search(String keyword) throws IOException;

	void save(Shape shape);

	void delete(Shape shape);
}
