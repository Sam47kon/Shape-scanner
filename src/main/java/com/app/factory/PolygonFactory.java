package com.app.factory;

import com.app.model.figure.polygon.Polygon;
import com.app.model.point.Point2D;

import java.util.List;

public class PolygonFactory implements iShapeFactory<Point2D> {
	@Override
	public Polygon createShape(List<Point2D> points) {
		return new Polygon(points);
	}
}
