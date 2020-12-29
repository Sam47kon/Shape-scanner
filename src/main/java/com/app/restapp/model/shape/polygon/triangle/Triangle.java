package com.app.restapp.model.shape.polygon.triangle;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.ShapeType;
import com.app.restapp.model.shape.polygon.Polygon;

import java.util.List;

/**
 * Треугольник
 */
public final class Triangle extends Polygon {

	public Triangle(List<Point> points) {
		super(points);
		shapeType = ShapeType.TRIANGLE;
	}

	public Triangle() {
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
