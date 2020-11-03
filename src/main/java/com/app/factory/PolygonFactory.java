package com.app.factory;

import com.app.model.point.Point2D;
import com.app.model.shape.polygon.Polygon;

import java.util.List;

public class PolygonFactory implements IShapeFactory<Point2D> {
	@Override
	public Polygon createShape(List<Point2D> points) {
		return new Polygon(points);
	}
}
