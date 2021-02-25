package com.app.mvvm;

import com.app.model.Point;
import com.app.model.Shape;

import java.io.IOException;
import java.util.List;

public interface ShapeService {

	List<Shape> search(String keyword) throws IOException;

	Shape create(List<Point> points);

	Shape rotate(String id, Double rotateAngle);

	Shape move(String id, Double moveX, Double moveY);

	Shape scale(String id, boolean increase, Double scale);

	void delete(Shape shape);
}
