package com.app.factory;

import com.app.model.figure.polygon.triangle.Triangle;
import com.app.model.point.Point2D;

import java.util.List;

public class TriangleFactory implements iShapeFactory<Point2D> {
	@Override
	public Triangle createShape(List<Point2D> points) {
		return new Triangle(points);
	}
}
