package com.app.factory;

import com.app.model.figure.circle.Circle;
import com.app.model.point.Point2D;

import java.util.List;

public class CircleFactory implements iShapeFactory<Point2D> {

	@Override
	public Circle createShape(List<Point2D> points) {
		return new Circle(points);
	}
}
