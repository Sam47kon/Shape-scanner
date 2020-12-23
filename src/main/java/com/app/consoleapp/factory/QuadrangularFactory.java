package com.app.consoleapp.factory;

import com.app.restapp.model.point.Point;
import com.app.restapp.model.shape.polygon.quadrangular.Quadrangular;

import java.util.List;

public class QuadrangularFactory implements IShapeFactory {
	@Override
	public Quadrangular createShape(List<Point> points) {
		return new Quadrangular(points);
	}
}
