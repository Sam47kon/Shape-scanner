package com.app.consoleapp.factory;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.polygon.triangle.Triangle;

import java.util.List;

public class TriangleFactory implements IShapeFactory {
	@Override
	public Triangle createShape(List<Point> points) {
		return new Triangle(points);
	}
}
