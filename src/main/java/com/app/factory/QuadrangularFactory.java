package com.app.factory;

import com.app.model.figure.polygon.quadrangular.Quadrangular;
import com.app.model.point.Point2D;

import java.util.List;

public class QuadrangularFactory implements iShapeFactory<Point2D> {
	@Override
	public Quadrangular createShape(List<Point2D> points) {
		return new Quadrangular(points);
	}
}
