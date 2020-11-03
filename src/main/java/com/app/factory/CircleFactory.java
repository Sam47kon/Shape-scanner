package com.app.factory;

import com.app.model.point.Point2D;
import com.app.model.shape.circle.Circle;

import java.util.List;

public class CircleFactory implements IShapeFactory<Point2D> {

	@Override
	public Circle createShape(List<Point2D> points) {
		return new Circle(points);
	}
}
