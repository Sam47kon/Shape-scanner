package com.app.factory;

import com.app.model.point.Point2D;
import com.app.model.shape.polygon.quadrangular.Quadrangular;

import java.util.List;

public class QuadrangularFactory implements IShapeFactory<Point2D> {
	@Override
	public Quadrangular createShape(List<Point2D> points) {
		return new Quadrangular(points);
	}
}
