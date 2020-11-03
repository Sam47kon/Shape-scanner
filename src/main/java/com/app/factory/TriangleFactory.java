package com.app.factory;

import com.app.model.point.Point2D;
import com.app.model.shape.polygon.triangle.Triangle;

import java.util.List;

public class TriangleFactory implements IShapeFactory<Point2D> {
	@Override
	public Triangle createShape(List<Point2D> points) {
		return new Triangle(points);
	}
}
