package com.app.consoleapp.factory;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.circle.Circle;

import java.util.List;

public class CircleFactory implements IShapeFactory {

	@Override
	public Circle createShape(List<Point> points) {
		return new Circle(points);
	}
}
