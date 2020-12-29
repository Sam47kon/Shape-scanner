package com.app.consoleapp.factory;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.polygon.Polygon;

import java.util.List;

public class PolygonFactory implements IShapeFactory {
	@Override
	public Polygon createShape(List<Point> points) {
		return new Polygon(points);
	}
}
